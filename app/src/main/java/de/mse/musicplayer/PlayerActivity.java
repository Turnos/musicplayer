package de.mse.musicplayer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;


public class PlayerActivity extends Activity {

    private PlayerAdapter mPlayerAdapter;
    private SeekBar mSeekbarAudio;
    private boolean mUserIsSeeking = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        this.initializeUI();
        this.initializeSeekBar();
        this.initializePlaybackController();
        if (getIntent().getBooleanExtra("Random", false)){
            //TODO set a Random data source if random is true
        }
    }

    private void initializeUI(){
        //Play button
        final Button playButton = this.findViewById(R.id.top_button);
        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO implement Music.Play
                if(mPlayerAdapter.isPlaying()){
                    mPlayerAdapter.pause();
                    playButton.setText("PAUSE");
                }else{
                    mPlayerAdapter.play();
                    playButton.setText("PLAY");
                }
            }
        });

        //Previous button
        Button prevButton = this.findViewById(R.id.left_button);
        prevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO implement Music.Back
            }
        });

        //Next button
        Button nextButton = this.findViewById(R.id.right_button);
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO implement Music.Next
            }
        });

        //Shuffle button - Shuffling current playlist
        Button shuffleButton = this.findViewById(R.id.bottom_button);
        shuffleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO implement Shuffling the current playlist
                //First release current MediaPlayer
                mPlayerAdapter.release();
                mPlayerAdapter.shuffle(); //then shuffle the current playlist


            }
        });

        //SeekBar
        mSeekbarAudio = (SeekBar) findViewById(R.id.seekbar_audio);
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(isChangingConfigurations() && mPlayerAdapter.isPlaying() ){
            //Don't release Mediaplayer as screen is rotating & playing
        }else{
            mPlayerAdapter.release();
        }
    }

    private void initializeSeekBar(){
        mSeekbarAudio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int userSelectedPosition = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    userSelectedPosition = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mUserIsSeeking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mUserIsSeeking = false;
                mPlayerAdapter.seekTo(userSelectedPosition);
            }
        });
    }

    private void initializePlaybackController(){
        MediaPlayerHolder mMediaPlayerHolder = new MediaPlayerHolder(this);
        mMediaPlayerHolder.setPlaybackInfoListener(new PlaybackListener());
        mPlayerAdapter = mMediaPlayerHolder;
    }

    public class PlaybackListener extends PlaybackInfoListener{
        @Override
        void onDurationChanged(int duration) {
            mSeekbarAudio.setMax(duration);
        }

        @Override
        void onPositionChanged(int position) {
            if(!mUserIsSeeking){
                mSeekbarAudio.setProgress(position);
            }
        }
    }
}

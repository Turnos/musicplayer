package de.mse.musicplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.ArrayList;

import de.mse.musicplayer.ListAdministration.Playlist;
import de.mse.musicplayer.ListAdministration.Song;


public class PlayerActivity extends Activity {

    private PlayerAdapter mPlayerAdapter;
    private SeekBar mSeekbarAudio;
    private boolean mUserIsSeeking = false;
    final int PLAYLIST_REQUEST_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        this.initializeUI();
        this.initializeSeekBar();
        this.initializePlaybackController();
        /*if (getIntent().getBooleanExtra("Random", false)){
            //TODO set a Random data source if random is true, Edit: einfach die gesamte Liste spielen, geschieht automatisch
            mPlayerAdapter.shuffle();
        }
        */

    }

    private void initializeUI(){
        //Play button
        ConstraintLayout conLayout = (ConstraintLayout) findViewById(R.id.conlayout);
        conLayout.setOnTouchListener(new OnSwipeListener(this) {
            AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

            @Override
            public void onSwipeRight() {
                mPlayerAdapter.next();
            }

            @Override
            public void onSwipeLeft() {
                mPlayerAdapter.previous();
            }

            @Override
            public void onSwipeTop() {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
            }

            @Override
            public void onSwipeBottom() {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
            }
        });
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
                mPlayerAdapter.previous();
            }
        });

        //Next button
        Button nextButton = this.findViewById(R.id.right_button);
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mPlayerAdapter.next();
            }
        });

        //Shuffle button - Shuffling current playlist
        Button shuffleButton = this.findViewById(R.id.bottom_button);
        shuffleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                 mPlayerAdapter.shuffle();

            }
        });

        //SeekBar
        mSeekbarAudio = (SeekBar) findViewById(R.id.seekbar_audio);


    }

    public void openChangeplaylist(View view){
        Intent intent = new Intent(PlayerActivity.this, ChangePlaylistActivity.class);
        startActivityForResult(intent, PLAYLIST_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PLAYLIST_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                ArrayList<Song> songlist = data.getParcelableArrayListExtra("songlist");
                int songPos = data.getIntExtra("songPos", 0);
                mPlayerAdapter.loadPlaylist(songlist, songPos);
                mPlayerAdapter.play();
            }
        }
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
        MediaPlayerHolder mMediaPlayerHolder = new MediaPlayerHolder(this,null, 0);
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

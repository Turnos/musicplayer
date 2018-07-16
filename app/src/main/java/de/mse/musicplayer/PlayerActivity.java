package de.mse.musicplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import de.mse.musicplayer.ListAdministration.Playlist;
import de.mse.musicplayer.ListAdministration.Song;


public class PlayerActivity extends Activity {

    private static final String TAG = "PlayerActivity";
    private PlayerAdapter mPlayerAdapter;
    private SeekBar mSeekbarAudio;
    private boolean mUserIsSeeking = false;
    private TextView mArtist, mTitle, mDuration;
    final int PLAYLIST_REQUEST_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        this.initializeUI();
        this.initializeSeekBar();
        this.initializePlaybackController();
        Intent intent = getIntent();
        try{
            ArrayList<Song> songlist = intent.getParcelableArrayListExtra("songlist");
            int songPos = intent.getIntExtra("songPos", 0);
            loadPlaylist(songlist, songPos);
        }catch(Exception e){
            Log.d(TAG, "No songlist in intent detected");
        }
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

            @Override
            public void doubleTap() {
                mPlayerAdapter.reset();
            }
        });
        final Button playButton = this.findViewById(R.id.top_button);
        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
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

        //Artist TextView
        mArtist = (TextView) findViewById(R.id.artist_textfield);

        //Title TextView
        mTitle = (TextView) findViewById(R.id.title_textfield);

        //Duration TextView
        mDuration = (TextView) findViewById(R.id.length_textfield);

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
                loadPlaylist(songlist, songPos);
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

    private void loadPlaylist(ArrayList<Song> songlist, int songPos){
        mPlayerAdapter.loadPlaylist(songlist, songPos);
        mPlayerAdapter.play();
    }

    public class PlaybackListener extends PlaybackInfoListener{
        @Override
        void onDurationChanged(int duration, String artist, String title) {
            mSeekbarAudio.setMax(duration);
            mArtist.setText(artist);
            mTitle.setText(title);
            mDuration.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(duration),
                            TimeUnit.MILLISECONDS.toSeconds(duration)-
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))));

        }

        @Override
        void onPositionChanged(int position) {
            if(!mUserIsSeeking){
                mSeekbarAudio.setProgress(position);
            }
        }
    }
}

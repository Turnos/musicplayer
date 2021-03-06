package de.mse.musicplayer.player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import de.mse.musicplayer.ChangePlaylistActivity;
import de.mse.musicplayer.R;
import de.mse.musicplayer.layoutClasses.OnSwipeListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import de.mse.musicplayer.ListAdministration.Song;


public class PlayerActivity extends Activity {

    private PlayerAdapter mPlayerAdapter;
    private SeekBar mSeekbarAudio;
    private boolean mUserIsSeeking = false;
    private TextView mArtist, mTitle, mDuration;
    private final int PLAYLIST_REQUEST_CODE = 0;
    private Button playButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        this.initializeUI();
        this.initializeSeekBar();
        this.initializePlaybackController();
        Intent intent = getIntent();
        ArrayList<Song> songlist = intent.getParcelableArrayListExtra("songlist");
        int songPos = intent.getIntExtra("songPos", 0);
        if(songlist!=null)loadPlaylist(songlist, songPos);

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
                playButton.setText("PLAY");
            }
        });
        playButton = this.findViewById(R.id.top_button);
        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mPlayerAdapter.isPlaying()){
                    mPlayerAdapter.pause();
                    playButton.setText("PLAY");
                }else{
                    mPlayerAdapter.play();
                    playButton.setText("PAUSE");
                }
            }
        });

        //Previous button
        Button prevButton = this.findViewById(R.id.left_button);
        prevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mPlayerAdapter.previous();
                if(mPlayerAdapter.isPlaying()) playButton.setText("PAUSE");
                else playButton.setText("PLAY");
            }
        });

        //Next button
        Button nextButton = this.findViewById(R.id.right_button);
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mPlayerAdapter.next();
                if(mPlayerAdapter.isPlaying()) playButton.setText("PAUSE");
                else playButton.setText("PLAY");
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
        MediaPlayerHolder mMediaPlayerHolder = new MediaPlayerHolder(this);
        mMediaPlayerHolder.setPlaybackInfoListener(new PlaybackListener());
        mPlayerAdapter = mMediaPlayerHolder;
    }

    private void loadPlaylist(ArrayList<Song> songlist, int songPos){
        mPlayerAdapter.loadPlaylist(songlist, songPos);
        mPlayerAdapter.play();
    }

    class PlaybackListener extends PlaybackInfoListener {
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

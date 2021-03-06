package de.mse.musicplayer.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import de.mse.musicplayer.ListAdministration.AudioReader;
import de.mse.musicplayer.ListAdministration.Song;

public class MediaPlayerHolder implements PlayerAdapter {

    private static final int PLAYBACK_POSITION_REFRESH_INTERVAL_MS = 1000;

    private final Context mContext;
    private MediaPlayer mMediaPlayer;
    private PlaybackInfoListener mPlaybackInfoListener;
    private String mResourcePath;
    private ScheduledExecutorService mExecutor;
    private Runnable mSeekbarPositionUpdateTask;
    private ArrayList<Song> currentSongList; //Current SongList
    private int curPos; //current Position in SongList
    private Timer timer;

    public MediaPlayerHolder(Context context){
        mContext = context.getApplicationContext();

        currentSongList = AudioReader.getInstance().getList();
        long seed = System.nanoTime();
        Collections.shuffle(currentSongList, new Random(seed));

        curPos = 0;
        mResourcePath = currentSongList.get(curPos).getUrl();
    }

    @Override
    public void loadPlaylist(ArrayList<Song> playlist, int pos){
        if(playlist == null){
            currentSongList = AudioReader.getInstance().getList();
            long seed = System.nanoTime();
            Collections.shuffle(currentSongList, new Random(seed));
        }else{
            this.currentSongList = playlist;
        }

        this.curPos = pos;
        mResourcePath = currentSongList.get(pos).getUrl();
        this.reset();
    }

    private void initializeMediaPlayer(){
        if(mMediaPlayer == null){
            mMediaPlayer = MediaPlayer.create(mContext, Uri.parse(mResourcePath));
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopUpdatingCallbackWithPosition(true);
                    if(mPlaybackInfoListener != null){
                        mPlaybackInfoListener.onStateChanged(PlaybackInfoListener.State.COMPLETED);
                        mPlaybackInfoListener.onPlaybackCompleted();
                    }
                    next();
                }
            });
        }
    }


    public void setPlaybackInfoListener(PlaybackInfoListener listener){
        mPlaybackInfoListener = listener;
    }

    private void statUpdatingCallbackWithPosition(){
        if(mExecutor == null){
            mExecutor = Executors.newSingleThreadScheduledExecutor();
        }
        if(mSeekbarPositionUpdateTask == null){
            mSeekbarPositionUpdateTask = new Runnable() {
                @Override
                public void run() {
                    updateProgressCallbackTask();
                }
            };
        }
        mExecutor.scheduleAtFixedRate(mSeekbarPositionUpdateTask,
                0, PLAYBACK_POSITION_REFRESH_INTERVAL_MS, TimeUnit.MILLISECONDS);
    }

    private void updateProgressCallbackTask(){
        if(mMediaPlayer != null && mMediaPlayer.isPlaying()){
            int currentPosition = mMediaPlayer.getCurrentPosition();
            if(mPlaybackInfoListener != null){
                mPlaybackInfoListener.onPositionChanged(currentPosition);
            }
        }
    }

    private void stopUpdatingCallbackWithPosition(boolean resetUIPlaybackPosition){
        if (mExecutor != null){
            mExecutor.shutdownNow();
            mExecutor = null;
            mSeekbarPositionUpdateTask = null;
            if(resetUIPlaybackPosition && mPlaybackInfoListener != null){
                mPlaybackInfoListener.onPositionChanged(0);
            }
        }
    }

    @Override
    public void loadMedia(String resourcePath) {
        mResourcePath = resourcePath;
        initializeMediaPlayer();
        try{
            mMediaPlayer.prepare();
        }catch(Exception e){
            Log.getStackTraceString(e);
        }

        initializeProgressCallback();
    }

    @Override
    public void release() {
        if(mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public boolean isPlaying() {
        if (mMediaPlayer != null){
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    @Override
    public void play() {
        if(mMediaPlayer != null && !mMediaPlayer.isPlaying()){
            mMediaPlayer.start();
            if(mPlaybackInfoListener != null){
                mPlaybackInfoListener.onStateChanged(PlaybackInfoListener.State.PLAYING);
            }
            statUpdatingCallbackWithPosition();
        }else{
            loadMedia(mResourcePath);
            play();
        }
    }

    @Override
    public void pause() {
        if(mMediaPlayer != null && mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
            if(mPlaybackInfoListener != null){
                mPlaybackInfoListener.onStateChanged(PlaybackInfoListener.State.PAUSED);
            }
        }
    }

    @Override
    public void reset() {
        if(mMediaPlayer != null){
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
            loadMedia(mResourcePath);
            if(mPlaybackInfoListener != null){
                mPlaybackInfoListener.onStateChanged(PlaybackInfoListener.State.RESET);
            }
            stopUpdatingCallbackWithPosition(true);
        }else{
            loadMedia(mResourcePath);
        }
    }

    @Override
    public void initializeProgressCallback() {
        final int duration = mMediaPlayer.getDuration();
        if(mPlaybackInfoListener != null){
            mPlaybackInfoListener.onDurationChanged(duration,
                    currentSongList.get(curPos).getArtist(),
                    currentSongList.get(curPos).getTitle());
            mPlaybackInfoListener.onPositionChanged(0);
        }
    }

    @Override
    public void seekTo(int position) {
        if(mMediaPlayer != null){
            mMediaPlayer.seekTo(position);
        }
    }

    @Override
    public void next() {
        if (mMediaPlayer != null) {
            boolean playing = mMediaPlayer.isPlaying();
            if (curPos == (currentSongList.size() - 1)) {
                //Restart list
                curPos = 0;
                mResourcePath = currentSongList.get(curPos).getUrl();
            } else {
                mResourcePath = currentSongList.get(++curPos).getUrl();
            }
            this.reset();
            if(playing) this.play();
        }
    }

    @Override
    public void previous() {
        if(mMediaPlayer != null){
            boolean playing = mMediaPlayer.isPlaying();
            if(curPos != 0){
                //Select Previous Song
                mResourcePath = currentSongList.get(--curPos).getUrl();
            }else{
                curPos = currentSongList.size()-1;
                mResourcePath = currentSongList.get(curPos).getUrl();
            }
            this.reset();
            if(playing) this.play();
        }
    }

    @Override
    public void shuffle() {
        long seed = System.nanoTime();
        Collections.shuffle(currentSongList, new Random(seed));
        curPos = 0;
        mResourcePath = currentSongList.get(curPos).getUrl();
        this.reset();
        this.play();
    }
}

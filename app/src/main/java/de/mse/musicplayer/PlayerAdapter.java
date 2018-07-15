package de.mse.musicplayer;

import java.util.ArrayList;

import de.mse.musicplayer.ListAdministration.Song;

/**
 *  Allows {@link de.mse.musicplayer.PlayerActivity} to control media Playback of {@link MediaPlayerHolder}
 */
public interface PlayerAdapter {

    void loadMedia(String ressourcePath);

    void release();

    boolean isPlaying();

    void play();

    void pause();

    void reset();

    void initializeProgressCallback();

    void seekTo(int position);

    void next();

    void previous();

    void shuffle();

    void loadPlaylist(ArrayList<Song> playlist, int pos);

}

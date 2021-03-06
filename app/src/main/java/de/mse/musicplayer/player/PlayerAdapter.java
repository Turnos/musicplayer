package de.mse.musicplayer.player;

import java.util.ArrayList;

import de.mse.musicplayer.ListAdministration.Song;

/**
 *  Allows {@link PlayerActivity} to control media Playback of {@link MediaPlayerHolder}
 */
interface PlayerAdapter {

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

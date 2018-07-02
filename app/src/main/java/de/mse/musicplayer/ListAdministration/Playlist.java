package de.mse.musicplayer.ListAdministration;

import java.util.ArrayList;

public class Playlist {
    private String playlistName;
    private ArrayList<Song> playlistContent;

    public Playlist(String playlistName, ArrayList<Song> playlistContent) {
        this.playlistName = playlistName;
        this.playlistContent = playlistContent;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public ArrayList<Song> getPlaylistContent() {
        return playlistContent;
    }
}

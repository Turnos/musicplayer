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

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public ArrayList<Song> getPlaylistContent() {
        return playlistContent;
    }

    public void addSong(Song song) {
        this.playlistContent.add(song);
    }

    public void addAllSongs(ArrayList<Song> list){
        this.playlistContent.addAll(list);
    }
}

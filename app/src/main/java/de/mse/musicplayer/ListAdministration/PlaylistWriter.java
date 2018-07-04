package de.mse.musicplayer.ListAdministration;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PlaylistWriter {
    //TODO eine txt-Datei vs. mehrere?
    //TODO PlaylistReader
    //TODO Playlist-Verzeichnis (zur Laufzeit)?
    private Context context;

    public PlaylistWriter (Context context) {
        this.context = context;
    }

    public void writePlaylistToStorage (Playlist playlist){
        ArrayList<Song> tempPlaylist = playlist.getPlaylistContent();
        String path = context.getFilesDir() + "/playlists/";

        try {
            FileWriter writer = new FileWriter(new File(path,  "playlists.txt"));

            for (Song e: tempPlaylist){
                writer.write(playlist.getPlaylistName() + "|" + e.getArtist() + "|" + e.getTitle() + "/n)");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

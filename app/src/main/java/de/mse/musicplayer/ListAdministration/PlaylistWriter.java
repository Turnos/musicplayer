package de.mse.musicplayer.ListAdministration;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PlaylistWriter {
    private Context context;

    public PlaylistWriter (Context context) {
        this.context = context;
    }

    public void writePlaylistToStorage (Playlist playlist){
        ArrayList<Song> tempPlaylist = playlist.getPlaylistContent();
        String path = context.getFilesDir() + "";

        try {
            FileWriter writer = new FileWriter(new File(path,  "playlists.txt"));
            writer.write("This line is for testing purposes!\n");
            for (Song e: tempPlaylist){
                writer.write(playlist.getPlaylistName() + "|" + e.getArtist() + "|" + e.getTitle() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

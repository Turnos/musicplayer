package de.mse.musicplayer.ListAdministration;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class PlaylistWriter {
    private static final String LOG = "PlaylistWriter";
    private Context context;

    public PlaylistWriter (Context context) {
        this.context = context;
    }

    public void writePlaylistsToStorage (ArrayList<Playlist> playlists){
        try {
            FileWriter writer = new FileWriter(new File( context.getFilesDir(),"playlists.txt"));
            for (Playlist e: playlists){
                ArrayList<Song> playlist = e.getPlaylistContent();
                for (Song p: playlist){
                    writer.write(e.getPlaylistName() + "|" + p.getArtist() + "|" + p.getTitle() + "\n");
                }
                Log.d(LOG, "writePlaylistsToStorage: " + e.getPlaylistName());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

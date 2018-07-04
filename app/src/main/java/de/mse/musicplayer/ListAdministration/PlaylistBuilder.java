package de.mse.musicplayer.ListAdministration;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static de.mse.musicplayer.MainActivity.audioList;

public class PlaylistBuilder {
    private static final String TAG = "PlaylistBuilder ";
    private Context context;
    private ArrayList<Playlist> playlists;

    public PlaylistBuilder (Context context) {
        this.context = context;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    private void initializePlaylists (){
        playlists = new ArrayList<>();
        ArrayList <String> rawList = this.readFromStorage();
        String [] elements;
        for (String e: rawList){
            elements = e.split("|");
            this.addElementToPlaylists(elements);
        }
    }

    private ArrayList<String> readFromStorage (){
        ArrayList<String> list = new ArrayList<>();
        String path = context.getFilesDir() + "/playlists/playlists.txt";
        try {
            InputStream inputStream = context.openFileInput(path);
            if (inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String buffer;
                while ((buffer = reader.readLine() )!= null){
                    list.add(buffer);
                }
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.d(TAG, "initializePlaylists: No PlaylistFile");
        } catch (IOException e) {
            Log.d(TAG, "initializePlaylists: IOException");
        }
        return list;
    }

    private void addElementToPlaylists(String[] elements) {
        for (Playlist e: playlists){
            if (e.getPlaylistName().equals(elements[0])){
                this.addElementToExistingPlaylist(elements, e);
            } else {
                this.addElementToNewPlaylist(elements);
            }
        }
    }

    private void addElementToNewPlaylist(String[] elements) {
        //TODO
        Song e = audioList.getSong(elements[1], elements[2]);
        if (e != null){
            ArrayList<Song> songList = new ArrayList<>();
            songList.add(e);
            Playlist newPlaylist = new Playlist(elements[0], songList);
            this.playlists.add(newPlaylist);
            Log.d(TAG, "addElementToNewPlaylist: " + elements[1] + " " + elements[2] + " added to " + elements[0]);
        } else {
            Log.d(TAG, "addElementToNewPlaylist: " + elements[1] + " " + elements[2] + " not found");
        }
    }

    private void addElementToExistingPlaylist(String[] elements, Playlist playlist) {
        //TODO
        Song e = audioList.getSong(elements[1], elements[2]);
        if (e != null){
            playlist.addSong(e);
            Log.d(TAG, "addElementToExistingPlaylist: "  + elements[1] + " " + elements[2] + " added to " + elements[0] );
        } else {
            Log.d(TAG, "addElementToExistingPlaylist: " + elements[0] + " not found");
        }
    }
}

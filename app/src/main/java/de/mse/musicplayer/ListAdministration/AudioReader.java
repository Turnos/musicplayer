package de.mse.musicplayer.ListAdministration;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

public class AudioReader {

    private static AudioReader instance;
    private Context context;
    private static final String TAG = "AudioReader";

    private ArrayList<Song> listOfTracks;

    private AudioReader(Context context) {
        this.context = context;
        this.listOfTracks = this.getAllSongsFromStorage();
    }

    public static void initializeAudioReader(Context context){
        instance = new AudioReader(context);
    }

    public static AudioReader getInstance(){
        return instance;
    }

    public ArrayList<Song> getList() {
        return this.listOfTracks;
    }

    private ArrayList<Song> getAllSongsFromStorage() {
        Log.d(TAG, "getAllSongsFromStorage: started");
        ArrayList<Song> list = new ArrayList<>();

        ContentResolver contentResolver = context.getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        Cursor songCursor = contentResolver.query(songUri, null, selection,
                null, null);
        if (songCursor != null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songUrl = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            while (songCursor.getCount() > songCursor.getPosition()) {
                String currentTitle = songCursor.getString(songTitle);
                String currentArtist = songCursor.getString(songArtist);
                String currentUrl = songCursor.getString(songUrl);
                if (currentUrl.endsWith(".mp3")) {
                    //If file is mp3, then add it to the list.
                    list.add(new Song(currentArtist, currentTitle, currentUrl));
                    Log.d(TAG, "getAllSongsFromStorage: Audio found = " + currentTitle);
                }
                songCursor.moveToNext();
            }
        }
        Log.d(TAG, "getAllSongsFromStorage: List size is " + list.size());
        return list;
    }

    public Song getSong(String artist, String title) {
        for (Song e : listOfTracks) {
            if (e.getTitle().equals(title) && e.getArtist().equals(artist)) return e;
        }
        Log.d(TAG, "getSong: No Song found with " + artist + " - " + title);
        return null;

    }

    public ArrayList<Song> getSongsOfArtist(String artist) {
        ArrayList<Song> tracksOfArtist = new ArrayList<>();
        for (Song e : listOfTracks){
            if(e.getArtist().equals(artist)) tracksOfArtist.add(e);
        }
        return tracksOfArtist;
    }
}
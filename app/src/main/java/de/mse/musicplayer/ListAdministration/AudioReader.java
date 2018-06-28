package de.mse.musicplayer.ListAdministration;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaDescription;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class AudioReader {

    private ArrayList<MediaBrowser.MediaItem> list;
    private Context context;

    public AudioReader (Context context){
        this.list = this.getAllTracks();
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public ArrayList<MediaBrowser.MediaItem> getListByArtist (String artist){
        ArrayList<MediaBrowser.MediaItem> listByArtist = new ArrayList<>();

        for (MediaBrowser.MediaItem e: list){
            if (!list.isEmpty() && checkItemForTitle(e) && e.getDescription().getTitle().toString().contains(artist)){
                listByArtist.add(e);
            }
        }

        return listByArtist;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public ArrayList<String> getArtists (){
        ArrayList<String> listOfArtists = new ArrayList<>();

        for (MediaBrowser.MediaItem e: list){
            String [] titleDescription = e.getDescription().getTitle().toString().split("|");
            if (!listOfArtists.isEmpty() && !listOfArtists.contains(titleDescription[1])){
                listOfArtists.add(titleDescription[1]);
            }
        }

        return listOfArtists;
    }

    public ArrayList<MediaBrowser.MediaItem> getList() {
        return list;
    }

    /*
    title des MediaDescriptionitems = artist + "|" + title + "|" + duration
     */
    @TargetApi(Build.VERSION_CODES.M)
    private ArrayList<MediaBrowser.MediaItem> getAllTracks () {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = context.getContentResolver().query(uri, null, null,null, null);
        ArrayList<MediaBrowser.MediaItem> listOfTracks = new ArrayList<>();

        try {
            cursor.moveToFirst();

            while (cursor.moveToNext()){
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

                MediaDescription.Builder builder = new MediaDescription.Builder();
                builder.setMediaUri(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                builder.setTitle(artist + "|" + title + "|" + duration);

                MediaDescription item = builder.build();

                MediaBrowser.MediaItem trackData = new MediaBrowser.MediaItem(item, MediaBrowser.MediaItem.FLAG_PLAYABLE);
                listOfTracks.add(trackData);
            }

        } catch (Exception e) {
            Log.d(TAG, "listOfTracks(): " + e.getMessage());
        }

        return listOfTracks;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean checkItemForTitle (MediaBrowser.MediaItem item){
        return item.getDescription().getTitle() != null;
    }
}

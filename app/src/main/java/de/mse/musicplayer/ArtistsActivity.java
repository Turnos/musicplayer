package de.mse.musicplayer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import de.mse.musicplayer.ListAdministration.AudioReader;
import de.mse.musicplayer.ListAdministration.Song;
import de.mse.musicplayer.layoutClasses.RecyclerViewAdapter;


public class ArtistsActivity extends Activity{

    private static final String TAG = "ArtistsActivity";

    private ArrayList<String> listOfArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists);
        Log.d(TAG, "onCreate: started");
        this.listOfArtists = initializeArtistNames();
        this.initializeRecyclerView();
    }

    private ArrayList<String> initializeArtistNames() {
        ArrayList<String> artistList = new ArrayList<>();
        ArrayList<Song> songList = AudioReader.getInstance().getList();
        artistList.add("All");
        for (Song e: songList){
            if (!artistList.contains(e.getArtist())){
                Log.d(TAG, "initializeArtistNames: " + e.getArtist() + " added.");
                artistList.add(e.getArtist());
            }
        }
        return artistList;
    }

    private void initializeRecyclerView (){
        Log.d(TAG, "initRecyclerView: called");
        RecyclerView recyclerView = findViewById(R.id.artists_list);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, listOfArtists);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

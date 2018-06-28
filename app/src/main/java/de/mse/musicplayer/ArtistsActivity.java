package de.mse.musicplayer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import de.mse.musicplayer.ListAdministration.AudioReader;
import de.mse.musicplayer.layoutClasses.RecyclerViewAdapter;

public class ArtistsActivity extends Activity{

    private static final String TAG = "ArtistsActivity";

    private ArrayList<String> listOfArtists = new ArrayList<>();
    private AudioReader trackAdministration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists);
        Log.d(TAG, "onCreate: started");
        this.initializeTracks();
        this.initializeRecyclerView();
    }

    private void initializeTracks() {
        //this.trackAdministration = new AudioReader(this);
        //this.listOfArtists = trackAdministration.getArtists();
        //TODO EDIT THIS HARDCODE
        this.listOfArtists.add("BLA|BLABLA");
        this.listOfArtists.add("YO|YOYOYO");
        this.listOfArtists.add("NA|NANANA");
        this.listOfArtists.add("HA|HAHAHA");
    }

    private void initializeRecyclerView (){
        Log.d(TAG, "initRecyclerView: called");
        RecyclerView recyclerView = findViewById(R.id.artists_list);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, listOfArtists);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

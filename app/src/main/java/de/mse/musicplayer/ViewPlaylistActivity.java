package de.mse.musicplayer;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import de.mse.musicplayer.ListAdministration.Song;
import de.mse.musicplayer.layoutClasses.SonglistAdapter;

public class ViewPlaylistActivity extends Activity {

    ArrayList<Song> playlist;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_playlist);
        playlist = getIntent().getParcelableArrayListExtra("songlist");
        updateView();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void updateView(){
        //initializeSonglist();
        initializeUI();
    }

    private void initializeUI() {
        listView = findViewById(R.id.list_playlist);

        listView.setAdapter(new SonglistAdapter(this, playlist));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("songlist", playlist);
                intent.putExtra("songPos", position);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initializeSonglist() {
        playlist = getIntent().getParcelableArrayListExtra("playlist");
    }
}

package de.mse.musicplayer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import de.mse.musicplayer.ListAdministration.Playlist;
import de.mse.musicplayer.ListAdministration.PlaylistBuilder;
import de.mse.musicplayer.layoutClasses.PlaylistAdapter;

public class PlaylistActivity extends Activity{

    PlaylistAdapter adapter;
    ListView listView;
    ArrayList<Playlist> playlists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);
        initializeUI();
    }

    private void initializeUI(){
        this.listView = findViewById(R.id.listViewPlaylists);

        initializePlaylists();

        adapter = new PlaylistAdapter(this, playlists);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO
                Toast.makeText(getApplicationContext(), playlists.get(position).getPlaylistName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializePlaylists() {
        PlaylistBuilder playlistBuilder = new PlaylistBuilder(this);
        playlists = playlistBuilder.getPlaylists();
        if (playlists == null){
            playlists = new ArrayList<>();
            playlists.add(new Playlist("You haven't got any playlist", null));
        }
    }
}

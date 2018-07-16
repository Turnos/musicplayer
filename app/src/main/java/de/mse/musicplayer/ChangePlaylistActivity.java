package de.mse.musicplayer;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import de.mse.musicplayer.ListAdministration.Playlist;
import de.mse.musicplayer.ListAdministration.PlaylistBuilder;
import de.mse.musicplayer.ListAdministration.Song;
import de.mse.musicplayer.layoutClasses.PlaylistAdapter;

public class ChangePlaylistActivity extends Activity {

    ArrayList<Playlist> playlists;
    ListView listView;
    PlaylistAdapter adapter;
    final int PLAYLIST_REQUEST_CODE  = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_playlist);
        //updateView();

        initializePlaylists();
        initializeUI();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void updateView(){
        initializePlaylists();
        initializeUI();
    }

    private void initializeUI(){
        this.listView = findViewById(R.id.list_playlist);
        adapter = new PlaylistAdapter(this, playlists);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChangePlaylistActivity.this, ViewPlaylistActivity.class);
                intent.putParcelableArrayListExtra("songlist", playlists.get(position).getPlaylistContent());
                startActivityForResult(intent, PLAYLIST_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == PLAYLIST_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                ArrayList<Song> songlist = data.getParcelableArrayListExtra("songlist");
                int songPos = data.getIntExtra("songPos", 0);
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("songlist", songlist);
                intent.putExtra("songPos", songPos);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    private void initializePlaylists(){
        playlists = PlaylistBuilder.getInstance(getApplicationContext()).getPlaylists();
    }
}

package de.mse.musicplayer;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.mse.musicplayer.ListAdministration.Playlist;
import de.mse.musicplayer.ListAdministration.PlaylistBuilder;
import de.mse.musicplayer.ListAdministration.PlaylistWriter;
import de.mse.musicplayer.layoutClasses.PlaylistAdapter;

import static android.support.constraint.Constraints.TAG;

public class PlaylistActivity extends Activity{

    PlaylistAdapter adapter;
    ListView listView;
    ArrayList<Playlist> playlists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);
        updateView();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void updateView(){
        initializePlaylists();
        initializeUI();
    }

    @Override
    public void onBackPressed() {
        Intent switcher = new Intent(PlaylistActivity.this, MainActivity.class);
        startActivity(switcher);
    }

    private void initializeUI(){
        this.listView = findViewById(R.id.listViewPlaylists);
        adapter = new PlaylistAdapter(this, playlists);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = view.findViewById(R.id.lv_playlisttitle);
                String title = textView.getText().toString();
                if (title.equals("You haven't got any playlist")) {
                    Toast.makeText(getApplicationContext(), "Can't edit", Toast.LENGTH_SHORT).show();
                } else {
                    Intent switcher = new Intent(PlaylistActivity.this, EditPlaylistActivity.class);
                    switcher.putExtra("PlaylistName", playlists.get(position).getPlaylistName());
                    startActivity(switcher);
                }
            }
        });

        Button button_addplaylist = findViewById(R.id.button_addplaylist);
        button_addplaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switcher = new Intent (PlaylistActivity.this, EditPlaylistActivity.class);
                switcher.putExtra("PlaylistName", "");
                startActivity(switcher);
            }
        });
    }

    private void initializePlaylists() {
        playlists = PlaylistBuilder.getInstance().getPlaylists();
        if (playlists.isEmpty()){
            playlists.add(new Playlist("You haven't got any playlist", null));
        }
    }
}

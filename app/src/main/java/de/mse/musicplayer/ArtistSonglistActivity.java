package de.mse.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import de.mse.musicplayer.ListAdministration.AudioReader;
import de.mse.musicplayer.ListAdministration.Song;
import de.mse.musicplayer.layoutClasses.SonglistAdapter;
import de.mse.musicplayer.player.PlayerActivity;

public class ArtistSonglistActivity extends Activity {

    private ListView listView;
    private ArrayList<Song> songArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_songlist);
        this.initializeSonglist();
        this.initializeUI();
    }

    private void initializeUI (){
        listView = findViewById(R.id.listview_artistsonglist);
        SonglistAdapter adapter = new SonglistAdapter(this, songArrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ArtistSonglistActivity.this, PlayerActivity.class);
                intent.putParcelableArrayListExtra("songlist", songArrayList);
                intent.putExtra("songPos", position);
                startActivity(intent);
            }
        });
        TextView textView = findViewById(R.id.textview_artistname);
        textView.setText(getIntent().getStringExtra("Artist"));
    }

    private void initializeSonglist (){
        String artistName = getIntent().getStringExtra("Artist");
        if (artistName.equals("All")) {
            this.songArrayList = AudioReader.getInstance().getList();
        } else {
            this.songArrayList = AudioReader.getInstance().getSongsOfArtist(artistName);
        }
    }
}

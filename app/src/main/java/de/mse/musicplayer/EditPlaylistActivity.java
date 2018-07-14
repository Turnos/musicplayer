package de.mse.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.mse.musicplayer.ListAdministration.AudioReader;
import de.mse.musicplayer.ListAdministration.Playlist;
import de.mse.musicplayer.ListAdministration.PlaylistBuilder;
import de.mse.musicplayer.ListAdministration.PlaylistWriter;
import de.mse.musicplayer.ListAdministration.Song;
import de.mse.musicplayer.layoutClasses.EditPlaylistAdapter;

public class EditPlaylistActivity extends Activity {

    private static final String TAG = "EditPlaylistActivity";
    private ArrayList<Song> songList; // songList from AudioReader
    private Playlist playlist; // songs from playlist /for editing or creating purposes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_songs);

        this.initializeAudioList();
        this.initializeUI();
    }

    private Playlist getPlaylistByName (String playlistName){
        return PlaylistBuilder.getInstance(getApplicationContext()).getPlaylistByName(playlistName);
    }

    private void initializeAudioList() {
        this.songList = AudioReader.getInstance().getList();
    }

    private void initializeUI() {

        final EditText editText = findViewById(R.id.edittext_playlistname);
        if (!getIntent().getStringExtra("PlaylistName").equals("")){
            String temp = getIntent().getStringExtra("PlaylistName");
            this.playlist = this.getPlaylistByName(temp);
            editText.setText(temp);
        } else {
            Log.d(TAG, "initializeUI: No Playlist found - " + getIntent().getStringExtra("PlaylistName"));
        }

        ListView listView = findViewById(R.id.listview_songoverview);
        final EditPlaylistAdapter adapter = new EditPlaylistAdapter(this, songList);
        listView.setAdapter(adapter);

        Button buttonCreatePlaylist = findViewById(R.id.button_createplaylist);
        buttonCreatePlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String playlistName = editText.getText().toString();
                if (playlistName.length() < 4){
                    Toast.makeText(getApplicationContext(), "The playlist name is too short", Toast.LENGTH_SHORT).show();
                } else {
                    if (playlist != null){ //editing playlist
                        playlist.setPlaylistName(playlistName);
                        playlist.getPlaylistContent().clear();
                        playlist.addAllSongs(adapter.getPlaylist());
                    } else { // creating playlist
                        playlist = new Playlist(playlistName, adapter.getPlaylist());
                        PlaylistBuilder.getInstance(getApplicationContext()).addPlaylist(playlist);
                    }
                    persistChanges();
                    Intent switcher = new Intent(EditPlaylistActivity.this, PlaylistActivity.class);
                    startActivity(switcher);
                }
            }
        });

        if (playlist != null && playlist.getPlaylistContent() != null){
            Log.d(TAG, "initializeUI: checking Checkboxes");
            checkPlaylistSettings(listView);
        }
    }

    private void persistChanges() {
        Log.d(TAG, "persistChanges");
        PlaylistWriter playlistWriter = new PlaylistWriter(EditPlaylistActivity.this);
        playlistWriter.writePlaylistsToStorage(PlaylistBuilder.getInstance(getApplicationContext()).getPlaylists());
    }

    private void checkPlaylistSettings(ListView listView) {
        //Checking the listView if a Song in the playlist is already selected and marking its CheckBox
        //It is very probable this code can get optimized since there are two for-Loops
        for (int i = 0; i < listView.getCount(); i++){
            View v = listView.getAdapter().getView(i,null,null);
            TextView tv_songname = v.findViewById(R.id.textview_songdetails); //Accessing the TextView of the adapter element / Song-entry
            String songName = (String) (tv_songname.getText());
            CheckBox checkBox = v.findViewById(R.id.checkbox_editplaylist);
            Log.d(TAG, "checkPlaylistSettings: " + songName);
            for (Song e: playlist.getPlaylistContent()){
                if (e.toString().equals(songName)) {
                    Log.d(TAG,"checkPlaylistSettings: setting check in CheckBox of " + songName);
                    checkBox.setChecked(true);
                    checkBox.callOnClick();
                }
            }
        }
    }
}

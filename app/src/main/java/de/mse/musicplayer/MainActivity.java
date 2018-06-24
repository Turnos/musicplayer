package de.mse.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeUI();
    }

    private void initializeUI(){
        setContentView(R.layout.activity_main);

        //Random button - directs to the player with a playlist with random tracks
        Button randomButton = this.findViewById(R.id.top_button);
        randomButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO change the target activity
                // Intent switcher = new Intent(WelcomeFrame.this, ArtistsActivity.class);
                // startActivity(switcher);
            }
        });

        //Artist button - directs to playlists filtered by artists
        Button artistButton = this.findViewById(R.id.left_button);
        artistButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent switcher = new Intent(MainActivity.this, ArtistsActivity.class);
                startActivity(switcher);
            }
        });

        //Playlist button - directs to custom playlists
        Button playlistButton = this.findViewById(R.id.right_button);
        playlistButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent switcher = new Intent(MainActivity.this, PlaylistActivity.class);
                startActivity(switcher);
            }
        });

        //Info button - directs to options and help
        Button infoButton = this.findViewById(R.id.bottom_button);
        infoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO change the target activity
                //Intent switcher = new Intent(WelcomeFrame.this, ArtistsActivity.class);
                //startActivity(switcher);
            }
        });
    }
}

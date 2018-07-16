package de.mse.musicplayer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import de.mse.musicplayer.ListAdministration.AudioReader;

import static de.mse.musicplayer.ListAdministration.AudioReader.initializeAudioReader;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private static final int MY_PERMISSION_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: " + this.checkPermissions());
        this.initializeAudioList();
        this.initializeUI();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //User answered Permission Request
        switch(requestCode){
            case MY_PERMISSION_REQUEST:{
                //User granted Permission?
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    //User denied Permission
                    Toast.makeText(this, "No permission granted!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }

    private boolean checkPermissions(){
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            //show Permission Request
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSION_REQUEST);
            }else{
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSION_REQUEST);
            }
        }else{
            //permissions already granted, you are ready to read EXTERNAL_STORAGE
            Log.d(TAG, "checkPermissions: TRUE");
            return true;
        }
        Log.d(TAG, "checkPermissions: FALSE");
        return false;
    }

    private void initializeUI(){
        setContentView(R.layout.activity_main);

        //Random button - directs to the player with a playlist with random tracks
        Button randomButton = this.findViewById(R.id.top_button);
        randomButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO
                Intent switcher = new Intent(MainActivity.this, PlayerActivity.class);
                switcher.putExtra("Random", true);
                startActivity(switcher);
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
                //TODO implement Info
                //Intent switcher = new Intent(WelcomeFrame.this, ArtistsActivity.class);
                //startActivity(switcher);
            }
        });
    }

    private void initializeAudioList(){
        initializeAudioReader(getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        //NOTHING HAPPENS -> Workaround to deny the cycle between switching from PlaylistActivity and MainActivity
    }
}

package de.mse.musicplayer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlayerActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        this.initializeUI();
    }

    private void initializeUI(){
        //Play button
        Button playButton = this.findViewById(R.id.top_button);
        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO implement Music.Play
            }
        });

        //Previous button
        Button prevButton = this.findViewById(R.id.left_button);
        prevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO implement Music.Back
            }
        });

        //Next button
        Button nextButton = this.findViewById(R.id.right_button);
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO implement Music.Next
            }
        });

        //Shuffle button - Shuffling current playlist
        Button shuffleButton = this.findViewById(R.id.bottom_button);
        shuffleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO implement Shuffling the current playlist
            }
        });
    }
}

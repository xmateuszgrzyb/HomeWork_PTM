package com.example.homework_1;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;


public class SecondActivity extends AppCompatActivity {

    public static final String LAST_POSITION_ID = "Last pos id";
    public static final String CONTACT_ID = "Contact id";
    public static final int BUTTON_REQUEST = 1;
    public MediaPlayer mp;
    public Uri[] received_sound;
    final Random rnd = new Random();
    private int selected_contact = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        Bundle extras = getIntent().getExtras();
        final String contact = extras.getString(MainActivity.CONTACT_ID);
        final TextView txV = (TextView) findViewById(R.id.current_contact_text);
        txV.setText(contact);



        final ImageView img = (ImageView) findViewById(R.id.imageView);
        // I have 3 images named img_0 to img_2, so...
        final String str = "img_" + rnd.nextInt(4);
        img.setImageDrawable
                (
                        getResources().getDrawable(getResourceID(str, "drawable",
                                getApplicationContext()))
                );

        final int position = extras.getInt(ThirdActivity.POSITION_ID);
        Button change_sound = (Button) findViewById(R.id.change_sounds);
        change_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp.isPlaying()){
                    mp.pause();
                }
                Intent soundPick = new Intent(getApplicationContext(), ThirdActivity.class);
                //Attach the current_sound value to the Intent. This value can be
                //retrieved with the SOUND_ID key.
                soundPick.putExtra(MainActivity.CONTACT_ID, contact);
                soundPick.putExtra(LAST_POSITION_ID,position);
                //Start the SecondActivity indicating that it will give a result
                //back for a BUTTON_REQUEST request code
                startActivityForResult(soundPick, BUTTON_REQUEST);
            }
        });

        received_sound = new Uri[1];
        if (extras.getString(ThirdActivity.SOUND_ID) != null) {

            String uriString = extras.getString(ThirdActivity.SOUND_ID);
            received_sound[0] = Uri.parse(uriString);

            FloatingActionButton play_pause_sound = (FloatingActionButton) findViewById(R.id.playSound);
            mp = MediaPlayer.create(this, received_sound[0]);
            play_pause_sound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mp.isPlaying()){
                        mp.pause();
                    }
                    else{
                        mp.start();
                    }
                }
            });
        }
        else
        {
            FloatingActionButton play_pause_sound = (FloatingActionButton) findViewById(R.id.playSound);
            mp = MediaPlayer.create(this, R.raw.ringd);
            play_pause_sound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mp.isPlaying()){
                        mp.pause();
                    }
                    else{
                        mp.start();
                    }
                }
            });
        }
    }

    protected final static int getResourceID
            (final String resName, final String resType, final Context ctx)
    {
        final int ResourceID =
                ctx.getResources().getIdentifier(resName, resType,
                        ctx.getApplicationInfo().packageName);
        if (ResourceID == 0)
        {
            throw new IllegalArgumentException
                    (
                            "No resource string found with name " + resName
                    );
        }
        else
        {
            return ResourceID;
        }

    }
    public void setReturnClick(View v){
        if (mp.isPlaying()){
            mp.pause();
        }
        //Create an empty Intent and add the selected_sound variable to it
        Intent data = new Intent(getApplicationContext(), MainActivity.class);
        data.putExtra(CONTACT_ID,selected_contact);
        //Set the result code for the MainActivity and attach the data Intent
        setResult(RESULT_OK, data);
        //Destroy this Activity and propagate the ActivityResult
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Make sure the request was successful
            if (requestCode == BUTTON_REQUEST) {
                selected_contact = data.getIntExtra(CONTACT_ID, 0);
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (mp.isPlaying()){
                mp.pause();
            }
            Toast.makeText(getApplicationContext(), getText(R.string.back_message2), Toast.LENGTH_SHORT).show();
        }
    }

}


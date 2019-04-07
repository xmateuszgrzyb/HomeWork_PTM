package com.example.homework_1;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {

    public static final String SOUND_ID = "Sound id";
    public static final String POSITION_ID = "Position_id";
    public int soundSelection;
    public int last_selection = 0;
    public Uri[] current_sound;
    private int pos = 0;
    public static final int BUTTON_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        final Bundle extras = getIntent().getExtras();
        final String contact = extras.getString(MainActivity.CONTACT_ID);
        last_selection = extras.getInt(SecondActivity.LAST_POSITION_ID);
        current_sound = new Uri[1];
        Button selectButton = (Button) findViewById(R.id.button4);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (soundSelection) {
                    case 0:
                        current_sound[0] = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ringd);
                        break;
                    case 1:
                        current_sound[0] = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mario);
                        break;
                    case 2:
                        current_sound[0] = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ring01);
                        break;
                    case 3:
                        current_sound[0] = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ring02);
                        break;
                    case 4:
                        current_sound[0] = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ring03);
                        break;
                    case 5:
                        current_sound[0] = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ring04);
                        break;
                }
                Intent soundPick = new Intent(getApplicationContext(), SecondActivity.class);
                //Attach the current_sound value to the Intent. This value can be
                //retrieved with the SOUND_ID key.
                soundPick.putExtra(SOUND_ID, current_sound[0].toString());
                soundPick.putExtra(MainActivity.CONTACT_ID, contact);
                soundPick.putExtra(POSITION_ID,soundSelection);
                //Start the SecondActivity indicating that it will give a result
                //back for a BUTTON_REQUEST request code
                startActivityForResult(soundPick, BUTTON_REQUEST);
                finish();
            }
        });

        Button delete = (Button) findViewById(R.id.button3);
        final Spinner spin = (Spinner) findViewById(R.id.spinner_sounds);

        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Spinner spinner = (Spinner) findViewById(R.id.spinner_sounds);
                spinner.setSelection(0);
            }
        });

        setSpinnerItemSelectedListener();
    }


    public void setSpinnerItemSelectedListener(){
        Spinner spinner = (Spinner) findViewById(R.id.spinner_sounds);
        spinner.setSelection(last_selection);
        if(spinner != null){
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    soundSelection = position;
                    if(soundSelection == 0)
                    {
                        Toast.makeText(parent.getContext(), "Selected: default_ring", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(parent.getContext(), "Selected: sound_" + soundSelection, Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Make sure the request was successful
            if (requestCode == BUTTON_REQUEST) {
                pos = data.getIntExtra(POSITION_ID, 0);
            }
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), getText(R.string.back_message), Toast.LENGTH_SHORT).show();
        }
    }
}

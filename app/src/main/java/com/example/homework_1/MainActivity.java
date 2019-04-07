package com.example.homework_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public boolean checked = false;
    public static final String CONTACT_ID = "Contact id";
    public static final int BUTTON_REQUEST = 1;
    private int current_contact = 0;
    public String selected_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button cancelButton = (Button) findViewById(R.id.button);
        final Button selectButton = (Button) findViewById(R.id.button2);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected_contact == null)
                {
                    Toast.makeText(getApplicationContext(), getText(R.string.wrongChoice), Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent contactPick = new Intent(getApplicationContext(), SecondActivity.class);
                    //Attach the current_sound value to the Intent. This value can be
                    //retrieved with the SOUND_ID key.
                    contactPick.putExtra(CONTACT_ID, selected_contact);
                    //Start the SecondActivity indicating that it will give a result
                    //back for a BUTTON_REQUEST request code
                    startActivityForResult(contactPick, BUTTON_REQUEST);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    RadioGroup group = (RadioGroup) findViewById(R.id.Kontener);
                    group.clearCheck();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Make sure the request was successful
            if (requestCode == BUTTON_REQUEST) {
                current_contact = data.getIntExtra(CONTACT_ID, 0);
            }
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), getText(R.string.back_message), Toast.LENGTH_SHORT).show();
        }
    }

    public void onRadioButtonClicked(View view) {
        checked = ((RadioButton) view).isChecked();
        if (checked)
        {
            // Check which radio button was clicked
            RadioGroup group = (RadioGroup) findViewById(R.id.Kontener);
            switch (view.getId()) {
                case R.id.radioButton1:
                    selected_contact = getString(R.string.Contact_1);
                    break;
                case R.id.radioButton2:
                    selected_contact = getString(R.string.Contact_2);
                    break;
                case R.id.radioButton3:
                    selected_contact = getString(R.string.Contact_3);
                    break;
                case R.id.radioButton4:
                    selected_contact = getString(R.string.Contact_4);
                    break;
                case R.id.radioButton5:
                    selected_contact = getString(R.string.Contact_5);
                    break;
            }
        }
        else
        {
            selected_contact = null;
        }
    }
}


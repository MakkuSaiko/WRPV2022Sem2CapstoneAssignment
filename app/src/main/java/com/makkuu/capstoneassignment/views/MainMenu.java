package com.makkuu.capstoneassignment.views;

import androidx.appcompat.app.AppCompatActivity;
import com.makkuu.capstoneassignment.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainMenu extends AppCompatActivity {

    RadioButton[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        RadioGroup group = findViewById(R.id.radioGroup);
        RadioButton[] buttons = new RadioButton[]{findViewById(R.id.rbtn2Players),findViewById(R.id.rbtn3Players), findViewById(R.id.rbtn4Players)};
        group.setOnCheckedChangeListener((view, other) -> setEditTextVisibility());

    }

    private void setEditTextVisibility()
    {

        if(buttons[0].isChecked()) setOtherInvisible();
        if(buttons[1].isChecked()) setPlayer3Visible();
        if(buttons[2].isChecked()) setPlayer4Visible();
    }

    private void setPlayer3Visible()
    {
        findViewById(R.id.edtTextPlayer3).setVisibility(View.VISIBLE);
    }
    private void setPlayer4Visible()
    {
        setPlayer3Visible();
        findViewById(R.id.edtTextPlayer4).setVisibility(View.VISIBLE);
    }

    private void setOtherInvisible()
    {
        findViewById(R.id.edtTextPlayer3).setVisibility(View.INVISIBLE);
        findViewById(R.id.edtTextPlayer4).setVisibility(View.INVISIBLE);
    }

    private void startGame(View view)
    {
        String[] playerNames = new String[4];   //initialize with max for players

        //add player 1 and 2 (will always be present)
        EditText[] editTexts = new EditText[]{findViewById(R.id.edtTextPlayer1),findViewById(R.id.edtTextPlayer2),findViewById(R.id.edtTextPlayer3),findViewById(R.id.edtTextPlayer4)};
        for (int i = 0; i < editTexts.length; i++) {
            if(i==2&&(!buttons[1].isChecked()||!buttons[2].isChecked())) break; //break loop if neither 3 players or 4 players are selected
            if(editTexts[i].getText().toString().equals("")) playerNames[i] = editTexts[i].getHint().toString();
            else playerNames[i] = editTexts[i].getText().toString();
        }
        Intent intent = new Intent();
        intent.setClassName(".controllers","Game");
        Bundle bundle = new Bundle();
        bundle.putStringArray("players", playerNames);
        startActivity(intent,bundle);

    }



}
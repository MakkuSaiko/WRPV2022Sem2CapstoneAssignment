package com.makkuu.capstoneassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.makkuu.capstoneassignment.models.Colours;
import com.makkuu.capstoneassignment.models.Shapes;
import com.makkuu.capstoneassignment.models.Tile;

import java.util.Random;

public class temp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        Shapes.getInstance(this);
        Colours.getInstance(this);
        findViewById(R.id.button3).setOnClickListener(this::addTile);
    }

    private void addTile(View view)
    {
        Random rand = new Random();

        LinearLayout layout = findViewById(R.id.linLayout);
        layout.addView(new Tile(this, Tile.Colour.values()[rand.nextInt(6)], Tile.Shape.values()[rand.nextInt(6)]));
    }
}
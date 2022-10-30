package com.makkuu.capstoneassignment.models;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.makkuu.capstoneassignment.R;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Colours
{

    static Map<Tile.Colour,Integer> colours;

    private Colours()
    {

    }

    public static Colours getInstance(Context context)
    {
        Colours colours = new Colours();
        initialize(context);
        return colours;
    }

    private static void initialize(Context context)
    {
        if(colours == null)
            colours = new HashMap<>();
        populateColours(context);

    }

    public static void populateColours(Context context)
    {
        colours.put(Tile.Colour.RED,context.getColor(R.color.RED));
        colours.put(Tile.Colour.BLUE,context.getColor(R.color.BLUE));
        colours.put(Tile.Colour.GREEN,context.getColor(R.color.GREEN));
        colours.put(Tile.Colour.ORANGE,context.getColor(R.color.ORANGE));
        colours.put(Tile.Colour.PURPLE,context.getColor(R.color.PURPLE));
        colours.put(Tile.Colour.YELLOW, context.getColor(R.color.YELLOW));

    }

    public static int getColor(Tile.Colour name)
    {
        return colours.get(name);
    }



}

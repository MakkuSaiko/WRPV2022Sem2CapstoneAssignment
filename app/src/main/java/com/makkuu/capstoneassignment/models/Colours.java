package com.makkuu.capstoneassignment.models;

import android.graphics.Color;

import com.makkuu.capstoneassignment.R;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Colours
{

    static Map<String,Color> colours;

    public Colours getInstance()
    {
        initialize();
        return this;
    }

    private static void initialize()
    {
        if(colours == null)
            colours = new HashMap<>();
    }

    public static void populateColours(Color red, Color blue, Color green, Color orange, Color purple, Color yellow)
    {
        colours.put("red",red);
        colours.put("blue",blue);
        colours.put("green",green);
        colours.put("orange",orange);
        colours.put("purple",purple);
        colours.put("yellow", yellow);

    }

    public static Color getColor(String name)
    {
        name = name.toLowerCase(Locale.ROOT);
        return colours.get(name);
    }



}

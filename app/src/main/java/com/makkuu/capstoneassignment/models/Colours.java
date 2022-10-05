package com.makkuu.capstoneassignment.models;

import android.graphics.Color;

import java.util.HashMap;

public class Colours
{
    static HashMap<String,Color> colours;

    public Colours getInstance()
    {
        return this;
    }

    static Color getColor(String name)
    {
        return colours.get(name);
    }

    /**
     * Code to initialise list of colours
     */
    static public void setColours()
    {
        colours.put("Orange", Color.argb(255,255,165,0));
        colours.put()
        colours.put()
        colours.put()
        colours.put()
        colours.put()
    }
}

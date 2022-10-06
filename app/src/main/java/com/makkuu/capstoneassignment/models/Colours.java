package com.makkuu.capstoneassignment.models;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public class Colours
{
    static Map<String,Color> colours;

    public Colours getInstance()
    {
        return this;
    }

    static Color getColor(String name)
    {
        return colours.get(name);
    }


}

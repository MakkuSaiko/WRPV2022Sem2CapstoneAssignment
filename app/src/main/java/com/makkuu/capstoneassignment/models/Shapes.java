package com.makkuu.capstoneassignment.models;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.makkuu.capstoneassignment.R;

import java.util.HashMap;
import java.util.Map;

public class Shapes
{

    static Map<Tile.Shape, Drawable> shapes;
    static boolean initialized = false;
    static Context context;

    private Shapes()
    {

    }

    public static Shapes getInstance(Context context)
    {
        Shapes shapes = new Shapes();
        Shapes.context = context;
        initialize();
        return shapes;
    }

    private static void initialize()
    {
        if(!initialized){
            shapes = new HashMap<>();
            initialized = true;
            populateColours(context);
        }


    }

    public static void populateColours(Context context)
    {
        shapes.put(Tile.Shape.CIRCLE,context.getDrawable(R.drawable.ic_circle));
        shapes.put(Tile.Shape.CLUB,context.getDrawable(R.drawable.ic_club));
        shapes.put(Tile.Shape.CROSS,context.getDrawable(R.drawable.ic_cross));
        shapes.put(Tile.Shape.DIAMOND,context.getDrawable(R.drawable.ic_diamond));
        shapes.put(Tile.Shape.SQUARE,context.getDrawable(R.drawable.ic_square));
        shapes.put(Tile.Shape.STAR,context.getDrawable(R.drawable.ic_star));


    }

    public static Drawable getShape(Tile.Shape name)
    {
        initialize();
        return shapes.get(name);
    }



}

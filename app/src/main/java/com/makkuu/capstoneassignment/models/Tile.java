package com.makkuu.capstoneassignment.models;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.widget.AppCompatButton;

import com.makkuu.capstoneassignment.R;

public class Tile extends androidx.appcompat.widget.AppCompatImageButton
{
    Image tile;
    Colour colour;
    Shape shape;

    public enum Colour { PURPLE, BLUE, GREEN, YELLOW, ORANGE, RED}
    public enum Shape {club, star, square, diamond, cross, circle}
    public enum Direction {ROW,COLUMN}

    //TODO: Implement Tile view

    public Tile(Context context, Tile.Colour colour, Tile.Shape shape) {
        super(context);
        setImage(colour,shape);

    }
    public void setImage(Tile.Colour colour, Tile.Shape shape)
    {
        this.colour = colour;
        this.shape = shape;
        setShape();
        setColour();


    }

    private void setShape()
    {
        String resourceName = "R.drawable.ic_" + shape.toString();
        int resourceID = getResources().getIdentifier(resourceName,"drawable",getContext().getPackageName());
        setImageDrawable(getResources().getDrawable(resourceID,null));

    }

    private void setColour()
    {
        String resourceName = "R.color." + colour.toString();
        int resourceID = getResources().getIdentifier(resourceName,"color", getContext().getPackageName());
        setColorFilter(getResources().getColor(resourceID,null));
//        setBackground(getDrawable());
    }

    public boolean colourEquals(Tile tile)
    {
        return this.colour.equals(tile.colour);
    }

    public boolean shapeEquals(Tile tile)
    {
        return this.shape.equals(tile.shape);
    }
}

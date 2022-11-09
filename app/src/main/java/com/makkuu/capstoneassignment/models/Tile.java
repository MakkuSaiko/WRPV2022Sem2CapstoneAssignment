package com.makkuu.capstoneassignment.models;

import android.app.ActionBar;
import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;

import com.makkuu.capstoneassignment.R;

public class Tile extends androidx.appcompat.widget.AppCompatImageButton
{
    Image tile;
    Colour colour;
    Shape shape;

    public enum Colour { PURPLE, BLUE, GREEN, YELLOW, ORANGE, RED}
    public enum Shape {CLUB, STAR, SQUARE, DIAMOND, CROSS, CIRCLE}
    public enum Direction {ROW,COLUMN}

    //TODO: Implement Tile view

    public Tile(Context context, Tile.Colour colour, Tile.Shape shape) {
        super(context);
        setImage(colour,shape);
        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
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

        setImageDrawable(Shapes.getShape(shape));

    }

    private void setColour()
    {
        //TODO use Colour class
        setColorFilter(Colours.getColor(colour));
//        String resourceName = "R.color." + colour.toString();
//        int resourceID = getResources().getIdentifier(resourceName,"color", getContext().getPackageName());
//        setColorFilter(getResources().getColor(resourceID,null));
        setBackground(getDrawable());
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

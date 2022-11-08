package com.makkuu.capstoneassignment.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bag {
    List<Tile> bag;
    Context context;

    public Bag(Context context) {
        this.context = context;
        bag = new ArrayList<>();
        //fill the bag with tiles and shuffle it
        populateBag();
        for (int i = 0; i < 10; i++) {
            shuffle();
        }


}

    private void populateBag()
    {
        if(bag.size()>0) bag.clear();
        Tile.Shape[] shapes = Tile.Shape.values();
        Tile.Colour[] colours = Tile.Colour.values();
        for (Tile.Shape shape:shapes) {
            for (Tile.Colour color: colours) {
                bag.add(new Tile(context,color,shape));
                bag.add(new Tile(context,color,shape));
                bag.add(new Tile(context,color,shape));
            }
        }
    }
    public Tile getNextTile()
    {
        return bag.remove(0);
    }

    public void shuffle()
    {
        Collections.shuffle(bag);
    }

    /**
     * Return tiles to the bag (generally after trading tiles)
     * @param tiles to be returned
     */
    public void returnTiles(List<Tile> tiles)
    {
        bag.addAll(tiles);
        shuffle();
    }

}

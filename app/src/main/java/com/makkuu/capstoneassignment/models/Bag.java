package com.makkuu.capstoneassignment.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bag {
    List<Tile> bag;

    public Bag() {
        bag = new ArrayList<>();
    }

    private void populateBag()
    {

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

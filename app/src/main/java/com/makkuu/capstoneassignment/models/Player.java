package com.makkuu.capstoneassignment.models;

import java.util.ArrayList;
import java.util.List;

public class Player {
    String name;
    int score;
    List<Tile> tiles;

    public Player(String name) {
        this.name = name;
        score = 0;
        tiles = new ArrayList<>();
    }

    public void incScore(int addAmt)
    {
        score+=addAmt;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) { this.tiles = tiles; }
}

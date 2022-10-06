package com.makkuu.capstoneassignment.models;

public class Player {
    String name;
    int score;
    Tile[] tiles;

    public Player(String name) {
        this.name = name;
        score = 0;
        tiles = new Tile[6];
    }

    public void incScore(int addAmt)
    {
        score+=addAmt;
    }

    public Tile[] getTiles() {
        return tiles;
    }
}

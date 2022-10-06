package com.makkuu.capstoneassignment.models;

import java.util.HashMap;
import java.util.Map;

public class Board {

    Map<Coordinate,Tile> board;

    public Board() {
        this.board = new HashMap<>();
    }

    public static class Coordinate
    {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public void putTile(Coordinate coord, Tile tile)
    {
        board.put(coord,tile);
    }
}

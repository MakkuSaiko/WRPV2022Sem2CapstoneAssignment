package com.makkuu.capstoneassignment.models;

import androidx.annotation.Nullable;

import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Board {

    Map<Coordinate,Tile> board;
    Deque<Coordinate> inputs;       //List of positions where player is currently placing tiles (uncommitted)

    public Board() {
        this.board = new HashMap<>();
        inputs = new ArrayDeque<>();
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public void putTile(Coordinate coord, Tile tile)
    {
        board.put(coord,tile);
    }

    public List<Map.Entry<Coordinate,Tile>> getConnectedTiles(int row, int col, Tile.Direction direction)
    {
        List<Map.Entry<Coordinate,Tile>> output = null;
        if(direction == Tile.Direction.ROW)
        {
           output = board.entrySet().stream()
                    .filter(entry -> entry.getKey().x==row)     //Filter to get the row of tiles
                    .filter(entry -> entry.getKey().y<=col+6 && entry.getKey().y>=col-6) //only get tiles up to 6 away (max size of a line)
                    .collect(Collectors.toList());
        }

        if(direction == Tile.Direction.COLUMN)
        {
            output = board.entrySet().stream()
                    .filter(entry-> entry.getKey().y==col)  //filter to get column of tiles
                    .filter(entry -> entry.getKey().x <=col+6 &&entry.getKey().y>=col-6)    //Only get tiles up to 6 away
                    .collect(Collectors.toList());
        }
        return output;
    }



    /**
     *
     * @param coord of tile to be placed
     * @param tile tile placed
     * @return True if valid move
     */
    public boolean doMove(Coordinate coord, Tile tile)
    {


        if(board.containsKey(coord)) return false; //cannot put a tile where another tile exists
        boolean placeable = true;

        //check if board allows player to place there
        Tile right =  board.getOrDefault(new Coordinate(coord.x+1, coord.y),null);

        if(right !=null)
        {
            placeable = tile.colourEquals(right) || tile.shapeEquals(right);
        }

        Tile left = board.getOrDefault(new Coordinate(coord.x-1, coord.y),null);
        if(left!=null)
        {
            placeable = tile.colourEquals(left) || tile.shapeEquals(left);
        }

        Tile up = board.getOrDefault(new Coordinate(coord.x, coord.y-1),null);
        if(up!=null)
        {
            placeable = tile.colourEquals(up) || tile.shapeEquals(up);
        }

        Tile down = board.getOrDefault(new Coordinate(coord.x, coord.y+1),null);
        if(down!=null)
        {
            placeable = tile.colourEquals(down) || tile.shapeEquals(down);
        }

        if(placeable)
        {
            //create a new tile entry to store placing tiles
            board.put(coord, tile);
            inputs.add(coord);

        }

        return placeable;
    }

}

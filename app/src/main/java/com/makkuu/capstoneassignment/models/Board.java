package com.makkuu.capstoneassignment.models;

import androidx.annotation.Nullable;

import com.makkuu.capstoneassignment.Property;

import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
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

    public List<Map.Entry<Coordinate,Tile>> getConnectedTiles (int col, int row, Tile.Direction direction)
    {
        List<Map.Entry<Coordinate,Tile>> output = new ArrayList<>();

        if(direction == Tile.Direction.ROW)
        { int loopVar = col + 1;
           Coordinate curCoord = new Coordinate(loopVar,row);
           Tile curTile = board.getOrDefault(curCoord,null);
           while(curTile!=null)
           {
               output.add(new AbstractMap.SimpleEntry<>(curCoord, curTile));
               loopVar += 1;
               curCoord = new Coordinate(loopVar,row);
               curTile=board.getOrDefault(curCoord,null);
           }

           loopVar = col-1;
           curCoord = new Coordinate(loopVar,row);
           curTile = board.getOrDefault(curCoord,null);
           while(curTile!=null)
           {
               output.add(new AbstractMap.SimpleEntry<>(curCoord, curTile));
               loopVar -= 1;
               curCoord = new Coordinate(loopVar,row);
               curTile=board.getOrDefault(curCoord,null);
           }
        }

        if(direction == Tile.Direction.COLUMN)
        {
            int loopVar = row + 1;
            Coordinate curCoord = new Coordinate(col,loopVar);
            Tile curTile = board.getOrDefault(curCoord,null);
            while(curTile!=null)
            {
                output.add(new AbstractMap.SimpleEntry<>(curCoord, curTile));
                loopVar += 1;
                curCoord = new Coordinate(col,loopVar);
                curTile=board.getOrDefault(curCoord,null);
            }

            loopVar = row - 1;
            curCoord = new Coordinate(col,loopVar);
            curTile = board.getOrDefault(curCoord,null);
            while(curTile!=null)
            {
                output.add(new AbstractMap.SimpleEntry<>(curCoord, curTile));
                loopVar -= 1;
                curCoord = new Coordinate(col,loopVar);
                curTile=board.getOrDefault(curCoord,null);
            }
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

        //Check if row is full
        List<Map.Entry<Coordinate, Tile>> row = getConnectedTiles(coord.x, coord.y, Tile.Direction.ROW);

        placeable = row.size()>=6;

        //check if col is full
        List<Map.Entry<Coordinate, Tile>> col = getConnectedTiles(coord.x, coord.y, Tile.Direction.COLUMN);

        placeable = col.size()>=6;

        //check if each row and col is a shape line or a colour line
        if(row.get(0).getValue().shapeEquals(row.get(1).getValue()))
        {
            placeable = tile.shapeEquals(row.get(0).getValue());
            //check if colour is already in row
            placeable = !row.stream().map(entry -> entry.getValue().colour).collect(Collectors.toList()).contains(tile.colour);
        }
        else
        {
            placeable = tile.colourEquals(row.get(0).getValue());
            //check if shape is in the row
            placeable = !row.stream().map(entry -> entry.getValue().shape).collect(Collectors.toList()).contains(tile.shape);
        }

        if(col.get(0).getValue().shape.equals(col.get(1).getValue().shape))
        {
            placeable = tile.shapeEquals(col.get(0).getValue());
            //check if colour is already in row
            placeable = !col.stream().map(entry -> entry.getValue().colour).collect(Collectors.toList()).contains(tile.colour);
        }
        else
        {
            placeable = tile.colourEquals(col.get(0).getValue());
            //check if shape is in the row
            placeable = !col.stream().map(entry -> entry.getValue().shape).collect(Collectors.toList()).contains(tile.shape);
        }

        if(placeable)
        {
            //create a new tile entry to store placing tiles
            board.put(coord, tile);
            inputs.add(coord);

        }

        return placeable;
    }

    public int commit()
    {
        int score = getScore();
        inputs.clear();
        return score;
    }

    public void revertOne()
    {
        Coordinate coord = inputs.removeLast();
        board.replace(coord,null);
    }

    public void revertAll()
    {
        while(inputs.size()>0)
        {
            revertOne();
        }
    }

    private int getScore() {
        int score = 0;
        Coordinate coord = inputs.peekLast();
        List<Map.Entry<Coordinate, Tile>> columnConnected = getConnectedTiles(coord.x, coord.y, Tile.Direction.COLUMN);
        score += columnConnected.size() +1;
        if(columnConnected.size()==5) score+=6;//Col qwirkle
        for (Coordinate coordinate:
             inputs) {
            List<Map.Entry<Coordinate, Tile>> rowConnected = getConnectedTiles(coordinate.x, coordinate.y, Tile.Direction.ROW);
            score+=rowConnected.size()+1;
            if(rowConnected.size()==5) score+=6;
        }
        return score;
    }
}

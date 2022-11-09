package com.makkuu.capstoneassignment.controllers;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.caverock.androidsvg.SVG;
import com.makkuu.capstoneassignment.Property;
import com.makkuu.capstoneassignment.models.Bag;
import com.makkuu.capstoneassignment.models.Board;
import com.makkuu.capstoneassignment.models.Colours;
import com.makkuu.capstoneassignment.models.Player;
import com.makkuu.capstoneassignment.R;
import com.makkuu.capstoneassignment.models.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Game extends AppCompatActivity {


    Property<Player> curPlayer;
    List<Player> players;
    Property<Integer> playerNr;
    Property<Integer> turnNr;
    Bag bag;
    Board board;
    AlertDialog exitConfirm;
    int startingTiles;

    List<Tile> selectedTiles = new ArrayList<>();

    //ui elements
    LinearLayout tiles;

    //integer for keeping track of edge of board for adjusting UI


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tiles=findViewById(R.id.tileArea);
        //Set up the initial colours
        Colours colours = Colours.getInstance(this);

        players = new ArrayList<>();
        Intent intent = getIntent();
        if(intent!=null)
        {
            String[] names = intent.getStringArrayExtra("players");
            for (String name:
                 names) {
                if(name!=null)
                players.add(new Player(name));
            }
            players.forEach(player -> player.setTiles(bag.getTiles(startingTiles)));
            //Shuffle players order
            Collections.shuffle(players);
        }

        setUpGame();
    }


    public void setUpGame() {
        //Initialize attributes
        curPlayer = new Property<>(players.get(0));

        bag = new Bag(this);
        board = new Board();

        setPropertyListeners();
        initializeExitMessage();
        setOnClickListeners();

    }

    /**
     * Sets up property listeners (Properties from last semester assignment)
     */
    private void setPropertyListeners()
    {
        playerNr.addListener((property, oldValue, newValue) -> {
            if(newValue >= players.size()) {playerNr.reset(0); return;}
            curPlayer.set(players.get(newValue));
            //TODO:ui changes
        });
        //TODO:ui changes
        turnNr.addListener((property, oldValue, newValue) -> playerNr.set(playerNr.get()+1));

        curPlayer.addListener((property, oldValue, newValue) -> {
            selectedTiles.clear();
            tiles.removeAllViews();
            for (Tile tile:
                 newValue.getTiles()) {
                tiles.addView(tile);
                tile.setOnClickListener(view -> selectTile(tile));
            }
        });
    }


    public void move_endTurn()
    {
        turnNr.set(turnNr.get()+1);
        board.commit();
    }

    public void move_tradeTiles(List<Tile> removedTiles)
    {
        //TODO: Get selected tiles

        List<Tile> removed = new ArrayList<Tile>();
        List<Tile> heldTiles = curPlayer.get().getTiles();

        heldTiles.removeAll(removedTiles);
        removed.addAll(removedTiles);
        while(heldTiles.size()<6)
        {
            try {
                heldTiles.add(bag.getNextTile());
            } catch(Exception e) {break; }  //if there is not enough tiles, break loop
        }



        bag.returnTiles(removed);
        board.revertAll();//do not set any moves as valid when trading tiles
        move_endTurn();

    }
    public void move_undo()
    {
        board.revertOne();
    }

    //TODO: get from arraylist
    public void move_placeTile(Board.Coordinate coordinate, Tile tile)
    {
        boolean validity = board.doMove(coordinate,tile);
        if(validity) Toast.makeText(this,getString(R.string.invalidMove),Toast.LENGTH_SHORT).show();
    }



    public void initializeExitMessage()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        exitConfirm  = builder.create();
        exitConfirm.setMessage(getString(R.string.quitConfirm));
        exitConfirm.setButton(AlertDialog.BUTTON_POSITIVE,getString(R.string.yes),(a,b) -> super.onBackPressed());
        exitConfirm.setButton(AlertDialog.BUTTON_NEGATIVE,getString(R.string.no), (a,b) -> a.dismiss());
    }

    @Override
    public void onBackPressed() {
        exitConfirm.show();
    }



    private void selectTile(Tile tile)
    {
        if(selectedTiles.contains(tile)) {
            selectedTiles.remove(tile);
            tile.setBackgroundColor(getColor(R.color.design_default_color_background));
        }
        else {
            selectedTiles.add(tile);
            tile.setBackgroundColor(getColor(R.color.selected));
        }
    }

    private void setOnClickListeners()
    {
        findViewById(R.id.btnRevert).setOnClickListener(view -> move_undo());
        findViewById(R.id.btnCommit).setOnClickListener(view -> { board.commit(); move_endTurn(); });
        findViewById(R.id.btnTrade).setOnClickListener(view -> move_tradeTiles(selectedTiles));

    }

}
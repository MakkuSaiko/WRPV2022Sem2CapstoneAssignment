package com.makkuu.capstoneassignment.controllers;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.makkuu.capstoneassignment.Property;
import com.makkuu.capstoneassignment.models.Bag;
import com.makkuu.capstoneassignment.models.Board;
import com.makkuu.capstoneassignment.models.Player;
import com.makkuu.capstoneassignment.R;
import com.makkuu.capstoneassignment.models.Tile;

import java.util.ArrayList;
import java.util.Arrays;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        players = new ArrayList<>();
        Intent intent = getIntent();
        if(intent!=null)
        {
            String[] names = intent.getStringArrayExtra("players");
            for (String name:
                 names) {
                players.add(new Player(name));
            }
            //Shuffle players order
            Collections.shuffle(players);
        }

        setUpGame();
    }


    public void setUpGame() {
        //Initialize attributes
        curPlayer.reset(players.get(0));

        bag = new Bag();
        board = new Board();

        setPropertyListeners();
        initializeExitMessage();

    }

    /**
     * Sets up property listeners (Properties from last semester assignment)
     */
    private void setPropertyListeners()
    {
        playerNr.addListener((property, oldValue, newValue) -> {
            if(newValue >= players.size()) {playerNr.set(0); return;}
            curPlayer.set(players.get(newValue));
            //TODO:ui changes
        });
        //TODO:ui changes
        turnNr.addListener((property, oldValue, newValue) -> playerNr.set(playerNr.get()+1));
    }


    public void move_endTurn()
    {
        turnNr.set(turnNr.get()+1);
    }

    public void move_tradeTiles(int[] removedIndex)
    {
        //TODO: Get selected tiles

        List<Tile> removed = new ArrayList<Tile>();
        Tile[] heldTiles = curPlayer.get().getTiles();
        for (int index:
             removedIndex) {
            removed.add(heldTiles[index]);
            heldTiles[index] = bag.getNextTile();
        }
        bag.returnTiles(removed);
        move_endTurn();

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
}
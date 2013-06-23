/**
 * This file is part of Thric3.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright 2013 Kai Körber
 */
package de.rebreok.thric3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class GameActivity extends Activity
{
    private final static String PLAYER_NAME = "player_name";
    private final static String PLAYER_SCORE = "player_score";
    private final static String PLAYER_ICON = "player_icon";
    
    private Deck deck;
    private CardGrid grid;
    private ArrayList<Player> players;
    private Player activePlayer;
    private boolean tutorial;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Resources res = getResources();
        players = new ArrayList<Player>();
        activePlayer = null;
        switch (getIntent().getIntExtra(MainActivity.GAME_MODE, -2))
        {
            case MainActivity.MODE_TUTORIAL:
                setContentView(R.layout.game1);
                players.add(new Player(res.getString(R.string.name_player1)));
                tutorial = true;
                break;
            case MainActivity.MODE_1P:
                setContentView(R.layout.game1);
                players.add(new Player(res.getString(R.string.name_player1)));
                break;
            case MainActivity.MODE_2P:
                setContentView(R.layout.game2);
                players.add(new Player(res.getString(R.string.name_player1)));
                players.add(new Player(res.getString(R.string.name_player2)));
                break;
            case MainActivity.MODE_3P:
                setContentView(R.layout.game3);
                players.add(new Player(res.getString(R.string.name_player1)));
                players.add(new Player(res.getString(R.string.name_player2)));
                players.add(new Player(res.getString(R.string.name_player3)));
                break;
        }
        
        deck = new Deck();
        grid = new CardGrid(this);
        LinearLayout gridContainer = (LinearLayout) findViewById(R.id.grid_container);
        gridContainer.addView(grid, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT, 0));
        updateUI();
    }
    
    public void onCardClick(CardView cardView) {
        if (activePlayer == null) {
            Toast.makeText(this, R.string.toast_call_first, 1).show();
        } else {
            grid.toggleSelection(cardView);
        }
    }
    
    private void updateUI() {
        TextView cardsLeft = (TextView) findViewById(R.id.text_cards_left);
        cardsLeft.setText(String.valueOf(deck.size()));
        TextView playerScore = (TextView) findViewById(R.id.text_score_player1);
        playerScore.setText(String.valueOf(players.get(0).getScore()));
        if (players.size() > 1) {
            playerScore = (TextView) findViewById(R.id.text_score_player2);
            playerScore.setText(String.valueOf(players.get(1).getScore()));
        }
        if (players.size() > 2) {
            playerScore = (TextView) findViewById(R.id.text_score_player3);
            playerScore.setText(String.valueOf(players.get(2).getScore()));
        }
        if (players.size() > 3) {
            playerScore = (TextView) findViewById(R.id.text_score_player4);
            playerScore.setText(String.valueOf(players.get(3).getScore()));
        }
    }
    
    public void onPlayerButton(View view) {
        ToggleButton button = (ToggleButton) view;
        Player player;
        if (view == findViewById(R.id.button_player1)) {
            player = players.get(0);
        } else if (view == findViewById(R.id.button_player2)) {
            player = players.get(1);
        } else if (view == findViewById(R.id.button_player3)) {
            player = players.get(2);
        } else {
            player = players.get(3);
        }
        if (player.isLocked()) { // Locked player tries to call SET:
            button.setChecked(false);
            Toast.makeText(this, R.string.toast_locked, 1).show();
        } else if (grid.getCount() == 0) { // Player tries to call SET with no cards on the table:
            button.setChecked(false);
        } else if (activePlayer == null) { // Player calls SET:
            callSet(player);
        } else if (activePlayer != player) { // Wrong player tries to confirm SET:
            Toast.makeText(this, R.string.toast_not_your_turn, 1).show();
            button.setChecked(false);
        } else if (grid.getSelectedCards().size() != 3) { // Player tries to confirm SET with wrong number of cards:
            Toast.makeText(this, R.string.toast_consists_of_three_cards, 1).show();
            button.setChecked(true);
        } else { // Player confirms SET:
            confirmSet(player);
        }
    }
    
    private void callSet(Player player) {
        activePlayer = player;
        grid.setAcceptSelection(true);
        if (tutorial)
            Toast.makeText(this, R.string.toast_select, 1).show();
    }
    
    private void confirmSet(Player player) {
        if (tutorial)
            onTutorialConfirmSet(grid.getSelectedCards().isValidSet());
        for (Player anyPlayer: players)
            anyPlayer.unlock();
        if (grid.getSelectedCards().isValidSet()) {
            Pile set = grid.getSelectedCards();
            for (Card card: set) {
                grid.remove(card);
                player.addCard(card);
            }
            if (!tutorial)
                Toast.makeText(this, R.string.toast_valid, 1).show();
        } else {
            if (players.size() > 1) {
                player.lock();
                if (!tutorial)
                    Toast.makeText(this, R.string.toast_no_valid_locked, 1).show();
            } else {
                if (!tutorial)
                    Toast.makeText(this, R.string.toast_no_valid, 1).show();
            }
        }
        grid.clearSelection();
        activePlayer = null;
        grid.setAcceptSelection(false);
        dealCards(false);
        updateUI();
    }
    
    public void onButtonDealCards(View view) {
        dealCards(true);
    }
    
    private void dealCards(boolean force) {
        int cards_in_game = grid.getCount();
        if (force || cards_in_game < 12) {
            if (activePlayer == null) {
                int nr_of_cards_to_deal = 0;
                if (cards_in_game < 12) {
                    nr_of_cards_to_deal = 12 - cards_in_game;
                } else if (cards_in_game < 21) {
                    nr_of_cards_to_deal = 3;
                }
                if (deck.size() == 0 && force) {
                    Toast.makeText(this, R.string.toast_no_cards_left, 1).show();
                }
                for (int i=0; i < nr_of_cards_to_deal; i++) {
                    if (deck.size() > 0) {
                        grid.add(deck.pop());
                    }
                }
                updateUI();
            } else {
                Toast.makeText(this, R.string.toast_select, 1).show();
            }
        }
        checkForGameOver();
    }
    
    private void checkForGameOver() {
        if (deck.size() == 0 && grid.getCount() < 21 && !grid.getAllCards().containsValidSet()) {
            onGameOver();
        }
    }
    
    private void onTutorialConfirmSet(boolean correct) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        ScrollView body = (ScrollView) inflater.inflate(R.layout.dialog_tutorial_confirm, null);
        builder.setView(body);
        
        TextView comment = (TextView)body.findViewById(R.id.comment);
        if (correct) {
            builder.setTitle(R.string.tutorial_dialog_title_correct);
            comment.setText(R.string.tutorial_explanation_correct);
        } else {
            builder.setTitle(R.string.tutorial_dialog_title_wrong);
            comment.setText(R.string.tutorial_explanation_wrong);
        }
        
            TextView comment_colors = (TextView)body.findViewById(R.id.comment_colors);
            if (grid.getSelectedCards().getColorCount() == 2) {
                comment_colors.setText(R.string.tutorial_not_ok);
                comment_colors.setTextColor(Color.RED);
            } else {
                comment_colors.setText(R.string.tutorial_ok);
                comment_colors.setTextColor(Color.GREEN);
            }
            TextView comment_shapes = (TextView)body.findViewById(R.id.comment_shapes);
            if (grid.getSelectedCards().getShapeCount() == 2) {
                comment_shapes.setText(R.string.tutorial_not_ok);
                comment_shapes.setTextColor(Color.RED);
            } else {
                comment_shapes.setText(R.string.tutorial_ok);
                comment_shapes.setTextColor(Color.GREEN);
            }
            TextView comment_numbers = (TextView)body.findViewById(R.id.comment_numbers);
            if (grid.getSelectedCards().getNumberCount() == 2) {
                comment_numbers.setText(R.string.tutorial_not_ok);
                comment_numbers.setTextColor(Color.RED);
            } else {
                comment_numbers.setText(R.string.tutorial_ok);
                comment_numbers.setTextColor(Color.GREEN);
            }
            TextView comment_fillings = (TextView)body.findViewById(R.id.comment_fillings);
            if (grid.getSelectedCards().getFillingCount() == 2) {
                comment_fillings.setText(R.string.tutorial_not_ok);
                comment_fillings.setTextColor(Color.RED);
            } else {
                comment_fillings.setText(R.string.tutorial_ok);
                comment_fillings.setTextColor(Color.GREEN);
            }
            
            //~ hbox_fillings.addView(label_fillings, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            //~ hbox_fillings.addView(comment_fillings, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            //~ vbox.addView(hbox_fillings);
            //~ 
        //~ }
        builder.setPositiveButton(R.string.ok, null);
        builder.create().show();
    }
    
    private void onGameOver() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_game_over_title);
        builder.setCancelable(false);
        
        ListView listView = new ListView(this);
        List<HashMap<String, String> > listData = new ArrayList<HashMap<String, String> >();
        ArrayList<Player> sortedPlayers = new ArrayList<Player>(players);
        Collections.sort(sortedPlayers);
        Collections.reverse(sortedPlayers);
        for (Player player: sortedPlayers)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(PLAYER_NAME, player.getName());
            map.put(PLAYER_SCORE, String.valueOf(player.getScore()));
            switch (players.indexOf(player)) {
                case 0:
                    map.put(PLAYER_ICON, String.valueOf(R.drawable.button_player1_normal));
                    break;
                case 1:
                    map.put(PLAYER_ICON, String.valueOf(R.drawable.button_player2_normal));
                    break;
                case 2:
                    map.put(PLAYER_ICON, String.valueOf(R.drawable.button_player3_normal));
                    break;
            }
            listData.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, listData, R.layout.final_score_entry, new String[] {PLAYER_NAME, PLAYER_SCORE, PLAYER_ICON}, new int[] {R.id.player_name, R.id.player_score, R.id.player_icon});
        listView.setAdapter(adapter);
        builder.setView(listView);
        
        //~ builder.setPositiveButton(R.string.button_replay, new DialogInterface.OnClickListener() {
                   //~ public void onClick(DialogInterface dialog, int id) {
                       //~ finish();
                   //~ }
               //~ });
        builder.setNegativeButton(R.string.button_back_to_menu, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       finish();
                   }
               });
        builder.create().show();
    }
}

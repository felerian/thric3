package de.rebreok.thric3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class GameActivity extends Activity
{
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
            case MainActivity.MODE_4P:
                setContentView(R.layout.game4);
                players.add(new Player(res.getString(R.string.name_player1)));
                players.add(new Player(res.getString(R.string.name_player2)));
                players.add(new Player(res.getString(R.string.name_player3)));
                players.add(new Player(res.getString(R.string.name_player4)));
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
            Toast.makeText(this, R.string.toast_call_set_first, 2).show();
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
    
    public void onButtonSet(View view) {
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
            Toast.makeText(this, R.string.toast_locked, 2).show();
        } else if (grid.getCount() == 0) { // Player tries to call SET with no cards on the table:
            button.setChecked(false);
        } else if (activePlayer == null) { // Player calls SET:
            callSet(player);
        } else if (activePlayer != player) { // Wrong player tries to confirm SET:
            Toast.makeText(this, R.string.toast_not_your_turn, 2).show();
            button.setChecked(false);
        } else if (grid.getSelectedCards().size() != 3) { // Player tries to confirm SET with wrong number of cards:
            Toast.makeText(this, R.string.toast_set_consists_of_three_cards, 2).show();
            button.setChecked(true);
        } else { // Player confirms SET:
            confirmSet(player);
        }
    }
    
    private void callSet(Player player) {
        activePlayer = player;
        grid.setAcceptSelection(true);
        if (tutorial)
            Toast.makeText(this, R.string.toast_select_your_set, 2).show();
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
                Toast.makeText(this, R.string.toast_valid_set, 2).show();
        } else {
            if (players.size() > 1) {
                player.lock();
                if (!tutorial)
                    Toast.makeText(this, R.string.toast_no_valid_set_locked, 2).show();
            } else {
                if (!tutorial)
                    Toast.makeText(this, R.string.toast_no_valid_set, 2).show();
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
                if (deck.size() == 0) {
                    Toast.makeText(this, R.string.toast_no_cards_left, 2).show();
                }
                for (int i=0; i < nr_of_cards_to_deal; i++) {
                    if (deck.size() > 0) {
                        grid.add(deck.pop());
                    }
                }
                updateUI();
            } else {
                Toast.makeText(this, R.string.toast_select_your_set, 2).show();
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
        if (correct) {
            builder.setTitle(R.string.tutorial_dialog_title_set_correct);
        } else {
            builder.setTitle(R.string.tutorial_dialog_title_set_wrong);
        }
        LinearLayout vbox = new LinearLayout(this);
        vbox.setPadding(20, 20, 20, 20);
        vbox.setOrientation(LinearLayout.VERTICAL);
        builder.setView(vbox);
        LinearLayout hbox = new LinearLayout(this);
        hbox.setOrientation(LinearLayout.HORIZONTAL);
        hbox.setGravity(Gravity.CENTER);
        for (Card card: grid.getSelectedCards()) {
            hbox.addView(new CardView(this, card, false), new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 200, 0));
        }
        vbox.addView(hbox, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        TextView comment = new TextView(this);
        comment.setPadding(20, 20, 20, 20);
        vbox.addView(comment);
        if (correct) {
            builder.setTitle(R.string.tutorial_dialog_title_set_correct);
            comment.setText(R.string.tutorial_explanation_set_correct);
        } else {
            builder.setTitle(R.string.tutorial_dialog_title_set_wrong);
            comment.setText(R.string.tutorial_explanation_set_wrong);
            
            LinearLayout hbox_colors = new LinearLayout(this);
            TextView label_colors = new TextView(this);
            label_colors.setGravity(Gravity.RIGHT);
            label_colors.setPadding(0, 0, 10, 0);
            label_colors.setText(R.string.tutorial_colors);
            TextView comment_colors = new TextView(this);
            if (grid.getSelectedCards().getColorCount() == 2) {
                comment_colors.setText(R.string.tutorial_not_ok);
                comment_colors.setTextColor(Color.RED);
            } else {
                comment_colors.setText(R.string.tutorial_ok);
                comment_colors.setTextColor(Color.GREEN);
            }
            hbox_colors.addView(label_colors, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            hbox_colors.addView(comment_colors, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            vbox.addView(hbox_colors);
            
            LinearLayout hbox_shapes = new LinearLayout(this);
            TextView label_shapes = new TextView(this);
            label_shapes.setGravity(Gravity.RIGHT);
            label_shapes.setPadding(0, 0, 10, 0);
            label_shapes.setText(R.string.tutorial_shapes);
            TextView comment_shapes = new TextView(this);
            if (grid.getSelectedCards().getShapeCount() == 2) {
                comment_shapes.setText(R.string.tutorial_not_ok);
                comment_shapes.setTextColor(Color.RED);
            } else {
                comment_shapes.setText(R.string.tutorial_ok);
                comment_shapes.setTextColor(Color.GREEN);
            }
            hbox_shapes.addView(label_shapes, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            hbox_shapes.addView(comment_shapes, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            vbox.addView(hbox_shapes);
            
            LinearLayout hbox_numbers = new LinearLayout(this);
            TextView label_numbers = new TextView(this);
            label_numbers.setGravity(Gravity.RIGHT);
            label_numbers.setPadding(0, 0, 10, 0);
            label_numbers.setText(R.string.tutorial_numbers);
            TextView comment_numbers = new TextView(this);
            if (grid.getSelectedCards().getNumberCount() == 2) {
                comment_numbers.setText(R.string.tutorial_not_ok);
                comment_numbers.setTextColor(Color.RED);
            } else {
                comment_numbers.setText(R.string.tutorial_ok);
                comment_numbers.setTextColor(Color.GREEN);
            }
            hbox_numbers.addView(label_numbers, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            hbox_numbers.addView(comment_numbers, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            vbox.addView(hbox_numbers);
            
            LinearLayout hbox_fillings = new LinearLayout(this);
            TextView label_fillings = new TextView(this);
            label_fillings.setGravity(Gravity.RIGHT);
            label_fillings.setPadding(0, 0, 10, 0);
            label_fillings.setText(R.string.tutorial_fillings);
            TextView comment_fillings = new TextView(this);
            if (grid.getSelectedCards().getFillingCount() == 2) {
                comment_fillings.setText(R.string.tutorial_not_ok);
                comment_fillings.setTextColor(Color.RED);
            } else {
                comment_fillings.setText(R.string.tutorial_ok);
                comment_fillings.setTextColor(Color.GREEN);
            }
            hbox_fillings.addView(label_fillings, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            hbox_fillings.addView(comment_fillings, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            vbox.addView(hbox_fillings);
            
        }
        builder.setPositiveButton(R.string.ok, null);
        builder.create().show();
    }
    
    private void onGameOver() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_game_over_title);
        builder.setCancelable(false);
        
        LinearLayout vbox = new LinearLayout(this);
        vbox.setPadding(20, 20, 20, 20);
        vbox.setOrientation(LinearLayout.VERTICAL);
        builder.setView(vbox);
        
        TextView message = new TextView(this);
        message.setPadding(20, 20, 20, 20);
        message.setText(R.string.dialog_game_over_text);
        vbox.addView(message);
        
        for (Player player: players) {
            LinearLayout hbox = new LinearLayout(this);
            hbox.setOrientation(LinearLayout.HORIZONTAL);
            hbox.setGravity(Gravity.CENTER_VERTICAL);
            vbox.addView(hbox, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT, 0));
            
            ImageView icon_left = new ImageView(this);
            ImageView icon_right = new ImageView(this);
            switch (players.indexOf(player)) {
                case 0:
                    icon_left.setImageResource(R.drawable.button_player1_normal);
                    icon_right.setImageResource(R.drawable.button_player1_normal);
                    break;
                case 1:
                    icon_left.setImageResource(R.drawable.button_player2_normal);
                    icon_right.setImageResource(R.drawable.button_player2_normal);
                    break;
                case 2:
                    icon_left.setImageResource(R.drawable.button_player3_normal);
                    icon_right.setImageResource(R.drawable.button_player3_normal);
                    break;
                case 3:
                    icon_left.setImageResource(R.drawable.button_player4_normal);
                    icon_right.setImageResource(R.drawable.button_player4_normal);
                    break;
            }
            hbox.addView(icon_left, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 50, 0));
            
            TextView playerName = new TextView(this);
            playerName.setPadding(20, 20, 20, 20);
            playerName.setText(player.getName());
            playerName.setTextSize(20);
            hbox.addView(playerName, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0));
            
            Space space = new Space(this);
            hbox.addView(space, new LinearLayout.LayoutParams(0, 0, 1));
            
            TextView playerScore = new TextView(this);
            playerName.setGravity(Gravity.RIGHT);
            playerScore.setPadding(20, 20, 20, 20);
            playerScore.setText(String.valueOf(player.getScore()));
            playerScore.setTextSize(20);
            hbox.addView(playerScore, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0));
            
            hbox.addView(icon_right, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 50, 0));
        }
        
        builder.setNegativeButton(R.string.button_back_to_menu, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       finish();
                   }
               });
        builder.create().show();
    }
}

package de.rebreok.thric3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.GridView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemClickListener;

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
    
    public void onButtonThric3Player1(View view) {
        Player buttonPlayer = players.get(0);
        if (buttonPlayer.isLocked()) {
            ToggleButton button = (ToggleButton) findViewById(R.id.button_player1);
            button.setChecked(false);
            Toast.makeText(this, R.string.toast_locked, 2).show();
        } else if (grid.getCount() == 0) {
            ToggleButton button = (ToggleButton) findViewById(R.id.button_player1);
            button.setChecked(false);
        } else {
            onButtonThric3(buttonPlayer);
        }
    }
    
    public void onButtonThric3Player2(View view) {
        Player buttonPlayer = players.get(1);
        if (buttonPlayer.isLocked()) {
            ToggleButton button = (ToggleButton) findViewById(R.id.button_player2);
            button.setChecked(false);
            Toast.makeText(this, R.string.toast_locked, 2).show();
        } else if (grid.getCount() == 0) {
            ToggleButton button = (ToggleButton) findViewById(R.id.button_player2);
            button.setChecked(false);
        } else {
            onButtonThric3(buttonPlayer);
        }
    }
    
    public void onButtonThric3Player3(View view) {
        Player buttonPlayer = players.get(2);
        if (buttonPlayer.isLocked()) {
            ToggleButton button = (ToggleButton) findViewById(R.id.button_player3);
            button.setChecked(false);
            Toast.makeText(this, R.string.toast_locked, 2).show();
        } else if (grid.getCount() == 0) {
            ToggleButton button = (ToggleButton) findViewById(R.id.button_player3);
            button.setChecked(false);
        } else {
            onButtonThric3(buttonPlayer);
        }
    }
    
    public void onButtonThric3Player4(View view) {
        Player buttonPlayer = players.get(3);
        if (buttonPlayer.isLocked()) {
            ToggleButton button = (ToggleButton) findViewById(R.id.button_player4);
            button.setChecked(false);
            Toast.makeText(this, R.string.toast_locked, 2).show();
        } else if (grid.getCount() == 0) {
            ToggleButton button = (ToggleButton) findViewById(R.id.button_player4);
            button.setChecked(false);
        } else {
            onButtonThric3(buttonPlayer);
        }
    }
    
    private void onButtonThric3(Player buttonPlayer) {
        if (activePlayer == null) {
            activePlayer = buttonPlayer;
            grid.setAcceptSelection(true);
            if (tutorial)
                Toast.makeText(this, R.string.toast_select_your_set, 2).show();
        } else if (activePlayer == buttonPlayer) {
            if (tutorial)
                onTutorialSetDone(grid.getSelectedCards().isValidSet());
            for (Player player: players) {
                player.unlock();
            }
            if (grid.getSelectedCards().isValidSet()) {
                Pile set = grid.getSelectedCards();
                for (Card card: set) {
                    grid.remove(card);
                    buttonPlayer.addCard(card);
                }
                if (!tutorial)
                    Toast.makeText(this, R.string.toast_valid_set, 2).show();
            } else {
                if (players.size() > 1) {
                    buttonPlayer.lock();
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
        } else {
            Toast.makeText(this, R.string.toast_not_your_turn, 2).show();
        }
    }
    
    private void setActivePlayer(Player player) {
        activePlayer = player;
        grid.setAcceptSelection(player != null);
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
    }
    
    private void onTutorialSetDone(boolean correct) {
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
        }
        builder.setPositiveButton(R.string.ok, null);
        builder.create().show();
    }
    
    private void onGameOver() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title_game_over);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.button_replay, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       finish();
                   }
               });
        builder.setNegativeButton(R.string.button_back_to_menu, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       finish();
                   }
               });
        builder.create().show();
    }
}

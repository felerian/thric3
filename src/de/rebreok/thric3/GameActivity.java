package de.rebreok.thric3;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;

public class GameActivity extends Activity
{
    private Deck deck;
    private CardAdapter grid;
    private ArrayList<Player> players;
    private int activePlayerIndex;
    
    private OnItemClickListener cardClickedHandler = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            if (activePlayerIndex == -1) {
                Toast.makeText(v.getContext(), R.string.toast_call_set_first, 2).show();
            } else {
                grid.toggleSelection(position);
            }
        }
    };
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Resources res = getResources();
        players = new ArrayList<Player>();
        activePlayerIndex = -1;
        switch (getIntent().getIntExtra(MainActivity.GAME_MODE, -1))
        {
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
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        grid = new CardAdapter(this);
        gridView.setAdapter(grid);
        
        gridView.setOnItemClickListener(cardClickedHandler);
        updateUI();
    }
    
    private void updateUI() {
        TextView cardsLeft = (TextView) findViewById(R.id.text_cards_left);
        cardsLeft.setText(String.valueOf(deck.size()));
        TextView playerScore;
        playerScore = (TextView) findViewById(R.id.text_score_player1);
        playerScore.setText(String.valueOf(players.get(0).getScore()));
        if (players.size() > 1) {
            playerScore = (TextView) findViewById(R.id.text_score_player2);
            playerScore.setText(String.valueOf(players.get(1).getScore()));
        }
        if (players.size() > 2) {
            playerScore = (TextView) findViewById(R.id.text_score_player3);
            playerScore.setText(String.valueOf(players.get(2).getScore()));
        }
    }
    
    public void onButtonThric3Player1(View view) {
        onButtonThric3(0);
    }
    
    public void onButtonThric3Player2(View view) {
        onButtonThric3(1);
    }
    
    public void onButtonThric3Player3(View view) {
        onButtonThric3(2);
    }
    
    private void onButtonThric3(int playerIndex) {
        if (players.get(playerIndex).isLocked()) {
            Toast.makeText(this, R.string.toast_locked, 2).show();
        } else {
            if (activePlayerIndex == -1) {
                activePlayerIndex = playerIndex;
                Toast.makeText(this, R.string.toast_select_your_set, 2).show();
            } else if (activePlayerIndex == playerIndex) {
                for (Player player: players) {
                    player.unlock();
                }
                if (grid.getSelectedCards().isValidSet()) {
                    Pile set = grid.getSelectedCards();
                    for (Card card: set) {
                        grid.remove(card);
                        players.get(playerIndex).addCard(card);
                    }
                    Toast.makeText(this, R.string.toast_valid_set, 2).show();
                } else {
                    if (players.size() > 1) {
                        players.get(playerIndex).lock();
                        Toast.makeText(this, R.string.toast_no_valid_set_locked, 2).show();
                    } else {
                        Toast.makeText(this, R.string.toast_no_valid_set, 2).show();
                    }
                        
                }
                grid.clearSelection();
                activePlayerIndex = -1;
                updateUI();
            } else {
                Toast.makeText(this, R.string.toast_not_your_turn, 2).show();
            }
        }
    }
    
    public void onButtonDealCards(View view) {
        if (activePlayerIndex == -1) {
            int nr_of_cards_to_deal = 0;
            int cards_in_game = grid.getCount();
            if (cards_in_game < 12) {
                nr_of_cards_to_deal = 12 - cards_in_game;
            } else if (cards_in_game < 21) {
                nr_of_cards_to_deal = 3;
            }
            for (int i=0; i < nr_of_cards_to_deal; i++) {
                if (deck.size() > 0) {
                    grid.add(deck.pop());
                }
            }
            if (nr_of_cards_to_deal == 0) {
                Toast.makeText(this, R.string.toast_no_cards_left, 2).show();
            }
            updateUI();
        } else {
            Toast.makeText(this, R.string.toast_select_your_set, 2).show();
        }
    }
}

package de.rebreok.thric3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;

public class GameActivity extends Activity
{
    private Deck deck;
    private CardAdapter grid;
    
    private OnItemClickListener cardClickedHandler = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            grid.remove(grid.getItem(position));
        }
    };
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        switch (getIntent().getIntExtra(MainActivity.GAME_MODE, -1))
        {
            case MainActivity.MODE_1P:
                setContentView(R.layout.game1);
                break;
            case MainActivity.MODE_2P:
                setContentView(R.layout.game2);
                break;
            case MainActivity.MODE_3P:
                setContentView(R.layout.game3);
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
        TextView textCardsLeft = (TextView) findViewById(R.id.text_cards_left);
        textCardsLeft.setText(String.valueOf(deck.getSize()));
    }
    
    public void onButtonDealCards(View view) {
        int nr_of_cards_to_deal = 0;
        int cards_in_game = grid.getCount();
        if (cards_in_game < 12) {
            nr_of_cards_to_deal = 12 - cards_in_game;
        } else if (cards_in_game < 21) {
            nr_of_cards_to_deal = 3;
        }
        for (int i=0; i < nr_of_cards_to_deal; i++) {
            if (deck.getSize() > 0) {
                grid.add(deck.drawCard());
            }
        }
        updateUI();
    }
}

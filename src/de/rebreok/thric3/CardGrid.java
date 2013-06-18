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

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.View;

import java.util.ArrayList;


public class CardGrid extends LinearLayout {
    
    private Context context;
    private boolean accept_selection;
    
    private ArrayList<CardView> cardViews;
    
    public CardGrid(Context context) {
        super(context);
        this.context = context;
        this.accept_selection = false;
        this.cardViews = new ArrayList<CardView>();
        
        setOrientation(HORIZONTAL);
        for (int col = 0; col < 7; col++) {
            LinearLayout colLayout = new LinearLayout(context);
            colLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams colParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            addView(colLayout, colParams);
            for (int row = 0; row < 3; row++) {
                CardView cardView = new CardView(context, null);
                cardViews.add(cardView);
                LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
                colLayout.addView(cardView, rowParams);
                cardView.setOnClickListener(new CardView.OnClickListener() {
                            public void onClick(View v) {
                                CardView cardView = (CardView) v;
                                if (cardView.getCard() != null) {
                                    if (accept_selection) {
                                        toggleSelection(v);
                                    } else {
                                        Toast.makeText(v.getContext(), R.string.toast_call_first, 2).show();
                                    }
                                }
                            }
                        });
            }
        }
    }
    
    public void toggleSelection(View view) {
        CardView cardView = (CardView) view;
        if (cardView.getCard() != null) {
            cardView.setSelection(!cardView.isSelected());
        }
    }
    
    public void clearSelection() {
        for (CardView cardView: cardViews) {
            cardView.setSelection(false);
        }
    }
    
    public Pile getSelectedCards() {
        Pile result = new Pile();
        for (CardView cardView: cardViews) {
            if (cardView.isSelected() && cardView.getCard() != null) {
                result.add(cardView.getCard());
            }
        }
        return result;
    }
    
    public Pile getAllCards() {
        Pile result = new Pile();
        for (CardView cardView: cardViews) {
            if (cardView.getCard() != null) {
                result.add(cardView.getCard());
            }
        }
        return result;
    }
    
    public void setAcceptSelection(boolean accept) {
        accept_selection = accept;
    }
    
    /**
     * Add a card to the table
     */
    public void add(Card card) {
        for (CardView cardView: cardViews) {
            if (cardView.getCard() == null) {
                cardView.setCard(card);
                break;
            }
        }
    }
    
    /**
     * Remove a card from the table
     */
    public void remove(Card card) {
        for (CardView cardView: cardViews) {
            if (cardView.getCard() == card) {
                cardView.setCard(null);
                break;
            }
        }
    }
    
    /**
     * Return the number of cards on the table
     */
    public int getCount() {
        int count = 0;
        for (CardView cardView: cardViews) {
            if (cardView.getCard() != null) {
                count++;
            }
        }
        return count;
    }
}

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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.widget.Toast;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class CardGrid extends GridLayout {
    
    private Context context;
    private boolean accept_selection;
    
    public CardGrid(Context context) {
        super(context);
        this.context = context;
        this.accept_selection = false;
        setOrientation(VERTICAL);
        setColumnCount(7);
        setRowCount(3);
        setUseDefaultMargins(false);
    }
    
    public void toggleSelection(View view) {
        CardView cardView = (CardView) view;
        cardView.setSelection(!cardView.isSelected());
    }
    
    public void clearSelection() {
        for (int i=0; i<getChildCount(); i++) {
            CardView cardView = (CardView) getChildAt(i);
            cardView.setSelection(false);
        }
    }
    
    public Pile getSelectedCards() {
        Pile result = new Pile();
        for (int i=0; i<getChildCount(); i++) {
            CardView cardView = (CardView) getChildAt(i);
            if (cardView.isSelected()) {
                result.add(cardView.getCard());
            }
        }
        return result;
    }
    
    public Pile getAllCards() {
        Pile result = new Pile();
        for (int i=0; i<getChildCount(); i++) {
            CardView cardView = (CardView) getChildAt(i);
            result.add(cardView.getCard());
        }
        return result;
    }
    
    public void setAcceptSelection(boolean accept) {
        accept_selection = accept;
    }
    
    public void add(Card card) {
        CardView cardView = new CardView(context, card, false);
        cardView.setOnClickListener(new CardView.OnClickListener() {
                public void onClick(View v) {
                    if (accept_selection) {
                        toggleSelection(v);
                    } else {
                        Toast.makeText(v.getContext(), R.string.toast_call_first, 2).show();
                    }
                }
            });
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = getWidth() / 7;
        params.height = getHeight() / 3;
        addView(cardView, params);
    }
    
    public void remove(Card card) {
        for (int i=0; i<getChildCount(); i++) {
            CardView cardView = (CardView) getChildAt(i);
            if (cardView.getCard() == card) {
                removeView(cardView);
            }
        }
    }
    
    public int getCount() {
        return getChildCount();
    }
}

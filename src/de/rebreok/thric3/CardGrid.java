package de.rebreok.thric3;

import android.content.Context;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class CardGrid extends GridLayout {
    
    private Context context;
    
    public CardGrid(Context context) {
        super(context);
        this.context = context;
    }
    
    public void toggleSelection(int position) {
        CardView cardView = (CardView) getChildAt(position);
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
    
    public void add(Card card) {
        addView(new CardView(context, card, false));
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

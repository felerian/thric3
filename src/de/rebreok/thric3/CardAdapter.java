package de.rebreok.thric3;

import android.content.Context;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class CardAdapter extends ArrayAdapter<Card> {
    
    private Context context;
    private ArrayList<Integer> selection;
    
    public CardAdapter(Context context) {
        super(context, 0);
        this.context = context;
        this.selection = new ArrayList<Integer>();
    }
    
    public void toggleSelection(int position) {
        if (selection.contains(position)) {
            selection.remove((Integer) position);
        } else {
            selection.add(position);
        }
        notifyDataSetChanged();
    }
    
    public void clearSelection() {
        selection.clear();
        notifyDataSetChanged();
    }
    
    public Pile getSelectedCards() {
        Pile result = new Pile();
        for (int i: selection) {
            result.addCard(getItem(i));
        }
        return result;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Card card = getItem(position);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int resid_card_layout;
        if (selection.contains(position)) {
            resid_card_layout = R.layout.card_view_selected;
        } else {
            resid_card_layout = R.layout.card_view_normal;
        }
        LinearLayout view = (LinearLayout) layoutInflater.inflate(resid_card_layout, null);
        for (int i = 0; i < card.getNumberAsInt(); i++) {
            ImageView symbol = new ImageView(context);
            symbol.setImageResource(card.getImageId());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50, 0);
            params.gravity = Gravity.CENTER;
            symbol.setLayoutParams(params);
            view.addView(symbol);
        }
        return view;
    }
}

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
            result.add(getItem(i));
        }
        return result;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return new CardView(context, getItem(position), selection.contains(position));
    }
}

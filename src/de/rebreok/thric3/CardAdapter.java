package de.rebreok.thric3;

import android.content.Context;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class CardAdapter extends ArrayAdapter<Card> {
    
    Context context;
    
    public CardAdapter(Context context) {
        super(context, 0);
        this.context = context;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Card card = getItem(position);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout) layoutInflater.inflate(R.layout.card_view_1, null);
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

package de.rebreok.thric3;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class CardView extends LinearLayout {
    
    private Card card;
    
    public CardView(Context context, Card card, boolean selected) {
        super(context);
        this.card = card;
        if (selected) {
            setBackground(context.getResources().getDrawable(R.drawable.card_front_selected));
        } else {
            setBackground(context.getResources().getDrawable(R.drawable.card_front));
        }
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        for (int i = 0; i < card.getNumberAsInt(); i++) {
            ImageView symbol = new ImageView(context);
            symbol.setImageResource(card.getImageId());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50, 0);
            params.gravity = Gravity.CENTER;
            symbol.setLayoutParams(params);
            addView(symbol);
        }
    }
}

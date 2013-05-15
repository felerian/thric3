package de.rebreok.thric3;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;


public class CardView extends LinearLayout {
    
    private final static int SYMBOL_HEIGHT = 65;
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
        LinearLayout.LayoutParams params;
        ImageView symbol;
        Space space;
        switch (card.getNumberAsInt()) {
            case 1:
                space = new Space(context);
                params = new LinearLayout.LayoutParams(SYMBOL_HEIGHT, SYMBOL_HEIGHT, LinearLayout.LayoutParams.FILL_PARENT);
                params.gravity = Gravity.CENTER;
                space.setLayoutParams(params);
                addView(space);
                symbol = new ImageView(context);
                symbol.setImageResource(card.getImageId());
                params = new LinearLayout.LayoutParams(SYMBOL_HEIGHT, SYMBOL_HEIGHT, LinearLayout.LayoutParams.FILL_PARENT);
                params.gravity = Gravity.CENTER;
                symbol.setLayoutParams(params);
                addView(symbol);
                space = new Space(context);
                params = new LinearLayout.LayoutParams(SYMBOL_HEIGHT, SYMBOL_HEIGHT, LinearLayout.LayoutParams.FILL_PARENT);
                params.gravity = Gravity.CENTER;
                space.setLayoutParams(params);
                addView(space);
                break;
            case 2:
                space = new Space(context);
                params = new LinearLayout.LayoutParams(SYMBOL_HEIGHT / 2, SYMBOL_HEIGHT / 2, LinearLayout.LayoutParams.FILL_PARENT);
                params.gravity = Gravity.CENTER;
                space.setLayoutParams(params);
                addView(space);
                for (int i = 0; i < 2; i++) {
                    symbol = new ImageView(context);
                    symbol.setImageResource(card.getImageId());
                    params = new LinearLayout.LayoutParams(SYMBOL_HEIGHT, SYMBOL_HEIGHT, LinearLayout.LayoutParams.FILL_PARENT);
                    params.gravity = Gravity.CENTER;
                    symbol.setLayoutParams(params);
                    addView(symbol);
                }
                space = new Space(context);
                params = new LinearLayout.LayoutParams(SYMBOL_HEIGHT / 2, SYMBOL_HEIGHT / 2, LinearLayout.LayoutParams.FILL_PARENT);
                params.gravity = Gravity.CENTER;
                space.setLayoutParams(params);
                addView(space);
                break;
            case 3:
                for (int i = 0; i < 3; i++) {
                    symbol = new ImageView(context);
                    symbol.setImageResource(card.getImageId());
                    params = new LinearLayout.LayoutParams(SYMBOL_HEIGHT, SYMBOL_HEIGHT, LinearLayout.LayoutParams.FILL_PARENT);
                    params.gravity = Gravity.CENTER;
                    symbol.setLayoutParams(params);
                    addView(symbol);
                }
        }
    }
}

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
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.Math;


/**
 * Widget displaying one card
 */
public class CardView extends LinearLayout {
    
    private final static int DEFAULT_WIDTH = 120;
    private final static float ASPECT_RATIO = 1.5f;
    private Context context;
    private Card card;
    private boolean selected;
    
    /**
     * Return the displayed card
     */
    public Card getCard() {
        return card;
    }
    
    /**
     * Constructor, used to inflate from xml
     */
    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.selected = false;
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        setCard(null);
    }
     
    /**
     * Constructor
     */
    public CardView(Context context, Card card) {
        super(context);
        this.context = context;
        this.selected = false;
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        setCard(card);
    }
    
    /**
     * Set the card to be displayed
     */
    public void setCard(Card card) {
        this.card = card;
        removeAllViews();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (card != null) {
            LinearLayout.LayoutParams params;
            ImageView symbol;
            View space;
            switch (card.getNumberAsInt()) {
                case 1:
                    space = (View) inflater.inflate(R.layout.space_symbol, this, false);
                    addView(space);
                    symbol = (ImageView) inflater.inflate(R.layout.symbol, this, false);
                    symbol.setImageResource(card.getImageId());
                    addView(symbol);
                    space = (View) inflater.inflate(R.layout.space_symbol, this, false);
                    addView(space);
                    break;
                case 2:
                    space = (View) inflater.inflate(R.layout.space_half_symbol, this, false);
                    addView(space);
                    for (int i = 0; i < 2; i++) {
                        symbol = (ImageView) inflater.inflate(R.layout.symbol, this, false);
                        symbol.setImageResource(card.getImageId());
                        addView(symbol);
                    }
                    space = (View) inflater.inflate(R.layout.space_half_symbol, this, false);
                    addView(space);
                    break;
                case 3:
                    for (int i = 0; i < 3; i++) {
                        symbol = (ImageView) inflater.inflate(R.layout.symbol, this, false);
                        symbol.setImageResource(card.getImageId());
                        addView(symbol);
                    }
            }
        }
        setSelection(false);
    }
    
    /**
     * Update the background image (an empty card)
     */
    private void updateBackground() {
        //~ if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            //~ if (card != null) {
                //~ if (selected) {
                    //~ setBackground(context.getResources().getDrawable(R.drawable.card_front_selected));
                //~ } else {
                    //~ setBackground(context.getResources().getDrawable(R.drawable.card_front));
                //~ }
            //~ } else {
                //~ setBackground(null);
            //~ }
        //~ } else {
            if (card != null) {
                if (selected) {
                    setBackgroundDrawable(context.getResources().getDrawable(R.drawable.card_front_selected));
                } else {
                    setBackgroundDrawable(context.getResources().getDrawable(R.drawable.card_front));
                }
            } else {
                setBackgroundDrawable(null);
            }
        //~ }
    }
    
    /**
     * Set the selection state
     */
    public void setSelection(boolean selected) {
        if (card != null) {
            this.selected = selected;
        }
        updateBackground();
    }
    
    /**
     * Check whether this card is selected
     */
    public boolean isSelected() {
        return selected;
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        
        int measuredWidth;
        int measuredHeight;
        
		if ( heightMode == MeasureSpec.EXACTLY && widthMode == MeasureSpec.EXACTLY ) {
			measuredWidth = widthSize;
			measuredHeight = heightSize;
		} else if ( heightMode == MeasureSpec.EXACTLY && widthMode == MeasureSpec.AT_MOST) {
			measuredWidth = (int) Math.min( widthSize, heightSize / ASPECT_RATIO );
			measuredHeight = (int) (measuredWidth * ASPECT_RATIO);
		} else if ( widthMode == MeasureSpec.EXACTLY  && heightMode == MeasureSpec.AT_MOST) {
			measuredHeight = (int) Math.min( heightSize, widthSize * ASPECT_RATIO );
			measuredWidth = (int) (measuredHeight / ASPECT_RATIO);
		} else if ( widthMode == MeasureSpec.AT_MOST  && heightMode == MeasureSpec.AT_MOST) {
			if ( widthSize > heightSize / ASPECT_RATIO ) {
				measuredHeight = heightSize;
				measuredWidth = (int)( measuredHeight / ASPECT_RATIO );
			} else {
				measuredWidth = widthSize;
				measuredHeight = (int) (measuredWidth * ASPECT_RATIO);
			}
		} else if ( heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.EXACTLY) {
			measuredWidth = (int) (heightSize / ASPECT_RATIO);
			measuredHeight = heightSize;
		} else if ( widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.EXACTLY) {
			measuredWidth = widthSize;
			measuredHeight = (int) (widthSize * ASPECT_RATIO);
		} else {
            measuredWidth = DEFAULT_WIDTH;
            measuredHeight = (int) (DEFAULT_WIDTH * ASPECT_RATIO);
        }
        
        super.onMeasure(MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY));
        
        setMeasuredDimension(measuredWidth, measuredHeight);
    }
}

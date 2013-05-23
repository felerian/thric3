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
import android.view.View;
import android.widget.ImageView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Space;

import java.lang.Math;

public class CardView extends LinearLayout {
    
    private final static int DEFAULT_WIDTH = 120;
    private final static float ASPECT_RATIO = 1.5f;
    private Context context;
    private Card card;
    private boolean selected;
    
    public Card getCard() {
        return card;
    }
    
    public CardView(Context context, Card card, boolean selected) {
        super(context);
        this.context = context;
        this.card = card;
        setSelection(selected);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params;
        ImageView symbol;
        Space space;
        switch (card.getNumberAsInt()) {
            case 1:
                space = new Space(context);
                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0, 1f);
                params.gravity = Gravity.CENTER;
                space.setLayoutParams(params);
                addView(space);
                symbol = new ImageView(context);
                symbol.setImageResource(card.getImageId());
                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0, 1f);
                params.gravity = Gravity.CENTER;
                symbol.setLayoutParams(params);
                symbol.setPadding(5, 5, 5, 5);
                addView(symbol);
                space = new Space(context);
                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0, 1f);
                params.gravity = Gravity.CENTER;
                space.setLayoutParams(params);
                addView(space);
                break;
            case 2:
                space = new Space(context);
                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0, 1f);
                params.gravity = Gravity.CENTER;
                space.setLayoutParams(params);
                addView(space);
                for (int i = 0; i < 2; i++) {
                    symbol = new ImageView(context);
                    symbol.setImageResource(card.getImageId());
                    params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0, 2f);
                    params.gravity = Gravity.CENTER;
                    symbol.setLayoutParams(params);
                    symbol.setPadding(5, 5, 5, 5);
                    addView(symbol);
                }
                space = new Space(context);
                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0, 1f);
                params.gravity = Gravity.CENTER;
                space.setLayoutParams(params);
                addView(space);
                break;
            case 3:
                for (int i = 0; i < 3; i++) {
                    symbol = new ImageView(context);
                    symbol.setImageResource(card.getImageId());
                    params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0, 1f);
                    params.gravity = Gravity.CENTER;
                    symbol.setLayoutParams(params);
                    symbol.setPadding(5, 5, 5, 5);
                    addView(symbol);
                }
        }
    }
    
    public void setSelection(boolean selected) {
        this.selected = selected;
        if (selected) {
            setBackground(context.getResources().getDrawable(R.drawable.card_front_selected));
        } else {
            setBackground(context.getResources().getDrawable(R.drawable.card_front));
        }
    }
    
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
		} else if ( heightMode == MeasureSpec.AT_MOST) {
			measuredWidth = (int) (heightSize / ASPECT_RATIO);
			measuredHeight = heightSize;
		} else if ( widthMode == MeasureSpec.AT_MOST) {
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

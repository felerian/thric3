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

import java.util.Stack;
import java.util.HashSet;
import java.util.Collections;


/**
 * A card in the game of Thric3
 */
class Card {
	
	static enum Color {RED, GREEN, BLUE};
	static enum Shape {CLUBS, HEARTS, DIAMONDS};
	static enum Number {ONE, TWO, THREE};
	static enum Filling {EMPTY, HALF, FULL};
	
	private Color color;
	private Shape shape;
	private Number number;
	private Filling filling;
	
	/**
	 * Creates a card based on given properties.
	 * 
	 * @param color
	 * @param shape
	 * @param number
	 * @param filling
	 */
	public Card(Color color, Shape shape, Number number, Filling filling) {
		this.color = color;
		this.shape = shape;
		this.number = number;
		this.filling = filling;
	}
    
    /**
	 * Creates a dummy card for debugging purposes.
	 */
	public Card() {
		this.color = Color.GREEN;
		this.shape = Shape.CLUBS;
		this.number = Number.THREE;
		this.filling = Filling.HALF;
	}

	/**
	 * Returns the card's color.
	 * 
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Returns the card's shape.
	 * 
	 * @return the shape
	 */
	public Shape getShape() {
		return shape;
	}

	/**
	 * Returns the card's number.
	 * 
	 * @return the number
	 */
	public Number getNumber() {
		return number;
	}

	/**
	 * Returns the card's number as an int.
	 * 
	 * @return the number
	 */
	public int getNumberAsInt() {
		switch (number) {
            case ONE:
                return 1;
            case TWO:
                return 2;
            case THREE:
                return 3;
        }
        return 0;
	}

	/**
	 * Returns the card's filling.
	 * 
	 * @return the filling
	 */
	public Filling getFilling() {
		return filling;
	}
	
	/**
	 * Returns the id of the image from ./res/drawable/ which is needed to
	 * draw this card.
	 * 
	 * Note: there must be a more elegant way to do this :-/
	 * 
	 * @return	An id like R.drawable.clubs_blue_half
	 */
	public int getImageId() {
		switch (shape) {
		case CLUBS:
			switch (color) {
			case RED:
				switch (filling) {
				case EMPTY:	return R.drawable.clubs_red_empty;
				case HALF:	return R.drawable.clubs_red_half;
				case FULL:	return R.drawable.clubs_red_full; }
			case GREEN:
				switch (filling) {
				case EMPTY:	return R.drawable.clubs_green_empty;
				case HALF:	return R.drawable.clubs_green_half;
				case FULL:	return R.drawable.clubs_green_full; }
			case BLUE:
				switch (filling) {
				case EMPTY:	return R.drawable.clubs_blue_empty;
				case HALF:	return R.drawable.clubs_blue_half;
				case FULL:	return R.drawable.clubs_blue_full; }
			}
		case HEARTS:
			switch (color) {
			case RED:
				switch (filling) {
				case EMPTY:	return R.drawable.hearts_red_empty;
				case HALF:	return R.drawable.hearts_red_half;
				case FULL:	return R.drawable.hearts_red_full; }
			case GREEN:
				switch (filling) {
				case EMPTY:	return R.drawable.hearts_green_empty;
				case HALF:	return R.drawable.hearts_green_half;
				case FULL:	return R.drawable.hearts_green_full;
				}
			case BLUE:
				switch (filling) {
				case EMPTY:	return R.drawable.hearts_blue_empty;
				case HALF:	return R.drawable.hearts_blue_half;
				case FULL:	return R.drawable.hearts_blue_full; }
			}
		case DIAMONDS:
			switch (color) {
			case RED:
				switch (filling) {
				case EMPTY:	return R.drawable.diamonds_red_empty;
				case HALF:	return R.drawable.diamonds_red_half;
				case FULL:	return R.drawable.diamonds_red_full; }
			case GREEN:
				switch (filling) {
				case EMPTY:	return R.drawable.diamonds_green_empty;
				case HALF:	return R.drawable.diamonds_green_half;
				case FULL:	return R.drawable.diamonds_green_full; }
			case BLUE:
				switch (filling) {
				case EMPTY:	return R.drawable.diamonds_blue_empty;
				case HALF:	return R.drawable.diamonds_blue_half;
				case FULL:	return R.drawable.diamonds_blue_full; }
			}
		}
		// If this line is executed, something went really wrong:
		assert false : "getImageId() didn't find a valid id to return!";
		// This is needed to sooth the compiler, though it will never be
		// executed:
		return -1;
	}
}







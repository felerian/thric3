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

import java.util.HashSet;


/**
 * An unordered pile of cards
 */
class Pile extends HashSet<Card>{

	/**
	 * Check if this pile is a valid Set of three cards, according to the rules
	 */
	public boolean isValidSet() {
		if (size() != 3)            // Check the number of cards
            return false;
		if (getColorCount() == 2)   // Check the colors
            return false;
		if (getShapeCount() == 2)   // Check the shapes
            return false;
		if (getNumberCount() == 2)  // Check the numbers
            return false;
		if (getFillingCount() == 2) // Check the fillings
            return false;
		return true;
	}
    
    public int getColorCount() {
        HashSet<Card.Color> colors = new HashSet<Card.Color>();
		for (Card card : this)
            colors.add(card.getColor());
		return colors.size();
    }
    
    public int getShapeCount() {
        HashSet<Card.Shape> shapes = new HashSet<Card.Shape>();
		for (Card card : this)
            shapes.add(card.getShape());
		return shapes.size();
    }
    
    public int getNumberCount() {
        HashSet<Card.Number> numbers = new HashSet<Card.Number>();
		for (Card card : this)
            numbers.add(card.getNumber());
		return numbers.size();
    }
    
    public int getFillingCount() {
        HashSet<Card.Filling> fillings = new HashSet<Card.Filling>();
		for (Card card : this)
            fillings.add(card.getFilling());
		return fillings.size();
    }
    
    /**
	 * Check if this pile contains any valid Set, according to the rules
	 */
    public boolean containsValidSet() {
        for (Card card1: this) {
            for (Card card2: this) {
                for (Card card3: this) {
                    Pile pile = new Pile();
                    pile.add(card1);
                    pile.add(card2);
                    pile.add(card3);
                    if (pile.size() == 3 && pile.isValidSet())
                        return true;
                }
            }
        }
        return false;
    }
}

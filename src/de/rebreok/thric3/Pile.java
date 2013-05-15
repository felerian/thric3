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
		// Check the number of cards:
		if (size() != 3)
            return false;
        
		// Check the colors:
		HashSet<Card.Color> colors = new HashSet<Card.Color>();
		for (Card card : this)
            colors.add(card.getColor());
		if (colors.size()==2)
            return false;
        
		// Check the shapes:
		HashSet<Card.Shape> shapes = new HashSet<Card.Shape>();
		for (Card card : this)
            shapes.add(card.getShape());
		if (shapes.size()==2)
            return false;
        
		// Check the numbers:
		HashSet<Card.Number> numbers = new HashSet<Card.Number>();
		for (Card card : this)
            numbers.add(card.getNumber());
		if (numbers.size()==2)
            return false;
        
		// Check the fillings:
		HashSet<Card.Filling> fillings = new HashSet<Card.Filling>();
		for (Card card : this)
            fillings.add(card.getFilling());
		if (fillings.size()==2)
            return false;
        
		// Everything is fine:
		return true;
	}
}

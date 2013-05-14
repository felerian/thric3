package de.rebreok.thric3;

import java.util.HashSet;


/**
 * An unordered pile of cards.
 * 
 * @author kai
 *
 */
class Pile extends HashSet<Card>{
	
	//~ /**
	 //~ * A Set object representing the cards constituting this pile.
	 //~ */
	//~ private HashSet<Card> set;
	
	//~ /**
	 //~ * Default constructor.
	 //~ * Creates an empty pile.
	 //~ */
	//~ public Pile() {
		//~ set = new HashSet<Card>();
	//~ }
	
	//~ /**
	 //~ * Creates a pile containing given cards.
	 //~ * 
	 //~ * @param cards
	 //~ */
	//~ public Pile(Card... cards) {
		//~ this();
		//~ addCards(cards);
	//~ }
	
	//~ /**
	 //~ * Adds given cards to the pile.
	 //~ * 
	 //~ * @param cards
	 //~ */
	//~ public void addCards(Card... cards) {
		//~ for (Card card : cards) {
			//~ addCard(card);
		//~ }
	//~ }
	
	/**
	 * Adds given card to the pile.
	 * 
	 * @param card
	 */
	public void addCard(Card card) {
		add(card);
	}
	
	/**
	 * Returns the number of cards on the pile.
	 * 
	 * @return	Number of cards on pile
	 */
	public int getSize() {
		return size();
	}
	
	/**
	 * Check, whether this pile is a valid set (according to the rules)
	 * of three cards.
	 * 
	 * @return	true, if this pile is a valid set
	 */
	public boolean isValidSet() {
		// First: Check the number of cards:
		if (getSize() != 3) { return false; }
		// Check the colors:
		HashSet<Card.Color> colors = new HashSet<Card.Color>();
		for (Card card : this) { colors.add(card.getColor()); }
		if (colors.size()==2) {return false; }
		// Check the shapes:
		HashSet<Card.Shape> shapes = new HashSet<Card.Shape>();
		for (Card card : this) { shapes.add(card.getShape()); }
		if (shapes.size()==2) {return false; }
		// Check the numbers:
		HashSet<Card.Number> numbers = new HashSet<Card.Number>();
		for (Card card : this) { numbers.add(card.getNumber()); }
		if (numbers.size()==2) {return false; }
		// Check the fillings:
		HashSet<Card.Filling> fillings = new HashSet<Card.Filling>();
		for (Card card : this) { fillings.add(card.getFilling()); }
		if (fillings.size()==2) {return false; }
		// Everything is fine:
		return true;
	}
}

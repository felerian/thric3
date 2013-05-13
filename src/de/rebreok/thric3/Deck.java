package de.rebreok.thric3;

import java.util.Stack;
import java.util.Collections;


/**
 * A deck of cards.
 * 
 * @author kai
 *
 */
class Deck {
	
	/**
	 * A Stack object representing the stack of cards constituting this deck.
	 */
	private Stack<Card> stack;
	
	/**
	 * Default constructor.
	 * Creates a new complete and shuffled deck of 81 cards.
	 */
	public Deck() {
		// Create empty stack:
		stack = new Stack<Card>();
		// Fill the stack with cards:
		for (Card.Color c : Card.Color.values()) {
			for (Card.Shape s : Card.Shape.values()) {
				for (Card.Number n : Card.Number.values()) {
					for (Card.Filling f : Card.Filling.values()) {
						stack.add(new Card(c,s,n,f));
					}
				}
			}
		}
		// Check the correct number of cards:
		assert stack.size()==81 : "A fresh deck must have 81 cards!";
		// Shuffle the cards:
		Collections.shuffle(stack);
	}
	
	/**
	 * Removes a card from the deck and returns it.
	 * 
	 * @return	The removed card
	 */
	public Card drawCard() {
		return stack.pop();
	}
	
	/**
	 * Returns the number of cards in the deck.
	 * 
	 * @return	Number of cards
	 */
	public int getSize() {
		return stack.size();
	}
}

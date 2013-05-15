package de.rebreok.thric3;

import java.util.Stack;
import java.util.Collections;


/**
 * A deck of cards.
 */
class Deck extends Stack<Card> {
    
	/** 
     * Create a new shuffled deck of 81 cards
     */
	public Deck() {
		super();
		for (Card.Color c : Card.Color.values()) {
			for (Card.Shape s : Card.Shape.values()) {
				for (Card.Number n : Card.Number.values()) {
					for (Card.Filling f : Card.Filling.values()) {
						add(new Card(c,s,n,f));
					}
				}
			}
		}
		Collections.shuffle(this);
	}
}

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

import java.lang.Comparable;


/**
 * A player
 * 
 * Complete with his/her own pile of cards, the ability to count his/her score
 * and the possibility to be locked.
 */
class Player implements Comparable<Player>{
	
	private String	name;
	private Pile	pile;
	private boolean	locked;
	
	public Player(String name) {
		this.name = name;
		this.pile = new Pile();
		this.locked = false;
	}
    
    public int compareTo(Player other) {
        return getScore() - other.getScore();
    }
	
    /**
	 * Returns true if the player is locked for calling an invalid set.
	 * 
	 * @return	true, if locked
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * Locks this player for calling an invalid set.
	 */
	public void lock() {
		this.locked = true;
	}
	
	/**
	 * Unlocks this player.
	 */
	public void unlock() {
		this.locked = false;
	}

	/**
	 * Returns this player's name.
	 * 
	 * @return	the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Adds a card to this player's pile.
	 */
	public void addCard(Card card) {
		pile.add(card);
	}
	
	/**
	 * Returns this player's score, i.e. the number of cards on this
	 * player's pile.
	 * 
	 * @return	the score
	 */
	public int getScore() {
		return pile.size();
	}
}


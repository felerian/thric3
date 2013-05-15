package de.rebreok.thric3;


class Player {
	
	private String	name;
	private Pile	pile;
	private boolean	locked;
	
	public Player(String name) {
		this.name = name;
		this.pile = new Pile();
		this.locked = false;
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


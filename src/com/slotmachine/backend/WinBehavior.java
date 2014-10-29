package com.slotmachine.backend;
/**
 * Determines a winning set of symbols.
 * @author Chad Golden
 *
 */
/* Key for reelDisplay:
 * [0][0]  [1][0] [2][0]
 * [0][1]  [1][1] [2][1]
 * [0][2]  [1][2] [2][2]
 * 
 * Beware indices are not typical
 * for matrices, because of how
 * the slot machine's reel display
 * works.
 */
public abstract class WinBehavior {

	/**
	 * The visible display of the current reel.
	 */
	protected Symbol[][] reelDisplay;
	
	/**
	 * Default class constructor.
	 * Initializes <code>reelDisplay</code>.
	 */
	public WinBehavior() {
		reelDisplay = new Symbol[3][3];
	}
	
	public WinBehavior(Symbol[][] reelDisplay) {
		this.reelDisplay = reelDisplay;
	}
	
	/**
	 * Determines if there is a winning combination given the
	 * current display of visible symbols.
	 * @return Set of symbols is a winning combination.
	 */
	public abstract boolean isWinner();
	
}

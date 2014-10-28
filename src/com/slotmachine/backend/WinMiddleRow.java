package com.slotmachine.backend;

/**
 * Determines winning set of symbols based on the middle row
 * having equivalent symbols.
 * @author Chad Golden
 *
 */
/*
 *i.e.
 * 
 * - - -
 * X X X
 * - - -
 * 
 */
public class WinMiddleRow extends WinBehavior {
	
	/**
	 * Default class constructor
	 */
	public WinMiddleRow() {
		super();
	}
	
	/**
	 * Class constructor that takes as arguments three arrays
	 * of reels that represent the visible portion of the
	 * slot machine.
	 * @param first First visible reel of symbols
	 * @param middle Middle visible reel of symbols
	 * @param last Last visible reel of symbols
	 */
	public WinMiddleRow(Symbol[] first, Symbol[] middle, Symbol[] last) {
		super(first, middle, last);
	}

	@Override
	public boolean isWinner() {
		// Check if first symbol is the same as the middle symbol
		boolean firstCondition = reelDisplay[0][1].toString().equals(
				reelDisplay[1][1].toString());
		
		// Check if middle symbol is the same as the last symbol
		boolean secondCondition = reelDisplay[1][1].toString().equals(
				reelDisplay[2][1].toString());
		
		return firstCondition && secondCondition;
	}

}

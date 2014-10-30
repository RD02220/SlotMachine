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
	 * Class constructor that takes as arguments a 2D array
	 * of symbols that represent the reel display.
	 * @param reelDisplay The visible display of the reels.
	 */
	public WinMiddleRow(Symbol[][] reelDisplay) {
		super(reelDisplay);
	}

	@Override
	public boolean isWinner() {
		// Check if first symbol is the same as the middle symbol
		boolean firstCondition = reelDisplay[0][1].toString().equals(
				reelDisplay[1][1].toString());
		
		// Check if middle symbol is the same as the last symbol
		boolean secondCondition = reelDisplay[1][1].toString().equals(
				reelDisplay[2][1].toString());
		
		if (firstCondition && secondCondition) {
			System.out.println("WINNER (middle row)");
			return true;
		}
		
		return false;
	}

}

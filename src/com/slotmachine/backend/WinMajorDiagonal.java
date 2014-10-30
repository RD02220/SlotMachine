package com.slotmachine.backend;

/**
 * Determines winning set of symbols based on the major
 * diagonal having equivalent symbols.
 * @author Chad Golden
 *
 */
/*
 *i.e.
 * 
 * X - -
 * - X -
 * - - X
 * 
 */
public class WinMajorDiagonal extends WinBehavior {
	
	/**
	 * Default class constructor.
	 */
	public WinMajorDiagonal() {
		super();
	}
	
	/**
	 * Class constructor that takes as arguments a 2D array
	 * of symbols that represent the reel display.
	 * @param reelDisplay The visible display of the reels.
	 */
	public WinMajorDiagonal(Symbol[][] reelDisplay) {
		super(reelDisplay);
	}

	@Override
	public boolean isWinner() {
		// Check if first symbol is the same as the middle symbol
		boolean firstCondition = reelDisplay[0][0].toString().equals(
				reelDisplay[1][1].toString());
		
		// Check if middle symbol is the same as the last symbol
		boolean secondCondition = reelDisplay[1][1].toString().equals(
				reelDisplay[2][2].toString());
		
		if (firstCondition && secondCondition) {
			System.out.println("WINNER (major diagonal)");
			return true;
		}
		
		return false;
	}

}

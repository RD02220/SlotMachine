package com.slotmachine.backend;
/**
 * Determines winning set of symbols based on the minor
 * diagonal having equivalent symbols.
 * @author Chad
 *
 */
/*
 *i.e.
 * 
 * - - X
 * - X -
 * X - -
 * 
 */
public class WinMinorDiagonal extends WinBehavior {

	/**
	 * Default class constructor.
	 */
	public WinMinorDiagonal() {
		super();
	}
	
	/**
	 * Class constructor that takes as arguments three arrays
	 * of reels that represent the visible portion of the
	 * slot machine.
	 * @param first First visible slot of symbols
	 * @param middle Middle visible slot of symbols
	 * @param last Last visible slot of symbols
	 */
	public WinMinorDiagonal(Symbol[] first, Symbol[] middle, Symbol[] last) {
		super(first, middle, last);
	}
	
	@Override
	public boolean isWinner() {
		// Check if first symbol is the same as the middle symbol
		boolean firstCondition = reelDisplay[2][0].toString().equals(
				reelDisplay[1][1].toString());
		
		// Check if middle symbol is the same as the last symbol
		boolean secondCondition = reelDisplay[1][1].toString().equals(
				reelDisplay[0][2].toString());
		
		return firstCondition && secondCondition;
	}

}

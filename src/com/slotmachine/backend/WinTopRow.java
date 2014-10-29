package com.slotmachine.backend;

/**
 * Determines winning set of symbols where the
 * symbols all match on the top row.
 * @author Chad Golden
 *
 */
/*
 * Win scenario:
 * 
 * Check WinBehavior.java for clarification of 2D
 * array indices.
 * 
 * X X X
 * - - -
 * - - -
 * 
 */
public class WinTopRow extends WinBehavior {
	
	public WinTopRow(Symbol[][] reelDisplay) {
		super(reelDisplay);
	}

	@Override
	public boolean isWinner() {
		boolean firstEqualsSecond = reelDisplay[0][0].toString().
				equals(reelDisplay[1][0].toString());
		boolean secondEqualsThird = reelDisplay[1][0].toString().
				equals(reelDisplay[2][0].toString());
		
		if (firstEqualsSecond && secondEqualsThird) {
			System.out.println("WINNER (top row)");
			return true;
		}
		return false;
	}

}

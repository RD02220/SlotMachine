package com.slotmachine.backend;

/**
 * Checks for win scenario for case F on Michael's
 * document.
 * @author Chad Golden
 *
 */

/*
 * CASE F win scenario:
 * 
 *  X - X
 *  - X -
 *  - - -
 * 
 */
public class WinCaseG extends WinBehavior {

	public WinCaseG(Symbol[][] reelDisplay) {
		super(reelDisplay);
	}

	@Override
	public boolean isWinner() {
		boolean firstEqualsSecond = reelDisplay[0][2].toString().
				equals(reelDisplay[1][1].toString());
		boolean secondEqualsThird = reelDisplay[1][1].toString().
				equals(reelDisplay[2][2].toString());
		
		if (firstEqualsSecond && secondEqualsThird) {
			System.out.println("WINNER (case G)");
			return true;
		}
		return false;
	}

}
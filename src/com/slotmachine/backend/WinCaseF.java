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
public class WinCaseF extends WinBehavior {

	public WinCaseF(Symbol[][] reelDisplay) {
		super(reelDisplay);
	}

	@Override
	public boolean isWinner() {
		boolean firstEqualsSecond = reelDisplay[0][0].toString().
				equals(reelDisplay[1][1].toString());
		boolean secondEqualsThird = reelDisplay[1][1].toString().
				equals(reelDisplay[2][0].toString());
		
		if (firstEqualsSecond && secondEqualsThird) {
			System.out.println("WINNER (case F)");
			return true;
		}
		return false;
	}

}

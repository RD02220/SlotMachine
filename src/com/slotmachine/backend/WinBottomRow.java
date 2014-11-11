package com.slotmachine.backend;


/**
 * Determines matching symbols on the bottom row of a
 * symbol display.
 * @author Chad Golden
 *
 */
public class WinBottomRow extends WinBehavior {

	public WinBottomRow(Symbol[][] reelDisplay) {
		super(reelDisplay);
	}

	@Override
	public boolean isWinner() {
		boolean firstEqualsSecond = reelDisplay[0][2].toString().
				equals(reelDisplay[1][2].toString());
		boolean secondEqualsThird = reelDisplay[1][2].toString().
				equals(reelDisplay[2][2].toString());
		
		if (firstEqualsSecond && secondEqualsThird) {
			System.out.println("WINNER (bottom row)");
			return true;
		}
		return false;
	}

}

package com.slotmachine.backend;

import java.util.ArrayList;

/**
 * Used as the context for the strategy pattern for
 * the win behaviors.
 * @author Chad Golden
 *
 */
public class WinContext {
	
	/**
	 * Holds all of the possible WinBehavior subtypes.
	 */
	private ArrayList<WinBehavior> winBehaviors;
	
	/**
	 * Class constructor that takes a 2D array of symbols
	 * and initializes the collection of win behaviors
	 * based on the 2D array of symbols.
	 * @param symbolDisplay
	 */
	public WinContext(Symbol[][] symbolDisplay) {
		winBehaviors = new ArrayList<>();
		winBehaviors.add(new WinTopRow(symbolDisplay));
		winBehaviors.add(new WinMiddleRow(symbolDisplay));
		winBehaviors.add(new WinBottomRow(symbolDisplay));
		winBehaviors.add(new WinMajorDiagonal(symbolDisplay));
		winBehaviors.add(new WinMinorDiagonal(symbolDisplay));
		winBehaviors.add(new WinCaseF(symbolDisplay));
		winBehaviors.add(new WinCaseG(symbolDisplay));
	}
	
	/**
	 * Iterates through all of the win behaviors 
	 * contained in the collection. If a winning 
	 * combination is a value of true is returned 
	 * otherwise false is returned.
	 * @return 
	 */
	public boolean checkWinner() {
		// Initialize to false.
		boolean isWinner = false;
		for (WinBehavior wb : winBehaviors) {
			if (wb.isWinner()) {
				isWinner = true;
			}
		}
		return isWinner;
	}

}

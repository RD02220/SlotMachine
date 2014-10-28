package com.slotmachine.backend;

/**
 * Contains main method
 * @author Chad Golden
 *
 */
public class Run {

	/**
	 * @param args Command line arguments
	 */
	public static void main(String[] args) throws Exception {
		// Create a SlotFacade
		SlotFacade player = new SlotFacade();
		
		// Counters
		int numberOfTries = 0;
		
		// Loop tries.
		while (numberOfTries++ < 100) {
			player.pullLever();
		}
		
//		double winPercent = player.getNumberOfWins() / (double)(numberOfTries - 1) * 100;
//		System.out.println("Winning percentage: ");
//		System.out.println(winPercent + " %");
	}

}

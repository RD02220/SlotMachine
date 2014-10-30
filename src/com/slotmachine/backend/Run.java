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
		double winPercent;
		
		// Loop tries.
		while (numberOfTries++ < 10000) {
			player.pullLever();
		}
		
		winPercent = player.getNumberOfWins() / (double)(--numberOfTries) * 100;
		
		System.out.println("Statistics:\n------------");
		System.out.println("Number of wins: " + player.getNumberOfWins());
		System.out.println("Number of attempts: " + numberOfTries);
		System.out.println("Win rate: "
				+ winPercent
				+ "%");
		
		// Each winning scenario has a 4% chance of happening
		// per pull of the slot machine. There are 7 winning
		// scenarios thus when lots of tests are run the rate
		// approaches 4% * 7 = 28%
		
	}

}

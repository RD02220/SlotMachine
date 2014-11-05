package com.slotmachine.backend;

/**
 * Facade pattern for the slot machine. Simplifies the interface for the users
 * of the values, references, and methods.
 * 
 * @author Chad Golden
 * 
 */
public class SlotFacade {

	/**
	 * Holds the singleton instance of <code>SlotMachine</code>
	 */
	private SlotMachine theSlotMachine;

	/**
	 * Holds each reel as part of the slot machine. A reel is a collection of
	 * <code>Symbol</code>'s.
	 */
	private Reel[] reels;

	/**
	 * Holds the visible portion of the symbols within the reels of the slot
	 * machine.
	 */
	private Symbol[][] symbolDisplay;

	private int numberOfWins;

	/**
	 * 
	 */
	public SlotFacade() {
		// Get the slot machine instance.
		theSlotMachine = SlotMachine.getSlotMachine();

		// Set the reels to a set of symbols.
		theSlotMachine.setFirstReel(ReelGenerator
				.generateRandomReel(Reel.NUMBER_OF_SYMBOLS));
		theSlotMachine.setMiddleReel(ReelGenerator
				.generateRandomReel(Reel.NUMBER_OF_SYMBOLS));
		theSlotMachine.setLastReel(ReelGenerator
				.generateRandomReel(Reel.NUMBER_OF_SYMBOLS));

		// Initialize reels.
		reels = new Reel[3];
		reels[0] = theSlotMachine.getFirstReel();
		reels[1] = theSlotMachine.getMiddleReel();
		reels[2] = theSlotMachine.getLastReel();

		// Initialize symbolDisplay
		symbolDisplay = new Symbol[3][3];
		symbolDisplay[0] = reels[0].getTriplet(1);
		symbolDisplay[1] = reels[1].getTriplet(1);
		symbolDisplay[2] = reels[2].getTriplet(1);

		// Number of wins
		numberOfWins = 0;
	}

	/**
	 * Pulls the lever to the machine. Spins each reel a random number of times
	 * to display three symbols.
	 */
	public void pullLever() {
		// System.out.println("Pulling lever...");
		symbolDisplay[0] = reels[0].getRandomTriplet();
		symbolDisplay[1] = reels[1].getRandomTriplet();
		symbolDisplay[2] = reels[2].getRandomTriplet();
		// display();
		// if (checkWinner()) {
		// System.out.println();
		// numberOfWins++;
		// }
	}

	/**
	 * Checks to see if the current symbol configuration of the visible symbols
	 * is a winning combination.
	 * 
	 * @return Winner is found.
	 */
	public boolean checkWinner() {
		return new WinContext(symbolDisplay).checkWinner();
	}

	/**
	 * Displays currently visible symbols for the reels of the slot machine.
	 */
	public void display() {
		for (int i = 0; i < symbolDisplay.length; i++) {
			for (int j = 0; j < symbolDisplay[i].length; j++) {
				System.out.printf("%-3s", symbolDisplay[j][i].toString());
			}
			System.out.println();
		}
		System.out.println();
	}

	public int getNumberOfWins() {
		return numberOfWins;
	}

	public String getReelOne() {
		String reel = "";
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < symbolDisplay[i].length; j++) {
				reel = reel + " " + symbolDisplay[j][i].toString();
			}
		}
		return reel;
	}

	public String getReelTwo() {
		String reel = "";
		for (int i = 1; i < 2; i++) {
			for (int j = 0; j < symbolDisplay[i].length; j++) {
				reel = reel + " " + symbolDisplay[j][i].toString();
			}
		}
		return reel;
	}

	public String getReelThree() {
		String reel = "";
		for (int i = 2; i < 3; i++) {
			for (int j = 0; j < symbolDisplay[i].length; j++) {
				reel = reel + " " + symbolDisplay[j][i].toString();
			}
		}
		return reel;
	}
}

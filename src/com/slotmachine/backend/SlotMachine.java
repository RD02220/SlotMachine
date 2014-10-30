package com.slotmachine.backend;

/**
 * Singleton Slot Machine.
 * @author Chad Golden
 *
 */
public class SlotMachine {

	/**
	 *  Singleton instance of SlotMachine
	 */
	private static SlotMachine singleInstanceOfSlotMachine;
	
	private Reel firstReel;
	private Reel middleReel;
	private Reel lastReel;
	
	/**
	 * Private class constructor. This class may not be
	 * instantiated outside of the <code>getSlotMachine()</code>
	 * method to adhere to the singleton pattern.
	 */
	private SlotMachine() {
	}
	
	/**
	 * Returns the single possible instance of <code>SlotMachine</code>.
	 * @return SlotMachine
	 */
	public static synchronized SlotMachine getSlotMachine() {
		if (singleInstanceOfSlotMachine == null) {
			return new SlotMachine();
		}
		return singleInstanceOfSlotMachine;
	}
	
	public void setFirstReel(Reel firstReel) {
		this.firstReel = firstReel;
	}
	
	public Reel getFirstReel() {
		return firstReel;
	}
	
	public void setMiddleReel(Reel middleReel) {
		this.middleReel = middleReel;
	}
	
	public Reel getMiddleReel() {
		return middleReel;
	}
	
	public void setLastReel(Reel lastReel) {
		this.lastReel = lastReel;
	}
	
	public Reel getLastReel() {
		return lastReel;
	}
	
}

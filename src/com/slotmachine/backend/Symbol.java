package com.slotmachine.backend;

public class Symbol {

	/**
	 * Holds the string representation for the symbol.
	 */
	private String symbolName;
	
	/**
	 * Default class constructor for <code>Symbol</code>
	 */
	public Symbol() {
		symbolName = "";
	}
	
	/**
	 * Class constructor for <code>Symbol</code>.
	 * @param symbolName String representation of the symbol.
	 */
	public Symbol(String symbolName) {
		this.symbolName = symbolName;
	}
	
	/**
	 * Class constructor for <code>Symbol</code>.
	 * @param symbolName Character to be converted into string
	 *         that becomes the string representation of symbol.
	 */
	public Symbol(char symbolName) {
		this.symbolName = String.valueOf(symbolName);
	}
	
	@Override
	public String toString() {
		return symbolName;
	}
	
}

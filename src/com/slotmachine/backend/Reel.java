package com.slotmachine.backend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
//import java.util.Spliterator;
//import java.util.function.Consumer;

/**
 * A slot machine usually has 3 reels which randomly land on a symbol. This
 * class includes an iterator.
 * 
 * @author Chad Golden
 * 
 */
public class Reel implements Iterable<Symbol> {

	/**
	 * Number of symbols the reel will hold.
	 */
	protected static final int NUMBER_OF_SYMBOLS = 5;

	/**
	 * Data structure to store the symbols.
	 */
	private ArrayList<Symbol> symbols;

	/**
	 * Default class constructor for <code>Reel</code>.
	 */
	public Reel() {
		symbols = new ArrayList<Symbol>(NUMBER_OF_SYMBOLS);
	}

	/**
	 * Creates Reel from string where each character of the passed string
	 * becomes a symbol for the Reel.
	 * 
	 * @param symbolsAsString
	 */
	public Reel(String symbolsAsString) {
		this();
		// Length of string must equal NUMBER_OF_SYMBOLS
		if (symbolsAsString.length() == NUMBER_OF_SYMBOLS) {
			for (int i = 0; i < symbolsAsString.length(); i++) {
				symbols.add(new Symbol(symbolsAsString.charAt(i)));
			}
		}
		// Report error text to console.
		else {
			System.err.println("Invalid constructor for Reel! "
					+ "Length of string must equal the number of "
					+ "reels that are held (" + NUMBER_OF_SYMBOLS + ")");
		}
	}

	/**
	 * Adds passed symbol to the end of the ArrayList.
	 * 
	 * @param symbol
	 *            Symbol to add.
	 */
	public void add(Symbol symbol) {
		symbols.add(symbol);
	}

	/**
	 * Retrieves symbol from specified index. Retrieves in a circular fashion.
	 * E.g. if index passed is the last index, the symbol at index 0 will be
	 * returned.
	 * 
	 * @param index
	 * @return Symbol of specified index.
	 */
	public Symbol get(int index) {
		if (index == -1) {
			return symbols.get(NUMBER_OF_SYMBOLS - 1);
		} else if (index == NUMBER_OF_SYMBOLS) {
			return symbols.get(0);
		} else if (index > -1 && index < NUMBER_OF_SYMBOLS) {
			return symbols.get(index);
		}
		throw new IllegalArgumentException("Index out of bounds.");
	}

	/**
	 * Returns randomly generated symbol from the collection of symbols
	 * contained in the reel.
	 * 
	 * @return Randomly generated symbol.
	 */
	public Symbol getRandom() {
		Random randomGenerator = new Random();
		return get(randomGenerator.nextInt(NUMBER_OF_SYMBOLS));
	}

	/**
	 * Returns an array of three symbols when passed an index that serves as the
	 * central symbol.
	 * 
	 * @return
	 */
	public Symbol[] getTriplet(int indexOfCenterSymbol) {
		Symbol[] triplet = new Symbol[3];
		triplet[0] = get(indexOfCenterSymbol - 1);
		triplet[1] = get(indexOfCenterSymbol);
		triplet[2] = get(indexOfCenterSymbol + 1);
		return triplet;
	}

	/**
	 * Returns an array of 3 random sequential symbols.
	 * 
	 * @return Symbol array of 3 sequential symbols.
	 */
	public Symbol[] getRandomTriplet() {
		return (getTriplet(new Random().nextInt(NUMBER_OF_SYMBOLS)));
	}

	/*
	 * Commented this override out because it was causing errors and not being
	 * called.
	 * 
	 * @Override public void forEach(Consumer<? super Symbol> arg0) { throw new
	 * UnsupportedOperationException("Method not supported."); }
	 */

	public Iterator<Symbol> iterator() {
		return new SymbolIterator();
	}

	/*
	 * Commented this override out because it was causing errors and not being
	 * called.
	 * 
	 * @Override public Spliterator<Symbol> spliterator() { throw new
	 * UnsupportedOperationException("Spliterator not supported."); }
	 */

	/**
	 * Iterates over the symbols contained in <code>Reel</code>.
	 * 
	 * @author Chad Golden
	 * 
	 */
	private class SymbolIterator implements Iterator<Symbol> {

		/**
		 * Current index for this iterator.
		 */
		private int currentIndex = 0;

		/**
		 * Default class constructor.
		 */
		SymbolIterator() {
		}

		/*
		 * Commented this override out because it was causing errors and not
		 * being called.
		 * 
		 * @SuppressWarnings("rawtypes")
		 * 
		 * @Override
		 * 
		 * public void forEachRemaining(Consumer arg0) { throw new
		 * UnsupportedOperationException
		 * ("Method forEachRemaining() not supported."); }
		 */
		public boolean hasNext() {
			return (currentIndex < symbols.size());
		}

		public Symbol next() {
			return (hasNext()) ? symbols.get(currentIndex++) : null;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Unsupported method.");
		}

	}

}

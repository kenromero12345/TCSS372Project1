/**
 * 
 */
package model;

import java.util.Arrays;

/**
 * @author miclo
 *
 */
public class BitString {
	private final static int MAX_BITS = 0;
	private final static int MAX_VALUE = 0; // 2^15 - 1
	private final static int MIN_VALUE = 0; // -2^15
	private final static int MAX_UNSIGNED_VALUE = 0; // 2^16 - 1
	
	private char[] mBits;
	private int mLength;
	
	public BitString() {
		
	}
	
	public void setBits() {
		
	}
	
	public void invert() {
		
	}
	

	public void addOne() {
		if (mBits == null) {
			throw new IllegalArgumentException("Bit String must be set first.");
		}
		for (int i = mLength - 1; i >= 0; i--) {
			if (mBits[i] == '0') {
				mBits[i] = '1';
				return;
			} else {
				mBits[i] = '0';
			}
		}
	}

	/**
	 * Sets the unsigned binary representation of the input 
	 * decimal number 
	 * @param n the unsigned value
	 */
	public void setValue(int n) {
		// Check if it can be represented in the bits available
		if (n < 0 || n > MAX_UNSIGNED_VALUE) {
			throw new IllegalArgumentException("Cannot represent in "
					+ MAX_BITS + " bits.");
		}
		
		mBits = new char[MAX_BITS];
		mLength = MAX_BITS;
		for (int i = mLength - 1; i >= 0; i--) {
			mBits[i] = (n % 2 == 0) ? '0' : '1';
			n /= 2;
		}
	}

	/**
	 * Sets the 2s complement binary value of the input value
	 * @param n negative or positive decimal value
	 */
	public void setValue2sComp(int n) {
		if (n < MIN_VALUE || n > MAX_VALUE) {
			throw new IllegalArgumentException("Cannot represent in "
					+ MAX_BITS + " bits.");
		}
		boolean isNegative = n < 0;
		if (n < 0) {
			n *= -1;
		}
		setValue(n);
		if (isNegative) {
			invert();
			addOne();
		}
	}

	/**
	 * Displays the BitString in groups of four or
	 * in one group of 16. 
	 * @param groupsOfFour 
	 */
	public void display(boolean groupsOfFour) {
		for (int i = 0; i < mLength; i++) {
			if (groupsOfFour && (i % 4 == 0) && i != 0) {
				System.out.print(" ");
			}
			if (mBits[i] == '0') {
				System.out.print("0");
			} else {
				System.out.print("1");
			}
		}
	}
	
	public void displayBin() {
		
	}
	
	public void displayHex() {
		
	}

	/**
	 * Creates a copy of the BitString and returns
	 * @return copy of BitString object
	 */
	public BitString copy() {
		if (mBits == null) {
			throw new IllegalArgumentException("Nothing to copy.");
		}
		BitString copy = new BitString();
		copy.mLength = mLength;
		copy.mBits = Arrays.copyOf(mBits, mLength);
		return copy;
	}

	/**
	 * Returns the unsigned value of the BitString representation. 
	 * @return decimal unsigned value
	 */
	public int getValue() {
		if (mBits == null) {
			throw new IllegalArgumentException("Bit String must be set first.");
		}
		int value = 0;
		for (int i = 0; i < mLength; i++) {
			value *= 2;
			value += mBits[i] == '1' ? 1 : 0;
		}
		return value;
	}

	/**
	 * Returns the 2s complement value of the BitString representation. 
	 * @return decimal value
	 */
	public int getValue2sComp() {
		if (mBits == null) {
			throw new IllegalArgumentException("Bit String must be set first.");
		}
		boolean negative = mBits[0] == '1';
		if (negative) {
			BitString copyString = copy();
			copyString.invert();
			copyString.addOne();
			return -1 * copyString.getValue();
		} else {
			return getValue();
		}
	}

	/**
	 * Returns a new BitString that is combination of this and the 
	 * other BitString appended to it. 
	 * @param other
	 * @return new BitString that combines both
	 */
	public BitString append(BitString other) {
		if (mBits == null || other == null) {
			throw new IllegalArgumentException("Bit String must be set first.");
		}
		BitString bitString = new BitString();
		if (mLength + other.mLength > MAX_BITS) {
			throw new IllegalArgumentException("Exceeds bit string length");
		}
		bitString.mBits = new char[mLength + other.mLength];
		int index = 0;
		for (int i = 0; i < this.mLength; i++) {
			bitString.mBits[index++] = this.mBits[i];
		}
		for (int i = 0; i < other.mLength; i++) {
			bitString.mBits[index++] = other.mBits[i];
		}
		bitString.mLength = mLength + other.mLength;

		return bitString;
	}

	/**
	 * Returns a substring of the given string. 
	 * @param source
	 * @param start
	 * @param length
	 * @return A new BitString is created from the source starting at the index
	 *         and with the length.
	 */
	public BitString substring(int start, int length) {
		BitString subStr = new BitString();
		subStr.mBits = new char[length];
		int i;
		for (i = 0; i < length; i++) {
			subStr.mBits[i] = mBits[start + i];
		}
		subStr.mLength = length;
		return subStr;
	}

	/**
	 * Returns an array of the bits stored in the BitString
	 * @return character array of bits
	 */
	public char[] getBits() {
		return mBits;
	}

	/**
	 * Returns the length of the BitString
	 * @return length
	 */
	public int getLength() {
		return mLength;
	}

}

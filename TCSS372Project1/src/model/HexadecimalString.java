/*
 * TCSS 372 Computer Architecture
 * Project 1
 */

package model;

/**
 * This class represents a hexadecimal string.
 * 
 * @author Michael Zachary Loria, Ken Romero
 * @version 11.11.19
 */
public class HexadecimalString {
	
	/**
	 * Integer representing maximum bits possible (32).
	 */
	private final static int MAX_BITS = 32;
	
	/**
	 * Integer representing maximum value that can be represented (2^31 - 1).
	 */
	private final static int MAX_VALUE = 2147483647;
	
	/**
	 * Integer representing minimum value that can be represented (-2^31).
	 */
	private final static int MIN_VALUE = -2147483648;
	
	/**
	 * Integer representing the decimal value of the hexadecimal string.
	 */
	private long decimalValue;
	
	/**
	 * Initial decimal value is set to 0.
	 */
	public HexadecimalString() {
		decimalValue = 0;
	}

	/**
	 * Sets the decimal value of the hexadecimal string.
	 * 
	 * @param n The decimal value of the hexadecimal string.
	 * @throws IllegalArgumentException If value cannot be represented with bits available.
	 */
	public void setDecimalValue(long n) {
		if (n < MIN_VALUE || n > MAX_VALUE) {
			throw new IllegalArgumentException("Cannot represent in "
					+ MAX_BITS + " bits.");
		}
		decimalValue = n;
	}
	
	/**
	 * Sets the decimal value of the hexadecimal string without
	 * throwing an exception if it overflows.
	 */
	public void setDecimalValueUnsigned(long n) {
		decimalValue = n;
		long actualValue = Long.parseLong(getFormattedHex(),16);
		decimalValue = actualValue;
	}

	/** 
	 * Get the decimal value of the hexadecimal string.
	 * 
	 * @return The decimal value of the hexadecimal string.
	 */
	public long getDecimalValue() {
		return decimalValue; 
	}
	
	/**
	 * Gets the hexadecimal value of the string formatted properly.
	 * 
	 * @return The formatted string representing the hexadecimal value.
	 */
	public String getFormattedHex() {
		String format = Long.toHexString(decimalValue);
		if(format.length() > 8) {
			format = format.substring(format.length()-8, format.length());
		}
		else if(format.length() < 8) {
			int padLength = 8 - format.length();
			String pad = "0";
			if(decimalValue < 0) {
				pad = "F";
			}
			for(int i = 0; i < padLength; i++) {
				format = pad + format;
			}
		}
		return format.toUpperCase();
	}
	
	@Override
	public String toString() {
		return getFormattedHex();
	}
	
	@Override
	public boolean equals(Object theOther) {
		HexadecimalString temp = (HexadecimalString)theOther;
		return temp.getFormattedHex().equals(this.getFormattedHex());
	}
}

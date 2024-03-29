/*
 * TCSS 372 Computer Architecture
 * Project 1
 */

package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import model.HexadecimalString;

/**
 * This class tests the methods in the
 * HexadecimalString class.
 * 
 * @author Michael Zachary Loria
 * @version November 7 2019
 */
public class HexadecimalStringTest {

	/** The HexadecimalString used for testing. */
	private HexadecimalString myHexString;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		myHexString = new HexadecimalString();
	}

	/**
	 * Test method for {@link HexadecimalString#getDecimalValue()}.
	 */
	@Test
	public void testGetDecimalValue() {
		assertEquals(myHexString.getDecimalValue(), 0);
		
		myHexString.setDecimalValue(2345);
		assertEquals(myHexString.getDecimalValue(), 2345);
		
		myHexString.setDecimalValue(9452);
		assertEquals(myHexString.getDecimalValue(), 9452);
		
		myHexString.setDecimalValue(212);
		assertEquals(myHexString.getDecimalValue(), 212);
		
		myHexString.setDecimalValue(-123);
		assertEquals(myHexString.getDecimalValue(), -123);
		
		myHexString.setDecimalValue(-7832);
		assertEquals(myHexString.getDecimalValue(), -7832);
	}
	
	/**
	 * Test IllegalArgumentException (positive) for {@link model.Computer#setDecimalValue()}.
	 */
	@Test(expected = IllegalArgumentException.class) 
	public void testSetDecimalValueExceptionPositive() {	
		long temp = Long.parseLong("2147483648");
		myHexString.setDecimalValue(temp);
	}
	
	/**
	 * Test IllegalArgumentException (negative) for {@link model.Computer#setDecimalValue()}.
	 */
	@Test(expected = IllegalArgumentException.class) 
	public void testSetDecimalValueExceptionNegative() {	
		long temp = Long.parseLong("-2147483649");
		myHexString.setDecimalValue(temp);
	}

	/**
	 * Test method for {@link HexadecimalString#setDecimalValue(long)}.
	 */
	@Test
	public void testSetDecimalValue() {
		myHexString.setDecimalValue(24);
		assertEquals(myHexString.getDecimalValue(), 24); 
		
		myHexString.setDecimalValue(234);
		assertEquals(myHexString.getDecimalValue(), 234); 
		
		myHexString.setDecimalValue(-500);
		assertEquals(myHexString.getDecimalValue(), -500); 
		
		myHexString.setDecimalValue(2147483647);
		assertEquals(myHexString.getDecimalValue(), 2147483647); 
		
		myHexString.setDecimalValue(-2147483648);
		assertEquals(myHexString.getDecimalValue(), -2147483648); 
	}
	
	/**
	 * Test method for {@link HexadecimalString#setDecimalValueUnsigned(long)}.
	 */
	@Test
	public void testSetDecimalValueUnsigned() {
		myHexString.setDecimalValueUnsigned(24);
		assertEquals(myHexString.getDecimalValue(), 24); 
		
		myHexString.setDecimalValueUnsigned(234);
		assertEquals(myHexString.getDecimalValue(), 234); 
		
		long temp = Long.parseLong("2147483648");
		myHexString.setDecimalValueUnsigned(temp);
		assertEquals(myHexString.getDecimalValue(), Long.parseLong("2147483648")); 
		
		long temp2 = Long.parseLong("-2147483649");
		myHexString.setDecimalValueUnsigned(temp2);
		assertEquals(myHexString.getDecimalValue(), Long.parseLong("2147483647")); 
	}
	
	/**
	 * Test method for {@link HexadecimalString#getFormattedHex()}.
	 */
	@Test
	public void testGetFormattedHex() {
		assertEquals(myHexString.getFormattedHex(), "00000000");
		
		myHexString.setDecimalValue(24);
		assertEquals(myHexString.getFormattedHex(), "00000018");
		
		myHexString.setDecimalValue(43);
		assertEquals(myHexString.getFormattedHex(), "0000002B");
		
		myHexString.setDecimalValue(-20);
		assertEquals(myHexString.getFormattedHex(), "FFFFFFEC");
		
		myHexString.setDecimalValue(-2345);
		assertEquals(myHexString.getFormattedHex(), "FFFFF6D7");
		
		myHexString.setDecimalValue(2345);
		assertEquals(myHexString.getFormattedHex(), "00000929");
		
		myHexString.setDecimalValue(2888);
		assertEquals(myHexString.getFormattedHex(), "00000B48");
	}

}

/*
 * TCSS 372 Computer Architecture
 * Project 1
 */

package tests;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

import model.Computer;
import model.HexadecimalString;
import model.Instruction;

/**
 * This class tests the methods in the
 * Computer class.
 * 
 * @author Michael Zachary Loria
 * @version November 7 2019
 */
public class ComputerTest {
	//todo: TEST EXCEPTION THROW IF OUT OF BOUNDS
	private Computer myComputer;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		myComputer = new Computer();
	}

	/**
	 * Test method for {@link HexadecimalString#assemble()}.
	 */
	@Test
	public void testAssemble() {
		// First Test
		myComputer.assemble(".data\nn:\n .word 15\n.text\r\n" + 
				"	\r\n" + 
				"main:\r\n" + 
				"	lw	$a0,n \r\n" + 
				"	jal fib	\r\n" + 
				"	j 	exit");
		Instruction[] tempInstr = new Instruction[100];
		HexadecimalString[] tempHexStr = new HexadecimalString[100];
		Map<String, Integer> tempMappings = new TreeMap<>();
		
		tempInstr[0] = new Instruction("lw    	$a0,n");
		tempInstr[1] = new Instruction("jal     fib");
		tempInstr[2] = new Instruction("j exit");
		
		tempHexStr[0] = new HexadecimalString(15);
		for(int i = 1; i < 100; i++) {
			tempHexStr[i] = new HexadecimalString();
		}
		
		tempMappings.put("n", 0);
		tempMappings.put("main", 0);

		assertArrayEquals(myComputer.getMemoryDataSegment(), tempHexStr);
		assertArrayEquals(myComputer.getMemoryTextSegment(), tempInstr);
		assertEquals(myComputer.getMaxInstructionIndex(), 2);
		assertEquals(myComputer.getSymbolTable(), tempMappings);
		
		// Second Test
		myComputer.assemble(".data\n"
				+ "n:\n"
				+ " .word 31\n"
				+ "j:\n" 
				+ ".word 234\n"
				+ "value:\n" 
				+ ".word -1223\n"
				+ ".text\n"
				+ "main:\r\n" + 
				"	lw	$a0,n \r\n" + 
				" lw $a1,j \n" +
				" lw $a2,value \n" +
				" addit:\n" +
				"	add $a0,$a1,$a2 \r\n" + 
				"	j 	exit\n" +
				" exit:");
		Instruction[] tempInstr2 = new Instruction[100];
		HexadecimalString[] tempHexStr2 = new HexadecimalString[100];
		Map<String, Integer> tempMappings2 = new TreeMap<>();
		
		tempInstr2[0] = new Instruction("lw    	$a0,n");
		tempInstr2[1] = new Instruction("lw $a1,j");
		tempInstr2[2] = new Instruction("lw $a2,value");
		tempInstr2[3] = new Instruction("add $a0,$a1,$a2");
		tempInstr2[4] = new Instruction("j exit");
		
		tempHexStr2[0] = new HexadecimalString(31);
		tempHexStr2[1] = new HexadecimalString(234);
		tempHexStr2[2] = new HexadecimalString(-1223);
		
		for(int i = 3; i < 100; i++) {
			tempHexStr2[i] = new HexadecimalString();
		}
		
		tempMappings2.put("n", 0);
		tempMappings2.put("j", 1);
		tempMappings2.put("value", 2);
		tempMappings2.put("main", 0);
		tempMappings2.put("addit", 3);
		tempMappings2.put("exit", 5);

		assertArrayEquals(myComputer.getMemoryDataSegment(), tempHexStr2);
		assertArrayEquals(myComputer.getMemoryTextSegment(), tempInstr2);
		assertEquals(myComputer.getMaxInstructionIndex(), 4);
		assertEquals(myComputer.getSymbolTable(), tempMappings2);
	}

	/**
	 * Test method for {@link HexadecimalString#getUnformattedHex()}.
	 */
	@Test
	public void testGetUnformattedHex() {
		/*
		assertEquals(myHexString.getUnformattedHex(), "0");
		
		myHexString.setDecimalValue(24);
		assertEquals(myHexString.getUnformattedHex(), "18");
		
		myHexString.setDecimalValue(43);
		assertEquals(myHexString.getUnformattedHex(), "2b");
		
		myHexString.setDecimalValue(-20);
		assertEquals(myHexString.getUnformattedHex(), "ffffffffffffffec");
		
		myHexString.setDecimalValue(-2345);
		assertEquals(myHexString.getUnformattedHex(), "fffffffffffff6d7"); */
	}

	/**
	 * Test method for {@link HexadecimalString#setDecimalValue(long)}.
	 */
	@Test
	public void testSetDecimalValue() {
		/*
		myHexString.setDecimalValue(24);
		assertEquals(myHexString.getDecimalValue(), 24); 
		
		myHexString.setDecimalValue(234);
		assertEquals(myHexString.getDecimalValue(), 234); 
		
		myHexString.setDecimalValue(-500);
		assertEquals(myHexString.getDecimalValue(), -500); 
		
		myHexString.setDecimalValue(2147483647);
		assertEquals(myHexString.getDecimalValue(), 2147483647); 
		
		myHexString.setDecimalValue(-2147483648);
		assertEquals(myHexString.getDecimalValue(), -2147483648);  */
	}
	
	/**
	 * Test method for {@link HexadecimalString#getFormattedHex()}.
	 */
	@Test
	public void testGetFormattedHex() {
	/*	assertEquals(myHexString.getFormattedHex(), "00000000");
		
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
		assertEquals(myHexString.getFormattedHex(), "00000B48"); */
	}

}

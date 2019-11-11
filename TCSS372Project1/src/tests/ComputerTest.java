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
	
	/** The computer used for testing. */
	private Computer myComputer;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		myComputer = new Computer();
	}

	/**
	 * Test method for {@link model.Computer#Computer()}.
	 */
	@Test
	public void testComputer() {
		assertEquals(myComputer.getMemoryDataIndex(), 0);
		assertEquals(myComputer.getMemoryTextIndex(), 0);
		assertEquals(myComputer.getMaxInstructionIndex(), -1);
		
		Instruction[] tempInstr = new Instruction[100];
		assertArrayEquals(myComputer.getMemoryTextSegment(), tempInstr);
		
		HexadecimalString[] tempHexStr = new HexadecimalString[100];
		for(int i = 0; i < 100; i++) {
			tempHexStr[i] = new HexadecimalString();
		}
		assertArrayEquals(myComputer.getMemoryDataSegment(), tempHexStr);
		
		
		HexadecimalString[] tempReg = new HexadecimalString[32];
		for(int i = 0; i < tempReg.length; i++) {
			tempReg[i] = new HexadecimalString();
		}
		tempReg[28].setDecimalValue(myComputer.getAddressGlobal());
		tempReg[29].setDecimalValue(myComputer.getAddressStack());
		assertArrayEquals(myComputer.getRegisters(), tempReg);

		Map<String, Integer> tempMappings = new TreeMap<>();
		assertEquals(myComputer.getSymbolTable(), tempMappings);
		
		HexadecimalString tempPC = new HexadecimalString();
		tempPC.setDecimalValue(myComputer.getStartingTextAddress());
		assertEquals(myComputer.getPC(), tempPC);

	}

	/**
	 * Test method for {@link model.Computer#getRegisters()}.
	 */
	@Test
	public void testGetRegisters() {
		HexadecimalString[] tempReg = new HexadecimalString[32];
		for(int i = 0; i < tempReg.length; i++) {
			tempReg[i] = new HexadecimalString();
		}
		tempReg[28].setDecimalValue(myComputer.getAddressGlobal());
		tempReg[29].setDecimalValue(myComputer.getAddressStack());
		assertArrayEquals(myComputer.getRegisters(), tempReg);
	}

	/**
	 * Test method for {@link model.Computer#getMemoryTextSegment()}.
	 */
	@Test
	public void testGetMemoryTextSegment() {
		Instruction[] tempInstr = new Instruction[100];
		assertArrayEquals(myComputer.getMemoryTextSegment(), tempInstr);
		
		myComputer.assemble(".data\nn:\n .word 15\n.text\r\n" + 
				"	\r\n" + 
				"main:\r\n" + 
				"	lw	$a0,n \r\n" + 
				"   addi $a0, $a0,1\n" +
				"	jal fib	\r\n" + 
				"	j 	exit");
		
		Instruction[] tempInstr2 = new Instruction[100];
		
		tempInstr2[0] = new Instruction("lw    	$a0,n");
		tempInstr2[1] = new Instruction("   addi $a0, $a0,1");
		tempInstr2[2] = new Instruction("jal     fib");
		tempInstr2[3] = new Instruction("j exit");
	}

	/**
	 * Test method for {@link model.Computer#getMemoryDataSegment()}.
	 */
	@Test
	public void testGetMemoryDataSegment() {
		HexadecimalString[] tempHexStr = new HexadecimalString[100];
		for(int i = 0; i < 100; i++) {
			tempHexStr[i] = new HexadecimalString();
		}
		assertArrayEquals(myComputer.getMemoryDataSegment(), tempHexStr);
		
		// ADD MORE TESTS ONCE YOU TEST SW
	}

	/**
	 * Test method for {@link model.Computer#getPC()}.
	 */
	@Test
	public void testGetPC() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Computer#getMaxInstructionIndex()}.
	 */
	@Test
	public void testGetMaxInstructionIndex() {
		assertEquals(myComputer.getMaxInstructionIndex(), -1);
		myComputer.assemble(
				"lw $a0,n\n" +
						"sw $a0,n\n"
					+	"add $a0,$a0,$a1\n"+
				" exit:");
		
		assertEquals(myComputer.getMaxInstructionIndex(), 2);
		
		myComputer.assemble(".data\n"
				+ "k:\n"
				+ " .word -2309\n"
				+ "m:\n" 
				+ ".word 2345\n"
				+ ".text\n"
				+ "lw $a0, k\n"
				+ "	j 	exit\n" +
				" exit:");
		
		assertEquals(myComputer.getMaxInstructionIndex(), 1);
	}

	/**
	 * Test method for {@link model.Computer#getSymbolTable()}.
	 */
	@Test
	public void testGetSymbolTable() {
		assertEquals(myComputer.getSymbolTable(), new TreeMap<String,Integer>());
		
		myComputer.assemble(".data\n"
				+ "k:\n"
				+ " .word -2309\n"
				+ "m:\n" 
				+ ".word 2345\n"
				+ ".text\n"
				+ "lw $a0, k\n"
				+ "	j 	exit\n" +
				" exit:");
		
		Map<String, Integer> temp = new TreeMap<>();
		temp.put("k", 0);
		temp.put("m", 1);
		temp.put("exit", 2);
		assertEquals(myComputer.getSymbolTable(), temp);
		
		myComputer.assemble(".data\n"
				+ "d:\n"
				+ " .word -2309\n"
				+ "g:\n" 
				+ ".word 2345\n"
				+ "u:\n"
				+ ".word 0"
				+ ".text\n"
				+ " start:\n"
				+ "lw $a0, k\n"
				+ "	j 	exit\n" +
				" exit:");
		
		Map<String, Integer> temp2 = new TreeMap<>();
		temp2.put("d", 0);
		temp2.put("g", 1);
		temp2.put("u", 2);
		temp2.put("start", 0);
		temp2.put("exit", 2);
		assertEquals(myComputer.getSymbolTable(), temp2);	
	}

	/**
	 * Test method for {@link model.Computer#executeOneLine()}.
	 */
	@Test
	public void testExecuteOneLine() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Computer#executeAllLines()}.
	 */
	@Test
	public void testExecuteAllLines() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Computer#add()}.
	 */
	@Test
	public void testAdd() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Computer#addU()}.
	 */
	@Test
	public void testAddU() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Computer#and()}.
	 */
	@Test
	public void testAnd() {
		myComputer.assemble("AND $a0,$gp,$sp");
		myComputer.executeOneLine();
		HexadecimalString temp1 = new HexadecimalString();
		temp1.setDecimalValue(268468224);
		HexadecimalString[] tempReg = myComputer.getRegisters();
		assertEquals(tempReg[4], temp1);
	}

	/**
	 * Test method for {@link model.Computer#or()}.
	 */
	@Test
	public void testOr() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Computer#addI()}.
	 */
	@Test
	public void testAddI() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Computer#addIU()}.
	 */
	@Test
	public void testAddIU() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Computer#andI()}.
	 */
	@Test
	public void testAndI() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Computer#orI()}.
	 */
	@Test
	public void testOrI() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Computer#lw()}.
	 */
	@Test
	public void testLw() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Computer#sw()}.
	 */
	@Test
	public void testSw() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Computer#beq()}.
	 */
	@Test
	public void testBeq() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Computer#bne()}.
	 */
	@Test
	public void testBne() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Computer#j()}.
	 */
	@Test
	public void testJ() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Computer#jr()}.
	 */
	@Test
	public void testJr() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.Computer#assemble(java.lang.String)}.
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
		
		// Third Test
		myComputer.assemble( 
				"	addi	$a0,$zero,2 \r\n" + 
				" addi	$a1,$zero,4 \n" +
				" addi	$a2,$zero,5 \n" +
				"	j 	exit\n" +
				" exit:");
		Instruction[] tempInstr3 = new Instruction[100];
		HexadecimalString[] tempHexStr3 = new HexadecimalString[100];
		Map<String, Integer> tempMappings3 = new TreeMap<>();
		
		tempInstr3[0] = new Instruction("addi $a0,$zero,2");
		tempInstr3[1] = new Instruction("addi $a1,$zero,4");
		tempInstr3[2] = new Instruction("addi $a2,$zero,5");
		tempInstr3[3] = new Instruction("j exit");
		
		for(int i = 0; i < 100; i++) {
			tempHexStr3[i] = new HexadecimalString();
		}
		
		tempMappings3.put("exit", 4);

		assertArrayEquals(myComputer.getMemoryDataSegment(), tempHexStr3);
		assertArrayEquals(myComputer.getMemoryTextSegment(), tempInstr3);
		assertEquals(myComputer.getMaxInstructionIndex(), 3);
		assertEquals(myComputer.getSymbolTable(), tempMappings3);
		
		// Four Test
		myComputer.assemble(".data\n"
				+ "k:\n"
				+ " .word -2309\n"
				+ "m:\n" 
				+ ".word 2345\n"
				+ "chicken:\n" 
				+ ".word -1\n"
				+ ".text\n"
				+ "	j 	exit\n" +
				" exit:");
		Instruction[] tempInstr4 = new Instruction[100];
		HexadecimalString[] tempHexStr4 = new HexadecimalString[100];
		Map<String, Integer> tempMappings4 = new TreeMap<>();
		
		tempInstr4[0] = new Instruction("j exit");
		
		tempHexStr4[0] = new HexadecimalString(-2309);
		tempHexStr4[1] = new HexadecimalString(2345);
		tempHexStr4[2] = new HexadecimalString(-1);
		
		for(int i = 3; i < 100; i++) {
			tempHexStr4[i] = new HexadecimalString();
		}
		
		tempMappings4.put("k", 0);
		tempMappings4.put("m", 1);
		tempMappings4.put("chicken", 2);
		tempMappings4.put("exit", 1);

		assertArrayEquals(myComputer.getMemoryDataSegment(), tempHexStr4);
		assertArrayEquals(myComputer.getMemoryTextSegment(), tempInstr4);
		assertEquals(myComputer.getMaxInstructionIndex(), 0);
		assertEquals(myComputer.getSymbolTable(), tempMappings4);
	}
	
	/**
	 * Test method for {@link model.Computer#getMemoryDataIndex()}.
	 */
	@Test
	public void testGetMemoryDataIndex() {
		assertEquals(myComputer.getMemoryDataIndex(), 0);
		
		myComputer.assemble(".data\n"
				+ "k:\n"
				+ " .word -2309\n"
				+ "m:\n" 
				+ ".word 2345\n"
				+ "chicken:\n" 
				+ ".word -1\n"
				+ ".text\n"
				+ "	j 	exit\n" +
				" exit:");
		
		assertEquals(myComputer.getMemoryDataIndex(), 3);
	}
	
	/**
	 * Test method for {@link model.Computer#getMemoryTextIndex()}.
	 */
	@Test
	public void testGetMemoryTextIndex() {
		assertEquals(myComputer.getMemoryTextIndex(), 0);
		
		myComputer.assemble(".data\n"
				+ "k:\n"
				+ " .word -2309\n"
				+ "m:\n" 
				+ ".word 2345\n"
				+ "chicken:\n" 
				+ ".word -1\n"
				+ ".text\n"
				+ "	j 	exit\n" +
				" exit:");
		
		assertEquals(myComputer.getMemoryTextIndex(), 1);
		
		myComputer.assemble(
				"add $a0,$a1,$a2\n" +
				"addi $a0,$a1,5\n" +
				"	j 	exit\n" +
				" exit:");
		
		assertEquals(myComputer.getMemoryTextIndex(), 3);
	}
	
	/**
	 * Test method for {@link model.Computer#getStartingTextAddress()}.
	 */
	@Test
	public void testGetStartingTextAddress() {
		assertEquals(myComputer.getStartingTextAddress(), 4194304);
	}
	
	/**
	 * Test method for {@link model.Computer#getAddressGlobal()}.
	 */
	@Test
	public void testGetAddressGlobal() {
		assertEquals(myComputer.getAddressGlobal(), 268468224);
	}
	
	/**
	 * Test method for {@link model.Computer#getAddressStack()}.
	 */
	@Test
	public void testGetAddressStack() {
		assertEquals(myComputer.getAddressStack(), 2147479548);
	}

}

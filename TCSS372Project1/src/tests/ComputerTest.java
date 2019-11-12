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
 * @author Michael Zachary Loria, Ken Romero
 * @version 11.11.19
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
	
	@Test
	public void testAdd() {
		myComputer.assemble("ADD $t0,$t0,$t0");
		myComputer.executeOneLine();
		assertEquals(myComputer.getRegisters()[8].getDecimalValue(), 0);
		myComputer.assemble("ADDI $t0,$t0,7");
		myComputer.assemble("ADDI $t0,$t0,$t0");
		myComputer.executeOneLine();
		myComputer.executeOneLine();
		assertEquals(myComputer.getRegisters()[8].getDecimalValue(), 7);
	}
	
	@Test
	public void testAddI() {
		myComputer.assemble("ADDI $t0,$t0,5");
		myComputer.executeOneLine();
		assertEquals(myComputer.getRegisters()[8].getDecimalValue(), 5);
	}
	
	@Test
	public void testOr() {
		myComputer.assemble("ADDI $t0,$t0,1");
		myComputer.executeOneLine();
		myComputer.assemble("OR $t0,$t0,$t0");
		myComputer.executeOneLine();
		assertEquals(myComputer.getRegisters()[8].getDecimalValue(), 1);
	}
	
	@Test
	public void testOrI() {
		myComputer.assemble("ORI $t0,$t0,1");
		myComputer.executeOneLine();
		assertEquals(myComputer.getRegisters()[8].getDecimalValue(), 1);
	}
	
	@Test
	public void testAndI() {
		myComputer.assemble("ANDI $t0,$t0,1");
		myComputer.executeOneLine();
		assertEquals(myComputer.getRegisters()[8].getDecimalValue(), 1);
	}
	
	@Test
	public void testAnd() {
		myComputer.assemble("ADDI $t0,$t0,1");
		myComputer.executeOneLine();
		myComputer.assemble("AND $t0,$t0,$t0");
		myComputer.executeOneLine();
		assertEquals(myComputer.getRegisters()[8].getDecimalValue(), 0);
	}
	
//	@Test
//	public void testBeq() {
//		myComputer.assemble("BEQ $t0,$t1,\n"
//				+ "			ADDI $t0,$t0,1");
//		myComputer.executeOneLine();
//		assertEquals(myComputer.getRegisters()[8].getDecimalValue(), 0);
//	}

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
		
		// ADD MORE TESTS HERE ONCE YOU FINISH SW
	}

	/**
	 * Test method for {@link model.Computer#getPC()}.
	 */
	@Test
	public void testGetPC() {
		myComputer.assemble("AND $a1,$a0,$sp");
		myComputer.executeOneLine();
		assertEquals(myComputer.getPC().getDecimalValue(),4194308);
		
		myComputer.assemble("ADDI $a0,$zero,11\n"
				+ "ADDI $a1,$zero,4\n"
				+ "OR	$a2,$a0,$a1");
		myComputer.executeOneLine();
		assertEquals(myComputer.getPC().getDecimalValue(),4194308);
		myComputer.executeOneLine();
		assertEquals(myComputer.getPC().getDecimalValue(),4194312);
		myComputer.executeOneLine();
		assertEquals(myComputer.getPC().getDecimalValue(),4194316);
		
		myComputer.assemble("ADDI $a0,$zero,11\n"
				+ "ADDI $a1,$zero,4\n"
				+ "OR	$a2,$a0,$a1\n"
				+ "J 	exit\n"
				+ "EXIT:");
		myComputer.executeAllLines();
		assertEquals(myComputer.getPC().getDecimalValue(),4194320);
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
		temp.put("K", 0);
		temp.put("M", 1);
		temp.put("EXIT", 2);
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
		temp2.put("D", 0);
		temp2.put("G", 1);
		temp2.put("U", 2);
		temp2.put("START", 0);
		temp2.put("EXIT", 2);
		assertEquals(myComputer.getSymbolTable(), temp2);	
	}

	/**
	 * Test method for {@link model.Computer#executeOneLine()}.
	 */
	@Test
	public void testExecuteOneLine() {
		myComputer.assemble("AND $a1,$a0,$sp");
		myComputer.executeOneLine();
		HexadecimalString temp1 = new HexadecimalString();
		temp1.setDecimalValue(0);
		HexadecimalString[] tempReg1 = myComputer.getRegisters();
		assertEquals(tempReg1[5], temp1);
		assertEquals(myComputer.getPC().getDecimalValue(),4194308);
		
		myComputer.assemble("ADDI $a0,$zero,11\n"
				+ "ADDI $a1,$zero,4\n"
				+ "OR	$a2,$a0,$a1");
		myComputer.executeOneLine();
		assertEquals(myComputer.getPC().getDecimalValue(),4194308);
		myComputer.executeOneLine();
		assertEquals(myComputer.getPC().getDecimalValue(),4194312);
		myComputer.executeOneLine();
		assertEquals(myComputer.getPC().getDecimalValue(),4194316);
		HexadecimalString temp2 = new HexadecimalString();
		temp2.setDecimalValue(15);
		HexadecimalString[] tempReg2 = myComputer.getRegisters();
		assertEquals(tempReg2[6], temp2);
	}

	/**
	 * Test method for {@link model.Computer#executeAllLines()}.
	 */
	@Test
	public void testExecuteAllLines() {
		myComputer.assemble("ADDI $a0,$zero,11\n"
				+ "BEQ	$a0,$zero,exit\n"
				+ "GOOD:\n"
				+ "ADDI $a1,$zero,4\n"
				+ "OR	$a2,$a0,$a1\n"
				+ "EXIT: ");
		myComputer.executeAllLines();
		HexadecimalString temp1 = new HexadecimalString();
		temp1.setDecimalValue(11);
		HexadecimalString temp2 = new HexadecimalString();
		temp2.setDecimalValue(4);
		HexadecimalString temp3 = new HexadecimalString();
		temp3.setDecimalValue(15);
		HexadecimalString[] tempReg1 = myComputer.getRegisters();
		assertEquals(tempReg1[4], temp1);
		assertEquals(tempReg1[5], temp2);
		assertEquals(tempReg1[6], temp3);
		assertEquals(myComputer.getPC().getDecimalValue(), 4194320);
	}

	/**
	 * Test method for {@link model.Computer#add()}.
	 */
	@Test
	public void testAdd() {
		myComputer.assemble("ADDI $a0,$a0,2\n"
				+ "ADD $a0,$zero,$a0");
		myComputer.executeAllLines();
		HexadecimalString temp1 = new HexadecimalString();
		temp1.setDecimalValue(2);
		HexadecimalString[] tempReg1 = myComputer.getRegisters();
		assertEquals(tempReg1[4], temp1);
		
		myComputer.assemble("ADDI $a1,$zero,2\n"
				+ "ADDI $a3,$zero,400\n"
				+ "ADD $v0,$a3,$a1");
		myComputer.executeAllLines();
		HexadecimalString temp2 = new HexadecimalString();
		temp2.setDecimalValue(402);
		HexadecimalString[] tempReg2 = myComputer.getRegisters();
		assertEquals(tempReg2[2], temp2);
		
		myComputer.assemble("ADDI $a1,$zero,4\n"
				+ "ADD $sp,$sp,$a1");
		myComputer.executeAllLines();
		HexadecimalString temp3 = new HexadecimalString();
		temp3.setDecimalValue(2147479552);
		HexadecimalString[] tempReg3 = myComputer.getRegisters();
		assertEquals(tempReg3[29], temp3);
	}

	/**
	 * Test method for {@link model.Computer#addU()}.
	 */
	@Test
	public void testAddU() {
		myComputer.assemble("ADDI $a0,$a0,25\n"
				+ "ADDU $a0,$zero,$a0");
		myComputer.executeAllLines();
		HexadecimalString temp1 = new HexadecimalString();
		temp1.setDecimalValue(25);
		HexadecimalString[] tempReg1 = myComputer.getRegisters();
		assertEquals(tempReg1[4], temp1);
		
		myComputer.assemble("ADDI $a1,$zero,270\n"
				+ "ADDI $a3,$zero,320\n"
				+ "ADDU $v0,$a3,$a1");
		myComputer.executeAllLines();
		HexadecimalString temp2 = new HexadecimalString();
		temp2.setDecimalValue(590);
		HexadecimalString[] tempReg2 = myComputer.getRegisters();
		assertEquals(tempReg2[2], temp2);
		
		myComputer.assemble("ADDI $a1,$zero,-2147483648\n"
				+ "ADDI $a2,$zero,-2\n"
				+ "ADDU $v0,$a1,$a2");
		myComputer.executeAllLines();
		HexadecimalString temp3 = new HexadecimalString();
		temp3.setDecimalValue(2147483646);
		HexadecimalString[] tempReg3 = myComputer.getRegisters();
		assertEquals(tempReg3[2], temp3);
		
		myComputer.assemble("ADDI $a1,$zero,2147483647\n"
				+ "ADDI $a2,$zero,2\n"
				+ "ADDU $a3,$a1,$a2");
		myComputer.executeAllLines();
		HexadecimalString temp4 = new HexadecimalString();
		temp4.setDecimalValue(-2147483647);
		HexadecimalString[] tempReg4 = myComputer.getRegisters();
		assertEquals(tempReg4[7], temp4);
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
		
		myComputer.assemble("AND $gp,$zero,$sp");
		myComputer.executeOneLine();
		HexadecimalString temp2 = new HexadecimalString();
		temp1.setDecimalValue(0);
		HexadecimalString[] tempReg2 = myComputer.getRegisters();
		assertEquals(tempReg2[28], temp2);
		
		myComputer.assemble("AND $a1,$a0,$sp");
		myComputer.executeOneLine();
		HexadecimalString temp3 = new HexadecimalString();
		temp3.setDecimalValue(0);
		HexadecimalString[] tempReg3 = myComputer.getRegisters();
		assertEquals(tempReg3[5], temp3);
		
		myComputer.assemble("ADDI $a0,$zero,11\n"
				+ "ADDI $a1,$zero,12\n"
				+ "AND	$a2,$a0,$a1");
		myComputer.executeAllLines();
		HexadecimalString temp4 = new HexadecimalString();
		temp4.setDecimalValue(8);
		HexadecimalString[] tempReg4 = myComputer.getRegisters();
		assertEquals(tempReg4[6], temp4);
	}

	/**
	 * Test method for {@link model.Computer#or()}.
	 */
	@Test
	public void testOr() {
		myComputer.assemble("OR $a0,$gp,$sp");
		myComputer.executeOneLine();
		HexadecimalString temp1 = new HexadecimalString();
		temp1.setDecimalValue(2147479548);
		HexadecimalString[] tempReg = myComputer.getRegisters();
		assertEquals(tempReg[4], temp1);
		
		myComputer.assemble("OR $a1,$zero,$t0");
		myComputer.executeOneLine();
		HexadecimalString temp2 = new HexadecimalString();
		temp1.setDecimalValue(0);
		HexadecimalString[] tempReg2 = myComputer.getRegisters();
		assertEquals(tempReg2[5], temp2);
		
		myComputer.assemble("ADDI $a0,$zero,11\n"
				+ "ADDI $a1,$zero,4\n"
				+ "OR	$a2,$a0,$a1");
		myComputer.executeAllLines();
		HexadecimalString temp3 = new HexadecimalString();
		temp3.setDecimalValue(15);
		HexadecimalString[] tempReg3 = myComputer.getRegisters();
		assertEquals(tempReg3[6], temp3);
	}

	/**
	 * Test method for {@link model.Computer#addI()}.
	 */
	@Test
	public void testAddI() {
		myComputer.assemble("ADDI $a0,$a0,2");
		myComputer.executeOneLine();
		HexadecimalString temp1 = new HexadecimalString();
		temp1.setDecimalValue(2);
		HexadecimalString[] tempReg1 = myComputer.getRegisters();
		assertEquals(tempReg1[4], temp1);
		
		myComputer.assemble("ADDI $a3,$zero,400");
		myComputer.executeOneLine();
		HexadecimalString temp2 = new HexadecimalString();
		temp2.setDecimalValue(400);
		HexadecimalString[] tempReg2 = myComputer.getRegisters();
		assertEquals(tempReg2[7], temp2);
		
		myComputer.assemble("ADDI $sp,$sp,8");
		myComputer.executeOneLine();
		HexadecimalString temp3 = new HexadecimalString();
		temp3.setDecimalValue(2147479556);
		HexadecimalString[] tempReg3 = myComputer.getRegisters();
		assertEquals(tempReg3[29], temp3);
		
		myComputer.assemble("ADDI $t0,$zero,25\nADDI $v0,$t0,-200");
		myComputer.executeOneLine();
		HexadecimalString temp4 = new HexadecimalString();
		temp4.setDecimalValue(25);
		myComputer.executeOneLine();
		HexadecimalString temp5 = new HexadecimalString();
		temp5.setDecimalValue(-175);
		HexadecimalString[] tempReg4 = myComputer.getRegisters();
		assertEquals(tempReg4[8], temp4);
		assertEquals(tempReg4[2], temp5);
	}	

	/**
	 * Test method for {@link model.Computer#addIU()}.
	 */
	@Test
	public void testAddIU() {
		myComputer.assemble("ADDIU $a0,$a0,-2147483648\n"
				+ "ADDIU $a0,$a0,-1");
		myComputer.executeOneLine();
		myComputer.executeOneLine();
		
		HexadecimalString temp1 = new HexadecimalString();
		temp1.setDecimalValue(2147483647);
		HexadecimalString[] tempReg1 = myComputer.getRegisters();
		assertEquals(tempReg1[4], temp1);
		
		myComputer.assemble("ADDIU $a0,$a0,2");
		myComputer.executeOneLine();
		HexadecimalString temp2 = new HexadecimalString();
		temp2.setDecimalValue(2);
		HexadecimalString[] tempReg2 = myComputer.getRegisters();
		assertEquals(tempReg2[4], temp2);
		
		myComputer.assemble("ADDIU $a3,$zero,400");
		myComputer.executeOneLine();
		HexadecimalString temp3 = new HexadecimalString();
		temp3.setDecimalValue(400);
		HexadecimalString[] tempReg3 = myComputer.getRegisters();
		assertEquals(tempReg3[7], temp3);
		
		myComputer.assemble("ADDIU $a0,$a0,2147483647\n"
				+ "ADDIU $a0,$a0,1");
		myComputer.executeOneLine();
		myComputer.executeOneLine();
		
		HexadecimalString temp4 = new HexadecimalString();
		temp4.setDecimalValue(-2147483648);
		HexadecimalString[] tempReg4 = myComputer.getRegisters();
		assertEquals(tempReg4[4], temp4);
	}

	/**
	 * Test method for {@link model.Computer#andI()}.
	 */
	@Test
	public void testAndI() {
		myComputer.assemble("ANDI $a0,$a0,200");
		myComputer.executeOneLine();
		HexadecimalString temp1 = new HexadecimalString();
		temp1.setDecimalValue(0);
		HexadecimalString[] tempReg1 = myComputer.getRegisters();
		assertEquals(tempReg1[4], temp1);
		
		myComputer.assemble("ADDI $a3,$zero,400\n"
				+ "ANDI $a3,$a3,200");
		myComputer.executeOneLine();
		myComputer.executeOneLine();
		HexadecimalString temp2 = new HexadecimalString();
		temp2.setDecimalValue(128);
		HexadecimalString[] tempReg2 = myComputer.getRegisters();
		assertEquals(tempReg2[7], temp2);
		
		myComputer.assemble("ADDI $t0,$zero,25\n"
				+ "ANDI $v0,$t0,3999");
		myComputer.executeOneLine();
		myComputer.executeOneLine();
		HexadecimalString temp4 = new HexadecimalString();
		temp4.setDecimalValue(25);
		HexadecimalString[] tempReg4 = myComputer.getRegisters();
		assertEquals(tempReg4[2], temp4);
	}

	/**
	 * Test method for {@link model.Computer#orI()}.
	 */
	@Test
	public void testOrI() {
		myComputer.assemble("ORI $a0,$a0,200");
		myComputer.executeOneLine();
		HexadecimalString temp1 = new HexadecimalString();
		temp1.setDecimalValue(200);
		HexadecimalString[] tempReg1 = myComputer.getRegisters();
		assertEquals(tempReg1[4], temp1);
		
		myComputer.assemble("ADDI $a3,$zero,400\n"
				+ "ORI $a3,$a3,200");
		myComputer.executeOneLine();
		myComputer.executeOneLine();
		HexadecimalString temp2 = new HexadecimalString();
		temp2.setDecimalValue(472);
		HexadecimalString[] tempReg2 = myComputer.getRegisters();
		assertEquals(tempReg2[7], temp2);
		
		myComputer.assemble("ADDI $t0,$zero,25\n"
				+ "ORI $v0,$t0,3999");
		myComputer.executeOneLine();
		myComputer.executeOneLine();
		HexadecimalString temp4 = new HexadecimalString();
		temp4.setDecimalValue(3999);
		HexadecimalString[] tempReg4 = myComputer.getRegisters();
		assertEquals(tempReg4[2], temp4);
	}

	/**
	 * Test method for {@link model.Computer#lw()}.
	 */
	@Test
	public void testLw() {
		// Label Test
		myComputer.assemble(".data\n"
		  		+ "n:\n"
		  		+ ".word 1738\n" 
		  		+ ".text\n"
		  		+ "lw $a0,n");
		myComputer.executeOneLine();
		HexadecimalString temp1 = new HexadecimalString();
		temp1.setDecimalValue(1738);
		HexadecimalString[] tempReg1 = myComputer.getRegisters();
        assertEquals(tempReg1[4], temp1);
        
		myComputer.assemble(".data\n"
		  		+ "d:\n"
		  		+ ".word 2349\n"
		  		+ "e:\n"
		  		+ ".word -123\n"
		  		+ "n:\n"
		  		+ ".word -45\n"
		  		+ ".text\n"
		  		+ "lw $a1,e\n"
		  		+ "lw $a2,d");
		myComputer.executeOneLine();
		myComputer.executeOneLine();
		HexadecimalString temp2 = new HexadecimalString();
		temp2.setDecimalValue(-123);
		HexadecimalString temp3 = new HexadecimalString();
		temp3.setDecimalValue(2349);
		HexadecimalString[] tempReg2 = myComputer.getRegisters();
		assertEquals(tempReg2[5], temp2);
		assertEquals(tempReg2[6], temp3);
		
		// Address with Offset Test in Stack
		myComputer.assemble("addi $a0,$zero,5\n"
		  		+ "sw $a0,0($sp)\n"
		  		+ "lw $a1,0($sp)");
		myComputer.executeAllLines();
		HexadecimalString temp4 = new HexadecimalString();
		temp4.setDecimalValue(5);
		HexadecimalString[] tempReg3 = myComputer.getRegisters();
		assertEquals(tempReg3[5], temp4);
		
		myComputer.assemble("addi $a0,$zero,5\n"
		  		+ "sw $a0,0($sp)\n"
				+ "addi $a1,$a0,234\n"
				+ "sw $a1,4($sp)\n"
				+ "addi $a1,$a0,23\n"
		  		+ "lw $a1,4($sp)");
		myComputer.executeAllLines();
		HexadecimalString temp5 = new HexadecimalString();
		temp5.setDecimalValue(239);
		HexadecimalString[] tempReg4 = myComputer.getRegisters();
		assertEquals(tempReg4[5], temp5);
		
		// Address with Offset Test in NonStack
		myComputer.assemble(".data\n"
		  		+ "addr:\n"
		  		+ ".word 26850100\n"
		  		+ "e:\n"
		  		+ ".word -2\n"
		  		+ "this:\n"
		  		+ ".word -33\n"
		  		+ ".text\n"
		  		+ "lw $a0,addr\n"
		  		+ "lw $a1,0($a0)\n"
		  		+ "lw $a2,-4($a0)");
		myComputer.executeAllLines();
		HexadecimalString temp6 = new HexadecimalString();
		temp6.setDecimalValue(26850100);
		HexadecimalString temp7 = new HexadecimalString();
		temp7.setDecimalValue(-33);
		HexadecimalString temp8 = new HexadecimalString();
		temp8.setDecimalValue(-2);
		HexadecimalString[] tempReg5 = myComputer.getRegisters();
		assertEquals(tempReg5[4], temp6);
		assertEquals(tempReg5[5], temp7);
		assertEquals(tempReg5[6], temp8);
		
		myComputer.assemble(".data\n"
		  		+ "addr:\n"
		  		+ ".word 26850092\n"
		  		+ "e:\n"
		  		+ ".word -2\n"
		  		+ "this:\n"
		  		+ ".word -33\n"
		  		+ "that:\n"
		  		+ ".word -323\n"
		  		+ "there:\n"
		  		+ ".word 99\n"
		  		+ ".text\n"
		  		+ "lw $a0,addr\n"
		  		+ "lw $a1,16($a0)\n"
		  		+ "lw $a2,4($a0)");
		myComputer.executeAllLines();
		HexadecimalString temp9 = new HexadecimalString();
		temp9.setDecimalValue(26850092);
		HexadecimalString temp10 = new HexadecimalString();
		temp10.setDecimalValue(99);
		HexadecimalString temp11 = new HexadecimalString();
		temp11.setDecimalValue(-2);
		HexadecimalString[] tempReg6 = myComputer.getRegisters();
		assertEquals(tempReg6[4], temp9);
		assertEquals(tempReg6[5], temp10);
		assertEquals(tempReg6[6], temp11);
	}

	/**
	 * Test method for {@link model.Computer#sw()}.
	 */
	@Test
	public void testSw() {
		// Label Test
		myComputer.assemble(".data\n"
		  		+ "prod:\n"
		  		+ ".word 2\n" 
		  		+ ".text\n"
		  		+ "addi $a0,$zero,100\n"
		  		+ "sw $a0,prod\n"
		  		+ "addi $a0,$zero,1");
		myComputer.executeAllLines();
		HexadecimalString temp1 = new HexadecimalString();
		temp1.setDecimalValue(100);
		HexadecimalString[] tempMem = myComputer.getMemoryDataSegment();
        assertEquals(tempMem[0], temp1);
        
		myComputer.assemble(".data\n"
		  		+ "d:\n"
		  		+ ".word 99\n" 
		  		+ "e:\n"
		  		+ ".word 0\n" 
		  		+ "z:\n"
		  		+ ".word 13\n" 
		  		+ ".text\n"
		  		+ "addi $a1,$zero,19\n"
		  		+ "sw $a1,z\n"
		  		+ "lw $a2,d\n"
		  		+ "addi $a2,$a2,100\n"
		  		+ "sw $a2,e\n");
		myComputer.executeAllLines();
		HexadecimalString temp2 = new HexadecimalString();
		temp2.setDecimalValue(19);
		HexadecimalString temp3 = new HexadecimalString();
		temp3.setDecimalValue(199);
		HexadecimalString[] tempMem2 = myComputer.getMemoryDataSegment();
		assertEquals(tempMem2[2], temp2);
		assertEquals(tempMem2[1], temp3);
		
		// Address with Offset Test in Stack
		myComputer.assemble("addi $a1,$zero,19\n"
		  		+ "sw $a1,0($sp)\n"
		  		+ "addi $a2,$zero,100\n"
		  		+ "sw $a2,4($sp)\n");
		myComputer.executeAllLines();
		HexadecimalString temp4 = new HexadecimalString();
		temp4.setDecimalValue(19);
		HexadecimalString temp5 = new HexadecimalString();
		temp5.setDecimalValue(100);
		HexadecimalString[] tempMem3 = myComputer.getMemoryDataSegment();
		assertEquals(tempMem3[50], temp4);
		assertEquals(tempMem3[51], temp5);
		
		myComputer.assemble("addi $a1,$zero,190\n"
		  		+ "sw $a1,100($sp)\n");
		myComputer.executeAllLines();
		HexadecimalString temp6 = new HexadecimalString();
		temp6.setDecimalValue(190);
		HexadecimalString[] tempMem4 = myComputer.getMemoryDataSegment();
		assertEquals(tempMem4[75], temp6);
		
		// Address with Offset Test in NonStack
		myComputer.assemble(".data\n"
		  		+ "addr:\n"
		  		+ ".word 26850092\n"
		  		+ ".text\n"
		  		+ "lw $a0,addr\n"
		  		+ "addi $a1,$zero,88\n"
		  		+ "sw $a1,4($a0)\n");
		myComputer.executeAllLines();
		HexadecimalString temp7 = new HexadecimalString();
		temp7.setDecimalValue(26850092);
		HexadecimalString temp8 = new HexadecimalString();
		temp8.setDecimalValue(88);
		HexadecimalString[] tempMem5 = myComputer.getMemoryDataSegment();
		assertEquals(tempMem5[0], temp7);
		assertEquals(tempMem5[1], temp8);
		
		myComputer.assemble(".data\n"
		  		+ "addr:\n"
		  		+ ".word 26850092\n"
		  		+ ".text\n"
		  		+ "lw $a0,addr\n"
		  		+ "addi $a1,$zero,123\n"
		  		+ "sw $a1,4($a0)\n"
		  		+ "addi $a1,$a1,77\n"
		  		+ "sw $a1,12($a0)\n");
		myComputer.executeAllLines();
		HexadecimalString temp10 = new HexadecimalString();
		temp10.setDecimalValue(123);
		HexadecimalString temp11 = new HexadecimalString();
		temp11.setDecimalValue(200);
		HexadecimalString[] tempMem6 = myComputer.getMemoryDataSegment();
		assertEquals(tempMem6[1], temp10);
		assertEquals(tempMem6[3], temp11);
	}

	/**
	 * Test method for {@link model.Computer#beq()}.
	 */
	@Test
	public void testBeq() {
		myComputer.assemble("ADDI $A0,$zero,0\n"
				+ "BEQ $a0,$zero,END\n"
				+ "ADDI $A0,$a0,250\n"
				+ "ADDI $A0,$A0,50\n"
				+ "END:\n"
				+ "ADDI $A0,$A0,1");
		myComputer.executeAllLines();
		HexadecimalString temp1 = new HexadecimalString();
		temp1.setDecimalValue(1);
		HexadecimalString[] tempReg1 = myComputer.getRegisters();
        assertEquals(tempReg1[4], temp1);
        
		myComputer.assemble("ADDI $A0,$zero,0\n"
				+ "BEQ $a0,$zero,END\n"
				+ "CHICKEN:\n"
				+ "ADDI $A0,$a0,250\n"
				+ "ADDI $A0,$A0,50\n"
				+ "END:\n"
				+ "ADDI $A0,$a0,1\n"
				+ "ADDI $A1,$a1,1\n"
				+ "BEQ $A1,$A0,CHICKEN");
		myComputer.executeAllLines();
		HexadecimalString temp2 = new HexadecimalString();
		temp2.setDecimalValue(302);
		HexadecimalString temp3 = new HexadecimalString();
		temp3.setDecimalValue(2);
		HexadecimalString[] tempReg2 = myComputer.getRegisters();
        assertEquals(tempReg2[4], temp2);
        assertEquals(tempReg2[5],temp3);
	}

	/**
	 * Test method for {@link model.Computer#bne()}.
	 */
	@Test
	public void testBne() {
		myComputer.assemble("ADDI $A0,$zero,0\n"
				+ "BNE $sp,$a0,END\n"
				+ "ADDI $A0,$a0,250\n"
				+ "ADDI $A0,$A0,50\n"
				+ "END:\n"
				+ "ADDI $A0,$A0,47");
		myComputer.executeAllLines();
		HexadecimalString temp1 = new HexadecimalString();
		temp1.setDecimalValue(47);
		HexadecimalString[] tempReg1 = myComputer.getRegisters();
        assertEquals(tempReg1[4], temp1);
        
		myComputer.assemble("ADDI $A0,$zero,0\n"
				+ "BNE $a0,$zero,END\n"
				+ "ADDI $A0,$a0,250\n"
				+ "ADDI $A0,$A0,50\n"
				+ "ADDI $A0,$a0,1\n"
				+ "ADDI $A1,$a1,1\n");
		myComputer.executeAllLines();
		HexadecimalString temp2 = new HexadecimalString();
		temp2.setDecimalValue(301);
		HexadecimalString temp3 = new HexadecimalString();
		temp3.setDecimalValue(1);
		HexadecimalString[] tempReg2 = myComputer.getRegisters();
        assertEquals(tempReg2[4], temp2);
        assertEquals(tempReg2[5],temp3);
	}

	/**
	 * Test method for {@link model.Computer#j()}.
	 */
	@Test
	public void testJ() {
		myComputer.assemble("ADDI $A0,$zero,0\n"
				+ "J REC\n"
				+ "ADDI $A0,$a0,250\n"
				+ "ADDI $A0,$A0,50\n"
				+ "REC:\n"
				+ "ADDI $A0,$A0,999");
		myComputer.executeAllLines();
		HexadecimalString temp1 = new HexadecimalString();
		temp1.setDecimalValue(999);
		HexadecimalString[] tempReg1 = myComputer.getRegisters();
        assertEquals(tempReg1[4], temp1);
        
		myComputer.assemble("ADDI $A0,$zero,0\n"
				+ "J KEN\n"
				+ "ADDI $A0,$a0,250\n"
				+ "MIC:\n"
				+ "ADDI $A0,$A0,50\n"
				+ "J EXIT\n"
				+ "KEN:\n"
				+ "ADDI $A0,$A0,550\n"
				+ "J MIC\n" 
				+ "EXIT:");
		myComputer.executeAllLines();
		HexadecimalString temp2 = new HexadecimalString();
		temp2.setDecimalValue(600);
		HexadecimalString[] tempReg2 = myComputer.getRegisters();
        assertEquals(tempReg2[4], temp2);
        
		myComputer.assemble("ADDI $A0,$zero,0\n"
				+ "J REC\n"
				+ "FIRST:\n"
				+ "ADDI $A0,$a0,250\n"
				+ "J EXIT\n"
				+ "REC:\n"
				+ "ADDI $A0,$A0,30\n"
				+ "J FIRST\n"
				+ "EXIT:");
		myComputer.executeAllLines();
		HexadecimalString temp3 = new HexadecimalString();
		temp3.setDecimalValue(280);
		HexadecimalString[] tempReg3 = myComputer.getRegisters();
        assertEquals(tempReg3[4], temp3);
	}

	/**
	 * Test method for {@link model.Computer#jr()}.
	 */
	@Test
	public void testJr() {
		myComputer.assemble(".data\n"
		  		+ "n:\n"
		  		+ ".word 4194312\n" 
		  		+ ".text\n"
		  		+ "lw $ra,n\n"
				+ "J EXIT\n"
				+ "ADDI $A0,$zero,250\n"
				+ "J END\n"
				+ "EXIT:\n"
				+ "JR $RA\n"
				+ "END: ");

		myComputer.executeAllLines();
		HexadecimalString temp1 = new HexadecimalString();
		temp1.setDecimalValue(250);
		HexadecimalString[] tempReg1 = myComputer.getRegisters();
        assertEquals(tempReg1[4], temp1);
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
		
		tempHexStr[0] = new HexadecimalString();
		tempHexStr[0].setDecimalValue(15);
		for(int i = 1; i < 100; i++) {
			tempHexStr[i] = new HexadecimalString();
		}
		
		tempMappings.put("N", 0);
		tempMappings.put("MAIN", 0);

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
		
		tempHexStr2[0] = new HexadecimalString();
		tempHexStr2[0].setDecimalValue(31);
		tempHexStr2[1] = new HexadecimalString();
		tempHexStr2[1].setDecimalValue(234);
		tempHexStr2[2] = new HexadecimalString();
		tempHexStr2[2].setDecimalValue(-1223);
		
		for(int i = 3; i < 100; i++) {
			tempHexStr2[i] = new HexadecimalString();
		}
		
		tempMappings2.put("N", 0);
		tempMappings2.put("J", 1);
		tempMappings2.put("VALUE", 2);
		tempMappings2.put("MAIN", 0);
		tempMappings2.put("ADDIT", 3);
		tempMappings2.put("EXIT", 5);

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
		
		tempMappings3.put("EXIT", 4);

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
		
		tempHexStr4[0] = new HexadecimalString();
		tempHexStr4[0].setDecimalValue(-2309);
		tempHexStr4[1] = new HexadecimalString();
		tempHexStr4[1].setDecimalValue(2345);
		tempHexStr4[2] = new HexadecimalString();
		tempHexStr4[2].setDecimalValue(-1);
		
		for(int i = 3; i < 100; i++) {
			tempHexStr4[i] = new HexadecimalString();
		}
		
		tempMappings4.put("K", 0);
		tempMappings4.put("M", 1);
		tempMappings4.put("CHICKEN", 2);
		tempMappings4.put("EXIT", 1);

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

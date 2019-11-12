/*
 * TCSS 372 Computer Architecture
 * Project 1
 */

package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Instruction;

/**
 * This class tests the methods in the
 * Instruction class.
 * 
 * @author Michael Zachary Loria, Ken Romero
 * @version 11.11.19
 */
public class InstructionTest {

	/** The Instruction used for testing. */
	private Instruction myInstruction;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		myInstruction = new Instruction();
	}

	/**
	 * Test method for {@link model.Instruction#Instruction()}.
	 */
	@Test
	public void testInstruction() {
		assertEquals("", myInstruction.getInstructionString());
		assertEquals("", myInstruction.getOperation());
		assertNull(myInstruction.getArguments());
		assertEquals(0, myInstruction.getNumberArguments());
	}

	/**
	 * Test method for {@link model.Instruction#getInstructionString()}.
	 */
	@Test
	public void testGetInstructionString() {
		myInstruction.setInstructionString("ADD $a0,$a1,$a2");
		assertEquals(myInstruction.getInstructionString(),"ADD $A0,$A1,$A2");
		
		myInstruction.setInstructionString("JR $ra");
		assertEquals(myInstruction.getInstructionString(),"JR $RA");
		
		myInstruction.setInstructionString("J fib");
		assertEquals(myInstruction.getInstructionString(),"J FIB");
	}

	/**
	 * Test method for {@link model.Instruction#setInstrString()}.
	 */
	@Test
	public void testSetInstrString() {
		myInstruction.setInstructionString("ADDI $a0,$a1,5");
		assertEquals(myInstruction.getInstructionString(),"ADDI $A0,$A1,5");
		
		myInstruction.setInstructionString("LW $A0,FIB");
		assertEquals(myInstruction.getInstructionString(),"LW $A0,FIB");
		
		myInstruction.setInstructionString("SW $S0,0($A1)");
		assertEquals(myInstruction.getInstructionString(),"SW $S0,0($A1)");
	}

	/**
	 * Test method for {@link model.Instruction#getOperation()}.
	 */
	@Test
	public void testGetOperation() {
		myInstruction.setInstructionString("ADDI $a0,$a1,5");
		assertEquals(myInstruction.getOperation(),"ADDI");
		
		myInstruction.setInstructionString("LW $A0,FIB");
		assertEquals(myInstruction.getOperation(),"LW");
		
		myInstruction.setInstructionString("SW $S0,0($A1)");
		assertEquals(myInstruction.getOperation(),"SW");
	}
	
	/**
	 * Test method for {@link model.Instruction#getNumberArguments()}.
	 */
	@Test
	public void testGetNumberArguments() {
		myInstruction.setInstructionString("ADDI $a0,$a1,5");
		assertEquals(myInstruction.getNumberArguments(),3);
		
		myInstruction.setInstructionString("LW $A0,FIB");
		assertEquals(myInstruction.getNumberArguments(),2);
		
		myInstruction.setInstructionString("AND $a1,$a2,$a3");
		assertEquals(myInstruction.getNumberArguments(),3);
	}
	
	/**
	 * Test method for {@link model.Instruction#getArguments()}.
	 */
	@Test
	public void testGetArguments() {
		myInstruction.setInstructionString("ADDI $a0,$a1,5");
		String[] temp1 = {"$A0","$A1","5"};
		assertArrayEquals(myInstruction.getArguments(), temp1);
		
		myInstruction.setInstructionString("LW $A0,FIB");
		String[] temp2 = {"$A0","FIB"};
		assertArrayEquals(myInstruction.getArguments(), temp2);
		
		myInstruction.setInstructionString("AND $a1,$a2,$a3");
		String[] temp3 = {"$A1","$A2","$A3"};
		assertArrayEquals(myInstruction.getArguments(), temp3);
	}

}

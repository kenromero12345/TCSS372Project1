# MIPS Simulator

The MIPS Simulator is a program that simulates the MIPS architecture and allows the user to run MIPS assembly language on the simulator.

## Authors

Michael Zachary Loria	
Ken Romero

## Version

13 November 2019

## Decisions

1. Language Level: The MIPS simulator will take in assembly language only.
2. Token Delimiters: Each instruction will be separated by a new line. Within each instruction, the operator and the operands will be separated with a space. If there is more than one operand, the operands will be separated with a comma (with no spaces). 
3. Input to Simulator: A JTextArea will be used to capture the instructions from the user. The user will be able to type MIPS assembly language in the text area in the graphical user interface.
4. Memory: The MIPS simulator will have 100 memory spaces. The data segment will start at address 0x10010000 (represented internally as index 0-49), and the stack pointer will start at address 0x7FFFEFFC (represented internally as index 50-99) allocated for the stack. The global pointer starting address will be at 0x10008000 (this value does not change throughout the execution of the program). A graphical user interface will be used to show the state of each memory location. Each memory location will have 4 bytes, so each memory location will be incremented by 4. The user should not enter an invalid address (the address minus 0x7FFFEFFC mod 4 must equal 0) or it will perform integer division when calculating the address. Instructions will start at address 0x00400000.
5. Registers: There will be 32 registers in the MIPS simulator. In addition, there will also be a program counter. The registers will be displayed in the graphical user interface.
6. GUI: The MIPS simulator will have a graphical user interface that the user can interact with. There are buttons that will allow the user to assemble the program, execute one line, and execute all lines.
7. Execution: The user has the option of executing one line, or all lines.

## Guidelines for Using MIPS Simulator

In order for the MIPS simulator to function properly, the user should read the following guidelines regarding formatting of instructions:
1. Case does not matter when inputting instructions. For example, the MIPS simulator can take in add $a0,$a0,1 or ADD $A0,$A0,1.
2. Registers are indicated with "$"(dollar sign). For example, $t0 or $zero.
3. When in immediate mode, the range of the constant is from -2^15(-32768) to 2^15 - 1(32767). If the number is out of this range, an error will be thrown.
4. The user must input at least one executable instruction before assembling. If the user does not input an executable instruction, an error will occur.
5. Labels must be placed on their own line. Labels should not have any operand or operators in the same line. For example, LABEL: JR $RA is not acceptable. Instead, the LABEL: should be on its own line.
6. The simulator will be able to read the following assembler directives: .data and .text. These must also be placed on their own lines.
7. The simulator can also take in the assembler directive: .word. The following format must be followed: .word VALUE, where VALUE is an integer.
8. Labels must have a colon immediately after the label. For example, FIB: would be an acceptable label.
9. Jumping to an address must be within range. If the address is too far away, an error will be thrown.
10. The following formats must be followed:
    a) ADD <register>,<register>,<register>
       Example: ADD $A0,$A1,$A2
    b) ADDU <register>,<register>,<register>
       Example: ADDU $V0,$A1,$A2
    c) AND <register>,<register>,<register>
       Example: AND $A0,$A1,$A2
    d) OR <register>,<register>,<register>
       Example: OR $A0,$A1,$A2
    e) ADDI <register>,<register>,<constant>
       Example: ADDI $A0,$A1,4
    f) ADDIU <register>,<register>,<constant>
       Example: ADDIU $A0,$A1,10
    g) ANDI <register>,<register>,<constant>
       Example: ANDI $A1,$A2,20
    h) ORI <register>,<register>,<constant>
       Example: ORI $A1,$A1,20
    i) LW <register>,<constant>(<register>)
       Example: LW $A0,4($SP)
       LW <register>,<label>
       Example: LW $A0,NUM
    j) SW <register>,<constant>(<register>)
       Example: SW $A1,8($SP)
       SW <register>,<label>
       Example: SW $A1,NUM
    k) BEQ <register>,<register>,<label>
       Example: BEQ $T0,$ZERO,LOOP
    l) BNE <register>,<register>,<label>
       Example: BNE $T0,$T1,REC
    m) J <label>
       Example: J FIB
    n) JR <register>
       Example: JR $RA
11. To execute the program, the user must click 'File' at the top of the file menu. The user must then click 'Assemble' to assemble the program. Afterwards, the user can click 'Execute All Instructions' or 'Execute One Instruction'.

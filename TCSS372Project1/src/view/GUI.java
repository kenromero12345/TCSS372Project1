/*
 * TCSS 372 Machine Organization
 * Project 1
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.Simulator;

/**
 * This is the class that handles the graphical
 * user interface of the MIPS simulator.
 * 
 * @author Michael Zachary Loria, Ken Romero
 * @version 11.12.19
 */
public class GUI {
	
	/** The maximum amount of registers. */
	private static final int MAX_MEMORY = 100;
	
	/** The cut off index for stack memory. */
	private static final int STACK_INDEX = 50;

	/** The width of the frame. */
	private static final int FRAMEWIDTH = 965;

	/** The width for memory panels. */
	private static final int WIDTHFORPANELS_M = 190;
	
	/** The width of the memory panels. */
	private static final int WIDTHFORPANEL_M = 230;

	/** The maximum integer signed for 32 bits. */
	private static final BigInteger MAXSIGNED = new BigInteger("2147483647");

	/** The minimum integer signed for 32 bits. */
	private static final BigInteger MINSIGNED = new BigInteger("2147483648").negate();

	/** The starting X position. */
	private static final int STARTX = 10;

	/** The width of the labels. */
	private static final int LABELWIDTH = 70;

	/** The height of the labels. */
	private static final int LABELHEIGHT = 15;

	/** The starting Y position. */
	private static final int STARTY = 10;

	/** The starting X for register panel. */
	private static final int XFORPANEL_R = 400;

	/** The gap for register panels in X position. */
	private static final int XGAPFORPANELS_R = 160;

	/** The gap for register panels in y position. */
	private static final int YGAPFORPANELS_R = 30;

	/** The width for register panels. */
	private static final int WIDTHFORPANELS_R = 150;

	/** The registers of MIPS. */
	private static final String[] REGISTERS = {
			"0 zero", "1 at", "2 v0", "3 v1", "4 a0", "5 a1", "6 a2", "7 a3"
			, "8 t0", "9 t1", "10 t2", "11 t3", "12 t4", "13 t5", "14 t6", "15 t7"
			,"16 s0", "17 s1", "18 s2", "19 s3", "20 s4", "21 s5", "22 s6", "23 s7"
			, "24 t8", "25 t9"
			, "26 k0", "27 k1"
			, "28 gp", "29 sp", "30 fp", "31 ra"};

	/** The height of the frame. */
	private static final int FRAMEHEIGHT = 560;

	/** The Y position of the frame. */
	private static final int FRAMEY = 0;

	/** The X position of the frame. */
	private static final int FRAMEX = 0;

	/** The instruction panel's x position. */
	private static final int XFORPANEL_I = 10;

	/** The instruction panel's y position. */
	private static final int YFORPANEL = 30;

	/** The width of instruction panels. */
	private static final int WIDTHFORPANEL_I = 380;

	/** The height of the panels. */
	private static final int HEIGHTFORPANELS = 470;

	/** The height of the register panels. */
	private static final int HEIGHTFORPANELS_R = 20;

	/** The memory panel's position Y. */
	private static final int YFORPANEL_M = 720;

	/** The gap length. */
	private static final int GAP = 10;

	/** The memory panel. */
	private JPanel memJPanel;

	/** The memory panel lists. */
	private ArrayList<RegisterMemJPanel> memList;
	
	/** The register panel lists. */
	private ArrayList<RegisterMemJPanel> regJPanel;

	/** The frame of the application. */
	private JFrame frmGui;

	/** The textarea for instruction. */
	private JTextArea txtArea;
	
	/** The menu bar. */
	private JMenu menu;
	
	/** The assemble menu item. */
	private JMenuItem assemble;
	
	/** The execute menu item. */
	private JMenuItem execute;
	
	/** The execute 1 menu item. */
	private JMenuItem execute1;

	/** The controller of the application. */
	private Simulator sim;
	
	/** The pc of the gui **/
	private RegisterMemJPanel pcJPanel;
	
	/**
	 * Create the application.
	 * 
	 * @param sim The simulator for the application.
	 */
	public GUI(Simulator sim) {
		this.sim = sim;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		initializeInstVar();
		setUpFrame();
		setUpComponents();
	}

	/**
	 * Sets up the components of the frame.
	 */
	private void setUpComponents() {
		memJPanel.setLayout(null);
		JScrollPane scrollPaneInst = new JScrollPane(txtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneInst.setBounds(XFORPANEL_I, YFORPANEL, WIDTHFORPANEL_I, HEIGHTFORPANELS);
		frmGui.getContentPane().add(scrollPaneInst);
		JScrollPane scrollPaneMem = new JScrollPane(memJPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneMem.setBounds(YFORPANEL_M, YFORPANEL, WIDTHFORPANEL_M, HEIGHTFORPANELS - YGAPFORPANELS_R);
		frmGui.getContentPane().add(scrollPaneMem);
		
		pcJPanel = new RegisterMemJPanel("PC");
		pcJPanel.setBounds(YFORPANEL_M + 10, YGAPFORPANELS_R + 15 * YGAPFORPANELS_R
				, WIDTHFORPANELS_M, HEIGHTFORPANELS_R);
		frmGui.getContentPane().add(pcJPanel);

		setLabels();
		setRegisterJPanel();
		setMemJPanel();
	}

	/**
	 * Sets up the labels.
	 */
	private void setLabels() {
		JLabel memL = new JLabel("Memory");
		memL.setBounds(YFORPANEL_M, STARTY, LABELWIDTH, LABELHEIGHT);
		frmGui.getContentPane().add(memL);

		JLabel regL = new JLabel("Register");
		regL.setBounds(XFORPANEL_R, STARTY, LABELWIDTH, LABELHEIGHT);
		frmGui.getContentPane().add(regL);

		JLabel instrL = new JLabel("Instruction");
		instrL.setBounds(STARTX, STARTY, LABELWIDTH, LABELHEIGHT);
		frmGui.getContentPane().add(instrL);
	}

	/**
	 * Sets up the frame.
	 */
	private void setUpFrame() {
		frmGui.setTitle("MIPS Simulator");
		frmGui.setBounds(FRAMEX, FRAMEY, FRAMEWIDTH, FRAMEHEIGHT);
		frmGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGui.setJMenuBar(createMenuBar());
		frmGui.getContentPane().setLayout(null);
		frmGui.setLocationRelativeTo(null);
		frmGui.setResizable(false);	
		frmGui.setVisible(true);
	}

	/**
	 * Initialize the instance variables.
	 */
	private void initializeInstVar() {
		frmGui = new JFrame();
		regJPanel = new ArrayList<>();
		memJPanel = new JPanel();
		memList = new ArrayList<>();
		txtArea = new JTextArea();
		txtArea.setLineWrap(true);
		txtArea.setWrapStyleWord(true);
		
		assemble = new JMenuItem("Assemble");
		assemble.addActionListener(theEvent -> assembleInstruction());
		
		execute = new JMenuItem("Execute All Instructions");
		execute.addActionListener(theEvent -> executeInstruction());
		
		execute1 = new JMenuItem("Execute One Instruction");
		execute1.addActionListener(theEvent -> execute1Instruction());
	}

	/**
	 * Sets up the register panel.
	 */
	private void setRegisterJPanel() {
		int i = 0;
		int j = 0;
		for (String s : REGISTERS) {
			RegisterMemJPanel pan = new RegisterMemJPanel(s);
			pan.setBounds(XFORPANEL_R + XGAPFORPANELS_R * j, YFORPANEL + i * YGAPFORPANELS_R
					, WIDTHFORPANELS_R, HEIGHTFORPANELS_R);
			frmGui.getContentPane().add(pan);
			regJPanel.add(pan);
			i++;
			if (i % 17 == 16) {
				j++;
				i = 0;
			}
		}
		regJPanel.get(28).setValue("268468224");
		regJPanel.get(29).setValue("2147479548");
	}

	/**
	 * Sets up the memory panel.
	 */
	private void setMemJPanel() {
		for (int i = 0; i < MAX_MEMORY - STACK_INDEX; i++) {
			RegisterMemJPanel pan = new RegisterMemJPanel("" + convertToHexOnMARSMemory(i));
			pan.setBounds(STARTX, STARTY + i * YGAPFORPANELS_R
					, WIDTHFORPANELS_M, HEIGHTFORPANELS_R);
			memJPanel.setPreferredSize(new Dimension(WIDTHFORPANELS_R
					, MAX_MEMORY * (HEIGHTFORPANELS_R + GAP) + GAP));
			memJPanel.add(pan);
			memList.add(pan);
		}
		for (int i = STACK_INDEX; i < MAX_MEMORY; i++) {
			RegisterMemJPanel pan = new RegisterMemJPanel("" + convertToHexOnStack(i-STACK_INDEX));
			pan.setBounds(STARTX, STARTY + i * YGAPFORPANELS_R
					, WIDTHFORPANELS_M, HEIGHTFORPANELS_R);
			memJPanel.setPreferredSize(new Dimension(WIDTHFORPANELS_R
					, MAX_MEMORY * (HEIGHTFORPANELS_R + GAP) + GAP));
			memJPanel.add(pan);
			memList.add(pan);
		}
	}
	
	/**
	 * Converts the integer to hex.
	 * 
	 * @param i The integer to be converted.
	 * @return The string containing the hexadecimal conversion.
	 */
	private String convertToHexOnMARSMemory(int i) {
		String startHex = "0x";
		BigInteger startDec = new BigInteger("268500992");
		BigInteger toHex= new BigInteger(startDec.add(BigInteger.valueOf(i * 4)).toString(), 10);
		return (startHex + toHex.toString(16)).toUpperCase();
	}
	
	/**
	 * Converts the integer to hex for the stack memory.
	 * 
	 * @param i The integer to be converted.
	 * @return The string containing the hexadecimal conversion.
	 */
	private String convertToHexOnStack(int i) {
		String startHex = "0x";
		BigInteger startDec = new BigInteger("2147479548");
		BigInteger toHex= new BigInteger(startDec.add(BigInteger.valueOf(i * 4)).toString(), 10);
		return (startHex + toHex.toString(16)).toUpperCase();
	}
	
	/**
	 * Shows a dialog message.
	 * 
	 * @param s The message to be displayed.
	 */
	public void showDialog(String s) {
		JOptionPane.showMessageDialog(frmGui, s);
	}

	/**
	 * Creates the JMenuBar.
	 *
	 * @return The JMenuBar.
	 */
	private JMenuBar createMenuBar() {
		final JMenuBar bar = new JMenuBar();
		createFileMenu();
		bar.add(menu);

		return bar;
	}

	/**
	 * Creates the file menu.
	 */
	private void createFileMenu() {
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);

		menu.add(assemble);
		menu.add(execute);
		menu.add(execute1);
		execute.setEnabled(false);
		execute1.setEnabled(false);
	}
	
	/**
	 * Assembles the instructions.
	 */
	private void assembleInstruction() {
		if (txtArea.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(frmGui, "Error assembling program.");
		} 
		else {
			try {
				sim.assemble(txtArea.getText());
				resetRegMemColor();
				resetRegisters();
				resetMemory();
				setPC(0);
				JOptionPane.showMessageDialog(frmGui, "Assembling Program Complete");
				execute.setEnabled(true);
				execute1.setEnabled(true);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(frmGui, "Error assembling program.");
			}
		}
	}
	
	/**
	 * Reset's the memory.
	 */
	private void resetMemory() {
		for (int i = 0; i < memList.size(); i++) {
			memList.get(i).resetTo0();
		}
	}

	/**
	 * Reset's the registers.
	 */
	private void resetRegisters() {
		for (int i = 0; i < regJPanel.size(); i++) {
			if (i == 28) {
				regJPanel.get(28).setValue("268468224");
			} else if (i == 29) {
				regJPanel.get(29).setValue("2147479548");
			} else {
				regJPanel.get(i).resetTo0();
			}
			
		}
		
	}

	/**
	 * Sets the register value based on the integer
	 * and the string. 
	 * 
	 * @param i The integer.
	 * @param s The string.
	 */
	public void setRegisterValue(int i, String s) {
		if (!s.equals(regJPanel.get(i).getValue())) {
			regJPanel.get(i).setValue(s);
			regJPanel.get(i).setBackground(Color.gray);
		}
	}
	
	/**
	 * Sets the memory value based on the integer 
	 * and the string. 
	 * 
	 * @param i The integer.
	 * @param s The string.
	 */
	public void setMemoryValue(int i, String s) {
		if (!s.equals(memList.get(i).getValue())) {
			memList.get(i).setValue(s);
			memList.get(i).setBackground(Color.gray);
		}
	}

	/**
	 * Execute the instructions.
	 */
	private void executeInstruction() {
		try {
			sim.execute();
			JOptionPane.showMessageDialog(frmGui, "Program execution complete.");
			execute.setEnabled(false);
			execute1.setEnabled(false);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(frmGui, "Error executing program. Program execution has been terminated.");
			execute.setEnabled(false);
			execute1.setEnabled(false);
		}
		
	}
	
	/**
	 * Executes one instruction.
	 */
	private void execute1Instruction() {
		try {
			resetRegMemColor();
			sim.executeOneLine();
			JOptionPane.showMessageDialog(frmGui, "Executing one instruction complete.");
		}
		catch (Exception e) {
			if(e instanceof NoSuchElementException) {
				JOptionPane.showMessageDialog(frmGui, "Program execution complete.");
				execute.setEnabled(false);
				execute1.setEnabled(false);
			}
			else {
				JOptionPane.showMessageDialog(frmGui, "Error executing line. Program execution has been terminated.");
				execute.setEnabled(false);
				execute1.setEnabled(false);
			}
		}
	}

	/**
	 * Resets the register memory color.
	 */
	private void resetRegMemColor() {
		for (RegisterMemJPanel p : memList)  {
			p.setBackground(Color.lightGray);
		}
		for (RegisterMemJPanel p : regJPanel)  {
			p.setBackground(Color.lightGray);
		}
	}
	
	public void setPC(long i) {
		pcJPanel.setValue("" + i);
	}

	/**
	 * The modified JPanel for the memory & register.
	 */
	public class RegisterMemJPanel extends JPanel{

		/** A generated serial version UID for object Serialization. */
		private static final long serialVersionUID = -6745484794302396327L;
		
		/** The JLabel for the value. */
		protected JLabel valLabel = new JLabel();
		
		/**
		 * Sets up the Register Memory JPanel.
		 * 
		 * @param reg The string of register.
		 */
		private RegisterMemJPanel(String reg) {
			super();
			setLayout(new BorderLayout());
			add(new JLabel(" " + reg), BorderLayout.WEST);
			valLabel.setText("0 ");
			add(valLabel, BorderLayout.EAST);
			setBackground(Color.LIGHT_GRAY);
		}

		/**
		 * Sets the text of a label.
		 * 
		 * @param s The given string.
		 * @throws OverFlowException
		 */
		public void setValue(String s) throws ArithmeticException {
			if(MAXSIGNED.compareTo(new BigInteger(s)) == -1 || MINSIGNED.compareTo(new BigInteger(s)) == 1) {
				throw new ArithmeticException();
			} else {
				valLabel.setText(s + " ");
			}
		}
		
		/**
		 * Get the text of the label.
		 * 
		 * @return the text
		 */
		private String getValue() {
			return valLabel.getText().substring(0, valLabel.getText().length()-1);
		}
		
		/**
		 * Resets the register or memory
		 */
		private void resetTo0() {
			valLabel.setText("0 ");
		}
	}
}

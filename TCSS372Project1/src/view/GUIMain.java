package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.math.BigInteger;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.Computer;
/**
 * @author Owner Ken Gil Romero
 * @version Fall 19 TCSS 372
 */
/**
 * The main application and the GUI
 */
public class GUIMain {

//	/**
//	 * Width of the constant textbox
//	 */
//	private static final int STARTWIDTH_FOR_CONST = 80;
	
	private static final int MAX_REGISTERS = 50;

	/**
	 * width of frame
	 */
	private static final int FRAMEWIDTH = 965;

	/**
	 * width for memory panels
	 */
	private static final int WIDTHFORPANELS_M = 190;
	
	/**
	 * width of memory panels
	 */
	private static final int WIDTHFORPANEL_M = 230;

	//	/**
//	 * Max unsigned for 32 bit
//	 */
//	private static final BigInteger MAXUNSIGNED = new BigInteger("4294967295");
//
	/**
	 * Max signed for 32 bit
	 */
	private static final BigInteger MAXSIGNED = new BigInteger("2147483647");

	/**
	 * Min signed for 32 bit
	 */
	private static final BigInteger MINSIGNED = new BigInteger("2147483648").negate();

	/**
	 * starting x position
	 */
	private static final int STARTX = 10;

	/**
	 * every labels width
	 */
	private static final int LABELWIDTH = 70;

	/**
	 * every label's height
	 */
	private static final int LABELHEIGHT = 15;

	/**
	 * starting y position
	 */
	private static final int STARTY = 10;

	/**
	 * starting x for register panel
	 */
	private static final int XFORPANEL_R = 400;

	/**
	 * gap for register panels in x position
	 */
	private static final int XGAPFORPANELS_R = 160;

	/**
	 * gap for register panels in y position
	 */
	private static final int YGAPFORPANELS_R = 30;

	/**
	 * width for register panesl
	 */
	private static final int WIDTHFORPANELS_R = 150;

//	/**
//	 * height for instructions
//	 */
//	private static final int HEIGHTFORINSTR = 20;

//	/**
//	 * opcodes for the instruction
//	 */
//	private static final String[] OPCODES = {
//			"", "ADD", "ADDU", "AND", "OR"
//			, "ADDI", "ADDIU", "ANDI", "ORI", "LW", "SW", "BEQ", "BNE"
//			, "J", "JR"};

	/**
	 * registers of mips
	 */
	private static final String[] REGISTERS = {
			"0 zero", "1 at", "2 v0", "3 v1", "4 a0", "5 a1", "6 a2", "7 a3"
			, "8 t0", "9 t1", "10 t2", "11 t3", "12 t4", "13 t5", "14 t6", "15 t7"
			,"16 s0", "17 s1", "18 s2", "19 s3", "20 s4", "21 s5", "22 s6", "23 s7"
			, "24 t8", "25 t9"
			, "26 k0", "27 k1"
			, "28 gp", "29 sp", "30 fp", "31 ra"};

	/**
	 * height of frame
	 */
	private static final int FRAMEHEIGHT = 560;

	/**
	 * y position of frame
	 */
	private static final int FRAMEY = 0;

	/**
	 * x position of frame
	 */
	private static final int FRAMEX = 0;

	/**
	 * instruction panel's x position
	 */
	private static final int XFORPANEL_I = 10;

	/**
	 * instruction panel's y position
	 */
	private static final int YFORPANEL = 30;

//	/**
//	 * width of comboboxes
//	 */
//	private static final int STARTWIDTH_FOR_CB = 60;

	/**
	 * width of instruction panels
	 */
	private static final int WIDTHFORPANEL_I = 380;

	/**
	 * height of the panels
	 */
	private static final int HEIGHTFORPANELS = 470;

	/**
	 * height of the register panels
	 */
	private static final int HEIGHTFORPANELS_R = 20;

	/**
	 * memory panel's position y
	 */
	private static final int YFORPANEL_M = 720;

	/**
	 * gap length
	 */
	private static final int GAP = 10;

//	/**
//	 * x position of memory panel
//	 */
//	private static final int XFORPANEL_M = 10;

//	/**
//	 * instruction panel
//	 */
//	private JPanel instrJPanel;

	/**
	 * memory panel
	 */
	private JPanel memJPanel;
//
//	/**
//	 * opcode comboboxes
//	 */
//	private ArrayList<OpcodeJComboBox> opcodeJComboBoxes;
//
//	/**
//	 * register destination comboboxes
//	 */
//	private ArrayList<RegisterJComboBox> regDestJComboBoxes;
//
//	/**
//	 * 1st register source comboboxes
//	 */
//	private ArrayList<RegisterJComboBox> regSource1JComboBoxes;
//
//	/**
//	 * 2nd register source comboboxes
//	 */
//	private ArrayList<RegisterJComboBox> regSource2JComboBoxes;
//
//	/**
//	 * constant textfields
//	 */
//	private ArrayList<ConstantJTextField> constantJTextFields;

	/**
	 * memory panel lists
	 */
	public ArrayList<RegisterMemJPanel> memList;

//	/**
//	 *
//	 */
//	private ArrayList<JButton> plusMinusJButton;

	/**
	 * register panel lists
	 */
	public ArrayList<RegisterMemJPanel> regJPanel;

//	/**
//	 * number of instructions
//	 */
//	private int counter = 0;

	/**
	 * the frame of the application
	 */
	private JFrame frmGui;

	/**
	 * the backend of the application
	 */
	private Computer comp;

	/**
	 * textarea for instruction
	 */
	private JTextArea txtArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIMain window = new GUIMain();
					window.frmGui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUIMain() {
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
	 * sets up the components of the frame
	 */
	public void setUpComponents() {
//		instrJPanel.setLayout(null);
		memJPanel.setLayout(null);
//		JScrollPane scrollPaneInst = new JScrollPane(instrJPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollPane scrollPaneInst = new JScrollPane(txtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneInst.setBounds(XFORPANEL_I, YFORPANEL, WIDTHFORPANEL_I, HEIGHTFORPANELS);
		frmGui.getContentPane().add(scrollPaneInst);
		JScrollPane scrollPaneMem = new JScrollPane(memJPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneMem.setBounds(YFORPANEL_M, YFORPANEL, WIDTHFORPANEL_M, HEIGHTFORPANELS);
		frmGui.getContentPane().add(scrollPaneMem);

		setLabels();
		setRegisterJPanel();
		setMemJPanel();
	}

	/**
	 * sets the labels
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
	 * sets up the frame
	 */
	public void setUpFrame() {
		frmGui = new JFrame();
		frmGui.setTitle("MIPS");
		frmGui.setBounds(FRAMEX, FRAMEY, FRAMEWIDTH, FRAMEHEIGHT);
		frmGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGui.setJMenuBar(createMenuBar());
		frmGui.getContentPane().setLayout(null);
		frmGui.setLocationRelativeTo(null);
		frmGui.setResizable(false);
	}

	/**
	 * initialize the instance variables
	 */
	public void initializeInstVar() {
//		opcodeJComboBoxes = new ArrayList<>();
//		regDestJComboBoxes = new ArrayList<>();
//		regSource1JComboBoxes = new ArrayList<>();
//		regSource2JComboBoxes = new ArrayList<>();
//		constantJTextFields = new ArrayList<>();
//		plusMinusJButton = new ArrayList<>();
		regJPanel = new ArrayList<>();
		comp = new Computer();
//		instrJPanel = new JPanel();
		memJPanel = new JPanel();
		memList = new ArrayList<>();
		txtArea = new JTextArea();
	}

	/**
	 * sets the register panel
	 */
	public void setRegisterJPanel() {
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
	}

	/**
	 * sets the memory panel
	 */
	public void setMemJPanel() {
		for (int i = 0; i < MAX_REGISTERS; i++) {
			RegisterMemJPanel pan = new RegisterMemJPanel("" + convertToHexOnMARSMemory(i));
			pan.setBounds(STARTX, STARTY + i * YGAPFORPANELS_R
					, WIDTHFORPANELS_M, HEIGHTFORPANELS_R);
			memJPanel.setPreferredSize(new Dimension(WIDTHFORPANELS_R
					, 50 * (HEIGHTFORPANELS_R + GAP) + GAP));
			memJPanel.add(pan);
			memList.add(pan);
		}
	}
	
	private String convertToHexOnMARSMemory(int i) {
		String startHex = "0x";
		BigInteger startDec = new BigInteger("268500992");
		BigInteger toHex= new BigInteger(startDec.add(BigInteger.valueOf(i * 4)).toString(), 10);
		return startHex + toHex.toString(16);
	}
	
//	/**
//	 * sets the combobox actions
//	 * @param cb the current opcode combobox
//	 */
//	public void setComboBoxAction(OpcodeJComboBox cb) {
//		removeFromListsAndPanel(cb, opcodeJComboBoxes.indexOf(cb));
//
//		RegisterJComboBox rd = new RegisterJComboBox();
//		RegisterJComboBox rs = new RegisterJComboBox();
//		RegisterJComboBox rt = new RegisterJComboBox();
//		ConstantJTextField constant = new ConstantJTextField();
////		JButton btn = new JButton("+/-");
//
//		if (cb.getSelectedIndex() >= 1 && cb.getSelectedIndex() <= 4) {
//			setRD(cb, rd);
//			setRS(cb, rs);
//			setRT(cb, rt);
//		} else if (cb.getSelectedIndex() == 6) {
//			setRD(cb, rd);
//			setRS(cb, rs);
//			setC(cb, constant);
//			constant.setUnsigned(true);
//		} else if (cb.getSelectedIndex() >= 5 && cb.getSelectedIndex() <= 12) {
//			setRD(cb, rd);
//			setRS(cb, rs);
//			setC(cb, constant);
////			setBtn(cb, constant, btn);
//		} else if (cb.getSelectedIndex() == 13) {
//			setC(cb, constant);
//		} else if (cb.getSelectedIndex() == 14) {
//			setRD(cb, rd);
//		}
//
//		addFromTheLists(rd, rs, rt, constant);
//
//		instrJPanel.revalidate();
//		instrJPanel.repaint();
//	}

//	/**
//	 * remove the instruction's constant, registers, and textfield
//	 * @param cb the opcode combobox
//	 * @param index the current number of the opcode on the opcode lists
//	 */
//	private void removeFromListsAndPanel(OpcodeJComboBox cb, int index) {
//		if (cb.isClicked) {
////			instrJPanel.remove(regDestJComboBoxes.get(index));
////			instrJPanel.remove(regSource1JComboBoxes.get(index));
////			instrJPanel.remove(regSource2JComboBoxes.get(index));
////			instrJPanel.remove(constantJTextFields.get(index));
////			instrJPanel.remove(plusMinusJButton.get(index));
//			regDestJComboBoxes.remove(index);
//			regSource1JComboBoxes.remove(index);
//			regSource2JComboBoxes.remove(index);
//			constantJTextFields.remove(index);
////			plusMinusJButton.remove(index);
//		}
//		cb.isClicked = true;
//	}

//	/**
//	 * @param cb
//	 * @param constant
//	 * @param btn
//	 */
//	private void setBtn(OpcodeJComboBox cb, ConstantJTextField constant, JButton btn) {
//		btn.setBounds(cb.getBounds().x + 4 * (STARTWIDTH_FOR_CB + GAP) + 20, cb.getBounds().y
//				, STARTWIDTH_FOR_CB, HEIGHTFORINSTR);
//		btn.addActionListener(event -> {
//			if (!constant.getText().toString().trim().equals("0")) {
//				if (constant.getText().toString().trim().charAt(0) != '-') {
//					constant.setText("-" + constant.getText());
//				} else {
//					constant.setText(constant.getText().toString().substring(1));
//				}
//			}
//		});
//	}

//	/**
//	 * set the constant textfield
//	 * @param cb the opcode of the instruction
//	 * @param constant the constant textfield
//	 */
//	private void setC(OpcodeJComboBox cb, ConstantJTextField constant) {
//		constant.setBounds(cb.getBounds().x + 3 * (STARTWIDTH_FOR_CB + GAP), cb.getBounds().y
//				, STARTWIDTH_FOR_CONST, HEIGHTFORINSTR);
//		instrJPanel.add(constant);
//	}
//
//	/**
//	 * set the second register source combobox
//	 * @param cb the opcode of the instruction
//	 * @param rt the second register source combobox
//	 */
//	private void setRT(OpcodeJComboBox cb, RegisterJComboBox rt) {
//		rt.setBounds(cb.getBounds().x + 3 * (STARTWIDTH_FOR_CB + GAP), cb.getBounds().y
//				, STARTWIDTH_FOR_CB, HEIGHTFORINSTR);
//		instrJPanel.add(rt);
//	}
//
//	/**
//	 * set the first register source combobox
//	 * @param cb the opcode of the instruction
//	 * @param rs the first register source combobox
//	 */
//	private void setRS(OpcodeJComboBox cb, RegisterJComboBox rs) {
//		rs.setBounds(cb.getBounds().x + 2 * (STARTWIDTH_FOR_CB + GAP), cb.getBounds().y
//				, STARTWIDTH_FOR_CB, HEIGHTFORINSTR);
//		instrJPanel.add(rs);
//	}
//
//	/**
//	 * set the destination register combobox
//	 * @param cb the opcode of the instruction
//	 * @param rd the destination register combobox
//	 */
//	private void setRD(OpcodeJComboBox cb, RegisterJComboBox rd) {
//		rd.setBounds(cb.getBounds().x + STARTWIDTH_FOR_CB + GAP, cb.getBounds().y
//				, STARTWIDTH_FOR_CB, HEIGHTFORINSTR);
//		instrJPanel.add(rd);
//	}
//
//	/**
//	 * add the components from the lists
//	 * @param rd the destination register combobox
//	 * @param rs the first register source combobox
//	 * @param rt the second register source combobox
//	 * @param constant the constant textfield
//	 */
//	private void addFromTheLists(RegisterJComboBox rd, RegisterJComboBox rs, RegisterJComboBox rt,
//			ConstantJTextField constant) {
//		regDestJComboBoxes.add(rd);
//		regSource1JComboBoxes.add(rs);
//		regSource2JComboBoxes.add(rt);
//		constantJTextFields.add(constant);
////		plusMinusJButton.add(btn);
////		instrJPanel.add(btn);
//	}

	/**
	 * Creates the JMenuBar.
	 *
	 * @return the JMenuBar
	 */
	public JMenuBar createMenuBar() {
		final JMenuBar bar = new JMenuBar();

		bar.add(createFileMenu());

		return bar;
	}

	/**
	 * Creates the File JMenu.
	 *
	 * @author Ken Gil Romero
	 * @return the File menu.
	 */
	private JMenu createFileMenu() {
		final JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);

//		final JMenuItem add = new JMenuItem("Add Instruction");
//		add.addActionListener(theEvent -> addInstruction());
		final JMenuItem execute = new JMenuItem("Execute");
		execute.addActionListener(theEvent -> executeInstruction());

//		menu.add(add);
		menu.add(execute);

		return menu;
	}

//	/**
//	 * add an instruction to the instruction panel
//	 */
//	public void addInstruction() {
//		OpcodeJComboBox cb = new OpcodeJComboBox();
//		opcodeJComboBoxes.add(cb);
////		cb.addActionListener(arg -> setComboBoxAction(cb));
//
//		cb.setBounds(XFORPANEL_I, YFORPANEL + counter * YGAPFORPANELS_R
//				, STARTWIDTH_FOR_CB, HEIGHTFORINSTR);
////		instrJPanel.add(cb);
//		counter++;
////		instrJPanel.setPreferredSize(new Dimension(WIDTHFORPANEL_I
////				, HEIGHTFORINSTR + counter * (HEIGHTFORINSTR + GAP) - GAP));
////		instrJPanel.revalidate();
//	}

	/**
	 * execute the instructions
	 */
	public void executeInstruction() {
//		for (int i = 0; i < counter; i++) {
//			//	comp.load();
//		}
		comp.execute(this, txtArea.getText());
	}

//	/**
//	 * modified combobox of the opcode
//	 */
//	class OpcodeJComboBox extends JComboBox<String> {
//
//		/**
//		 * A generated serial version UID for object Serialization.
//		 */
//		private static final long serialVersionUID = 4888307132644146890L;
//
//		/**
//		 * shows if the combobox has been clicked
//		 */
//		protected boolean isClicked;
//
//		/**
//		 * contructor of the opcode combobox
//		 */
//		public OpcodeJComboBox() {
//			super();
//			for (String op : OPCODES) {
//				addItem(op);
//			}
//		}
//	}
//
//	/**
//	 * modified register combobox
//	 */
//	class RegisterJComboBox extends JComboBox<String> {
//
//		/**
//		 * A generated serial version UID for object Serialization.
//		 */
//		private static final long serialVersionUID = -1028191438120090593L;
//
//		/**
//		 * constructor for the register combobox
//		 */
//		public RegisterJComboBox() {
//			super();
//			for (String r : REGISTERS) {
//				addItem(r);
//			}
//		}
//	}
//
//	/**
//	 * modified textfield for the constant
//	 */
//	class ConstantJTextField extends JTextField {
//
//		/**
//		 * a boolean to know if the constant is unsigned
//		 */
//		protected boolean unsigned;
//
//		/**
//		 * A generated serial version UID for object Serialization.
//		 */
//		private static final long serialVersionUID = -2660852489854492593L;
//
//		/**
//		 * constructor of the text field
//		 */
//		public ConstantJTextField() {
//			super("0");
//			this.addKeyListener(new KeyAdapter( ) {
//
//				@Override
//				public void keyTyped(KeyEvent e) {
//					consumeNumAndNegative(e);
//				}
//
//				@Override
//				public void keyReleased(KeyEvent e) {
//					removeNegativeWhenNotAtFirst();
//					removeNegativeAfterFirstWhenThereIsNegativeAtFirst();
//					removeLeading0AndNegative0();
//					constantLimit();
//				}
//
//				/**
//				 * remove - after the first char when there is a - at the first
//				 */
//				private void removeNegativeAfterFirstWhenThereIsNegativeAtFirst() {
//					if (getText().chars().filter(ch -> ch == '-').count() > 1) {
//						boolean flag = false;
//						if (getText().charAt(0) == '-') {
//							flag = true;
//						}
//						setText(getText().replace("-", ""));
//						if (flag) {
//							setText("-" + getText());
//						}
//					}
//				}
//
//				/**
//				 * remove - when not at first char
//				 */
//				private void removeNegativeWhenNotAtFirst() {
//					if (getText().contains("-") && getText().charAt(0) != '-') {
//						setText(getText().replace("-", ""));
//					}
//				}
//
//				/**
//				 * consumes the non numbers except backspaces, delete, and -
//				 * @param e the event of the key
//				 */
//				private void consumeNumAndNegative(KeyEvent e) {
//					char input = e.getKeyChar();
//					if ((input < '0' || input > '9') && input != '\b' && input != '-') {
//						e.consume();
//					}
//				}
//
//				/**
//				 * remove the leading 0s and negative 0
//				 */
//				private void removeLeading0AndNegative0() {
//					if (!getText().equals("") && !getText().equals("0")) {
//						if (getText().charAt(0) == '0' || (getText().charAt(0) == '-' && getText().charAt(1) == '0')) {
//							setText(getText().substring(1));
//						}
//					} else {
//						setText("0");
//					}
//				}
//
//				/**
//				 * sets the constant limit
//				 */
//				private void constantLimit() {
//					if (unsigned) {
//						if(MAXUNSIGNED.compareTo(
//								new BigInteger(getText())) == -1) {
//							setText(MAXUNSIGNED.toString());
//						}
//
//						if (BigInteger.ZERO.compareTo(new BigInteger(getText())) == 1) {
//							setText(BigInteger.ZERO.toString());
//						}
//
//					} else {
//						if(MAXSIGNED.compareTo(new BigInteger(getText())) == -1) {
//							setText(MAXSIGNED.toString());
//						}
//
//						if (MINSIGNED.compareTo(new BigInteger(getText())) == 1) {
//							setText(MINSIGNED.toString());
//						}
//					}
//				}
//			});
//		}
//
//		/**
//		 * sets if the constant is unsigned
//		 */
//		protected void setUnsigned(boolean b) {
//			unsigned = b;
//		}
//	}

	/**
	 * the modified jpanel for the memory & register
	 */
	public class RegisterMemJPanel extends JPanel{

		/**
		 * A generated serial version UID for object Serialization.
		 */
		private static final long serialVersionUID = -6745484794302396327L;
		protected JLabel valLabel = new JLabel();
		public RegisterMemJPanel(String reg) {
			super();
			setLayout(new BorderLayout());
			add(new JLabel(" " + reg), BorderLayout.WEST);
			valLabel.setText("0 ");
			add(valLabel, BorderLayout.EAST);
			setBackground(Color.LIGHT_GRAY);
		}

		/**
		 * sets the text of a label
		 * @param s the given string
		 * @throws OverFlowException
		 */
		public void setValue(String s) throws ArithmeticException {
			if(MAXSIGNED.compareTo(new BigInteger(s)) == -1 || MINSIGNED.compareTo(new BigInteger(s)) == 1) {
				throw new ArithmeticException();
			} else {
				valLabel.setText(s + " ");
			}
		}
	}
}

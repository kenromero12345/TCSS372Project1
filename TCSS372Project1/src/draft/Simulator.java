package draft;
//package view;
//import java.awt.BorderLayout;
//import java.awt.Dimension;
//import java.awt.EventQueue;
//import java.awt.event.KeyEvent;
//
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JMenu;
//import javax.swing.JMenuBar;
//import javax.swing.JMenuItem;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;
//
//import model.Computer;
//
//public class Simulator extends JFrame {
//	
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 5536459206378112768L;
//
//	private static final int FRAMEHEIGHT = 500;
//
//	private static final int FRAMEWIDTH = 750;
//
//	private static final int FRAMEY = 100;
//
//	private static final int FRAMEX = 100;	
//	
//	private static final int PANELWIDTH = 300;
//	
//	private JTextArea txtArea;
//	private Computer comp;
//	private JPanel leftPanel;
//	private JPanel registerPanel;
//	private JPanel memoryPanel;
//
//	/**
//	 * Create the application.
//	 */
//	public Simulator()  {
//		super("MIPS");
//		initialize();
//	}
//
//	/**
//	 * Initialize the contents of the frame.
//	 */
//	private void initialize() {
//		setUpComponents();
////		pack();
//		setLocationRelativeTo(null);
//		setResizable(false);
//		setVisible(true);
//		//add()
//		//frmGui.getContentPane().add(new JLabel("a"));
//	}
//	
//	public void setUpComponents() {
//		setJMenuBar(createMenuBar());
//		setBounds(FRAMEX, FRAMEY, FRAMEWIDTH, FRAMEHEIGHT);
//		
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//		leftPanel = new JPanel(new BorderLayout());
//		memoryPanel = new JPanel();
//		registerPanel = new JPanel();
//		leftPanel.setPreferredSize(new Dimension(PANELWIDTH,0));
//		add(leftPanel, BorderLayout.EAST);
//		/*TODO: RegisterJPanel*/
//		leftPanel.add(registerPanel, BorderLayout.NORTH);
//		leftPanel.add(memoryPanel, BorderLayout.SOUTH);
//		registerPanel.add(new JLabel("1"));
//		memoryPanel.add(new JLabel("1"));
//		
//		txtArea = new JTextArea();
//		JScrollPane scrollPane = new JScrollPane(txtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		add(scrollPane, BorderLayout.CENTER);
//	}
//	
//	/**
//	 * Creates the JMenuBar.
//	 * 
//	 * @return the JMenuBar
//	 */
//	public JMenuBar createMenuBar() {
//		final JMenuBar bar = new JMenuBar();
//
//		bar.add(createFileMenu());
//
//		return bar;
//	}
//	
//	/**
//	 * Creates the File JMenu.
//	 * 
//	 * @author Ken Gil Romero
//	 * @return the File menu.
//	 */
//	private JMenu createFileMenu() {
//		final JMenu menu = new JMenu("File");
//		menu.setMnemonic(KeyEvent.VK_F);
//
//		final JMenuItem execute = new JMenuItem("Execute");
//		execute.addActionListener(theEvent -> executeInstruction());
//
//		menu.add(execute);
//
//		return menu;
//	}
//	
//	public void executeInstruction() {
//		comp.execute();
//	}
//	
//	class RegisterJPanel extends JPanel{
//		protected String value;
//		public RegisterJPanel(String reg, String val) {
//			super();
//			setLayout(new BorderLayout());
//			add(new JLabel(reg), BorderLayout.WEST);
//			add(new JLabel(val), BorderLayout.EAST);
//			value = val;
//		}
//		
//	}
//}

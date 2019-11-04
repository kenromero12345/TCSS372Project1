package tests;
//package application;
//
//import java.awt.EventQueue;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;
//
//import view.Simulator;
//
///**
// * Main driver class for this application.
// */
//public final class Main {
//
//	/**
//	 * Private constructor, to prevent instantiation of this class.
//	 */
//	private Main() {
//		throw new IllegalStateException();
//	}
//
//	/**
//	 * Main method.
//	 * 
//	 * @param theArgs command lone arguments, ignored
//	 */
//	public static void main(final String[] theArgs) {
//		try {
//			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//		} catch (final UnsupportedLookAndFeelException ex) {
//			ex.printStackTrace();
//		} catch (final IllegalAccessException ex) {
//			ex.printStackTrace();
//		} catch (final InstantiationException ex) {
//			ex.printStackTrace();
//		} catch (final ClassNotFoundException ex) {
//			ex.printStackTrace();
//		}
//		UIManager.put("swing.boldMetal", Boolean.FALSE);
//		EventQueue.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				new Simulator();
//			}
//		});
//	}
//}
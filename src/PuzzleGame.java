
public class PuzzleGame {
	// ********** MAIN METHOD **********
	public static void main(String[] args) {
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
		}catch (Exception e) {  /* do nothing */  }
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new GUI();
			}
		});
	}
}

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


@SuppressWarnings("serial")
public class HighScoresFrame extends JFrame {
	// ********** GLOBAL VARIABLES **********
	private JLabel number, number2, name, name2, time, time2, moveCount, moveCount2;
	private JLabel[] filler = new JLabel[4];
	private JLabel[] filler2 = new JLabel[4];
	private JLabel[][] easyLabels;
	private JLabel[][] hardLabels;
	private final SimpleDateFormat SDF = new SimpleDateFormat("mm:ss");
	
	// ********** CONSTRUCTORS **********
	public HighScoresFrame() {
		number = new JLabel("#");
		number2 = new JLabel("#");
		name = new JLabel("Name");
		name2 = new JLabel("Name");
		time = new JLabel("Time (mm:ss)");
		time2 = new JLabel("Time (mm:ss)");
		moveCount = new JLabel("# of Moves");
		moveCount2 = new JLabel("# of Moves");
		for (int x=0; x<filler.length; x++) {  filler[x] = new JLabel("----------");  }
		for (int x=0; x<filler2.length; x++) {  filler2[x] = new JLabel("----------");  }
		easyLabels = new JLabel[10][4];
		hardLabels = new JLabel[10][4];
		
		setTitle("High Scores");
		setSize(375,400);
		setLocation(getStartLocation(this.getWidth(),this.getHeight()));
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(tabbedPane());
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
	}
	
	// ********** METHODS **********
	private java.awt.Point getStartLocation(int frameWidth, int frameHeight) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		java.awt.Dimension screen = tk.getScreenSize();
		java.awt.Point location = new java.awt.Point((screen.width/2)-(frameWidth/2), 
				(screen.height/2)-(frameWidth/2));
		return location;
	}
	private JTabbedPane tabbedPane() {
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Easy Scores", easyScores());
		tabbedPane.addTab("Hard Scores", hardScores());
		return tabbedPane;
	}
	private JPanel easyScores() {
		JPanel panel  = new JPanel(new BorderLayout());
		
		JPanel northPanel = new JPanel(new GridLayout(2,4));
		northPanel.add(number);
		northPanel.add(name);
		northPanel.add(time);
		northPanel.add(moveCount);
		for (int x=0; x<filler.length; x++) {  northPanel.add(filler[x]);  }
		
		JPanel centerPanel = new JPanel(new GridLayout(10,4));
		for (int x=0; x<easyLabels.length; x++) {
			easyLabels[x][0] = new JLabel();
			centerPanel.add(easyLabels[x][0]);
			easyLabels[x][1] = new JLabel();
			centerPanel.add(easyLabels[x][1]);
			easyLabels[x][2] = new JLabel();
			centerPanel.add(easyLabels[x][2]);
			easyLabels[x][3] = new JLabel();
			centerPanel.add(easyLabels[x][3]);
		}
		
		panel.add(northPanel, BorderLayout.NORTH);
		panel.add(centerPanel, BorderLayout.CENTER);
		
		return panel;
	}
	private JPanel hardScores() {
		JPanel panel2 = new JPanel(new BorderLayout());
		
		JPanel northPanel2 = new JPanel(new GridLayout(2,4));
		northPanel2.add(number2);
		northPanel2.add(name2);
		northPanel2.add(time2);
		northPanel2.add(moveCount2);
		for (int x=0; x<filler2.length; x++) {  northPanel2.add(filler2[x]);  }
		
		JPanel centerPanel2 = new JPanel(new GridLayout(10,4));
		for (int x=0; x<easyLabels.length; x++) {
			hardLabels[x][0] = new JLabel();
			centerPanel2.add(hardLabels[x][0]);
			hardLabels[x][1] = new JLabel();
			centerPanel2.add(hardLabels[x][1]);
			hardLabels[x][2] = new JLabel();
			centerPanel2.add(hardLabels[x][2]);
			hardLabels[x][3] = new JLabel();
			centerPanel2.add(hardLabels[x][3]);
		}
		
		panel2.add(northPanel2, BorderLayout.NORTH);
		panel2.add(centerPanel2, BorderLayout.CENTER);
		
		return panel2;
	}
	public void update(HighScore[] highScores) {
		for (int x=0; x<easyLabels.length; x++) {
			easyLabels[x][0].setText("#"+(x+1));
			if (highScores[x].getMoveCount() == 0) {
				easyLabels[x][1].setText("");
				easyLabels[x][2].setText("");
				easyLabels[x][3].setText("");
			}
			else {
				easyLabels[x][1].setText(highScores[x].getName());
				easyLabels[x][2].setText(SDF.format(highScores[x].getTime()));
				easyLabels[x][3].setText(Integer.toString(highScores[x].getMoveCount()));
			}
		}
		for (int x=0; x<hardLabels.length; x++) {
			hardLabels[x][0].setText("#"+(x+1));
			if (highScores[x].getMoveCount() == 0) {
				hardLabels[x][1].setText("");
				hardLabels[x][2].setText("");
				hardLabels[x][3].setText("");
			}
			else {
				hardLabels[x][1].setText(highScores[x].getName());
				hardLabels[x][2].setText(SDF.format(highScores[x].getTime()));
				hardLabels[x][3].setText(Integer.toString(highScores[x].getMoveCount()));
			}
		}
	}
	public void display() {
		setVisible(true);
	}
}
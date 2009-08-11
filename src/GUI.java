import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener, WindowListener {
	// ********** GLOBAL VARIABLES **********
	private final Color ZERO = Color.RED; // 0 Identifier
	private final Color ONE = Color.BLUE; // 1 Identifier
	private final Color TWO = Color.GREEN; // 2 Identifier
	private Field field;
	private HighScore[] highScores = new HighScore[20];
	private int movesNum = 0, difficulty = 2;
	private JButton[] buttons = new JButton[9];
	private HighScoresFrame hsFrame = new HighScoresFrame();
	private JLabel timerLabel, movesLabel;
	private JMenu menu, difficultyMenu;
	private JMenuBar menuBar;
	private JMenuItem newGame, quit, highScoreMenuItem, easy, hard;
	private MenuListener menuListener = new MenuListener();
	private long startTime, currentTime, endTime;
	private final SimpleDateFormat SDF = new SimpleDateFormat("mm:ss");
	private final String HIGH_SCORES = System.getProperty("user.dir")+"/high_scores.txt";
	private Timer timer;
	
	// ********** CONSTRUCTOR **********
	public GUI() {
		for (int x=0; x<buttons.length; x++) {
			buttons[x] = new JButton();
			buttons[x].setName(""+x);
			buttons[x].addActionListener(this);
		}
		
		setTitle("Puzzle Game");
		setSize(400,400);
		setLocation(getStartLocation(400,400));
		setJMenuBar(initMenuBar());
		getContentPane().add(getUI());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		addWindowListener(this);
		
		field = new Field(difficulty);
		updateGUI();
		timer.start();
		startTime = System.currentTimeMillis();
		setVisible(true);
	}
	
	// ********** METHODS **********
	private java.awt.Point getStartLocation(int frameWidth, int frameHeight) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		java.awt.Dimension screen = tk.getScreenSize();
		java.awt.Point location = new java.awt.Point((screen.width/2)-(frameWidth/2), 
				(screen.height/2)-(frameWidth/2));
		return location;
	}
	private JMenuBar initMenuBar() {
		newGame = new JMenuItem("New Game");
		newGame.setMnemonic(KeyEvent.VK_N);
		newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
		newGame.addActionListener(menuListener);
		
		highScoreMenuItem = new JMenuItem("High Scores");
		highScoreMenuItem.setMnemonic(KeyEvent.VK_H);
		highScoreMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_MASK));
		highScoreMenuItem.addActionListener(menuListener);
		initHighScores();
		
		quit = new JMenuItem("Quit");
		quit.setMnemonic(KeyEvent.VK_Q);
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
		quit.addActionListener(menuListener);
		
		easy = new JMenuItem("Easy");
		easy.setMnemonic(KeyEvent.VK_E);
		easy.addActionListener(menuListener);
		
		hard = new JMenuItem("Hard");
		hard.setMnemonic(KeyEvent.VK_H);
		hard.addActionListener(menuListener);
		
		menu = new JMenu("Menu");
		menu.setMnemonic(KeyEvent.VK_M);
		menu.add(newGame);
		menu.add(highScoreMenuItem);
		menu.addSeparator();
		menu.add(quit);
		
		difficultyMenu = new JMenu("Difficulty");
		difficultyMenu.setMnemonic(KeyEvent.VK_D);
		difficultyMenu.add(easy);
		difficultyMenu.add(hard);
		
		menuBar = new JMenuBar();
		menuBar.add(menu);
		menuBar.add(difficultyMenu);
		
		return menuBar;
	}
	private JPanel getUI() {
		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				currentTime=System.currentTimeMillis();
				timerLabel.setText(SDF.format(currentTime - startTime));
			}
		});
		
		movesLabel = new JLabel("Total moves: "+movesNum);
		timerLabel = new JLabel();
		timerLabel.setHorizontalAlignment(JLabel.RIGHT);
		JPanel labelPanel = new JPanel(new BorderLayout());
		labelPanel.add(movesLabel, BorderLayout.WEST);
		labelPanel.add(timerLabel, BorderLayout.EAST);
		
		JPanel gridPanel = new JPanel(new GridLayout(3,3));
		for (int x=0; x<buttons.length; x++) {  gridPanel.add(buttons[x]);  }
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(labelPanel, BorderLayout.NORTH);
		panel.add(gridPanel, BorderLayout.CENTER);
		return panel;
	}
	private void updateGUI() {
		for (int x=0; x<buttons.length; x++) {
			if (field.getPiece(x).getValue() == 0) {  buttons[x].setBackground(ZERO);  }
			else if (field.getPiece(x).getValue() == 1) {  buttons[x].setBackground(ONE);  }
			else {  buttons[x].setBackground(TWO);  }
		}
		movesLabel.setText("Total moves: "+movesNum);
		timerLabel.setText(SDF.format(currentTime-startTime));
	}
	private void enableButtons(boolean bool) {
		for (int x=0; x<buttons.length; x++) {  buttons[x].setEnabled(bool);  }
	}
	private void initHighScores() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(HIGH_SCORES));
			for (int x=0; x<highScores.length; x++) {
				highScores[x] = new HighScore();
				highScores[x].setName(reader.readLine());
				String s = reader.readLine();
				highScores[x].setTime(Long.parseLong(s.trim()));
				highScores[x].setMoveCount(Integer.parseInt(reader.readLine()));
				highScores[x].setDifficulty(Integer.parseInt(reader.readLine()));
			}
		} catch (Exception e) {  e.printStackTrace();  }
		hsFrame.update(highScores);
	}
	private boolean isHighScore(long time) {
		boolean test = false;
		if (difficulty == 2) {
			for (int x=0; x<(highScores.length/2); x++) {
				if (highScores[x].getMoveCount() == 0) {  test = true;  }
				else if (time<highScores[x].getTime()) { test = true;  }
			}
		}
		else {
			for (int x=(highScores.length/2); x<highScores.length; x++) {
				if (highScores[x].getMoveCount() == 0) {  test = true;  }
				else if (time<highScores[x].getTime()) { test = true;  }
			}
		}
		return test;
	}
	private void inputHighScore(long time, String name) {
		int position = 99;
		if (difficulty == 2) {
			for (int x=0; x<(highScores.length/2); x++) {
				if (highScores[x].getMoveCount() == 0) {  position = x; break;  }
				else if (time<highScores[x].getTime()) {  position = x; break;  }
			}
		}
		else {
			for (int x=(highScores.length/2); x<highScores.length; x++) {
				if (highScores[x].getMoveCount() == 0) {  position = x; break;  }
				else if (time<highScores[x].getTime()) {  position = x; break;  }
			}
		}
		HighScore[] temp = new HighScore[highScores.length];
		boolean input = false;
		if (difficulty == 2) {
			for (int x=0; x<(highScores.length/2); x++) {
				if (input) { temp[x] = highScores[x-1];  }
				else {
					if (x == position) {  temp[x] = new HighScore(name, time, movesNum, difficulty); input = true;  }
					else {  temp[x] = highScores[x];  }
				}
			}
			for (int x=(highScores.length/2); x<highScores.length; x++) {  temp[x] = highScores[x];  }
		}
		else {
			for (int x=0; x<(highScores.length/2); x++) {  temp[x] = highScores[x];  }
			for (int x=(highScores.length/2); x<highScores.length; x++) {
				if (input) { temp[x] = highScores[x-1];  }
				else {
					if (x == position) {  temp[x] = new HighScore(name, time, movesNum, difficulty); input = true;  }
					else {  temp[x] = highScores[x];  }
				}
			}
		}
		highScores = temp;
		hsFrame.update(highScores);
	}
	private void saveHighScores() {
		/* Name
		 * Time
		 * Count */
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(HIGH_SCORES));
			for (int x=0; x<highScores.length; x++) {
				writer.write(highScores[x].getName()); writer.newLine();
				writer.write(Long.toString(highScores[x].getTime())); writer.newLine();
				writer.write(Integer.toString(highScores[x].getMoveCount())); writer.newLine();
				writer.write(Integer.toString(highScores[x].getDifficulty())); writer.newLine();
			}
			writer.close();
		}catch (Exception e) {  e.printStackTrace();  }
	}
	
	// ********** ACTION LISTENERS **********
	@Override
	public void actionPerformed(ActionEvent ae) {
		JButton button = (JButton) ae.getSource();
		field.piecePressed(Integer.parseInt(button.getName()));
		movesNum++;
		updateGUI();
		if (field.isComplete()) {
			enableButtons(false);
			timer.stop();
			endTime = currentTime - startTime;
			JOptionPane.showMessageDialog(this, "You've won in "+SDF.format(endTime)+" with "+movesNum+" moves!",
					"Congratulations", JOptionPane.INFORMATION_MESSAGE);
			if (isHighScore(endTime)) {
				String name = JOptionPane.showInputDialog(this, "You've made a high score! Please input your"+
						" name.");
				inputHighScore(endTime, name);
			}
		}
	}
	// MenuBar's Action Listener
	public class MenuListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(newGame)) {  
				field.randomize();
				movesNum = 0;
				enableButtons(true);
				timer.start();
				startTime = System.currentTimeMillis();
				updateGUI();
			}
			else if (ae.getSource().equals(highScoreMenuItem)) {  hsFrame.display();  }
			else if (ae.getSource().equals(quit)) {  dispose();  }
			else if (ae.getSource().equals(easy)) {
				difficulty = 2;
				field = new Field(difficulty);
				movesNum = 0;
				enableButtons(true);
				updateGUI();
				timer.start();
				startTime = System.currentTimeMillis();
			}
			else {
				difficulty = 3;
				field = new Field(difficulty);
				movesNum = 0;
				enableButtons(true);
				updateGUI();
				timer.start();
				startTime = System.currentTimeMillis();
			}
		}
	}
	
	// ********** WINDOW LISTENER **********
	@Override
	public void windowActivated(WindowEvent we) {  /* not used */  }
	@Override
	public void windowClosed(WindowEvent we) {  saveHighScores();  }
	@Override
	public void windowClosing(WindowEvent we) {  saveHighScores();  }
	@Override
	public void windowDeactivated(WindowEvent we) {  /* not used */  }
	@Override
	public void windowDeiconified(WindowEvent we) {  /* not used */  }
	@Override
	public void windowIconified(WindowEvent we) {  /* not used */  }
	@Override
	public void windowOpened(WindowEvent we) {  /* not used */  }
}
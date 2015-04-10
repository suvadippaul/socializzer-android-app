package swingme;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PlayGameSinglePlayer {
	Board ticTacBoard;
	boolean[][] revealed;

	JFrame mainFrame;
	JFrame junkFrame;
	JPanel ticTacPanel;
	JButton[][] Buttons;
	JLabel statusLabel;
	int move;
	int numberOfXWins;
	int numberOfOWins;

	PlayGameSinglePlayer(JFrame frame, Board b) {
		ticTacBoard = b;
		junkFrame = frame;
		junkFrame.setVisible(false);
		move = 0;
		numberOfOWins = 0;
		numberOfXWins = 0;
	}

	PlayGameSinglePlayer(int M) {
		ticTacBoard = new Board(M);
	}

	public void createAndShowGUI() {
		// Creates the main window. JFrame is a generic top level container.
		mainFrame = new JFrame("Game Begins");
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				junkFrame.setVisible(true);

			}
		});
		// We should set a minimum size to make the GUI look a little nicer
		// while making it.
		mainFrame.setMinimumSize(new Dimension(600, 600));

		Container mainPane = mainFrame.getContentPane();

		// Create a menu bar
		JMenuBar mBar = new JMenuBar();
		JMenu menu = new JMenu("Options");
		// Create new game
		JMenuItem newItem = menu.add("New Game - with same board size");
		newItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("In 1");
				newGame();
			}
		});
		// Quit
		JMenuItem quitItem = menu.add("Quit - to change board size");
		quitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("In 2");

				mainFrame.dispose();
				junkFrame.setVisible(true);
			}
		});
		mBar.add(menu);

		mainPane.add(mBar, BorderLayout.PAGE_START);

		// Create status bar
		statusLabel = new JLabel("X's Turn");
		mainPane.add(statusLabel, BorderLayout.PAGE_END);

		// Create a MxN grid layout
		ticTacPanel = new JPanel(new GridLayout(ticTacBoard.M, ticTacBoard.N));

		mainPane.add(ticTacPanel, BorderLayout.CENTER);
		// Pack is required actually decide how components should be laid out.
		// It should be called before the GUI is displayed.
		// mainFrame.pack();
		// Of course, the frame needs to be made visible.
		System.out.println("In 3");

		mainFrame.setVisible(true);
		newGame();

	}

	private void newGame() {
		// Reset the reveal matrix
		move = 0;

		
		System.out.println("move is " + move);
		revealed = new boolean[ticTacBoard.M][ticTacBoard.N];
		for (int i = 0; i < ticTacBoard.M; i++)
			for (int j = 0; j < ticTacBoard.N; j++)
				revealed[i][j] = false;
		for (int i = 0; i < ticTacBoard.M; i++)
			for (int j = 0; j < ticTacBoard.N; j++)
				ticTacBoard.board[i][j] = 0;
		// Remove everything in the panel, and reset it
		ticTacPanel.removeAll();
		Buttons = new JButton[ticTacBoard.M][ticTacBoard.N];

			statusLabel.setText(String.format("X's Turn"));

		for (int i = 0; i < ticTacBoard.M; i++) {
			for (int j = 0; j < ticTacBoard.N; j++) {
				final String coords = String.format("%c%d", 'a' + i, j);
				final int mi = i;
				final int mj = j;
				Buttons[i][j] = new JButton("");
				Buttons[i][j].addMouseListener(new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent e) {					
						if (e.getButton() == MouseEvent.BUTTON1) {
							System.out.println(coords);
							printme();
							if (revealed[mi][mj] == false) {
								revealed[mi][mj] = true;
								revealSquare(mi, mj);
								move = move + 1;
								printme();

							}
						}
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub

					}
				});
				ticTacPanel.add(Buttons[i][j]);
			}
		}
		mainFrame.pack();

	}

	public void printme(){
		int M = ticTacBoard.M;
		for ( int i = 0 ; i < M ; i++){
			for ( int j = 0 ; j< M ; j++)
				System.out.print(ticTacBoard.board[i][j] + " ");
			System.out.println();
		}
		
		for ( int i = 0 ; i < M ; i++){
			for ( int j = 0 ; j< M ; j++)
				System.out.print(revealed[i][j] + " ");
			System.out.println();
		}
	}


	private void revealSquare(int mi, int mj) {
		// TODO Auto-generated method stub
			revealed[mi][mj] = true;
			ticTacBoard.board[mi][mj] = 1;
			Buttons[mi][mj].setText("X");
			Buttons[mi][mj].setBackground(Color.cyan);
			if (didXWin()) {
				statusLabel.setText(String
						.format("X Wins "));
				numberOfXWins++;
				IQuit(1);
			} 
			else if (ticTacBoard.isItFull()) {
				IQuit(0);
			}
			else 
				makeRandomMove();
		

	}

	private void makeRandomMove() {
		// TODO Auto-generated method stub

		int M = ticTacBoard.M;
		int count = 0;
		for (int i = 0; i < M; i++)
			for (int j = 0; j < M; j++)
				if (ticTacBoard.board[i][j] == 0)
					count++;
		Random rand = new Random(System.currentTimeMillis());
		;

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt(count) + 1;
		System.out.println("Random number is " + randomNum);
		int meter = 0;
		if ( count > 0)
		for (int i = 0; i < M; i++)
			for (int j = 0; j < M; j++)
				if (ticTacBoard.board[i][j] == 0) {
					meter++;
					if (meter == randomNum) {
						ticTacBoard.board[i][j] = 2;
						revealed[i][j] = true;
						Buttons[i][j].setText("O");
						Buttons[i][j].setBackground(Color.pink);
						move++;
					}
				}
		if (didOWin()) {
			statusLabel.setText(String
					.format("O Wins "));
			numberOfOWins++;
			IQuit(2);
		}
	}

	private boolean didXWin() {
		boolean flag;

		int size = ticTacBoard.M;
		for (int i = 0; i < size; i++) {
			flag = true;
			for (int j = 0; j < size; j++) {
				if (ticTacBoard.board[i][j] != 1) {
					flag = false;
					break;
				}
			}
			if (flag == true)
				return true;

		}

		for (int j = 0; j < size; j++) {
			flag = true;
			for (int i = 0; i < size; i++) {
				if (ticTacBoard.board[i][j] != 1) {
					flag = false;
					break;
				}
			}
			if (flag == true)
				return true;

		}

		flag = true;
		for (int j = 0; j < size; j++) {
			if (ticTacBoard.board[j][j] != 1) {
				flag = false;
				break;
			}

		}

		if (flag == true)
			return true;

		flag = true;
		for (int j = 0; j < size; j++) {
			if (ticTacBoard.board[size - j - 1][j] != 1) {
				flag = false;
				break;
			}

		}

		if (flag == true)
			return true;

		return false;
	}

	private boolean didOWin() {
		boolean flag;

		int size = ticTacBoard.M;
		for (int i = 0; i < size; i++) {
			flag = true;
			for (int j = 0; j < size; j++) {
				if (ticTacBoard.board[i][j] != 2) {
					flag = false;
					break;
				}
			}
			if (flag == true)
				return true;

		}

		for (int j = 0; j < size; j++) {
			flag = true;
			for (int i = 0; i < size; i++) {
				if (ticTacBoard.board[i][j] != 2) {
					flag = false;
					break;
				}
			}
			if (flag == true)
				return true;

		}

		flag = true;
		for (int j = 0; j < size; j++) {
			if (ticTacBoard.board[j][j] != 2) {
				flag = false;
				break;
			}

		}

		if (flag == true)
			return true;

		flag = true;
		for (int j = 0; j < size; j++) {
			if (ticTacBoard.board[size - j - 1][j] != 2) {
				flag = false;
				break;
			}

		}

		if (flag == true)
			return true;

		return false;
	}

	private void IQuit(int whoWon) {
		// Custom button text
		String XWin = "Number of times X has won till now - " + numberOfXWins;
		String OWin = "Number of times O has won till now - " + numberOfOWins;

		if (whoWon == 1) {
			JOptionPane.showMessageDialog(mainFrame, "X Wins ! !", "X Won",
					JOptionPane.INFORMATION_MESSAGE);

		} else if (whoWon == 2) {
			JOptionPane.showMessageDialog(mainFrame, "O Wins !", "O Won",
					JOptionPane.INFORMATION_MESSAGE);

		} else {
			JOptionPane.showMessageDialog(mainFrame, "Its a draw !",
					"Draw -_-", JOptionPane.INFORMATION_MESSAGE);

		}
		int n = JOptionPane.showConfirmDialog(mainFrame,
				"Would you like to continue the duel with another game ? \n"
						+ XWin + "\n" + OWin, "To Continue Or Not to Continue",
				JOptionPane.YES_NO_OPTION);

		if (n == 0) {
			newGame();
			String Winner = "X Starts";
				statusLabel.setText(String.format("X's Turn"));

				JOptionPane.showMessageDialog(mainFrame, Winner,
						"X Starts", JOptionPane.INFORMATION_MESSAGE);
		} else {
			mainFrame.dispose();
			junkFrame.setVisible(true);
		}

	}
}

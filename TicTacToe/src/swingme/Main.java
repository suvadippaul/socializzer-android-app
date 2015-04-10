package swingme;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Main {

	private JFrame frame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	private JSlider jslider;

	private static int M;

	/**
	 * @param args
	 */

	public Main() {
		M = 3;
		makeFrontScreenGUI();
	}

	private void makeFrontScreenGUI() {
		frame = new JFrame("Tic Tac Toe Game");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.setSize(450, 300);
		frame.setLayout(new GridLayout(3, 1));

		headerLabel = new JLabel("Soup", JLabel.CENTER);
		statusLabel = new JLabel("            Size of Board", JLabel.LEFT);

		statusLabel.setSize(350, 100);
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		jslider = new JSlider(JSlider.HORIZONTAL, 3, 11, 3);
		jslider.setMajorTickSpacing(2);
		jslider.setMinorTickSpacing(1);
		jslider.setPaintTicks(true);
		jslider.setPaintLabels(true);
		Font font = new Font("Serif", Font.ITALIC, 12);
		jslider.setFont(font);

		frame.add(headerLabel);
		frame.add(controlPanel);
		frame.add(statusLabel);
		frame.add(jslider);
		frame.setVisible(true);
	}

	private void showEventDemo() {
		headerLabel.setText("Tic Tac Toe");

		JButton singlePlayerButton = new JButton("Single Player - Random");
		JButton singlePlayerButtonAI = new JButton("Single Player - AI");
		JButton multiplePlayerButton = new JButton("Multiple Player");
		JButton quitButton = new JButton("Quit");

		singlePlayerButton.setActionCommand("Single Player - Random");
		singlePlayerButtonAI.setActionCommand("Single Player - AI");
		multiplePlayerButton.setActionCommand("Multiple Player");
		quitButton.setActionCommand("Quit");

		singlePlayerButton.addActionListener(new ButtonClickListener());
		singlePlayerButtonAI.addActionListener(new ButtonClickListener());
		multiplePlayerButton.addActionListener(new ButtonClickListener());
		quitButton.addActionListener(new ButtonClickListener());
		jslider.addChangeListener(new SliderListener());

		controlPanel.add(singlePlayerButton);
		controlPanel.add(singlePlayerButtonAI);
		controlPanel.add(multiplePlayerButton);
		controlPanel.add(quitButton);

		frame.setVisible(true);
	}

	private class ButtonClickListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if (command.equals("Single Player - Random")) {
				javax.swing.SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						Board b = new Board(M);
						PlayGameSinglePlayer win = new PlayGameSinglePlayer(frame, b);
						win.createAndShowGUI();
					}
				});
			} else if (command.equals("Multiple Player")) {
				javax.swing.SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						Board b = new Board(M);
						PlayGame win = new PlayGame(frame, b);
						win.createAndShowGUI();
					}
				});
			} else if (command.equals("Single Player - AI")) {
				javax.swing.SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						Board b = new Board(M);
						PlayGameSinglePlayerAI win = new PlayGameSinglePlayerAI(frame, b);
						win.createAndShowGUI();
					}
				});
			} else {
				statusLabel.setText("Quiting .....");
				System.exit(0);
			}
		}
	}

	class SliderListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider) e.getSource();
			if (!source.getValueIsAdjusting()) {
				int m = (int) source.getValue();
				M = m;
				System.out.println(m);
			}
		}
	}

	public static void main(String[] args) {

		Main begin = new Main();
		begin.showEventDemo();

	}

}

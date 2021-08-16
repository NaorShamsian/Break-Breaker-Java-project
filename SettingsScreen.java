package main;

import java.awt.BasicStroke;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;

public class SettingsScreen {
	public static GamePanel panel;
	private Image background;
	public static int buttonSize;
	public static final int fontY = 40;
	public int buttonX;
	public int buttonY;
	public static int itemChose;
	public static Rectangle backButton;
	public static SelectOptions mapSizeOptions;
	public static SelectOptions ballSpeedOptions;
	public static SelectOptions ballImageOptions;
	public static SelectOptions orderBricks;

	public SettingsScreen(GamePanel panel) {
		SettingsScreen.panel = panel;
		ImageIcon ii = new ImageIcon("src/images/background2.jpg");
		background = ii.getImage();
		buttonSize = 70;
		buttonX = 20;
		buttonY = 50;
		mapSizeOptions = new SelectOptions(2, 5, this, "mapSizeOptions");
		ballSpeedOptions = new SelectOptions(2, 4, this, "ballSpeedOptions");
		ballImageOptions = new SelectOptions(1, 3, this, "ballImageOptions");
		orderBricks = new SelectOptions(1, 2, this, "orderBricks");
		mapSizeOptions.options = new HashMap<Integer, SettingsScreen.Matrix>();
		ballSpeedOptions.options = new HashMap<Integer, Double>();
		ballImageOptions.options = new HashMap<Integer, Image>();
		orderBricks.options = new HashMap<Integer, Boolean>();
		initMapOptions();
		initBallSpeedOptions();
		initBallImageOptions();
		initOrderBrickOptions();

	}

	@SuppressWarnings("unchecked")
	public void initOrderBrickOptions() {
		orderBricks.options.put(0, false);
		orderBricks.options.put(1, true);
	}

	@SuppressWarnings("unchecked")
	public void initMapOptions() {
		mapSizeOptions.options.put(0, new Matrix(5, 10));
		mapSizeOptions.options.put(1, new Matrix(15, 15));
		mapSizeOptions.options.put(2, new Matrix(15, 20));
		mapSizeOptions.options.put(3, new Matrix(20, 20));
		mapSizeOptions.options.put(4, new Matrix(30, 40));
	}

	@SuppressWarnings("unchecked")
	public void initBallSpeedOptions() {
		ballSpeedOptions.options.put(0, 0.5);
		ballSpeedOptions.options.put(1, 1.0);
		ballSpeedOptions.options.put(2, 2.0);
		ballSpeedOptions.options.put(3, 2.5);

	}

	@SuppressWarnings("unchecked")
	public void initBallImageOptions() {
		ballImageOptions.options.put(0, new ImageIcon("src/images/ball1.png").getImage());
		ballImageOptions.options.put(1, new ImageIcon("src/images/ball2.png").getImage());
		ballImageOptions.options.put(2, new ImageIcon("src/images/ball3.png").getImage());
	}

	public void draw(Graphics g) {
		int distanceFonts = fontY + buttonY + buttonSize + 20;
		Graphics2D g2d = (Graphics2D) g;
		// draw background
		g.drawImage(background, 0, 0, panel.getWidth(), panel.getHeight(), null);
		// draw back button

		backButton = new Rectangle(panel.getWidth() - 100, 1, buttonSize, buttonSize);
		backButton.setBounds(backButton);
		g2d.drawImage(new ImageIcon("src/images/right.png").getImage(), backButton.x, backButton.y, buttonSize,
				buttonSize, null);
		g2d.draw(backButton);
		// draw texts
		Font font = new Font("serif", Font.BOLD, 24);
		g.setFont(font);
		g.setColor(Color.orange);
		g.drawString("Map size level: ", 20, fontY);
		g.drawString("Ball speed: ", 20, distanceFonts);
		g.drawString("Ball Image: ", 20, distanceFonts + 120);
		g.drawString("Randomise bricks: ", 20, distanceFonts + 240);
		// draw map size buttons
		mapSizeOptions.drawOptions(g2d, 0);
		ballSpeedOptions.drawOptions(g2d, distanceFonts - 40);
		ballImageOptions.drawOptions(g2d, distanceFonts + 80);
		orderBricks.drawOptions(g2d, distanceFonts + 200);

	}

	@SuppressWarnings("unchecked")
	public static Matrix getMatrixByIndex(int index) {
		for (Map.Entry<Integer, SettingsScreen.Matrix> entry : ((Map<Integer, SettingsScreen.Matrix>) mapSizeOptions.options)
				.entrySet()) {
			if (Objects.equals(index, entry.getKey())) {
				return entry.getValue();
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static double getValueByIndex(int index) {
		for (Entry<Integer, Double> entry : ((Map<Integer, Double>) ballSpeedOptions.options).entrySet()) {
			if (Objects.equals(index, entry.getKey())) {
				return entry.getValue();
			}
		}
		return -1;
	}

	@SuppressWarnings("unchecked")
	public static Image getImagexByIndex(int index) {
		for (Map.Entry<Integer, Image> entry : ((Map<Integer, Image>) ballImageOptions.options).entrySet()) {
			if (Objects.equals(index, entry.getKey())) {
				return entry.getValue();
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static boolean getBooleanByIndex(int index) {
		for (Map.Entry<Integer, Boolean> entry : ((Map<Integer, Boolean>) orderBricks.options).entrySet()) {
			if (Objects.equals(index, entry.getKey())) {
				return entry.getValue();
			}
		}
		return false;
	}

	public static class Matrix {
		public int row;
		public int col;

		public Matrix(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}

	// mouse events
	public static class MouseClicks implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (GamePanel.currentState == GamePanel.STATE.SETTINGS) {
				int x = e.getX();
				int y = e.getY();
				// map size
				for (int i = 0; i < mapSizeOptions.buttons.length; i++) {
					if (x >= mapSizeOptions.buttons[i].x
							&& x <= mapSizeOptions.buttons[i].x + mapSizeOptions.buttons[i].width)
						if (y >= mapSizeOptions.buttons[i].y
								&& y <= mapSizeOptions.buttons[i].y + mapSizeOptions.buttons[i].height) {
							// player chose map mode

							switch (i) {
							case 0: {
								mapSizeOptions.itemChose = 0;
								break;
							}
							case 1: {
								mapSizeOptions.itemChose = 1;
								break;
							}
							case 2: {
								mapSizeOptions.itemChose = 2;
								break;
							}
							case 3: {
								mapSizeOptions.itemChose = 3;
								break;
							}
							case 4: {
								mapSizeOptions.itemChose = 4;
								break;
							}

							}
						}

				}
				// ball speed
				for (int i = 0; i < ballSpeedOptions.buttons.length; i++) {
					if (x >= ballSpeedOptions.buttons[i].x
							&& x <= ballSpeedOptions.buttons[i].x + ballSpeedOptions.buttons[i].width)
						if (y >= ballSpeedOptions.buttons[i].y
								&& y <= ballSpeedOptions.buttons[i].y + ballSpeedOptions.buttons[i].height) {
							// player chose map mode

							switch (i) {
							case 0: {
								ballSpeedOptions.itemChose = 0;
								break;
							}
							case 1: {
								ballSpeedOptions.itemChose = 1;
								break;
							}
							case 2: {
								ballSpeedOptions.itemChose = 2;
								break;
							}
							case 3: {
								ballSpeedOptions.itemChose = 3;
								break;
							}

							}
						}

				}
				// ball image
				for (int i = 0; i < ballImageOptions.buttons.length; i++) {
					if (x >= ballImageOptions.buttons[i].x
							&& x <= ballImageOptions.buttons[i].x + ballImageOptions.buttons[i].width)
						if (y >= ballImageOptions.buttons[i].y
								&& y <= ballImageOptions.buttons[i].y + ballImageOptions.buttons[i].height) {
							// player chooses image
							switch (i) {
							case 0: {
								ballImageOptions.itemChose = 0;
								break;
							}
							case 1: {
								ballImageOptions.itemChose = 1;
								break;
							}
							case 2: {
								ballImageOptions.itemChose = 2;
								break;
							}

							}
						}

				}
				// Randomize bricks
				for (int i = 0; i < orderBricks.buttons.length; i++) {
					if (x >= orderBricks.buttons[i].x && x <= orderBricks.buttons[i].x + orderBricks.buttons[i].width)
						if (y >= orderBricks.buttons[i].y
								&& y <= orderBricks.buttons[i].y + orderBricks.buttons[i].height) {
							// player chooses image
							switch (i) {
							case 0: {
								orderBricks.itemChose = 0;
								break;
							}
							case 1: {
								orderBricks.itemChose = 1;
								break;
							}

							}
						}

				}
				if (x >= backButton.x && x <= backButton.x + backButton.width) {
					if (y >= backButton.y && y <= backButton.y + backButton.height) {
						// Settings button clicked
						System.out.println("clickkk!!");
						GamePanel.currentState = GamePanel.STATE.MENU;

					}
				}

//
//				if (x >= Menu.quitButton.x && x <= Menu.quitButton.x + Menu.quitButton.width) {
//					if (y >= Menu.quitButton.y && y <= Menu.quitButton.y + Menu.quitButton.height) {
//						// Exit button clicked
//						System.exit(1);
//					}
//				}
				panel.repaint();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}
}

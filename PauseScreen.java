package main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

public class PauseScreen extends Menu {
	private static GamePanel panel;
	private Image background;

	public PauseScreen(GamePanel panel) {
		this.panel = panel;
		ImageIcon ii = new ImageIcon("src/images/background.jpg");
		this.background = ii.getImage();
	}

	public void draw(Graphics g) {
		textX = this.panel.getWidth() / 3;
		textY = this.panel.getHeight() / 4;
		buttonX = textX + textX / 10;
		buttonY = textY + textY / 3;
		playButton = new Rectangle(buttonX, buttonY, BUTTONX, BUTTONY);
		quitButton = new Rectangle(buttonX, buttonY + DISTANCEBETWEENBUTTONS, BUTTONX, BUTTONY);
		Graphics2D g2d = (Graphics2D) g;
		// draw background
		g.drawImage(background, 0, 0, panel.getWidth(), panel.getHeight(), null);
		// draw texts
		Font font = new Font("serif", Font.BOLD, TITLESIZE);
		g.setFont(font);
		g.setColor(Color.orange);
		g.drawString("Game paused", textX, textY);
		// draw buttons
		Font textButton = new Font("arial", Font.CENTER_BASELINE, TEXTSIZE);
		g.setFont(textButton);
		g.drawString("Resume game", playButton.x + playButton.width / 7 - 30,
				playButton.y + playButton.height / 2 + 10);
		g2d.draw(playButton);
		g.drawString("Exit", quitButton.x + quitButton.width / 2 - 30, quitButton.y + quitButton.height / 2 + 10);
		g2d.draw(quitButton);
	}

	public static class MouseClicks implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (GamePanel.currentState == GamePanel.STATE.PAUSE) {
				int x = e.getX();
				int y = e.getY();

				if (x >= Menu.playButton.x && x <= Menu.playButton.x + Menu.playButton.width) {
					if (y >= Menu.playButton.y && y <= Menu.playButton.y + Menu.playButton.height) {
						// Play button clicked
						Robot r = null;
						try {
							r = new Robot();
						} catch (AWTException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						r.keyPress(KeyEvent.VK_BACK_SPACE);
						
					}
				}

				if (x >= Menu.quitButton.x && x <= Menu.quitButton.x + Menu.quitButton.width) {
					if (y >= Menu.quitButton.y && y <= Menu.quitButton.y + Menu.quitButton.height) {
						// Exit button clicked
						GamePanel.currentState = GamePanel.STATE.MENU;
						
						
					    
					}
				}
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
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}

}

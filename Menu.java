package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;

public class Menu {
	public static final int BUTTONX = 265;
	public static final int BUTTONY = 50;
	public static final int TITLESIZE = 100;
	public static final int TEXTSIZE = 35;
	public static final int DISTANCEBETWEENBUTTONS = 100;
	public int textX;
	public int textY;
	public int buttonX;
	public int buttonY;
	public static GamePanel panel;
	public Image background;
	public static Rectangle playButton;
	public static Rectangle quickPlayButton;
	public static Rectangle quitButton;
	public static Rectangle settingsButton;

	public Menu () {}
	public void setPanel (GamePanel panel) {
		this.panel = panel;
	}
	public Menu(GamePanel panel) {
		
		
		this.panel = panel;
		ImageIcon ii = new ImageIcon("src/images/menuBackground.jpg");
		this.background = ii.getImage();

	}

	public void draw(Graphics g) {
	    textX = this.panel.getWidth() / 3;
	    textY = this.panel.getHeight() / 4;
	    buttonX = textX +textX/4 ;
	    buttonY = textY +textY/2;
	    quickPlayButton = new Rectangle(buttonX, buttonY, BUTTONX, BUTTONY);
	    playButton = new Rectangle(buttonX,buttonY+ DISTANCEBETWEENBUTTONS,BUTTONX,BUTTONY);
		settingsButton = new Rectangle(buttonX, buttonY + DISTANCEBETWEENBUTTONS+100, BUTTONX, BUTTONY);
		quitButton = new Rectangle(buttonX, buttonY + DISTANCEBETWEENBUTTONS + 200, BUTTONX, BUTTONY);
		Graphics2D g2d = (Graphics2D) g;
		// draw background
		g.drawImage(background, 0, 0, panel.getWidth(), panel.getHeight(), null);
		// draw texts
		Font font = new Font("serif", Font.BOLD, TITLESIZE);
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString("Brick Breaker", textX, textY);
		// draw buttons
		Font textButton = new Font("arial", Font.CENTER_BASELINE, TEXTSIZE);
		g.setFont(textButton);
		g.drawString("Quick play", quickPlayButton.x + quickPlayButton.width / 2 - 80, quickPlayButton.y + quickPlayButton.height / 2 + 10);
		g2d.draw(quickPlayButton);
		g.drawString("Play", playButton.x + playButton.width / 2 - 30, playButton.y + playButton.height / 2 + 10);
		g2d.draw(playButton);
		g.drawString("Settings", settingsButton.x-30 + settingsButton.width / 2 - 30, settingsButton.y + settingsButton.height / 2 + 10);
		g2d.draw(settingsButton);
		g.drawString("Exit", quitButton.x + quitButton.width / 2 - 30, quitButton.y + quitButton.height / 2 + 10);
		g2d.draw(quitButton);
	}
	

	public static class MouseClicks implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (GamePanel.currentState == GamePanel.STATE.MENU) {
				int x = e.getX();
				int y = e.getY();

				if (x >= Menu.quickPlayButton.x && x <= Menu.quickPlayButton.x + Menu.quickPlayButton.width) {
					if (y >= Menu.quickPlayButton.y && y <= Menu.quickPlayButton.y + Menu.quickPlayButton.height) {
						// Quick Play button clicked
						GamePanel.currentState = GamePanel.STATE.GAME;
						// בוצע !!!!  - לסדר רק בעיות של קביעת גודל מפה כי עושים איניט וזה משנה הכל
						panel.init();		   
					    panel.getPlayer().getPlayerShooter().timeLeft=0;
					    
					}
				}
				if (x >= Menu.playButton.x && x <= Menu.playButton.x + Menu.playButton.width) {
					if (y >= Menu.playButton.y && y <= Menu.playButton.y + Menu.playButton.height) {
						// Play button clicked
						GamePanel.currentState = GamePanel.STATE.LEVELSELECT;
					}
				}
				
				if (x >= Menu.settingsButton.x && x <= Menu.settingsButton.x + Menu.settingsButton.width) {
					if (y >= Menu.settingsButton.y && y <= Menu.settingsButton.y + Menu.settingsButton.height) {
						// Settings button clicked
						GamePanel.currentState = GamePanel.STATE.SETTINGS;
					}
				}

				if (x >= Menu.quitButton.x && x <= Menu.quitButton.x + Menu.quitButton.width) {
					if (y >= Menu.quitButton.y && y <= Menu.quitButton.y + Menu.quitButton.height) {
						// Exit button clicked
						System.exit(1);
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

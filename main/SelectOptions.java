package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import main.GamePanel.STATE;

public class SelectOptions {
	public static final int MAX_LEVEL = 4;
	public SettingsScreen settingsPanel;
	public LevelSelectScreen levelsPanel;
	public int itemChose;
	public Rectangle[] buttons;
	public Map options;
	public String name;

	public SelectOptions(int item, int numOfOptions, SettingsScreen ss, String name) {
		settingsPanel = ss;
		itemChose = item;
		options = new HashMap();
		buttons = new Rectangle[numOfOptions];
		this.name = name;
		initRectanglesArray();

	}

	public SelectOptions(int numOfOptions, LevelSelectScreen ss, String name) {
		itemChose = -1;
		levelsPanel = ss;
		options = new HashMap();
		buttons = new Rectangle[numOfOptions];
		this.name = name;
		initRectanglesArray();

	}

	public void initRectanglesArray() {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new Rectangle();
		}
	}

	public void drawOptions(Graphics2D g2d, int buttonYaddition) {
		if (GamePanel.currentState == STATE.SETTINGS) {
			for (int i = 0; i < buttons.length; i++) {
				buttons[i] = new Rectangle(settingsPanel.buttonX * (i * SettingsScreen.buttonSize / 14),
						settingsPanel.buttonY + buttonYaddition, SettingsScreen.buttonSize + 5,
						SettingsScreen.buttonSize);
				Font options = new Font("arial", Font.BOLD, 24);
				g2d.setFont(options);
				g2d.setColor(Color.orange);
				drawStringOnRectangles(g2d, 2);
				g2d.draw(buttons[i]);
			}
			if (itemChose != -1) {
				g2d.fill(buttons[itemChose]);
				g2d.setColor(Color.white);
				drawStringOnRectangles(g2d, 2); 
			}
		} else if (GamePanel.currentState == STATE.LEVELSELECT) {
			for (int i = 0; i < buttons.length; i++) {
				buttons[i] = new Rectangle(levelsPanel.buttonX * (i * LevelSelectScreen.buttonSize / 14),
						levelsPanel.buttonY + buttonYaddition, LevelSelectScreen.buttonSize + 5,
						LevelSelectScreen.buttonSize);
				Font options = new Font("arial", Font.BOLD, 24);
				g2d.setFont(options);
				g2d.setColor(Color.orange);
				drawStringOnRectangles(g2d, 2);
				g2d.draw(buttons[i]);
			}
			if (itemChose != -1) {
				g2d.fill(buttons[itemChose]);
				g2d.setColor(Color.white);
				drawStringOnRectangles(g2d, 2);
			}
		}
	}

	public void drawStringOnRectangles(Graphics2D g2d, int buttonXaddition) {
		if (GamePanel.currentState == STATE.SETTINGS) {
			if (name.equals("mapSizeOptions")) {
				g2d.drawString("5X10", buttons[0].x + buttons[0].width / 7 - buttonXaddition,
						buttons[0].y + buttons[0].height / 2);
				g2d.drawString("15X15", buttons[1].x + buttons[1].width / 7 - buttonXaddition,
						buttons[1].y + buttons[1].height / 2);
				g2d.drawString("15X20", buttons[2].x + buttons[2].width / 7 - buttonXaddition,
						buttons[2].y + buttons[2].height / 2);
				g2d.drawString("20X20", buttons[3].x + buttons[3].width / 7 - buttonXaddition,
						buttons[3].y + buttons[3].height / 2);
				g2d.drawString("30X40", buttons[4].x + buttons[4].width / 7 - buttonXaddition,
						buttons[4].y + buttons[4].height / 2);
			}

			if (name.equals("ballSpeedOptions")) {
				g2d.drawString("X0.5", buttons[3].x + buttons[3].width / 5, buttons[3].y + buttons[3].height / 2);
				g2d.drawString("X1.0", buttons[2].x + buttons[2].width / 5, buttons[2].y + buttons[2].height / 2);
				g2d.drawString("X2.0", buttons[1].x + buttons[1].width / 5, buttons[1].y + buttons[1].height / 2);
				g2d.drawString("X2.5", buttons[0].x + buttons[0].width / 5, buttons[0].y + buttons[0].height / 2);

			}
			if (name.equals("ballImageOptions")) {
				for (int i = 0; i < buttons.length; i++)
					g2d.drawImage(SettingsScreen.getImagexByIndex(i), buttons[i].x, buttons[i].y, buttons[i].width,
							buttons[i].height, null);
			}
			if (name.equals("ballSpeedOptions")) {
				g2d.drawString("X0.5", buttons[3].x + buttons[3].width / 5, buttons[3].y + buttons[3].height / 2);
				g2d.drawString("X1.0", buttons[2].x + buttons[2].width / 5, buttons[2].y + buttons[2].height / 2);
				g2d.drawString("X2.0", buttons[1].x + buttons[1].width / 5, buttons[1].y + buttons[1].height / 2);
				g2d.drawString("X2.5", buttons[0].x + buttons[0].width / 5, buttons[0].y + buttons[0].height / 2);

			}
			if(name.equals("orderBricks")) {
				g2d.drawString("No", buttons[0].x + buttons[0].width / 5, buttons[0].y + buttons[0].height / 2);
				g2d.drawString("Yes", buttons[1].x + buttons[1].width / 5, buttons[1].y + buttons[1].height / 2);
			}
		} else if (GamePanel.currentState == STATE.LEVELSELECT) {
			if (name.equals("levels")) {
				for (int i = 0; i < buttons.length; i++) {

					g2d.drawString("lvl " + (i+1), buttons[i].x + buttons[i].width / 5,
							buttons[i].y + buttons[i].height / 2);
				}

			}
		}

	}
	
}

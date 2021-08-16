package game_objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import main.GamePanel;
import main.SettingsScreen;

public class MapCreator extends Thread {
	private GamePanel panel;
	public Brick map[][];
	public String customMap[][];
	public int distanceFromTop;
	public int distanceFromLeft;
	public boolean enableOrder;
	public int profitLevel;
	public static final int Finalrow = 50;
	public static final int Finalcol = 50;
	public int row;
	public int col;

	public MapCreator(GamePanel panel) {
		this.panel = panel;
		row = SettingsScreen.getMatrixByIndex(SettingsScreen.mapSizeOptions.itemChose).row;
		col = SettingsScreen.getMatrixByIndex(SettingsScreen.mapSizeOptions.itemChose).col;
		enableOrder = SettingsScreen.getBooleanByIndex(SettingsScreen.orderBricks.itemChose);
		map = new Brick[row][col];
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map[0].length; j++) {
				BrickColors bc = new BrickColors();
				if (enableOrder) {
					Random rnd = new Random();
					map[i][j] = new Brick(bc.getColor(rnd.nextInt(bc.getTotalColors()) + 1));
				} else {
					if (i < row / 3)
						map[i][j] = new Brick(bc.getColor(3));
					else if (i < row / 2)
						map[i][j] = new Brick(bc.getColor(2));
					else
						map[i][j] = new Brick(bc.getColor(1));
				}
				if (row >= 20) {
					map[i][j].setWidth((int) (panel.getWidth() * 0.02));
					map[i][j].setHeight((int) (panel.getHeight() * 0.02));
				} else {
					map[i][j].setWidth((int) (panel.getWidth() * 0.04));
					map[i][j].setHeight((int) (panel.getHeight() * 0.04));
				}

			}

		distanceFromTop = panel.getHeight() / 13;
		distanceFromLeft = panel.getWidth() / 2 - ((col * map[0][0].getWidth()) / 2);
		profitLevel = 3;
		start();
	}

	public MapCreator(GamePanel panel, int row, int col) {
		this.panel = panel;
		this.row = row;
		this.col = col;
		int center = Math.abs((row / 10 - 5));
		map = new Brick[row][col];
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map[0].length; j++) {
				BrickColors bc = new BrickColors();
				if (i < row / 3)
					map[i][j] = new Brick(bc.getColor(3));
				else if (i < row / 2)
					map[i][j] = new Brick(bc.getColor(2));
				else
					map[i][j] = new Brick(bc.getColor(1));
				if (row >= 20) {
					map[i][j].setWidth((int) (panel.getWidth() * 0.02));
					map[i][j].setHeight((int) (panel.getHeight() * 0.02));
				} else {
					map[i][j].setWidth((int) (panel.getWidth() * 0.04));
					map[i][j].setHeight((int) (panel.getHeight() * 0.04));
				}

			}

		distanceFromTop = panel.getHeight() / 13;
		distanceFromLeft = panel.getWidth() / 2 - ((col * map[0][0].getWidth()) / 2);
		profitLevel = 3;
		start();
	}

	public void draw(Graphics2D g) {
		panel.repaint();
		for (int i = 0; i < map.length; i++) { // create the 2D array of
												// bricks
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] != null)
					if (customMap != null) {
						if (customMap[i][j].equals("1")) {
							map[i][j].setX(j * map[i][j].getWidth() + distanceFromLeft
									+ j * (panel.getWidth() / (panel.getWidth() / profitLevel)));
							map[i][j].setY(i * map[i][j].getHeight() + distanceFromTop
									+ i * (panel.getWidth() / (panel.getWidth() / profitLevel)));
							if (map[i][j].getHits() < Brick.MAX_HITS) {
								g.setColor(map[i][j].getBrickColor());
								g.fill3DRect(map[i][j].getX(), map[i][j].getY(), map[i][j].getWidth(),
										map[i][j].getHeight(), true);

							}

							else {
								// draw prizes
								if (map[i][j] != null) {
									if (map[i][j].isPrizeIncluded() && map[i][j].getSelectedPrize().isTouched()) {
										map[i][j].getSelectedPrize().drawPrize(g);
									}

								}
							}

						}
					} else {
						map[i][j].setX(j * map[i][j].getWidth() + distanceFromLeft
								+ j * (panel.getWidth() / (panel.getWidth() / profitLevel)));
						map[i][j].setY(i * map[i][j].getHeight() + distanceFromTop
								+ i * (panel.getWidth() / (panel.getWidth() / profitLevel)));
						if (map[i][j].getHits() < Brick.MAX_HITS) {
							g.setColor(map[i][j].getBrickColor());
							g.fill3DRect(map[i][j].getX(), map[i][j].getY(), map[i][j].getWidth(),
									map[i][j].getHeight(), true);

						}

						else {
							// draw prizes
							if (map[i][j] != null) {
								if (map[i][j].isPrizeIncluded() && map[i][j].getSelectedPrize().isTouched()) {
									map[i][j].getSelectedPrize().drawPrize(g);
								}

							}
						}
					}
				panel.repaint();
			}
		}
	}

	public int getTotalBricks() {
		int result = 0;
		BrickColors bc = new BrickColors();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] != null)
					result += bc.getKeyByValue(map[i][j].getBrickColor());
			}
		}
		return result;
	}
}

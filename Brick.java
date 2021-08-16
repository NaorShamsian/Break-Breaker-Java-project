package game_objects;

import java.awt.Color;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

import main.GamePanel;

public class Brick extends Thread {
	public static int MAX_HITS;
	private Color brickColor;
	private Image wallIMG;
	private int hits;
	private int x;
	private int y;
	private int width;
	private int height;
	public static int numOfBricksPrize;
	private boolean isPrizeIncluded;
	private Prize selectedPrize;
	private boolean interactionFlag;
	private boolean explationDrawed;
	public BrickColors bc;

	public Brick(Color b) {
		brickColor = b;
		bc = new BrickColors();
		MAX_HITS = bc.getTotalColors();
		interactionFlag = true;
			switch (bc.getKeyByValue(brickColor)) {
			case 1: {
				this.hits = 2;
				break;
			}
			case 2: {
				this.hits = 1;
				break;
			}
			case 3: {
				this.hits = 0;
				break;
			}
			}
		
		Random rndPrize = new Random();
		// The chances to make brick with a prize
		int chance = rndPrize.nextInt(200);
		if (chance <= 30) {
			setPrizeIncluded(true);
			numOfBricksPrize++;
		} else
			setPrizeIncluded(false);

	} 

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// whatever the brick is still alive return true else return false
	public boolean brickHit() {
		if (hits < MAX_HITS) {
				hits++;
				// -1 means not include the not able bricks to break
				if (hits != bc.getTotalColors()) {
					brickColor = bc.getColor(MAX_HITS - hits);
				}
			
			return true;
		} else
			return false;
	}
	


	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

//	public boolean isExsit() {
//		return exsit;
//	}
//
//	public void setExsit(boolean exsit) {
//		this.exsit = exsit;
//	}

	public boolean isInteractionFlag() {
		return interactionFlag;
	}

	public void setInteractionFlag(boolean interactionFlag) {
		this.interactionFlag = interactionFlag;
	}

	public Color getBrickColor() {
		return brickColor;
	}

	public Image getWallIMG() {
		return wallIMG;
	}

	public boolean isPrizeIncluded() {
		return isPrizeIncluded;
	}

	public void setPrizeIncluded(boolean isPrizeIncluded) {
		this.isPrizeIncluded = isPrizeIncluded;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Prize getSelectedPrize() {
		return selectedPrize;
	}

	public void setSelectedPrize(Prize selectedPrize) {
		this.selectedPrize = selectedPrize;
	}
	
	public boolean isExplationDrawed() {
		return explationDrawed;
	}

	public void setExplationDrawed(boolean explationDrawed) {
		this.explationDrawed = explationDrawed;
	}

	public void setImage (Image im) {
		this.wallIMG = im;
	}

}

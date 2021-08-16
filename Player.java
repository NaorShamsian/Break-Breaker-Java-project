package game_objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import main.GamePanel;

public class Player extends Thread {

	private GamePanel panel;
	public final int startingPlayerWidth;
	private int playerX;
	private int playerY;
	private int playerWidth;
	private int playerHeight;
	private boolean canShoot;
	private Image playerImage;
	private Lasers playerShooter;
	

	public Player(GamePanel panel) {
		ImageIcon ii = new ImageIcon("src/images/paddle.png");
		this.playerImage = ii.getImage();
		this.panel=panel;
		this.playerX=0;
		this.playerY=panel.getHeight()-80;
		this.playerWidth=170;
		startingPlayerWidth = this.playerWidth;
		this.playerHeight=30;
		this.canShoot=true;
		playerShooter = new Lasers(panel);
		start();
	}
	
	public void run() {
		while (true) {		
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			panel.repaint();
		}
	}
	
	public void moveRight() {
		//play = true;
		playerX += 20;
	}

	public void moveLeft() {
		//play = true;
		playerX -= 20;
	}

	public int getPlayerX() {
		return playerX;
	}

	public Image getImage() {
		return this.playerImage;
	}

	public void setPlayerX(int playerX) {
		this.playerX = playerX;
	}

	public int getPlayerY() {
		return playerY;
	}

	public void setPlayerY(int playerY) {
		this.playerY = playerY;
	}

	public int getPlayerWidth() {
		return playerWidth;
	}

	public void setPlayerWidth(int playerWidth) {
		this.playerWidth = playerWidth;
	}

	public int getPlayerHeight() {
		return playerHeight;
	}

	public void setPlayerHeight(int playerHeight) {
		this.playerHeight = playerHeight;
	}

	public Image getPlayerImage() {
		return playerImage;
	}

	public void setPlayerImage(Image playerImage) {
		this.playerImage = playerImage;
	}
	public void setCanShoot(boolean b) {
		canShoot=b;
		
	}
	public boolean getCanShoot () {
		return canShoot;
	}


	public Lasers getPlayerShooter() {
		return playerShooter;
	}

	public void setPlayerShooter(Lasers playerShooter) {
		this.playerShooter = playerShooter;
	}

	public void drawPlayer (Graphics g)
	{
		g.drawImage(playerImage, playerX, playerY, playerWidth,
				playerHeight, null);
		panel.repaint();
	}

	

}

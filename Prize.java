package game_objects;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;

import java.awt.Graphics;

import main.GamePanel;

enum Types {
	extendPaddle, addHP, addBall, speedUpBall, extendBall, substructBall, paddleShooter;
}

public class Prize extends Thread {
	private GamePanel panel;
	public Types type;
	public static final int numOfLasers = 10;
	private int x;
	private int y;
	private int size;
	private Image image;
	private boolean touched;

	public Prize(GamePanel panel, Types t) {
		this.panel = panel;
		this.size = 50;
		this.type = t;
		this.touched = false;
	}

	public void run() {

		while (true) {
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (panel.isPauseFlag()) {
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
			y++;
			power();

		}
	}

	public void power() {
		// Calculate border outside the paddle to maximize accuracy of
		// interaction with prize
		int YBig = panel.getPlayer().getPlayerY();
		int centerPrizePosX = x + size / 2;
		int centerPrizePosY = y + size / 2;
		if (centerPrizePosX >= panel.getPlayer().getPlayerX()
				&& centerPrizePosX <= panel.getPlayer().getPlayerX() + panel.getPlayer().getPlayerWidth()
				&& centerPrizePosY >= YBig && centerPrizePosY <= panel.getPlayer().getPlayerY()) {
			switch (type) {
			case extendPaddle:
				if (panel.getPlayer().getPlayerWidth() <= panel.getPlayer().startingPlayerWidth * 2)
					panel.getPlayer().setPlayerWidth(panel.getPlayer().getPlayerWidth() + 30); {
				touched = false;

				break;
			}

			case addHP: {
				panel.setLife((panel.getLife() + 1));
				touched = false;

				break;
			}

			case addBall: {
				if (!panel.isFirstLaunch()) {
					Random getRandomBall = new Random();
					if (panel.balls.size() < Ball.maxBallsInGame) {
						int otherBall = getRandomBall.nextInt(panel.balls.size());
						System.out.println("copy from ball " + otherBall);
						Ball newBall = new Ball(panel.balls.get(otherBall));
						panel.balls.add(newBall);
						Thread ballThread = new Thread(newBall);
						ballThread.start();
						touched = false;

						break;
					}
				}
			}
			case speedUpBall: {
				Random getRandomBall = new Random();
				int otherBall = getRandomBall.nextInt(panel.balls.size());
				panel.balls.get(otherBall).speedUpBall();
				touched = false;
				break;
			}

			case extendBall: {
				Random getRandomBall = new Random();
				int otherBall = getRandomBall.nextInt(panel.balls.size());
				if (panel.balls.get(otherBall).getBallSize() <= panel.balls.get(otherBall).getStartingSize() * 3)
					panel.balls.get(otherBall).setBallSize(panel.balls.get(otherBall).getBallSize() + 10);
				touched = false;
				break;
			}

			case substructBall: {
				Random getRandomBall = new Random();
				int otherBall = getRandomBall.nextInt(panel.balls.size());
				if (panel.balls.get(otherBall).getBallSize() >= panel.balls.get(otherBall).getStartingSize() - 10)
					panel.balls.get(otherBall).setBallSize(panel.balls.get(otherBall).getBallSize() - 2);
				touched = false;
				break;
			}

			case paddleShooter: {
				if (panel.getPlayer().getCanShoot()) {
					panel.getPlayer().setCanShoot(false);
					panel.getPlayer().setPlayerShooter(new Lasers(panel));
					panel.getPlayer().getPlayerShooter().lasers.add(new Laser(panel));
					panel.getPlayer().getPlayerShooter().timer.start();
					panel.getPlayer().getPlayerShooter().laserSpeed.start();
					touched = false;
					break;
				}

			}

			}
		}

	}

	public void setNumOfTypes() {

	}

	public void getPosiotionByBrickPosition(int BrickX, int BrickY, int brickW, int brickH) {
		this.x = BrickX + brickW / 2;
		this.y = BrickY + brickH;
	}

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
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

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}

	public void drawPrize(Graphics g) {
		Image img = null;
		switch (type) {
		case extendPaddle: {
			img = new ImageIcon("src/images/power1.png").getImage();
			g.drawImage(img, x, y, size, size, null);
			panel.repaint();
			setImage(img);
			break;
		}

		case addHP: {
			img = new ImageIcon("src/images/power2.png").getImage();
			g.drawImage(img, x, y, size, size, null);
			panel.repaint();
			setImage(img);
			break;
		}

		case addBall: {
			img = new ImageIcon("src/images/power3.png").getImage();
			g.drawImage(img, x, y, size, size, null);
			panel.repaint();
			setImage(img);
			break;
		}

		case speedUpBall: {
			img = new ImageIcon("src/images/power4.png").getImage();
			g.drawImage(img, x, y, size, size, null);
			panel.repaint();
			setImage(img);
			break;
		}

		case extendBall: {
			img = new ImageIcon("src/images/power5.png").getImage();
			g.drawImage(img, x, y, size, size, null);
			panel.repaint();
			setImage(img);
			break;
		}

		case substructBall: {
			img = new ImageIcon("src/images/power6.png").getImage();
			g.drawImage(img, x, y, size, size, null);
			panel.repaint();
			setImage(img);
			break;
		}

		case paddleShooter: {
			img = new ImageIcon("src/images/power7.png").getImage();
			g.drawImage(img, x, y, size, size, null);
			panel.repaint();
			setImage(img);
			break;
		}

		}
	}
}

package game_objects;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import main.GamePanel;

public class Laser extends Thread {
	private GamePanel panel;
	public Image image;
	public int x;
	public int y;
	public int size;
	public boolean touched;
	public boolean drawed;

	public Laser(GamePanel panel) {
		this.panel = panel;
		this.x = (panel.getPlayer().getPlayerX() + panel.getPlayer().getPlayerWidth())
				- (panel.getPlayer().getPlayerWidth() / 2);
		this.y = panel.getPlayer().getPlayerY();
		ImageIcon ii = new ImageIcon("src/images/lazer.png");
		this.image = ii.getImage();
		this.size = 40;
		this.touched = false;
		this.drawed = false;
		start();
	}

	public void run() {
		while (y >= 0 && panel.getPlayer().getPlayerShooter().timeLeft > 0) {
			for (int i = 0; i < panel.getMap().map.length; i++) {
				for (int j = 0; j < panel.getMap().map[0].length; j++) {
					if (panel.getMap().map[i][j] != null) {
						if (panel.getMap().map[i][j].getHits() < Brick.MAX_HITS
								&& panel.getMap().map[i][j].isInteractionFlag()) {
							interaction(panel.getMap().map[i][j]);
							for (int k = 0; k < panel.balls.size(); k++) {
								if (panel.balls.get(k) != null)
									synchronized (panel.balls.get(k)) {
										if (touched) {
											panel.setScore(panel.getScore() + 5);
											GamePanel.totalBricks--;
											if (panel.getMap().map[i][j].isPrizeIncluded()
													&& panel.getMap().map[i][j].getHits() >= Brick.MAX_HITS) {
												panel.getMap().map[i][j].getSelectedPrize().setTouched(true);
												panel.getMap().map[i][j].getSelectedPrize().getPosiotionByBrickPosition(
														panel.getMap().map[i][j].getX(),
														panel.getMap().map[i][j].getY(),
														panel.getMap().map[i][j].getWidth(),
														panel.getMap().map[i][j].getHeight());
												panel.getMap().map[i][j].getSelectedPrize().start();
											}
											currentThread().stop();

										}

									}
							}
						}

					}
				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				y--;

				if (panel.isPauseFlag()) {
					synchronized (this) {
						try {
							wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

				}
				panel.repaint();
			}
		}
		panel.getPlayer().getPlayerShooter().lasers.remove(this);

		// panel.getPlayer().setCanShoot(false);
		// panel.getPlayer().getPlayerShooter().lasers.add(new Laser(panel));
	}
//	public void run() {
//		boolean done = false;
//		while (y >= 0 && panel.getPlayer().getPlayerShooter().timeLeft>0) {
//			panel.getPlayer().getPlayerShooter().removeLaser();
//			for (int i = 0; i < panel.getMap().map.length; i++) {
//				for (int j = 0; j < panel.getMap().map[0].length; j++) {
//					if (panel.getMap().map[i][j].getHits() < Brick.MAX_HITS
//							&& panel.getMap().map[i][j].isInteractionFlag()) {
//						interaction(panel.getMap().map[i][j]);
//						if (touched) {
//							panel.setScore(panel.getScore() + 5);
//							GamePanel.totalBricks--;
//							if (panel.getMap().map[i][j].isPrizeIncluded()
//									&& panel.getMap().map[i][j].getHits() >= Brick.MAX_HITS) {
//								panel.getMap().map[i][j].getSelectedPrize().setTouched(true);
//								panel.getMap().map[i][j].getSelectedPrize().getPosiotionByBrickPosition(
//										panel.getMap().map[i][j].getX(), panel.getMap().map[i][j].getY(),
//										panel.getMap().map[i][j].getWidth(), panel.getMap().map[i][j].getHeight());
//								panel.getMap().map[i][j].getSelectedPrize().start();
//							}
//							if(panel.getPlayer().getPlayerShooter().timeLeft>0)
//							panel.getPlayer().getPlayerShooter().lasers.add(new Laser(panel));
//							panel.getPlayer().getPlayerShooter().removeLaser();
//							stop();
//						}
//
//					}
//
//				}
//			}
//			try {
//				Thread.sleep(1);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			y--;
//
//			if (panel.isPauseFlag()) {
//				synchronized (this) {
//					try {
//						wait();
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//
//			}
//			panel.repaint();
//		}
//		//panel.getPlayer().setCanShoot(false);
//		panel.getPlayer().getPlayerShooter().lasers.add(new Laser(panel));
//	}

	public void interaction(Brick brick) {
//		if (x >= brick.getX() && x <= brick.getX() + brick.getWidth())
//			if (y >= brick.getY() && y <= brick.getY() + brick.getHeight()) {
		if (x + size / 2 >= brick.getX() && x + size / 2 <= brick.getX() + brick.getWidth() && y >= brick.getY()
				&& y <= brick.getY() + brick.getHeight() + size)
			if (brick.brickHit()) {
				touched = true;
			}
	}

	public void draw(Graphics g) {
		g.drawImage(image, x, y, size, size, null);
		panel.repaint();

	}
}

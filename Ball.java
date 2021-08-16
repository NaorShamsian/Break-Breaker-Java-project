package game_objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

import main.GamePanel;
import main.SettingsScreen;

public class Ball implements Runnable {
	public Thread thread;
	private GamePanel panel;
	public static final int maxBallsInGame = 4;
	private double ballSpeed;
	private int id;
	private int ballposX;
	private int ballposY;
	private int size;
	private int startingSize;
	private int delay;
	private int ballXdir;
	private int ballYdir;
	private BallDirections dir;
	private boolean ballTouchedBottomVertex;
	private boolean ballTouchedUpperVertex;
	private Image ballImage;

	public Ball(GamePanel panel) {
		this.panel = panel;
		this.ballImage = SettingsScreen.getImagexByIndex(SettingsScreen.ballImageOptions.itemChose);
		this.id = panel.balls.size();
		this.ballSpeed = 5 * SettingsScreen.getValueByIndex(SettingsScreen.ballSpeedOptions.itemChose);
		this.ballXdir = 0;
		this.ballYdir = 3;
		this.size = 27;
		this.setStartingSize(size);
		this.delay = 0;
		this.dir = new BallDirections(panel);
		this.ballTouchedBottomVertex = false;
		this.ballTouchedUpperVertex = false;
		thread = new Thread(this);
		thread.start();
	}

	public Ball(Ball other) {
		this.panel = other.panel;

		this.ballImage = other.getBallImage();
		this.id = panel.balls.size();
		this.ballXdir = -other.ballXdir;
		this.ballYdir = other.ballYdir;
		this.ballposX = other.ballposX;
		this.ballposY = other.ballposY;
		this.ballSpeed = other.ballSpeed;
		this.size = other.size;
		this.setStartingSize(size);
		this.delay = other.delay;
		this.dir = other.dir;
		this.ballTouchedBottomVertex = other.ballTouchedBottomVertex;
		this.ballTouchedUpperVertex = other.ballTouchedUpperVertex;
	}

	public void run() {

		if (panel != null) {
			while (true) {
				delay++;
				try {
					// cause the ball stay close to the paddle
					if (panel.isFirstLaunch() && panel.balls.indexOf(this) != -1) {
						// System.out.println("index of: " + panel.balls.indexOf(this));

						this.ballXdir = 0;
						this.ballYdir = -3;
						this.ballposX = this.panel.getPlayer().getPlayerX()
								+ (this.panel.getPlayer().getPlayerWidth() / 2);
						this.ballposY = panel.getPlayer().getPlayerY() - size;

					} else {
						if (panel.isPlay()) {
							// System.out.println("Size balls array: " + panel.balls.size());
							// find out if the ball and the player are intersecting

							if (new Rectangle(ballposX, ballposY, size, size)
									.intersects(new Rectangle(panel.getPlayer().getPlayerX(),
											panel.getPlayer().getPlayerY(), panel.getPlayer().getPlayerWidth(), 1))
									&& ballposY + size >= panel.getPlayer().getPlayerY()) {
								ballXdir = dir.setBallDirection(this);
								System.out.println("dir: --> " + dir.setBallDirection(this));
								ballYdir = -ballYdir;

							}
							// find out if the ball and brick are intersecting
							for (int k = 0; k < panel.balls.size(); k++) {
								synchronized (panel.balls.get(k)) {
									for (int i = 0; i < panel.getMap().map.length; i++) {
										for (int j = 0; j < panel.getMap().map[0].length; j++) {
											if (panel.getMap().map[i][j] != null) {
												Rectangle brickRect = new Rectangle(panel.getMap().map[i][j].getX(),
														panel.getMap().map[i][j].getY(),
														panel.getMap().map[i][j].getWidth(),
														panel.getMap().map[i][j].getHeight());
												// Calculate border outside the default brick to maximize accuracy of
												// interaction
												int hBig = brickRect.height + 1;
												int xBig = brickRect.x - 1;
												int wBig = brickRect.width + 1;
												int yBig = brickRect.y - 1;
												int centerballPosX = ballposX + size / 2;
												int centerballPosY = ballposY + size / 2;
												int ballRadius = size / 2;

												if ((centerballPosX >= xBig) && (centerballPosX <= xBig + wBig)
														&& (centerballPosY >= yBig) && (centerballPosY <= yBig + hBig)
														&& (panel.getMap().map[i][j].getHits() < Brick.MAX_HITS)
														&& (panel.getMap().map[i][j].isInteractionFlag())) {

													int vertexBottomLeft = distance(centerballPosX, centerballPosY,
															brickRect.x, brickRect.y + brickRect.height);
													int vertexBottomRight = distance(centerballPosX, centerballPosY,
															brickRect.x + brickRect.width,
															brickRect.y + brickRect.height);
													int vertexUpperLeft = distance(centerballPosX, centerballPosY,
															brickRect.x, brickRect.y);
													int vertexUpperRight = distance(centerballPosX, centerballPosY,
															brickRect.x + brickRect.width, brickRect.y);
													// System.out.println(i + " , " + j);
													
														// System.out.println("Levana");
														if (ballRadius >= vertexUpperRight) {
															ballTouchedUpperVertex = true;
															// ballposX += 3;
//											ballposY += 4;
														} else if (ballRadius >= vertexBottomRight) {
															ballTouchedBottomVertex = true;
//											ballposX -= 3;
//											ballposY -= 4;
														} else if (ballRadius >= vertexUpperLeft) {
															ballTouchedUpperVertex = true;
															// ballposX -= 3;
//											ballposY += 4;
														} else if (ballRadius >= vertexBottomLeft) {
															ballTouchedBottomVertex = true;
//											ballposX += 3;
//											ballposY -= 4;
														}
													// panel.getMap().map[i][j].setInteractionFlag(false);
													// System.out.println(ballTouchedVertex);
													// left and right side
													if (ballTouchedBottomVertex || ballTouchedUpperVertex && ballYdir > 0) {
														if (panel.getMap().map[i][j].brickHit()) {
															// Thread.sleep(700);

															ballYdir = -ballYdir;

															ballXdir = -ballXdir;

															panel.getMap().map[i][j].setInteractionFlag(false);
														}
													} else {
														if (centerballPosX >= brickRect.x + brickRect.width
																|| centerballPosX <= brickRect.x) {
															if (panel.getMap().map[i][j].brickHit()) {

																// ballXdir = (ballXdir > 0) ? 1 : -1;
																ballXdir = -ballXdir;
																// slowBall();
																panel.getMap().map[i][j].setInteractionFlag(false);
																// System.out.println("leftright");
															}
														} else {
															// up and down sides
															if (panel.getMap().map[i][j].brickHit()) {
																ballYdir = -ballYdir;

																panel.getMap().map[i][j].setInteractionFlag(false);
																// System.out.println("updown");
															}

														}

													}
													
														panel.setScore(panel.getScore() + 5);
														GamePanel.totalBricks--;

													

													if (panel.getMap().map[i][j].isPrizeIncluded()
															&& panel.getMap().map[i][j].getHits() >= Brick.MAX_HITS) {
														panel.getMap().map[i][j].getSelectedPrize().setTouched(true);
														panel.getMap().map[i][j].getSelectedPrize()
																.getPosiotionByBrickPosition(
																		panel.getMap().map[i][j].getX(),
																		panel.getMap().map[i][j].getY(),
																		panel.getMap().map[i][j].getWidth(),
																		panel.getMap().map[i][j].getHeight());
														panel.getMap().map[i][j].getSelectedPrize().start();
													}

												}

												ballIsOutside(i, j, centerballPosX, centerballPosY, xBig, yBig, wBig,
														hBig);

											}
										}
									}
								}

							}
							// keeps the ball move anyway
							// {
							
								ballposX += ballXdir;
								ballposY += ballYdir;
							
							// }

							if (ballposX < 0) {
								ballXdir = -ballXdir;
							}
							if (ballposY < 0) {
								ballYdir = -ballYdir;
							}
							if (ballposX+size > panel.getWidth()) {
								ballXdir = -ballXdir;
							}
							if (ballposY > panel.getHeight() && delay >= GamePanel.SECOND) {
								panel.balls.remove(this);
								delay = 0;
								if (panel.balls.size() == 0) {
									panel.getPlayer().getPlayerShooter().timeLeft = 0;
									panel.getPlayer().setCanShoot(true);
									panel.minusLife();
									Ball newBall = new Ball(panel);
									panel.balls.add(newBall);
									panel.setFirstLaunch(true);
									thread = null;
								}
							}
						}

						// if player pressed pause button
						if (panel.isPauseFlag()) {
							synchronized (this) {
								try {
									wait();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}

						}
						// ball speed
						Thread.sleep((long) ballSpeed);

					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				panel.repaint();
			}
		}
	}

	public void speedUpBall() {

		if (ballSpeed > 3) // max speed
			ballSpeed -= 2;

	}

	public int getBallposX() {
		return ballposX;
	}

	public void setBallposX(int ballposX) {
		this.ballposX = ballposX;
	}

	public int getBallposY() {
		return ballposY;
	}

	public void setBallposY(int ballposY) {
		this.ballposY = ballposY;
	}

	public int getBallXdir() {
		return ballXdir;
	}

	public void setBallXdir(int ballXdir) {
		this.ballXdir = ballXdir;
	}

	public GamePanel getGamePanel() {
		return this.panel;
	}

	public int getBallYdir() {
		return ballYdir;
	}

	public void setBallYdir(int ballYdir) {
		this.ballYdir = ballYdir;
	}

	public Image getBallImage() {
		return ballImage;
	}

	public void setBallImage(Image ballImage) {
		this.ballImage = ballImage;
	}

	public int getBallSize() {
		return size;
	}

	public int getStartingSize() {
		return startingSize;
	}

	public void setStartingSize(int startingSize) {
		this.startingSize = startingSize;
	}

	public void setBallSize(int size) {
		this.size = size;
	}

	public int getId() {
		return this.id;
	}

	public boolean ballIsOutside(int i, int j, int centerballPosX, int centerballPosY, int xBig, int yBig, int wBig,
			int hBig) {
		// if center ball pos is outside the big rect set InteractionFlag to true
		if (!((centerballPosX >= xBig) && (centerballPosX <= xBig + wBig) && (centerballPosY >= yBig)
				&& (centerballPosY <= yBig + hBig) && (panel.getMap().map[i][j].getHits() < Brick.MAX_HITS))) {
			panel.getMap().map[i][j].setInteractionFlag(true);
			ballTouchedBottomVertex = false;
			ballTouchedUpperVertex = false;
			return true;
		}
		return false;
	}

	public int distance(int x1, int y1, int x2, int y2) {
		return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
	}

	public void drawBall(Graphics g) {
		g.drawImage(ballImage, ballposX, ballposY, size, size, null);

	}
}

package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import game_objects.Ball;
import game_objects.MapCreator;
import game_objects.Laser;
import game_objects.Lasers;
import game_objects.Player;
import game_objects.Prize;
import game_objects.Prizes;
import main.GamePanel.STATE;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {
	public static final int SECOND = 1000;
	private Image backGroundImage;
	private boolean firstPlayerInstance;
	private boolean firstLaunch;
	private boolean PauseFlag;
	public boolean levelDone;
	private boolean play;
	private Timer gameTimer;
	private int timeLeft;
	private int score;
	private int life;
	public static int totalBricks;
	private Player player;
	public ArrayList<Ball> balls;
	private MapCreator map;
	public Prizes prizes;

	public static enum STATE {
		MENU, PAUSE, GAME, SETTINGS, LEVELSELECT;
	}

	public static STATE currentState = STATE.MENU;
	public Menu menu;
	public PauseScreen pause;
	public SettingsScreen settings;
	public LevelSelectScreen levelSelect;
	public GamePanel startingGamePanel = this;

	public GamePanel() {
		// Settings can be changed (not routine)
		this.settings = new SettingsScreen(this);
		this.levelSelect = new LevelSelectScreen(this);
		// Rest of game basic element are routine (like ball is moving)
		init();
		addKeyListener(new KL());
		addMouseMotionListener(new MML());
		addMouseListener(new ML());
		addMouseListener(new Menu.MouseClicks());
		addMouseListener(new PauseScreen.MouseClicks());
		addMouseListener(new SettingsScreen.MouseClicks());
		setFocusable(true);
	}

	public void init() {
		firstPlayerInstance = true;
		timeLeft = 200;
		firstLaunch = true;
		PauseFlag = false;
		life = 3;
		play = true;
		score = 0;
		this.menu = new Menu(this);
		this.pause = new PauseScreen(this);
		this.map = new MapCreator(this);
		this.prizes = new Prizes(this);
		this.player = new Player(this);
		this.balls = new ArrayList<Ball>();
		this.balls.add(new Ball(this));
		ImageIcon ii = new ImageIcon("src/images/background.jpg");
		this.backGroundImage = ii.getImage();
		gameTimer = new Timer(SECOND, new AL());
	}

	public void init(String[][] newMap, int row, int col) {
		firstPlayerInstance = true;
		if(gameTimer.isRunning())
			gameTimer.stop();
		timeLeft = 200;
		firstLaunch = true;
		PauseFlag = false;
		life = 3;
		play = true;
		score = 0;
		this.menu = new Menu(this);
		this.pause = new PauseScreen(this);
		this.map = new MapCreator(this, row, col);
		if (LevelSelectScreen.isCustomMap)
			map.customMap = newMap;
		this.prizes = new Prizes(this);
		this.player = new Player(this);
		this.balls = new ArrayList<Ball>();
		this.balls.add(new Ball(this));
		ImageIcon ii = new ImageIcon("src/images/background.jpg");
		this.backGroundImage = ii.getImage();
		gameTimer = new Timer(SECOND, new AL());
	}

	public void paintComponent(Graphics g) {
		System.out.println("chose: " + levelSelect.levelSelectOptions.itemChose);
		if (currentState == STATE.MENU) {
			showMouseCursor();
			if (getWidth() > 0) {
//				this.map = new MapCreator(this);
//				this.prizes = new Prizes(this);
				menu.draw(g);
			}
		} else if (currentState == STATE.GAME) {
			hideMouseCursor();
			if (play) {
				super.paintComponent(g);
				if (getWidth() > 0 && firstPlayerInstance) {

					// System.out.println(map.getTotalBricks());
					totalBricks = map.getTotalBricks();
					player.setPlayerX(0);
					player.setPlayerY(getHeight() - 80);
					firstPlayerInstance = false;
				}

				// draw background
				g.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), null);
				// draw player (paddle)
				player.drawPlayer(g);
				// draw lasers if it should be
				if (player.getPlayerShooter() != null)
					for (int i = 0; player.getPlayerShooter().lasers.size() != -1
							&& i < player.getPlayerShooter().lasers.size(); i++) {
						if (!player.getPlayerShooter().lasers.get(i).touched
								&& player.getPlayerShooter().lasers.get(i).isAlive()
								&& player.getPlayerShooter().timeLeft > 0) {
							player.getPlayerShooter().lasers.get(i).draw(g);

						}

					}
				// draw ball/balls
				for (int i = 0; i < balls.size(); i++) {
					balls.get(i).drawBall(g);
				}
				// draw map
				map.draw((Graphics2D) g);

				// draw score boards
				g.setColor(Color.white);
				g.setFont(new Font("Arial", Font.BOLD, 25));
				g.drawString("Score: " + score, 20, 30);
				g.drawString("Time left: " + timeLeft, getWidth() - 180, 30);
				// if player took the laser prize:
				if (!player.getCanShoot() && player.getPlayerShooter().timeLeft > 0)
					g.drawString("Laser time: " + player.getPlayerShooter().timeLeft, getWidth() - 400, 30);
				else
					player.setCanShoot(true);
				g.drawString("Life: " + life, 200, 30);

				// draw pause screen
				if (PauseFlag) {
					currentState = STATE.PAUSE;
//					g.setColor(new Color(255, 255, 255, 80));
//					g.fillRect(0, 0, getWidth(), getHeight());

				}
				// draw time left message
				if (timeLeft <= 0) {
					play = false;
					g.setColor(new Color(255, 255, 255, 80));
					g.fillRect(0, 0, getWidth(), getHeight());
					g.setColor(Color.white);
					g.setFont(new Font("Arial", Font.BOLD, 50));
					g.drawString("Time left, your Score is: " + score, getWidth() / 2 - getWidth() / 5,
							getHeight() / 2);
					g.setColor(new Color(255, 255, 255, 80));
					g.fillRect(0, 0, getWidth(), getHeight());
				}
				// draw win screen
				if (totalBricks <= 0) {
					if (levelSelect.levelSelectOptions.itemChose != -1) {
						levelDone = true;
						play = false;
						g.setColor(Color.GREEN);
						g.setFont(new Font("Arial", Font.BOLD, 50));
						g.drawString("You won well done, your Score is: " + score,
								getWidth() / 2 - getWidth() / 5, getHeight() / 2);
						g.drawString("to next level press c", getWidth() / 2 - getWidth() / 5, getHeight() / 2+130);
						g.setColor(new Color(255, 255, 255, 90));
						g.fillRect(0, 0, getWidth(), getHeight());
					} else {
						play = false;
						g.setColor(Color.GREEN);
						g.setFont(new Font("Arial", Font.BOLD, 50));
						g.drawString("You won well done, your Score is: " + score, getWidth() / 2 - getWidth() / 5,
								getHeight() / 2);
						g.setColor(new Color(255, 255, 255, 90));
						g.fillRect(0, 0, getWidth(), getHeight());
					}
				}
				// draw lose screen
				if (life == 0) {
					play = false;
					g.setColor(new Color(255, 255, 255, 80));
					g.fillRect(0, 0, getWidth(), getHeight());
					g.setColor(Color.red);
					g.setFont(new Font("Arial", Font.BOLD, 50));
					g.drawString("You lost, your Score is: : " + score, getWidth() / 2 - getWidth() / 5,
							getHeight() / 2);
					g.setColor(new Color(255, 255, 255, 90));
					g.fillRect(0, 0, getWidth(), getHeight());

				}
				repaint();
			}
		} else if (currentState == STATE.PAUSE) {
			showMouseCursor();
			if (getWidth() > 0 && PauseFlag) {

				pause.draw(g);
			} else
				currentState = STATE.GAME;
		} else if (currentState == STATE.SETTINGS) {
			showMouseCursor();
			if (getWidth() > 0) {
				settings.draw(g);
			}
		} else if (currentState == STATE.LEVELSELECT) {
			showMouseCursor();
			if (getWidth() > 0) {
				levelSelect.draw(g);
			}
		}
	}

	public void hideMouseCursor() {
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorimg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorimg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JPanel.
		setCursor(blankCursor);
	}

	public void showMouseCursor() {
		ImageIcon ii = new ImageIcon("src/images/mycursor.png");
		Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(ii.getImage(), new Point(0, 0),
				"customCursor");
		setCursor(customCursor);
	}

	class MML extends MouseMotionAdapter {
		public void mouseMoved(MouseEvent e) {
			if (isPlay() && !PauseFlag && currentState == STATE.GAME) {
				player.setPlayerX(e.getX());
				if (e.getX() >= 0)
					player.moveLeft();
				else
					player.setPlayerX(0);
				if (e.getX() + player.getPlayerWidth() <= getWidth())
					player.moveRight();
				else
					player.setPlayerX(getWidth() - player.getPlayerWidth());
			}
			e.consume();

		}
	}

	class ML extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			int b = e.getButton();
			if (b == MouseEvent.BUTTON1 && firstLaunch && !PauseFlag && currentState == STATE.GAME) {
				// start game timer and release the ball
				gameTimer.start();
				firstLaunch = false;

			}
			if (GamePanel.currentState == GamePanel.STATE.LEVELSELECT) {
				int x = e.getX();
				int y = e.getY();
				// level select
				for (int i = 0; i < levelSelect.levelSelectOptions.buttons.length; i++) {
					if (x >= levelSelect.levelSelectOptions.buttons[i].x && x <= levelSelect.levelSelectOptions.buttons[i].x + levelSelect.levelSelectOptions.buttons[i].width)
						if (y >= levelSelect.levelSelectOptions.buttons[i].y
								&& y <= levelSelect.levelSelectOptions.buttons[i].y + levelSelect.levelSelectOptions.buttons[i].height) {
							levelSelect.levelSelectOptions.itemChose = i;	
							levelSelect.LevelBuilder();
							GamePanel.currentState = GamePanel.STATE.GAME;
							
						}
				}
				// back button pressed
				if (x >= levelSelect.backButton.width && x <= levelSelect.backButton.x + levelSelect.backButton.width) {
					if (y >= levelSelect.backButton.y && y <= levelSelect.backButton.y + levelSelect.backButton.height) {
						// Settings button clicked
						System.out.println("clickkk!!");
						GamePanel.currentState = GamePanel.STATE.MENU;

					}
				}
			}
		}
	}

	class KL extends KeyAdapter {
		public void keyPressed(KeyEvent e) {

			int code = e.getKeyCode();
			if (code == KeyEvent.VK_ESCAPE) {
				System.exit(1);
			}

			if (code == KeyEvent.VK_ENTER && currentState == STATE.GAME) {
				if (!PauseFlag) {
					PauseFlag = true;
					gameTimer.stop();

					repaint();
				}
			}
			if (code == KeyEvent.VK_C && levelDone) {
				if (levelSelect.levelSelectOptions.itemChose < SelectOptions.MAX_LEVEL) {
					levelSelect.levelSelectOptions.itemChose++;
					levelSelect.LevelBuilder();
					
					GamePanel.currentState = GamePanel.STATE.GAME;
				}

			}

			if (code == KeyEvent.VK_BACK_SPACE && currentState == STATE.PAUSE) {
				if (PauseFlag) {
					for (int i = 0; i < balls.size(); i++) {
						synchronized (balls.get(i)) {
							balls.get(i).notify();
						}
					}
					for (int i = 0; i < player.getPlayerShooter().lasers.size(); i++) {
						synchronized (player.getPlayerShooter().lasers.get(i)) {
							player.getPlayerShooter().lasers.get(i).notify();
						}
					}
					for (int i = 0; i < map.map.length; i++) {
						for (int j = 0; j < map.map[0].length; j++) {
							if (map.map[i][j].getSelectedPrize() != null) {
								synchronized (map.map[i][j].getSelectedPrize()) {
									map.map[i][j].getSelectedPrize().notify();
								}
							}
						}
					}
				}
				gameTimer.start();
				PauseFlag = false;
			}

		}
	}

	class AL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (timeLeft > 0)
				timeLeft--;
			repaint();
		}

	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public MapCreator getMap() {
		return this.map;
	}

	public boolean isFirstLaunch() {
		return firstLaunch;
	}

	public void setFirstLaunch(boolean firstLaunch) {
		this.firstLaunch = firstLaunch;
	}

	public void setMap(MapCreator map) {
		this.map = map;
	}

	public boolean isPlay() {
		return play;
	}

	public void setPlay(boolean play) {
		this.play = play;
	}

	public boolean isPauseFlag() {
		return PauseFlag;
	}

	public void setPauseFlag(boolean PauseFlag) {
		this.PauseFlag = PauseFlag;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Timer getGameTimer() {
		return gameTimer;
	}

	public void setGameTimer(Timer gameTimer) {
		this.gameTimer = gameTimer;
	}

	public int getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public void minusLife() {
		if (life > 0)
			--life;
	}

	public static void main(String[] args) {
		JFrame f = new JFrame("Brick Breaker Naor Shamsian");
		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = ((int) tk.getScreenSize().getWidth());
		int ySize = ((int) tk.getScreenSize().getHeight());
		f.setSize(xSize, ySize);
		GamePanel bp = new GamePanel();
		f.add(bp);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setUndecorated(true);
		f.setResizable(false);
		f.setVisible(true);
		f.setFocusable(false);
	}

}

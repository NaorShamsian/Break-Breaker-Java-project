package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.ImageIcon;

import game_objects.Brick;
import game_objects.MapCreator;
import main.SettingsScreen.Matrix;

public class LevelSelectScreen {
	public GamePanel panel;
	private Image background;
	public static int buttonSize;
	public int buttonX;
	public int buttonY;
	public static Rectangle backButton;
	public SelectOptions levelSelectOptions;
	public static boolean isCustomMap;

	public LevelSelectScreen(GamePanel panel) {
		this.panel = panel;
		ImageIcon ii = new ImageIcon("src/images/background2.jpg");
		background = ii.getImage();
		buttonSize = 70;
		buttonX = 20;
		buttonY = 50;
		levelSelectOptions = new SelectOptions(4, this, "levels");
		levelSelectOptions.options = new HashMap<Integer, String>();
	}

	// להמשיך
	@SuppressWarnings("unchecked")
	public void initLevelsOptions() {
		for (int i = 0; i < levelSelectOptions.buttons.length; i++) {
			levelSelectOptions.options.put(i, "level" + (i + 1));
		}

	}

	public void draw(Graphics g) {
		// draw background
		g.drawImage(background, 0, 0, panel.getWidth(), panel.getHeight(), null);
		Graphics2D g2d = (Graphics2D) g;
		// draw back button

		backButton = new Rectangle(panel.getWidth() - 100, 1, buttonSize, buttonSize);
		backButton.setBounds(backButton);
		g2d.drawImage(new ImageIcon("src/images/right.png").getImage(), backButton.x, backButton.y, buttonSize,
				buttonSize, null);
		g2d.draw(backButton);
		levelSelectOptions.drawOptions(g2d, 20);
	}


		
			public void LevelBuilder()
			{
				int row,col;
				switch (levelSelectOptions.itemChose) {
				case 0:
					String[][] customArr = { 			
							{"1","1","1"},
							{"1","0","1"}};
					isCustomMap=true;	
					 row = customArr.length ;
				     col=customArr[0].length;
					panel.balls=null;
					panel.init(customArr,row,col);
					break;
				case 1:
					String[][] customArr1 = { 
							
							{"1","1","1"},
							{"0","1","0"}};
					isCustomMap=true;	
					 row = customArr1.length ;
				     col=customArr1[0].length;
					panel.balls=null;
					panel.init(customArr1,row,col);
					break;
				case 2:
					int[][] customArr2 = { 
							
							{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
							{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
							{0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,1,1,1},
							{0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,1,1,1,0,0,1,1,1,1},
							{0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,1,1,1,1,1,0,0,1,1,1},
							{0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,6,0,0,1,1,1,0,0,0,0,0,2},
							{0,0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
							{0,0,1,1,1,1,1,0,0,0,0,0,2,1,2,0,0,0,0,0,0,0,0,0,0,0,0},
							{0,0,0,1,1,1,0,0,0,0,3,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
					String [][]customArr21 = new String [customArr2.length][customArr2[0].length];
					for(int i=0;i<customArr2.length;i++)
						for(int j=0;j<customArr2[0].length;j++)
						{
							int val = customArr2[i][j];
							customArr21[i][j] = Integer.toString(val);
						}
					isCustomMap=true;	
					 row = customArr2.length ;
				     col=customArr2[0].length;
					panel.balls=null;
					panel.init(customArr21,row,col);
					break;
				case 3:
					String[][] customArr3 = { 
							{"1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1"},
							{"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
							{"1","0","1","0","0","0","1","1","1","0","1","1","1","0","1","1","1","1","1","0","0","0","1"},
							{"1","0","1","0","0","0","1","0","1","0","1","0","0","0","0","0","1","0","0","0","0","0","1"},
							{"1","0","1","0","0","0","1","1","1","0","1","0","0","0","0","0","1","0","0","0","0","0","1"},
							{"1","0","1","0","0","0","1","0","1","0","1","1","1","0","0","0","1","0","0","0","0","0","1"},
							{"1","0","1","0","0","0","1","0","1","0","0","0","1","0","0","0","1","0","0","0","0","0","1"},
							{"1","0","1","0","0","0","1","0","1","0","0","0","1","0","0","0","1","0","0","0","0","0","1"},
							{"1","0","1","1","1","0","1","0","1","0","1","1","1","0","0","0","1","0","0","0","0","0","1"},
							{"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
							{"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
							{"1","0","1","0","0","0","1","1","1","0","1","0","1","0","1","1","1","0","1","0","0","0","1"},
							{"1","0","1","0","0","0","1","0","0","0","1","0","1","0","1","0","0","0","1","0","0","0","1"},
							{"1","0","1","0","0","0","1","0","0","0","1","0","1","0","1","0","0","0","1","0","0","0","1"},
							{"1","0","1","0","0","0","1","1","1","0","1","0","1","0","1","1","1","0","1","0","0","0","1"},
							{"1","0","1","0","0","0","1","0","0","0","1","0","1","0","1","0","0","0","1","0","0","0","1"},
							{"1","0","1","0","0","0","1","0","0","0","1","0","1","0","1","0","0","0","1","0","0","0","1"},
							{"1","0","1","1","1","0","1","1","1","0","1","1","1","0","1","1","1","0","1","1","1","0","1"},
							{"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
							{"1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1"}};
					isCustomMap=true;	
					 row = customArr3.length ;
				     col=customArr3[0].length;
					panel.balls=null;
					panel.prizes=null;
					panel.init(customArr3,row,col);
					break;
				}
			}
	
}

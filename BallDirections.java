package game_objects;

import main.GamePanel;

public class BallDirections {
	public GamePanel panel;
	public static final int NUMOFDIRECTIONS = 7;
	public int directions[];

	public BallDirections(GamePanel panel) {
		this.panel = panel;
		directions = new int[NUMOFDIRECTIONS];
		directions[0] = -3;
		directions[1] = -2;
		directions[2] = -1;
		directions[3] = 0;
		directions[4] = 1;
		directions[5] = 2;
		directions[6] = 3;
	}

	public int setBallDirection(Ball ball) {
		// Calculate the average of paddle with number of directions
		int avgDir = panel.getPlayer().getPlayerWidth() / NUMOFDIRECTIONS;
		int avg = avgDir;
		int j = 0;
		for (int i = 0; i < panel.balls.size(); i++) {
			while (j < directions.length) {

				int current = avgDir + panel.getPlayer().getPlayerX(),
						next = avgDir + avg + panel.getPlayer().getPlayerX();
				int ballCenter = panel.balls.get(i).getBallposX() + panel.balls.get(i).getBallSize() / 2;
				if (ballCenter >= current && ballCenter <= next && avgDir < panel.getPlayer().getPlayerWidth())
					return directions[j];

				if (ballCenter < current-10)
					return directions[0];

				j++;
				avgDir += avg;
			}
		}

		return -1;

	}
}

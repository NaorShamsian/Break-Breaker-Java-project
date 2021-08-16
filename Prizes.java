package game_objects;

import java.util.Random;

import main.GamePanel;

public class Prizes {
	public Prize[] prizes;
	public GamePanel panel;
	public int currentPrize;

	public void setPanel(GamePanel panel) {
		this.panel = panel;
	}

	public Prizes(GamePanel panel) {
		this.panel = panel;
		prizes = new Prize[Brick.numOfBricksPrize];
		this.currentPrize = 0;
		Random rndPrize = new Random();
		for (currentPrize = 0; currentPrize < prizes.length; currentPrize++) {
			int chance = rndPrize.nextInt(200);
			System.out.println("chance: " + chance);
			System.out.println("k=" + currentPrize);
			Types randomType = Types.values()[(int) (Math.random() * Types.values().length)];
			if (chance >= 0 && chance <= 20)
				prizes[currentPrize] = new Prize(panel, Types.paddleShooter);
			else if (chance > 20 && chance <= 40)
				prizes[currentPrize] = new Prize(panel, Types.addBall);
			else if (chance > 40 && chance <= 50)
				prizes[currentPrize] = new Prize(panel, Types.addHP);
			else if (chance > 50 && chance <= 85)
				prizes[currentPrize] = new Prize(panel, Types.substructBall);
			else if (chance > 85 && chance <= 120)
				prizes[currentPrize] = new Prize(panel, Types.extendBall);
			else if (chance > 120 && chance <= 140)
				prizes[currentPrize] = new Prize(panel, Types.speedUpBall);
			else if (chance > 140 && chance <= 200)
				prizes[currentPrize] = new Prize(panel, Types.paddleShooter);
		}
		currentPrize = 0;
		if (panel.getMap().map != null)
			for (int i = 0; i < panel.getMap().map.length; i++)
				for (int j = 0; j < panel.getMap().map[0].length; j++) {
					if (panel.getMap().map[i][j] != null) {
						if (panel.getMap().map[i][j].isPrizeIncluded() && currentPrize < Brick.numOfBricksPrize) {
							System.out.println("prizes[" + currentPrize + "]");
							panel.getMap().map[i][j].setSelectedPrize(prizes[currentPrize]);
							currentPrize++;
						}
					}

				}

	}
}

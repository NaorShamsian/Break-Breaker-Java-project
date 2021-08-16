package game_objects;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import main.GamePanel;

public class Lasers {
	public GamePanel panel;
	public Timer timer;
	public Timer laserSpeed;
	public int timeLeft;
	public ArrayList<Laser> lasers;

	public Lasers(GamePanel panel) {
		lasers = new ArrayList<Laser>();
		this.panel = panel;
		timeLeft=15;
		timer=  new Timer(GamePanel.SECOND,new AL());
		laserSpeed = new Timer(GamePanel.SECOND/5,new AL_B());

 	}

	public void removeLaser() {
		for (int i = 0; i < lasers.size(); i++) {
			if (lasers.get(i).y <= 0 || lasers.get(i).touched) {
				lasers.remove(lasers.get(i));		
			}
		}
		
	}
	class AL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (timeLeft > 0)
			{
				timeLeft--;
			}
			panel.repaint();
		}

	}
	class AL_B implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (timeLeft > 0)
			{
				panel.getPlayer().getPlayerShooter().lasers.add(new Laser(panel));
			}
			panel.repaint();
		}

	}
	
}

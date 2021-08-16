package game_objects;

import java.awt.Color;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ImageIcon;

import java.util.Objects;

public class BrickColors {
	private Map<Integer, Color> states = new HashMap<Integer, Color>();

	public BrickColors() {
		states.put(1, Color.RED);
		states.put(2, Color.YELLOW);
		states.put(3, Color.GREEN);
	}

	public Color getColor(int num) {
		return states.get(num);
	}

	public int getKeyByValue(Color c) {
		for (Entry<Integer, Color> entry : states.entrySet()) {
			if (Objects.equals(c, entry.getValue())) {
				return entry.getKey();
			}
		}
		return 0;
	}

	public int getTotalColors() {
		return states.size();
	}

}

package com.slotmachine.menu.components;

import com.slotmachine.menu.MainMenu;
import org.newdawn.slick.Color;

public class Label implements Component {

	int x = 0, y = 0;
	String label = "";
	Color color;

	public Label(String theLabel, int xPos, int yPos) {
		label = theLabel;
		x = xPos;
		y = yPos;
		color = Color.white;
	}

	public Label(String theLabel, int xPos, int yPos, Color c) {
		label = theLabel;
		x = xPos;
		y = yPos;
		color = c;
	}

	public String getLabelText() {
		return label;
	}

	@Override
	public String getToolTip() {
		return null;
	}

	@Override
	public void setToolTip(String tip) {

	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public void setX(int xPos) {
		x = xPos;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setY(int yPos) {
		y = yPos;

	}

	public void setLabel(String theLabel) {
		label = theLabel;
	}

	@Override
	public void draw() {
		MainMenu.textureLoader.textBoxTitle.drawString(x, y, label, color);
	}

}

package com.slotmachine.menu.components;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;

import com.slotmachine.api.Images;
import com.slotmachine.main.Main;
import com.slotmachine.menu.MainMenu;

public class Button implements Component {

	private String toolTip = "", button = "";
	private int xPos = 0, yPos = 0;
	private Boolean hit = false;
	Boolean canSet = false;
	Boolean isSelected = false;

	public Button(String buttonText, int x, int y) {
		button = buttonText;
		setX(x);
		setY(y);
	}

	public Button(String buttonText, int x, int y, Boolean isSettable,
			Boolean isSet) {
		button = buttonText;
		setX(x);
		setY(y);
		canSet = isSettable;
		isSelected = isSet;
	}

	public void setLabel(String s) {
		button = s;
	}

	Boolean clicked = false;

	public void draw() {
		if (isActivated() && !isSelected && !clicked) {
			clicked = true;
			isSelected = true;
		} else if (isActivated() && isSelected && !clicked) {
			clicked = true;
			isSelected = false;
		}
		if (!isActivated() && clicked) {
			clicked = false;
		}
		if (!canSet) {
			if (!isHovered())
				Images.drawImage(MainMenu.textureLoader.button, xPos, yPos);
			else if (isHovered())
				Images.drawImage(MainMenu.textureLoader.buttonHover, xPos, yPos);
			if (setSelected()) {
				hit = true;
			}
			Color.white.bind();
			MainMenu.textureLoader.font.drawString(xPos
					+ (MainMenu.textureLoader.button.getImageWidth() / 2)
					- (MainMenu.textureLoader.metrics.stringWidth(button) / 2),
					yPos + 9, button);
		} else {
			if (!isHovered() && !isSelected)
				Images.drawImage(MainMenu.textureLoader.button, xPos, yPos);
			else if (isHovered() || isSelected)
				Images.drawImage(MainMenu.textureLoader.buttonHover, xPos, yPos);
			if (setSelected()) {
				hit = true;
			}
			Color.white.bind();
			MainMenu.textureLoader.font.drawString(xPos
					+ (MainMenu.textureLoader.button.getImageWidth() / 2)
					- (MainMenu.textureLoader.metrics.stringWidth(button) / 2),
					yPos + 9, button);

		}
	}

	public String getToolTip() {
		return toolTip;
	}

	public void setToolTip(String tip) {
		toolTip = tip;
	}

	public int getX() {
		return xPos;
	}

	public void setX(int x) {
		xPos = x;
	}

	public int getY() {
		return yPos;
	}

	public void setY(int y) {
		yPos = y;
	}

	public Boolean isHovered() {
		if (!MainMenu.clickedOptions
				&& (System.currentTimeMillis() - MainMenu.lastOptioned) > 100) {
			Rectangle r = new Rectangle(getX(), getY(),
					(int) MainMenu.textureLoader.button.getImageWidth(),
					(int) MainMenu.textureLoader.button.getImageHeight());
			if (r.contains(Mouse.getX(), (Main.getHeight() - Mouse.getY())))
				return true;
		}
		return false;
	}

	public Boolean setSelected() {
		if (Mouse.isButtonDown(0) && isHovered()) {
			return true;
		}
		return false;
	}

	public Boolean isActivated() {
		return Mouse.isButtonDown(0) && isHovered();
	}
}

package com.slotmachine.menu.components;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;

import com.slotmachine.api.Images;
import com.slotmachine.main.Main;
import com.slotmachine.menu.MainMenu;

public class MenuButton implements Component {

	private String toolTip = "", button = "";
	private int xPos = 0, yPos = 0;
	int bNumber = 0;
	private Boolean hit = false;

	public MenuButton(String buttonText, int number) {
		button = buttonText;
		bNumber = number;
		xPos = (Main.getWidth() / 2)
				- ((MainMenu.textureLoader.button.getImageWidth()
						+ MainMenu.textureLoader.textBox.getImageWidth() + 10) / 2);
		yPos = 225 + (MainMenu.textureLoader.button.getImageHeight() * bNumber)
				+ (bNumber * 5);
	}

	public void draw() {
		xPos = (Main.getWidth() / 2)
				- ((MainMenu.textureLoader.button.getImageWidth()
						+ MainMenu.textureLoader.textBox.getImageWidth() + 10) / 2);
		yPos = 225 + (MainMenu.textureLoader.button.getImageHeight() * bNumber)
				+ (bNumber * 5);
		if (Main.getHeight() < 650) {
			if ((Main.getHeight() - 650) + 75 < 0) {
				yPos = 150
						+ (MainMenu.textureLoader.button.getImageHeight() * bNumber)
						+ (bNumber * 5);
			} else {
				yPos = (Main.getHeight() - 650)
						+ 225
						+ (MainMenu.textureLoader.button.getImageHeight() * bNumber)
						+ (bNumber * 5);
			}
		} else
			yPos = 225
					+ (MainMenu.textureLoader.button.getImageHeight() * bNumber)
					+ (bNumber * 5);
		if (isHovered())
			Images.drawImage(MainMenu.textureLoader.buttonHover, xPos, yPos);
		if (!isHovered())
			Images.drawImage(MainMenu.textureLoader.button, xPos, yPos);
		if (setSelected()) {
			hit = true;
		}
		Color.white.bind();
		MainMenu.textureLoader.font.drawString(xPos
				+ (MainMenu.textureLoader.button.getImageWidth() / 2)
				- (MainMenu.textureLoader.metrics.stringWidth(button) / 2),
				yPos + 9, button);
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
		Rectangle r = new Rectangle(getX(), getY(),
				(int) MainMenu.textureLoader.button.getImageWidth(),
				(int) MainMenu.textureLoader.button.getImageHeight());
		if (!MainMenu.overSubmenu())
			if (r.contains(Mouse.getX(), (Main.getHeight() - Mouse.getY())))
				return true;
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

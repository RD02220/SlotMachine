package com.slotmachine.menu.components;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.slotmachine.api.Images;
import com.slotmachine.main.Main;
import com.slotmachine.menu.MainMenu;

public class SubMenu implements Component {

	String title = "";
	Boolean centered = true, movable = true, minimized = false, opened = false;
	int x = 0, y = 0;
	int dragX = 0, dragY = 0;
	ArrayList<Component> menuComponents = new ArrayList<Component>();

	public SubMenu(String menuTitle, Boolean isCentered, Boolean isMovable,
			Boolean startMinimized, int x, int y) {
		title = menuTitle;
		centered = isCentered;
		movable = isMovable;
		minimized = startMinimized;
	}

	public Boolean isMinimized() {
		return minimized;
	}

	Boolean clickedWithin = false;
	int lastX = 0, lastY = 0;

	public void draw() {
		if (opened) {
			Images.drawImage(MainMenu.textureLoader.subMenu, x + dragX, y
					+ dragY);
			MainMenu.textureLoader.textBoxTitle.drawString(x + 7 + dragX, y + 2
					+ dragY, title);
			MainMenu.textureLoader.textBoxTitle.drawString(x
					+ MainMenu.textureLoader.subMenu.getImageWidth() - 37
					+ dragX, y + 2 + dragY, "_ [] X");
			for (Component c : menuComponents) {
				int prevX = c.getX();
				int prevY = c.getY();
				c.setX(prevX + dragX);
				c.setY(prevY + dragY);
				c.draw();
				c.setX(prevX);
				c.setY(prevY);
			}
			Rectangle header = new Rectangle(x + dragX, y + dragY,
					MainMenu.textureLoader.subMenu.getImageWidth(), 17);
			if (Mouse.isButtonDown(0)
					&& header.contains(new Point(Mouse.getX(), Main.getHeight()
							- Mouse.getY())) && !clickedWithin) {
				System.out.println("Clicked Within");
				clickedWithin = true;
				lastX = Mouse.getX();
				lastY = (Main.getHeight() - Mouse.getY());
			} else if (Mouse.isButtonDown(0) && clickedWithin) {
				dragX = Mouse.getX() - lastX;
				dragY = (Main.getHeight() - Mouse.getY()) - lastY;
			} else if (!Mouse.isButtonDown(0) && clickedWithin) {
				clickedWithin = false;
				lastX = 0;
				lastY = 0;
			}
		}
	}

	public void setOpened(Boolean open) {
		opened = open;
	}

	public Boolean getOpened() {
		return opened;
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
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	public void addComponent(Component c) {
		menuComponents.add(c);
	}

	public void removeComponent(Component c) {
		menuComponents.remove(c);
	}

	public Boolean isWithin() {
		Rectangle header = new Rectangle(x + dragX, y + dragY,
				MainMenu.textureLoader.subMenu.getImageWidth(),
				MainMenu.textureLoader.subMenu.getImageHeight());
		return header.contains(new Point(Mouse.getX(), Main.getHeight()
				- Mouse.getY()));
	}
}

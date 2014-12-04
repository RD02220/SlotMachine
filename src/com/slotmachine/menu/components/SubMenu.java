package com.slotmachine.menu.components;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import com.slotmachine.api.Images;
import com.slotmachine.main.Main;
import com.slotmachine.menu.MainMenu;

public class SubMenu implements Component {

	public static int subMenusMinimized = -1;
	public static int subMenuCount = 1;
	int subMenuMinID = 0;
	int subMenuID = 0;
	String title = "";
	Boolean centered = true, movable = true, minimized = false, opened = false;
	int x = 0, y = 0;
	int dragX = 0, dragY = 0;
	ArrayList<Component> menuComponents = new ArrayList<Component>();
	public static ArrayList<SubMenu> subMenus = new ArrayList<SubMenu>();
	int targetButton;
	int type = 0;

	Boolean focused = false;

	public SubMenu(String menuTitle, Boolean isCentered, Boolean isMovable,
			Boolean startMinimized, int x, int y, int menuType) {
		title = menuTitle;
		centered = isCentered;
		movable = isMovable;
		minimized = startMinimized;
		subMenuCount++;
		subMenuID = subMenuCount;
		menuComponents = new ArrayList<Component>();
		subMenus.add(this);
		type = menuType;
	}

	public Boolean isMinimized() {
		return minimized;
	}

	Boolean clickedWithin = false;
	int lastX = 0, lastY = 0;
	boolean insideBox = false;

	Boolean clickedMinimize = false, clickedMaximize = false,
			clickedExit = false;
	public static Boolean anySubDragged = false;

	public void setFocused(Boolean set) {
		focused = set;
	}

	Boolean tabPresssed = false;

	public void draw() {
		anySubDragged = clickedWithin;
		if (!minimized) {
			if (clickedWithin) {
				if (!focused) {
					for (SubMenu sm : subMenus) {
						if (sm.focused)
							sm.setFocused(false);
					}
					focused = true;
				}
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_TAB) && !tabPresssed) {
				tabPresssed = true;
				Boolean theFocus = false;
				a: for (Component c : menuComponents) {
					try {
						if (((MenuTextBoxV2) c) != null) {
							if (!theFocus) {
								if (((MenuTextBoxV2) c).isFocused()) {
									((MenuTextBoxV2) c).setFocused(false);
									theFocus = true;
								}
							} else {
								if (!((MenuTextBoxV2) c).isFocused()) {
									((MenuTextBoxV2) c).setFocused(true);

									break a;
								}
							}
						}
					} catch (Exception e) {

					}
				}
			} else if (!Keyboard.isKeyDown(Keyboard.KEY_TAB) && tabPresssed) {
				tabPresssed = false;
			}
			Images.drawImage(MainMenu.textureLoader.subMenu, x + dragX, y
					+ dragY);
			MainMenu.textureLoader.textBoxTitle.drawString(x + 7 + dragX, y + 2
					+ dragY, title);
			String minimize = " _ ";
			String maximize = "[]";
			String close = " X ";
			Rectangle r = new Rectangle(x
					+ MainMenu.textureLoader.subMenu.getImageWidth() - 37
					+ dragX, y + 2 + dragY,
					MainMenu.textureLoader.metrics2.stringWidth(minimize),
					MainMenu.textureLoader.metrics2.getHeight());
			if (r.contains(new Point(Mouse.getX(), Main.getHeight()
					- Mouse.getY()))) {
				MainMenu.textureLoader.textBoxTitle.drawString(x
						+ MainMenu.textureLoader.subMenu.getImageWidth() - 37
						+ dragX, y + 2 + dragY, minimize, Color.black);
				if (Mouse.isButtonDown(0) && !clickedMinimize) {
					System.out.println("Minimize Pressed");
					clickedMinimize = true;
					minimized = true;
					subMenusMinimized++;
					subMenuMinID = subMenusMinimized;
				} else if (!Mouse.isButtonDown(0) && clickedMinimize) {
					clickedMinimize = false;
				}
			} else {
				MainMenu.textureLoader.textBoxTitle.drawString(x
						+ MainMenu.textureLoader.subMenu.getImageWidth() - 37
						+ dragX, y + 2 + dragY, minimize);
			}
			r = new Rectangle(x
					+ MainMenu.textureLoader.subMenu.getImageWidth() - 37
					+ dragX + 13, y + 2 + dragY,
					MainMenu.textureLoader.metrics2.stringWidth(maximize),
					MainMenu.textureLoader.metrics2.getHeight());
			if (r.contains(new Point(Mouse.getX(), Main.getHeight()
					- Mouse.getY()))) {
				MainMenu.textureLoader.textBoxTitle.drawString(x
						+ MainMenu.textureLoader.subMenu.getImageWidth() - 37
						+ dragX + 13, y + 2 + dragY, maximize, Color.black);
				if (Mouse.isButtonDown(0) && !clickedMaximize) {
					System.out.println("Maximize Pressed");
					clickedMaximize = true;
				} else if (!Mouse.isButtonDown(0) && clickedMaximize) {
					clickedMaximize = false;
				}
			} else {
				MainMenu.textureLoader.textBoxTitle.drawString(x
						+ MainMenu.textureLoader.subMenu.getImageWidth() - 37
						+ dragX + 13, y + 2 + dragY, maximize);
			}
			r = new Rectangle(x
					+ MainMenu.textureLoader.subMenu.getImageWidth() - 37
					+ dragX + 22, y + 2 + dragY,
					MainMenu.textureLoader.metrics2.stringWidth(close),
					MainMenu.textureLoader.metrics2.getHeight());
			if (r.contains(new Point(Mouse.getX(), Main.getHeight()
					- Mouse.getY()))) {
				MainMenu.textureLoader.textBoxTitle.drawString(x
						+ MainMenu.textureLoader.subMenu.getImageWidth() - 37
						+ dragX + 22, y + 2 + dragY, close, Color.black);
				if (Mouse.isButtonDown(0) && !clickedExit) {
					System.out.println("Close Pressed");
					clickedExit = true;
				} else if (!Mouse.isButtonDown(0) && clickedExit) {
					clickedExit = false;
				}
			} else {
				MainMenu.textureLoader.textBoxTitle.drawString(x
						+ MainMenu.textureLoader.subMenu.getImageWidth() - 37
						+ dragX + 22, y + 2 + dragY, close);
			}
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
			Rectangle inside = new Rectangle(x + dragX, y + dragY,
					MainMenu.textureLoader.subMenu.getImageWidth(),
					MainMenu.textureLoader.subMenu.getImageHeight());
			if (inside.contains(new Point(Mouse.getX(), Mouse.getY())))
				insideBox = true;
			else
				insideBox = false;
			if (!minimized)
				if (Mouse.isButtonDown(0)
						&& header.contains(new Point(Mouse.getX(), Main
								.getHeight() - Mouse.getY())) && !clickedWithin) {
					Boolean isOnTop = true;
					for (SubMenu m : subMenus) {
						Rectangle header2 = new Rectangle(m.x + m.dragX, m.y
								+ m.dragY,
								MainMenu.textureLoader.subMenu.getImageWidth(),
								17);
						if (header2.contains(new Point(Mouse.getX(), Main
								.getHeight() - Mouse.getY()))) {
							if (subMenuID < m.subMenuID)
								isOnTop = false;
						}
					}
					Boolean anyDragged = false;
					for (SubMenu sm : subMenus) {
						if (sm.clickedWithin && sm.subMenuID != subMenuID) {
							anyDragged = true;
						}
					}
					if (isOnTop && !anyDragged) {
						insideBox = true;
						System.out.println("Clicked Within: " + x + " " + y
								+ " " + lastX + " " + lastY + " " + dragX + " "
								+ dragY);
						clickedWithin = true;
						lastX = Mouse.getX() - dragX;
						lastY = (Main.getHeight() - Mouse.getY()) - dragY;
					}
				} else if (Mouse.isButtonDown(0) && clickedWithin) {
					insideBox = true;
					if (Mouse.getX() - lastX > 0
							&& Mouse.getX() - lastX < Main.getWidth()
									- MainMenu.textureLoader.subMenu
											.getImageWidth())
						dragX = Mouse.getX() - lastX;
					else if (Mouse.getX() - lastX < 0)
						dragX = 0;
					else if (Mouse.getX() - lastX > Main.getWidth()
							- MainMenu.textureLoader.subMenu.getImageWidth()) {
						dragX = Main.getWidth()
								- MainMenu.textureLoader.subMenu
										.getImageWidth();
					}
					if ((Main.getHeight() - Mouse.getY()) - lastY > 0
							&& (Main.getHeight() - Mouse.getY()) - lastY < Main
									.getHeight()
									- MainMenu.textureLoader.subMenu
											.getImageHeight())
						dragY = (Main.getHeight() - Mouse.getY()) - lastY;
					else if ((Main.getHeight() - Mouse.getY()) - lastY < 0)
						dragY = 0;
					else if ((Main.getHeight() - Mouse.getY()) - lastY > Main
							.getHeight()
							- MainMenu.textureLoader.subMenu.getImageHeight()) {
						dragY = Main.getHeight()
								- MainMenu.textureLoader.subMenu
										.getImageHeight();
					}
				} else if (!Mouse.isButtonDown(0) && clickedWithin) {
					insideBox = true;
					clickedWithin = false;
					System.out.println("Let Go: " + x + " " + y + " " + lastX
							+ " " + lastY + " " + dragX + " " + dragY);
				}
		}
		if (minimized) {
			int xTemp = x;
			int yTemp = y;
			x = MainMenu.textureLoader.subMenu.getImageWidth() * subMenuMinID;
			y = Main.getHeight() - 20;
			Images.drawImage(MainMenu.textureLoader.subMenu, x, y);
			MainMenu.textureLoader.textBoxTitle.drawString(x + 8, y + 3, title);
			String minimize = " _ ";
			String maximize = "[]";
			String close = " X ";
			Rectangle r = new Rectangle(x
					+ MainMenu.textureLoader.subMenu.getImageWidth() - 37
					+ dragX, y + 2 + dragY,
					MainMenu.textureLoader.metrics2.stringWidth(minimize),
					MainMenu.textureLoader.metrics2.getHeight());
			if (r.contains(new Point(Mouse.getX(), Main.getHeight()
					- Mouse.getY()))) {
				MainMenu.textureLoader.textBoxTitle.drawString(x
						+ MainMenu.textureLoader.subMenu.getImageWidth() - 37
						+ dragX, y + 2 + dragY, minimize, Color.black);
				if (Mouse.isButtonDown(0) && !clickedMinimize) {
					System.out.println("Minimize Pressed");
					clickedMinimize = true;
					minimized = false;
					for (SubMenu m : subMenus) {
						if (m.subMenuMinID > subMenuMinID) {
							m.subMenuMinID--;
						}
					}
					subMenusMinimized--;
					subMenuMinID = 0;
				} else if (!Mouse.isButtonDown(0) && clickedMinimize) {
					clickedMinimize = false;
				}
			} else {
				MainMenu.textureLoader.textBoxTitle.drawString(x
						+ MainMenu.textureLoader.subMenu.getImageWidth() - 37,
						y + 2, minimize);
			}
			r = new Rectangle(x
					+ MainMenu.textureLoader.subMenu.getImageWidth() - 37 + 13,
					y + 2,
					MainMenu.textureLoader.metrics2.stringWidth(maximize),
					MainMenu.textureLoader.metrics2.getHeight());
			if (r.contains(new Point(Mouse.getX(), Main.getHeight()
					- Mouse.getY()))) {
				MainMenu.textureLoader.textBoxTitle.drawString(x
						+ MainMenu.textureLoader.subMenu.getImageWidth() - 37
						+ 13, y + 2, maximize, Color.black);
				if (Mouse.isButtonDown(0) && !clickedMaximize) {
					System.out.println("Maximize Pressed");
					clickedMaximize = true;
					minimized = false;
					subMenusMinimized--;
					subMenuMinID = 0;
				} else if (!Mouse.isButtonDown(0) && clickedMaximize) {
					clickedMaximize = false;
				}
			} else {
				MainMenu.textureLoader.textBoxTitle.drawString(x
						+ MainMenu.textureLoader.subMenu.getImageWidth() - 37
						+ 13, y + 2, maximize);
			}
			r = new Rectangle(x
					+ MainMenu.textureLoader.subMenu.getImageWidth() - 37 + 22,
					y + 2, MainMenu.textureLoader.metrics2.stringWidth(close),
					MainMenu.textureLoader.metrics2.getHeight());
			if (r.contains(new Point(Mouse.getX(), Main.getHeight()
					- Mouse.getY()))) {
				MainMenu.textureLoader.textBoxTitle.drawString(x
						+ MainMenu.textureLoader.subMenu.getImageWidth() - 37
						+ 22, y + 2, close, Color.black);
				if (Mouse.isButtonDown(0) && !clickedExit) {
					System.out.println("Close Pressed");
					clickedExit = true;
				} else if (!Mouse.isButtonDown(0) && clickedExit) {
					clickedExit = false;
				}
			} else {
				MainMenu.textureLoader.textBoxTitle.drawString(x
						+ MainMenu.textureLoader.subMenu.getImageWidth() - 37
						+ 22, y + 2, close);
			}
			x = xTemp;
			y = yTemp;
		}
	}

	public void unMinimized() {
		subMenusMinimized--;
		for (SubMenu m : subMenus) {
			if (m.subMenuMinID > subMenuMinID) {
				m.subMenuMinID--;
			}
		}
		subMenuMinID = 0;

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

	public void addComponent(Component c, Boolean target) {
		menuComponents.add(c);
		if (target) {
			targetButton = menuComponents.size() - 1;
		}
	}

	public void removeComponent(Component c) {
		menuComponents.remove(c);
	}

	public Boolean isWithin() {
		Rectangle header = new Rectangle(x + dragX, y + dragY,
				MainMenu.textureLoader.subMenu.getImageWidth(),
				MainMenu.textureLoader.subMenu.getImageHeight());
		return header.contains(new Point(Mouse.getX(), Main.getHeight()
				- Mouse.getY()))
				|| insideBox;
	}

	Boolean isSubmitted = false;

	public Boolean isSubMenuSubmitted() {
		Boolean anyDragged = false;
		for (SubMenu sm : subMenus) {
			if (sm.clickedWithin) {
				anyDragged = true;
			}
		}
		if (opened && !minimized && !clickedWithin && !anyDragged)
			for (Component c : menuComponents) {
				try {
					if (((Button) c).isActivated() && !isSubmitted) {
						System.out.println(title + " submenu activated");
						isSubmitted = true;
						return true;
					} else if (!((Button) c).isActivated() && isSubmitted) {
						isSubmitted = false;
					}
				} catch (Exception e) {
				}
			}
		return false;
	}

	public String getUsername() {
		String username = "";
		if (type == 0 || type == 1) {
			if (menuComponents != null) {
				if (menuComponents.get(0) != null) {
					try {
						username = ((MenuTextBoxV2) menuComponents.get(0))
								.getSubmitted();
					} catch (Exception e) {

					}
				}

			}
		}
		return username;
	}

	public String getPassword() {
		String username = "";
		if (type == 0 || type == 1) {
			if (menuComponents != null) {
				if (menuComponents.get(1) != null) {
					try {
						username = ((MenuTextBoxV2) menuComponents.get(1))
								.getSubmitted();
					} catch (Exception e) {

					}
				}

			}
		}
		return username;
	}

	public String getPasswordConfirm() {
		String username = "";
		if (type == 1) {
			if (menuComponents != null) {
				if (menuComponents.get(2) != null) {
					try {
						username = ((MenuTextBoxV2) menuComponents.get(2))
								.getSubmitted();
					} catch (Exception e) {

					}
				}

			}
		}
		return username;
	}
}

package com.slotmachine.menu.components;

import java.awt.Rectangle;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.slotmachine.api.Images;
import com.slotmachine.api.PlayerKeyboard;
import com.slotmachine.main.Main;
import com.slotmachine.menu.MainMenu;

public class MenuTextBoxV2 implements Component {

	private String toolTip = "", startText = "";
	private int xPos = 0, yPos = 0;
	private Boolean isFocused = false, clearOnClick = false, isPass = false;

	public MenuTextBoxV2(String startingText, Boolean clearOnFirstClick,
			Boolean isStarFocused, int x, int y, Boolean IsPassword) {
		startText = startingText;
		xPos = x;
		yPos = y;
		isFocused = isStarFocused;
		clearOnClick = clearOnFirstClick;
		isPass = IsPassword;
	}

	long timeFlash, lastFlash;
	Boolean first = true;
	int timerFlash = 0;
	Boolean isKeyPressed = false;
	long timeKeyPressed = 0;
	Boolean isTabPressed = false;
	Boolean deleteFirst = true;
	Boolean isFlashed = true;
	String lastLetterAdded = "";
	Long timeAdded;
	long timeDeletePressed = 0;

	Boolean neverClicked = true;

	public void draw() {
		timeFlash = System.currentTimeMillis();
		if (first) {
			lastFlash = timeFlash;
			first = false;
		}
		if (Mouse.isButtonDown(0)) {
			if (isInTextBox(Mouse.getX(), Mouse.getY())) {
				if (!SubMenu.anySubDragged) {
					if (neverClicked) {
						if (clearOnClick) {
							startText = "";
						}
						neverClicked = false;
					}
					isFocused = true;
				} else {
					isFocused = false;
				}
			} else {
				isFocused = false;

			}
		}
		if (isFocused) {
			if (PlayerKeyboard.isKeyPressed(Keyboard.KEY_BACK) && !isKeyPressed) {
				isKeyPressed = true;
				timeKeyPressed = System.currentTimeMillis();
				if (startText.length() > 0)
					startText = startText.substring(0, startText.length() - 1);
			} else if (PlayerKeyboard.getPressedKey() == null
					|| (System.currentTimeMillis() - timeKeyPressed) > 75) {
				timeKeyPressed = 0;
				isKeyPressed = false;
			}

			if (PlayerKeyboard.getPressedKey() != null) {
				if (isUsableChar(PlayerKeyboard.getPressedKey())
						&& isKeyPressed == false) {
					isKeyPressed = true;
					timeKeyPressed = System.currentTimeMillis();
					if ((startText + theLetter(PlayerKeyboard.getPressedKey(),
							PlayerKeyboard.isShiftDown())).length() < 30) {
						if (lastLetterAdded != null) {
							if (PlayerKeyboard.getPressedKey().equals(
									lastLetterAdded)) {
								if (System.currentTimeMillis() - timeAdded < 500) {

								} else if (System.currentTimeMillis()
										- timeAdded > 500) {
									System.out.println("1");
									startText = startText
											+ theLetter(
													PlayerKeyboard
															.getPressedKey(),
													PlayerKeyboard
															.isShiftDown());
									if (lastLetterAdded.equals(PlayerKeyboard
											.getPressedKey())) {
										lastLetterAdded = PlayerKeyboard
												.getPressedKey();
									} else {
										lastLetterAdded = PlayerKeyboard
												.getPressedKey();
										timeAdded = System.currentTimeMillis();
									}
								}
							} else {
								System.out.println("2: "
										+ (theLetter(
												PlayerKeyboard.getPressedKey(),
												PlayerKeyboard.isShiftDown())
												+ " " + lastLetterAdded));
								startText = startText
										+ theLetter(
												PlayerKeyboard.getPressedKey(),
												PlayerKeyboard.isShiftDown());
								lastLetterAdded = PlayerKeyboard
										.getPressedKey();
								timeAdded = System.currentTimeMillis();
							}
						} else {
							System.out.println("3");
							startText = startText
									+ theLetter(PlayerKeyboard.getPressedKey(),
											PlayerKeyboard.isShiftDown());
							lastLetterAdded = PlayerKeyboard.getPressedKey();
							timeAdded = System.currentTimeMillis();
						}
					}

				} else if (PlayerKeyboard.getPressedKey() == null
						|| (System.currentTimeMillis() - timeKeyPressed) > 150) {
					isKeyPressed = false;
					timeKeyPressed = 0;
				}

			} else {
				lastLetterAdded = "";
				timeAdded = System.currentTimeMillis();

			}
			Images.drawImage(MainMenu.textureLoader.writeTextBox, xPos, yPos);

			if (timeFlash - lastFlash < 500) {
				MainMenu.textureLoader.textBoxTitle.drawString(xPos + 9,
						yPos + 3, startText + "_");
			} else {
				MainMenu.textureLoader.textBoxTitle.drawString(xPos + 9,
						yPos + 3, startText);
				if (timeFlash - lastFlash > 1000) {
					lastFlash = System.currentTimeMillis();
				}
			}

		} else {
			Images.drawImage(MainMenu.textureLoader.writeTextBox, xPos, yPos);
			String replace = "";
			for (int i = 0; i < startText.toCharArray().length; i++) {
				replace = replace + "*";
			}
			if (!isPass)
				MainMenu.textureLoader.textBoxTitle.drawString(xPos + 9,
						yPos + 3, startText);
			else if (isPass)
				if (!neverClicked)
					MainMenu.textureLoader.textBoxTitle.drawString(xPos + 9,
							yPos + 3, replace);
				else if (neverClicked)
					MainMenu.textureLoader.textBoxTitle.drawString(xPos + 9,
							yPos + 3, startText);
		}

	}

	public String theLetter(String theChar, Boolean isShiftDown) {
		if (theChar.contains("SPACE")) {
			theChar = " ";
		}
		if (isShiftDown)
			return theChar.toUpperCase();
		else
			return theChar.toLowerCase();
	}

	public Boolean isInTextBox(int xp, int yp) {
		Rectangle r = new Rectangle(xPos, (Main.getHeight() - yPos)
				- MainMenu.textureLoader.writeTextBox.getImageHeight(),
				MainMenu.textureLoader.writeTextBox.getImageWidth(),
				MainMenu.textureLoader.writeTextBox.getImageHeight());
		if (r.contains(xp, yp))
			return true;
		return false;
	}

	public String getSubmitted() {
		return startText;
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
		return xPos;
	}

	@Override
	public void setX(int x) {
		xPos = x;
	}

	@Override
	public int getY() {
		return yPos;
	}

	@Override
	public void setY(int y) {
		yPos = y;

	}

	public Boolean isUsableChar(String chars) {
		String[] usables = new String[] { "SPACE", " ", "A", "B", "C", "D",
				"E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
				"Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2",
				"3", "4", "5", "6", "7", "8", "9", "0", " " };
		for (String t : usables) {
			if (t.contains(chars)) {
				return true;
			}
		}
		return false;
	}
}

package com.slotmachine.menu;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import com.slotmachine.api.Images;
import com.slotmachine.main.GameState;
import com.slotmachine.main.Main;
import com.slotmachine.menu.components.Button;
import com.slotmachine.menu.components.MenuButton;
import com.slotmachine.menu.components.MenuTextBox;
import com.slotmachine.menu.components.SubMenu;
import com.slotmachine.menu.components.TextBox;

public class MainMenu {
	public static MenuTextures textureLoader;
	MenuButton start, options, aboutus, exit, users;
	TextBox box;
	Boolean isExited = false, isOnScreen = true, pressedSingle = false;

	public static SubMenu login;

	public MainMenu() throws IOException {

		textureLoader = new MenuTextures();
		login = new SubMenu("Login", true, true, false, 0, 0);
		login.addComponent(new MenuTextBox("Username", false, false, 20, 40));
		login.addComponent(new MenuTextBox("Password", false, false, 20, 85));
		login.addComponent(new Button("Login", 60, 130));

		start = new MenuButton("Start Playing", 0);
		options = new MenuButton("Options", 1);
		aboutus = new MenuButton("About Us", 2);
		users = new MenuButton("Users", 3);
		exit = new MenuButton("Exit", 4);
		box = new TextBox("Merlin's Ghost", "Lets play some slots!",
				(Main.getWidth() / 2) + 15, 225);
	}

	public void drawMainMenu() {
		if (isOnScreen) {
			if (start.isActivated()) {
				System.out.println("Start " + isOnScreen);
				pressedSingle = true;
				isOnScreen = false;
				isExited = true;
				// Start slot machine
			} else if (options.isActivated()) {
				System.out.println("Options Menu display: " + isOnScreen);
				pressedSingle = true;
				isOnScreen = false;
				Main.o.setOnScreen(true);
				Main.setState(GameState.OPTIONSMENU);
			} else if (aboutus.isActivated()) {
				// About Us menu
			} else if (exit.isActivated()) {
				setIsExited(true);
			}
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			GL11.glViewport(0, 0, Main.getWidth(), Main.getHeight());
			GL11.glMatrixMode(GL11.GL_MODELVIEW);

			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, Main.getWidth(), Main.getHeight(), 0, 1, -1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			Color.white.bind();
			drawBackground();
			if (Main.getHeight() < 650) {
				if ((Main.getHeight() - 650) + 75 > 0) {
					Images.drawImage(textureLoader.logo, (Main.getWidth() / 2)
							- (textureLoader.logo.getImageWidth() / 2),
							(Main.getHeight() - 650) + 75);
				} else {
					Images.drawImage(textureLoader.logo, (Main.getWidth() / 2)
							- (textureLoader.logo.getImageWidth() / 2), 1);
				}
			} else
				Images.drawImage(textureLoader.logo, (Main.getWidth() / 2)
						- (textureLoader.logo.getImageWidth() / 2), 75);
			start.draw();
			options.draw();
			aboutus.draw();
			users.draw();
			exit.draw();
			if (start.isHovered()) {
				box.setTitle("Start");
				box.setBodyText("Lets play some slots!");
			} else if (options.isHovered()) {
				box.setTitle("Options");
				box.setBodyText("Select your options here!/n"
						+ "Graphical, Sounds, Music, etc.");
			} else if (options.isHovered()) {
				box.setTitle("Options");
				box.setBodyText("Edit your individual video, memory, sound and \nother options here!");
			} else if (aboutus.isHovered()) {
				box.setTitle("About Us");
				box.setBodyText("Matt Stratton/nRyan DeBerardino/nO'Neal Jones/nChad Golden");
			} else if (users.isHovered()) {
				box.setTitle("Users");
				box.setBodyText("View Users and Highscores!");
			} else if (exit.isHovered()) {
				box.setTitle("Exit Merlin's Ghost?");
				box.setBodyText("Exit the game? (But why would you want too?)");
			}
			box.draw();
			drawLoginInfo();
			if (!login.isMinimized())
				login.draw();
		}
	}

	public void drawBackground() {
		for (int i = 0; i <= Main.getHeight()
				/ textureLoader.background.getTextureHeight() + 5; i++) {
			for (int t = 0; t <= Main.getWidth()
					/ textureLoader.background.getTextureWidth() + 5; t++) {
				Images.drawImage(textureLoader.background,
						(int) (t * textureLoader.background.getTextureWidth())
								- (t * 28) - 1,
						(int) (i * textureLoader.background.getTextureHeight())
								- (i * 28) - 1);
			}
		}
	}

	public Boolean getIsExited() {
		return isExited;
	}

	public void setIsExited(Boolean isExited) {
		this.isExited = isExited;
	}

	Boolean clicked = true;

	public void drawLoginInfo() {
		String s1 = " Login Now", s2 = " | or | ", s3 = "Create Account ";
		Rectangle r = new Rectangle(Main.getWidth()
				- textureLoader.metrics2.stringWidth(s1 + s2 + s3) - 3,
				Main.getHeight() - 22, textureLoader.metrics2.stringWidth(s1),
				textureLoader.metrics2.getHeight());
		if (r.contains(new Point(Mouse.getX(), Main.getHeight() - Mouse.getY()))) {
			textureLoader.textBoxTitle.drawString(Main.getWidth()
					- textureLoader.metrics2.stringWidth(s1 + s2 + s3) - 3,
					Main.getHeight() - 22, s1, Color.black);
			if (Mouse.isButtonDown(0) && !clicked) {
				clicked = true;
				if (!login.getOpened()) {
					login.setOpened(true);
				} else
					login.setOpened(false);
			} else if (!Mouse.isButtonDown(0)) {
				clicked = false;
			}
		} else {
			textureLoader.textBoxTitle.drawString(Main.getWidth()
					- textureLoader.metrics2.stringWidth(s1 + s2 + s3) - 3,
					Main.getHeight() - 22, s1);
		}
		textureLoader.textBoxTitle.drawString(Main.getWidth()
				- textureLoader.metrics2.stringWidth(s2 + s3) - 3,
				Main.getHeight() - 22, s2);

		r = new Rectangle(Main.getWidth()
				- textureLoader.metrics2.stringWidth(s3) - 3,
				Main.getHeight() - 22, textureLoader.metrics2.stringWidth(s3),
				textureLoader.metrics2.getHeight());
		if (r.contains(new Point(Mouse.getX(), Main.getHeight() - Mouse.getY()))) {
			textureLoader.textBoxTitle.drawString(Main.getWidth()
					- textureLoader.metrics2.stringWidth(s3) - 3,
					Main.getHeight() - 22, s3, Color.black);
		} else {
			textureLoader.textBoxTitle.drawString(Main.getWidth()
					- textureLoader.metrics2.stringWidth(s3) - 3,
					Main.getHeight() - 22, s3);
		}
	}

	public static Boolean overSubmenu() {
		return login.isWithin();
	}
}
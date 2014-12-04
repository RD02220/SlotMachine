package com.slotmachine.menu;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.slotmachine.api.Images;
import com.slotmachine.api.Sound;
import com.slotmachine.main.GameState;
import com.slotmachine.main.Main;
import com.slotmachine.menu.components.Button;
import com.slotmachine.menu.components.Label;
import com.slotmachine.menu.components.MenuTextBox;

public class OptionsMenu {
	public static MenuTextures textureLoader;
	Button playSounds, playMusic;
	MenuTextBox textBox;
	Boolean isExited = false, isOnScreen = false, pressedSingle = false;
	Button mainMenu;
	public static int volume = 5;
	Label volumeL;

	Texture vol1, vol2, vol3, vol4;

	public OptionsMenu() throws IOException {
		vol1 = TextureLoader.getTexture("PNG", ResourceLoader
				.getResourceAsStream("textures/Volume/Volume1.png"));
		vol2 = TextureLoader.getTexture("PNG", ResourceLoader
				.getResourceAsStream("textures/Volume/Volume2.png"));
		vol3 = TextureLoader.getTexture("PNG", ResourceLoader
				.getResourceAsStream("textures/Volume/Volume3.png"));
		vol4 = TextureLoader.getTexture("PNG", ResourceLoader
				.getResourceAsStream("textures/Volume/Volume4.png"));
		textureLoader = MainMenu.textureLoader;
		playSounds = new Button("Play Sounds: On", (Main.getWidth() / 2)
				- (MainMenu.textureLoader.button.getImageWidth() / 2) - 70,
				180, true, true);
		playMusic = new Button("Play Music: On", (Main.getWidth() / 2)
				- (MainMenu.textureLoader.button.getImageWidth() / 2) - 70,
				230, true, true);
		mainMenu = new Button("Back to Main Menu", (Main.getWidth() / 2)
				- (MainMenu.textureLoader.button.getImageWidth() / 2), 480);
		volumeL = new Label("Volume: " + volume, (Main.getWidth() / 2)
				- (MainMenu.textureLoader.button.getImageWidth() / 2) + 195,
				180);
	}

	Boolean mainMenuButton = false;
	Boolean pressed = false, pressed2 = false;
	Boolean upPressed = false, downPressed = false;

	public void drawMainMenu() {
		if (isOnScreen) {
			if (!Mouse.isButtonDown(0) && MainMenu.clickedOptions)
				MainMenu.clickedOptions = false;
			playSounds.setX((Main.getWidth() / 2)
					- (MainMenu.textureLoader.button.getImageWidth() / 2) - 70);
			playMusic.setX((Main.getWidth() / 2)
					- (MainMenu.textureLoader.button.getImageWidth() / 2) - 70);
			mainMenu.setX((Main.getWidth() / 2)
					- (MainMenu.textureLoader.button.getImageWidth() / 2));

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
			if (mainMenu.isActivated() && !mainMenuButton) {
				isOnScreen = false;
				Main.m.setOnScreen(true);
				Main.setState(GameState.MAINMENU);

			} else if (!mainMenu.isActivated() && mainMenuButton) {
				mainMenuButton = false;
			}
			Images.drawImage(MainMenu.textureLoader.logo, (Main.getWidth() / 2)
					- (MainMenu.textureLoader.logo.getImageWidth() / 2), 30);
			mainMenu.draw();
			if (Main.isSoundOn) {
				playSounds.setLabel("Play Sounds: On");
			} else {
				playSounds.setLabel("Play Sounds: Off");
			}
			if (playSounds.isActivated() && !pressed
					&& !MainMenu.clickedOptions) {
				Main.isSoundOn = !Main.isSoundOn;
				pressed = true;
			} else if (!playSounds.isActivated() && pressed) {
				pressed = false;
			}
			playSounds.draw();
			volumeL.setLabel("Volume: " + volume);
			volumeL.draw();
			Rectangle up = new Rectangle(
					(Main.getWidth() / 2)
							- (MainMenu.textureLoader.button.getImageWidth() / 2)
							+ 190, 202, 20, 20);
			if (Mouse.isButtonDown(0)
					&& up.contains(new Point(Mouse.getX(),
							(Main.getHeight() - Mouse.getY()))) && !upPressed) {
				System.out.println("Pressed Up");
				upPressed = true;

				if (volume < 9) {
					volume++;
					Sound.playSound(0);
				}
			}
			if (upPressed && !Mouse.isButtonDown(0)) {
				upPressed = false;
			}
			Images.drawImage(
					MainMenu.textureLoader.up,
					(Main.getWidth() / 2)
							- (MainMenu.textureLoader.button.getImageWidth() / 2)
							+ 190, 202);
			Rectangle down = new Rectangle(
					(Main.getWidth() / 2)
							- (MainMenu.textureLoader.button.getImageWidth() / 2)
							+ 190, 226, 20, 20);
			if (Mouse.isButtonDown(0)
					&& down.contains(new Point(Mouse.getX(),
							(Main.getHeight() - Mouse.getY()))) && !downPressed) {
				System.out.println("Pressed Down");
				downPressed = true;
				if (volume > 1) {
					volume--;
					Sound.playSound(0);
				}
			}
			if (downPressed && !Mouse.isButtonDown(0)) {
				downPressed = false;
			}
			Images.drawImage(
					MainMenu.textureLoader.down,
					(Main.getWidth() / 2)
							- (MainMenu.textureLoader.button.getImageWidth() / 2)
							+ 190, 226);

			if (Main.isMusicOn) {
				playMusic.setLabel("Play Music: On");
			} else {
				playMusic.setLabel("Play Music: Off");
			}
			if (playMusic.isActivated() && !pressed2
					&& !MainMenu.clickedOptions) {
				Main.isMusicOn = !Main.isMusicOn;
				pressed2 = true;
			} else if (!playMusic.isActivated() && pressed2) {
				pressed2 = false;
			}
			if (volume < 3) {
				Images.drawImage(vol1, (Main.getWidth() / 2)
						- (MainMenu.textureLoader.button.getImageWidth() / 2)
						+ 220, 203);
			} else if (volume >= 3 && volume < 6) {
				Images.drawImage(vol2, (Main.getWidth() / 2)
						- (MainMenu.textureLoader.button.getImageWidth() / 2)
						+ 220, 203);
			} else if (volume >= 6 && volume < 8) {
				Images.drawImage(vol3, (Main.getWidth() / 2)
						- (MainMenu.textureLoader.button.getImageWidth() / 2)
						+ 220, 203);
			} else if (volume >= 8 && volume < 11) {
				Images.drawImage(vol4, (Main.getWidth() / 2)
						- (MainMenu.textureLoader.button.getImageWidth() / 2)
						+ 220, 203);
			}
			playMusic.draw();
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

	public Boolean isOnScreen() {
		return isOnScreen;
	}

	public void setOnScreen(boolean b) {
		isOnScreen = b;
	}
}
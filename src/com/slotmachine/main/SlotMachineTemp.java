package com.slotmachine.main;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import com.slotmachine.api.Images;
import com.slotmachine.backend.SlotFacade;
import com.slotmachine.menu.MainMenu;
import com.slotmachine.menu.components.Button;
import com.slotmachine.menu.components.Label;
import com.slotmachine.menu.components.TextBox;

public class SlotMachineTemp {
	TextBox box;
	Boolean isExited = false, isOnScreen = false, pressedSingle = false;

	Button lever, mainMenu;
	Label currencyCounter, reel1, reel2, reel3, winner;
	Boolean started = false;

	public SlotMachineTemp() throws IOException {
		lever = new Button("Pull Lever", (Main.getWidth() / 2)
				- (MainMenu.textureLoader.button.getImageWidth() / 2), 400);
		mainMenu = new Button("Back to Main Menu", (Main.getWidth() / 2)
				- (MainMenu.textureLoader.button.getImageWidth() / 2), 450);
		currencyCounter = new Label("Currency: " + Main.currency,
				(Main.getWidth() / 2)
						- (MainMenu.textureLoader.button.getImageWidth() / 2)
						+ 90, 375);
		reel1 = new Label("", (Main.getWidth() / 2)
				- (MainMenu.textureLoader.button.getImageWidth() / 2) + 110,
				250);
		reel2 = new Label("", (Main.getWidth() / 2)
				- (MainMenu.textureLoader.button.getImageWidth() / 2) + 110,
				270);
		reel3 = new Label("", (Main.getWidth() / 2)
				- (MainMenu.textureLoader.button.getImageWidth() / 2) + 110,
				290);
		winner = new Label("WINNER!", (Main.getWidth() / 2)
				- (MainMenu.textureLoader.button.getImageWidth() / 2) + 100,
				320, Color.red);
	}

	Boolean pulled = false, checkWin = false, mainMenuButton = false;

	public void drawMainMenu() {
		SlotFacade player = new SlotFacade();
		if (isOnScreen) {
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
					Images.drawImage(
							MainMenu.textureLoader.logo,
							(Main.getWidth() / 2)
									- (MainMenu.textureLoader.logo
											.getImageWidth() / 2),
							(Main.getHeight() - 650) + 75);
				} else {
					Images.drawImage(
							MainMenu.textureLoader.logo,
							(Main.getWidth() / 2)
									- (MainMenu.textureLoader.logo
											.getImageWidth() / 2), 1);
				}
			} else
				Images.drawImage(
						MainMenu.textureLoader.logo,
						(Main.getWidth() / 2)
								- (MainMenu.textureLoader.logo.getImageWidth() / 2),
						75);
			lever.draw();
			if (lever.isActivated() && !pulled) {
				Main.currency--;
				started = true;
				pulled = true;
				System.out.println("Pulling Lever");
				player.pullLever();
				reel1.setLabel(player.getReelOne());
				reel2.setLabel(player.getReelTwo());
				reel3.setLabel(player.getReelThree());
				checkWin = player.checkWinner();
				if (checkWin)
					Main.currency = Main.currency + 3;

			} else if (!lever.isActivated() && pulled) {
				pulled = false;
			}
			if (mainMenu.isActivated() && !mainMenuButton) {
				isOnScreen = false;
				Main.m.setOnScreen(true);
				Main.setState(GameState.MAINMENU);

			} else if (!mainMenu.isActivated() && mainMenuButton) {
				mainMenuButton = false;
			}
			reel1.setX((Main.getWidth() / 2)
					- (MainMenu.textureLoader.button.getImageWidth() / 2) + 110);
			reel2.setX((Main.getWidth() / 2)
					- (MainMenu.textureLoader.button.getImageWidth() / 2) + 110);
			reel3.setX((Main.getWidth() / 2)
					- (MainMenu.textureLoader.button.getImageWidth() / 2) + 110);
			lever.setX((Main.getWidth() / 2)
					- (MainMenu.textureLoader.button.getImageWidth() / 2));
			currencyCounter.setX((Main.getWidth() / 2)
					- (MainMenu.textureLoader.button.getImageWidth() / 2) + 90);
			winner.setX((Main.getWidth() / 2)
					- (MainMenu.textureLoader.button.getImageWidth() / 2) + 100);
			currencyCounter.setLabel("Currency: " + Main.currency);
			currencyCounter.draw();
			reel1.draw();
			reel2.draw();
			reel3.draw();
			mainMenu.setX((Main.getWidth() / 2)
					- (MainMenu.textureLoader.button.getImageWidth() / 2));
			mainMenu.draw();
			if (started) {
				if (checkWin) {
					winner.draw();
				}
			}
			drawLogin();
		}
	}

	public void drawLogin() {
		if (Main.isLoggedIn) {
			MainMenu.textureLoader.textBoxTitle.drawString(2, 2,
					"Welcome back " + Main.username);
			MainMenu.textureLoader.textBoxTitle.drawString(2, 15,
					"Current Currency: " + Main.currency);
		}
	}

	public void drawBackground() {
		for (int i = 0; i <= Main.getHeight()
				/ MainMenu.textureLoader.background.getTextureHeight() + 5; i++) {
			for (int t = 0; t <= Main.getWidth()
					/ MainMenu.textureLoader.background.getTextureWidth() + 5; t++) {
				Images.drawImage(
						MainMenu.textureLoader.background,
						(int) (t * MainMenu.textureLoader.background
								.getTextureWidth()) - (t * 28) - 1,
						(int) (i * MainMenu.textureLoader.background
								.getTextureHeight()) - (i * 28) - 1);
			}
		}
	}

	public Boolean getIsExited() {
		return isExited;
	}

	public Boolean isOnScreen() {
		return isOnScreen;
	}

	public void setOnScreen(boolean b) {
		isOnScreen = b;
	}

	public void setIsExited(Boolean isExited) {
		this.isExited = isExited;
	}

	Boolean clicked = true, clicked2 = true;

}
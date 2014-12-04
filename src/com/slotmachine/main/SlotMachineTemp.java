package com.slotmachine.main;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.slotmachine.api.Animation;
import com.slotmachine.api.Images;
import com.slotmachine.api.SpriteSheet;
import com.slotmachine.backend.SlotFacade;
import com.slotmachine.menu.MainMenu;
import com.slotmachine.menu.components.Button;
import com.slotmachine.menu.components.Label;
import com.slotmachine.menu.components.TextBox;

public class SlotMachineTemp {
	TextBox box;
	Boolean isExited = false, isOnScreen = false, pressedSingle = false;

	Button lever, mainMenu;
	Label currencyCounter, reel1, reel2, reel3, winner, bettingCoins;
	Boolean started = false;
	SpriteSheet a;

	int betting = 1;
	ArrayList<Texture> slotMachine = new ArrayList<Texture>();
	ArrayList<Texture> candlesGlow = new ArrayList<Texture>();
	Texture slotAdd;

	ArrayList<Texture> tiles = new ArrayList<Texture>();
	Texture tileAdd;

	public SlotMachineTemp() throws IOException {
		int i = 1;
		while (i < 9) {
			tileAdd = TextureLoader.getTexture(
					"PNG",
					ResourceLoader.getResourceAsStream("textures/Tiles/Tile"
							+ i + ".png"));
			tiles.add(tileAdd);
			i++;
		}
		i = 1;
		while (i < 12) {
			slotAdd = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("textures/SlotMachine/SlotMachine" + i
							+ ".png"));
			slotMachine.add(slotAdd);
			i++;
		}
		lever = new Button("Pull Lever", (Main.getWidth() / 2)
				- (MainMenu.textureLoader.button.getImageWidth() / 2), 400);
		mainMenu = new Button("Back to Main Menu", (Main.getWidth() / 2)
				- (MainMenu.textureLoader.button.getImageWidth() / 2), 480);
		currencyCounter = new Label("Currency: " + Main.currency,
				(Main.getWidth() / 2)
						- (MainMenu.textureLoader.button.getImageWidth() / 2)
						+ 90, 470);
		bettingCoins = new Label("Betting: " + betting + " coins",
				(Main.getWidth() / 2)
						- (MainMenu.textureLoader.button.getImageWidth() / 2)
						+ 90, 470);
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
		a = new SpriteSheet("textures/SlotMachine/SlotMachine1.png", 304, 344);

	}

	Boolean pulled = false, checkWin = false, mainMenuButton = false;

	Boolean pressedLever = false;
	Boolean leverActivated = false;
	Boolean down = false;
	int leverFrame = 0;

	int slotX = 0, slotY = 0;

	public void drawSlot() throws IOException {
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
			Images.drawImage(MainMenu.textureLoader.logo, (Main.getWidth() / 2)
					- (MainMenu.textureLoader.logo.getImageWidth() / 2), 30);
			/*
			 * lever.draw(); if (lever.isActivated() && !pulled) {
			 * Main.currency--; started = true; pulled = true;
			 * System.out.println("Pulling Lever"); player.pullLever();
			 * reel1.setLabel(player.getReelOne());
			 * reel2.setLabel(player.getReelTwo());
			 * reel3.setLabel(player.getReelThree()); checkWin =
			 * player.checkWinner(); if (checkWin) Main.currency = Main.currency
			 * + 3; if (Main.isLoggedIn)
			 * Main.dbAccess.setCurrencyForPlayer(Main.username, Main.currency);
			 * 
			 * } else if (!lever.isActivated() && pulled) { pulled = false; }
			 */
			slotX = (Main.getWidth() / 2)
					- (MainMenu.textureLoader.slotmachine.getImageWidth() / 2)
					+ 10;
			slotY = 160;
			if (mainMenu.isActivated() && !mainMenuButton) {
				isOnScreen = false;
				Main.m.setOnScreen(true);
				Main.setState(GameState.MAINMENU);

			} else if (!mainMenu.isActivated() && mainMenuButton) {
				mainMenuButton = false;
			}
			Rectangle handle = new Rectangle((Main.getWidth() / 2)
					- (MainMenu.textureLoader.slotmachine.getImageWidth() / 2)
					+ 10 + 295, (Main.getHeight() - 160 - 50 - 93), 25, 50);
			Boolean drawn = false;
			if (Mouse.isButtonDown(0)
					&& handle.contains(new Point(Mouse.getX(), Mouse.getY()))
					&& !leverActivated) {

				drawn = true;
				leverActivated = true;
				Main.currency--;
				started = true;
				down = true;
				pulled = true;
				System.out.println("Pulling Lever");
				player.pullLever();
				reel1.setLabel(player.getReelOne().trim());
				reel2.setLabel(player.getReelTwo().trim());
				reel3.setLabel(player.getReelThree().trim());
				checkWin = player.checkWinner();
				if (checkWin)
					Main.currency = Main.currency + 3;
				if (Main.isLoggedIn)
					Main.dbAccess.setCurrencyForPlayer(Main.username,
							Main.currency);
			} else if (!Mouse.isButtonDown(0) && leverActivated && down) {
				if (leverFrame > 10) {
					down = false;
					leverFrame = 0;
				}
			}
			if (leverActivated && leverFrame < 11 && down) {
				drawn = true;
				slotX = (Main.getWidth() / 2)
						- (MainMenu.textureLoader.slotmachine.getImageWidth() / 2)
						+ 10;
				slotY = 160;
				Images.drawImage(
						slotMachine.get(leverFrame),
						(Main.getWidth() / 2)
								- (MainMenu.textureLoader.slotmachine
										.getImageWidth() / 2) + 10, 160);
				leverFrame++;
			} else if (leverActivated && leverFrame > 10) {
				leverFrame = 0;
				down = false;
			}
			if (leverActivated && !down && leverFrame < 11) {
				drawn = true;
				slotX = (Main.getWidth() / 2)
						- (MainMenu.textureLoader.slotmachine.getImageWidth() / 2)
						+ 10;
				slotY = 160;
				Images.drawImage(
						slotMachine.get(10 - leverFrame),
						(Main.getWidth() / 2)
								- (MainMenu.textureLoader.slotmachine
										.getImageWidth() / 2) + 10, 160);
				leverFrame++;
			}
			if (leverActivated && !down && leverFrame > 10) {
				leverActivated = false;
				drawn = false;
				pulled = false;
				leverFrame = 0;

			}
			if (!drawn) {
				slotX = (Main.getWidth() / 2)
						- (MainMenu.textureLoader.slotmachine.getImageWidth() / 2)
						+ 10;
				slotY = 160;
				Images.drawImage(
						MainMenu.textureLoader.slotmachine,
						(Main.getWidth() / 2)
								- (MainMenu.textureLoader.slotmachine
										.getImageWidth() / 2) + 10, 160);
			}
			bettingCoins
					.setX((Main.getWidth() / 2)
							- (MainMenu.textureLoader.button.getImageWidth() / 2)
							+ 320);
			bettingCoins.setY(250);
			if (betting == 0)
				betting = 1;
			if (betting == 1)
				bettingCoins.setLabel("Betting: " + betting + " coin");
			else
				bettingCoins.setLabel("Betting: " + betting + " coins");
			Images.drawImage(
					MainMenu.textureLoader.up,
					(Main.getWidth() / 2)
							- (MainMenu.textureLoader.button.getImageWidth() / 2)
							+ 401, 235);
			Images.drawImage(
					MainMenu.textureLoader.down,
					(Main.getWidth() / 2)
							- (MainMenu.textureLoader.button.getImageWidth() / 2)
							+ 401, 258);
			bettingCoins.draw();

			reel1.setX((Main.getWidth() / 2)
					- (MainMenu.textureLoader.button.getImageWidth() / 2) + 110);
			reel2.setX((Main.getWidth() / 2)
					- (MainMenu.textureLoader.button.getImageWidth() / 2) + 110);
			reel3.setX((Main.getWidth() / 2)
					- (MainMenu.textureLoader.button.getImageWidth() / 2) + 110);
			lever.setX((Main.getWidth() / 2)
					- (MainMenu.textureLoader.button.getImageWidth() / 2));

			currencyCounter.setX(15);
			currencyCounter.setY(Main.getHeight() - 20);
			winner.setX(15);
			winner.setY(Main.getHeight() - 40);
			currencyCounter.setLabel("Currency: " + Main.currency);
			currencyCounter.draw();
			drawTiles();
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

	public void drawTiles() {
		String line1 = reel1.getLabelText();
		String[] line1Split = line1.split(" ");
		String line2 = reel2.getLabelText();
		String[] line2Split = line2.split(" ");
		String line3 = reel3.getLabelText();
		String[] line3Split = line3.split(" ");
		for (int i = 0; i < 3; i++) {
			for (int t = 0; t < 3; t++) {
				int prev = slotX;
				if (t == 1) {
					prev = slotX + 4;
				}
				if (t == 2) {
					prev = slotX + 4 + 4;
				}
				String[] lineToUse = null;
				if (i == 0) {
					lineToUse = line1Split;
				} else if (i == 1) {
					lineToUse = line2Split;
				} else if (i == 2) {
					lineToUse = line3Split;
				}
				if (!line1.isEmpty()) {
					if (lineToUse != null) {
						Texture tileToDraw = tiles.get(0);
						if (lineToUse[t].equals("A")) {
							tileToDraw = tiles.get(1);
						} else if (lineToUse[t].equals("B")) {
							tileToDraw = tiles.get(2);
						} else if (lineToUse[t].equals("C")) {
							tileToDraw = tiles.get(3);
						} else if (lineToUse[t].equals("D")) {
							tileToDraw = tiles.get(4);
						} else if (lineToUse[t].equals("E")) {
							tileToDraw = tiles.get(5);
						}

						Images.drawImage(tileToDraw, (prev + 31)
								+ ((t * tiles.get(0).getImageWidth())),
								(slotY + 44)
										+ (i * tiles.get(0).getImageHeight())
										- (i * 2));
					}
				}
			}
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
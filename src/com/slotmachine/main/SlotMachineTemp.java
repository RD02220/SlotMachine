package com.slotmachine.main;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.slotmachine.api.Images;
import com.slotmachine.api.Sound;
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
	ArrayList<Texture> candleFlick = new ArrayList<Texture>();
	Texture slotAdd;

	ArrayList<Texture> tiles = new ArrayList<Texture>();
	Texture tileAdd;

	Boolean isAnimating = false;
	long startedAnimating = 0;
	ArrayList<Integer> orderToStop = new ArrayList<Integer>();

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
		i = 1;
		while (i < 6) {
			slotAdd = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("textures/Candles/CandleFlick" + i
							+ ".png"));
			candleFlick.add(slotAdd);
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

	Boolean upPressed = false, downPressed = false;

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
					&& !leverActivated && (Main.currency > betting)
					&& Main.currency > 0 && !isAnimating) {
				isAnimating = true;
				startedAnimating = System.currentTimeMillis();
				Random r = new Random();
				orderToStop = new ArrayList<Integer>();
				for (int s = 1; s < 10; s++) {
					int ran = r.nextInt(10);
					while (orderToStop.contains(ran) || ran == 0) {
						ran = r.nextInt(10);
					}
					orderToStop.add(ran);
				}
				Sound.playSound(1);
				drawn = true;
				leverActivated = true;
				Main.currency = Main.currency - betting;
				started = true;
				down = true;
				pulled = true;
				System.out.println("Pulling Lever");
				player.pullLever();
				reel1.setLabel(player.getReelOne().trim());
				reel2.setLabel(player.getReelTwo().trim());
				reel3.setLabel(player.getReelThree().trim());
				checkWin = player.checkWinner();

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
			SoundStore.get().poll(0);
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
			Rectangle up = new Rectangle(
					(Main.getWidth() / 2)
							- (MainMenu.textureLoader.button.getImageWidth() / 2)
							+ 404, 235, 20, 20);
			if (Mouse.isButtonDown(0)
					&& up.contains(new Point(Mouse.getX(),
							(Main.getHeight() - Mouse.getY()))) && !upPressed) {
				System.out.println("Pressed Up");
				upPressed = true;

				if (betting < 5) {
					Sound.playSound(0);
					betting++;
				}
			}
			if (upPressed && !Mouse.isButtonDown(0)) {
				upPressed = false;
			}
			Images.drawImage(
					MainMenu.textureLoader.up,
					(Main.getWidth() / 2)
							- (MainMenu.textureLoader.button.getImageWidth() / 2)
							+ 404, 235);
			Rectangle down = new Rectangle(
					(Main.getWidth() / 2)
							- (MainMenu.textureLoader.button.getImageWidth() / 2)
							+ 404, 258, 20, 20);
			if (Mouse.isButtonDown(0)
					&& down.contains(new Point(Mouse.getX(),
							(Main.getHeight() - Mouse.getY()))) && !downPressed) {
				System.out.println("Pressed Down");
				downPressed = true;
				if (betting > 1) {
					Sound.playSound(0);
					betting--;
				}
			}
			if (downPressed && !Mouse.isButtonDown(0)) {
				downPressed = false;
			}
			Images.drawImage(
					MainMenu.textureLoader.down,
					(Main.getWidth() / 2)
							- (MainMenu.textureLoader.button.getImageWidth() / 2)
							+ 404, 258);
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

			bettingCoins.draw();
			if (betting == 1) {
				Images.drawImage(
						MainMenu.textureLoader.coin1,
						(Main.getWidth() / 2)
								- (MainMenu.textureLoader.button
										.getImageWidth() / 2) + 435, 240);
			} else if (betting == 2) {
				Images.drawImage(
						MainMenu.textureLoader.coin2,
						(Main.getWidth() / 2)
								- (MainMenu.textureLoader.button
										.getImageWidth() / 2) + 435, 240);
			} else if (betting == 3) {
				Images.drawImage(
						MainMenu.textureLoader.coin3,
						(Main.getWidth() / 2)
								- (MainMenu.textureLoader.button
										.getImageWidth() / 2) + 435, 240);
			} else if (betting == 4) {
				Images.drawImage(
						MainMenu.textureLoader.coin4,
						(Main.getWidth() / 2)
								- (MainMenu.textureLoader.button
										.getImageWidth() / 2) + 435, 240);
			}
			if (betting == 5) {
				Images.drawImage(
						MainMenu.textureLoader.coin5,
						(Main.getWidth() / 2)
								- (MainMenu.textureLoader.button
										.getImageWidth() / 2) + 435, 240);
			}
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

			drawTiles();
			drawCandles();
			mainMenu.setX((Main.getWidth() / 2)
					- (MainMenu.textureLoader.button.getImageWidth() / 2));
			mainMenu.draw();
			drawMoney();
			if (started) {
				if (checkWin) {
					// draw();
				}
			}
			drawLogin();
		}
	}

	int flick1 = 0;
	int flick2 = 2;
	int flick3 = 0;
	int flick4 = 1;
	int flick5 = 0;
	int flick6 = 3;

	long lastFlick = 0;

	Boolean dancing = false;
	long lastDancing = 0;

	Boolean flashFirst = false;
	long flashChange = 0;

	public void drawCandles() {
		if (flashChange == 0)
			flashChange = System.currentTimeMillis();
		Random r = new Random();
		if (r.nextInt(300) == 75 && !dancing) {
			lastDancing = System.currentTimeMillis();
			dancing = true;
		}
		if (dancing && (System.currentTimeMillis() - lastDancing) > 3000) {
			dancing = false;
		}
		Images.drawImage(MainMenu.textureLoader.candle, slotX - 12, slotY - 20);
		Images.drawImage(MainMenu.textureLoader.sideCandle, slotX - 4,
				slotY + 75);
		Images.drawImage(MainMenu.textureLoader.sideCandle2, slotX + 269,
				slotY + 75);
		if (flick1 < 4) {
			if (!dancing) {
				drawFlick(slotX + 27, slotY - 20, flick1);
				drawFlick(slotX + 64, slotY - 16, flick1);
				drawFlick(slotX + 130, slotY - 17, flick1);
				drawFlick(slotX + 173, slotY - 16, flick1);
				drawFlick(slotX + 230, slotY - 16, flick1);
				drawFlick(slotX + 280, slotY - 20, flick1);
				drawFlick(slotX + 6, slotY + 74, flick1);
				drawFlick(slotX + 6, slotY + 138, flick1);
				drawFlick(slotX + 6, slotY + 228, flick1);

				drawFlick(slotX + 290, slotY + 74, flick1);
				drawFlick(slotX + 290, slotY + 138, flick1);
				drawFlick(slotX + 290, slotY + 228, flick1);
			} else {
				if (flashFirst) {
					drawFlick(slotX + 27, slotY - 20, flick1);
					drawFlick(slotX + 130, slotY - 17, flick1);
					drawFlick(slotX + 230, slotY - 16, flick1);
					drawFlick(slotX + 6, slotY + 138, flick1);
					drawFlick(slotX + 290, slotY + 138, flick1);
					if ((System.currentTimeMillis() - flashChange) > 500) {
						flashChange = System.currentTimeMillis();
						flashFirst = false;
					}
				} else if (!flashFirst) {
					drawFlick(slotX + 64, slotY - 16, flick1);
					drawFlick(slotX + 173, slotY - 16, flick1);
					drawFlick(slotX + 280, slotY - 20, flick1);
					drawFlick(slotX + 6, slotY + 74, flick1);
					drawFlick(slotX + 6, slotY + 228, flick1);
					drawFlick(slotX + 290, slotY + 74, flick1);
					drawFlick(slotX + 290, slotY + 228, flick1);
					if ((System.currentTimeMillis() - flashChange) > 500) {
						flashChange = System.currentTimeMillis();
						flashFirst = true;
					}
				}
			}
			if (lastFlick != 0) {
				if (System.currentTimeMillis() - lastFlick > 75) {
					flick1++;
					lastFlick = System.currentTimeMillis();
				}
			} else {
				lastFlick = System.currentTimeMillis();
			}
		} else {
			flick1 = 0;
		}

	}

	public void drawFlick(int x, int y, int flicker) {
		Images.drawImage(candleFlick.get(flicker), x, y);
	}

	int stopped = 1;
	long lastStop = 0;
	long stoppedAt = 0;

	public void drawTiles() {
		String line1 = reel1.getLabelText();
		String[] line1Split = line1.split(" ");
		String line2 = reel2.getLabelText();
		String[] line2Split = line2.split(" ");
		String line3 = reel3.getLabelText();
		String[] line3Split = line3.split(" ");
		int f = 1;
		int total = 1;
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
				if ((System.currentTimeMillis() - stoppedAt) < 1500) {
					if (checkWin) {

					} else {
						Images.drawImage(tiles.get(5), (prev + 31)
								+ ((t * tiles.get(0).getImageWidth())),
								(slotY + 44)
										+ (i * tiles.get(0).getImageHeight())
										- (i * 2));
					}

				} else {
					Images.drawImage(tiles.get(5), (prev + 31)
							+ ((t * tiles.get(0).getImageWidth())),
							(slotY + 44) + (i * tiles.get(0).getImageHeight())
									- (i * 2));
				}
				if (!line1.isEmpty()) {
					if (lineToUse != null) {
						if (isAnimating
								&& ((System.currentTimeMillis() - startedAnimating)) > 1800) {
							isAnimating = false;
							stopped = 1;
							stoppedAt = System.currentTimeMillis();
							f = 1;
							if (checkWin) {
								Sound.playSound(2);
								Main.currency = Main.currency + (betting * 3);
							}
							if (Main.isLoggedIn)
								Main.dbAccess.setCurrencyForPlayer(
										Main.username, Main.currency);
						}
						if (isAnimating) {
							if (((System.currentTimeMillis() - startedAnimating)) < 600) {
								Images.drawImage(tiles.get(new Random()
										.nextInt(5)), (prev + 31)
										+ ((t * tiles.get(0).getImageWidth())),
										(slotY + 44)
												+ (i * tiles.get(0)
														.getImageHeight())
												- (i * 2));
							} else if (((System.currentTimeMillis() - startedAnimating)) >= 600
									&& ((System.currentTimeMillis() - startedAnimating)) < 1200) {
								if (t == 0) {
									Texture tileToDraw = tiles.get(0);
									if (lineToUse[t].equals("A")) {
										tileToDraw = tiles.get(0);
									} else if (lineToUse[t].equals("B")) {
										tileToDraw = tiles.get(1);
									} else if (lineToUse[t].equals("C")) {
										tileToDraw = tiles.get(2);
									} else if (lineToUse[t].equals("D")) {
										tileToDraw = tiles.get(3);
									} else if (lineToUse[t].equals("E")) {
										tileToDraw = tiles.get(4);
									}
									Images.drawImage(tileToDraw, (prev + 31)
											+ ((t * tiles.get(0)
													.getImageWidth())),
											(slotY + 44)
													+ (i * tiles.get(0)
															.getImageHeight())
													- (i * 2));
								} else {
									Images.drawImage(tiles.get(new Random()
											.nextInt(5)), (prev + 31)
											+ ((t * tiles.get(0)
													.getImageWidth())),
											(slotY + 44)
													+ (i * tiles.get(0)
															.getImageHeight())
													- (i * 2));
								}
							} else if (((System.currentTimeMillis() - startedAnimating)) >= 1200) {
								if (t == 1 || t == 0) {
									Texture tileToDraw = tiles.get(0);
									if (lineToUse[t].equals("A")) {
										tileToDraw = tiles.get(0);
									} else if (lineToUse[t].equals("B")) {
										tileToDraw = tiles.get(1);
									} else if (lineToUse[t].equals("C")) {
										tileToDraw = tiles.get(2);
									} else if (lineToUse[t].equals("D")) {
										tileToDraw = tiles.get(3);
									} else if (lineToUse[t].equals("E")) {
										tileToDraw = tiles.get(4);
									}
									Images.drawImage(tileToDraw, (prev + 31)
											+ ((t * tiles.get(0)
													.getImageWidth())),
											(slotY + 44)
													+ (i * tiles.get(0)
															.getImageHeight())
													- (i * 2));
								} else {
									Images.drawImage(tiles.get(new Random()
											.nextInt(5)), (prev + 31)
											+ ((t * tiles.get(0)
													.getImageWidth())),
											(slotY + 44)
													+ (i * tiles.get(0)
															.getImageHeight())
													- (i * 2));
								}
							}
						} else if (!isAnimating) {
							Texture tileToDraw = tiles.get(0);
							if (lineToUse[t].equals("A")) {
								tileToDraw = tiles.get(0);
							} else if (lineToUse[t].equals("B")) {
								tileToDraw = tiles.get(1);
							} else if (lineToUse[t].equals("C")) {
								tileToDraw = tiles.get(2);
							} else if (lineToUse[t].equals("D")) {
								tileToDraw = tiles.get(3);
							} else if (lineToUse[t].equals("E")) {
								tileToDraw = tiles.get(4);
							}
							Images.drawImage(tileToDraw, (prev + 31)
									+ ((t * tiles.get(0).getImageWidth())),
									(slotY + 44)
											+ (i * tiles.get(0)
													.getImageHeight())
											- (i * 2));
						}

					}
				}
				total++;
			}
		}
	}

	public void drawMoney() {
		currencyCounter.setX((Main.getWidth() / 2)
				- (MainMenu.textureLoader.button.getImageWidth() / 2) - 230);
		currencyCounter.setY(360);
		currencyCounter.setLabel(Main.currency + " coins");
		currencyCounter.draw();
		Images.drawImage(MainMenu.textureLoader.coin5, (Main.getWidth() / 2)
				- (MainMenu.textureLoader.button.getImageWidth() / 2) - 180,
				390);
		Images.drawImage(MainMenu.textureLoader.coin1, (Main.getWidth() / 2)
				- (MainMenu.textureLoader.button.getImageWidth() / 2) - 220,
				390);
		Images.drawImage(MainMenu.textureLoader.coin2, (Main.getWidth() / 2)
				- (MainMenu.textureLoader.button.getImageWidth() / 2) - 200,
				390);
		for (int i = 0; i < Main.currency / 3; i = i + 5) {
			Images.drawImage(
					MainMenu.textureLoader.coin5,
					(Main.getWidth() / 2)
							- (MainMenu.textureLoader.button.getImageWidth() / 2)
							- 150, 390 - (i * 2));
		}
		for (int i = 0; i < Main.currency / 3; i = i + 5) {
			Images.drawImage(
					MainMenu.textureLoader.coin5,
					(Main.getWidth() / 2)
							- (MainMenu.textureLoader.button.getImageWidth() / 2)
							- 129, 394 - (i * 2));
		}
		for (int i = 0; i < (Main.currency / 3) - 7; i = i + 5) {
			Images.drawImage(
					MainMenu.textureLoader.coin5,
					(Main.getWidth() / 2)
							- (MainMenu.textureLoader.button.getImageWidth() / 2)
							- 110, 394 - (i * 2));
		}
		for (int i = 0; i < Main.currency / 5; i = i + 5) {
			Images.drawImage(
					MainMenu.textureLoader.coin5,
					(Main.getWidth() / 2)
							- (MainMenu.textureLoader.button.getImageWidth() / 2)
							- 155, 400 - (i * 2));
		}
		for (int i = 0; i < Main.currency / 4; i = i + 5) {
			Images.drawImage(
					MainMenu.textureLoader.coin5,
					(Main.getWidth() / 2)
							- (MainMenu.textureLoader.button.getImageWidth() / 2)
							- 130, 400 - (i * 2));
		}
		for (int i = 0; i < Main.currency / 7; i = i + 5) {
			Images.drawImage(
					MainMenu.textureLoader.coin5,
					(Main.getWidth() / 2)
							- (MainMenu.textureLoader.button.getImageWidth() / 2)
							- 140, 400 - (i * 2));
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
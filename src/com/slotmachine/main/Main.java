package com.slotmachine.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.sound.midi.Soundbank;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.openal.SoundStore;

import com.slotmachine.api.Sound;
import com.slotmachine.db.DatabaseAccess;
import com.slotmachine.menu.MainMenu;
import com.slotmachine.menu.OptionsMenu;
import com.slotmachine.menu.components.Label;

public class Main {
	// Client Variables
	public static int WD, HT;
	public static int currency = 100;
	Boolean drawSubMenuInfo = false;
	public static Boolean isLoggedIn = false;
	public static String username = "";
	public static DatabaseAccess dbAccess = new DatabaseAccess();

	public static Boolean isSoundOn = true, isMusicOn = true;

	// Button Event Variables
	Boolean isDown = false, isDown2 = false, ended = false;

	// Memory Variables
	private static final long MEGABYTE = 1024L * 1024L;

	// Timing and FPS Variables
	long lastFrame, lastFPS;
	int fps;

	// Menu variables
	public static MainMenu m;
	public static OptionsMenu o;
	public static SlotMachineTemp slot;

	// Game State
	private static GameState gameState = GameState.STARTUPLOADING;

	public String currentStatus = "";
	public Label status;

	public Main() throws Exception {
		try {
			WD = 800;
			HT = 600;
			Display.setResizable(true);
			Display.setDisplayMode(new DisplayMode(WD, HT));
			Display.setTitle("The Slot Machine");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
		gameloop();
	}

	Boolean fOnePressed = false;

	int lastPlayedVolume = 0;
	long lastChanged = 0;
	long lastTimeUploadedToSite = 0;

	public void gameloop() throws IOException {
		if (lastTimeUploadedToSite == 0)
			lastTimeUploadedToSite = System.currentTimeMillis();
		if (lastChanged == 0)
			lastChanged = System.currentTimeMillis();
		while (!Display.isCloseRequested()) {

			if (isMusicOn) {
				if (MainMenu.textureLoader != null)
					if (MainMenu.textureLoader.meadow != null)
						if (!MainMenu.textureLoader.meadow.isPlaying()) {
							lastPlayedVolume = OptionsMenu.volume;
							if ((System.currentTimeMillis() - lastChanged) > 1500) {
								Sound.playSound(3);
							}
						} else {
							if (OptionsMenu.volume != lastPlayedVolume) {
								lastChanged = System.currentTimeMillis();
								MainMenu.textureLoader.meadow.stop();
							}
						}
			} else {
				if (MainMenu.textureLoader != null)
					if (MainMenu.textureLoader.meadow != null)
						if (MainMenu.textureLoader.meadow.isPlaying()) {
							MainMenu.textureLoader.meadow.stop();
						}
			}
			SoundStore.get().poll(0);
			WD = Display.getWidth();
			HT = Display.getHeight();

			if (ended)
				break;
			switch (gameState) {
			case LOGIN:
				// Database login code here
			case STARTUPLOADING:
				m = new MainMenu();
				o = new OptionsMenu();
				slot = new SlotMachineTemp();
				gameState = GameState.MAINMENU;
			case MAINMENU:
				if (Keyboard.isKeyDown(Keyboard.KEY_F1) && !fOnePressed) {
					System.out.println("Pressed");
					fOnePressed = true;
					drawSubMenuInfo = !drawSubMenuInfo;
				} else if (!Keyboard.isKeyDown(Keyboard.KEY_F1) && fOnePressed) {
					fOnePressed = false;
				}
				if (!m.getIsExited() && !o.isOnScreen() && !slot.isOnScreen()) {
					m.drawMainMenu();
					if (drawSubMenuInfo)
						drawSubMenuInfo();
					Display.update();
					Display.sync(60);
				}
				if (m.getIsExited()) {
					Display.destroy();
					System.exit(0);
				}

			case OPTIONSMENU:
				if (o.isOnScreen()) {
					o.drawMainMenu();
					Display.update();
					Display.sync(60);
				}
			case SLOTMACHINE:
				if (slot.isOnScreen()) {
					slot.drawSlot();
					Display.update();
					Display.sync(60);
				}
			default:
				break;

			}
			if (status == null)
				status = new Label("Status: " + currentStatus,
						(Main.getWidth() / 2)
								- (MainMenu.textureLoader.button
										.getImageWidth() / 2) + 195, 180);
			if (isLoggedIn
					&& (System.currentTimeMillis() - lastTimeUploadedToSite) > 15000) {
				currentStatus = "Uploading the most recent data for "
						+ username + " after " + (lastTimeUploadedToSite / 100)
						+ " seconds";
				status = new Label("Status: " + currentStatus, 10,
						getHeight() - 20);
				status.draw();

				Main.dbAccess
						.setCurrencyForPlayer(Main.username, Main.currency);
				lastTimeUploadedToSite = System.currentTimeMillis();
			}
		}
		AL.destroy();
		Display.destroy();
	}

	private void drawSubMenuInfo() {

		MainMenu.textureLoader.textBoxTitle.drawString(5, 5, "Test");
	}

	public static BufferedImage glScreenshot() {
		GL11.glReadBuffer(GL11.GL_FRONT);
		int w = Display.getDisplayMode().getWidth();
		int h = Display.getDisplayMode().getHeight();
		int bpp = 4;
		ByteBuffer buffer = BufferUtils.createByteBuffer(w * h * bpp);

		GL11.glReadPixels(0, 0, w, h, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE,
				buffer);

		BufferedImage image = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int i = (x + (w * y)) * bpp;
				int r = buffer.get(i) & 0xFF;
				int g = buffer.get(i + 1) & 0xFF;
				int b = buffer.get(i + 2) & 0xFF;
				image.setRGB(x, h - (y + 1), (0xFF << 24) | (r << 16)
						| (g << 8) | b);
			}
		}

		return image;
	}

	public ByteBuffer loadIcon(String filename, int width, int height)
			throws IOException {
		BufferedImage image = ImageIO.read(new File(filename)); // load image

		// convert image to byte array
		byte[] imageBytes = new byte[width * height * 4];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int pixel = image.getRGB(j, i);
				for (int k = 0; k < 3; k++)
					// red, green, blue
					imageBytes[(i * 16 + j) * 4 + k] = (byte) (((pixel >> (2 - k) * 8)) & 255);
				imageBytes[(i * 16 + j) * 4 + 3] = (byte) (((pixel >> (3) * 8)) & 255); // alpha
			}
		}
		return ByteBuffer.wrap(imageBytes);
	}

	public static void setState(GameState gs) {
		gameState = gs;
	}

	public static long bytesToMegabytes(long bytes) {
		return bytes / MEGABYTE;
	}

	public long getTime() {
		return System.nanoTime() / 1000000;
	}

	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	int f = 0;

	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("Thot FPS: " + fps);
			f = fps;
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public static int getWidth() {
		return Display.getWidth();
	}

	public static int getHeight() {
		return Display.getHeight();
	}

	public static void main(String[] args) throws Exception {
		new Main();
	}
}

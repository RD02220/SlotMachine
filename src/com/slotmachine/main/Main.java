package com.slotmachine.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.examples.spaceinvaders.TextureLoader;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.*;

public class Main {
	// Client Variables
	public static int WD, HT;
	public static double xVariation = 0;
	public Boolean drawInfo = false;

	// Button Event Variables
	Boolean isDown = false, isDown2 = false;

	// Font Variables
	// public TrueTypeFont font;

	// Display Variables
	boolean vsync = false;

	// Memory Variables
	private static final long MEGABYTE = 1024L * 1024L;

	// Timing and FPS Variables
	long lastFrame, lastFPS;
	int fps;

	// World Variables
	int worldID = 0;
	public static boolean editMap = false;
	public static boolean placeObjects = false;

	// Player Variables

	public Main() throws Exception {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Basic LWJGL Display");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
		while (!Display.isCloseRequested()) {
			Display.update();
			Display.sync(60);
		}
		Display.destroy();
		System.exit(0);
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

	public void setDisplayMode(int width, int height, boolean fullscreen) {
		// return if requested DisplayMode is already set
		if ((Display.getDisplayMode().getWidth() == width)
				&& (Display.getDisplayMode().getHeight() == height)
				&& (Display.isFullscreen() == fullscreen)) {
			return;
		}

		try {
			DisplayMode targetDisplayMode = null;

			if (fullscreen) {
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;

				for (int i = 0; i < modes.length; i++) {
					DisplayMode current = modes[i];

					if ((current.getWidth() == width)
							&& (current.getHeight() == height)) {
						if ((targetDisplayMode == null)
								|| (current.getFrequency() >= freq)) {
							if ((targetDisplayMode == null)
									|| (current.getBitsPerPixel() > targetDisplayMode
											.getBitsPerPixel())) {
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}

						// if we've found a match for bpp and frequence against
						// the
						// original display mode then it's probably best to go
						// for this one
						// since it's most likely compatible with the monitor
						if ((current.getBitsPerPixel() == Display
								.getDesktopDisplayMode().getBitsPerPixel())
								&& (current.getFrequency() == Display
										.getDesktopDisplayMode().getFrequency())) {
							targetDisplayMode = current;
							break;
						}
					}
				}
			} else {
				targetDisplayMode = new DisplayMode(width, height);
			}

			if (targetDisplayMode == null) {
				System.out.println("Failed to find value mode: " + width + "x"
						+ height + " fs=" + fullscreen);
				return;
			}

			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);

		} catch (LWJGLException e) {
			System.out.println("Unable to setup mode " + width + "x" + height
					+ " fullscreen=" + fullscreen + e);
		}
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

package com.slotmachine.api;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class SpriteSheet {

	// Strings
	private String path = "";

	// Images
	private BufferedImage spriteSheet;
	
	int h = 0, w = 0;

	public SpriteSheet(String filepath, int height, int width)
			throws IOException {
		path = filepath;
		spriteSheet = ImageIO.read(new File(path));
		h = height;
		w = width;
	}

	public InputStream getSprite(int xGrid, int yGrid, int englarge)
			throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		BufferedImage sub = (spriteSheet.getSubimage(0, yGrid * h,
				spriteSheet.getWidth(), h));
		sub = enlarge(sub, englarge);
		ImageIO.write(sub, "png", os);
		InputStream is = new ByteArrayInputStream(os.toByteArray());
		return is;
	}

	public InputStream getSprite(int xGrid, int yGrid) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		BufferedImage sub = (spriteSheet.getSubimage(0, yGrid * h,
				spriteSheet.getWidth(), h));
		ImageIO.write(sub, "png", os);
		InputStream is = new ByteArrayInputStream(os.toByteArray());
		return is;
	}
	
	public static BufferedImage enlarge(BufferedImage image, int n) {
		int w = n * image.getWidth();
		int h = n * image.getHeight();
		BufferedImage enlargedImage = new BufferedImage(w, h, image.getType());
		for (int y = 0; y < h; ++y) {
			for (int x = 0; x < w; ++x) {
				enlargedImage.setRGB(x, y, image.getRGB(x / n, y / n));
			}
		}
		return enlargedImage;
	}
}

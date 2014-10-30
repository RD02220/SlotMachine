package com.slotmachine.menu;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import com.slotmachine.api.Images;
import com.slotmachine.main.Main;
import com.slotmachine.menu.components.Button;
import com.slotmachine.menu.components.MenuTextBox;

public class OptionsMenu {
	public static MenuTextures textureLoader;
	Button playSounds;
	MenuTextBox textBox;
	Boolean isExited = false, isOnScreen = false, pressedSingle = false;

	public OptionsMenu() throws IOException {
		textureLoader = MainMenu.textureLoader;
		playSounds = new Button("Test", 0, 0);
		textBox = new MenuTextBox("Starting Text", false, false, 20, 100);
	}

	public void drawMainMenu() {
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
			playSounds.draw();
			textBox.draw();
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
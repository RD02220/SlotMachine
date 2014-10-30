package com.slotmachine.menu;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import com.slotmachine.api.Images;
import com.slotmachine.main.Main;
import com.slotmachine.menu.components.MenuButton;
import com.slotmachine.menu.components.TextBox;

public class MainMenu {
	public static MenuTextures textureLoader;
	MenuButton start, options, aboutus, exit;
	TextBox box;
	Boolean isExited = false, isOnScreen = true, pressedSingle = false;

	public MainMenu() throws IOException {

		textureLoader = new MenuTextures();
		start = new MenuButton("Start Playing", 0);
		options = new MenuButton("Options", 1);
		aboutus = new MenuButton("About Us", 2);
		exit = new MenuButton("Exit", 3);
		box = new TextBox("Merlin's Ghost", "Lets play some slots!",
				(Main.getWidth() / 2) + 15, 225);
	}

	public void drawMainMenu() {
		if (isOnScreen) {
			if (start.isActivated()) {
				System.out.println("Start " + isOnScreen);
				pressedSingle = true;
				isOnScreen = false;
				// Start slot machine
			} else if (options.isActivated()) {
				// Options Menu
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
			exit.draw();
			if (start.isHovered()) {
				box.setTitle("Start");
				box.setBodyText("Lets play some slots!");
			} else if (options.isHovered()) {
				box.setTitle("Multiplayer");
				box.setBodyText("");
			} else if (options.isHovered()) {
				box.setTitle("Options");
				box.setBodyText("Edit your individual video, memory, sound and /nother options here!");
			} else if (aboutus.isHovered()) {
				box.setTitle("About Us");
				box.setBodyText("Matt Ryan O'Neal and Chad");
			} else if (exit.isHovered()) {
				box.setTitle("Exit Merlin's Ghost?");
				box.setBodyText("Exit the game? (But why would you want too?)");
			}
			box.draw();
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
}
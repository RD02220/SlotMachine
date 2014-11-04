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

	Button t;
	Label currencyCounter;

	public SlotMachineTemp() throws IOException {
		t = new Button("Pull Lever", (Main.getWidth() / 2)
				- (MainMenu.textureLoader.button.getImageWidth() / 2), 400);
		currencyCounter = new Label("Currency: " + Main.currency,
				(Main.getWidth() / 2)
						- (MainMenu.textureLoader.button.getImageWidth() / 2)
						+ 90, 375);
	}

	Boolean pulled = false;

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
			t.draw();
			if (t.isActivated() && !pulled) {
				pulled = true;
				System.out.println("Pulling Lever");
				// Create a SlotFacade
				SlotFacade player = new SlotFacade();

				// Counters
				int numberOfTries = 0;
				double winPercent;

				// Loop tries.
				while (numberOfTries++ < 1) {
					player.pullLever();
				}
			} else if (!t.isActivated() && pulled) {
				pulled = false;
			}
			currencyCounter.draw();
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
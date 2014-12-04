package com.slotmachine.api;

import org.newdawn.slick.openal.SoundStore;

import com.slotmachine.main.Main;
import com.slotmachine.menu.MainMenu;
import com.slotmachine.menu.OptionsMenu;

public class Sound implements Command {

	public static void playSound(int id) {
		Float[] floats = new Float[10];
		floats[0] = 0.0f;
		floats[1] = 0.1f;
		floats[2] = 0.2f;
		floats[3] = 0.3f;
		floats[4] = 0.4f;
		floats[5] = 0.5f;
		floats[6] = 0.6f;
		floats[7] = 0.7f;
		floats[8] = 0.8f;
		floats[9] = 0.9f;
		if (Main.isSoundOn) {
			if (id == 0) {
				MainMenu.textureLoader.changeBet.playAsSoundEffect(1.0f,
						floats[OptionsMenu.volume], false);
			} else if (id == 1) {
				MainMenu.textureLoader.pullLever.playAsSoundEffect(1.0f,
						floats[OptionsMenu.volume], false);
			} else if (id == 2) {
				MainMenu.textureLoader.spinning.playAsSoundEffect(1.0f,
						floats[OptionsMenu.volume], false);
			} else if (id == 3) {
				MainMenu.textureLoader.meadow.playAsSoundEffect(1.0f,
						floats[OptionsMenu.volume], false);
			}
			SoundStore.get().poll(0);
		}
	}

	public void execute(int id) {
		playSound(id);
	}
}

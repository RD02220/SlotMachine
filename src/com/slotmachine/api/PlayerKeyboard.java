package com.slotmachine.api;

import org.lwjgl.input.Keyboard;

public class PlayerKeyboard {

	public static Boolean isKeyPressed(int key) {
		return Keyboard.isKeyDown(key);
	}

	public static String getPressedKey() {
		Keyboard.enableRepeatEvents(false);
		for (int i = 0; i <= Keyboard.getKeyCount(); i++) {
			if (isKeyPressed(i)) {
				return Keyboard.getKeyName(i);
			}
		}

		return null;
	}

	public static Boolean isShiftDown() {
		if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)
				|| Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			return true;
		return false;
	}
}

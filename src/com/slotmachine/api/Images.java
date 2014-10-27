package com.slotmachine.api;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.slotmachine.main.Main;

public class Images {

	static Texture binds = null;

	public static void drawImage(Texture t, int x, int y) {
		if (binds == null) {
			binds = t;
		}
		if (t != binds) {
			t.bind();
			binds = t;
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// enable alpha blending
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glViewport(0, 0, Main.WD, Main.HT);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Main.WD, Main.HT, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		Color.white.bind();

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(x, y);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(x + t.getTextureWidth(), y);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(x + t.getTextureWidth(), y + t.getTextureHeight());
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(x, y + t.getTextureHeight());
		GL11.glEnd();

	}
}

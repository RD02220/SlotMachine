package com.slotmachine.menu;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class MenuTextures {

	public Texture background, logo, button, buttonHover, textBox,
			writeTextBox, subMenu, slotmachine;
	public TrueTypeFont font, textBoxTitle;
	public FontMetrics metrics, metrics2;

	public MenuTextures() throws IOException {
		Font awtFont = new Font("Tahoma", Font.BOLD, 15);
		font = new TrueTypeFont(awtFont, true);
		BufferedImage bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
		metrics = bi.getGraphics().getFontMetrics(awtFont);
		font = new TrueTypeFont(awtFont, true);

		Font awtFont2 = new Font("Tahoma", Font.BOLD, 11);
		textBoxTitle = new TrueTypeFont(awtFont2, true);
		BufferedImage bi2 = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
		metrics2 = bi2.getGraphics().getFontMetrics(awtFont2);
		textBoxTitle = new TrueTypeFont(awtFont2, true);
		background = TextureLoader.getTexture("PNG",
				ResourceLoader.getResourceAsStream("textures/Menus/bg.png"));
		logo = TextureLoader.getTexture("PNG",
				ResourceLoader.getResourceAsStream("textures/Menus/Logo.png"));

		button = TextureLoader.getTexture("PNG", ResourceLoader
				.getResourceAsStream("textures/Menus/Components/Button.png"));
		buttonHover = TextureLoader
				.getTexture(
						"PNG",
						ResourceLoader
								.getResourceAsStream("textures/Menus/Components/ButtonHover.png"));
		textBox = TextureLoader.getTexture("PNG", ResourceLoader
				.getResourceAsStream("textures/Menus/Components/TextBox.png"));
		writeTextBox = TextureLoader.getTexture("PNG", ResourceLoader
				.getResourceAsStream("textures/Menus/Components/TextBox2.png"));
		subMenu = TextureLoader.getTexture("PNG", ResourceLoader
				.getResourceAsStream("textures/Menus/Components/SubMenu.png"));
		slotmachine = TextureLoader.getTexture("PNG", ResourceLoader
				.getResourceAsStream("textures/SlotMachine/SlotMachine1.png"));
	}
}

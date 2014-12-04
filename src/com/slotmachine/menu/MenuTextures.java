package com.slotmachine.menu;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class MenuTextures {

	public Texture background, logo, button, buttonHover, textBox,
			writeTextBox, subMenu, slotmachine, up, down;
	public Texture coin1, coin2, coin3, coin4, coin5, candle, sideCandle,
			sideCandle2;
	public TrueTypeFont font, textBoxTitle;
	public FontMetrics metrics, metrics2;
	public Audio changeBet, pullLever, spinning, meadow;

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
		up = TextureLoader.getTexture("PNG",
				ResourceLoader.getResourceAsStream("textures/Up.png"));
		down = TextureLoader.getTexture("PNG",
				ResourceLoader.getResourceAsStream("textures/Down.png"));
		coin1 = TextureLoader.getTexture("PNG", ResourceLoader
				.getResourceAsStream("textures/SlotMachine/1Coin.png"));
		coin2 = TextureLoader.getTexture("PNG", ResourceLoader
				.getResourceAsStream("textures/SlotMachine/2Coins.png"));
		coin3 = TextureLoader.getTexture("PNG", ResourceLoader
				.getResourceAsStream("textures/SlotMachine/3Coins.png"));
		coin4 = TextureLoader.getTexture("PNG", ResourceLoader
				.getResourceAsStream("textures/SlotMachine/4Coins.png"));
		coin5 = TextureLoader.getTexture("PNG", ResourceLoader
				.getResourceAsStream("textures/SlotMachine/5Coins.png"));
		candle = TextureLoader.getTexture("PNG", ResourceLoader
				.getResourceAsStream("textures/Candles/Candles.png"));
		sideCandle = TextureLoader.getTexture("PNG", ResourceLoader
				.getResourceAsStream("textures/Candles/SideCandles.png"));
		sideCandle2 = TextureLoader.getTexture("PNG", ResourceLoader
				.getResourceAsStream("textures/Candles/SideCandles2.png"));
		changeBet = AudioLoader.getAudio("WAV", ResourceLoader
				.getResourceAsStream("textures/SoundEffects/playerBet.wav"));
		pullLever = AudioLoader.getAudio("WAV", ResourceLoader
				.getResourceAsStream("textures/SoundEffects/pullLever.wav"));
		spinning = AudioLoader.getAudio("WAV", ResourceLoader
				.getResourceAsStream("textures/SoundEffects/winner.wav"));
		meadow = AudioLoader
				.getAudio(
						"WAV",
						ResourceLoader
								.getResourceAsStream("textures/SoundEffects/MeadowOfThePast.wav"));
	}
}

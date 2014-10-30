package com.slotmachine.menu.components;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import com.slotmachine.api.Images;
import com.slotmachine.main.Main;
import com.slotmachine.menu.MainMenu;

public class TextBox implements Component {

	private String toolTip = "", title = "", bodyText = "";
	private int xPos = 0, yPos = 0;
	public TrueTypeFont font;
	public FontMetrics metrics;

	public TextBox(String titl, String body, int x, int y) {
		Font awtFont = new Font("Tahoma", Font.PLAIN, 11);
		font = new TrueTypeFont(awtFont, true);
		BufferedImage bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
		metrics = bi.getGraphics().getFontMetrics(awtFont);
		font = new TrueTypeFont(awtFont, true);
		title = titl;
		bodyText = body;
		setX(x);
		setY(y);
	}

	public void draw() {
		setX((Main.getWidth() / 2) + 15);
		if (Main.getHeight() < 650) {
			if ((Main.getHeight() - 650) + 75 < 0) {
				yPos = 150;
			} else {
				yPos = (Main.getHeight() - 650) + 225;
			}
		} else
			yPos = 225;
		// Images.drawImage(MainMenu.textureLoader.textBox, xPos, yPos);
		Color.white.bind();
		MainMenu.textureLoader.textBoxTitle.drawString(
				xPos
						+ (MainMenu.textureLoader.textBox.getImageWidth() / 2)
						- (MainMenu.textureLoader.metrics2
								.stringWidth(getTitle()) / 2), yPos + 2,
				getTitle());
		drawTextBody();
	}

	public void drawTextBody() {
		String[] lines = getBodyText().split("/n");
		int i = 1;
		for (String line : lines) {
			if (i == 1)
				font.drawString(xPos + 10, yPos + (19), line);
			else
				font.drawString(xPos + 10, yPos + (18 * i), line);
			i++;
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBodyText() {
		return bodyText;
	}

	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}

	public String getToolTip() {
		return toolTip;
	}

	public void setToolTip(String tip) {
		toolTip = tip;
	}

	public int getX() {
		return xPos;
	}

	public void setX(int x) {
		xPos = x;
	}

	public int getY() {
		return yPos;
	}

	public void setY(int y) {
		yPos = y;
	}

}

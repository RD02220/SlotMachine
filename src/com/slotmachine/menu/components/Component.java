package com.slotmachine.menu.components;

public interface Component {

	public abstract String getToolTip();

	public abstract void setToolTip(String tip);

	public abstract int getX();

	public abstract void setX(int x);

	public abstract int getY();

	public abstract void setY(int y);

	public abstract void draw();
}

package com.slotmachine.world;

public class Tile {
	/**
	 * Tile class to represent space and what not while mapping the 3d world the
	 * slotmachine sits in.
	 * 
	 * @author Ryan
	 * 
	 */
	private int x, z, plane, type = 0;

	public Tile(int xPos, int zPos, int planePos, int type) {
		x = xPos;
		z = zPos;
		plane = planePos;
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public int getPlane() {
		return plane;
	}

	public void setPlane(int plane) {
		this.plane = plane;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}

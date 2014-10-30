package com.slotmachine.api.models;

import org.lwjgl.util.vector.Vector3f;

public class Face {
	/**
	 * Model Face Class. Holds the vector3f values.
	 * 
	 * @author Ryan
	 * 
	 */
	public Vector3f vertex = new Vector3f();
	public Vector3f normal = new Vector3f();

	public Face(Vector3f vertex, Vector3f normal) {
		this.vertex = vertex;
		this.normal = normal;
	}
}

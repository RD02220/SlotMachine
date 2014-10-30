package com.slotmachine.api.models;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Model {

	/**
	 * Model class that holds arraylists of verticies, normals and faces.
	 * 
	 * @author Ryan
	 * 
	 */
	private List<Vector3f> verticies = new ArrayList<Vector3f>();
	private List<Vector3f> normals = new ArrayList<Vector3f>();
	private List<Face> faces = new ArrayList<Face>();
	private List<Vector2f> texVert = new ArrayList<Vector2f>();

	public List<Vector2f> getTexVert() {
		return texVert;
	}

	public void setTexVert(List<Vector2f> texVert) {
		this.texVert = texVert;
	}

	public void draw() {
		GL11.glBegin(GL11.GL_TRIANGLES);
		for (Face face : getFaces()) {
			Vector3f v1 = getVerticies().get((int) face.vertex.x - 1);
			GL11.glVertex3f(v1.x, v1.y, v1.z);
			Vector3f v2 = getVerticies().get((int) face.vertex.y - 1);
			GL11.glVertex3f(v2.x, v2.y, v2.z);
			Vector3f v3 = getVerticies().get((int) face.vertex.z - 1);
			GL11.glVertex3f(v3.x, v3.y, v3.z);
		}
	}

	public void draw(float x, float y, float z) {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glBegin(GL11.GL_TRIANGLES);
		for (Face face : getFaces()) {
			Vector3f v1 = getVerticies().get((int) face.vertex.x - 1);
			GL11.glVertex3f(v1.x + x, v1.y + y, v1.z + z);
			Vector3f v2 = getVerticies().get((int) face.vertex.y - 1);
			GL11.glVertex3f(v2.x + x, v2.y + y, v2.z + z);
			Vector3f v3 = getVerticies().get((int) face.vertex.z - 1);
			GL11.glVertex3f(v3.x + x, v3.y + y, v3.z + z);
		}
		GL11.glDisable(GL11.GL_CULL_FACE);
	}

	public List<Vector3f> getVerticies() {
		return verticies;
	}

	public void setVerticies(List<Vector3f> verticies) {
		this.verticies = verticies;
	}

	public List<Vector3f> getNormals() {
		return normals;
	}

	public void setNormals(List<Vector3f> normals) {
		this.normals = normals;
	}

	public List<Face> getFaces() {
		return faces;
	}

	public void setFaces(List<Face> faces) {
		this.faces = faces;
	}
}

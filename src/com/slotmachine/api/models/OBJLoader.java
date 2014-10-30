package com.slotmachine.api.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

public class OBJLoader {

	@SuppressWarnings("resource")
	public static Model loadModel(File f) throws FileNotFoundException,
			IOException {
		BufferedReader reader = new BufferedReader(new FileReader(f));
		Model m = new Model();
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.startsWith("v ")) {
				float x = Float.valueOf(line.split(" ")[1]);
				float y = Float.valueOf(line.split(" ")[2]);
				float z = Float.valueOf(line.split(" ")[3]);
				List<Vector3f> vert = m.getVerticies();
				vert.add(new Vector3f(x, y, z));
				m.setVerticies(vert);
			} else if (line.startsWith("vn ")) {
				float x = Float.valueOf(line.split(" ")[1]);
				float y = Float.valueOf(line.split(" ")[2]);
				float z = Float.valueOf(line.split(" ")[3]);
				List<Vector3f> norm = m.getNormals();
				norm.add(new Vector3f(x, y, z));
				m.setNormals(norm);
			} else if (line.startsWith("f ")) {
				Vector3f vecIndicies = new Vector3f(Float.valueOf(line
						.split(" ")[1].split("/")[0]), Float.valueOf(line
						.split(" ")[2].split("/")[0]), Float.valueOf(line
						.split(" ")[3].split("/")[0]));
				Vector3f normIndicies = new Vector3f(Float.valueOf(line
						.split(" ")[1].split("/")[2]), Float.valueOf(line
						.split(" ")[2].split("/")[2]), Float.valueOf(line
						.split(" ")[3].split("/")[2]));
				List<Face> face = m.getFaces();
				face.add(new Face(vecIndicies, normIndicies));
				m.setFaces(face);
			}
		}
		reader.close();
		return m;
	}
}

package engine.terrain;

import engine.entity.Mesh;
import engine.entity.Model;
import org.joml.Vector3f;

import static engine.entity.ObjectLoader.generateTerrain;
import static engine.entity.ObjectLoader.loadTexture;
import static utils.Constants.TERRAIN_SIZE;

public class Terrain {

    private final Vector3f position;
    private final Vector3f rotation;
    private float scale;
    private Model model;

    public Terrain(float xPos, float zPos) throws Exception {
        position = new Vector3f(xPos * TERRAIN_SIZE, 0, zPos * TERRAIN_SIZE);
        rotation = new Vector3f();
        model = new Model(generateTerrain(), loadTexture("grass.jpg", false));
        scale = 1;
    }

    public Terrain(float xPos, float yPos, float zPos) throws Exception {
        position = new Vector3f(xPos * TERRAIN_SIZE, yPos, zPos * TERRAIN_SIZE);
        rotation = new Vector3f();
        model = new Model(generateTerrain(), loadTexture("grass.jpg", true));
        scale = 1;
    }

    public Terrain(float xPosition, float zPosition, Model model) throws Exception {
        position = new Vector3f(xPosition * TERRAIN_SIZE, 0, zPosition * TERRAIN_SIZE);
        rotation = new Vector3f();
        this.model = model;
        scale = 1;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public Model getModel() {
        return model;
    }

    public Mesh getMesh() {
        return model.getMesh();
    }
}

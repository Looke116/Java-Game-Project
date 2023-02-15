package engine.terrain;

import engine.entity.Mesh;
import engine.entity.Model;
import engine.entity.Texture;
import org.joml.Vector3f;

import static utils.Constants.TERRAIN_SIZE;
import static utils.ObjectLoader.generateTerrain;
import static utils.ObjectLoader.loadTexture;

public class Terrain {

    private final Vector3f position;
    private final Vector3f rotation;
    private float scale;
    private TerrainModel model;

    public Terrain(float xPos, float zPos) throws Exception {
        position = new Vector3f(xPos * TERRAIN_SIZE, 0, zPos * TERRAIN_SIZE);
        rotation = new Vector3f();
        model = new TerrainModel(
                generateTerrain(),
                loadTexture("grass.jpg", true),
                loadTexture("mud.png", true),
                loadTexture("grassFlowers.png", true),
                loadTexture("path.png", true),
                loadTexture("blendMap.png", true)
        );
        scale = 1;
    }

    public Terrain(float xPos, float yPos, float zPos) throws Exception {
        position = new Vector3f(xPos * TERRAIN_SIZE, yPos, zPos * TERRAIN_SIZE);
        rotation = new Vector3f();
        model = new TerrainModel(
                generateTerrain(),
                loadTexture("grass.jpg", true),
                loadTexture("mud.png", true),
                loadTexture("grassFlowers.png", true),
                loadTexture("path.png", true),
                loadTexture("blendMap.png", true)
        );
        scale = 1;
    }

    public Terrain(float xPosition, float zPosition, TerrainModel model) {
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

    public TerrainModel getModel() {
        return model;
    }

    public Mesh getMesh() {
        return model.getMesh();
    }

    public Texture getTexture() {
        return model.getTexture();
    }
}

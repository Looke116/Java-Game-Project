package engine.entity;

import org.joml.Vector3f;

public class Entity {

    private final Vector3f position;
    private final Vector3f rotation;
    private float scale;
    private Model model;

    public Entity(Vector3f position, Vector3f rotation, Model model) {
        this.position = position;
        this.rotation = rotation;
        this.model = model;
        scale = 1;
    }

    public Entity(Vector3f position, Vector3f rotation, float scale, Model model) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.model = model;
    }

    public void incrementPos(float x, float y, float z) {
        position.x += x;
        position.y += y;
        position.z += z;
    }

    public void incrementRot(float x, float y, float z) {
        rotation.x += x;
        rotation.y += y;
        rotation.z += z;
    }

    public void setPos(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public void setRot(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
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

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Mesh getMesh() {
        return model.getMesh();
    }

    public void setMesh(Mesh mesh) {
        model.setMesh(mesh);
    }

    public Texture getTexture() {
        return model.getTexture();
    }

    public void setTexture(Texture texture) {
        model.setTexture(texture);
    }

    public float getShineDamper() {
        return model.getMaterial().getShineDamper();
    }

    public void setShineDamper(float shineDamper) {
        model.getMaterial().setShineDamper(shineDamper);
    }

    public float getReflectivity() {
        return model.getMaterial().getReflectivity();
    }

    public void setReflectivity(float reflectivity) {
        model.getMaterial().setReflectivity(reflectivity);
    }
}

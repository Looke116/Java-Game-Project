package engine.entity;

public class Model {

    private Mesh mesh;
    private Texture texture;
    private Material material;
    private boolean useFakeLighting = false;

    public Model() {
        mesh = new Mesh();
        texture = new Texture();
        material = new Material();
    }

    public Model(Mesh mesh, Texture texture) {
        this.mesh = mesh;
        this.texture = texture;
        material = new Material();
    }

    public Model(Mesh mesh, Texture texture, Material material) {
        this.mesh = mesh;
        this.texture = texture;
        this.material = material;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public boolean getTransparency() {
        return texture.getTransparency();
    }

    public void setTransparency(boolean transparency) {
        texture.setTransparency(transparency);
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public float getShineDamper() {
        return material.getShineDamper();
    }

    public void setShineDamper(float shineDamper) {
        material.setShineDamper(shineDamper);
    }

    public float getReflectivity() {
        return material.getReflectivity();
    }

    public void setReflectivity(float reflectivity) {
        material.setReflectivity(reflectivity);
    }

    public boolean isUseFakeLighting() {
        return useFakeLighting;
    }

    public void setUseFakeLighting(boolean useFakeLighting) {
        this.useFakeLighting = useFakeLighting;
    }
}

package engine.entity;

public class Model {

    private Mesh mesh;
    private Texture texture;
    private Material material;

    public Model() {
        mesh = null;
        texture = null;
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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}

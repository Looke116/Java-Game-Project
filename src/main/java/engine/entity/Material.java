package engine.entity;

public class Material {

    private float shineDamper;
    private float reflectivity;

    public Material() {
        shineDamper = 1;
        reflectivity = 0;
    }

    public Material(float shineDamper, float reflectivity) {
        this.shineDamper = shineDamper;
        this.reflectivity = reflectivity;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    /*
    private Vector4f ambientColor;
    private Vector4f diffuseColor;
    private Vector4f specularColor;
    private Texture texture;
    private float reflectance;

    public Material() {
        ambientColor = DEFAULT_COLOR;
        diffuseColor = DEFAULT_COLOR;
        specularColor = DEFAULT_COLOR;
        texture = null;
        reflectance = 0;
    }

    public Material(Texture texture) {
        this(DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR, texture, 0);
    }

    public Material(Vector4f color, float reflectance) {
        this(color, color, color, null, reflectance);
    }

    public Material(Vector4f color, float reflectance, Texture texture) {
        this(color, color, color, texture, reflectance);
    }

    public Material(Vector4f ambientColor, Vector4f diffuseColor, Vector4f specularColor, Texture texture, float reflectance) {
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.texture = texture;
        this.reflectance = reflectance;
    }

    public boolean hasTexture() {
        return texture != null;
    }

    public Vector4f getAmbientColor() {
        return ambientColor;
    }

    public void setAmbientColor(Vector4f ambientColor) {
        this.ambientColor = ambientColor;
    }

    public Vector4f getDiffuseColor() {
        return diffuseColor;
    }

    public void setDiffuseColor(Vector4f diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    public Vector4f getSpecularColor() {
        return specularColor;
    }

    public void setSpecularColor(Vector4f specularColor) {
        this.specularColor = specularColor;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public float getReflectance() {
        return reflectance;
    }

    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }*/
}

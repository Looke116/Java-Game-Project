package engine.terrain;

import engine.entity.Material;
import engine.entity.Mesh;
import engine.entity.Model;
import engine.entity.Texture;

public class TerrainModel extends Model {
    Texture rTexture;
    Texture gTexture;
    Texture bTexture;
    Texture blendMap;

    public TerrainModel(Mesh mesh, Texture texture, Texture rTexture, Texture gTexture, Texture bTexture, Texture blendMap) {
        super(mesh, texture);
        this.rTexture = rTexture;
        this.gTexture = gTexture;
        this.bTexture = bTexture;
        this.blendMap = blendMap;
    }

    public TerrainModel(Mesh mesh, Texture texture, Material material, Texture rTexture, Texture gTexture, Texture bTexture, Texture blendMap) {
        super(mesh, texture, material);
        this.rTexture = rTexture;
        this.gTexture = gTexture;
        this.bTexture = bTexture;
        this.blendMap = blendMap;
    }

    public Texture getrTexture() {
        return rTexture;
    }

    public Texture getgTexture() {
        return gTexture;
    }

    public Texture getbTexture() {
        return bTexture;
    }

    public Texture getBlendMap() {
        return blendMap;
    }
}

package main;

import engine.entity.Entity;
import engine.entity.Model;
import engine.lighting.Light;
import engine.rendering.Camera;
import engine.terrain.Terrain;
import org.joml.Vector3f;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scene {

    private Camera camera;
    private Light light;

    private Map<Model, List<Entity>> entities;
    private List<Entity> entityList;
    private List<Terrain> terrainList;
    private Vector3f skyColor;

    private float fogDensity;
    private float fogGradient;

    public Scene(List<Entity> entityList, List<Terrain> terrainList, Camera camera, Light light) {
        this.camera = camera;
        this.light = light;
        this.entities = new HashMap<>();
        this.entityList = entityList;
        this.terrainList = terrainList;
        this.skyColor = new Vector3f(0.5f, 0.5f, 1);
        processEntities(entityList);
    }

    public Scene(List<Entity> entityList, List<Terrain> terrainList, Camera camera, Light light, Vector3f skyColor) {
        this.camera = camera;
        this.light = light;
        this.entities = new HashMap<>();
        this.entityList = entityList;
        this.terrainList = terrainList;
        this.skyColor = skyColor;
        processEntities(entityList);
    }

    private void processEntities(List<Entity> entityList) {
        for (Entity entity : entityList) {
            List<Entity> list;
            Model model = entity.getModel();
            if (entities.containsKey(model)) {
                list = entities.get(model);
            } else {
                list = new ArrayList<>();
            }
            list.add(entity);
            entities.put(model, list);
        }
    }

    public void clearScene() {
        camera = null;
        light = null;
        entities.clear();
        entityList.clear();
        terrainList.clear();
        skyColor = null;
    }

    public void addEntity(Entity entity) {
        Model model = entity.getModel();
    }

    public void addTerrain(Terrain terrain) {
        terrainList.add(terrain);
    }

    public void moveCamera(int x, int y, int z) {
        camera.movePos(x, y, z);
    }

    public void moveCamera(Vector3f vector) {
        camera.movePos(vector);
    }

    public void rotateCamera(int x, int y, int z) {
        camera.moveRot(x, y, z);
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<Entity> entityList) {
        this.entityList = entityList;
    }

    public List<Terrain> getTerrainList() {
        return terrainList;
    }

    public void setTerrainList(List<Terrain> terrainList) {
        this.terrainList = terrainList;
    }

    public Map<Model, List<Entity>> getEntities() {
        return entities;
    }

    public void setEntities(Map<Model, List<Entity>> entities) {
        this.entities = entities;
    }

    public Vector3f getSkyColor() {
        return skyColor;
    }

    public void setSkyColor(Vector3f skyColor) {
        this.skyColor = skyColor;
    }

    public float getFogDensity() {
        return fogDensity;
    }

    public void setFogDensity(float fogDensity) {
        this.fogDensity = fogDensity;
    }

    public float getFogGradient() {
        return fogGradient;
    }

    public void setFogGradient(float fogGradient) {
        this.fogGradient = fogGradient;
    }
}

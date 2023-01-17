package main;

import engine.Window;
import engine.entity.Entity;
import engine.entity.Material;
import engine.entity.Model;
import engine.entity.ObjectLoader;
import engine.io.MouseInput;
import engine.lighting.Light;
import engine.rendering.Camera;
import engine.rendering.Render;
import engine.terrain.Terrain;
import logic.ILogic;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static engine.entity.ObjectLoader.loadOBJModel;
import static engine.entity.ObjectLoader.loadTexture;
import static org.lwjgl.glfw.GLFW.*;
import static utils.Constants.CAMERA_MOVE_SPEED;

enum Map {
    PLAINS,
    FOREST
}

public class TestGame implements ILogic {

    private final Window window;
    private final Render render;
    private final ObjectLoader loader;
    private final Vector3f cameraInc;
    private Scene forest;
    private Scene plains;
    private Map map;

    public TestGame() {
        window = Launcher.getWindow();
        render = Launcher.getRender();
        loader = new ObjectLoader();
        cameraInc = new Vector3f();
//        lightAngle = -90;
    }

    @Override
    public void init() throws Exception {
        setUpPlains();
        setUpForest();
        map = Map.PLAINS;
    }

    @Override
    public void input() {
        int cameraMovementSpeed = 1;
        if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
            cameraMovementSpeed = 5;
        }

        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -cameraMovementSpeed;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = cameraMovementSpeed;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -cameraMovementSpeed;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = cameraMovementSpeed;
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            cameraInc.y = -cameraMovementSpeed;
        } else if (window.isKeyPressed(GLFW_KEY_SPACE)) {
            cameraInc.y = cameraMovementSpeed;
        }

        glfwSetKeyCallback(window.getId(), (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_X && action == GLFW_RELEASE) {
                if (map == Map.PLAINS) {
                    map = Map.FOREST;
                } else if (map == Map.FOREST) {
                    map = Map.PLAINS;
                }
            } else if (key == GLFW_KEY_R && action == GLFW_RELEASE) {
                if (map == Map.PLAINS) {
                    Camera camera = plains.getCamera();
                    plains.clearScene();
                    try {
                        setUpPlains();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    plains.setCamera(camera);
                } else if (map == Map.FOREST) {
                    Camera camera = forest.getCamera();
                    forest.clearScene();
                    try {
                        setUpForest();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    forest.setCamera(camera);
                }
            }
        });
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {
        if (map == Map.PLAINS) {
            plains.moveCamera(cameraInc.mul(CAMERA_MOVE_SPEED));

            if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
                plains.rotateCamera(0, 1, 0);
            } else if (window.isKeyPressed(GLFW_KEY_LEFT)) {
                plains.rotateCamera(0, -1, 0);
            }
            if (window.isKeyPressed(GLFW_KEY_UP)) {
                plains.rotateCamera(-1, 0, 0);
            } else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
                plains.rotateCamera(1, 0, 0);
            }
        } else if (map == Map.FOREST) {
            forest.moveCamera(cameraInc.mul(CAMERA_MOVE_SPEED));

            if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
                forest.rotateCamera(0, 1, 0);
            } else if (window.isKeyPressed(GLFW_KEY_LEFT)) {
                forest.rotateCamera(0, -1, 0);
            }
            if (window.isKeyPressed(GLFW_KEY_UP)) {
                forest.rotateCamera(-1, 0, 0);
            } else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
                forest.rotateCamera(1, 0, 0);
            }
        }

//        if (mouseInput.isLeftButtonPressed()) {
//            Vector2f rotVec = mouseInput.getDisplayVector();
//            camera.moveRot(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
//        }
//        if (window.isKeyPressed(GLFW_KEY_E)) {
//            for (Terrain entity : terrainList) {
//                if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
//                    entity.incrementRot(0.0f, 1.0f * 2, 0.0f);
//                } else {
//                    entity.incrementRot(0.0f, 1.0f, 0.0f);
//                }
//            }
//        } else if (window.isKeyPressed(GLFW_KEY_Q)) {
//            for (Terrain entity : terrainList) {
//                if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
//                    entity.incrementRot(0.0f, -1.0f * 2, 0.0f);
//
//                } else {
//                    entity.incrementRot(0.0f, -1.0f, 0.0f);
//                }
//            }
//        }
//        lightAngle += 0.5f;
//        if (lightAngle > 90) {
//            directionalLight.setIntensity(0);
//            if (lightAngle >= 360) {
//                lightAngle = -90;
//            }
//        } else if (lightAngle <= -80 || lightAngle >= 80) {
//            float factor = 1- (Math.abs(lightAngle) - 80) / 10.0f;
//            directionalLight.setIntensity(factor);
//            directionalLight.getColor().y = Math.max(factor, 0.9f);
//            directionalLight.getColor().z = Math.max(factor, 0.5f);
//        }else {
//            directionalLight.setIntensity(1);
//            directionalLight.getColor().x = 1;
//            directionalLight.getColor().y = 1;
//            directionalLight.getColor().z = 1;
//        }
//        double angle = Math.toRadians(lightAngle);
//        directionalLight.getDirection().x = (float) Math.sin(angle);
//        directionalLight.getDirection().y = (float) Math.cos(angle);*/
    }

    @Override
    public void render() {
        if (map == Map.PLAINS) {
            render.render(plains);
        } else if (map == Map.FOREST) {
            render.render(forest);
        }
    }

    @Override
    public void cleanup() {
        loader.cleanup();
        render.cleanup();
    }

    void setUpPlains() throws Exception {
        List<Entity> entityList = new ArrayList<>();
        List<Terrain> terrainList = new ArrayList<>();

        Model stallModel = new Model(
                loadOBJModel("stall.obj"),
                loadTexture("stallTexture.png", false),
                new Material(40, 0.4f));
        Entity stall = new Entity(
                new Vector3f(1, -2, -20),
                new Vector3f(0, 200, 0),
                stallModel);
        stall.setScale(0.5F);
        entityList.add(stall);

        Random random = new Random();
        Model treeModel = new Model(loadOBJModel("tree.obj"),
                loadTexture("tree.png", false),
                new Material());
        for (int i = 0; i < random.nextInt(500, 1000); i++) {
            Vector3f pos = new Vector3f(
                    random.nextFloat(-300, 300),
                    -2,
                    random.nextFloat(-300, 300));
            Entity tree = new Entity(
                    pos,
                    new Vector3f(0, random.nextFloat(0, 360), 0),
                    treeModel);
            entityList.add(tree);
        }
        Model grassModel = new Model(loadOBJModel("grassModel.obj"),
                loadTexture("grassTexture.png", false),
                new Material());
        grassModel.setTransparency(true);
        grassModel.setUseFakeLighting(true);
        Model flowerModel = new Model(loadOBJModel("grassModel.obj"),
                loadTexture("flower.png", false),
                new Material());
        flowerModel.setTransparency(true);
        flowerModel.setUseFakeLighting(true);
        for (int i = 0; i < random.nextInt(1000, 2000); i++) {
            Vector3f pos = new Vector3f(
                    random.nextFloat(-300, 300),
                    -2,
                    random.nextFloat(-300, 300));
            Entity grass;
            if (random.nextFloat(0, 1) > 0.5f) {
                grass = new Entity(
                        pos,
                        new Vector3f(0, random.nextFloat(0, 360), 0),
                        grassModel);
            } else {
                grass = new Entity(
                        pos,
                        new Vector3f(0, random.nextFloat(0, 360), 0),
                        flowerModel);
            }
            entityList.add(grass);
        }
        Model fernModel = new Model(loadOBJModel("fern.obj"),
                loadTexture("fern.png", false),
                new Material());
        fernModel.setTransparency(true);
        fernModel.setUseFakeLighting(true);
        for (int i = 0; i < 4000; i++) {
            Vector3f pos = new Vector3f(
                    random.nextFloat(-300, 300),
                    -2,
                    random.nextFloat(-300, 300));
            Entity fern = new Entity(
                    pos,
                    new Vector3f(0, random.nextFloat(0, 360), 0),
                    fernModel);
            fern.setScale(0.3F);
            entityList.add(fern);
        }


        Terrain terrain1 = new Terrain(0, -2, 0);
        Terrain terrain2 = new Terrain(-1, -2, 0);
        Terrain terrain3 = new Terrain(0, -2, -1);
        Terrain terrain4 = new Terrain(-1, -2, -1);
        terrainList.add(terrain1);
        terrainList.add(terrain2);
        terrainList.add(terrain3);
        terrainList.add(terrain4);

        plains = new Scene(entityList,
                terrainList,
                new Camera(),
                new Light());
        plains.setFogDensity(0.007f);
        plains.setFogGradient(3);
    }

    void setUpForest() throws Exception {
        List<Entity> entityList = new ArrayList<>();
        List<Terrain> terrainList = new ArrayList<>();

        Random random = new Random();
        Model treeModel1 = new Model(loadOBJModel("tree.obj"),
                loadTexture("tree.png", false),
                new Material());
        Model treeModel2 = new Model(loadOBJModel("lowPolyTree.obj"),
                loadTexture("lowPolyTree.png", false),
                new Material());
        for (int i = 0; i < random.nextInt(2000, 2500); i++) {
            Entity tree;
            Vector3f pos = new Vector3f(
                    random.nextFloat(-300, 300),
                    -2,
                    random.nextFloat(-300, 300));
            float chance = random.nextFloat(0, 2);
            if (chance < 1) {
                tree = new Entity(
                        pos,
                        new Vector3f(0, random.nextFloat(0, 360), 0),
                        treeModel1);
                chance = random.nextFloat(2, 4.5f);
                tree.setScale(chance);
            } else {
                tree = new Entity(
                        pos,
                        new Vector3f(0, random.nextFloat(0, 360), 0),
                        treeModel2);
                chance = random.nextFloat(0, 1);
                tree.setScale(chance);
            }
            entityList.add(tree);
        }

        Terrain terrain1 = new Terrain(0, -2, 0);
        Terrain terrain2 = new Terrain(-1, -2, 0);
        Terrain terrain3 = new Terrain(0, -2, -1);
        Terrain terrain4 = new Terrain(-1, -2, -1);
        terrainList.add(terrain1);
        terrainList.add(terrain2);
        terrainList.add(terrain3);
        terrainList.add(terrain4);

        Light light = new Light();
        light.setColor(0.4f,0.4f,0.4f);
        forest = new Scene(entityList,
                terrainList,
                new Camera(),
                light,
                new Vector3f(0.3f, 0.3f, 0.3f));
        forest.setFogDensity(0.01f);
        forest.setFogGradient(1f);
    }
}

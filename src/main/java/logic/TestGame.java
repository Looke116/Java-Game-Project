package logic;

import engine.Window;
import engine.entity.*;
import engine.io.MouseInput;
import engine.lighting.Light;
import engine.rendering.Camera;
import engine.rendering.Render;
import main.Launcher;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static utils.Consts.CAMERA_MOVE_SPEED;
import static utils.Consts.MOUSE_SENSITIVITY;

public class TestGame implements ILogic {

    private final Window window;
    private final Render render;
    private final ObjectLoader loader;
    private final List<Entity> entities;
    private final Camera camera;
    private final Vector3f cameraInc;
    private Light light;

    public TestGame() {
        window = Launcher.getWindow();
        render = Launcher.getRender();
        loader = new ObjectLoader();
        entities = new ArrayList<>();
        camera = new Camera();
        cameraInc = new Vector3f();
//        lightAngle = -90;
    }

    @Override
    public void init() throws Exception {
//        float[] vertices = new float[]{
//                -0.5f, 0.5f, 0.5f,
//                -0.5f, -0.5f, 0.5f,
//                0.5f, -0.5f, 0.5f,
//                0.5f, 0.5f, 0.5f,
//                -0.5f, 0.5f, -0.5f,
//                0.5f, 0.5f, -0.5f,
//                -0.5f, -0.5f, -0.5f,
//                0.5f, -0.5f, -0.5f,
//                -0.5f, 0.5f, -0.5f,
//                0.5f, 0.5f, -0.5f,
//                -0.5f, 0.5f, 0.5f,
//                0.5f, 0.5f, 0.5f,
//                0.5f, 0.5f, 0.5f,
//                0.5f, -0.5f, 0.5f,
//                -0.5f, 0.5f, 0.5f,
//                -0.5f, -0.5f, 0.5f,
//                -0.5f, -0.5f, -0.5f,
//                0.5f, -0.5f, -0.5f,
//                -0.5f, -0.5f, 0.5f,
//                0.5f, -0.5f, 0.5f,
//        };
//
//        float[] textureCoords = new float[]{
//                0.0f, 0.0f,
//                0.0f, 0.5f,
//                0.5f, 0.5f,
//                0.5f, 0.0f,
//                0.0f, 0.0f,
//                0.5f, 0.0f,
//                0.0f, 0.5f,
//                0.5f, 0.5f,
//                0.0f, 0.5f,
//                0.5f, 0.5f,
//                0.0f, 1.0f,
//                0.5f, 1.0f,
//                0.0f, 0.0f,
//                0.0f, 0.5f,
//                0.5f, 0.0f,
//                0.5f, 0.5f,
//                0.5f, 0.0f,
//                1.0f, 0.0f,
//                0.5f, 0.5f,
//                1.0f, 0.5f,
//        };
//
//        int[] indices = new int[]{
//                0, 1, 3, 3, 1, 2,       // Front
//                8, 10, 11, 9, 8, 11,    // Top
//                12, 13, 7, 5, 12, 7,    // Right
//                6, 15, 14, 6, 14, 4,    // Left
//                19, 18, 16, 19, 16, 17, // Bottom
//                7, 6, 4, 7, 4, 5,       // Back
//        };
//
//        Model cubeModel;
//        cubeModel = loader.loadModel(vertices, textureCoords, indices);
//        cubeModel.setMaterial(new Texture(loader.loadTexture("missing.png")));
//        Entity cube = new Entity(cubeModel, new Vector3f(-2, 0, -5), new Vector3f(0, 0, 0), 1);
//        entities.add(cube);
        Model stallModel = new Model(
                loader.loadOBJModel("stall.obj"),
                new Texture(loader.loadTexture("stallTexture.png")),
                new Material(40, 0.4f));
        Entity stall = new Entity(new Vector3f(1, -2, -20), new Vector3f(0, 200, 0), stallModel);
        entities.add(stall);

        Model logModel = new Model();
        logModel.setMesh(loader.loadOBJModel("log.obj"));
        logModel.setTexture(new Texture(loader.loadTexture("log.jpg")));
        Entity log = new Entity(new Vector3f(3, -0.5f, -5), new Vector3f(0, 200, 0), logModel);
        entities.add(log);

        Model dragonModel = new Model(
                loader.loadOBJModel("dragon.obj"),
                new Texture(loader.loadTexture("white.png")),
                new Material(10, 1));
        Entity dragon = new Entity(
                new Vector3f(-5, -2, -10),
                new Vector3f(0, 200, 0),
                dragonModel);
        entities.add(dragon);

        light = new Light(new Vector3f(-1, -10, 0), new Vector3f(1, 1, 1));
    }

    @Override
    public void input() {
        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -1;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -1;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            cameraInc.y = -1;
        } else if (window.isKeyPressed(GLFW_KEY_SPACE)) {
            cameraInc.y = 1;
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {
        camera.movePos(cameraInc.mul(CAMERA_MOVE_SPEED));

        if (mouseInput.isLeftButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplayVector();
            camera.moveRot(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        }
        if (window.isKeyPressed(GLFW_KEY_E)) {
            for (Entity entity : entities) {
                if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
                    entity.incrementRot(0.0f, 1.0f * 2, 0.0f);
                } else {
                    entity.incrementRot(0.0f, 1.0f, 0.0f);
                }
            }
        } else if (window.isKeyPressed(GLFW_KEY_Q)) {
            for (Entity entity : entities) {
                if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
                    entity.incrementRot(0.0f, -1.0f * 2, 0.0f);

                } else {
                    entity.incrementRot(0.0f, -1.0f, 0.0f);
                }
            }
        }
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
//        directionalLight.getDirection().y = (float) Math.cos(angle);
    }

    @Override
    public void render() {
        render.render(entities, camera, light);
    }

    @Override
    public void cleanup() {
        loader.cleanup();
        render.cleanup();
    }
}

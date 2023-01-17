package utils;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Constants {

    // Strings
    public static final String TITLE = "GAME";
    public static final String RESOURCES_PATH = "src/main/resources/";

    // Rendering Constants
    public static final float Z_NEAR = 0.01f;
    public static final float Z_FAR = 1000f;
    public static final float FOV = (float) Math.toRadians(60);

    // Window Constants
    public static final int WIDTH = 1600;
    public static final int HEIGHT = 900;

    // Mouse Constants
    public final static float MOUSE_SENSITIVITY = 1.0f;

    // Camera Constants
    public final static float CAMERA_MOVE_SPEED = 0.05f;

    // Terrain Constants
    public final static float TERRAIN_SIZE = 800;
    public final static int TERRAIN_VERTEX_COUNT = 128;

    public final static float SPECULAR_POWER = 10f;

    public final static Vector4f DEFAULT_COLOR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    public final static Vector3f AMBIENT_LIGHT = new Vector3f(0.3f, 0.3f, 0.3f);
}

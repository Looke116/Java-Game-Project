package utils;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Consts {

    public static final String TITLE = "GAME";
    public static final String RESOURCES_PATH = "src/main/resources/";

    public static final float FOV = (float) Math.toRadians(60);
    public static final float Z_NEAR = 0.01f;
    public static final float Z_FAR = 1000f;

    public final static float MOUSE_SENSITIVITY = 1.0f;
    public final static float CAMERA_MOVE_SPEED = 0.05f;

    public final static float SPECULAR_POWER = 10f;

    public final static Vector4f DEFAULT_COLOR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    public final static Vector3f AMBIENT_LIGHT = new Vector3f(0.3f, 0.3f, 0.3f);
}

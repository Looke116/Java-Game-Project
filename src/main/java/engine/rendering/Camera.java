package engine.rendering;

import org.joml.Vector3f;

public class Camera {

    private final Vector3f pos;
    private final Vector3f rot;

    public Camera() {
        pos = new Vector3f(0, 0, 0);
        rot = new Vector3f(0, 0, 0);
    }

    public Camera(Vector3f pos, Vector3f rot) {
        this.pos = pos;
        this.rot = rot;
    }

    public void movePos(Vector3f vector) {
        if (vector.z != 0) {
            pos.x += (float) Math.sin(Math.toRadians(rot.y)) * -1.0f * vector.z;
            pos.z += (float) Math.cos(Math.toRadians(rot.y)) * vector.z;
        }


        if (vector.x != 0) {
            pos.x += (float) Math.sin(Math.toRadians(rot.y - 90)) * -1.0f * vector.x;
            pos.z += (float) Math.cos(Math.toRadians(rot.y - 90)) * vector.x;
        }
        pos.y += vector.y;
    }

    public void movePos(float x, float y, float z) {
        if (z != 0) {
            pos.x += (float) Math.sin(Math.toRadians(rot.y)) * -1.0f * z;
            pos.z += (float) Math.cos(Math.toRadians(rot.y)) * z;
        }


        if (x != 0) {
            pos.x += (float) Math.sin(Math.toRadians(rot.y - 90)) * -1.0f * x;
            pos.z += (float) Math.cos(Math.toRadians(rot.y - 90)) * x;
        }
        pos.y += y;
    }

    public void moveRot(float x, float y, float z) {
        rot.x += x;
        rot.y += y;
        rot.z += z;
    }

    public void setPos(long x, long y, long z) {
        pos.x = x;
        pos.y = y;
        pos.z = z;
    }

    public void setRot(long x, long y, long z) {

        rot.x = x;
        rot.y = y;
        rot.z = z;
    }

    public Vector3f getPos() {
        return pos;
    }

    public Vector3f getRot() {
        return rot;
    }
}

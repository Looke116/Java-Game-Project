package engine.lighting;

import org.joml.Vector3f;

public class Light {
    private Vector3f position;
    private Vector3f color;

    public Light(Vector3f position, float colorR, float colorG, float colorB) {
        this.position = position;
        this.color = new Vector3f(colorR, colorG, colorB);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
}

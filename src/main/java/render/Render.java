package render;

import engine.Window;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;

public class Render {

    private final Window window;

    public Render(Window window) {
        GL.createCapabilities();
        this.window = window;
    }

    public void init() {

    }

    public void render() {
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void cleanup() {

    }
}

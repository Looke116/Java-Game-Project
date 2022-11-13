package logic;

import engine.Window;
import main.Launcher;
import render.Render;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static utils.Consts.TITLE;

public class TestGame implements ILogic {

    private int direction = 0;
    private float color = 0.0f;

    private final Window window;
    private final Render render;

    public TestGame() {
        window = Launcher.getWindow();
        render = new Render(window);
    }

    @Override
    public void init() throws Exception {
        render.init();
    }

    @Override
    public void input() {
        if (window.isKeyPressed(GLFW_KEY_UP)) {
            direction = 1;
        } else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            direction = -1;
        } else {
            direction = 0;
        }
    }

    @Override
    public void update() {
        color += direction * 0.01f;
        if (color > 1) {
            color = 1;
        } else if (color < 0) {
            color = 0;
        }
    }

    @Override
    public void render() {
        window.setClearColor(color, color, color, 1.0f);
        render.clear();
    }

    @Override
    public void cleanup() {
        render.cleanup();
    }

    public Window getWindow() {
        return window;
    }
}

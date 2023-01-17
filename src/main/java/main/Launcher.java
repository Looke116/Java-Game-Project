package main;

import engine.GameEngine;
import engine.Window;
import engine.rendering.Render;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static utils.Constants.*;

public class Launcher {

    private static Window window;
    private static Render render;
    private static TestGame testGame;
    GLFWErrorCallback errorCallback;

    public static Window getWindow() {
        return window;
    }

    public static Render getRender() {
        return render;
    }

    public static TestGame getGame() {
        return testGame;
    }

    public void init() {
        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
        if (!glfwInit()) throw new RuntimeException("Could not initialize GLFW");
    }

    public void launch() {
        init();

        window = new Window(TITLE, WIDTH, HEIGHT, false);
        render = new Render();
        testGame = new TestGame();
        GameEngine gameEngine = new GameEngine();
        gameEngine.run();

        cleanup();
    }

    private void cleanup() {
        errorCallback.free();
    }
}

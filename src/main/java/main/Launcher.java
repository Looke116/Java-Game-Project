package main;

import engine.GameEngine;
import engine.Window;
import engine.rendering.Render;
import logic.TestGame;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static utils.Consts.TITLE;

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

        window = new Window(TITLE, 800, 600, false);
        render = new Render(window);
        render.init();
        testGame = new TestGame();
        GameEngine gameEngine = new GameEngine();


        try {
            gameEngine.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        cleanup();
    }

    private void cleanup() {
        errorCallback.free();
    }
}

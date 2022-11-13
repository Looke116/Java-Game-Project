package main;

import engine.GameEngine;
import engine.Window;
import logic.TestGame;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static utils.Consts.TITLE;

public class Launcher {

    GLFWErrorCallback errorCallback;
    private static Window window;
    private static TestGame testGame;

    public void init() {
        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
        if (!glfwInit()) throw new RuntimeException("Could not initialize GLFW");
    }

    public void launch(){
        init();

        window = new Window(TITLE, 800, 600, false);
        testGame = new TestGame();
        GameEngine gameEngine = new GameEngine();


        try {
            gameEngine.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        cleanup();
    }

    public static Window getWindow() {
        return window;
    }

    public static TestGame getGame() {
        return testGame;
    }

    private void cleanup(){
        errorCallback.free();
    }
}

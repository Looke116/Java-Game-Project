package engine;

import engine.io.MouseInput;
import logic.GameLogic;
import main.Launcher;

import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static utils.Constants.TITLE;

public class GameEngine implements Runnable{

    // Timing Variables
    private static final long NANOSECOND = 1000000000L;
    private static final float FRAMERATE = 60;
    private static final float frametime = 1.0f / FRAMERATE;
    private static int fps;

    private boolean isRunning;
    private Window window;
    private GameLogic gameLogic;
    private MouseInput mouseInput;

    public static int getFps() {
        return fps;
    }

    public static void setFps(int fps) {
        GameEngine.fps = fps;
    }

    private void init() throws Exception {
        if (isRunning) return;
        isRunning = true;

        window = Launcher.getWindow();
        gameLogic = Launcher.getGame();
        gameLogic.init();
        mouseInput = new MouseInput();
        mouseInput.init();
    }

    public void stop() {
        if (!isRunning) return;
        isRunning = false;
        cleanup();
    }

    public void run() {
        try {
            init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int frames = 0;
        long frameCounter = 0;
        long lastTime = System.nanoTime();
        double unprocessedTime = 0;

        while (isRunning) {
            boolean canRender = false;
            long startTime = System.nanoTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime / (double) NANOSECOND;
            frameCounter += passedTime;

            input();

            while (unprocessedTime > frametime) {
                canRender = true;
                unprocessedTime -= frametime;

                if (window.windowShouldClose()) {
                    stop();
                    return;
                }

                if (frameCounter >= NANOSECOND) {
                    setFps(frames);
                    window.setTitle(TITLE + " - " + getFps() + "FPS");
                    frames = 0;
                    frameCounter = 0;
                }
            }

            //NOTE
            if (canRender) {
                update(frametime);
                render();
                frames++;
            }
        }
    }

    private void input() {
        mouseInput.input();
        gameLogic.input();
        window.input();
    }

    private void update(float interval) {
        gameLogic.update(interval, mouseInput);
    }

    private void render() {
        gameLogic.render();
        window.render();
    }

    private void cleanup() {
        gameLogic.cleanup();
        window.cleanup();
        glfwTerminate();
    }
}

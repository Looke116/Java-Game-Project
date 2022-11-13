package engine;

import logic.ILogic;
import main.Launcher;

import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static utils.Consts.TITLE;

public class GameEngine {

    // Timing Variables
    private static final long NANOSECOND = 1000000000L;
    private static final float FRAMERATE = 60;
    private static float frametime = 1.0f / FRAMERATE;
    private static int fps;

    private boolean isRunning;
    private Window window;
    private ILogic gameLogic;

    public void start() throws Exception {
        if (isRunning) return;
        isRunning = true;

        window = Launcher.getWindow();
        gameLogic = Launcher.getGame();
        gameLogic.init();

        run();
    }

    public void stop() {
        if (!isRunning) return;
        isRunning = false;
        cleanup();
    }

    private void run() {
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
                update();
                render();
                frames++;
            }
        }
    }

    private void input() {
        gameLogic.input();
//        window.pollEvents();
    }

    private void update() {
        gameLogic.update();
    }

    private void render() {
        gameLogic.render();
        window.update();
    }

    private void cleanup() {
        gameLogic.cleanup();
        window.cleanup();
        glfwTerminate();
    }

    public static int getFps() {
        return fps;
    }

    public static void setFps(int fps) {
        GameEngine.fps = fps;
    }
}

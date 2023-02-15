package engine;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static utils.Constants.*;

public class Window {

//    private final Matrix4f projectionMatrix;
    private String title;
    private final int width;
    private final int height;
    private final boolean vSync;
    private final long id;

    public Window(String title, int width, int height, boolean vSync) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.vSync = vSync;

        setWindowHints();
        id = glfwCreateWindow(width, height, title, NULL, NULL);
        if (id == NULL) throw new RuntimeException("Could not create GLFW window. Is GLFW initialized?");
        setWindowCallbacks();
        centerWindow();
        glfwMakeContextCurrent(id);
        if (vSync) glfwSwapInterval(1);
        glfwShowWindow(id);
        enableParam();
    }

    private void setWindowHints() {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
    }

    private void setWindowCallbacks() {
        glfwSetKeyCallback(id, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwWindowShouldClose(id);
            }
        });
    }

    private void centerWindow() {
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(id, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
    }

    private void enableParam() {
        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_STENCIL_TEST);
    }

    public void render() {
        glfwSwapBuffers(id);
    }

    public void input() {
        glfwPollEvents();
    }

    public boolean isKeyPressed(int keycode) {
        return glfwGetKey(id, keycode) == GLFW_PRESS;
    }

    public boolean isKeyReleased(int keycode) {
        return glfwGetKey(id, keycode) == GLFW_RELEASE;
    }

    public boolean windowShouldClose() {
        return glfwWindowShouldClose(id);
    }

    public void cleanup() {
        glfwFreeCallbacks(id);
        glfwDestroyWindow(id);
    }

    public void setClearColor(float r, float g, float b, float a) {
        glClearColor(r, g, b, a);
    }

    public void setTitle(String title) {
        glfwSetWindowTitle(id, title);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getId() {
        return id;
    }

    /*public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4f updateProjectionMatrix() {
        float aspectRation = (float) width / height;
        return projectionMatrix.setPerspective(FOV, aspectRation, Z_NEAR, Z_FAR);
    }

    public Matrix4f updateProjectionMatrix(Matrix4f matrix, int width, int height) {
        float aspectRation = (float) width / height;
        return projectionMatrix.setPerspective(FOV, aspectRation, Z_NEAR, Z_FAR);
    }*/
}

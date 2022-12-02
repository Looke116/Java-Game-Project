package engine.io;

import main.Launcher;
import org.joml.Vector2d;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class MouseInput {

    private final Vector2d currentPos;
    private final Vector2d previousPos;
    private final Vector2f displayVector;

    private boolean insideWindow = false;
    private boolean leftButtonPressed = false;
    private boolean rightButtonPressed = false;

    public MouseInput() {
        previousPos = new Vector2d(-1, -1);
        currentPos = new Vector2d(0, 0);
        displayVector = new Vector2f();
    }

    public void init() {
        glfwSetCursorPosCallback(Launcher.getWindow().getId(), (window, xpos, ypos) -> {
            currentPos.x = xpos;
            currentPos.y = ypos;
        });

        glfwSetCursorEnterCallback(Launcher.getWindow().getId(), (window, entered) -> {
            insideWindow = entered;
        });

        glfwSetMouseButtonCallback(Launcher.getWindow().getId(), (window, button, action, mode) -> {
            leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
            rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
        });
    }

    public void input() {
        displayVector.x = 0;
        displayVector.y = 0;
        if (previousPos.x > 0 && previousPos.y > 0 && insideWindow) {
            double x = currentPos.x - previousPos.x;
            double y = currentPos.y - previousPos.y;
            boolean rotateX = x != 0;
            boolean rotateY = y != 0;
            if (rotateX) {
                displayVector.y = (float) x;
            } else if (rotateY) {
                displayVector.x = (float) y;
            }
        }
        previousPos.x = currentPos.x;
        previousPos.y = currentPos.y;
    }

    public Vector2f getDisplayVector() {
        return displayVector;
    }

    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }
}

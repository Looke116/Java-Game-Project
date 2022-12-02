package engine.rendering;

import engine.Window;
import engine.entity.Entity;
import engine.lighting.Light;
import org.lwjgl.opengl.GL;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static utils.Transformation.createTransformationMatrix;
import static utils.Transformation.getViewMatrix;

public class Render {

    private final Window window;
    private ShaderProgram shaderProgram;

    public Render(Window window) {
        GL.createCapabilities();
        this.window = window;
    }

    public void init() {
        try {
            shaderProgram = new ShaderProgram();
            shaderProgram.createShader(GL_VERTEX_SHADER, "vertex.glsl");
            shaderProgram.createShader(GL_FRAGMENT_SHADER, "fragment.glsl");
            shaderProgram.link();
            shaderProgram.createUniform("textureSampler");
            shaderProgram.createUniform("transformationMatrix");
            shaderProgram.createUniform("projectionMatrix");
            shaderProgram.createUniform("viewMatrix");
            shaderProgram.createUniform("lightPosition");
            shaderProgram.createUniform("lightColor");
            shaderProgram.createUniform("shineDamper");
            shaderProgram.createUniform("reflectivity");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void render(List<Entity> entities, Camera camera, Light light) {
        clear();
        shaderProgram.bind();

        for (Entity entity : entities) {
            shaderProgram.setUniform("textureSampler", 0);
            shaderProgram.setUniform("transformationMatrix", createTransformationMatrix(entity));
            shaderProgram.setUniform("projectionMatrix", window.updateProjectionMatrix());
            shaderProgram.setUniform("viewMatrix", getViewMatrix(camera));
            shaderProgram.setUniform("lightPosition", light.getPosition());
            shaderProgram.setUniform("lightColor", light.getColor());
            shaderProgram.setUniform("shineDamper", entity.getShineDamper());
            shaderProgram.setUniform("reflectivity", entity.getReflectivity());

            glBindVertexArray(entity.getMesh().getId());
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);
            glEnableVertexAttribArray(2);
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, entity.getTexture().getId());

            glDrawElements(GL_TRIANGLES, entity.getMesh().getVertexCount(), GL_UNSIGNED_INT, 0);

            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(1);
            glDisableVertexAttribArray(2);
            glBindVertexArray(0);
        }
        shaderProgram.unbind();
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void cleanup() {
        shaderProgram.cleanup();
    }
}

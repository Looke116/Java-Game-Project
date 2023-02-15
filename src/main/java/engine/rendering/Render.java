package engine.rendering;

import engine.entity.Entity;
import engine.entity.Model;
import engine.lighting.Light;
import engine.terrain.Terrain;
import engine.terrain.TerrainModel;
import main.Scene;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static utils.Constants.*;
import static utils.Transformation.createTransformationMatrix;
import static utils.Transformation.getViewMatrix;

public class Render {

    private Matrix4f projectionMatrix;
    private ShaderProgram entityShaderProgram;
    private ShaderProgram terrainShaderProgram;

    public Render() {
        GL.createCapabilities();
        enableCulling();
        projectionMatrix = createProjectionMatrix();
        try {
            entityShaderProgram = new ShaderProgram();
            terrainShaderProgram = new ShaderProgram();
            initEntityShader();
            initTerrainShader();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void enableCulling() {
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
    }

    private static void disableCulling() {
        glDisable(GL_CULL_FACE);
    }

    public void clear(Vector3f color) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(color.x, color.y, color.z, 1);
    }

    private Matrix4f createProjectionMatrix() {
        float aspectRation = (float) WIDTH / HEIGHT;
        projectionMatrix = new Matrix4f();
        return projectionMatrix.setPerspective(FOV, aspectRation, Z_NEAR, Z_FAR);
    }

    private void bindModelCommon(Model model, ShaderProgram shaderProgram) {
        glBindVertexArray(model.getMesh().getId());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

//        glActiveTexture(GL_TEXTURE0);
//        glBindTexture(GL_TEXTURE_2D, model.getTexture().getId());

        shaderProgram.setUniform("shineDamper", model.getShineDamper());
        shaderProgram.setUniform("reflectivity", model.getReflectivity());
    }

    private void bindEntityModel(Model model) {
        bindModelCommon(model, entityShaderProgram);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, model.getTexture().getId());

        if (model.getTransparency()) {
            disableCulling();
        }
        entityShaderProgram.setUniform("useFakeLighting", model.isUseFakeLighting());
    }

    private void bindTerrainModel(TerrainModel model) {
        bindModelCommon(model, terrainShaderProgram);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, model.getTexture().getId());

        glActiveTexture(GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, model.getrTexture().getId());

        glActiveTexture(GL_TEXTURE2);
        glBindTexture(GL_TEXTURE_2D, model.getgTexture().getId());

        glActiveTexture(GL_TEXTURE3);
        glBindTexture(GL_TEXTURE_2D, model.getbTexture().getId());

        glActiveTexture(GL_TEXTURE4);
        glBindTexture(GL_TEXTURE_2D, model.getBlendMap().getId());
    }

    private void unbindModel() {
        enableCulling();
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
    }

    private void initShaderCommon(ShaderProgram shaderProgram) throws Exception {
        shaderProgram.link();
//        shaderProgram.createUniform("textureSampler");
        shaderProgram.createUniform("transformationMatrix");
        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.createUniform("viewMatrix");
        shaderProgram.createUniform("lightPosition");
        shaderProgram.createUniform("lightColor");
        shaderProgram.createUniform("shineDamper");
        shaderProgram.createUniform("reflectivity");
        shaderProgram.createUniform("skyColor");
        shaderProgram.createUniform("fogDensity");
        shaderProgram.createUniform("fogGradient");
    }

    private void initEntityShader() throws Exception {
        entityShaderProgram.createShader(GL_VERTEX_SHADER, "vertex.glsl");
        entityShaderProgram.createShader(GL_FRAGMENT_SHADER, "fragment.glsl");

        initShaderCommon(entityShaderProgram);
        entityShaderProgram.createUniform("textureSampler");
        entityShaderProgram.createUniform("useFakeLighting");
    }

    private void initTerrainShader() throws Exception {
        terrainShaderProgram.createShader(GL_VERTEX_SHADER, "terrainVertex.glsl");
        terrainShaderProgram.createShader(GL_FRAGMENT_SHADER, "terrainFragment.glsl");

        initShaderCommon(terrainShaderProgram);
        terrainShaderProgram.createUniform("backgroundTexture");
        terrainShaderProgram.createUniform("rTexture");
        terrainShaderProgram.createUniform("gTexture");
        terrainShaderProgram.createUniform("bTexture");
        terrainShaderProgram.createUniform("blendMap");
    }

    private void setupProgramCommon(Scene scene, ShaderProgram shaderProgram) {
        Camera camera = scene.getCamera();
        Light light = scene.getLight();

        shaderProgram.bind();
//        shaderProgram.setUniform("textureSampler", 0);
        shaderProgram.setUniform("projectionMatrix", projectionMatrix);
        shaderProgram.setUniform("viewMatrix", getViewMatrix(camera));

        shaderProgram.setUniform("lightPosition", light.getPosition());
        shaderProgram.setUniform("lightColor", light.getColor());
        shaderProgram.setUniform("skyColor", scene.getSkyColor());
        shaderProgram.setUniform("fogDensity", scene.getFogDensity());
        shaderProgram.setUniform("fogGradient", scene.getFogGradient());
    }

    private void setupEntityProgram(Scene scene) {
        setupProgramCommon(scene, entityShaderProgram);
        entityShaderProgram.setUniform("textureSampler", 0);
    }

    private void setupTerrainProgram(Scene scene) {
        setupProgramCommon(scene, terrainShaderProgram);
        terrainShaderProgram.setUniform("backgroundTexture", 0);
        terrainShaderProgram.setUniform("rTexture", 1);
        terrainShaderProgram.setUniform("gTexture", 2);
        terrainShaderProgram.setUniform("bTexture", 3);
        terrainShaderProgram.setUniform("blendMap", 4);
    }

    public void render(Scene scene) {
        clear(scene.getSkyColor());
        renderEntities(scene.getEntities(), scene);
        renderTerrain(scene.getTerrainList(), scene);
    }

    private void renderEntities(Map<Model, List<Entity>> entities, Scene scene) {
        setupEntityProgram(scene);
        for (Model model : entities.keySet()) {
            bindEntityModel(model);
            for (Entity entity : entities.get(model)) {
                entityShaderProgram.setUniform("transformationMatrix", createTransformationMatrix(entity));
                glDrawElements(GL_TRIANGLES, entity.getMesh().getVertexCount(), GL_UNSIGNED_INT, 0);
            }
            unbindModel();
        }
        entityShaderProgram.unbind();
    }

    private void renderTerrain(List<Terrain> terrains, Scene scene) {
        setupTerrainProgram(scene);
        for (Terrain terrain : terrains) {
            bindTerrainModel(terrain.getModel());
            terrainShaderProgram.setUniform("transformationMatrix", createTransformationMatrix(terrain));
            glDrawElements(GL_TRIANGLES, terrain.getMesh().getVertexCount(), GL_UNSIGNED_INT, 0);
            unbindModel();
        }
        terrainShaderProgram.unbind();
    }

    public void cleanup() {
        entityShaderProgram.cleanup();
        terrainShaderProgram.cleanup();
    }
}

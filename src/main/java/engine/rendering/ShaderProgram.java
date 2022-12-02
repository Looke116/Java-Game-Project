package engine.rendering;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;
import static utils.Consts.RESOURCES_PATH;

public class ShaderProgram {

    private final int id;
    private final Map<String, Integer> uniforms;
    private final List<Shader> shaders;

    public ShaderProgram() throws Exception {
        id = glCreateProgram();
        if (id == 0) throw new Exception("Could not create Shader Program");

        uniforms = new HashMap<>();
        shaders = new ArrayList<>();
    }

    public void createUniform(String uniformName) throws Exception {
        int uniformLocation = glGetUniformLocation(id, uniformName);
        if (uniformLocation < 0) throw new Exception("Could not find uniform " + uniformName);
        uniforms.put(uniformName, uniformLocation);
    }

    public void setUniform(String uniformName, boolean value) {
        float res = 0;
        if (value) res = 1;
        glUniform1f(uniforms.get(uniformName), res);
    }

    public void setUniform(String uniformName, int value) {
        glUniform1i(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, float value) {
        glUniform1f(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, Vector3f value) {
        glUniform3f(uniforms.get(uniformName), value.x, value.y, value.z);
    }

    public void setUniform(String uniformName, Vector4f value) {
        glUniform4f(uniforms.get(uniformName), value.x, value.y, value.z, value.w);
    }

    public void setUniform(String uniformName, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            glUniformMatrix4fv(uniforms.get(uniformName), false, value.get(stack.mallocFloat(16)));
        }
    }

    public void attachShader(Shader shader) {
        glAttachShader(id, shader.getID());
        shaders.add(shader);
    }

    public void createShader(int type, String fileName) throws Exception {
        Shader shader = new Shader(type, RESOURCES_PATH + "shaders/" + fileName);
        attachShader(shader);
    }

    public void link() throws Exception {
        glLinkProgram(id);
        if (glGetProgrami(id, GL_LINK_STATUS) == 0)
            throw new Exception("Error linking Shader Program: " + glGetProgramInfoLog(id));
        validate();
    }


    public void validate() {
        // TODO remember to disable this
        // This method is used mainly for debugging purposes,
        // and it should be removed when your game reaches production stage.
        glValidateProgram(id);
        if (glGetProgrami(id, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Error validating Shader code: " + glGetProgramInfoLog(id));
        }
    }

    public void bind() {
        glUseProgram(id);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanup() {
        for (Shader shader : shaders) {
            shader.cleanup(id);
        }
        unbind();
        glDeleteProgram(id);
    }
}

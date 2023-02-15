package engine.rendering;

import engine.exceptions.ShaderCreationException;

import static org.lwjgl.opengl.GL20.*;
import static utils.Utils.loadFromFile;

public class Shader {
    private final int id;
    private final int type;

    Shader(int type, String path) throws Exception {
        id = glCreateShader(type);
        if (id == 0) throw new ShaderCreationException("Error creating shader! Type: " + type);
        this.type = type;

        CharSequence source = loadFromFile(path, Shader.class);
        source(source);
        compile();
        checkStatus();
    }

    public void source(CharSequence source) {
        glShaderSource(id, source);
    }

    public void compile() {
        glCompileShader(id);
    }

    public void checkStatus() throws Exception {
        if (glGetShaderi(id, GL_COMPILE_STATUS) == 0)
            throw new Exception("Error compiling shader code! Type: " + type +
                    "\n[Info] - " + glGetShaderInfoLog(id));
    }

    public void detachShader() {
        if (id != 0) {
            glDetachShader(id, type);
        }
    }

    public int getID() {
        return id;
    }

    public int getType() {
        return type;
    }

    public void cleanup(int programID) {
        glDetachShader(programID, id);
        glDeleteShader(id);
    }
}
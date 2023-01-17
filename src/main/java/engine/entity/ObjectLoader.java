package engine.entity;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;
import static utils.Constants.*;
import static utils.Constants.TERRAIN_VERTEX_COUNT;
import static utils.Utils.*;

public class ObjectLoader {

    private final static List<Integer> vaos = new ArrayList<>();
    private final static List<Integer> vbos = new ArrayList<>();
    private final static List<Integer> textures = new ArrayList<>();

    private static void processVertex(Vector3i face, List<Vector2f> textureCoordsList,
                                      List<Vector3f> normalsList, List<Integer> indicesList,
                                      float[] textCoordsArr, float[] normalsArr) {
        int pos = face.x;
        int textCoords = face.y;
        int normal = face.z;

        indicesList.add(pos);

        if (textCoords >= 0) {
            Vector2f textureCoordsVec = textureCoordsList.get(textCoords);
            textCoordsArr[pos * 2] = textureCoordsVec.x;
            textCoordsArr[pos * 2 + 1] = 1 - textureCoordsVec.y;
        }

        if (normal >= 0) {
            Vector3f normalsVec = normalsList.get(normal);
            normalsArr[pos * 3] = normalsVec.x;
            normalsArr[pos * 3 + 1] = normalsVec.y;
            normalsArr[pos * 3 + 2] = normalsVec.z;
        }
    }

    private static void processFaces(String token, List<Vector3i> faces) {
        String[] lineToken = token.split("/");
        int pos = Integer.parseInt(lineToken[0]) - 1;
        int coordinate = -1, normal = -1;
        if (lineToken.length > 1) {
            coordinate = lineToken[1].length() > 0 ? Integer.parseInt(lineToken[1]) - 1 : -1;
            if (lineToken.length > 2) {
                normal = Integer.parseInt(lineToken[2]) - 1;
            }
        }
        Vector3i face = new Vector3i(pos, coordinate, normal);
        faces.add(face);
    }

    public static Mesh generateTerrain(){
        int count = TERRAIN_VERTEX_COUNT * TERRAIN_VERTEX_COUNT;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count*2];
        int[] indices = new int[6*(TERRAIN_VERTEX_COUNT-1)*(TERRAIN_VERTEX_COUNT-1)];
        int vertexPointer = 0;
        for(int i=0;i<TERRAIN_VERTEX_COUNT;i++){
            for(int j=0;j<TERRAIN_VERTEX_COUNT;j++){
                vertices[vertexPointer*3] = (float)j/((float)TERRAIN_VERTEX_COUNT - 1) * TERRAIN_SIZE;
                vertices[vertexPointer*3+1] = 0;
                vertices[vertexPointer*3+2] = (float)i/((float)TERRAIN_VERTEX_COUNT - 1) * TERRAIN_SIZE;
                normals[vertexPointer*3] = 0;
                normals[vertexPointer*3+1] = 1;
                normals[vertexPointer*3+2] = 0;
                textureCoords[vertexPointer*2] = (float)j/((float)TERRAIN_VERTEX_COUNT - 1);
                textureCoords[vertexPointer*2+1] = (float)i/((float)TERRAIN_VERTEX_COUNT - 1);
                vertexPointer++;
            }
        }
        int pointer = 0;
        for(int gz=0;gz<TERRAIN_VERTEX_COUNT-1;gz++){
            for(int gx=0;gx<TERRAIN_VERTEX_COUNT-1;gx++){
                int topLeft = (gz*TERRAIN_VERTEX_COUNT)+gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz+1)*TERRAIN_VERTEX_COUNT)+gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        return loadModel(vertices, textureCoords, normals, indices);
    }

    public static Mesh loadModel(float[] vertices, float[] textureCoords, float[] normals, int[] indices) {
        int id = createVAO();
        storeIndicesBuffer(indices);
        storeData(0, 3, vertices);
        storeData(1, 2, textureCoords);
        storeData(2, 3, normals);
        unbind();
        return new Mesh(id, indices.length);
    }

    public static Texture loadTexture(String fileName, boolean repeat) throws Exception {
        String path = RESOURCES_PATH + "engine/entity/textures/" + fileName;
//        String stream = Texture.class.getResource("textures/" + fileName).toString();
        int width, height;
        ByteBuffer buffer;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer c = stack.mallocInt(1);

            buffer = STBImage.stbi_load(path, w, h, c, 4);
            if (buffer == null) throw new FileNotFoundException("Image File " + path + " could not be loaded!" +
                    "\n[Info] - " + STBImage.stbi_failure_reason());

            width = w.get();
            height = h.get();
        }

        int id = glGenTextures();
        textures.add(id);
        glBindTexture(GL_TEXTURE_2D, id);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        if(repeat) {    // TODO this not work  workaround in terrain shader
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        }

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        glGenerateMipmap(GL_TEXTURE_2D);
        STBImage.stbi_image_free(buffer);
        return new Texture(id);
    }

    public static Mesh loadOBJModel(String fileName) {
        // For some reason this is the only instance where it won't find the file any other way
        // I don't understand why can't all other file calls work like this
        String path = /*RESOURCES_PATH +*/ "models/" + fileName;
        List<String> lines = readAllLines(path, Mesh.class);

        List<Vector3f> verticesList = new ArrayList<>();
        List<Vector3f> normalsList = new ArrayList<>();
        List<Vector2f> texturesList = new ArrayList<>();
        List<Vector3i> facesList = new ArrayList<>();

        for (String line : lines) {
            String[] tokens = line.split("\\s+");
            switch (tokens[0]) {
                case "v":
                    Vector3f vertex = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3])
                    );
                    verticesList.add(vertex);
                    break;
                case "vt":
                    Vector2f texture = new Vector2f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2])
                    );
                    texturesList.add(texture);
                    break;
                case "vn":
                    Vector3f normal = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3])
                    );
                    normalsList.add(normal);
                    break;
                case "f":
                    processFaces(tokens[1], facesList);
                    processFaces(tokens[2], facesList);
                    processFaces(tokens[3], facesList);
                    break;
            }
        }

        float[] verticesArr = new float[verticesList.size() * 3];
        int i = 0;
        for (Vector3f vertex : verticesList) {
            verticesArr[i * 3] = vertex.x;
            verticesArr[i * 3 + 1] = vertex.y;
            verticesArr[i * 3 + 2] = vertex.z;
            i++;
        }

        List<Integer> indicesList = new ArrayList<>();
        float[] textureCoordsArr = new float[verticesList.size() * 2];
        float[] normalArr = new float[verticesList.size() * 3];

        for (Vector3i face : facesList) {
            processVertex(face, texturesList, normalsList, indicesList, textureCoordsArr, normalArr);
        }

        int[] indicesArr = indicesList.stream().mapToInt((Integer v) -> v).toArray();

        return loadModel(verticesArr, textureCoordsArr, normalArr, indicesArr);
    }

    private static int createVAO() {
        int id = glGenVertexArrays();
        vaos.add(id);
        glBindVertexArray(id);
        return id;
    }

    private static void storeData(int attribNo, int coordinateSize, float[] data) {
        int vbo = glGenBuffers();
        vbos.add(vbo);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        FloatBuffer buffer = toFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(attribNo, coordinateSize, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private static void storeIndicesBuffer(int[] indices) {
        int vbo = glGenBuffers();
        vbos.add(vbo);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo);
        IntBuffer buffer = toIntBuffer(indices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    }

    private static void unbind() {
        glBindVertexArray(0);
    }

    public void cleanup() {
        for (int vao : vaos) {
            glDeleteVertexArrays(vao);
        }
        for (int vbo : vbos) {
            glDeleteBuffers(vbo);
        }
        for (int texture : textures) {
            glDeleteTextures(texture);
        }
    }
}

package engine.entity;

public class Mesh {

    private final int id;
    private final int vertexCount;

    public Mesh(){
        id = -1;
        vertexCount = -1;
    }

    public Mesh(int id, int vertexCount) {
        this.id = id;
        this.vertexCount = vertexCount;
    }

    public int getId() {
        return id;
    }

    public int getVertexCount() {
        return vertexCount;
    }
}

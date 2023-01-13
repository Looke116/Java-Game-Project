package engine.entity;

public class Texture {

    private final int id;
    private boolean hasTransparency;

    public Texture() {
        id = -1;
        hasTransparency = false;
    }

    public Texture(int id) {
        this.id = id;
        hasTransparency = false;
    }

    public Texture(int id, boolean hasTransparency) {
        this.id = id;
        this.hasTransparency = hasTransparency;
    }

    public int getId() {
        return id;
    }

    public boolean getTransparency() {
        return hasTransparency;
    }

    public void setTransparency(boolean transparency) {
        this.hasTransparency = transparency;
    }
}

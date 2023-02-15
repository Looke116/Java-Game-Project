package logic;

import engine.io.MouseInput;

public interface GameLogic {

    void init() throws Exception;

    void input();

    void update(float interval, MouseInput mouseInput);

    void render();

    void cleanup();
}

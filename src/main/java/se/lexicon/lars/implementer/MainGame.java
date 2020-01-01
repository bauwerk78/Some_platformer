package se.lexicon.lars.implementer;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import se.lexicon.lars.model.Level;
import se.lexicon.lars.model.PlayerCharacter;

import static se.lexicon.lars.graphics.Renderer.*;
import static se.lexicon.lars.model.Level.*;

public class MainGame {

    private Level level;
    private PlayerCharacter player;
    //private boolean playerCollided;


    public MainGame() {
        init();
    }

    public void init() {
        level = new Level();
        player = new PlayerCharacter();
    }

    public boolean getCollision(double tileX, double tileY) {
        return level.getCollideAble(tileX, tileY);
    }

    private void renderGame(GraphicsContext gc, Scene scene) {
        level.renderLevel(gc);
        player.render(gc, scene, this);
        //System.out.println(player.getTileX() + " : " + player.getTileY());
    }

    public void mainLoop(GraphicsContext gc, Scene scene) {
        renderGame(gc, scene);
    }
}

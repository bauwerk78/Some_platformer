package se.lexicon.lars.implementer;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import se.lexicon.lars.model.Camera;
import se.lexicon.lars.model.Level;
import se.lexicon.lars.model.PlayerCharacter;

import static se.lexicon.lars.graphics.Renderer.*;
import static se.lexicon.lars.model.Level.*;

public class MainGame {

    private Level level;
    private PlayerCharacter player;
    private Camera camera;
    //private boolean playerCollided;


    public MainGame(Group group, Scene scene, GraphicsContext gc) {
        init(group, scene, gc);
    }

    public void init(Group group, Scene scene, GraphicsContext gc) {
        level = new Level();
        player = new PlayerCharacter();
        camera = new Camera(group, scene, gc );
    }

    public boolean getCollision(double tileX, double tileY) {
        return level.getCollideAble(tileX, tileY);
    }

    private void renderGame(GraphicsContext gc, Scene scene, Group group) {
        level.renderLevel(gc);
        player.render(gc, scene, this);
        camera.update(this, player, gc, group, scene);



        //System.out.println(player.getTileX() + " : " + player.getTileY());
    }

    public void mainLoop(GraphicsContext gc, Scene scene, Group group) {
        renderGame(gc, scene, group);
    }
}

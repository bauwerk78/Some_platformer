package se.lexicon.lars.implementer;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import se.lexicon.lars.model.Camera;
import se.lexicon.lars.model.GameObject;
import se.lexicon.lars.model.Level;
import se.lexicon.lars.model.PlayerCharacter;

import static se.lexicon.lars.graphics.Renderer.*;
import static se.lexicon.lars.model.Level.*;

public class MainGame {

    private Level level;
    private PlayerCharacter player;
    private Camera camera;
    private Canvas canvas;
    private GraphicsContext gc;
    private boolean newLevel = true;

    private double collidedPosX;
    private double collidedPosY;


    public MainGame(Group group, Scene scene) {
        init(group, scene);
    }

    public void init(Group group, Scene scene) {
        level = new Level();
        player = new PlayerCharacter();
        camera = new Camera(scene);
        initGraphics(group);
    }

    public boolean getCollision(double tileX, double tileY) {
        return level.getCollideAble(tileX, tileY);
    }

    public boolean getCollisions(double tileX, double tileY) {
        return level.getCollideAbles(tileX, tileY);
    }


    private void renderGame(Scene scene) {
        gc.clearRect(0, 0, windowWidth, windowHeight);
        level.renderLevel(gc);
        player.render(gc, scene, this);
        camera.update(player);
        //System.out.println(player.getTileX() + " : " + player.getTileY());
    }

    public void mainLoop(Scene scene) {
        renderGame(scene);
    }

    public void initGraphics(Group group) {
        canvas = new Canvas(levelW * TILESIZE, levelH * TILESIZE);
        group.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
    }

    public double getCollidedPosX() {
        return collidedPosX;
    }

    public void setCollidedPosX(double collidedPosX) {
        this.collidedPosX = collidedPosX;
    }

    public double getCollidedPosY() {
        return collidedPosY;
    }

    public void setCollidedPosY(double collidedPosY) {
        this.collidedPosY = collidedPosY;
    }
}//End of class



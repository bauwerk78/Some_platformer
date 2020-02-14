package se.lexicon.lars.implementer;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import se.lexicon.lars.graphics.BackgroundAndForeground;
import se.lexicon.lars.graphics.ScrollingLayer;
import se.lexicon.lars.model.Camera;
import se.lexicon.lars.model.Level;
import se.lexicon.lars.model.PlayerCharacter;
import se.lexicon.lars.model.enemies.Santa;
import se.lexicon.lars.tools.Randomize;

import java.util.ArrayList;
import java.util.List;

import static se.lexicon.lars.model.Level.*;

public class MainGame implements Randomize {

    private List<Santa> santas = new ArrayList<>();

    private Level level;
    private PlayerCharacter player;
    private Camera camera;
    private Canvas canvas;
    private GraphicsContext gc;
    private BackgroundAndForeground backgroundAndForeground;
    private ScrollingLayer backgroundLayer1;
    private ScrollingLayer backgroundLayer2;
    //private boolean newLevel = true;

    private double collidedPosX;
    private double collidedPosY;


    public MainGame(Group group, Scene scene) {
        init(group, scene);
    }

    public void init(Group group, Scene scene) {
        level = new Level();
        player = new PlayerCharacter();
        camera = new Camera(scene);
        for (int i = 1; i <= 10; i++) {
            santas.add(new Santa(Randomize.randPositionX(2, 10), 2, Randomize.randBoolean()));
        }
        //santa = new Santa(320, 256);
        backgroundLayer1 = new ScrollingLayer("file:Images/Backgrounds/skill-desc_0002_far-buildings.png", 5);
        backgroundLayer2 = new ScrollingLayer("file:Images/Backgrounds/skill-desc_0001_buildings.png", 10);
        backgroundAndForeground = new BackgroundAndForeground("file:Images/Backgrounds/skill-desc_0003_bg.png", "file:Images/Backgrounds/skill-desc_0000_foreground.png");
        initGraphics(group);
    }

    public boolean getCollision(double tileX, double tileY) {
        return level.getCollideAble(tileX, tileY);
    }

    public boolean getCollisions(double tileX, double tileY) {
        return level.getCollideAbles(tileX, tileY);
    }

    public boolean collisionDetection(Rectangle2D object1, Rectangle2D object2) {
        if (object1.intersects(object2)) {
            //System.out.println("collision detected.");
            object2 = null;
            object1 = null;
            return true;
        }
        return false;
    }

    private void updateScrollingBackground() {
/*        if (player.isGoingRight() && !player.isStandingStill() && player.getOffX() > 0) {
            if (scrollingBackground.getImageView1PosX() < scrollingBackground.getImageView2PosX() && player.getPositionX() > scrollingBackground.getImageView1PosX() + scrollingBackground.getImageViewWidth(1) + windowWidth / 2d) {
                scrollingBackground.setImageView1PosX(scrollingBackground.getImageView1PosX() + scrollingBackground.getImageViewWidth(1) * 2);
            } else if (scrollingBackground.getImageView2PosX() < scrollingBackground.getImageView1PosX() && player.getPositionX() > scrollingBackground.getImageView2PosX() + scrollingBackground.getImageViewWidth(2) + windowWidth / 2d) {
                scrollingBackground.setImageView2PosX(scrollingBackground.getImageView2PosX() + scrollingBackground.getImageViewWidth(2) * 2);
            }

            scrollingBackground.setImageView1PosX(scrollingBackground.getImageView1PosX() - scrollingBackground.getSecondLayerScrollSpeed() * elapsedTime);
            scrollingBackground.setImageView2PosX(scrollingBackground.getImageView2PosX() - scrollingBackground.getSecondLayerScrollSpeed() * elapsedTime);
            scrollingBackground.update();
        }
        if (!player.isGoingRight() && !player.isStandingStill() && player.getOffX() < 0) {
            if (scrollingBackground.getImageView1PosX() > scrollingBackground.getImageView2PosX() && player.getPositionX() < scrollingBackground.getImageView1PosX() - scrollingBackground.getImageViewWidth(1) - windowWidth / 2d) {
                scrollingBackground.setImageView1PosX(scrollingBackground.getImageView1PosX() - scrollingBackground.getImageViewWidth(1) * 2);
            } else if (scrollingBackground.getImageView2PosX() > scrollingBackground.getImageView1PosX() && player.getPositionX() < scrollingBackground.getImageView2PosX() - scrollingBackground.getImageViewWidth(2) - windowWidth / 2d) {
                scrollingBackground.setImageView2PosX(scrollingBackground.getImageView2PosX() - scrollingBackground.getImageViewWidth(2) * 2);
            }
            scrollingBackground.setImageView1PosX(scrollingBackground.getImageView1PosX() + scrollingBackground.getSecondLayerScrollSpeed() * elapsedTime);
            scrollingBackground.setImageView2PosX(scrollingBackground.getImageView2PosX() + scrollingBackground.getSecondLayerScrollSpeed() * elapsedTime);
            scrollingBackground.update();
        }*/

        backgroundLayer1.update(player.isGoingRight(), player.isStandingStill(), player.getOffX(), player.getPositionX());
        backgroundLayer2.update(player.isGoingRight(), player.isStandingStill(), player.getOffX(), player.getPositionX());
        //backgroundLayer2.update(player.isGoingRight(), player.isStandingStill(), player.getPositionX());
    }

    private void renderGame(Scene scene) {
        System.out.println(player.getPositionX());
        gc.clearRect(0, 0, levelW * TILESIZE, levelH * TILESIZE);
        level.renderLevel(gc);
        player.render(gc, scene, this);
        for (Santa sant : santas) {
            sant.render(gc, scene, this);
        }
        //santa.render(gc, scene, this);
        camera.update(player);
        updateScrollingBackground();
        //System.out.println(player.getTileX() + " : " + player.getTileY());
    }

    public void mainLoop(Scene scene) {
        renderGame(scene);
    }

    public void initGraphics(Group group) {
        canvas = new Canvas(levelW * TILESIZE, levelH * TILESIZE);
        //group.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        group.getChildren().addAll(backgroundAndForeground.getBackgroundRegion(), backgroundLayer1.getLayerPane(), backgroundLayer2.getLayerPane(), backgroundAndForeground.getForegroundPane(), canvas);

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



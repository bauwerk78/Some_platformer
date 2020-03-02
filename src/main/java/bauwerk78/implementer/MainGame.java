package bauwerk78.implementer;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import bauwerk78.graphics.BackgroundAndForeground;
import bauwerk78.graphics.ScrollingLayer;
import bauwerk78.model.Camera;
import bauwerk78.model.Level;
import bauwerk78.model.PlayerCharacter;
import bauwerk78.model.enemies.Santa;
import bauwerk78.tools.Randomize;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static bauwerk78.model.Level.*;

public class MainGame extends Application implements Randomize {

    public static final int windowWidth = 1024;
    public static final int windowHeight = 768;
    public static Long startNanoTime = System.nanoTime();
    public static double elapsedTime;

    public static void nanoTimer(long currentNanoTime) {
        elapsedTime = (currentNanoTime - startNanoTime.doubleValue()) / 1_000_000_000d;
        startNanoTime = currentNanoTime;
    }

    private List<Santa> santas = new ArrayList<>();

    private Group root = new Group();
    private Scene mainScene = new Scene(root, windowWidth, windowHeight);
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

    public MainGame() {
        init();
    }


    public void init() {
        level = new Level();
        player = new PlayerCharacter(level.getPlayerStartingX(), level.getPlayerStartingY());
        camera = new Camera(mainScene);
        for (int i = 1; i <= 10; i++) {
            santas.add(new Santa(Randomize.randPositionX(2, 10), 2, Randomize.randBoolean()));
        }
        backgroundLayer1 = new ScrollingLayer("file:Images/Backgrounds/far_buildings.png", 10);
        backgroundLayer2 = new ScrollingLayer("file:Images/Backgrounds/closer_buildings.png", 20);
        backgroundAndForeground = new BackgroundAndForeground("file:Images/Backgrounds/skill-desc_0003_bg.png", "file:Images/Backgrounds/skill-desc_0000_foreground.png");
        initGraphics();
    }

    public void initGraphics() {
        canvas = new Canvas(levelW * TILESIZE, levelH * TILESIZE);
        //group.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().addAll(backgroundAndForeground.getBackgroundRegion(), backgroundLayer1.getLayerPane(), backgroundLayer2.getLayerPane(),
                backgroundAndForeground.getForegroundPane(), level.getGroup(), canvas);

    }

    @Override
    public void start(Stage stage) {

        stage.setTitle("Some Platformer");
        stage.setScene(mainScene);
        stage.setResizable(false);
        stage.sizeToScene();

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                nanoTimer(currentNanoTime);
                mainLoop();
            }
        }.start();

        stage.show();


    }

    public boolean getCollision(double tileX, double tileY) {
        return level.getCollideAbles(tileX, tileY);
    }

    public boolean getCollisions(double tileX, double tileY) {
        return level.getCollideAbles(tileX, tileY);
    }

    public boolean collisionDetection(Rectangle2D object1, Rectangle2D object2) {
        return object1.intersects(object2);
    }

    private void updateScrollingBackground() {
        backgroundLayer1.update(player.isGoingRight(), player.isStandingStill(), player.getOffX(), player.getPositionX());
        backgroundLayer2.update(player.isGoingRight(), player.isStandingStill(), player.getOffX(), player.getPositionX());
    }

    private void renderGame() {
        //System.out.println(player.getPositionX());
        gc.clearRect(player.getPositionX() - windowWidth, 0, windowWidth * 2, windowHeight);
        player.render(gc, mainScene, this);
        for (Santa sant : santas) {
            sant.render(gc, mainScene, this);
        }
        camera.update(player);
        updateScrollingBackground();
    }

    public void mainLoop() {
        renderGame();
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



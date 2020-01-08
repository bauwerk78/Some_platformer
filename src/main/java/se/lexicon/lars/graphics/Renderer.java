package se.lexicon.lars.graphics;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import se.lexicon.lars.implementer.MainGame;
import se.lexicon.lars.model.Level;
import se.lexicon.lars.model.PlayerCharacter;

public class Renderer extends Application {

    public static final int windowWidth = 1024;
    public static final int windowHeight = 768;
    public static Long startNanoTime = System.nanoTime();
    public static double elapsedTime;

    public static void nanoTimer(long currentNanoTime) {
        elapsedTime = (currentNanoTime - Renderer.startNanoTime.doubleValue()) / 1_000_000_000d;
        Renderer.startNanoTime = currentNanoTime;
    }

    @Override
    public void start(Stage stage) {

        stage.setTitle("Some Platformer");
        Group root = new Group();
        Scene mainScene = new Scene(root, windowWidth, windowHeight);


        stage.setScene(mainScene);
        stage.setResizable(false);
        stage.sizeToScene();

        /*Region nodeRegion = new Region();

        SubScene cameraScene = new SubScene(nodeRegion, 200, 200);*/


/*        ParallelCamera camera = new ParallelCamera();
        cameraScene.setCamera(camera);
        camera.setNearClip(0.1);
        camera.setFarClip(1000);
        camera.setScaleZ(0.5);
        //camera.resize(50, 50);
        root.getChildren().add(cameraScene);*/


        /*PerspectiveCamera camera = new PerspectiveCamera(true);
        mainScene.setCamera(camera);
        camera.translateZProperty().set(-800);
        camera.setNearClip(0.1);
        camera.setFarClip(1000);
        //camera.setLayoutX(50);
        //camera.setLayoutY(50);
        //camera.setScaleX(1);
        //camera.setScaleY(1);
        //camera.setScaleZ(200);
        //camera.relocate(-100, -50);
        //camera.resize(50, 50);
        camera.setFieldOfView(30);*/

        Canvas canvas = new Canvas(windowWidth * 2, windowHeight * 2);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();


        MainGame mainGame = new MainGame(root, mainScene, gc);

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                nanoTimer(currentNanoTime);
                gc.clearRect(0, 0, windowWidth * 2, windowHeight * 2);
                //gc.clearRect(0, 0, windowWidth, windowHeight);
                mainGame.mainLoop(gc, mainScene, root);


            }
        }.start();

        stage.show();


    }
}





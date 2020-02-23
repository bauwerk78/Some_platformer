package bauwerk78.graphics;

import bauwerk78.implementer.MainGame;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

        MainGame mainGame = new MainGame(root, mainScene);

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                nanoTimer(currentNanoTime);
                mainGame.mainLoop(mainScene);
            }
        }.start();

        stage.show();


    }
}





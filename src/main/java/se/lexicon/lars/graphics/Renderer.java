package se.lexicon.lars.graphics;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Some Platformer");
        Group root = new Group();
        Scene mainScene = new Scene(root);

        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        Canvas canvas = new Canvas(windowWidth, windowHeight);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                nanoTimer(currentNanoTime);


            }
        }.start();

        primaryStage.show();


    }
}





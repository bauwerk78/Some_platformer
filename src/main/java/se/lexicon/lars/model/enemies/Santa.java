package se.lexicon.lars.model.enemies;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import se.lexicon.lars.implementer.MainGame;
import se.lexicon.lars.model.GameObject;
import se.lexicon.lars.tools.Delayer;

public class Santa extends GameObject {

    private double positionX;
    private double positionY;

    private Delayer delayer;

    private Image image;
    private int frameHeight = 72;
    private int frameWidth = 64;
    private int imageWidth = 704;
    private int currentFrame;
    private int[] imageSrcPos = new int[imageWidth/frameWidth];

    public Santa(double posX, double posY) {
        this.positionX = posX;
        this.positionY = posY;
        init();


    }

    private void setFrameSrcPositions() {
         for (int i = 0; i < imageSrcPos.length; i++) {
            imageSrcPos[i] = i * frameWidth;
        }
    }

    @Override
    protected void init() {
        image = new Image("file:Images/Santa.png");
        setFrameSrcPositions();
    }

    @Override
    protected void update(Scene scene, MainGame mg) {

    }

    @Override
    protected void render(GraphicsContext gc, Scene scene, MainGame mg) {

    }
}

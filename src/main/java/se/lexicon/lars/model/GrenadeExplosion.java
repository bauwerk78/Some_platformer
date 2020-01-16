package se.lexicon.lars.model;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import se.lexicon.lars.implementer.MainGame;
import se.lexicon.lars.tools.Delayer;

public class GrenadeExplosion {

    private Image image;
    private Delayer delayer = new Delayer();

    private double posX;
    private double posY;
    private double objectWidth;
    private double objectHeight;

    private boolean nextFrame;
    private boolean goingRight;
    private boolean explosionRendered;

    private double imageWidth;
    private double imageHeight;

    private int[] imageSrcPos;
    private int numberOfFrames = 12;
    private int frameSize = 96;
    private int drawSize = 96;
    private int currentTile = 0;


    public GrenadeExplosion(double posX, double posY, boolean direction, double objWidth, double objHeight) {
        this.posX = posX;
        this.posY = posY;
        this.goingRight = direction;
        objectWidth = objWidth;
        objectHeight = objHeight;
        init();
    }

    protected void init() {
        image = new Image("file:Images/Explosion.png");
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
        setFrameSrcPositions();
    }

    protected void render(GraphicsContext gc, Scene scene, MainGame mg) {
        if (!nextFrame) {
            nextFrame = delayer.delayTimer(0.03);
            if (nextFrame) {
                if (currentTile < numberOfFrames) {
                    gc.drawImage(image, imageSrcPos[currentTile], 0, frameSize, frameSize,
                            (posX + objectWidth / 2) - (drawSize / 2d), (posY + objectHeight / 2) - (drawSize / 2d), drawSize, drawSize);
                    currentTile++;
                    nextFrame = false;
                } else {
                    explosionRendered = true;
                }
            }
        }
    }

    private void setFrameSrcPositions() {
        imageSrcPos = new int[numberOfFrames];
        for (int i = 0; i < numberOfFrames; i++) {
            imageSrcPos[i] = i * frameSize;
        }
    }

    public boolean isExplosionRendered() {
        return explosionRendered;
    }

    public int getNumberOfFrames() {
        return numberOfFrames;
    }

    public int getCurrentTile() {
        return currentTile;
    }
}

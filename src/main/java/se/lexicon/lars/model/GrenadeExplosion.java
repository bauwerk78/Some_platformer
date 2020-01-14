package se.lexicon.lars.model;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import se.lexicon.lars.implementer.MainGame;
import se.lexicon.lars.tools.Delayer;

public class GrenadeExplosion extends Grenade{

    private Image image;
    private Delayer delayer = new Delayer();

    private double srcX;
    private double srcY;

    private boolean exploded;
    private boolean nextFrame;

    private double imageWidth;
    private double imageHeight;

    private int[] imageSrcPos;
    private int numberOfFrames = 12;
    private int frameSize = 8;
    private int currentTile = 0;


    public GrenadeExplosion(double posX, double posY) {
        super(posX, posY);
        init();
    }

    @Override
    protected void init() {
        image = new Image("file:Images/Explosion.png");
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
        setFrameSrcPositions();


    }

    @Override
    protected void update(Scene scene, MainGame mg) {

    }

    @Override
    protected void render(GraphicsContext gc, Scene scene, MainGame mg) {

        gc.drawImage(image, imageSrcPos[currentTile], imageSrcPos[currentTile], frameSize, frameSize,
                getPositionX(), getPositionY(), Level.TILESIZE, Level.TILESIZE);
        if(!nextFrame && !exploded) {

            nextFrame = delayer.delayTimer(0.5);
            if(nextFrame) {

                if(currentTile < numberOfFrames) {
                    System.out.println("hello?");
                    currentTile++;
                    nextFrame = false;
                } else {
                    System.out.println("exploded?");
                    exploded = true;
                }

            }
        }

    }

    private void setFrameSrcPositions() {
        imageSrcPos = new int[numberOfFrames];
        for (int i = 0; i < numberOfFrames; i++) {
            imageSrcPos[i] = currentTile * frameSize;
        }
    }


}

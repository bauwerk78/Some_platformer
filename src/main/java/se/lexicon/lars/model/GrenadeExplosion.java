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

    private boolean nextFrame;

    private double imageWidth;
    private double imageHeight;

    private int[] imageSrcPos;
    private int numberOfFrames = 12;
    private int frameSize = 96;
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


        if(!nextFrame && !isExploded()) {

            nextFrame = delayer.delayTimer(0.05);
            if(nextFrame) {
                //Todo fix rendering position
                if(currentTile < numberOfFrames) {
                    gc.drawImage(image, imageSrcPos[currentTile], 0, frameSize, frameSize,
                            getPositionX(), getPositionY(), Level.TILESIZE, Level.TILESIZE);
                    System.out.println("current tile: " + currentTile);
                    System.out.println("imagesrcpos: " + imageSrcPos[currentTile]);
                    currentTile++;
                    nextFrame = false;
                } else {
                    System.out.println("animation rendered.");
                    setExplosionRendered(true);
                    setRenderExplosion(false);
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


}

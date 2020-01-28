package se.lexicon.lars.tools;

import javafx.scene.image.Image;

public class Animator {

    private double positionX;
    private double positionY;

    private Delayer delayer;

    private Image image;
    private String imageName;
    private int srcFrameWidth;
    private int srcFrameHeight;
    private int dstFrameWidth;
    private int dstFrameHeight;
    private double frameDelay;
    private double imageWidth;

    private int currentFrame;
    private int[] imageSrcPos;

    public Animator() {

    }

    public Animator(double positionX, double positionY, int srcFrameWidth, int srcFrameHeight, int dstFrameWidth, int dstFrameHeight, double frameDelay, String imageName) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.srcFrameWidth = srcFrameWidth;
        this.srcFrameHeight = srcFrameHeight;
        this.dstFrameWidth = dstFrameWidth;
        this.dstFrameHeight = dstFrameHeight;
        this.frameDelay = frameDelay;
        this.imageName = imageName;
        init();

    }

    public void init() {
        image = new Image("file:/Images/" + imageName);
        imageWidth = image.getWidth();
        imageSrcPos = new int[(int) imageWidth / srcFrameWidth];
        setFrameSrcPositions();
    }

    public void update() {

    }

    public void render() {


    }

    private void setFrameSrcPositions() {
        for (int i = 0; i < imageSrcPos.length; i++) {
            imageSrcPos[i] = i * srcFrameWidth;
        }
    }

}//End of class.

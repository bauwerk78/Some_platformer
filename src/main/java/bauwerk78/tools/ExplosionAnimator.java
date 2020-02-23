package bauwerk78.tools;

import javafx.scene.image.Image;

public class ExplosionAnimator {

    private double positionX;
    private double positionY;

    private Delayer delayer;

    private Image image;
    private String imagePathAndName;
    private int srcFrameWidth;
    private int srcFrameHeight;
    private int dstFrameWidth;
    private int dstFrameHeight;
    private double frameDelay;
    private double imageWidth;

    private int currentFrame;
    private int[] imageSrcPos;

    public ExplosionAnimator() {

    }

    public ExplosionAnimator(double positionX, double positionY, int srcFrameWidth, int srcFrameHeight, int dstFrameWidth, int dstFrameHeight, double frameDelay, String imagePathAndName) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.srcFrameWidth = srcFrameWidth;
        this.srcFrameHeight = srcFrameHeight;
        this.dstFrameWidth = dstFrameWidth;
        this.dstFrameHeight = dstFrameHeight;
        this.frameDelay = frameDelay;
        this.imagePathAndName = imagePathAndName;
        init();

    }

    public void init() {
        image = new Image(imagePathAndName);
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

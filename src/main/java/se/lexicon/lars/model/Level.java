package se.lexicon.lars.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Level {

    public static int levelH;
    public static int levelW;
    public static final int TILESIZE = 64;

    private Image levelImage;

    private int imageWidth;
    private int imageHeight;
    private boolean[] collideAble;

    private int positionX;
    private int positionY;


    public Level() {
        init();
    }

    public void init() {
        setLevelImage();
        setCollideAble();
    }

    private void setLevelImage() {
        levelImage = new Image("file:Images/level3_64tile2.png");
        imageWidth = (int) levelImage.getWidth();
        imageHeight = (int) levelImage.getHeight();
    }

    public void renderLevel(GraphicsContext gc) {
        for (int y = 0; y < levelH; y++) {
            for (int x = 0; x < levelW; x++) {
                if (collideAble[(int) ((y * levelW) + x)]) {
                    gc.setFill(Color.BLACK);
                } else {
                    gc.setFill(Color.WHITE);
                }
                gc.fillRect(x * TILESIZE, y * TILESIZE, TILESIZE, TILESIZE);
            }
        }
    }

    private void setCollideAble() {

        levelW = imageWidth;
        levelH = imageHeight;

        collideAble = new boolean[levelW * levelH];

        for (int y = 0; y < levelImage.getHeight(); y++) {
            for (int x = 0; x < levelImage.getWidth(); x++) {

                if ((levelImage.getPixelReader().getArgb(x, y)) == 0xff000000) {
                    collideAble[(int) ((y * levelImage.getWidth()) + x)] = true;
                } else
                    collideAble[(int) ((y * levelImage.getWidth()) + x)] = false;
            }

        }
    }

    public boolean getCollideAble(double x, double y) {
        return collideAble[(int) (y * levelW) + (int) x];
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}

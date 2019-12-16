package se.lexicon.lars.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import static se.lexicon.lars.graphics.Renderer.windowWidth;
import static se.lexicon.lars.graphics.Renderer.windowHeight;

public class Level {

    private Image levelImage;

    private int imageWidth;
    private int imageHeight;
    private boolean[] collideAble;
    //private int[] collision;
    private int levelH;
    private int levelW;
    private int positionX;
    private int positionY;
    private int tileW;
    private int tileH;
    private final int TILESIZE = 16;


    public Level() {
        init();
    }

    public void init() {
        setLevelImage();
        setCollideAble();
    }

    private void setLevelImage() {
        levelImage = new Image("file:Images/level2.png");
        imageWidth = (int) levelImage.getWidth();
        imageHeight = (int) levelImage.getHeight();
    }

    public void renderLevel(GraphicsContext gc) {
        for (int y = 0; y < levelH ; y++) {
            for (int x = 0; x < levelW ; x++) {
                if(collideAble[(int) ((y * levelW) + x)]) {
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

        //levelImage.getPixelReader().getColor()


        for (int y = 0; y < levelImage.getHeight(); y++) {
            for (int x = 0; x < levelImage.getWidth(); x++) {

                //y * levelImage.getWidth()) + x == 0xff000000
                if ((levelImage.getPixelReader().getArgb(x, y)) == 0xff000000) {
                    collideAble[(int) ((y * levelImage.getWidth()) + x)] = true;
                } else
                    collideAble[(int) ((y * levelImage.getWidth()) + x)] = false;
            }

        }
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

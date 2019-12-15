package se.lexicon.lars.model;

import javafx.scene.image.Image;

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



    private void setLevelImage() {
        levelImage = new Image("file:Images/leveltest1.png");
        imageWidth = (int) levelImage.getWidth();
        imageHeight = (int) levelImage.getHeight();
    }

    private void setCollideAble() {

        levelW = imageWidth;
        levelH = imageHeight;

        collideAble = new boolean[levelW * levelH];

        for (int y = 0; y < levelImage.getHeight(); y++) {
            for (int x = 0; x < levelImage.getWidth(); x++) {
                if(y + x == 0xff000000){
                    collideAble[y + x] = true;
                } else
                    collideAble[y + x] = false;
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

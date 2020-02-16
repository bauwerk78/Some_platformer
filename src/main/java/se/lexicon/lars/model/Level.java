package se.lexicon.lars.model;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Level {

    public static int levelH;
    public static int levelW;
    public static final int TILESIZE = 64;

    private List<Rectangle> rectangles = new ArrayList<>();
    private Image levelImage;
    private Rectangle rectangle;
    private Group levelGroup = new Group();
    private Color color;
    private int imageWidth;
    private int imageHeight;
    private boolean[] collideAble;
    private boolean[][] collideAbles;

    private int positionX;
    private int positionY;


    public Level() {
        init();
    }

    public void init() {
        setLevelImage();
        setCollideAbles();
        renderStaticLevel();
    }

    private void setLevelImage() {
        levelImage = new Image("file:Images/level_64tile_128x12.png");
        //levelImage = new Image("file:Images/level3_64tile3_64x48.png");
        imageWidth = (int) levelImage.getWidth();
        imageHeight = (int) levelImage.getHeight();
    }

/*    public void renderLevel(GraphicsContext gc) {
        for (int y = 0; y < levelH; y++) {
            for (int x = 0; x < levelW; x++) {
                if (collideAble[(int) ((y * levelW) + x)]) {
                    gc.setFill(Color.LIGHTGRAY);
                } else {
                    gc.setFill(Color.TRANSPARENT);
                }
                gc.fillRect(x * TILESIZE, y * TILESIZE, TILESIZE, TILESIZE);
            }
        }
    }*/

    public void renderStaticLevel() {
        for (int y = 0; y < levelH; y++) {
            for (int x = 0; x < levelW; x++) {
                if (collideAbles[y][x]) {
                    color = Color.LIGHTGRAY;
                } else {
                    color = Color.TRANSPARENT;
                }
                rectangle = new Rectangle(x * TILESIZE, y * TILESIZE, TILESIZE, TILESIZE);
                rectangle.setFill(color);
                levelGroup.getChildren().add(rectangle);
                rectangles.add(rectangle);
            }
        }
    }

    private void setCollideAbles() {

        levelW = imageWidth;
        levelH = imageHeight;

        collideAbles = new boolean[levelH][levelW];

        for (int y = 0; y < levelH; y++) {
            for (int x = 0; x < levelW; x++) {

                if ((levelImage.getPixelReader().getArgb(x, y)) == 0xff000000) {
                    collideAbles[y][x] = true;
                } else
                    collideAbles[y][x] = false;
            }

        }
    }

    public Group getGroup() {
        return levelGroup;
    }

    public boolean getCollideAble(double x, double y) {
        return collideAble[(int) (y * levelW) + (int) x];
    }

    public boolean getCollideAbles(double x, double y) {
        return collideAbles[(int)y][(int)x];
    }
    public double getCollideAbleX(double x) {
        return x * TILESIZE;
    }

    public double getCollideAbleY(double y) {
        return y * TILESIZE;
    }

/*    public void getActualCollideAblePosition(double x, double y) {
        if(getCollideAble(x, y)) {
            System.out.println("position x " + x + " position y " + y);
        }
    }*/

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

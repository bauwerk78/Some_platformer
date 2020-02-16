package se.lexicon.lars.model;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    //private List<ImageView> imageViews = new ArrayList<>();
    private Image levelImage;
    private Image streetImage;
    private ImageView imageView;
    private Group levelGroup = new Group();
    private Pane pane;
    private int imageWidth;
    private int imageHeight;
    private boolean[][] collideAbles;
    private double playerStartingX;
    private double playerStartingY;


    public Level() {
        init();
    }

    public void init() {
        setLevelImage();
        //setCollideAbles();
        pane = new Pane();
        pane.setPrefSize(levelW * TILESIZE, levelH * TILESIZE);
        streetImage = new Image("file:Images/Level/acera.png");
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

/*
    public void renderStaticLevel() {
        for (int y = 0; y < levelH; y++) {
            for (int x = 0; x < levelW; x++) {
                Color color;
                if (collideAbles[y][x]) {
                    color = Color.LIGHTGRAY;
                } else {
                    color = Color.TRANSPARENT;
                }
                Rectangle rectangle = new Rectangle(x * TILESIZE, y * TILESIZE, TILESIZE, TILESIZE);
                rectangle.setFill(color);
                levelGroup.getChildren().add(rectangle);
                rectangles.add(rectangle);
            }
        }
    }
*/

    public void renderStaticLevel() {
        levelW = imageWidth;
        levelH = imageHeight;
        collideAbles = new boolean[levelH][levelW];

        for (int y = 0; y < levelH; y++) {
            for (int x = 0; x < levelW; x++) {
                //Render street images.
                if (levelImage.getPixelReader().getArgb(x, y) == 0xff000000) {
                    imageView = new ImageView(streetImage);
                    imageView.setFitWidth(TILESIZE);
                    imageView.setFitHeight(TILESIZE);
                    imageView.setX(x * TILESIZE);
                    imageView.setY(y* TILESIZE);
                    //imageViews.add(imageView);
                    pane.getChildren().add(imageView);
                    collideAbles[y][x] = true;

                } else
                //Get player spawn position.
                if (levelImage.getPixelReader().getArgb(x, y) == 0xff00c300) {
                    playerStartingX = x * TILESIZE;
                    playerStartingY = y * TILESIZE;
                    collideAbles[y][x] = false;
                } else
                //Render the rest of the colliedables for now.
                if(levelImage.getPixelReader().getArgb(x, y) == 0xff000cc3) {
                    Rectangle rectangle = new Rectangle(x * TILESIZE, y * TILESIZE, TILESIZE, TILESIZE);
                    rectangle.setFill(Color.LIGHTGRAY);
                    levelGroup.getChildren().add(rectangle);
                    collideAbles[y][x] = true;
                } else {
                    collideAbles[y][x] = false;
                }

            }
        }
        levelGroup.getChildren().add(pane);

    }


/*
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
*/

    public Group getGroup() {
        return levelGroup;
    }

    public boolean getCollideAbles(double x, double y) {
        return collideAbles[(int) y][(int) x];
    }

    public double getPlayerStartingX() {
        return playerStartingX;
    }

    public double getPlayerStartingY() {
        return playerStartingY;
    }
}//End of class.

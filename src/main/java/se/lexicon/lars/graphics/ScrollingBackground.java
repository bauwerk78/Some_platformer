package se.lexicon.lars.graphics;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import se.lexicon.lars.model.Level;


public class ScrollingBackground {

    private Region backgroundRegion = new Region();
    //private Background background;

    private Pane paneLayer2 = new Pane();
    private Pane pane2 = new Pane();
    private Pane paneLayer4 = new Pane();
    private Pane pane4 = new Pane();
    private Image image1;
    private Image image2;
    private Image image3;
    private Image image4;
    private ImageView imageView1 = new ImageView();
    private ImageView imageView2 = new ImageView();
    private ImageView imageView3 = new ImageView();
    private ImageView imageView4 = new ImageView();
    private double imageView2PosX;
    private double speedXImage2 = 10;
    private int direction;


    public ScrollingBackground() {

    }

    public ScrollingBackground(Group group) {
        init(group);
    }

    public void init(Group group) {


        image1 = new Image("file:Images/Backgrounds/skill-desc_0003_bg.png");
        image2 = new Image("file:Images/Backgrounds/skill-desc_0002_far-buildings.png");
        image3 = new Image("file:Images/Backgrounds/skill-desc_0001_buildings.png");
        image4 = new Image("file:Images/Backgrounds/skill-desc_0000_foreground.png");
        BackgroundImage backgroundImage1 = new BackgroundImage(image1, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(Renderer.windowWidth, Renderer.windowHeight, false, false, false, false));
        BackgroundImage backgroundImage2 = new BackgroundImage(image2, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(Renderer.windowWidth, Renderer.windowHeight, false, false, false, false));
        BackgroundImage backgroundImage3 = new BackgroundImage(image3, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(Renderer.windowWidth, Renderer.windowHeight, false, false, false, false));
        BackgroundImage backgroundImage4 = new BackgroundImage(image4, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(Renderer.windowWidth, Renderer.windowHeight, false, false, false, false));

        imageView1.setImage(image2);
        imageView1.setFitHeight(Renderer.windowHeight);
        imageView1.setFitWidth(Renderer.windowWidth);
        //imageView1.setOpacity(1);
        imageView1.setVisible(true);


        imageView2.setImage(image2);
        imageView2.setFitHeight(Renderer.windowHeight);
        imageView2.setFitWidth(Renderer.windowWidth);
        //imageView2.setOpacity(0.5);
        imageView2.setVisible(true);


        imageView3.setImage(image3);
        imageView3.setFitHeight(Renderer.windowHeight);
        imageView3.setFitWidth(Renderer.windowWidth);
        //imageView3.setOpacity(0.5);
        imageView3.setVisible(true);

        imageView4.setImage(image4);
        imageView4.setFitHeight(Renderer.windowHeight);
        imageView4.setFitWidth(Renderer.windowWidth);
        //imageView4.setViewport(new Rectangle2D(0, Renderer.windowHeight / 2d, Renderer.windowWidth, Renderer.windowHeight / 2d));
        //imageView4.setOpacity(0.5);
        imageView4.setVisible(true);

        Background background1 = new Background(backgroundImage1);
        Background background2 = new Background(backgroundImage2);
        Background background3 = new Background(backgroundImage3);
        Background background4 = new Background(backgroundImage3, backgroundImage4);

        //First layer.
        backgroundRegion.setBackground(background1);
        backgroundRegion.setPrefSize(Level.levelW * Level.TILESIZE, Level.levelH * Level.TILESIZE);
        backgroundRegion.setVisible(true);

        //Second layer.
        paneLayer2.setPrefSize(Level.levelW * Level.TILESIZE, Level.levelH * Level.TILESIZE);
        paneLayer2.setVisible(true);
        paneLayer2.getChildren().addAll(imageView1, imageView2);

        //Fourth layer.
        paneLayer4.setBackground(background4);
        paneLayer4.setPrefSize(Level.levelW * Level.TILESIZE, Level.levelH * Level.TILESIZE);
        paneLayer4.setVisible(true);


        group.getChildren().addAll(backgroundRegion, paneLayer2, paneLayer4);

    }

    public void update() {
        imageView2.relocate(imageView2PosX, 0);
    }

    private void render(Group group) {

    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public double getImageView2PosX() {
        return imageView2PosX;
    }

    public void setImageView2PosX(double imageView2PosX) {
        this.imageView2PosX = imageView2PosX;
    }

    public double getSpeedXImage2() {
        return speedXImage2;
    }

    public void setSpeedXImage2(double speedXImage2) {
        this.speedXImage2 = speedXImage2;
    }
}//End of class.


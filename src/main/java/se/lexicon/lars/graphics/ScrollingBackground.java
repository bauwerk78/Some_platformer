package se.lexicon.lars.graphics;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import se.lexicon.lars.model.Level;


public class ScrollingBackground {

    private Region region1 = new Region();
    private Background background;

    private Pane background1 = new Pane();
    private Pane background2 = new Pane();
    private Pane background3 = new Pane();
    private Pane background4 = new Pane();
    private Image image1;
    private Image image2;
    private Image image3;
    private Image image4;
    private ImageView imageView1 = new ImageView();
    private ImageView imageView2 = new ImageView();
    private ImageView imageView3 = new ImageView();
    private ImageView imageView4 = new ImageView();



    public ScrollingBackground() {

    }

    public void init(Group group) {



        image1 = new Image("file:Images/Backgrounds/skill-desc_0003_bg.png");
        image2 = new Image("file:Images/Backgrounds/skill-desc_0002_far-buildings.png");
        image3 = new Image("file:Images/Backgrounds/skill-desc_0001_buildings.png");
        image4 = new Image("file:Images/Backgrounds/skill-desc_0000_foreground.png");
        BackgroundImage backgroundImage1 = new BackgroundImage(image1, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(Renderer.windowWidth, Renderer.windowHeight, false, false, false, false));
        BackgroundImage backgroundImage2 = new BackgroundImage(image2, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(Renderer.windowWidth, Renderer.windowHeight, false, false, false, false));
        BackgroundImage backgroundImage3 = new BackgroundImage(image3, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(Renderer.windowWidth * 2, Renderer.windowHeight * 2, false, false, false, false));
        BackgroundImage backgroundImage4 = new BackgroundImage(image4, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(Renderer.windowWidth, Renderer.windowHeight, false, false, false, false));

        imageView1.setImage(image1);
        imageView1.setFitHeight(Renderer.windowHeight * 2);
        imageView1.setFitWidth(Renderer.windowWidth * 2);
        //imageView1.setOpacity(1);
        imageView1.setVisible(true);
        background1.setPrefSize(Renderer.windowWidth * 2, Renderer.windowHeight * 2);
        background1.getChildren().add(imageView1);

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

        //stackPane.getChildren().addAll(imageView1, imageView2, imageView3, imageView4);
        //group.getChildren().add(stackPane);
        //group.getChildren().addAll(background1, background2, background3, background4);
        background = new Background(backgroundImage1, backgroundImage2, backgroundImage3, backgroundImage4);
        region1.setBackground(background);
        region1.setPrefSize(Level.levelW * Level.TILESIZE, Level.levelH * Level.TILESIZE);
        region1.setVisible(true);
        group.getChildren().add(region1);
    }

    private void render(Group group) {

    }

}

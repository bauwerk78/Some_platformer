package se.lexicon.lars.graphics;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


public class Background {

    private StackPane stackPane = new StackPane();
    private Image image1;
    private Image image2;
    private Image image3;
    private Image image4;
    private ImageView imageView1 = new ImageView();
    private ImageView imageView2 = new ImageView();
    private ImageView imageView3 = new ImageView();
    private ImageView imageView4 = new ImageView();



    public Background() {

    }

    public void init(Group group) {
        image1 = new Image("file:Images/Backgrounds/skill-desc_0003_bg.png");
        image2 = new Image("file:Images/Backgrounds/skill-desc_0002_far-buildings.png");
        image3 = new Image("file:Images/Backgrounds/skill-desc_0001_buildings.png");
        image4 = new Image("file:Images/Backgrounds/skill-desc_0000_foreground.png");
        imageView1.setImage(image1);
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

        stackPane.getChildren().addAll(imageView1, imageView2, imageView3, imageView4);
        group.getChildren().add(stackPane);
    }

    private void render(Group group) {

    }

}

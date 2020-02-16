package se.lexicon.lars.graphics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import se.lexicon.lars.model.Level;

import static se.lexicon.lars.graphics.Renderer.elapsedTime;

public class ScrollingLayer {

    private Pane layerPane = new Pane();
    private ImageView imageView1 = new ImageView();
    private ImageView imageView2 = new ImageView();
    private double imageView1PosX;
    private double imageView2PosX;
    private double imageViewWidth;
    private double layerScrollSpeed;
    private String fileLocationAndName;


    //"file:Images/Backgrounds/skill-desc_0003_bg.png" as an example for fileLocation.
    public ScrollingLayer(String fileLocationAndName, double layerScrollSpeed) {
        this.fileLocationAndName = fileLocationAndName;
        this.layerScrollSpeed = layerScrollSpeed;
        init();
    }

    public void init() {
        Image image = new Image(fileLocationAndName);

        imageView1.setImage(image);
        imageView1.setFitHeight(Renderer.windowHeight);
        imageView1.setFitWidth(Renderer.windowWidth * 2);
        //imageView1.setOpacity(1);
        imageView1.setVisible(true);

        imageView2.setImage(image);
        imageView2.setFitHeight(Renderer.windowHeight);
        imageView2.setFitWidth(Renderer.windowWidth * 2);
        //imageView2.setOpacity(0.5);
        imageView2.setVisible(true);

        //Scrolling layer.
        layerPane.setPrefSize(Level.levelW * Level.TILESIZE, Level.levelH * Level.TILESIZE);
        layerPane.setVisible(true);
        layerPane.getChildren().addAll(imageView1, imageView2);

        imageViewWidth = imageView1.getFitWidth();
        imageView1PosX = 0;
        imageView2PosX = imageViewWidth;

    }

    public void update(boolean isPlayerGoingRight, boolean isPlayerStandingStill, double playerOffX, double playerPosX) {

        if (isPlayerGoingRight && !isPlayerStandingStill && playerOffX > 0) {
            if (imageView1PosX < imageView2PosX && playerPosX > imageView1PosX + imageViewWidth + (imageViewWidth / 2)) {
                setImageView1PosX(imageView1PosX + imageViewWidth * 2);
            } else if (imageView2PosX < imageView1PosX && playerPosX > imageView2PosX + imageViewWidth + (imageViewWidth / 2)) {
                setImageView2PosX(imageView2PosX + imageViewWidth * 2);
            }
            setImageView1PosX(imageView1PosX - getLayerScrollSpeed() * elapsedTime);
            setImageView2PosX(imageView2PosX - getLayerScrollSpeed() * elapsedTime);
        }
        if (!isPlayerGoingRight && !isPlayerStandingStill && playerOffX < 0) {
            if (imageView1PosX > imageView2PosX && playerPosX < imageView1PosX - imageViewWidth + (imageViewWidth / 2d)) {
                setImageView1PosX(imageView1PosX - imageViewWidth * 2);
            } else if (imageView2PosX > imageView1PosX && playerPosX < imageView2PosX - imageViewWidth + (imageViewWidth / 2d)) {
                setImageView2PosX(imageView2PosX - imageViewWidth * 2);
            }
            setImageView1PosX(imageView1PosX + getLayerScrollSpeed() * elapsedTime);
            setImageView2PosX(imageView2PosX + getLayerScrollSpeed() * elapsedTime);
        }
        imageView1.relocate(imageView1PosX, 0);
        imageView2.relocate(imageView2PosX, 0);
    }

    public Pane getLayerPane() {
        return layerPane;
    }

    public double getLayerScrollSpeed() {
        return layerScrollSpeed;
    }

    public void setLayerScrollSpeed(double layerScrollSpeed) {
        this.layerScrollSpeed = layerScrollSpeed;
    }

    public double getImageView1PosX() {
        return imageView1PosX;
    }

    public void setImageView1PosX(double imageView1PosX) {
        this.imageView1PosX = imageView1PosX;
    }

    public double getImageView2PosX() {
        return imageView2PosX;
    }

    public void setImageView2PosX(double imageView2PosX) {
        this.imageView2PosX = imageView2PosX;
    }


}
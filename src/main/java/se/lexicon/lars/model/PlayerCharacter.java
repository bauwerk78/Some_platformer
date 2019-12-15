package se.lexicon.lars.model;

import static se.lexicon.lars.graphics.Renderer.windowWidth;
import static se.lexicon.lars.graphics.Renderer.windowHeight;
import static se.lexicon.lars.graphics.Renderer.elapsedTime;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class PlayerCharacter extends GameObject {
    private Image image;
    ArrayList<String> input = new ArrayList<>();


    public PlayerCharacter() {
        init();
    }

    public PlayerCharacter(double positionX, double positionY) {
        super(positionX, positionY);
        init();
    }

    private void getPlayerInput(Scene scene) {
        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();
                        if (!input.contains(code))
                            input.add(code);
                    }
                });

        scene.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();
                        input.remove(code);
                    }
                });

    }


    @Override
    protected void init() {
        setObjectWidth(50);
        setObjectHeight(50);
        setImage();
    }

    @Override
    protected void update() {

    }

    @Override
    protected void render(GraphicsContext gc) {
        gc.drawImage(image, 100, 100, getObjectWidth(), getObjectHeight());
    }

    @Override
    protected void move() {

    }

    public void setImage() {
        image = new Image("file:Images/mario_rambo2.gif", getObjectWidth(), getObjectHeight(), false, false);
    }


}//End of class.


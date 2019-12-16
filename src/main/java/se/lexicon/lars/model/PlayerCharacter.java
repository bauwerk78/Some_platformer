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
    private double velocity;
    private double gravity;
    private double jumpSpeed;
    private int tileSize = 16;

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
        setObjectWidth(16);
        setObjectHeight(16);
        setPositionX(7 * 16);
        setPositionY(windowHeight - (10 * 16));
        setJumpSpeed(-16);
        setObjectSpeedX(100);
        gravity = 200;
        setImage();
    }

    @Override
    protected void update() {
        velocity += (gravity * elapsedTime);
        setPositionY(getPositionY() + velocity);
        if(getPositionY() > windowHeight - 2 * 16) {
            setPositionY(windowHeight - 2 * 16);
            velocity = 0;
        }
    }

    @Override
    protected void render(GraphicsContext gc) {
        //move(scene);
        update();
        gc.drawImage(image, getPositionX(), getPositionY(), getObjectWidth(), getObjectHeight());
    }

    public void renderPlayer(GraphicsContext gc, Scene scene) {
        move(scene);
        update();
        gc.drawImage(image, getPositionX(), getPositionY(), getObjectWidth(), getObjectHeight());
    }

    @Override
    protected void move(Scene scene) {
        getPlayerInput(scene);
        if(input.contains("UP") && velocity == 0) {
            velocity += jumpSpeed;
        }
        if(input.contains("LEFT")) {
            setPositionX(getPositionX() - (getObjectSpeedX() * elapsedTime));
        }
        if(input.contains("RIGHT")) {
            setPositionX(getPositionX() + (getObjectSpeedX() * elapsedTime));
        }

    }

    public void setImage() {
        image = new Image("file:Images/mario_rambo2.gif", getObjectWidth(), getObjectHeight(), false, false);
    }

    public double getJumpSpeed() {
        return jumpSpeed;
    }

    public void setJumpSpeed(double jumpSpeed) {
        this.jumpSpeed = jumpSpeed;
    }
}//End of class.


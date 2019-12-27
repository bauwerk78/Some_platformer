package se.lexicon.lars.model;

import static se.lexicon.lars.graphics.Renderer.elapsedTime;
import static se.lexicon.lars.model.Level.TILESIZE;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class PlayerCharacter extends GameObject {

    private final double fallSpeed = 100;
    private final double jumpHeight = -25;


    private Image image;
    private double velocity = 0;
    private boolean goingRight = true;
    private boolean playerJumping = false;
    private boolean playerFalling = true;
    private boolean playerGrounded = false;
    private int tileX;
    private int tileY;

    ArrayList<String> input = new ArrayList<>();


    public PlayerCharacter() {
        init();
    }

    public PlayerCharacter(double positionX, double positionY) {
        super(positionX, positionY);
        init();
    }

    @Override
    protected void init() {
        setObjectWidth(TILESIZE);
        setObjectHeight(TILESIZE);
        //Todo not working.
        setTileX(2);
        setTileY(8);
        setPositionX(getTileX() * TILESIZE);
        setPositionY(getTileY() * TILESIZE);
        setObjectSpeedX(300);
        setImage();
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
    protected void move(Scene scene) {
        getPlayerInput(scene);
        if (input.contains("UP") && isPlayerGrounded()) {
            velocity += jumpHeight;
            playerGrounded = false;
            playerJumping = true;
            setPositionY(getPositionY() + velocity);
            //TODO modify so you don't get constant jumping.
            //input.clear();
        }
        if (input.contains("LEFT")) {
            setPositionX(getPositionX() - (getObjectSpeedX() * elapsedTime));
            goingRight = false;
        }
        if (input.contains("RIGHT")) {
            setPositionX(getPositionX() + (getObjectSpeedX() * elapsedTime));
            goingRight = true;
        }
    }

    @Override
    protected void update() {
        if (!playerGrounded) {
            velocity += (fallSpeed * elapsedTime);
            setPositionY(getPositionY() + velocity);
        }
        double tempX;
        double tempY;

        tileX = (int) ((getPositionX() + getObjectWidth() / 2) / TILESIZE);
        tileY = (int) (((getPositionY()) / TILESIZE));
    }

    @Override
    public void render(GraphicsContext gc, Scene scene) {
        move(scene);
        update();
        if (goingRight) {
            gc.drawImage(image, getPositionX(), getPositionY(), getObjectWidth(), getObjectHeight());
        } else {
            gc.drawImage(image, getPositionX() + getObjectWidth(), getPositionY(), -getObjectWidth(), getObjectHeight());
        }

    }


    public void setImage() {
        image = new Image("file:Images/mario_rambo3.gif", getObjectWidth(), getObjectHeight(), false, false);
    }

    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public boolean isPlayerJumping() {
        return playerJumping;
    }

    public void setPlayerJumping(boolean playerJumping) {
        this.playerJumping = playerJumping;
    }

    public boolean isPlayerFalling() {
        return playerFalling;
    }

    public void setPlayerFalling(boolean playerFalling) {
        this.playerFalling = playerFalling;
    }

    public boolean isGoingRight() {
        return goingRight;
    }

    public void setGoingRight(boolean goingRight) {
        this.goingRight = goingRight;
    }

    public boolean isPlayerGrounded() {
        return playerGrounded;
    }

    public void setPlayerGrounded(boolean playerGrounded) {
        this.playerGrounded = playerGrounded;
    }
}//End of class.


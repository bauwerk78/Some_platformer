package se.lexicon.lars.model;

import static se.lexicon.lars.graphics.Renderer.elapsedTime;
import static se.lexicon.lars.model.Level.TILESIZE;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import se.lexicon.lars.implementer.MainGame;

import java.util.ArrayList;

public class PlayerCharacter extends GameObject {

    private final double gravity = 100;
    private final double jumpHeight = -25;


    private Image image;
    private double velocity = 0;
    private boolean goingRight = true;
    private boolean playerJumping = false;
    private boolean playerFalling = true;
    private boolean playerGrounded = false;
    private double tileX;
    private double tileY;
    private double offX = 0;
    private double offY = 0;

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
        setTileX(3);
        setTileY(9);
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
    protected void move(Scene scene, MainGame mg) {
        //TODO broken movement, left right works fine but not when falling or jumping atm.
        getPlayerInput(scene);
        //Player falling.
        if (!mg.getCollision(getTileX(), getTileY() + 1)) {
            playerGrounded = false;
            velocity += gravity * elapsedTime;
            offY += velocity;
            if (offY > TILESIZE) {
                tileY++;
                offY = 0;
                offY += velocity;
            }
        } else {
            playerGrounded = true;
            playerJumping = false;
            offY = 0;
            velocity = 0;
        }

        //Jumping
        if (input.contains("UP") && isPlayerGrounded()) {
            velocity += jumpHeight;
            offY += velocity;
            playerGrounded = false;
            playerJumping = true;
            if (offY < TILESIZE) {
                tileY--;
                offY = 0;
                offY += velocity;
            }

        }


        if (input.contains("LEFT")) {
            goingRight = false;
            offX -= (getObjectSpeedX() * elapsedTime);
            if (mg.getCollision(getTileX() - 1, getTileY()) && offX <= 0) {
                offX = 0;
            }
            if (offX < -TILESIZE) {
                tileX--;
                offX = 0;
            }

        }
        if (input.contains("RIGHT")) {
            goingRight = true;
            offX += (getObjectSpeedX() * elapsedTime);
            if (mg.getCollision(getTileX() + 1, getTileY()) && offX >= 0) {
                System.out.println("colliding right: ");
                offX = 0;
            }
            if (offX > TILESIZE) {
                tileX++;
                offX = 0;
            }

        }
        //tileY = (int) (((getPositionY()) / TILESIZE));

        setPositionY((getTileY() * TILESIZE) + offY);
        setPositionX((getTileX() * TILESIZE) + offX);

    }

    @Override
    protected void update(MainGame mg) {


    }

    @Override
    public void render(GraphicsContext gc, Scene scene, MainGame mg) {
        move(scene, mg);
        update(mg);
        gc.setFill(Color.RED);
        gc.fillRect(getPositionX(), getPositionY(), TILESIZE, TILESIZE);
/*
        if (goingRight) {
            gc.drawImage(image, getPositionX(), getPositionY(), getObjectWidth(), getObjectHeight());
        } else {
            gc.drawImage(image, getPositionX() + getObjectWidth(), getPositionY(), -getObjectWidth(), getObjectHeight());
        }
*/

    }


    public void setImage() {
        image = new Image("file:Images/mario_rambo3.gif", getObjectWidth(), getObjectHeight(), false, false);
    }

    public double getOffX() {
        return offX;
    }

    public void setOffX(double offX) {
        this.offX = offX;
    }

    public double getOffY() {
        return offY;
    }

    public void setOffY(double offY) {
        this.offY = offY;
    }

    public double getTileX() {
        return tileX;
    }

    public void setTileX(double tileX) {
        this.tileX = tileX;
    }

    public double getTileY() {
        return tileY;
    }

    public void setTileY(double tileY) {
        this.tileY = tileY;
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


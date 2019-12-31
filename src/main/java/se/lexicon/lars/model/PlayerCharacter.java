package se.lexicon.lars.model;

import static se.lexicon.lars.graphics.Renderer.elapsedTime;
import static se.lexicon.lars.model.Level.TILESIZE;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import se.lexicon.lars.implementer.MainGame;

import java.util.ArrayList;

public class PlayerCharacter extends GameObject {

    private final double gravity = 100;
    private final double jumpHeight = -25;


    private Image image;
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
        setTileX(7);
        setTileY(2);
        setPositionX(getTileX() * TILESIZE);
        setPositionY(getTileY() * TILESIZE);
        setObjectSpeedX(300);
        setObjectSpeedY(0);
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
    protected void update(Scene scene, MainGame mg) {
        getPlayerInput(scene);

        //Jumping and falling.
        setObjectSpeedY(getObjectSpeedY() + (gravity * elapsedTime));

        //Jumping
        if (input.contains("UP") && isPlayerGrounded()) {
            setObjectSpeedY(getObjectSpeedY() + jumpHeight);
            playerGrounded = false;
            playerJumping = true;
        }

        //Colliding upwards
        if (getObjectSpeedY() < 0) {
            if (isPlayerJumping()) {
                if (mg.getCollision(getTileX(), tileY - 1) && offY <= 0) {
                    offY = 0;
                    setObjectSpeedY(0);
                }
            }
        }

        //Colliding with tile beneath player.
        if (getObjectSpeedY() > 0) {
            if (mg.getCollision(getTileX(), getTileY() + 1) && offY >= 0) {
                playerGrounded = true;
                //playerJumping = false;
                offY = 0;
                setObjectSpeedY(0);
            }
        }

        offY += getObjectSpeedY();
        //End of jumping and falling.

        //Right and left movement.
        if (input.contains("LEFT")) {
            goingRight = false;
            offX -= (getObjectSpeedX() * elapsedTime);
            //Going left.

            if (mg.getCollision(getTileX() - 1, getTileY()) && offX <= 0) {
                System.out.println("colliding left: ");
                offX = 0;
            }

        }

        if (input.contains("RIGHT")) {
            goingRight = true;
            offX += (getObjectSpeedX() * elapsedTime);
            //Going right.

            if (mg.getCollision(getTileX() + 1, getTileY()) && offX >= 0) {
                System.out.println("colliding right: ");
                offX = 0;
            }

        }
        //End of right and left movement.

        //Update positions.
        if (offY > TILESIZE / 2f) {
            tileY++;
            offY -= TILESIZE;
        }

        if (offY < -TILESIZE / 2f) {
            tileY--;
            offY += TILESIZE;
        }

        if (offX < -TILESIZE / 2f) {
            tileX--;
            offX += TILESIZE;
        }

        if (offX > TILESIZE / 2f) {
            tileX++;
            offX -= TILESIZE;
        }

        setPositionX((getTileX() * TILESIZE) + offX);
        setPositionY((getTileY() * TILESIZE) + offY);
    }


    @Override
    public void render(GraphicsContext gc, Scene scene, MainGame mg) {
        update(scene, mg);
        //gc.setFill(Color.RED);
        //gc.fillRect(getPositionX(), getPositionY(), TILESIZE, TILESIZE);
        if (goingRight) {
            gc.drawImage(image, getPositionX(), getPositionY(), getObjectWidth(), getObjectHeight());
        } else {
            gc.drawImage(image, getPositionX() + getObjectWidth(), getPositionY(), -getObjectWidth(), getObjectHeight());
        }

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


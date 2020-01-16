package se.lexicon.lars.model;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import se.lexicon.lars.implementer.MainGame;
import se.lexicon.lars.tools.Delayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import static se.lexicon.lars.graphics.Renderer.elapsedTime;
import static se.lexicon.lars.model.Level.TILESIZE;

public class PlayerCharacter extends GameObject {

    private final double gravity = 100;
    private final double jumpHeight = -25;

    //Using a constant since elapsed time resulted in jerky movement.
    private final double elapsedTime = 0.015;

    List<Bullet> bullets = new ArrayList<>();
    Iterator<Bullet> bulletIterator;
    List<Grenade> grenades = new ArrayList<>();
    Iterator<Grenade> grenadeIterator;

    //Player input.
    List<String> input = new ArrayList<>();

    private Image image;
    private Delayer bulletDelayer = new Delayer();
    private Delayer grenadeDelayer = new Delayer();
    private boolean bulletReady = true;
    private boolean grenadeReady = true;

    private boolean goingRight = true;
    private boolean playerJumping = false;
    private boolean playerGrounded = false;
    private double tileX;
    private double tileY;
    private double offX = 0;
    private double offY = 0;


    public PlayerCharacter() {
        init();
    }

    public PlayerCharacter(double positionX, double positionY) {
        super(positionX, positionY);
        init();
    }

    @Override
    protected void init() {
        setID("Player");
        setObjectWidth(TILESIZE);
        setObjectHeight(TILESIZE);
        setTileX(4);
        setTileY(6);
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

/*        new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                if (!input.contains(code))
                    input.add(code);
            }
        });*/

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

        //Jumping
        if (input.contains("UP") && isPlayerGrounded()) {
            setObjectSpeedY(getObjectSpeedY() + jumpHeight);
            //setObjectSpeedY(jumpHeight);
            playerGrounded = false;
            playerJumping = true;
            input.remove("UP");
        }

        //Colliding upwards
        if (getObjectSpeedY() < 0) {
            if (isPlayerJumping()) {
                /*if (mg.getCollision(tileX, tileY - 1) && offY <= 0) {*/
                if ((mg.getCollision(Math.floor(getPositionX() / TILESIZE), Math.floor(getPositionY() / TILESIZE) - 1) && offY >= 0) ||
                        (mg.getCollision(Math.ceil(getPositionX() / TILESIZE), Math.floor(getPositionY() / TILESIZE) - 1) && offY >= 0)) {
                    offY = 0;
                    setObjectSpeedY(0);
                }
            }
        }

        //Colliding with tile beneath player.
        if (getObjectSpeedY() >= 0) {
            if ((mg.getCollision(Math.floor((getPositionX()) / TILESIZE), tileY + 1)) ||
                    (mg.getCollision(Math.ceil((getPositionX()) / TILESIZE), tileY + 1))) {

                setObjectSpeedY(0);
                playerGrounded = true;
                playerJumping = false;
                offY = 0;

            } else {
                playerGrounded = false;
            }
        }
        //System.out.println(getObjectSpeedY());
        offY += getObjectSpeedY();
        if (getObjectSpeedY() > 0 || getObjectSpeedY() < 0 || !isPlayerGrounded()) {
            //System.out.println("meow");
            setObjectSpeedY(getObjectSpeedY() + (gravity * elapsedTime));
        }

        //System.out.println(offY);

        //End of jumping and falling.


        //Right and left movement.
        if (input.contains("LEFT")) {
            goingRight = false;
            offX -= (getObjectSpeedX() * elapsedTime);
            if (mg.getCollision(tileX - 1, tileY) && offX < 0) {
                //System.out.println("colliding left: ");
                offX = 0;
            }


        }

        if (input.contains("RIGHT")) {
            goingRight = true;
            offX += (getObjectSpeedX() * elapsedTime);
            if (mg.getCollision(tileX + 1, tileY) && offX > 0) {
                //System.out.println("colliding right: ");
                offX = 0;
            }

        }
        //End of right and left movement.

        //Update player position.

        //Down
        if (offY > TILESIZE / 2f) {
            tileY++;
            offY -= TILESIZE;
        }
        //Up
        if (offY < -TILESIZE / 2f) {
            tileY--;
            offY += TILESIZE;
        }
        // Left
        if (offX < -TILESIZE / 2f) {
            tileX--;
            offX += TILESIZE;
        }
        //Right
        if (offX > TILESIZE / 2f) {
            tileX++;
            offX -= TILESIZE;
        }

        setPositionX((tileX * TILESIZE) + offX);
        setPositionY((tileY * TILESIZE) + offY);


        //Start of player firing.

        //Regular bullet
        if (input.contains("F") && bulletReady) {
            if (goingRight) {
                bullets.add(new Bullet(getPositionX() + getObjectWidth() - 6, getPositionY() + 15, true));
            } else {
                bullets.add(new Bullet(getPositionX() - 3, getPositionY() + 15, false));
            }
            bulletReady = false;
        }

        if (!bulletReady) {
            bulletReady = bulletDelayer.delayTimer(0.2);
        }

        //Grenade
        if (input.contains("G") && grenadeReady) {
            //System.out.println("throwing grenade");
            if (goingRight) {
                grenades.add(new Grenade(getPositionX() + getObjectWidth() - 6, getPositionY() + 15, true));
            } else {
                grenades.add(new Grenade(getPositionX() - 3, getPositionY() + 15, false));
            }
            grenadeReady = false;
        }

        if (!grenadeReady) {
            grenadeReady = grenadeDelayer.delayTimer(1);
        }

        //End of player firing.

        //System.out.println(input);

    }


    @Override
    public void render(GraphicsContext gc, Scene scene, MainGame mg) {
        update(scene, mg);
        bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            if (bullet.isCollided()) {
                bulletIterator.remove();
            } else {
                bullet.render(gc, scene, mg);
            }
        }

        grenadeIterator = grenades.iterator();
        while (grenadeIterator.hasNext()) {
            Grenade grenade = grenadeIterator.next();
            if(grenade.isDone()) {
                grenadeIterator.remove();
            } else {
                grenade.render(gc, scene, mg);
            }

        }

/*        gc.setFill(Color.RED);
        gc.fillRect(getPositionX(), getPositionY(), TILESIZE, TILESIZE);*/
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


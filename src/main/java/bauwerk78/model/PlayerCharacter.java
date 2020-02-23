package bauwerk78.model;

import bauwerk78.implementer.MainGame;
import bauwerk78.tools.CharacterAnimation;
import bauwerk78.tools.Delayer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import static se.lexicon.lars.graphics.Renderer.elapsedTime;


public class PlayerCharacter extends GameObject {

    private final double gravity = 100;
    private final double jumpHeight = -25;

    //Using a constant since elapsed time resulted in jerky movement.
    private final double elapsedTime = 0.015;

    private List<Bullet> bullets = new ArrayList<>();
    private Iterator<Bullet> bulletIterator;
    private List<Grenade> grenades = new ArrayList<>();
    private Iterator<Grenade> grenadeIterator;

    //Player input.
    private List<String> input = new ArrayList<>();

    private Image image;
    private Delayer bulletDelayer = new Delayer();
    private Delayer grenadeDelayer = new Delayer();
    private int playerDrawWidth = 64;
    private int playerDrawHeight = 64;
    private CharacterAnimation walkingRightAnimation = new CharacterAnimation("Images/PlayerCharacter/Cowboy/walking_right_128x128_9_frames.png", 9, 128, 128, 0.1, playerDrawWidth, playerDrawHeight);
    private CharacterAnimation walkingLeftAnimation = new CharacterAnimation("Images/PlayerCharacter/Cowboy/walking_left_128x128_9_frames.png", 9, 128, 128, 0.1, playerDrawWidth, playerDrawHeight);
    private CharacterAnimation shootingRightAnimation = new CharacterAnimation("Images/PlayerCharacter/Cowboy/shooting_right_128x128_3_frames.png", 3, 128, 128, 0.1, playerDrawWidth, playerDrawHeight);
    private CharacterAnimation shootingLeftAnimation = new CharacterAnimation("Images/PlayerCharacter/Cowboy/shooting_left_128x128_3_frames.png", 3, 128, 128, 0.1, playerDrawWidth, playerDrawHeight);
    private Image playerIdleRightImage = new Image("file:Images/PlayerCharacter/Cowboy/idle_right_128x128.png");
    private Image playerIdleLeftImage = new Image("file:Images/PlayerCharacter/Cowboy/idle_left_128x128.png");
    private Image playerJumpingRightImage = new Image("file:Images/PlayerCharacter/Cowboy/jumping_right_128x128.png");
    private Image playerJumpingLeftImage = new Image("file:Images/PlayerCharacter/Cowboy/jumping_left_128x128.png");
    private ImageView imageView = new ImageView(playerJumpingRightImage);
    private boolean bulletReady = true;
    private boolean grenadeReady = true;

    private boolean goingRight = true;
    private boolean standingStill;
    private boolean playerJumping = false;
    private boolean playerGrounded = false;
    private boolean bulletFired;
    private double tileX;
    private double tileY;
    private double offX = 0;
    private double offY = 0;

    MotionBlur motionBlur = new MotionBlur();


    public PlayerCharacter() {
        init();
    }

    public PlayerCharacter(double positionX, double positionY) {
        super(positionX, positionY);
        setTileX(positionX / Level.TILESIZE);
        setTileY(positionY / Level.TILESIZE);
        init();
    }

    @Override
    protected void init() {
        setID("Player1");
        setObjectWidth(playerDrawWidth);
        setObjectHeight(playerDrawHeight);
        setObjectSpeedX(300);
        setObjectSpeedY(0);
        //TODO just testing
        motionBlur.setRadius(10);
        motionBlur.setAngle(-15);

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
        //System.out.println(offX);
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
                if ((mg.getCollision(Math.floor(getPositionX() / Level.TILESIZE), Math.floor(getPositionY() / Level.TILESIZE) - 1) && offY >= 0) ||
                        (mg.getCollision(Math.ceil(getPositionX() / Level.TILESIZE), Math.floor(getPositionY() / Level.TILESIZE) - 1) && offY >= 0)) {
                    offY = 0;
                    setObjectSpeedY(0);
                }
            }
        }

        //Colliding with tile beneath player.
        if (getObjectSpeedY() >= 0) {
            if ((mg.getCollision(Math.floor((getPositionX()) / Level.TILESIZE), tileY + 1)) ||
                    (mg.getCollision(Math.ceil((getPositionX()) / Level.TILESIZE), tileY + 1))) {

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
            standingStill = false;
            if (mg.getCollision(tileX - 1, tileY) && offX < 0 || tileX - 1 == 0 && offX < 0) {
                //System.out.println("colliding left: ");
                offX = 0;
            }
        } else if (input.contains("RIGHT")) {
            goingRight = true;
            offX += (getObjectSpeedX() * elapsedTime);
            standingStill = false;
            if (mg.getCollision(tileX + 1, tileY) && offX > 0 || tileX + 1 == 0 && offX > 0) {
                //System.out.println("colliding right: ");
                offX = 0;
            }
        } else {
            standingStill = true;
        }
        //End of right and left movement.

        //Update player position.

        //Down
        if (offY > Level.TILESIZE / 2f) {
            tileY++;
            offY -= Level.TILESIZE;
        }
        //Up
        if (offY < -Level.TILESIZE / 2f) {
            tileY--;
            offY += Level.TILESIZE;
        }
        // Left
        if (offX < -Level.TILESIZE / 2f) {
            tileX--;
            offX += Level.TILESIZE;
        }
        //Right
        if (offX > Level.TILESIZE / 2f) {
            tileX++;
            offX -= Level.TILESIZE;
        }

        setPositionX((tileX * Level.TILESIZE) + offX);
        setPositionY((tileY * Level.TILESIZE) + offY);

        //Start of player firing weapons.

        //Regular bullet
        if (input.contains("F") && bulletReady) {
            if (goingRight) {
                bullets.add(new Bullet(getPositionX() + getObjectWidth() - 6, getPositionY() + 15, true));
            } else {
                bullets.add(new Bullet(getPositionX() - 3, getPositionY() + 15, false));
            }
            bulletFired = true;
            bulletReady = false;
        }

        if (!bulletReady) {
            bulletReady = bulletDelayer.delayTimer(0.2);
            if(bulletReady) {
                bulletFired = false;
                shootingLeftAnimation.setCurrentFrame(0);
                shootingRightAnimation.setCurrentFrame(0);
            }
        }
        //End of bullets.

        //Grenade
        if (input.contains("G") && grenadeReady) {
            if (goingRight) {
                if (mg.getCollision(tileX + 1, tileY) && offX + getObjectWidth() >= 0) {
                    grenades.add(new Grenade(getPositionX() + getObjectWidth() - 6, getPositionY() + 15, false));
                } else {
                    grenades.add(new Grenade(getPositionX() + getObjectWidth() - 6, getPositionY() + 15, true));
                }
            }
            if (!goingRight) {
                if (mg.getCollision(tileX - 1, tileY) && offX - getObjectWidth() <= 0) {
                    grenades.add(new Grenade(getPositionX() - 3, getPositionY() + 15, true));
                } else {
                    grenades.add(new Grenade(getPositionX() - 3, getPositionY() + 15, false));
                }
            }
            grenadeReady = false;
        }

        if (!grenadeReady) {
            grenadeReady = grenadeDelayer.delayTimer(1);
        }

        //End of grenade.

        //End of player firing weapons.

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
            if (grenade.isDone()) {
                grenadeIterator.remove();
            } else {
                grenade.render(gc, scene, mg);
            }

        }

/*        gc.setFill(Color.RED);
        gc.fillRect(getPositionX(), getPositionY(), TILESIZE, TILESIZE);*/
/*        if (goingRight) {
            gc.drawImage(image, getPositionX(), getPositionY(), getObjectWidth(), getObjectHeight());
        } else {
            gc.drawImage(image, getPositionX() + getObjectWidth(), getPositionY(), -getObjectWidth(), getObjectHeight());
        }*/
        if (playerGrounded) {
            if (goingRight && !standingStill) {
                walkingRightAnimation.walking(true, gc, getPositionX(), getPositionY());
            }
            if (!goingRight && !standingStill) {
                walkingLeftAnimation.walking(true, gc, getPositionX(), getPositionY());
            }
            if(goingRight && standingStill && !bulletFired) {
                gc.drawImage(playerIdleRightImage, getPositionX(), getPositionY(), playerDrawWidth, playerDrawHeight);
            }
            if(!goingRight && standingStill && !bulletFired) {
                gc.drawImage(playerIdleLeftImage, getPositionX(), getPositionY(), playerDrawWidth, playerDrawHeight);
            }
            if(goingRight && bulletFired) {
                shootingRightAnimation.shooting(gc, getPositionX(), getPositionY());
            }
            if(!goingRight && bulletFired) {
                shootingLeftAnimation.shooting(gc, getPositionX(), getPositionY());
            }
        }
        if (playerJumping || !playerGrounded) {
            if (goingRight) {
                gc.drawImage(playerJumpingRightImage, getPositionX(), getPositionY(), playerDrawWidth, playerDrawHeight);
            } else {
                gc.drawImage(playerJumpingLeftImage, getPositionX(), getPositionY(), playerDrawWidth, playerDrawHeight);
            }
        }


    }


    public void setImage() {
        image = new Image("file:Images/PlayerCharacter/Rocky.png", getObjectWidth(), getObjectHeight(), false, false);
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

    public boolean isStandingStill() {
        return standingStill;
    }
}//End of class.


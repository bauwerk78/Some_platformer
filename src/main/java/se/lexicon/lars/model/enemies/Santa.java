package se.lexicon.lars.model.enemies;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import se.lexicon.lars.implementer.MainGame;
import se.lexicon.lars.model.GameObject;
import se.lexicon.lars.model.Level;
import se.lexicon.lars.tools.Delayer;
import se.lexicon.lars.tools.Randomize;

import static se.lexicon.lars.model.Level.TILESIZE;

public class Santa extends GameObject implements Randomize {

/*    private double positionX;
    private double positionY;*/

    private final double gravity = 100;
    private final double jumpHeight = -25;

    //Using a constant since elapsed time resulted in jerky movement.
    private final double elapsedTime = 0.015;


    private Delayer delayer;

    private Image image;
    private int frameHeight = 72;
    private int frameWidth = 64;
    private int imageWidth = 704;
    private int currentFrame = 0;
    private int[] imageSrcPos = new int[imageWidth / frameWidth];

    private boolean goingRight = true;
    private boolean charJumping = false;
    private boolean charGrounded = false;
    private double tileX;
    private double tileY;
    private double offX = 0;
    private double offY = 0;
    private int offXMaxRight = -15;
    private int offXMaxLeft = 15;

    public Santa(double posX, double posY) {
        super(posX, posY);
        init();


    }

    public Santa(double posX, double posY, boolean goingRight) {
        super(posX, posY);
        this.goingRight = goingRight;
        init();


    }

    public Santa(int tileX, int tileY, boolean goingRight) {
        super(tileX * TILESIZE, tileY * TILESIZE);
        this.goingRight = goingRight;
        this.tileX = tileX;
        this.tileY = tileY;
        init();


    }

    @Override
    protected void init() {
        image = new Image("file:Images/Santa.png");
        setFrameSrcPositions();
        setObjectSpeedX(100);
        setObjectSpeedY(0);
    }

    private void setFrameSrcPositions() {
        for (int i = 0; i < imageSrcPos.length; i++) {
            imageSrcPos[i] = i * frameWidth;
        }
    }

    private boolean randomizeJumping() {
        return Randomize.randBoolean();
    }

    @Override
    protected void update(Scene scene, MainGame mg) {

        if (goingRight & charGrounded  & (tileY - 2 != 0 || tileY - 1 != 0)) {
            //If you can jump one tile up.
            if (!mg.getCollision(tileX + 1, tileY - 1) & mg.getCollision(tileX + 1, tileY) & offX >= offXMaxRight) {
                charJumping = randomizeJumping();
                System.out.println("wtf1");
            } else if (!mg.getCollision(tileX + 1, tileY) & mg.getCollision(tileX + 1, tileY - 1) & !mg.getCollision(tileX + 1, tileY - 2) & offX >= offXMaxRight) {
                charJumping = randomizeJumping();
                System.out.println("wtf2");
            } else if (mg.getCollision(tileX + 1, tileY) & mg.getCollision(tileX + 1, tileY - 1) & !mg.getCollision(tileX + 1, tileY - 2) & offX >= offXMaxRight) {
                charJumping = randomizeJumping();
                System.out.println("wtf3");
            } else {
                charJumping = false;
                //System.out.println("wtf4");
            }


        }

        if (!goingRight & charGrounded  & (tileY - 2 != 0 || tileY - 1 != 0)) {
            //If you can jump one tile up.
            if (!mg.getCollision(tileX - 1, tileY - 1) & mg.getCollision(tileX - 1, tileY) & offX <= offXMaxLeft) {
                charJumping = randomizeJumping();
                System.out.println("left1");
            } else if (!mg.getCollision(tileX - 1, tileY) & mg.getCollision(tileX - 1, tileY - 1) & !mg.getCollision(tileX - 1, tileY - 2) & offX <= offXMaxLeft) {
                charJumping = randomizeJumping();
                System.out.println("left2");
            } else if (mg.getCollision(tileX - 1, tileY) & mg.getCollision(tileX - 1, tileY - 1) & !mg.getCollision(tileX - 1, tileY - 2) & offX <= offXMaxLeft) {
                charJumping = randomizeJumping();
                System.out.println("left3");
            } else {
                charJumping = false;
            }
        }


/*        if(goingRight && ((!mg.getCollision(tileX + 1, tileY - 1) && mg.getCollision(tileX + 1, tileY) ||
                (!mg.getCollision(tileX + 1, tileY) && mg.getCollision(tileX + 1, tileY - 2))) && offX <= 0)) {
            charJumping = true;
        }*/

/*        if(!goingRight && !mg.getCollision(tileX - 1, tileY - 1) && mg.getCollision(tileX - 1, tileY) && offX >= 0) {
            charJumping = true;
        }*/

        //Jumping
        if (charJumping && charGrounded) {
            setObjectSpeedY(getObjectSpeedY() + jumpHeight);
            //setObjectSpeedY(jumpHeight);
            charGrounded = false;
            //charJumping = false;

        }

        //Colliding upwards
        if (getObjectSpeedY() < 0) {
            if (charJumping) {

                if (mg.getCollision(tileX, tileY - 1) && offY <= 0) {
                    System.out.println("colliding up?");
                    if ((mg.getCollision(Math.floor(getPositionX() / TILESIZE), Math.floor(getPositionY() / TILESIZE) - 1) && offY >= 0) ||
                            (mg.getCollision(Math.ceil(getPositionX() / TILESIZE), Math.floor(getPositionY() / TILESIZE) - 1) && offY >= 0)) {
                        offY = 0;
                        setObjectSpeedY(0);
                    }
                }
            }
        }

        //Colliding with tile beneath player.
        if (getObjectSpeedY() >= 0) {
            if ((mg.getCollision(Math.floor((getPositionX()) / TILESIZE), tileY + 1)) ||
                    (mg.getCollision(Math.ceil((getPositionX()) / TILESIZE), tileY + 1))) {

                setObjectSpeedY(0);
                charGrounded = true;
                charJumping = false;
                offY = 0;

            } else {
                charGrounded = false;
            }
        }

        offY += getObjectSpeedY();
        if (getObjectSpeedY() > 0 || getObjectSpeedY() < 0 || !charGrounded) {

            setObjectSpeedY(getObjectSpeedY() + (gravity * elapsedTime));
        }

        //End of jumping and falling.

        //Right and left movement.
        //Going left
        if (!goingRight) {

            offX -= (getObjectSpeedX() * elapsedTime);
            if (mg.getCollision(tileX - 1, tileY) && offX < 0) {
                //System.out.println("colliding left: ");
                offX = 0;
                goingRight = true;
            }
        }

        //Going right
        if (goingRight) {

            offX += (getObjectSpeedX() * elapsedTime);
            if (mg.getCollision(tileX + 1, tileY) && offX > 0) {
                //System.out.println("colliding right: ");
                offX = 0;
                goingRight = false;
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
    }

    @Override
    public void render(GraphicsContext gc, Scene scene, MainGame mg) {
        update(scene, mg);
        if (goingRight) {
            gc.drawImage(image, imageSrcPos[0], 0, frameWidth, frameHeight, getPositionX(), getPositionY(), Level.TILESIZE, Level.TILESIZE);
        } else {
            gc.drawImage(image, imageSrcPos[0], 0, frameWidth, frameHeight, getPositionX() + frameWidth, getPositionY(), -Level.TILESIZE, Level.TILESIZE);
        }

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

    public boolean isCharJumping() {
        return charJumping;
    }

    public void setCharJumping(boolean charJumping) {
        this.charJumping = charJumping;
    }

    public boolean isCharGrounded() {
        return charGrounded;
    }

    public void setCharGrounded(boolean charGrounded) {
        this.charGrounded = charGrounded;
    }
}//End of class.

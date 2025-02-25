package bauwerk78.model.enemies;

import bauwerk78.implementer.MainGame;
import bauwerk78.tools.CharacterAnimation;
import bauwerk78.tools.Delayer;
import bauwerk78.tools.Randomize;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import bauwerk78.model.GameObject;
import bauwerk78.model.Level;

import static bauwerk78.model.Level.TILESIZE;

public class Santa extends GameObject implements Randomize {

/*    private double positionX;
    private double positionY;*/

    private final double gravity = 20;
    private final double jumpHeight = -9;

    //Using a constant since elapsed time resulted in jerky movement.
    private final double elapsedTime = 0.015;


    private Delayer delayer;

    //private Image image;
    private CharacterAnimation walkingAnimation = new CharacterAnimation("Images/Enemies/MidgetSanta/Walking/", "", "png", 13, 0.1, 64, 64);
    private CharacterAnimation jumpingAnimation = new CharacterAnimation("Images/Enemies/MidgetSanta/Jumping/", "", "png", 16, 0.1, 64, 64);
    private int frameHeight = 72;
    private int frameWidth = 64;
    private int imageWidth = 704;
    private int currentFrame = 0;
    private int[] imageSrcPos = new int[imageWidth / frameWidth];

    private boolean goingRight = true;
    private boolean standingStill = false;
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
        //image = new Image("file:Images/Santa.png");
        setFrameSrcPositions();
        setObjectSpeedX(100);
        setObjectSpeedY(0);
    }

    private void setFrameSrcPositions() {
        for (int i = 0; i < imageSrcPos.length; i++) {
            imageSrcPos[i] = i * frameWidth;
        }
    }

    private void checkJumpingConditions(MainGame mg) {
        if (goingRight && charGrounded && !(tileY - 3 <= 0 | tileY - 2 <= 0 | tileY - 1 <= 0 | tileY <= 0) &&
                !mg.getCollisions(tileX, tileY - 1)) {

            //If you are going off the map going right.
            if (tileX + 1 >= Level.levelW - 1) {
                goingRight = false;
                return;
            }

            if (mg.getCollisions(tileX + 1, tileY)) {
                charJumping = Randomize.randBoolean();
                if (!charJumping) {
                    goingRight = false;
                }
                return;
            }

            if (!mg.getCollisions(tileX + 1, tileY + 1)) {
                charJumping = Randomize.randBoolean();
            }

        }


        if (!goingRight && charGrounded && !(tileY - 3 <= 0 | tileY - 2 <= 0 | tileY - 1 <= 0 | tileY <= 0) &&
                !mg.getCollisions(tileX, tileY - 1)) {

            //If you are going off the map going left.
            if (tileX - 1 <= 0) {
                goingRight = true;
                return;
            }

            if (mg.getCollisions(tileX - 1, tileY)) {
                charJumping = Randomize.randBoolean();
                if (!charJumping) {
                    goingRight = true;
                }
                return;
            }
            // && offX == offXMaxLeft
            if (!mg.getCollisions(tileX - 1, tileY + 1)) {
                charJumping = Randomize.randBoolean();
            }

        }
    }

    @Override
    protected void update(Scene scene, MainGame mg) {

        checkJumpingConditions(mg);

        //Jumping
        if (charJumping && charGrounded) {
            setObjectSpeedY(getObjectSpeedY() + jumpHeight);
            charGrounded = false;
        }

        //Colliding upwards
        if (getObjectSpeedY() < 0) {
            if (charJumping) {
                //System.out.println("colliding up?");
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
        }

        //Going right
        if (goingRight) {
            offX += (getObjectSpeedX() * elapsedTime);
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
/*        if (goingRight) {
            gc.drawImage(image, imageSrcPos[0], 0, frameWidth, frameHeight, getPositionX(), getPositionY(), Level.TILESIZE, Level.TILESIZE);
        } else {
            gc.drawImage(image, imageSrcPos[0], 0, frameWidth, frameHeight, getPositionX() + frameWidth, getPositionY(), -Level.TILESIZE, Level.TILESIZE);
        }*/
        if(charGrounded) {
            walkingAnimation.walking(goingRight, gc, getPositionX(), getPositionY());
        }
        if(charJumping || !charGrounded) {
            jumpingAnimation.jumping(goingRight, charJumping, gc, getPositionX(), getPositionY());
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

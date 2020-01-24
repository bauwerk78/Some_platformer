package se.lexicon.lars.model;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import se.lexicon.lars.implementer.MainGame;
import se.lexicon.lars.tools.Delayer;

import java.util.Arrays;

public class Grenade extends GameObject {

    private final double fakeDeltaTime = 0.015;

    private Delayer delayer = new Delayer();
    private GrenadeExplosion grenadeExplosion;
    private Image image;

    private double gravity = 300;
    private double throwHeight = -150;
    private double lastPosX;
    private boolean goingRight;
    private boolean grenadeThrown;
    private boolean exploded;
    private boolean renderExplosion;
    private boolean done;
    private boolean grounded;
    private Object[][] collideAbles = new Object[4][2];

    public Grenade(double posX, double posY) {
        super(posX, posY);
    }

    public Grenade(double posX, double posY, boolean direction) {
        this(posX, posY);
        this.goingRight = direction;
        init();
    }

    @Override
    protected void init() {
        setObjectWidth(20);
        setObjectHeight(20);
        setObjectSpeedX(300);
        setObjectSpeedY(throwHeight);
        image = new Image("file:Images/pixel_grenade_by_darkarts33_d4vigez-fullview.png");
        lastPosX = getPositionX();

    }

    private void checkCollideAbles(MainGame mg) {
        //Left
        collideAbles[0][0] = mg.getCollisions((getPositionX() / Level.TILESIZE) - 1, (getPositionY() + getObjectHeight()) / Level.TILESIZE);
        if ((boolean) collideAbles[0][0]) {
            collideAbles[0][1] = Math.ceil((getPositionX() / Level.TILESIZE) - 1) * (Level.TILESIZE);
            //System.out.println("colliding left: " + (double)collideAbles[0][1]);
        }

        //Right
        collideAbles[1][0] = mg.getCollisions(((getPositionX() + getObjectWidth()) / Level.TILESIZE) + 1, (getPositionY() + getObjectHeight()) / Level.TILESIZE);
        if ((boolean) collideAbles[1][0]) {
            collideAbles[1][1] = Math.floor((getPositionX() + getObjectWidth()) / (Level.TILESIZE) + 1) * (Level.TILESIZE);
            //System.out.println("colliding right: " + (double)collideAbles[1][1]);
        }

        //Up
        collideAbles[2][0] = mg.getCollisions((getPositionX() / Level.TILESIZE), (getPositionY() + getObjectHeight()) / Level.TILESIZE - 1);
        //Down
        collideAbles[3][0] = mg.getCollisions((getPositionX() / Level.TILESIZE), (getPositionY() + getObjectHeight()) / Level.TILESIZE + 1);
        if ((boolean) collideAbles[3][0]) {
            collideAbles[3][1] = Math.floor((getPositionY()) / (Level.TILESIZE) + 1) * (Level.TILESIZE);
            //System.out.println("colliding down: " + (double) collideAbles[3][1]);
        }

    }

    @Override
    protected void update(Scene scene, MainGame mg) {


        //Collision detection begins.
        checkCollideAbles(mg);
        //System.out.println(goingRight);
        if (goingRight) {
            if ((boolean) collideAbles[1][0] && getPositionX() + getObjectWidth() > (double)collideAbles[1][1] - 2) {
                System.out.println("yes");
                goingRight = false;
                //448X första höger maxvärde före kollision.
/*                if(getPositionX() > (double)collideAbles[1][1]) {

                    //System.out.println((double)collideAbles[1][1]);
                    setPositionX((double)collideAbles[1][1]);
                    goingRight = false;
                }*/

                //System.out.println("collidedposx going right : " + (mg.getCollidedPosX() - 1));
                //System.out.println("going right triggered!");



            } else {
                setPositionX(getPositionX() + (getObjectSpeedX() * fakeDeltaTime));
                //setPositionY(getPositionY() + (getObjectSpeedY() * fakeDeltaTime));
            }
        }


        if (!goingRight) {
            if ((boolean) collideAbles[0][0] && getPositionX() < (double)collideAbles[0][1] + 2) {
                //System.out.println("going left triggered!");
                System.out.println("no");
                    goingRight = true;

            } else {
                setPositionX(getPositionX() - (getObjectSpeedX() * fakeDeltaTime));
                //setPositionY(getPositionY() + (getObjectSpeedY() * fakeDeltaTime));
            }
        }
        //Colliding down.
        if (getObjectSpeedY() > 0) {
            if ((mg.getCollisions(Math.floor(getPositionX() + getObjectWidth()) / Level.TILESIZE, Math.floor(getPositionY() + getObjectHeight()) / (Level.TILESIZE))
                    || (mg.getCollisions(Math.ceil(getPositionX() + getObjectWidth()) / Level.TILESIZE, Math.floor(getPositionY() + getObjectHeight()) / (Level.TILESIZE))))) {

                //Set half of both Y and X speed when bouncing on floor.
                if (getObjectSpeedY() > 0) {
                    setObjectSpeedY(-(getObjectSpeedY() / 2));
                    //System.out.println("speedY: " + Math.abs(getObjectSpeedY()));
                }
                if (getObjectSpeedX() > 0) {
                    setObjectSpeedX((getObjectSpeedX() / 2));
                    //System.out.println("speedX: " + Math.abs(getObjectSpeedX()));
                }

            } else {
                setPositionY(getPositionY() + (getObjectSpeedY() * fakeDeltaTime));
            }
        } /*else {
            setPositionY(getPositionY() + (getObjectSpeedY() * fakeDeltaTime));
        }
*/
        //Colliding up
        if (getObjectSpeedY() < 0) {
            if ((mg.getCollisions(Math.floor(getPositionX()) / Level.TILESIZE, Math.floor(getPositionY()) / (Level.TILESIZE))
                    || (mg.getCollisions(Math.ceil(getPositionX()) / Level.TILESIZE, Math.floor(getPositionY()) / (Level.TILESIZE))))) {
                //System.out.println("colliding up");
                //System.out.println(Math.abs(getObjectSpeedY()));
                setObjectSpeedY(Math.abs(getObjectSpeedY()));
            } else {
                setPositionY(getPositionY() + (getObjectSpeedY() * fakeDeltaTime));
            }
        }

        //Explosion
        if (!exploded) {
            exploded = delayer.delayTimer(3);
        }

        if (exploded & !renderExplosion) {
            grenadeExplosion = new GrenadeExplosion(getPositionX(), getPositionY(), goingRight, getObjectWidth(), getObjectHeight());
            renderExplosion = true;
        }

        setObjectSpeedX(getObjectSpeedX() / 1.01);
        setObjectSpeedY(getObjectSpeedY() + (gravity * fakeDeltaTime));
    }


    @Override
    protected void render(GraphicsContext gc, Scene scene, MainGame mg) {
        update(scene, mg);
        if (renderExplosion) {
            if (!grenadeExplosion.isExplosionRendered()) {
                grenadeExplosion.render(gc, scene, mg);
            } else {
                done = true;
            }
        }

        if (!renderExplosion && !done) {
            //gc.setFill(Color.DARKGREEN);
            //gc.fillOval(getPositionX(), getPositionY(), getObjectWidth(), getObjectHeight());
            gc.drawImage(image, getPositionX(), getPositionY(), Level.TILESIZE / 2d - 10, Level.TILESIZE / 2d - 10);
            //gc.fillRect(getPositionX(), getPositionY(), getObjectWidth(), getObjectHeight());
        }
    }

    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    public boolean isRenderExplosion() {
        return renderExplosion;
    }

    public void setRenderExplosion(boolean renderExplosion) {
        this.renderExplosion = renderExplosion;
    }

    public boolean isGoingRight() {
        return goingRight;
    }

    public void setGoingRight(boolean goingRight) {
        this.goingRight = goingRight;
    }

    public boolean isDone() {
        return done;
    }
}

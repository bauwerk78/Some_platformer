package se.lexicon.lars.model;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import se.lexicon.lars.implementer.MainGame;
import se.lexicon.lars.tools.Delayer;

import java.util.ArrayList;
import java.util.List;

public class Grenade extends GameObject {

    private final double fakeDeltaTime = 0.015;

    private Delayer delayer = new Delayer();
    private GrenadeExplosion grenadeExplosion;
    private Image image;

    private double gravity = 300;
    private double throwHeight = -150;
    private boolean goingRight;
    private boolean grenadeThrown;
    private boolean exploded;
    private boolean renderExplosion;
    private boolean done;
    private boolean collided;

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
    }

    @Override
    protected void update(Scene scene, MainGame mg) {

        //Collision detection.
        //System.out.println(Math.floor(((getPositionX() + getObjectWidth()) / (Level.TILESIZE) + 1) * Level.TILESIZE));
        if (goingRight) {
            //TODO test with && if getpositionx and y are further right or down than the tile it is colliding with.
            if (mg.getCollisions((getPositionX() + getObjectWidth()) / (Level.TILESIZE) + 1, (getPositionY() + getObjectHeight()) / Level.TILESIZE)
                    && getPositionX() + getObjectWidth() >= mg.getCollidedPosX() - 1) {
                System.out.println("collidedposx going right : " + (mg.getCollidedPosX() - 1));
                collided = true;
                goingRight = false;
            } else {
                setPositionX(getPositionX() + (getObjectSpeedX() * fakeDeltaTime));
                //setPositionY(getPositionY() + (getObjectSpeedY() * fakeDeltaTime));
            }
        }

        if (!goingRight) {
            //TODO try subtracting the whole levelsize in combination with the collding position.
            if (mg.getCollisions((getPositionX() / Level.TILESIZE) - 1, (getPositionY() + getObjectHeight()) / Level.TILESIZE)) {
                if (getPositionX() >= mg.getCollidedPosX() + Level.TILESIZE + 1) {
                    setPositionX(getPositionX() - (getObjectSpeedX() * fakeDeltaTime));
                } else {
                    System.out.println("collidedposx going left :  " + (mg.getCollidedPosX() + Level.TILESIZE + 1));
                    collided = true;
                    goingRight = true;
                }

            } else {
                setPositionX(getPositionX() - (getObjectSpeedX() * fakeDeltaTime));
                //setPositionY(getPositionY() + (getObjectSpeedY() * fakeDeltaTime));
            }
        }
        //TODO for all these checks, make sure you are actually checking one tile down, left or right, so it's not two tiles or more.
        if (getObjectSpeedY() > 0) {
            if (mg.getCollisions((getPositionX() + getObjectWidth()) / (Level.TILESIZE), (getPositionY() + getObjectHeight()) / (Level.TILESIZE) + 1)) {
                System.out.println("grenade position?: " + (getPositionY() + getObjectHeight()));
                if (getPositionY() + getObjectHeight() < mg.getCollidedPosY() - 1) {
                    setPositionY(getPositionY() + (getObjectSpeedY() * fakeDeltaTime));
                    System.out.println("hello?!?!");
                } else {
                    System.out.println("colliding down: ");
                    setObjectSpeedY(0);
                }

            } else {
                setPositionY(getPositionY() + (getObjectSpeedY() * fakeDeltaTime));
            }
        } else {
            setPositionY(getPositionY() + (getObjectSpeedY() * fakeDeltaTime));
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

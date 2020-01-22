package se.lexicon.lars.model;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import se.lexicon.lars.implementer.MainGame;
import se.lexicon.lars.tools.Delayer;

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

    @Override
    protected void update(Scene scene, MainGame mg) {


        //TODO perhaps initialize speedx as either positive or negative?!?!
        //TODO might have to do a "full" else extra here on both right and left to get movement if there is no else in this statements.
        //Collision detection.
        if (goingRight) {
            if (mg.getCollisions((getPositionX() + getObjectWidth()) / (Level.TILESIZE), (getPositionY() + getObjectHeight()) / Level.TILESIZE)) {
                //System.out.println("collidedposx going right : " + (mg.getCollidedPosX() - 1));
                //System.out.println("going right triggered!");

                goingRight = false;

            } else {
                setPositionX(getPositionX() + (getObjectSpeedX() * fakeDeltaTime));
                //setPositionY(getPositionY() + (getObjectSpeedY() * fakeDeltaTime));
            }
        }


        if (!goingRight) {
            if (mg.getCollisions((getPositionX() / Level.TILESIZE), (getPositionY() + getObjectHeight()) / Level.TILESIZE)) {
                //System.out.println("going left triggered!");

                goingRight = true;

            } else {
                setPositionX(getPositionX() - (getObjectSpeedX() * fakeDeltaTime));
                //setPositionY(getPositionY() + (getObjectSpeedY() * fakeDeltaTime));
            }
        }
        //Colliding down.
        if (getObjectSpeedY() >= 0) {
            if ((mg.getCollisions(Math.floor(getPositionX() + getObjectWidth()) / Level.TILESIZE, Math.floor(getPositionY() + getObjectHeight()) / (Level.TILESIZE))
                    || (mg.getCollisions(Math.ceil(getPositionX() + getObjectWidth()) / Level.TILESIZE, Math.floor(getPositionY() + getObjectHeight()) / (Level.TILESIZE))))) {

                //grounded = true;
/*                if(getPositionY() + getObjectHeight() > Math.floor(getPositionY() + getObjectHeight()) / (Level.TILESIZE) * Level.TILESIZE) {
                    setPositionY(getPositionY() - 2);
                    grounded = true;
                    setObjectSpeedY(0);
                }*/
                //Set half of both Y and X speed when bouncing on floor.
                if (getObjectSpeedY() > 0) {
                    setObjectSpeedY(-(getObjectSpeedY() / 2));
                    System.out.println("speedY: " + Math.abs(getObjectSpeedY()));
                }
                //TODO ????
                if (getObjectSpeedX() > 0) {
                    setObjectSpeedX((getObjectSpeedX() / 2));
                    //System.out.println("speedX: " + Math.abs(getObjectSpeedX()));
                } else {
                    setObjectSpeedX((getObjectSpeedX() / 2));
                }

            } else {
                setPositionY(getPositionY() + (getObjectSpeedY() * fakeDeltaTime));
            }
        } else {
            setPositionY(getPositionY() + (getObjectSpeedY() * fakeDeltaTime));
        }

        //Colliding up
        if (getObjectSpeedY() < 0) {
            if ((mg.getCollisions(Math.floor(getPositionX()) / Level.TILESIZE, Math.floor(getPositionY()) / (Level.TILESIZE))
                    || (mg.getCollisions(Math.ceil(getPositionX()) / Level.TILESIZE, Math.floor(getPositionY()) / (Level.TILESIZE))))) {
                //System.out.println("colliding up");
                //System.out.println(Math.abs(getObjectSpeedY()));
                setObjectSpeedY(Math.abs(getObjectSpeedY()));
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

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
        if (goingRight) {
            //TODO test with && if getpositionx and y are further right or down than the tile it is colliding with.
            if (mg.getCollisions((getPositionX() + getObjectWidth()) / Level.TILESIZE, (getPositionY() + getObjectHeight()) / Level.TILESIZE)) {
                double rangeX = getPositionX();
                double rangeY = getPositionY();

                collided = true;
                goingRight = false;
            } else {
                setPositionX(getPositionX() + (getObjectSpeedX() * fakeDeltaTime));
                setPositionY(getPositionY() + (getObjectSpeedY() * fakeDeltaTime));
            }
        }

        if (!goingRight) {
            if (mg.getCollisions(Math.floor(getPositionX() / Level.TILESIZE), Math.floor((getPositionY() + getObjectHeight()) / Level.TILESIZE))) {
                collided = true;
                goingRight = true;
            } else {
                setPositionX(getPositionX() - (getObjectSpeedX() * fakeDeltaTime));
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

package se.lexicon.lars.model;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import se.lexicon.lars.implementer.MainGame;
import se.lexicon.lars.tools.Delayer;

public class Grenade extends GameObject {

    private final double fakeDeltaTime = 0.015;

    private Delayer delayer = new Delayer();
    private GrenadeExplosion grenadeExplosion;

    private double gravity = 300;
    private double throwHeight = -150;
    private boolean goingRight;
    private boolean grenadeThrown;
    private boolean exploded;
    private boolean renderExplosion;
    private boolean explosionRendered;
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

    }

    @Override
    protected void update(Scene scene, MainGame mg) {
        if ((getPositionX() + getObjectWidth()) / Level.TILESIZE > Level.levelW - 1 || (getPositionX()) / Level.TILESIZE < 1 && !collided) {
            collided = true;
            return;
        }

        if (goingRight) {
            if(mg.getCollision(Math.floor(getPositionX() / Level.TILESIZE), Math.floor(getPositionY() / Level.TILESIZE))) {
                //System.out.println("collided right: ");
                collided =  true;
            } else {
                setPositionX(getPositionX() + (getObjectSpeedX() * fakeDeltaTime));
                setPositionY(getPositionY() + (getObjectSpeedY() * fakeDeltaTime));
            }
            //System.out.println("right triggered");

        }

        if (!goingRight) {
            if(mg.getCollision(Math.floor(getPositionX() / Level.TILESIZE), Math.floor(getPositionY() / Level.TILESIZE))) {
                //System.out.println("collided left: ");
                collided = true;
            } else {
                setPositionX(getPositionX() - (getObjectSpeedX() * fakeDeltaTime));
                setPositionY(getPositionY() + (getObjectSpeedY() * fakeDeltaTime));
            }
            //System.out.println("left triggered");

        }

        //System.out.println(getPositionX() + " : " + getPositionY());
        if(!exploded) {
            exploded =  delayer.delayTimer(3);

        }

        if(exploded & !renderExplosion & !explosionRendered) {
            System.out.println("helloooooooo");
            grenadeExplosion = new GrenadeExplosion(getPositionX(), getPositionY());
            renderExplosion = true;
        }
        setObjectSpeedX(getObjectSpeedX() / 1.01);
        setObjectSpeedY(getObjectSpeedY() + (gravity * fakeDeltaTime));

    }

    @Override
    protected void render(GraphicsContext gc, Scene scene, MainGame mg) {
        update(scene, mg);
        if(renderExplosion) {
            grenadeExplosion.render(gc,scene,mg);
        }

        gc.setFill(Color.DARKGREEN);
        gc.fillOval(getPositionX(), getPositionY(), getObjectWidth(), getObjectHeight());

    }

    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    public boolean isExplosionRendered() {
        return explosionRendered;
    }

    public void setExplosionRendered(boolean explosionRendered) {
        this.explosionRendered = explosionRendered;
    }

    public boolean isRenderExplosion() {
        return renderExplosion;
    }

    public void setRenderExplosion(boolean renderExplosion) {
        this.renderExplosion = renderExplosion;
    }
}

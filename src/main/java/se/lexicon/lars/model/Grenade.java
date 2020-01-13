package se.lexicon.lars.model;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import se.lexicon.lars.implementer.MainGame;

public class Grenade extends GameObject {

    private final double fakeDeltaTime = 0.015;

    private double gravity = 100;
    private double throwHeight = -50;
    private boolean goingRight;
    private boolean grenadeThrown = true;
    private boolean exploded;
    private boolean collided;

    public Grenade(double posX, double posY, boolean direction) {
        super(posX, posY);
        this.goingRight = direction;
        init();
    }

    @Override
    protected void init() {
        setObjectWidth(20);
        setObjectHeight(20);
        setObjectSpeedX(300);
        setObjectSpeedY(0);

    }

    @Override
    protected void update(Scene scene, MainGame mg) {
        if(grenadeThrown) {
            setObjectSpeedY(getObjectSpeedY() + throwHeight);
            grenadeThrown = false;
        }
        if ((getPositionX() + getObjectWidth()) / Level.TILESIZE > Level.levelW - 1 || (getPositionX()) / Level.TILESIZE < 1 && !collided) {
            collided = true;
            return;
        }

        if (goingRight) {
            if(mg.getCollision(Math.floor(getPositionX() / Level.TILESIZE), Math.floor(getPositionY() / Level.TILESIZE))) {
                System.out.println("collided right: ");
                collided =  true;
            } else {
                setPositionX(getPositionX() + (getObjectSpeedX() * fakeDeltaTime));
                setPositionY(getPositionY() + (getObjectSpeedY() * fakeDeltaTime));
            }
            //System.out.println("right triggered");

        }

        if (!goingRight) {
            if(mg.getCollision(Math.floor(getPositionX() / Level.TILESIZE), Math.floor(getPositionY() / Level.TILESIZE))) {
                System.out.println("collided left: ");
            } else {
                setPositionX(getPositionX() - (getObjectSpeedX() * fakeDeltaTime));
                setPositionY(getPositionY() + (getObjectSpeedY() * fakeDeltaTime));
            }
            //System.out.println("left triggered");

        }

        //System.out.println(getPositionX() + " : " + getPositionY());

        setObjectSpeedY(getObjectSpeedY() + (gravity * fakeDeltaTime));

    }

    @Override
    protected void render(GraphicsContext gc, Scene scene, MainGame mg) {
        update(scene, mg);
        gc.setFill(Color.DARKGREEN);
        gc.fillOval(getPositionX(), getPositionY(), getObjectWidth(), getObjectHeight());

    }
}

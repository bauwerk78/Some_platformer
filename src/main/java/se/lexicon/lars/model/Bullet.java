package se.lexicon.lars.model;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import se.lexicon.lars.implementer.MainGame;
import se.lexicon.lars.tools.Delayer;

public class Bullet extends GameObject {

    private static int bulletId = 0;

    private Image bullet = new Image("file:Images/Weapons/Bullets/bullet1.png");
    private boolean goingRight;
    private boolean collided;
    private boolean collidedRight;
    private boolean collidedDown;
    private double bulletSpeed;
    private final double fakeDeltaTime = 0.015;


    public Bullet() {
        init();
    }

    public Bullet(double posX, double posY, boolean direction) {
        super(posX, posY);
        this.goingRight = direction;
        init();
    }


    @Override
    protected void init() {
        bulletId++;
        setObjectWidth(10);
        setObjectHeight(10);
        bulletSpeed = 500;
    }

    private void reset() {
        bulletId = 0;
    }


    @Override
    protected void update(Scene scene, MainGame mg) {
        if ((getPositionX() + getObjectWidth()) / Level.TILESIZE > Level.levelW - 1 || (getPositionX()) / Level.TILESIZE < 1 && !collided) {
            collided = true;
            return;
        }

        if (goingRight && !collided) {
            if (mg.getCollision(Math.floor((getPositionX() + getObjectWidth()) / Level.TILESIZE),
                    Math.floor((getPositionY() + getObjectHeight() / 2) / Level.TILESIZE))) {
                //System.out.println("bullet colliding right");
                collided = true;
            } else {
                setPositionX(getPositionX() + bulletSpeed * fakeDeltaTime);
            }
        }

        if (!goingRight && !collided) {
            if (mg.getCollision(Math.floor((getPositionX()) / Level.TILESIZE),
                    Math.floor((getPositionY() + getObjectHeight() / 2) / Level.TILESIZE))) {
                //System.out.println("bullet colliding left");
                collided = true;
            } else {
                setPositionX(getPositionX() - bulletSpeed * fakeDeltaTime);
            }
        }


    }

    @Override
    protected void render(GraphicsContext gc, Scene scene, MainGame mg) {
        update(scene, mg);
        gc.drawImage(bullet, getPositionX(), getPositionY(), getObjectWidth(), getObjectHeight());
        /*gc.setFill(Color.GREENYELLOW);
        gc.fillRect(getPositionX(), getPositionY(), getObjectWidth(), getObjectHeight());*/
    }

    public static int getBulletId() {
        return bulletId;
    }

    public boolean isCollided() {
        return collided;
    }
}

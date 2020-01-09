package se.lexicon.lars.model;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import se.lexicon.lars.implementer.MainGame;

public class Bullet extends GameObject {

    private static int bulletId = 0;

    private boolean goingRight;
    private double bulletSpeed;


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
        bulletSpeed = 8;
    }

    @Override
    protected void update(Scene scene, MainGame mg) {
        if (goingRight) {
            setPositionX(getPositionX() + bulletSpeed);
        } else {
            setPositionX(getPositionX() - bulletSpeed);
        }

    }

    @Override
    protected void render(GraphicsContext gc, Scene scene, MainGame mg) {
        update(scene, mg);
        gc.setFill(Color.DARKRED);
        gc.fillRect(getPositionX(), getPositionY(), getObjectWidth(), getObjectHeight());
    }

    public static int getBulletId() {
        return bulletId;
    }

    public static void setBulletId(int bulletId) {
        Bullet.bulletId = bulletId;
    }
}

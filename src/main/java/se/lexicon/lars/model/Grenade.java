package se.lexicon.lars.model;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import se.lexicon.lars.implementer.MainGame;

public class Grenade extends GameObject {

    private double gravity = 50;
    private double throwHeight = 10;
    private boolean goingRight;
    private boolean exploded;

    @Override
    protected void init() {
        setObjectWidth(10);
        setObjectHeight(10);
        setObjectSpeedX(5);

    }

    @Override
    protected void update(Scene scene, MainGame mg) {

    }

    @Override
    protected void render(GraphicsContext gc, Scene scene, MainGame mg) {
        gc.setFill(Color.DARKGREEN);
        gc.fillOval(getPositionX(), getPositionY(), getObjectWidth(), getObjectHeight());

    }
}

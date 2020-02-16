package se.lexicon.lars.model;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import se.lexicon.lars.graphics.Renderer;
import se.lexicon.lars.implementer.MainGame;
import se.lexicon.lars.tools.Delayer;

public class Grenade extends GameObject {

    private final double fakeDeltaTime = 0.015;

    private Delayer delayer = new Delayer();
    private GrenadeExplosion grenadeExplosion;
    private Image image;

    private Rectangle2D leftRect;
    private Rectangle2D rightRect;
    private Rectangle2D uppRect;
    private Rectangle2D downRect;
    private double gravity = 300;
    private double throwHeight = -150;
    private double lastPosX;
    private boolean goingRight;
    private boolean grenadeThrown;
    private boolean exploded;
    private boolean renderExplosion;
    private boolean done;
    private boolean grounded;
    private boolean initXY = true;
    private Object[][] collideAbles = new Object[4][2];

    public Grenade(double posX, double posY) {
        super(posX, posY);
    }

    public Grenade(double posX, double posY, boolean direction) {
        this(posX, posY);
        this.goingRight = direction;
        init();
    }

    private void setRectangles(GraphicsContext gc) {
        gc.setFill(Color.RED);

        leftRect = new Rectangle2D(getPositionX(), getPositionY() + getObjectHeight() / 4, getObjectWidth() / 4, getObjectHeight() * 0.6);
        gc.fillRect(getPositionX(), getPositionY() + getObjectHeight() / 4, getObjectWidth() / 4, getObjectHeight() * 0.6);

        rightRect = new Rectangle2D(getPositionX() + getObjectWidth(), getPositionY() + getObjectHeight() / 4, getObjectWidth() / 4, getObjectHeight() * 0.6);
        gc.fillRect(getPositionX() + getObjectWidth(), getPositionY() + getObjectHeight() / 4, getObjectWidth() / 4, getObjectHeight() * 0.6);

        uppRect = new Rectangle2D(getPositionX() + getObjectWidth() / 4, getPositionY(), getObjectWidth() * 0.7, getObjectHeight() / 4);
        gc.fillRect(getPositionX() + getObjectWidth() / 4, getPositionY(), getObjectWidth() * 0.7, getObjectHeight() / 4);

        downRect = new Rectangle2D(getPositionX() + getObjectWidth() / 4, getPositionY() + getObjectHeight(), getObjectWidth() * 0.7, getObjectHeight() / 4);
        gc.fillRect(getPositionX() + getObjectWidth() / 4, getPositionY() + getObjectHeight(), getObjectWidth() * 0.7, getObjectHeight() / 4);
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
        if (mg.getCollisions((getPositionX() / Level.TILESIZE) - 1, (getPositionY() + getObjectHeight()) / Level.TILESIZE)) {
            collideAbles[0][0] = mg.getCollisions((getPositionX() / Level.TILESIZE) - 1, (getPositionY() + getObjectHeight()) / Level.TILESIZE);
            collideAbles[0][1] = Math.ceil((getPositionX() / Level.TILESIZE) - 1) * (Level.TILESIZE);
        } else if (mg.getCollisions((getPositionX() / Level.TILESIZE) - 1, (getPositionY()) / Level.TILESIZE)) {
            collideAbles[0][0] = mg.getCollisions((getPositionX() / Level.TILESIZE) - 1, (getPositionY()) / Level.TILESIZE);
            collideAbles[0][1] = Math.ceil((getPositionX() / Level.TILESIZE) - 1) * (Level.TILESIZE);
        } else {
            collideAbles[0][0] = mg.getCollisions((getPositionX() / Level.TILESIZE) - 1, (getPositionY()) / Level.TILESIZE);
        }

        //Right
        if (mg.getCollisions((getPositionX() / Level.TILESIZE) + 1, (getPositionY() + getObjectHeight()) / Level.TILESIZE)) {
            collideAbles[1][0] = mg.getCollisions((getPositionX() / Level.TILESIZE) + 1, (getPositionY() + getObjectHeight()) / Level.TILESIZE);
            collideAbles[1][1] = Math.floor((getPositionX() / Level.TILESIZE) + 1) * (Level.TILESIZE);
        } else if (mg.getCollisions((getPositionX() / Level.TILESIZE) + 1, (getPositionY()) / Level.TILESIZE)) {
            collideAbles[1][0] = mg.getCollisions((getPositionX() / Level.TILESIZE) + 1, (getPositionY()) / Level.TILESIZE);
            collideAbles[1][1] = Math.floor((getPositionX() / Level.TILESIZE) + 1) * (Level.TILESIZE);
        } else {
            collideAbles[1][0] = mg.getCollisions((getPositionX() / Level.TILESIZE) + 1, (getPositionY()) / Level.TILESIZE);
        }

        //Up
        if (mg.getCollisions(((getPositionX() / Level.TILESIZE)), (getPositionY()) / (Level.TILESIZE) - 1)) {
            collideAbles[2][0] = mg.getCollisions(((getPositionX() / Level.TILESIZE)), (getPositionY()) / (Level.TILESIZE) - 1);
            collideAbles[2][1] = Math.ceil((getPositionY()) / (Level.TILESIZE) - 1) * (Level.TILESIZE);
        } else if (mg.getCollisions(((getPositionX() + getObjectWidth()) / Level.TILESIZE), (getPositionY()) / (Level.TILESIZE) - 1)) {
            collideAbles[2][0] = mg.getCollisions(((getPositionX() + getObjectWidth()) / Level.TILESIZE), (getPositionY()) / (Level.TILESIZE) - 1);
            collideAbles[2][1] = Math.ceil((getPositionY()) / (Level.TILESIZE) - 1) * (Level.TILESIZE);
        } else {
            collideAbles[2][0] = mg.getCollisions(((getPositionX() / Level.TILESIZE)), (getPositionY()) / (Level.TILESIZE) - 1);
        }

        //Down
        if (mg.getCollisions((getPositionX() / Level.TILESIZE), (getPositionY()) / (Level.TILESIZE) + 1)) {
            collideAbles[3][0] = mg.getCollisions((getPositionX() / Level.TILESIZE), (getPositionY()) / (Level.TILESIZE) + 1);
            collideAbles[3][1] = Math.floor((getPositionY()) / (Level.TILESIZE) + 1) * (Level.TILESIZE);

        } else if (mg.getCollisions(((getPositionX() + getObjectWidth()) / Level.TILESIZE), (getPositionY()) / (Level.TILESIZE) + 1)) {
            collideAbles[3][0] = mg.getCollisions(((getPositionX() + getObjectWidth()) / Level.TILESIZE), (getPositionY()) / (Level.TILESIZE) + 1);
            collideAbles[3][1] = Math.floor((getPositionY()) / (Level.TILESIZE) + 1) * (Level.TILESIZE);
        } else {
            collideAbles[3][0] = mg.getCollisions(((getPositionX() / Level.TILESIZE)), (getPositionY()) / (Level.TILESIZE) + 1);
        }
    }

    @Override
    protected void update(Scene scene, MainGame mg) {

        //Collision detection begins.
        checkCollideAbles(mg);

        if (goingRight) {
            if ((boolean) collideAbles[1][0] && getPositionX() + getObjectWidth() > (double) collideAbles[1][1] - 5 || getPositionX() + getObjectWidth() > Level.levelW * Level.TILESIZE - Level.TILESIZE) {
                //setPositionX((double) collideAbles[1][1] - getObjectWidth() - 10);
                goingRight = false;
            } else {
                setPositionX(getPositionX() + (getObjectSpeedX() * fakeDeltaTime));
            }
        }

        if (!goingRight) {
            if ((boolean) collideAbles[0][0] && getPositionX() < (double) collideAbles[0][1] + 5  || getPositionX() < Level.TILESIZE) {
                goingRight = true;
            } else {
                setPositionX(getPositionX() - (getObjectSpeedX() * fakeDeltaTime));
            }
        }

        //Colliding down.
        if (getObjectSpeedY() > 0) {
            //System.out.println(getObjectSpeedY());
            if ((boolean) collideAbles[3][0] && getPositionY() + getObjectHeight() > (double) collideAbles[3][1] - 4) {
                if (getObjectSpeedY() > 0) {
                    setObjectSpeedY(-(getObjectSpeedY() / 2));
                }
                if (getObjectSpeedX() > 0) {
                    setObjectSpeedX((getObjectSpeedX() / 2));
                }
            } else {
                setPositionY(getPositionY() + (getObjectSpeedY() * fakeDeltaTime));
            }
        }

        //Colliding up
        if (getObjectSpeedY() < 0) {
            //System.out.println(getObjectSpeedY());
            if ((boolean) collideAbles[2][0] && getPositionY() < (double) collideAbles[2][1] + 2) {
                setObjectSpeedY(Math.abs(getObjectSpeedY()));
            } else {
                setPositionY(getPositionY() + (getObjectSpeedY() * fakeDeltaTime));
            }
        }

        //End of collision detection.

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
        //setRectangles(gc);
        update(scene, mg);
        if (renderExplosion) {
            if (!grenadeExplosion.isExplosionRendered()) {
                grenadeExplosion.render(gc, scene, mg);
            } else {
                done = true;
            }
        }

        if (!renderExplosion && !done) {
            gc.drawImage(image, getPositionX(), getPositionY(), Level.TILESIZE / 2d - 10, Level.TILESIZE / 2d - 10);
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

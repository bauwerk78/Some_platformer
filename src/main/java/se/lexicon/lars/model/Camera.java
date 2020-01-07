package se.lexicon.lars.model;

import javafx.scene.canvas.GraphicsContext;
import se.lexicon.lars.graphics.Renderer;
import se.lexicon.lars.implementer.MainGame;

public class Camera {

    private String targetId;
    private double positionX;
    private double positionY;
    private double offMaxX = (Level.levelW * Level.TILESIZE) / 2d;
    private double offMaxY = (Level.levelH * Level.TILESIZE) / 2d;
    private double offMinX = 0;
    private double offMinY = 0;
    private double offX;
    private double offY;
    private double levelSizeX = Level.levelW * Level.TILESIZE;
    private double levelSizeY = Level.levelH * Level.TILESIZE;
    private double camX;
    private double camY;




    public Camera() {
        init();
    }

    public void init() {
        this.targetId = "Player";

    }

    public void update(MainGame mg, PlayerCharacter player, GraphicsContext gc) {
        /*offX = (player.getPositionX() + player.getObjectWidth() / 2) - Renderer.windowWidth;
        offY = (player.getPositionY() + player.getObjectHeight() / 2) - Renderer.windowHeight;*/
        camX = player.getPositionX() - offMaxX;
        camY = player.getPositionY() - offMaxY;

        if(camX > offMaxX) {
            camX = offMaxX;
        }
        if (camX < offMinX) {
            camX = offMinX;
        }
        if(camY > offMaxY) {
            camY = offMaxY;
        }
        if(camY < offMinY) {
            camY = offMinY;
        }

/*        if(camX > offMaxX) {
            camX = offMaxX;
        } else if (camX < offMinX) {
            camX = offMinX;
        }
        if(camY > offMaxY) {
            camY = offMaxY;
        } else if(camY < offMinY) {
            camY = offMinY;
        }*/
        gc.translate(-camX, -camY);
        System.out.println("camX position: " + camX);

    }

    public double getOffMaxX() {
        return offMaxX;
    }

    public void setOffMaxX(double offMaxX) {
        this.offMaxX = offMaxX;
    }

    public double getOffMaxY() {
        return offMaxY;
    }

    public void setOffMaxY(double offMaxY) {
        this.offMaxY = offMaxY;
    }

    public double getOffMinX() {
        return offMinX;
    }

    public void setOffMinX(double offMinX) {
        this.offMinX = offMinX;
    }

    public double getOffMinY() {
        return offMinY;
    }

    public void setOffMinY(double offMinY) {
        this.offMinY = offMinY;
    }

    public double getCamX() {
        return camX;
    }

    public void setCamX(double camX) {
        this.camX = camX;
    }

    public double getCamY() {
        return camY;
    }

    public void setCamY(double camY) {
        this.camY = camY;
    }
}

package se.lexicon.lars.model;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {

    private double positionX = 0;
    private double positionY = 0;
    private double objectWidth;
    private double objectHeight;
    private double objectSpeedX;
    private double objectSpeedY;

    public GameObject(){}

    public GameObject(double positionX) {
        this.positionX = positionX;
    }

    public GameObject(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public GameObject(double positionY, double objectWidth, double objectHeight) {
        this.positionY = positionY;
        this.objectWidth = objectWidth;
        this.objectHeight = objectHeight;
    }

    public GameObject(double objectWidth, double objectHeight, double objectSpeedX, double objectSpeedY) {
        this.objectWidth = objectWidth;
        this.objectHeight = objectHeight;
        this.objectSpeedX = objectSpeedX;
        this.objectSpeedY = objectSpeedY;
    }

    public GameObject(double positionX, double positionY, double objectWidth, double objectHeight, double objectSpeedX, double objectSpeedY) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.objectWidth = objectWidth;
        this.objectHeight = objectHeight;
        this.objectSpeedX = objectSpeedX;
        this.objectSpeedY = objectSpeedY;
    }

    protected abstract void init();

    protected abstract void update();

    protected abstract void render(GraphicsContext gc);

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getObjectHeight() {
        return objectHeight;
    }

    public void setObjectHeight(double objectHeight) {
        this.objectHeight = objectHeight;
    }

    public double getObjectWidth() {
        return objectWidth;
    }

    public void setObjectWidth(double objectWidth) {
        this.objectWidth = objectWidth;
    }

    public double getObjectSpeedX() {
        return objectSpeedX;
    }

    public void setObjectSpeedX(double objectSpeedX) {
        this.objectSpeedX = objectSpeedX;
    }

    public double getObjectSpeedY() {
        return objectSpeedY;
    }

    public void setObjectSpeedY(double objectSpeedY) {
        this.objectSpeedY = objectSpeedY;
    }
}

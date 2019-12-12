package se.lexicon.lars.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PlayerCharacter extends GameObject {
    private Image image;

    public PlayerCharacter() {
        init();
    }

    public PlayerCharacter(double positionX, double positionY) {
        super(positionX, positionY);
        init();
    }

    @Override
    protected void init() {
        setObjectWidth(50);
        setObjectHeight(50);
        setImage();
    }

    @Override
    protected void update() {

    }

    @Override
    protected void render(GraphicsContext gc) {
        gc.drawImage(image, 100, 100, getObjectWidth(), getObjectHeight());
    }

    public void setImage() {
        image = new Image("file:Images/mario_rambo2.gif", getObjectWidth(), getObjectHeight(), false, false);
    }


}//End of class.


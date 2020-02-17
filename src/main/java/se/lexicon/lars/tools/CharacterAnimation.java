package se.lexicon.lars.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import se.lexicon.lars.model.Level;

import java.util.ArrayList;
import java.util.List;

public class CharacterAnimation {

    List<Image> imageList = new ArrayList<>();
    private Delayer delayer = new Delayer();
    private String path;
    private String fileName;
    private String fileExtension;
    private int numberOfImages;
    private int counter;
    private int currentFrame;
    private boolean renderNextFrame;
    private double timeToDelay = 0.1;


    public CharacterAnimation(String path, String fileName, String fileExtension, int numberOfImages) {
        this.path = path;
        this.fileName = fileName;
        this.numberOfImages = numberOfImages;
        this.fileExtension = fileExtension;
        importImages();

    }

    public void importImages() {
        for (int i = 0; i < numberOfImages; i++) {
            fileName = String.format("%03d", counter);
            imageList.add(new Image("file:" + path + fileName + "." + fileExtension));
            counter++;
        }
    }

    private void init() {

    }

    public void updateFrame() {

    }

    public void walking(boolean goingRight, GraphicsContext gc, double posX, double posY) {
        if (goingRight) {
            if (!renderNextFrame) {
                renderNextFrame = delayer.delayTimer(timeToDelay);
                if (renderNextFrame) {
                    renderNextFrame = false;
                    currentFrame++;
                    if (currentFrame == numberOfImages - 1) {
                        currentFrame = 0;
                    }
                }
                gc.drawImage(imageList.get(currentFrame), posX, posY, Level.TILESIZE, Level.TILESIZE);
            }

        }
        if (!goingRight) {
            if (!renderNextFrame) {
                renderNextFrame = delayer.delayTimer(timeToDelay);
                if (renderNextFrame) {
                    renderNextFrame = false;
                    currentFrame++;
                    if (currentFrame == numberOfImages - 1) {
                        currentFrame = 0;
                    }
                }
                gc.drawImage(imageList.get(currentFrame), posX + Level.TILESIZE, posY, -Level.TILESIZE, Level.TILESIZE);
            }

        }
    }

    public void jumping(boolean goingRight, boolean jumping, GraphicsContext gc, double posX, double posY) {
        if (goingRight) {
            if (!renderNextFrame) {
                renderNextFrame = delayer.delayTimer(timeToDelay);
                if (renderNextFrame) {
                    renderNextFrame = false;
                    currentFrame++;
                    if (currentFrame == numberOfImages - 1) {
                        currentFrame = 0;
                    }
                }
                gc.drawImage(imageList.get(currentFrame), posX, posY, Level.TILESIZE, Level.TILESIZE);
            }

        }
        if (!goingRight) {
            if (!renderNextFrame) {
                renderNextFrame = delayer.delayTimer(timeToDelay);
                if (renderNextFrame) {
                    renderNextFrame = false;
                    currentFrame++;
                    if (currentFrame == numberOfImages - 1) {
                        currentFrame = 0;
                    }
                }
                gc.drawImage(imageList.get(currentFrame), posX + Level.TILESIZE, posY, -Level.TILESIZE, Level.TILESIZE);
            }

        }

    }

    public void update(boolean goingRight, boolean standingStill, boolean jumping, double posX, double posY, GraphicsContext gc) {

    }


}//End of class

package bauwerk78.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import bauwerk78.model.Level;

import java.util.ArrayList;
import java.util.List;

public class CharacterAnimation {

    private List<Image> imageList = new ArrayList<>();
    private Image image;
    private ImageView imageView;
    private Delayer delayer = new Delayer();
    private String path;
    private String fileName;
    private String fileExtension;
    private int numberOfFrames;
    private int widthOfFrame;
    private int heightOfFrame;
    private int[] imageSrcPosX;
    private int counter;
    private int currentFrame;
    private boolean renderNextFrame;
    private boolean renderingImages;
    private boolean renderingFrames;
    private double timeToDelay = 0.1;
    private double drawSizeWidth;
    private double drawSizeHeight;


    public CharacterAnimation(String path, String fileName, String fileExtension, int numberOfFrames, double timeToDelay, double drawSizeWidth, double drawSizeHeight) {
        this.path = path;
        this.fileName = fileName;
        this.numberOfFrames = numberOfFrames;
        this.fileExtension = fileExtension;
        this.timeToDelay = timeToDelay;
        this.drawSizeWidth = drawSizeWidth;
        this.drawSizeHeight = drawSizeHeight;
        renderingImages = true;
        importImages();

    }

    public CharacterAnimation(String pathAndFileName, int numberOfFrames, int widthOfFrame, int heightOfFrame, double timeToDelay, double drawSizeWidth, double drawSizeHeight) {
        this.path = pathAndFileName;
        this.numberOfFrames = numberOfFrames;
        this.widthOfFrame = widthOfFrame;
        this.heightOfFrame = heightOfFrame;
        this.timeToDelay = timeToDelay;
        this.drawSizeWidth = drawSizeWidth;
        this.drawSizeHeight = drawSizeHeight;
        renderingFrames = true;
        image = new Image("file:" + pathAndFileName);
        imageView = new ImageView(image);
        setFrameSrcPositions();


    }

    public void importImages() {
        for (int i = 0; i < numberOfFrames; i++) {
            fileName = String.format("%03d", counter);
            imageList.add(new Image("file:" + path + fileName + "." + fileExtension));
            counter++;
        }
    }

    private void setFrameSrcPositions() {
        imageSrcPosX = new int[numberOfFrames];
        for (int i = 0; i < numberOfFrames; i++) {
            imageSrcPosX[i] = i * widthOfFrame;
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
                    if (currentFrame == numberOfFrames - 1) {
                        currentFrame = 0;
                    }
                }
                if (renderingImages) {
                    gc.drawImage(imageList.get(currentFrame), posX, posY, drawSizeWidth, drawSizeHeight);
                }
                if (renderingFrames) {

                    gc.drawImage(image, imageSrcPosX[currentFrame], 0, widthOfFrame, heightOfFrame,
                            posX, posY, drawSizeWidth, drawSizeHeight);
                }

            }

        }
        if (!goingRight) {
            if (!renderNextFrame) {
                renderNextFrame = delayer.delayTimer(timeToDelay);
                if (renderNextFrame) {
                    renderNextFrame = false;
                    currentFrame++;
                    if (currentFrame == numberOfFrames - 1) {
                        currentFrame = 0;
                    }
                }
                if (renderingImages) {
                    gc.drawImage(imageList.get(currentFrame), posX + Level.TILESIZE, posY, -drawSizeWidth, drawSizeHeight);
                }

                if (renderingFrames) {
                    gc.drawImage(image, imageSrcPosX[currentFrame], 0, widthOfFrame, heightOfFrame,
                            posX, posY, drawSizeWidth, drawSizeHeight);
                }

            }
        }
    }

    public void shooting(GraphicsContext gc, double posX, double posY) {
        if (!renderNextFrame) {
            System.out.println(currentFrame);
            renderNextFrame = delayer.delayTimer(timeToDelay);
            if (renderNextFrame) {
                renderNextFrame = false;
                currentFrame++;

                if (currentFrame == numberOfFrames - 1) {
                    currentFrame = 0;
                }
            }
            if (renderingImages) {
                gc.drawImage(imageList.get(currentFrame), posX, posY, drawSizeWidth, drawSizeHeight);
            }

            if (renderingFrames) {
                gc.drawImage(image, imageSrcPosX[currentFrame], 0, widthOfFrame, heightOfFrame,
                        posX, posY, drawSizeWidth, drawSizeHeight);
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
                    if (currentFrame == numberOfFrames - 1) {
                        currentFrame = 0;
                    }
                }
                if (renderingImages) {
                    gc.drawImage(imageList.get(currentFrame), posX, posY, drawSizeWidth, drawSizeHeight);
                }

                if (renderingFrames) {
                    gc.drawImage(image, imageSrcPosX[currentFrame], 0, widthOfFrame, heightOfFrame,
                            posX, posY, drawSizeWidth, drawSizeHeight);
                }

            }

        }
        if (!goingRight) {
            if (!renderNextFrame) {
                renderNextFrame = delayer.delayTimer(timeToDelay);
                if (renderNextFrame) {
                    renderNextFrame = false;
                    currentFrame++;
                    if (currentFrame == numberOfFrames - 1) {
                        currentFrame = 0;
                    }
                }
                if (renderingImages) {
                    gc.drawImage(imageList.get(currentFrame), posX + Level.TILESIZE, posY, -drawSizeWidth, drawSizeHeight);
                }
                if (renderingFrames) {
                    gc.drawImage(image, imageSrcPosX[currentFrame], 0, widthOfFrame, heightOfFrame,
                            posX, posY, drawSizeWidth, drawSizeHeight);
                }

            }

        }

    }

    public void update(boolean goingRight, boolean standingStill, boolean jumping, double posX, double posY, GraphicsContext gc) {

    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }
}//End of class

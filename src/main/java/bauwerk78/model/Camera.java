package bauwerk78.model;

import bauwerk78.implementer.MainGame;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;

public class Camera {

    private String targetId;
    private ParallelCamera camera;
    /*    private double fovX = (Level.levelW * Level.TILESIZE) / 2d;
        private double fovY = (Level.levelH * Level.TILESIZE) / 2d;*/
    private double fovX = (MainGame.windowWidth) / 2d;
    private double fovY = (MainGame.windowHeight) / 2d;
    private double camX = 0;
    private double camY = 0;
    private double targetX;
    private double targetY;
    private double camAccX = 0.1;
    private double camAccY = 0.1;
    private double scaling = 1;


    public Camera(Scene scene) {
        init(scene);
    }

    public void init(Scene scene) {
        this.targetId = "Player";
        createCamera(scene);

    }

    public void createCamera(Scene scene) {
        camera = new ParallelCamera();
        scene.setCamera(camera);
        camera.setScaleX(scaling);
        camera.setScaleY(scaling);
    }

    public void update(PlayerCharacter player) {
        targetX = (player.getPositionX() + player.getObjectWidth() / 2) - fovX;
        targetY = (player.getPositionY() + player.getObjectHeight() / 2) - fovY;

        //Adjusting so the camera don't show the outside of the level.
        if(targetX < 0) {
            targetX = 0;
        }
        if(targetX > (Level.levelW * Level.TILESIZE) - MainGame.windowWidth) {
            targetX = (Level.levelW * Level.TILESIZE) - MainGame.windowWidth;
        }

        if(targetY < 0) {
            targetY = 0;
        }
        if(targetY > (Level.levelH * Level.TILESIZE) - MainGame.windowHeight) {
            targetY = (Level.levelH * Level.TILESIZE) - MainGame.windowHeight;
        }

        //Lerp calculation for smoother camera movement.
        camX += (targetX - camX) * camAccX;
        camY += (targetY - camY) * camAccY;


        camera.relocate(camX, camY);
    }

    public double getCamX() {
        return camX;
    }

    public double getCamY() {
        return camY;
    }

    public double getTargetX() {
        return targetX;
    }

    public double getTargetY() {
        return targetY;
    }
}//End of class.

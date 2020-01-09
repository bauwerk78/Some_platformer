package se.lexicon.lars.model;

import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import se.lexicon.lars.graphics.Renderer;

public class Camera {

    private String targetId;
    private ParallelCamera camera;
    /*    private double fovX = (Level.levelW * Level.TILESIZE) / 2d;
        private double fovY = (Level.levelH * Level.TILESIZE) / 2d;*/
    private double fovX = (Renderer.windowWidth) / 2d;
    private double fovY = (Renderer.windowHeight) / 2d;
    private double camX = 0;
    private double camY = 0;
    private double targetX;
    private double targetY;
    private double camAccX = 0.1;
    private double camAccY = 0.1;


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
        //camera.setScaleX(2);
        //camera.setScaleY(2);
    }

    public void update(PlayerCharacter player) {
        targetX = (player.getPositionX() + player.getObjectWidth() / 2) - fovX;
        targetY = (player.getPositionY() + player.getObjectHeight() / 2) - fovY;

        //Lerp calculation for smoother camera movement.
        camX += (targetX - camX) * camAccX;
        camY += (targetY - camY) * camAccY;


        camera.relocate(camX, camY);
    }

}//End of class.

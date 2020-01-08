package se.lexicon.lars.model;

import javafx.scene.Group;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import se.lexicon.lars.graphics.Renderer;
import se.lexicon.lars.implementer.MainGame;

public class Camera {

    private String targetId;
    private ParallelCamera camera;
    private double positionX;
    private double positionY;
    private double levelSizeX = Level.levelW * Level.TILESIZE;
    private double levelSizeY = Level.levelH * Level.TILESIZE;
    private double fovX = levelSizeX / 2;
    private double fovY = levelSizeY / 2;
    private double offMaxX = levelSizeX - fovX;
    private double offMaxY = levelSizeY - fovY;
    private double offMinX = 0;
    private double offMinY = 0;
    private double offX;
    private double offY;
    private boolean parallel = true;
    private boolean init =  true;
    private double oldXPosition;

    private double camX = 0;
    private double camY = 0;


    public Camera(Group group, Scene scene, GraphicsContext gc) {
        init(group, scene, gc);
    }

    public void init(Group group, Scene scene, GraphicsContext gc) {
        this.targetId = "Player";
        createCamera(group, scene);
        if(!parallel) {
        }
    }

    public void createCamera(Group group, Scene scene) {
        camera = new ParallelCamera();
        //group.getChildren().add(camera);
        scene.setCamera(camera);



        /*camera.setLayoutX(levelSizeX);
        camera.setLayoutY(levelSizeY);*/

    }

    public void update(MainGame mg, PlayerCharacter player, GraphicsContext gc, Group group, Scene scene) {
        /*offX = (player.getPositionX() + player.getObjectWidth() / 2) - Renderer.windowWidth;
        offY = (player.getPositionY() + player.getObjectHeight() / 2) - Renderer.windowHeight;*/
        /*camX = player.getPositionX() - fovX;
        camY = player.getPositionY() - fovY;*/


        if(parallel) {
            camX = (player.getPositionX() + player.getObjectWidth() / 2) - fovX / 2;
            camY = (player.getPositionY() + player.getObjectHeight() / 2) - fovY / 2;
            camera.relocate(camX, camY);
            //camera.setTranslateX(camX);
            //System.out.println(camera.getLayoutBounds());

        }
        if (!parallel) {

/*            camX = (int) ((player.getPositionX()) - fovX / 2);
            camY = (int) ((player.getPositionY()) - fovY / 2);*/
/*            camX = (int) ((player.getPositionX()));
            camY = (int) ((player.getPositionY()));*/

            //System.out.println("offmaxx: " + offMaxX);
            //System.out.println("offminx: " + offMinX);

            if(player.getPositionX() > fovX) {
                camX = fovX;
                gc.translate(-fovX, 0);
            }

            if(player.getPositionX() < fovX) {
                camX = 0;
                gc.translate(camX, 0);
            }

            System.out.println(camX);
/*            if(camX < fovX / 2) {
                gc.translate(0,0);
            }
            if(camX > fovX / 2) {
                gc.translate(-10, 0);
            }*/
 /*           if (camX > offMaxX) {
                System.out.println("triggered");
                camX = (int) offMaxX;
            }
            if (camX < offMinX) {
                //System.out.println("triggered minx");
                camX = (int) offMinX;
            }
 *//*           if(camX > offMinX) {
                camX = 512;
            }*//*
            if (camY > offMaxY) {
                camY = (int) offMaxY;
            }
            if (camY < offMinY) {
                camY = (int) offMinY;
            }*/

            //gc.translate(-camX, 0);


            System.out.println();

        }

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

}

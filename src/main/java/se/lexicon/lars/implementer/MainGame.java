package se.lexicon.lars.implementer;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import se.lexicon.lars.model.Level;
import se.lexicon.lars.model.PlayerCharacter;

import static se.lexicon.lars.graphics.Renderer.*;
import static se.lexicon.lars.model.Level.*;

public class MainGame {

    private Level level;
    private PlayerCharacter player;
    private boolean playerCollided;
    private double newXPosition;
    private double newYPosition;
    private double oldXTile;
    private double oldYTile;
    private double collidingYPos;
    private double collidingXPos;

    public MainGame() {
        init();
    }

    public void init() {
        level = new Level();
        player = new PlayerCharacter();
    }

    //TODO checking one more position back so i can use 0 as 1 position wich makes it easier for collision detection.
    private boolean getCollision(double tileX, double tileY) {
        return level.getCollideAble(tileX, tileY);
    }

    //TODO, player can still dip into walls.
    private void handlePlayerCollision(int posX, int posY) {
        //Going right
        if (player.isGoingRight()) {
            if (getCollision(player.getTileX() + 1, player.getTileY())) {
                if (player.getPositionX() + player.getObjectWidth() >= (player.getTileX() + 1) * (TILESIZE)) {
                    //player.setPositionX((player.getPositionX() - TILESIZE));
                    newXPosition = windowWidth - (player.getTileX() + 1) * TILESIZE;
                    //System.out.println(newXPosition);
                    player.setPositionX(windowWidth - newXPosition - player.getObjectWidth());
                }
            }
        }
         //Going left
        //TODO, player seems to be drawn to end of the map when close.
        if (!player.isGoingRight()) {
            if (getCollision(player.getTileX() - 1, player.getTileY())) {
                System.out.println("colliding going left: " + player.getTileX());
                if(player.getPositionX() < (player.getTileX() - 1) * TILESIZE) {
                    player.setPositionX((player.getTileX() - 1) * TILESIZE);
                }
                //player.setPositionX(collidingXPos);
            }
        }

        //Going down
        if (!player.isPlayerGrounded()) {
            if (getCollision(player.getTileX(), player.getTileY() + 1)) {
                collidingYPos = (player.getTileY() + 1) * TILESIZE;
                System.out.println("colliding " + collidingYPos);
                if (player.getPositionY() + player.getObjectHeight() > (player.getTileY() + 1) * TILESIZE) {
                    newYPosition = collidingYPos;
                    player.setPositionY(newYPosition - TILESIZE);
                    player.setPlayerGrounded(true);
                    player.setPlayerJumping(false);
                    player.setVelocity(0);
                }
            }
        }

        if (!getCollision(player.getTileX(), player.getTileY() + 1)) {
            player.setPlayerGrounded(false);
        }
        //TODO broken....
        //Going up
        if(player.isPlayerJumping()) {
            System.out.println(player.getPositionY() + " : " + ((player.getTileY()) * TILESIZE));
             if (getCollision(player.getTileX(), player.getTileY() - 1)) {
                 oldXTile = player.getTileX();
                 oldYTile = player.getTileY() - 1;
                System.out.println("collision is true");
                //System.out.println(player.getPositionY() + " : " + (player.getTileY() - 1) * TILESIZE + TILESIZE);
                if (player.getPositionY() <= ((oldYTile) * TILESIZE)) {
                    if (oldYTile == 0) {
                        newYPosition = TILESIZE;
                    } else {
                        newYPosition = (oldYTile * TILESIZE) + TILESIZE;
                    }
                    player.setPositionY(newYPosition);
                    //player.setPlayerJumping(false);
                }
            }
        }
    }

    private void renderGame(GraphicsContext gc, Scene scene) {
        level.renderLevel(gc);
        player.render(gc, scene);
        //System.out.println(player.getTileX() + " : " + player.getTileY());
        //System.out.println(player.getPositionY() + " : " + ((player.getTileY()) * TILESIZE));
        //System.out.println(player.isPlayerGrounded());
        //System.out.println(player.getPositionX() + " : " + player.getPositionY());
        handlePlayerCollision((int) player.getTileX(), (int) player.getTileY());

 /*       if (handlePlayerCollision((int) player.getPositionX(), (int) player.getPositionY())) {

            playerCollided = true;
            return;
        }
        playerCollided = false;*/
    }

    public void information() {
        if (playerCollided) {
            System.out.println("player collided with object at position X: " + player.getPositionX() + " Y: " + player.getPositionY());
        } else {
            System.out.println("Current tileX: " + player.getTileX() + " tileY: " + player.getTileY());
        }


    }

    public void mainLoop(GraphicsContext gc, Scene scene) {
        renderGame(gc, scene);
        //information();
        //System.out.println("x " + player.getPositionX() + " y " + player.getPositionY());
    }
}

package src.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
    private static final float MOVEMENT_SPEED = 400;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final int minDistFromEdge;

    /**
     * Construct a new Paddle instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param inputListener     Key input listener to manage the game keys and function of the user.
     * @param windowDimensions      The game window's dimensions in x and y axes.
     * @param minDistFromEdge - The minimum distance from the window's edge for the paddle to stop.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions,
                  Renderable renderable, UserInputListener inputListener,
                  Vector2 windowDimensions, int minDistFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.minDistFromEdge = minDistFromEdge;

    }

    /**
     * Updates the paddle movement in the game.
     * @param deltaTime - Game clock
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            if (this.getTopLeftCorner().x() <= minDistFromEdge)
            {
                movementDir = new Vector2(0,0);
            }
            else {
                movementDir = movementDir.add(Vector2.LEFT);
            }
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (this.getTopLeftCorner().x() + this.getDimensions().x() >= windowDimensions.x() - minDistFromEdge)
            {
                movementDir = new Vector2(0,0);
            }
            else {
                movementDir = movementDir.add(Vector2.RIGHT);
            }
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }
}


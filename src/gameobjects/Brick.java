package src.gameobjects;

import src.brick_strategies.*;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Class Brick - responsible for managing the brick instances in the game - called when need to be created or removed.
 */
public class Brick extends GameObject {
    private CollisionStrategy collisionStrategy;
    private final Counter brickCounter;

    /**
     * Construct a new Brick instance.
     *  @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param strategy Game Collision Strategy of the brick - the strategy is to remove the brick when the ball
*                 collides with the specific brick.
     * @param brickCounter Counter instance for brick remaining in the game.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, CollisionStrategy strategy,
                 Counter brickCounter) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = strategy;
        this.brickCounter = brickCounter;
    }

    /**
     * Instructions for the brick - what to do in case of collisions with other Game Objects from the collection,
     * @param other - Other Game Object
     * @param collision - Collision instance - holds info about the specific collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision  collision) {
        super.onCollisionEnter(other, collision);
        this.collisionStrategy.onCollision(this, other, brickCounter);
    }
}

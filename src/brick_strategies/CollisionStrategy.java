package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * Class CollisionStrategy responsible for brick strategy when a collision with a ball occurs.
 * Makes the brick disappear.
 */
public class CollisionStrategy {
    private final GameObjectCollection gameObjectCollection;

    /**
     * Constructor for CollisionStrategy instance.
     * @param gameObjectCollection Total Game Objects collection in the Bricker Game.
     */
    public CollisionStrategy(GameObjectCollection gameObjectCollection) {
        this.gameObjectCollection = gameObjectCollection;
    };

    /**
     * Decides the strategy when we the ball is collided with the brick. When they collide the brick will disappear
     * from the screen.
     * @param collidedObj Brick Objects - collided object to remove from the screen.
     * @param colliderObj Ball object - collider object - it will not be removed.
     * @param bricksCounter Counter for bricks GameObjects remaining in the game.
     */
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        boolean brickRemoved = gameObjectCollection.removeGameObject(collidedObj);
        if (brickRemoved) {
            bricksCounter.decrement();
        }
    }



}

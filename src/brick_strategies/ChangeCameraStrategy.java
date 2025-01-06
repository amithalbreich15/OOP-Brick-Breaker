package src.brick_strategies;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Puck;

/**
 * ChangeCameraStrategy class - responsible for ChangeCameraStrategy kind of Brick CollisionStrategy which focuses the
 * camera on the Ball when hitting a brick with this type of Strategy.
 */
public class ChangeCameraStrategy extends CollisionStrategy {
    private final GameManager gameManager;
    private final WindowController windowController;

    /**
     * Constructor for ChangeCameraStrategy - creates a new instance of ChangeCameraStrategy
     * @param gameObjectCollection Total GameObjects in the Bricker Game.
     * @param gameManager GameManager instance.
     * @param windowController WindowController Instance - controls the window Behaviour.
     */
    public ChangeCameraStrategy(GameObjectCollection gameObjectCollection, GameManager gameManager,
                                WindowController windowController) {
        super(gameObjectCollection);
        this.gameManager = gameManager;
        this.windowController = windowController;
    }

    /**
     * Decides what to do when the ball collides the brick with the ChangeCameraStrategy - changes camera perspective to
     * trace the ball movement until 4 collisions of the ball with different GameObjects occurs.
     * @param collidedObj Brick Objects - collided object to remove from the screen.
     * @param colliderObj Ball object - collider object - it will not be removed.
     * @param bricksCounter Counter for bricks GameObjects remaining in the game.
     */
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        super.onCollision(collidedObj,colliderObj,bricksCounter);
    if (gameManager.getCamera() == null && !(colliderObj instanceof Puck) ) {
            gameManager.setCamera( new Camera( colliderObj, Vector2.ZERO,
                    windowController.getWindowDimensions().mult(1.2f),
                    windowController.getWindowDimensions()) );
        }
    }
}

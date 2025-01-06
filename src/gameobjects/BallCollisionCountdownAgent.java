package src.gameobjects;

import danogl.GameManager;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * BallCollisionCountdownAgent class - manages the ball countdown of 4 collisions and decides if the
 * camera effect should stop.
 */
public class BallCollisionCountdownAgent {
    private static final int MAX_COLLISIONS = 4;
    private final Ball ball;
    private Counter collisionCounter;
    private GameObjectCollection gameObjectCollection;


    /**
     * Constructor for BallCollisionCountdownAgent - manages the ball countdown of 4 collisions and decides if the
     * camera effect should stop.
     * @param ball - Ball
     * @param collisionCounter counts down the total number of collisions per ChangeCameraStrategy effect.
     * @param gameObjectCollection - Total GameObjects in the game.
     */
    public BallCollisionCountdownAgent(Ball ball, Counter collisionCounter, GameObjectCollection gameObjectCollection) {
        this.ball = ball;
        this.collisionCounter = collisionCounter;
        this.gameObjectCollection = gameObjectCollection;
    }

    /**
     * Check if camara effect of following the game Ball should stop after 4 collsions.
     * @param gameManager - GameManager instance.
     */
    public void checkStopCameraEffect(GameManager gameManager) {
        if (collisionCounter.value() == 1) {
            gameManager.setCamera(null);
            collisionCounter.increaseBy(MAX_COLLISIONS - 1);
        }
        else {
            collisionCounter.decrement();
        }
    }
}

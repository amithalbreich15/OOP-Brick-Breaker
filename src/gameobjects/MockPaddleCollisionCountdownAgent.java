package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * MockPaddleCollisionCountdownAgent class - manages the MockPaddle countdown of 3 collisions  with other
 * objects and decides if the MockPaddle should disappear.
 */
public class MockPaddleCollisionCountdownAgent {
    private static final int MAX_COLLISIONS = 3;
    private Counter collisionCounter;
    private GameObjectCollection gameObjectCollection;

    /**
     * @param collisionCounter Counts down the total number of collisions per MockPaddle instance.
     * @param gameObjectCollection - Total GameObjects in the bricker game.
     * @param paddleCounter Counts the number of MockPaddle on the  screen - shouldn't exceed 1 MockPaddle per screen.
     */
    public MockPaddleCollisionCountdownAgent(Counter collisionCounter,
                                       GameObjectCollection gameObjectCollection, Counter paddleCounter) {
        this.collisionCounter = collisionCounter;
        this.gameObjectCollection = gameObjectCollection;
    }

    /**
     *
     * @param mockPaddle MockPaddle instance.
     * @param other other GameObject instance.
     * @param paddleCounter Counts the number of MockPaddle on the  screen - shouldn't exceed 1 MockPaddle per screen.
     */
    public void checkShouldPaddleDisappear(GameObject mockPaddle, GameObject other, Counter paddleCounter) {
        if (collisionCounter.value() == 1) {
            gameObjectCollection.removeGameObject(mockPaddle);
            paddleCounter.decrement();
            collisionCounter.increaseBy(MAX_COLLISIONS - 1);
        }
        collisionCounter.decrement();
    }
}


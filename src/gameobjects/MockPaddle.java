package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Class MockPaddle -
 */
public class MockPaddle extends Paddle{
    private final int MAX_COLLISIONS = 3;
    private MockPaddleCollisionCountdownAgent mockPaddleAgent;
    private Counter collisionCounter;
    private Counter paddleCounter;

    /**
     * Construct a new MockPaddle instance.
     *  @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param inputListener     Key input listener to manage the game keys and function of the user.
     * @param windowDimensions      The game window's dimensions in x and y axes.
     * @param minDistFromEdge - The minimum distance from the window's edge for the paddle to stop.
     */
    public MockPaddle(Vector2 topLeftCorner, Vector2 dimensions,
                      Renderable renderable, UserInputListener inputListener,
                      Vector2 windowDimensions, int minDistFromEdge,
                      GameObjectCollection gameObjectCollection, Counter paddleCounter) {
        super(topLeftCorner, dimensions, renderable, inputListener,windowDimensions, minDistFromEdge);
        this.collisionCounter = new Counter(MAX_COLLISIONS);
        this.paddleCounter = paddleCounter;
        this.mockPaddleAgent = new MockPaddleCollisionCountdownAgent(collisionCounter,
                gameObjectCollection, paddleCounter);
    }

    /**
     * Updates the MockPaddle movement in the game.
     * @param deltaTime - Game clock
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    /**
     * Defines what needs to happen when the MockPaddle collides with other object. It will act like the regular
     * Paddle bt in addition it will ve removed from the screen after being hit 3 times by different GameObjects.
     * @param other other GameObject instance.
     * @param collision Collision instance.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        mockPaddleAgent.checkShouldPaddleDisappear(this,other, paddleCounter);

    }
}

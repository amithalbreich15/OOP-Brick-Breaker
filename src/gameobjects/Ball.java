package src.gameobjects;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Ball extends GameObject {
    private static final int MAX_COLLISIONS = 4;
    private final Sound collisionSound;
    private Counter collisionCounter;
    private BallCollisionCountdownAgent ballAgent;
    private GameObjectCollection gameObjectCollection;
    private GameManager gameManager;

    /**
     * Construct a new Ball instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param  collisionSound       Sound of the collision when ball collides with other Game Objects.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
    }

    /**
     * Construct a new Ball instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param  collisionSound       Sound of the collision when ball collides with other Game Objects.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound,
                GameObjectCollection gameObjectCollection, GameManager gameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjectCollection = gameObjectCollection;
        this.collisionSound = collisionSound;
        this.collisionCounter = new Counter(MAX_COLLISIONS);
        this.gameManager = gameManager;
        this.ballAgent = new BallCollisionCountdownAgent(this,collisionCounter,gameObjectCollection);
    }

    /**
     * Instructions for the ball - what to do in case of collisions with other Game Objects from the collection,
     * @param other - Other Game Object
     * @param collision - Collision instance - holds info about the specific collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collision.getNormal();
        Vector2 newVelocity = getVelocity().flipped(collision.getNormal());
        setVelocity(newVelocity);
        collisionSound.play();
        if (!(this instanceof Puck)) {
            ballAgent.checkStopCameraEffect(gameManager);
        }

    }
}

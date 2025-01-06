package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.brick_strategies.GetExtraLifeStrategy;

/**
 *
 */
public class ExtraLife extends GameObject {
    private GameObjectCollection gameObjectCollection;
    private final Vector2 windowDimensions;
    private GetExtraLifeStrategy getExtraLifeStrategy;

    /**
     * Construct a new ExtraLife instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public ExtraLife(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                     GameObjectCollection gameObjectCollection,GetExtraLifeStrategy extraLifeStrategy,
                     Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjectCollection = gameObjectCollection;
        this.getExtraLifeStrategy = extraLifeStrategy;
        this.windowDimensions = windowDimensions;
    }

    /**
     * Decides what needs to happen when ExtraLife object collides with the paddle. Once it will collide the ExtraLife
     * Object will be removed and GraphicLifeCounter and NumericLifeCounter will be updated accordingly with +1 life.
     * @param other other GameObject - should be main Paddle
     * @param collision Collision instance.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        getExtraLifeStrategy.onHeartPaddleCollision(this,other);
    }

    /**
     * Check which objects should collide with the ExtraLife object - only main paddle should be able to collide with
     * ExtraLife Object.
     * @param other other game object
     * @return true if the collidedObject is Paddle false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return (other instanceof Paddle) && !(other instanceof MockPaddle);
    }

    /**
     * Updates the Graphics of the ExtraLife falling heart game object - will be deleted from the screen if it reaches
     * the end of the screen y dimension coordinate.
     * @param deltaTime - Game Clock
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.getCenter().y() > this.windowDimensions.y()) {
            this.gameObjectCollection.removeGameObject(this);
        }
    }
}

package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.Layer;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Puck extends Ball{
    private final Sound collisionSound;

    /**
     * Construct a new Puck (MockBall) instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param  collisionSound       Sound of the collision when ball collides with other Game Objects.
     */
    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable, collisionSound);
        this.collisionSound = collisionSound;
    }

    /**
     * Instructions for the Puck Ball - what to do in case of collisions with other Game Objects from the collection,
     * @param other - Other Game Object
     * @param collision - Collision instance - holds info about the specific collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        collision.getNormal();
        Vector2 newVelocity = getVelocity().flipped(collision.getNormal());
        setVelocity(newVelocity);
        collisionSound.play();
    }
}

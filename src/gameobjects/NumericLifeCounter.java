package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.awt.Color;

/**
 * NumericLifeCounter class - Visual graphic number represents life counter - changing colors accordingly.
 * Also switching colors when life is reducing/increasing:
 *      * Green - 3  or 4 lives remaining
 *      * Yellow - 2 lives remaining
 *      * Red - 1 lives remaining
 */
public class NumericLifeCounter extends GameObject {
    private static final String FOUR_LIVES = "4";
    private static final String THREE_LIVES = "3";
    private static final String  TWO_LIVES = "2";
    private static final String ONE_LIVES = "1";
    private final Counter livesCounter;
    private final TextRenderable renderable;
    /**
     * Construct a new NumericLifeCounter instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param gameObjectCollection      The total Game Objects collection
     */
    public NumericLifeCounter(Counter livesCounter,Vector2 topLeftCorner,
                              Vector2 dimensions,
                              GameObjectCollection gameObjectCollection) {
        super(topLeftCorner,dimensions,null);
        TextRenderable textRenderable = new TextRenderable("3");
        this.renderer().setRenderable(textRenderable);
        this.renderable = textRenderable;
        this.livesCounter = livesCounter;
    }

    /**
     * Increments livesCounter by 1 at a time;
     * @return livesCounter value increased by 1.
     */
    public int incrementLivesCounter() { return livesCounter.value() + 1 ; }

    /**
     * Updates the NumericLifeCounter counter on board.
     * Counts down according to current lifeCount remaining for the user to play.
     * Also switching colors when life is reducing:
     * Green - 3 or 4 lives remaining
     * Yellow - 2 lives remaining
     * Red - 1 lives remaining
     * @param deltaTime - Game clock.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        int lifeCount = livesCounter.value();
        String numString = String.valueOf(lifeCount);
        this.renderable.setString(numString);
        switch (numString) {
            case FOUR_LIVES:
                this.renderable.setColor(Color.green);
            case THREE_LIVES:
                this.renderable.setColor(Color.green);
                break;
            case TWO_LIVES:
                this.renderable.setColor(Color.yellow);
                break;
            case ONE_LIVES:
                this.renderable.setColor(Color.red);
                break;
        }
    }
}

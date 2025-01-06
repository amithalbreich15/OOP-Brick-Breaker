package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.ExtraLife;
import src.gameobjects.GraphicLifeCounter;
import src.gameobjects.NumericLifeCounter;


/**
 * GetExtraLifeStrategy class - responsible for GetExtraLifeStrategy inherits of Brick CollisionStrategy.
 * When a brick carrying this strategy shatters - an ExtraLife heart will drop from the middle of the brick.
 * If the user will collect the ExtraLife falling from the brick with the main Paddle - he will get ExtraLife and
 * his livesCounter will be increased by 1. Maximum is 4 Lives.
 */
public class GetExtraLifeStrategy extends CollisionStrategy {
    private static final int HEART_WIDTH = 25;
    private static final int HEART_HEIGHT = 25;
    private static final float EXTRA_LIFE_SPEED = 100;
    private static final int MAX_LIVES_NUM = 4;
    private final GameObjectCollection gameObjectCollection;
    private final ImageReader imageReader;
    private GraphicLifeCounter graphicLifeCounter;
    private NumericLifeCounter numericLifeCounter;
    private Vector2 windowDimensions;

    /**
     * Constructor for GetExtraLifeStrategy instance - constructs a new ExtraLifeStrategy instance.
     * @param gameObjectCollection Total Game Objets in the Bricker Game.
     * @param imageReader - ImageReader instance to read the image of the ExtraLife heart symbol.
     * @param graphicLifeCounter GraphicLifeCounter instance to update
     * @param numericLifeCounter NumericLifeCounter instance to update
     * @param windowDimensions Window dimensions (x,y) coordinates (Height,Width).
     */
    public GetExtraLifeStrategy(GameObjectCollection gameObjectCollection, ImageReader imageReader,
                                GraphicLifeCounter graphicLifeCounter,
                                NumericLifeCounter numericLifeCounter, Vector2 windowDimensions) {
        super(gameObjectCollection);
        this.gameObjectCollection = gameObjectCollection;
        this.imageReader = imageReader;
        this.graphicLifeCounter = graphicLifeCounter;
        this.numericLifeCounter = numericLifeCounter;
        this.windowDimensions = windowDimensions;
    }

    /**
     * When a ball/puck collides a brick with GetExtraLifeStrategy a fallingLife object will fall from the middle of
     * the brick - life will increase by 1 if the user collects it with the main Paddle.
     * @param collidedObj Brick Objects - collided object to remove from the screen.
     * @param colliderObj Ball object - collider object - it will not be removed.
     * @param bricksCounter Counter for bricks GameObjects remaining in the game.
     */
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        super.onCollision(collidedObj,colliderObj, bricksCounter);
         createFallingLife(imageReader,collidedObj);
    }

    /**
     * This function defines what to do in case of collision between the falling heart ExtraLife object and the paddle.
     * When such collision occurs the GraphicLifeCounter and NumericLifeCounter will be updated and life will increase
     * by 1 for each collected ExtraLife object.
     * @param collidedObject ExtraLife falling heart object
     * @param colliderObject Paddle
     */
    public void onHeartPaddleCollision(GameObject collidedObject, GameObject colliderObject) {
        gameObjectCollection.removeGameObject(collidedObject);
        if (graphicLifeCounter.getLivesCounter().value() < MAX_LIVES_NUM) {
            graphicLifeCounter.incrementLivesCounter();
            numericLifeCounter.incrementLivesCounter();
        }
    }

    /**
     * Create Falling life ExtraLife object.
     * @param imageReader ImageReader instance to read the Heart image.
     * @param collidedObject Brick Object.
     */
    private void createFallingLife(ImageReader imageReader, GameObject collidedObject) {
        Renderable livesImage =
                imageReader.readImage("assets/heart.png", true);
        ExtraLife heartSymbol = new ExtraLife(
                new Vector2(collidedObject.getCenter().x() - (float)(HEART_WIDTH/2),collidedObject.getCenter().y())
                ,new Vector2(HEART_WIDTH, HEART_HEIGHT),livesImage,
                gameObjectCollection,this, windowDimensions);
        this.gameObjectCollection.addGameObject(heartSymbol);
        heartSymbol.setVelocity(new Vector2(0, EXTRA_LIFE_SPEED));
    }
}

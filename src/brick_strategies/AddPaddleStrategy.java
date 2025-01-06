package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.MockPaddle;

/**
 * Class for AddPaddleStrategy - Inherits from CollisionStrategy and as an extra creates a MockPaddle in the middle of
 * the screen.
 */
public class AddPaddleStrategy extends CollisionStrategy  {
    private static final int MIN_DIST_FROM_EDGE = 10;
    private static final int PADDLE_WIDTH = 120;
    private static final int PADDLE_HEIGHT = 20;
    private final GameObjectCollection gameObjectCollection;
    private MockPaddle mockPaddle;
    private final Vector2 windowDimensions;
    private final UserInputListener inputListener;
        private static final Counter paddleCounter = new Counter(0);

    /**
     * Constructor for AddPaddleStrategy instance.
     * @param gameObjectCollection Total Game Objects collection in the Bricker Game.
     * @param imageReader - Image reader instance
     * @param inputListener - Keyboard input listener.
     * @param windowDimensions - Thw window dimensions 2D Vector with x and y dimensions.
     */
    public AddPaddleStrategy(GameObjectCollection gameObjectCollection,
                             ImageReader imageReader, UserInputListener inputListener, Vector2 windowDimensions) {
        super(gameObjectCollection);
        this.windowDimensions = windowDimensions;
        this.gameObjectCollection = gameObjectCollection;
        this.inputListener = inputListener;
        if (paddleCounter.value() == 0 )
            this.mockPaddle = createMockPaddle(imageReader);
    };

    /**
     * Decides the strategy when we the ball is collided with the brick. When they collide the brick will disappear
     * from the screen and MockPaddle will appear in the middle of the screen.
     * @param collidedObj Brick Objects - collided object to remove from the screen.
     * @param colliderObj Ball object - collider object - it will not be removed.
     * @param bricksCounter Counter for bricks GameObjects remaining in the game.
     */
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        super.onCollision(collidedObj,colliderObj, bricksCounter);
        if (paddleCounter.value() == 0){
            gameObjectCollection.addGameObject(this.mockPaddle);
            paddleCounter.increment();
        }
    }

    /**
     * This Method creates the MockPaddle and place it on the screen.
     * @param imageReader - Image reader instance.
     * @return A MockPaddle instance to place in the middle of the screen.
     */
    private MockPaddle createMockPaddle(ImageReader imageReader) {
        Renderable mockPaddleImage = imageReader.readImage(
                "assets/botGood.png", false);
        return new MockPaddle(new Vector2(this.windowDimensions.x()/2,this.windowDimensions.y()/2),
                new Vector2(PADDLE_WIDTH,PADDLE_HEIGHT),
                mockPaddleImage, this.inputListener, this.windowDimensions, MIN_DIST_FROM_EDGE, gameObjectCollection,
                paddleCounter);
    }
}

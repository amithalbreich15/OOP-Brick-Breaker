package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Puck;

import java.util.Random;

/**
 * PuckStrategy cass- kind of CollisionStrategy and inherits from this class. When this strategy is activated, when
 * shattering a brick with this kind of strategy 3 Puck Objects will come out from the middle of the brick in different
 * Diagonals in 45 degrees.
 */
public class PuckStrategy extends CollisionStrategy {

    private static final int MOCK_BALLS_COUNT = 3;
    private static final float BALL_SPEED = 350;
    private final GameObjectCollection gameObjectCollection;
    private final ImageReader imageReader;
    private final SoundReader soundReader;

    /**
     * Constructor for PuckStrategy instance - constructs a new PuckStrategy instance
     * @param gameObjectCollection - Total Game Objets in the Bricker Game.
     * @param imageReader ImageReader instance to be able to read the image file.
     * @param soundReader Sound reader to read the sound wav file for the collision sound.
     */
    public PuckStrategy(GameObjectCollection gameObjectCollection, ImageReader imageReader, SoundReader soundReader) {
        super(gameObjectCollection);
        this.gameObjectCollection = gameObjectCollection;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
    }

    /**
     * This method inherits fron onCollision of CollisionStrategy - therefore the brick will shatter when the ball
     * hits the brick. In addition 3 pucks will come out from the middle of the hit brick.
     * @param collidedObj Brick Objects - collided object to remove from the screen.
     * @param colliderObj Ball object - collider object - it will not be removed.
     * @param bricksCounter Counter for bricks GameObjects remaining in the game.
     */
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        super.onCollision(collidedObj,colliderObj,bricksCounter);
        createMockBalls(imageReader,collidedObj,soundReader);


    }

    /**
     * This method creates the Puck balls and places it on the screen with given start speed in one of 4 Diagonals in
     * 45 degrees.
     * @param imageReader ImageReader instance to be able to read the image file.
     * @param collidedObj Collided Object - the brick object.
     * @param soundReader Sound reader to read the sound wav file for the collision sound.
     */
    private void createMockBalls(ImageReader imageReader, GameObject collidedObj, SoundReader soundReader) {
        Sound collisionSound = soundReader.readSound("assets/blop.wav");
        Renderable mockBallImage = imageReader.readImage(
                "assets/mockBall.png", true);
        float brickWidthX= collidedObj.getDimensions().x();
        // *** I created only 2 Puck Objects and removed up diagonals pucks creation so the game won't end too soon. ***
        for (int i = 1; i < MOCK_BALLS_COUNT; i++) {
            Puck mockBall = new Puck(new Vector2((collidedObj.getCenter().x()-brickWidthX/2)
                    + (i*brickWidthX)/3,collidedObj.getCenter().y()),
                    new Vector2(brickWidthX/3, brickWidthX/3),mockBallImage,collisionSound);
             // add option for up left ad right 45 degrees diagonals - comment out because game ends to soon.
            float ballVelX = BALL_SPEED;
            float ballVelY = BALL_SPEED;
//            Random rand = new Random();
//            if (rand.nextBoolean())
//                ballVelX *= -1;
//            if (rand.nextBoolean())
//                ballVelY *= -1;
            mockBall.setVelocity(new Vector2(ballVelX, ballVelY));
            gameObjectCollection.addGameObject(mockBall);

        }
    }
}

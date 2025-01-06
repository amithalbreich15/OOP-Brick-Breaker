package src;

import danogl.components.CoordinateSpace;
import src.brick_strategies.BrickStrategyFactory;
import src.brick_strategies.CollisionStrategy;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.*;

import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Class BrickerGameManager manages
 */
public class BrickerGameManager extends GameManager {
    private static final int MIN_DIST_FROM_EDGE = 10;
    private static final float MIN_NUM_COUNTER_DIST = 135;
    private static final int MIN_DIST_OF_HEART = 27;
    private static final int TARGET_FRAME_RATE = 80;
    private NumericLifeCounter numericLifeCounter;
    private static final int BORDER_WIDTH = 3;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_WIDTH = 120;
    private static final int BRICK_HEIGHT = 20;
    private static final int BRICK_WIDTH = 100;
    private static final int HEART_HEIGHT = 25;
    private static final int HEART_WIDTH = 25;
    private static final int BALL_RADIUS = 35;
    private static final float BALL_SPEED = 250;
    private static final int ROW_NUM = 7;
    private static final int COL_NUM = 8;
    private Counter brickCounter;

    private Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private final int numOfLives = 4;
    private Counter livesCounter;
    private UserInputListener inputListener;
    private GraphicLifeCounter graphicLifeCounter;
    private BrickStrategyFactory brickStrategyFactory;

    /**
     * The Bricker Game Manager constructor - constructs a new BrickerGameManager instance.
     * @param windowTitle Title of the window of the Game.
     * @param windowDimensions Windows dimensions in y and x axes.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    /**
     * Initializes the game with all required Game Objects and places them on the screen in correct areas.
     * @param imageReader reads image
     * @param soundReader reads sound
     * @param inputListener input listener for keyboard
     * @param windowController window Controller
     */
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {

        // initialize constructor fields
        this.brickCounter = new Counter(0);
        this.livesCounter = new Counter(numOfLives - 1);
        this.windowController = windowController;
        windowDimensions = windowController.getWindowDimensions();
        this.inputListener = inputListener;
        // Change frame rate to smoothen game graphics
        windowController.setTargetFramerate(TARGET_FRAME_RATE);

        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        // Create background
        createBackground(imageReader);

        // *** Create all Game Objects  *** //

        //create borders
        createBorders(windowDimensions);

        // Create ball
        createBall(imageReader, soundReader, windowController);

        // Create paddle
        Renderable paddleImage = imageReader.readImage(
                "assets/paddle.png", false);
        createPaddle(paddleImage, inputListener, windowDimensions);

        // Create Graphic Lives visualization
        createGraphicLives(imageReader);

        // create numericLifeCounter
        this.numericLifeCounter = new NumericLifeCounter(livesCounter,
                new Vector2(MIN_NUM_COUNTER_DIST,
                        windowDimensions.y() - 35), new Vector2(30, 30), gameObjects());
        this.numericLifeCounter.setCenter(
                new Vector2( MIN_NUM_COUNTER_DIST,windowDimensions.y() - 30));
        gameObjects().addGameObject(numericLifeCounter, Layer.UI);

        this.brickStrategyFactory = new BrickStrategyFactory( gameObjects()
                , imageReader, inputListener, windowDimensions, soundReader, this, windowController,
                graphicLifeCounter, numericLifeCounter, livesCounter);

        // Create all bricks with different strategies
        createAllBrick(imageReader,soundReader);

    }

    private void createBackground(ImageReader imageReader) {
        Renderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg",false);
        GameObject background = new GameObject(new Vector2(0,0), windowDimensions, backgroundImage);
        CoordinateSpace coordinateSpace = CoordinateSpace.CAMERA_COORDINATES;
        background.setCoordinateSpace(coordinateSpace);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /**
     * Updates the whole game.
     * @param deltaTime Game clock.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
    }

    /**
     * Checks if game should end. Logics for reset when the user loses life, creates prompt messages etc...
     */
    private void checkForGameEnd() {
        double ballHeight = ball.getCenter().y();
        String prompt = "";
        Counter brickCount =brickCounter;
        if(ballHeight > windowDimensions.y() ){
            this.livesCounter.decrement();
            ball.setCenter(windowDimensions.mult(0.5f));
            setBall();
        }
        if((ballHeight < windowDimensions.y() && ballHeight > 0 && this.brickCounter.value() == 0) ||
                inputListener.isKeyPressed(KeyEvent.VK_W)) {
            //we win
            prompt = "You win!";
        }
        if(ballHeight > windowDimensions.y() && this.livesCounter.value() == 0) {
            //we lost
            prompt = "You Lose!";
        }
        if(!prompt.isEmpty()) {
            prompt += " Play again?";
            if(windowController.openYesNoDialog(prompt))
                windowController.resetGame();
            else
                windowController.closeWindow();
        }
    }

    /**
     * Creates the ball.
     * @param imageReader Reads the image.
     * @param soundReader Reads the sound wav file.
     * @param windowController Window controller
     */
    private void createBall(ImageReader imageReader, SoundReader soundReader, WindowController windowController) {
        Renderable ballImage =
                imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop.wav");
        ball = new Ball(
                Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound, gameObjects(),this);

        Vector2 windowDimensions = windowController.getWindowDimensions();
        ball.setCenter(windowDimensions.mult(0.5f));
        gameObjects().addGameObject(ball);
        setBall();
    }

    /**
     * sets the ball in the center of the screen with initial velocity to different directions in 45 degrees in +-x
     * +-y axes.
     */
    private void setBall() {
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean())
            ballVelX *= -1;
        if (rand.nextBoolean())
            ballVelY *= -1;
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    /**
     * Creates all brick objects and add them to the screen.
     * @param imageReader Reads the image file.
     */
    private void createAllBrick(ImageReader imageReader, SoundReader soundReader) {
        Renderable brickImage =
                imageReader.readImage("assets/brick.png", false);
        for (int i = 0; i < ROW_NUM; i++) {
            for (int j = 0; j < COL_NUM; j++) {
                Random random = new Random();
                int randomStrategy = random.nextInt( 6);
                CollisionStrategy strategyType = brickStrategyFactory.buildStrategy(randomStrategy);
                Brick brick = new Brick(new Vector2( i*BRICK_WIDTH + 50,(j+2)*BRICK_HEIGHT),
                        new Vector2(BRICK_WIDTH, BRICK_HEIGHT), brickImage, strategyType, brickCounter);
                gameObjects().addGameObject(brick);
                brickCounter.increment();
            }
        }
    }

    /**
     * Creates GraphicLivesCounter on screen visualization.
     * @param imageReader Read the image
     */
    private void createGraphicLives(ImageReader imageReader) {
        Renderable livesImage =
                imageReader.readImage("assets/heart.png", true);
        this.graphicLifeCounter = new GraphicLifeCounter(
                new Vector2(MIN_DIST_OF_HEART, (int)windowDimensions.y()-40),
                new Vector2(HEART_WIDTH, HEART_HEIGHT),livesCounter, livesImage, gameObjects(), numOfLives);
        gameObjects().addGameObject(graphicLifeCounter,Layer.UI);
    }

    /**
     * Creates the paddle instance for the game.
     * @param paddleImage Image for paddle to print to the screen.
     * @param inputListener InputListener for keyboard keys.
     * @param windowDimensions Window Dimensions in x and y axes.
     */
    private void createPaddle(Renderable paddleImage, UserInputListener inputListener, Vector2 windowDimensions) {
        GameObject paddle = new Paddle(
                Vector2.ZERO,
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage,
                inputListener, windowDimensions, MIN_DIST_FROM_EDGE);

        paddle.setCenter(
                new Vector2(windowDimensions.x()/2, (int)windowDimensions.y()-30));
        gameObjects().addGameObject(paddle);
    }

    /**
     * Creating the 3 borders in the game - top-left-right borders.
     * Prevents the ball from being out of the screen.
     * @param windowDimensions - Window Dimensions in x and y axes.
     */
    private void createBorders(Vector2 windowDimensions) {
        gameObjects().addGameObject(
                new GameObject(
                        Vector2.ZERO,
                        new Vector2(BORDER_WIDTH, windowDimensions.y()),
                        null)
        );
        gameObjects().addGameObject(
                new GameObject(
                        new Vector2(windowDimensions.x()-BORDER_WIDTH - 1, 0),
                        new Vector2(BORDER_WIDTH, windowDimensions.y()),
                        null)
        );
        gameObjects().addGameObject(
                new GameObject(
                        Vector2.ZERO,
                        new Vector2(windowDimensions.x(),BORDER_WIDTH),
                        null)
        );
    }

    /**
     * Main function - runs the game in a new Bricker Game window.
     * @param args - Environment args
     */
    public static void main(String[] args) {
        new BrickerGameManager(
                "Bricker",
                new Vector2(800, 700)).run();
    }
}


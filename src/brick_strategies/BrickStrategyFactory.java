package src.brick_strategies;

import danogl.GameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.*;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.GraphicLifeCounter;
import src.gameobjects.NumericLifeCounter;

/**
 * class BrickStrategyFactory - Responsible for building different Brick Strategies from a variety of 6 types:
 * [CollisionStrategy, AddPaddleStrategy, ChangeCameraStrategy, PuckStrategy, GetExtraLifeStrategy, DoubleDownStrategy]
 */
public class BrickStrategyFactory {
    private final GameObjectCollection gameObjectCollection;
    private final ImageReader imageReader;
    private static final int COLLISION_STRATEGY_IDX = 0;
    private static final int ADD_PADDLE_STRATEGY_IDX = 1;
    private static final int CHANGE_CAMERA_STRATEGY_IDX = 2;
    private static final int PUCK_STRATEGY_IDX = 3;
    private static final int GET_EXTRA_LIFE_STRATEGY_IDX = 4;
    private static final int DOUBLE_DOWN_STRATEGY_IDX = 5;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final SoundReader soundReader;
    private final GameManager gameManager;
    private final WindowController windowController;
    private GraphicLifeCounter graphicLifeCounter;
    private NumericLifeCounter numericLifeCounter;
    private int doubleAmount = 0;

    /**
     * Constructor for BrickStrategyFactory instance. Constructs a new BrickStrategyFactory with all needed fields in
     * order to create all types of BrickStrategy in the BrickerGame.
     * @param gameObjectCollection Total GameObjects in the Bricker Game.
     * @param imageReader ImageReader instance to read different image files for game objects.
     * @param inputListener UserInputListener instance to listen to the keyboard keys - for paddle instances
     * @param windowDimensions Vector 2D for windows dimensions (x,y).
     * @param soundReader SoundReader instance to read sound files.
     * @param gameManager GameManager instance to control the camera settings when ChangeCameraStrategy is activated.
     * @param windowController Window Controller instance.
     * @param graphicLifeCounter GraphicLifeCounter instance to update for AddExtraLifeStrategy
     * @param numericLifeCounter NumericLifeCounter instance to update for AddExtraLifeStrategy
     */
    public BrickStrategyFactory( GameObjectCollection gameObjectCollection,
                                ImageReader imageReader, UserInputListener inputListener,
                                Vector2 windowDimensions, SoundReader soundReader,
                                GameManager gameManager, WindowController windowController,
                                GraphicLifeCounter graphicLifeCounter, NumericLifeCounter numericLifeCounter,
                                Counter livesCounter)
    {
        this.gameObjectCollection = gameObjectCollection;
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.soundReader = soundReader;
        this.gameManager = gameManager;
        this.windowController = windowController;
        this.graphicLifeCounter = graphicLifeCounter;
        this.numericLifeCounter = numericLifeCounter;
    }


    /**
     * buildStrategy function - builds a certain Brick Strategy according to the given strategyType int.
     *
     * @param strategyType represents a given strategy of the brick that need to be constructed.
     * @return return a strategyType from the following [CollisionStrategy, AddPaddleStrategy, ChangeCameraStrategy,
     * PuckStrategy, GetExtraLifeStrategy, DoubleDownStrategy]
     */
    public CollisionStrategy buildStrategy(int strategyType) {
        CollisionStrategy brickStrategy = null;
        switch (strategyType) {
            case COLLISION_STRATEGY_IDX:
                brickStrategy = new CollisionStrategy(gameObjectCollection);
                break;
            case ADD_PADDLE_STRATEGY_IDX:
                brickStrategy = new AddPaddleStrategy(gameObjectCollection, imageReader,
                        inputListener,windowDimensions);
                break;
            case CHANGE_CAMERA_STRATEGY_IDX:
                brickStrategy = new ChangeCameraStrategy(gameObjectCollection, gameManager, windowController);
                break;
            case PUCK_STRATEGY_IDX:
                brickStrategy = new PuckStrategy(gameObjectCollection, imageReader,soundReader);
                break;
            case GET_EXTRA_LIFE_STRATEGY_IDX:
                brickStrategy = new GetExtraLifeStrategy(gameObjectCollection, imageReader,
                        graphicLifeCounter, numericLifeCounter, windowDimensions);
                break;
            case DOUBLE_DOWN_STRATEGY_IDX:
                doubleAmount++;
                brickStrategy = new DoubleDownStrategy( this,gameObjectCollection, doubleAmount);
                doubleAmount = 0;
                break;
            default:
                break;
        }
        return brickStrategy;
    }
}

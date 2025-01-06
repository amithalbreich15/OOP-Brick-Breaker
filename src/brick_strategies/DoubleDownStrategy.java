package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

import java.util.Random;

/**
 * DoubleDownStrategy class - This class describes a kind of strategy that inherits from CollisionStrategy.
 * In this strategy once a brick with this strategy shatters, multiple strategies will be activated according to the
 * number of doubles were raffled in the strategy "lottery". The strategies will be raffled from the list:
 * [1-AddPaddleStrategy, 2-ChangeCameraStrategy, 3-PuckStrategy, 4-GetExtraLifeStrategy, 5-DoubleDownStrategy]
 */
public class DoubleDownStrategy extends CollisionStrategy {
    private static final int COLLISION_STRATEGY_IDX = 0;
    private static final int ADD_PADDLE_STRATEGY_IDX = 1;
    private static final int CHANGE_CAMERA_STRATEGY_IDX = 2;
    private static final int PUCK_STRATEGY_IDX = 3;
    private static final int GET_EXTRA_LIFE_STRATEGY_IDX = 4;
    private static final int DOUBLE_DOWN_STRATEGY_IDX = 5;
    private static final int MAX_POWERUPS = 3;
    private CollisionStrategy[] brickStrategies;
    private BrickStrategyFactory brickStrategyFactory;
    private int[] specificStrategiesIdx;
    private int doubleAmount;

    /**
     * Constructor for DoubleDownStrategy instance.
     * @param brickStrategyFactory Factory that constructs the strategies according to given indices that represents
     * different Brick CollisionStrategy instances from different kinds.
     * @param gameObjectCollection Total Game Objets in the Bricker Game.
     * @param doubleAmount amount of DoubleDownStrategy instances that were raffled.
     */
    public DoubleDownStrategy(BrickStrategyFactory brickStrategyFactory, GameObjectCollection gameObjectCollection,
                              int doubleAmount) {
        super(gameObjectCollection);
        this.brickStrategies = new CollisionStrategy[MAX_POWERUPS];
        this.brickStrategyFactory = brickStrategyFactory;
        this.brickStrategies = new CollisionStrategy[MAX_POWERUPS];
        this.specificStrategiesIdx = new int[]{-1, -1, -1};
        this.doubleAmount = doubleAmount;
        this.specificStrategiesIdx = getRandomStrategies();
        for (int i = 0; i < MAX_POWERUPS; i++) {
            if (specificStrategiesIdx[i] != -1) {
                brickStrategies[i] = this.brickStrategyFactory.buildStrategy(specificStrategiesIdx[i]);
            }

        }
    }

    /**
     *
     * @param collidedObj Brick Objects - collided object to remove from the screen.
     * @param colliderObj Ball object - collider object - it will not be removed.
     * @param bricksCounter Counter for bricks GameObjects remaining in the game.
     */
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        super.onCollision(collidedObj, colliderObj, bricksCounter);
        for (int i = 0; i < MAX_POWERUPS; i++) {
            if (brickStrategies[i] != null) {
                this.brickStrategies[i].onCollision(collidedObj, colliderObj, bricksCounter);
            }
        }
    }

    /**\
     * This function is helper function that checks if more strategies should be raffled.
     * @param strategiesArray The strategies array that holds up to 3 strategies indices that represents strategies to
     * be constructed using the StrategyFactory.
     * @param doubleAmount amount of DoubleDownStrategy raffled from strategies raffle. Maximum doubled allowed are 2.
     * @return true if we should continue raffle more strategies, false if we raffled sufficient strategies.
     */
    private boolean checkMaxStrategies(int[] strategiesArray, int doubleAmount) {
        return (doubleAmount == 1 && !(strategiesArray[0] == -1) && strategiesArray[1] == -1) ||
                (doubleAmount == 2 && !(strategiesArray[0] == -1) && (strategiesArray[1] == -1) &&
                        strategiesArray[2] == -1) || (doubleAmount == 2 && !(strategiesArray[0] == -1) &&
                !(strategiesArray[1] == -1) && strategiesArray[2] == -1);
    }

    /**\
     * This method gets the strategies array as matching indexes per strategy, with specific indices that represents
     * the bricks strategies to be constructed using the StrategyFactory. The number of strategies in the array depends
     * on the number of raffled doubleAmount - if 1 double was raffled only 2 strategies will be filled, if number
     * of doubles is 2 (maximum doubles allowed) 3 special strategies will be raffled from the list:
     * [1-AddPaddleStrategy, 2-ChangeCameraStrategy, 3-PuckStrategy, 4-GetExtraLifeStrategy]
     * excluding DoubleDownStrategy.
     * @return An array of int indices that represents the strategies to be constructed using the BrickStrategyFactory.
     */
    private int[] getRandomStrategies() {
        Random random = new Random();
        int randomStrategy = random.nextInt(6);
        int strategiesSize = 0;
        while ((checkMaxStrategies(specificStrategiesIdx, doubleAmount) || specificStrategiesIdx[0] == -1)) {
            if (doubleAmount == 1)
            {
                randomStrategy = random.nextInt(1, 6);
            }
            if (doubleAmount == 2){
                randomStrategy = random.nextInt(1,5);
            }
            int brickStrategy;
            switch (randomStrategy) {
                case COLLISION_STRATEGY_IDX:
                    brickStrategy = COLLISION_STRATEGY_IDX;
                    specificStrategiesIdx[strategiesSize++] = brickStrategy;
                    break;
                case ADD_PADDLE_STRATEGY_IDX:
                    brickStrategy = ADD_PADDLE_STRATEGY_IDX;
                    specificStrategiesIdx[strategiesSize++] = brickStrategy;
                    break;
                case CHANGE_CAMERA_STRATEGY_IDX:
                    brickStrategy = CHANGE_CAMERA_STRATEGY_IDX;
                    specificStrategiesIdx[strategiesSize++] = brickStrategy;
                    break;
                case PUCK_STRATEGY_IDX:
                    brickStrategy = PUCK_STRATEGY_IDX;
                    specificStrategiesIdx[strategiesSize++] = brickStrategy;
                    break;
                case GET_EXTRA_LIFE_STRATEGY_IDX:
                    brickStrategy = GET_EXTRA_LIFE_STRATEGY_IDX;
                    specificStrategiesIdx[strategiesSize++] = brickStrategy;
                    break;
                case DOUBLE_DOWN_STRATEGY_IDX:
                    doubleAmount++;
                    break;
            }
        }
        return specificStrategiesIdx;
    }

}

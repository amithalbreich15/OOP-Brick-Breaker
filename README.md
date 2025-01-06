# OOP-Brick-Breaker
This project involves developing a brick-breaking game called "Bricker," inspired by the classic game Arkanoid. The game will be implemented using the DanoGameLab library (v1.1.0), which provides game engine functionalities.

# Bricker Game Project

This object-oriented programming exercise involves developing a game called "Bricker," inspired by the classic Arkanoid. The task is recommended to be done in pairs. It is divided into two main parts: creating a basic version of the game and then enhancing it with special effects.

---

## Game Engine and Tools
We provide you with the DanoGameLab library (version 1.1.0), which will serve as the game engine for this project. Using this library, you can create game objects managed by the GameManager.

### Key Objectives:
1. Familiarize yourself with the DanoGameLab library.
2. Implement a basic version of the Bricker game.
3. Enhance the game with special effects and additional features.

---

## Part 1: Basic Game Development

### Gameplay Overview:
The player controls a paddle that moves left and right. The goal is to break all the bricks by bouncing a ball off the paddle. The game ends when all bricks are broken, or the ball falls off the screen.

### API Requirements for Part 1:
**Package: bricker.gameobjects**
- **Class Ball**
  - `constructor`
  - `void onCollisionEnter(GameObject other, Collision collision)`
  - `int getCollisionCounter()`

- **Class Paddle**
  - `constructor`
  - `void update(float deltaTime)`

- **Class Brick**
  - `constructor`
  - `void onCollisionEnter(GameObject other, Collision collision)`

- **Life Counters**
  - Classes that implement both graphical and numeric life counters.

**Package: bricker.brick_strategies**
- **Interface CollisionStrategy**
  - `void onCollision(GameObject thisObj, GameObject otherObj)`

- **Class BasicCollisionStrategy**
  - `constructor`
  - `void onCollision(GameObject thisObj, GameObject otherObj)`

**Package: bricker.main**
- **Class BrickerGameManager**
  - `public static void main(String[] args)`
  - `constructor`
  - `void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController)`
  - `void update(float deltaTime)`
  - Additional methods based on your design.

---

## Part 2: Advanced Game Enhancements

### Special Brick Behaviors:
In this part, you will implement special behaviors for some bricks. When a ball hits a brick, various effects may occur:

1. **Regular Behavior** (50% probability):
   - The brick simply disappears when hit.

2. **Special Behaviors** (50% combined probability):
   - **Additional Balls (10%)**: Two additional balls (called "Pucks") appear.
   - **Additional Paddle (10%)**: A secondary paddle appears.
   - **Camera Shift (10%)**: The camera follows the ball for a limited time.
   - **Life Restoration (10%)**: A heart object falls from the brick, restoring a life when caught.
   - **Double Behavior (10%)**: Two special behaviors are triggered simultaneously.

### Detailed Instructions for Each Special Behavior:
- **Additional Balls**: Two smaller balls spawn at the brick's position.
- **Additional Paddle**: A second paddle appears at the screen's center and mirrors the player's movements.
- **Camera Shift**: The game camera follows the main ball until it collides with four objects.
- **Life Restoration**: A heart object falls from the brick, and the player must catch it with the primary paddle to restore a life.
- **Double Behavior**: Two random special behaviors are triggered when a brick is hit.

### Design Guidelines:
- Avoid creating unnecessary classes.
- Use a minimal and well-structured API.
- Ensure your code is maintainable and extendable.
- Follow the Open-Close Principle: existing classes should remain unchanged while new functionalities are added via new classes.

---




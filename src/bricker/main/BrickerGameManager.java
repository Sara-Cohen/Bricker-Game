package bricker.main;

import bricker.brick_strategies.CollisionStrategy;
import bricker.gameObject.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.Random;

public class BrickerGameManager extends GameManager {
    private Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private Heart heart;
    private Paddle paddle;
    private Brick brick;
    private CollisionStrategy collisionStrategy;

    public BrickerGameManager(String windowTitle,
                              Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {

        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController = windowController;
        this.windowDimensions = windowController.getWindowDimensions();

        //Define ball:
        final int BALL_SPEED = 300;
        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        Sound soundCollision = soundReader.readSound("assets/blop_cut_silenced.wav");
        ball = new Ball(Vector2.ZERO, new Vector2(20, 20), ballImage, soundCollision);
        Random rand = new Random();
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        if (rand.nextBoolean())
            ballVelY *= -1;
        if (rand.nextBoolean())
            ballVelX *= -1;
        ball.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 45));
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
        gameObjects().addGameObject(ball);

        //Define paddle
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);
        paddle = new Paddle(Vector2.ZERO, new Vector2(100, 15), paddleImage, inputListener, windowDimensions);
        paddle.setCenter(new Vector2(windowDimensions.x() / 2, (int) windowDimensions.y() - 30));
        gameObjects().addGameObject(paddle);

        //define invisible walls
        int[] wallLocation = {(int) windowDimensions.x(), 0};
        for (int i = 0; i < wallLocation.length; i++) {
            GameObject wall = new GameObject(Vector2.ZERO, new Vector2(2, windowDimensions.y()), null);
            wall.setCenter(new Vector2(wallLocation[i], windowDimensions.y() / 2));
            gameObjects().addGameObject(wall);
        }
        GameObject wall = new GameObject(Vector2.LEFT, new Vector2(windowDimensions.x() * 2, 2), null);
        wall.setCenter(new Vector2(windowDimensions.y(), 0));
        gameObjects().addGameObject(wall);

        //define background
        GameObject background = new GameObject(Vector2.ZERO, windowController.getWindowDimensions(), imageReader.readImage("assets/DARK_BG2_small.jpeg", false));
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);

        //define bricks
        Renderable brickImage = imageReader.readImage("assets/brick.png", false);
        collisionStrategy = new CollisionStrategy(gameObjects());
        final int ROW =1;
        final int COLUMN = 1;
        float width = (windowDimensions.x() / COLUMN);
        for (int j = 0; j < ROW; j++) {
            for (int i = 0; i < COLUMN; i++) {
                brick = new Brick(Vector2.ZERO, new Vector2(width, 20), brickImage, collisionStrategy);
                brick.setCenter(new Vector2(i * width + width / 2, 10 + 21 * j));
                gameObjects().addGameObject(brick);
            }
        }

        //define heart
        final int NUM_OF_HEART = 3;
        Renderable imageHeart = imageReader.readImage("assets/heart.png", true);
        heart = new Heart(Vector2.ZERO, new Vector2(30, 30), imageHeart, gameObjects(), NUM_OF_HEART);
    }


    private boolean winCondition = false;

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        String prompt = "";
        if (heart.GetNumOfHearts() == 0)
            prompt = "You lose!";
        if (winCondition)
            prompt = "You win";
        if (!prompt.isEmpty()) {
            prompt += " Do you want to play again?";
            if (windowController.openYesNoDialog(prompt))
                windowController.resetGame();
            else
                windowController.closeWindow();
        }
        float ballHeight = ball.getCenter().y() - ball.getDimensions().y();
        if (ballHeight > windowDimensions.y()) {
            heart.removeHeart();
            paddle.setCenter(new Vector2(windowDimensions.x() / 2, (int) windowDimensions.y() - 30));
            ball.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 45));
        }
        if (collisionStrategy.getCountBricks() == 0) {
            winCondition = true;
            paddle.setCenter(new Vector2(windowDimensions.x() / 2, (int) windowDimensions.y() - 30));
            ball.setCenter(new Vector2(-50, -50));
        }
    }

    public static void main(String[] args) {
        new BrickerGameManager("Bricker", new Vector2(700, 500)).run();
    }


}
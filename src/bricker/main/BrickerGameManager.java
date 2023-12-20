package bricker.main;

import bricker.brick_strategies.CollisionStrategy;
import bricker.gameObject.AiPaddle;
import bricker.gameObject.Brick;
import bricker.gameObject.UserPaddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.gameObject.Ball;

import java.awt.*;
import java.util.Random;

public class BrickerGameManager extends GameManager {
   private GameObject ball;
    private Vector2 windowDimensions;
    private WindowController windowController;

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        double ballHeight = ball.getCenter().y()-ball.getDimensions().y();
        String prompt = "";
        if (ballHeight > windowDimensions.y())
            prompt = "you lose";
        if (!prompt.isEmpty()) {
            prompt += " play again?";
            if (windowController.openYesNoDialog(prompt))
                windowController.resetGame();
            else
                windowController.closeWindow();
        }
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
       final int BALL_SPEED = 250;

        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        Sound soundColision = soundReader.readSound("assets/blop_cut_silenced.wav");
        ball = new Ball(Vector2.ZERO, new Vector2(20, 20), ballImage, soundColision);
        Random rand = new Random();
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
//        if (rand.nextBoolean())
//            ballVelX *= -1;
        if (rand.nextBoolean())
            ballVelY *= -1;
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
        ball.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 45));
        gameObjects().addGameObject(ball);


        //Define paddle
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);
        Vector2 WindowDimensions = windowController.getWindowDimensions();
        //user paddle
        GameObject userPaddle = new UserPaddle(Vector2.ZERO, new Vector2(100, 15), paddleImage, inputListener, WindowDimensions);
        userPaddle.setCenter(new Vector2(windowDimensions.x() / 2, (int) windowDimensions.y() - 30));
        gameObjects().addGameObject(userPaddle);

        //define invisible walls
        int[] wallLocation = {(int) windowDimensions.x(), 0};
        Color BORDER_COLOR = new Color(60, 60, 60);
        RectangleRenderable rectangleRenderable = new RectangleRenderable(BORDER_COLOR);
        for (int i = 0; i < wallLocation.length; i++) {
            GameObject wall = new GameObject(Vector2.ZERO, new Vector2(2, windowDimensions.y()), rectangleRenderable);
            wall.setCenter(new Vector2(wallLocation[i], windowDimensions.y() / 2));
            gameObjects().addGameObject(wall);
        }
        GameObject wall = new GameObject(Vector2.LEFT, new Vector2(windowDimensions.x() * 2, 2), rectangleRenderable);
        wall.setCenter(new Vector2(windowDimensions.y(), 0));
        gameObjects().addGameObject(wall);

        //define background

        GameObject background = new GameObject(Vector2.ZERO, windowController.getWindowDimensions(), imageReader.readImage("assets/DARK_BG2_small.jpeg", false));
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);

        //define bricks
        Renderable brickImage = imageReader.readImage("assets/brick.png", false);
        CollisionStrategy collisionStrategy = new CollisionStrategy(gameObjects());
        float width = (windowDimensions.x() / 8);
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 8; i++) {
                GameObject brick = new Brick(Vector2.ZERO, new Vector2(windowDimensions.x() / 8, 20), brickImage, collisionStrategy);
                brick.setCenter(new Vector2(i * width + width / 2, 10 + 21 * j));
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }
        }


    }


    public static void main(String[] args) {
        new BrickerGameManager("Bricker", new Vector2(700, 500)).run();
    }
}

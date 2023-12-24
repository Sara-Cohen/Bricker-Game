package bricker.gameObject;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
    private static final int MOVEMENT_SPEED = 420;
    private static final int MIN_DISTANCE_FROM_SCREEN_EDGE = -60;
    private Vector2 topLeftCorner;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;

    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener, Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);

        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT))
            movementDir = movementDir.add(Vector2.LEFT);
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT))
            movementDir = movementDir.add(Vector2.RIGHT);
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
        if (getTopLeftCorner().x() < MIN_DISTANCE_FROM_SCREEN_EDGE) {
            transform().setTopLeftCornerX(MIN_DISTANCE_FROM_SCREEN_EDGE);
        }
        if (getTopLeftCorner().x() > windowDimensions.x() - MIN_DISTANCE_FROM_SCREEN_EDGE - getDimensions().x()) {
            transform().setTopLeftCornerX(windowDimensions.x() - MIN_DISTANCE_FROM_SCREEN_EDGE - getDimensions().x());
        }

    }
}

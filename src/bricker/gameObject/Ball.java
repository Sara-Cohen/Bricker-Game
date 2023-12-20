package bricker.gameObject;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Ball extends GameObject {
    private Sound soundCollision;

    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound soundCollision) {
        super(topLeftCorner, dimensions, renderable);
        this.soundCollision = soundCollision;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (!(other instanceof Heart))
        {
            setVelocity(getVelocity().flipped(collision.getNormal()));
            soundCollision.play();
        }
    }
}
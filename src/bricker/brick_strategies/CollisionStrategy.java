package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;


public class CollisionStrategy {

    private final GameObjectCollection gameObjectsCollection;
    private Counter countBricks = new Counter();

    public CollisionStrategy(GameObjectCollection gameObjectCollection) {
        this.gameObjectsCollection = gameObjectCollection;
    }

    public void onCollision(GameObject thisObj, GameObject otherObj) {
        gameObjectsCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
        this.countBricks.decrement();

    }

    public void setCountBricks() {
        this.countBricks.increment();
    }

    public int getCountBricks() {
        return countBricks.value();
    }
}
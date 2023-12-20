package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;


public class CollisionStrategy {

    private final GameObjectCollection gameObjectsCollection;
    private int countBricks = 0;

    public CollisionStrategy(GameObjectCollection gameObjectCollection) {
        this.gameObjectsCollection = gameObjectCollection;
    }

    public void onCollision(GameObject thisObj, GameObject otherObj) {
        gameObjectsCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
        this.countBricks--;

    }

    public void setCountBricks() {
        this.countBricks++;
    }

    public int getCountBricks() {
        return countBricks;
    }
}
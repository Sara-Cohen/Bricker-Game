package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;



public class CollisionStrategy {

    private final GameObjectCollection gameObjectsCollection;

    public CollisionStrategy(GameObjectCollection gameObjectCollection) {
        this.gameObjectsCollection = gameObjectCollection;
    }

    public void onCollision(GameObject thisObj, GameObject otherObj) {
        gameObjectsCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
    }
}
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
        System.out.println("collision with brick detected");
        gameObjectsCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
    }
}
package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;
import danogl.util.Vector2;


public class CollisionStrategy {

    private final GameObjectCollection gameObjectsCollection;
    private int countBricks = 0;

    public CollisionStrategy(GameObjectCollection gameObjectCollection) {
        this.gameObjectsCollection = gameObjectCollection;
    }

    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if(countBricks==1)
            thisObj.setCenter(new Vector2(-50, -50));
        this.gameObjectsCollection.removeGameObject(thisObj);
        this.countBricks--;
    }

    public void setCountBricks() {
        this.countBricks++;
    }

    public int getCountBricks() {
        return countBricks;
    }
}
package bricker.gameObject;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Heart extends GameObject {
    private int numOfHeart;
    private GameObject[] arrHeart;
    private GameObjectCollection gameObjectsCollection;


    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, GameObjectCollection gameObjectsCollection, int numOfHeart) {
        super(topLeftCorner, dimensions, renderable);
        this.numOfHeart = numOfHeart;
        this.gameObjectsCollection = gameObjectsCollection;
        this.arrHeart = new Heart[numOfHeart];
        for (int i = 0; i < numOfHeart; i++) {
            this.arrHeart[i] = new Heart(topLeftCorner, dimensions, renderable);
            this.arrHeart[i].setTopLeftCorner(new Vector2(30 * i + 10, 470));
            gameObjectsCollection.addGameObject(arrHeart[i]);
        }
    }

    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
    }

    public void removeHeart() {
        if(numOfHeart==1)
            this.arrHeart[0].setCenter(new Vector2(-50, -50));
        this.gameObjectsCollection.removeGameObject(arrHeart[numOfHeart - 1]);
        this.numOfHeart--;
    }

    public int GetNumOfHearts() {
        return this.numOfHeart;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
}

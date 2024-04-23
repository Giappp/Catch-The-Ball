package object;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Ball {
    private float speed;
    private Vector2 startPosition;
    private float radius;

    public Ball() {
        this.startPosition = new Vector2();
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Vector2 getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(float x, float y) {
        this.startPosition.x = x;
        this.startPosition.y = y;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public boolean overlaps(Rectangle catchField) {
        throw new NotImplementedException();
    }
}

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
        if(catchField.getX() <= this.startPosition.x &&
                this.startPosition.x <= catchField.getX() + catchField.getWidth()
        && this.startPosition.y <= (catchField.getHeight() + catchField.y ) ){
            return true;
        }
        return false;
        //return catchField.contains(new Circle(startPosition.x,startPosition.y,radius));
    }
}

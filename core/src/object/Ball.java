package object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Ball extends Image {
    private static final float speed = 600;
    private float radius;
    public final Circle ball;
    // start time in millisecond
    public float startTime;
    public boolean hasHyper;
    public Ball hyperDashTarget;
    public float distanceToHyperDash;
    public boolean catched;
    public float delay;
    public Size size;

    public enum Size{
        Fruit,
        Droplet,
        Banana
    }
    public Ball(float x,float y, float radius,float startTime) {
        super(new Texture("fruit-drop.png"));
        ball = new Circle(x,y,radius);
        this.setPosition(ball.x, ball.y);
        this.startTime = startTime;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        while (ball.y > 0){
            ball.y -= speed;
            this.setPosition(ball.x, ball.y);
            if(ball.y == 0){
                remove();
            }
        }
    }
    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.circle(ball.x, ball.y, ball.radius);
    }

    @Override
    public void drawDebugBounds(ShapeRenderer shapes) {
        shapes.circle(ball.x, ball.y, ball.radius);
    }
}

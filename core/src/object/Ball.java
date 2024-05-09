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
    public Circle ball;
    // start time in millisecond
    public float startTime;
    public boolean hasHyper;
    public Ball hyperDashTarget;
    public float distanceToHyperDash;
    public boolean catched;
    public Size size;

    public enum Size{
        Fruit,
        Droplet,
        Banana
    }
    public Ball(float x,float y, float radius,float startTime) {
        super(new Texture("fruit-drop.png"));
        ball = new Circle(x,y,radius);
        this.startTime = startTime;
        this.setBounds(ball.x, ball.y, ball.radius, ball.radius);
        setPosition(ball.x, ball.y);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        getDrawable().draw(batch,ball.x - ball.radius, ball.y - ball.radius, ball.radius * 2, ball.radius * 2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        ball.y -= (speed * delta);
        setWidth(ball.radius * 2);
        setHeight(ball.radius * 2);
        setPosition(ball.x - ball.radius, ball.y - ball.radius);
        if(ball.y <= 0){
            remove();
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

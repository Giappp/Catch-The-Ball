package object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class CatchPlayField extends Image {
    public static final float catcherSpeed = 800;
    public static final float ALLOWED_CATCH_RANGE = 320;
    // y default position for the sprite
    private static float DEFAULT_Y_POSITION;
    public final Rectangle catcher;
    private final Texture playerTexture;
    private final Texture playerLeftTexture;
//    private ParticleEffect effect;
    private boolean isSpeedUp;
    private int speedModifier;


    public CatchPlayField(){
        super(new Texture("player.png"));
        playerTexture = new Texture("player.png");
        playerLeftTexture = new Texture("player-left.png");

//        effect = new ParticleEffect();
//        effect.load(Gdx.files.internal("effect/default.p"),Gdx.files.internal("effect"));

        catcher = new Rectangle();
        catcher.width = getWidth();
        catcher.height = getHeight() / 6f;
        catcher.x = Gdx.graphics.getWidth() / 2f - catcher.width / 2f;
        catcher.y = 220f;
        DEFAULT_Y_POSITION = - catcher.height / 2f;
        //this.setBounds(catcher.x,catcher.y,catcher.width,catcher.height);
        this.setPosition(catcher.x,DEFAULT_Y_POSITION);
//        effect.start();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
//        if(isSpeedUp){
//            effect.draw(batch);
//        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        speedModifier = 1;
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
            isSpeedUp = true;
            speedModifier = 2;
        }else{
            isSpeedUp = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            catcher.x -= catcherSpeed * speedModifier * delta;
            setDrawable(new SpriteDrawable(new Sprite(playerLeftTexture)));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            catcher.x += catcherSpeed * speedModifier * delta;
            setDrawable(new SpriteDrawable(new Sprite(playerTexture)));
        }
        if (catcher.x < 0) {
            catcher.x = 0;
        }
        if (catcher.x > Gdx.graphics.getWidth() - catcher.width) {
            catcher.x = Gdx.graphics.getWidth() - catcher.width;
        }
        setPosition(catcher.x,DEFAULT_Y_POSITION);
//        effect.setPosition(catcher.x + 130,133);
//        effect.update(delta);
    }
    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.rect(catcher.x, catcher.y, catcher.width, catcher.height);
    }

    @Override
    public void drawDebugBounds(ShapeRenderer shapes) {
        shapes.rect(catcher.x, catcher.y, catcher.width, catcher.height);
    }
    public boolean overLaps(Circle ball){
        // Calculate the distance between the circle's center and the rectangle's center
        Vector2 circleCenter = new Vector2(ball.x, ball.y);
        Vector2 rectangleCenter = new Vector2(catcher.x + catcher.width / 2,
                catcher.y + catcher.height / 2);
        float distance = circleCenter.dst(rectangleCenter);

        // Calculate the sum of the circle's radius and half of the rectangle's diagonal
        float circleRadius = ball.radius;
        float rectangleDiagonal = new Vector2(catcher.width, catcher.height).len() / 2;
        float sumRadiusDiagonal = circleRadius + rectangleDiagonal;

        // Check conditions for overlap
        if (distance > sumRadiusDiagonal) {
            // They definitely don't overlap
            return false;
        }

        // Check if the circle is completely outside the rectangle
        if (catcher.contains(ball.x, ball.y)) {
            return true; // Circle center is inside rectangle
        }

        // Check if the circle is completely inside the rectangle
        if (ball.x - ball.radius > catcher.x && ball.x + ball.radius < catcher.x + catcher.width &&
                ball.y - ball.radius > catcher.y && ball.y + ball.radius < catcher.y + catcher.height) {
            return true; // Circle is completely inside rectangle
        }

        // Check if any part of the circle is inside the rectangle
        if (catcher.contains(ball.x - ball.radius, ball.y) ||
                catcher.contains(ball.x + ball.radius, ball.y) ||
                catcher.contains(ball.x, ball.y - ball.radius) ||
                catcher.contains(ball.x, ball.y + ball.radius)) {
            return true; // Some part of circle is inside rectangle
        }

        // No overlap found
        return false;
    }
}

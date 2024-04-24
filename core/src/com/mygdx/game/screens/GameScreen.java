package com.mygdx.game.screens;

import java.util.Iterator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.MyGame;
import object.Ball;

public class GameScreen implements Screen {
    private static final float DEFAULT_SPEED = 200;
    private boolean isSpeedUp = false;
    private static final float SPEED_UP_MULTIPLIER = 2.0f;
    final Game game;

    private static final SpriteBatch batch = new SpriteBatch();

    Texture dropImage;
    Texture catcherImage;
    Sound dropSound;
    Music rainMusic;
    OrthographicCamera camera;
    ShapeRenderer shapeRenderer;
    Rectangle catchField;
    Array<Ball> balls;
    long lastDropTime;
    int dropsGathered;

    public GameScreen(final Game game) {
        this.game = game;

        shapeRenderer = new ShapeRenderer();
        dropImage = new Texture(Gdx.files.internal("drop.png"));
        catcherImage = new Texture(Gdx.files.internal("player.png"));

        // load the drop sound effect and the rain background "music"
//        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
//        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
//        rainMusic.setLooping(true);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1980, 1080);

        // the catch field
        catchField = new Rectangle();
        catchField.x = camera.viewportWidth / 2 - 64 / 2; // center the catcher horizontally
        catchField.y = 0; // bottom left corner of the bucket is 20 pixels above
        catchField.width = 293;
        catchField.height = 320;

        // create the raindrops array and spawn the first raindrop
        balls = new Array<>();
        spawnBall();

    }

    private void spawnBall() {
        Ball ball = new Ball();
        ball.setStartPosition(MathUtils.random(0, 800 - 64),800);
        ball.setRadius(64);
        ball.setSpeed(100);
        balls.add(ball);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to clear are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        batch.begin();
        MyGame.smallFont.draw(batch, "Drops Collected: " + dropsGathered, 0, 800);
        batch.draw(catcherImage, catchField.x, catchField.y, catchField.width, catchField.height);
        for (Ball ball : balls) {
            batch.draw(dropImage, ball.getStartPosition().x, ball.getStartPosition().y);
        }
        batch.end();

        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            if(isSpeedUp){
                catchField.x -= DEFAULT_SPEED * Gdx.graphics.getDeltaTime() * SPEED_UP_MULTIPLIER;
            }else{
                catchField.x -= DEFAULT_SPEED * Gdx.graphics.getDeltaTime();
            }
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)){
            if(isSpeedUp){
                catchField.x += DEFAULT_SPEED * Gdx.graphics.getDeltaTime() * SPEED_UP_MULTIPLIER;
            }else{
                catchField.x += DEFAULT_SPEED * Gdx.graphics.getDeltaTime();
            }
        }
        if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)){
            isSpeedUp = true;
        }

        // make sure the bucket stays within the screen bounds
        if (catchField.x < 0)
            catchField.x = 0;
        if (catchField.x > 1280 - catchField.width)
            catchField.x = 1280 - catchField.width;

        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
            spawnBall();

        Iterator<Ball> iter = balls.iterator();
        while (iter.hasNext()) {
            Ball ball = iter.next();
            ball.getStartPosition().y -= ball.getSpeed() * Gdx.graphics.getDeltaTime();
            //System.out.println("Ball position: " + ball.getStartPosition().x + " " + ball.getStartPosition().y);
            if (ball.getStartPosition().y + 64 < 0)
                iter.remove();
            if (ball.overlaps(catchField)) {
                System.out.println("Ball position: " + ball.getStartPosition().x + " " + ball.getStartPosition().y);
                System.out.println("Catch Field position: " + catchField.x + " " + catchField.y);
                dropsGathered++;
//                dropSound.play();
                iter.remove();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        dropImage.dispose();
        catcherImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }
}

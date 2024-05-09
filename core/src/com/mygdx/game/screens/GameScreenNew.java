package com.mygdx.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGame;
import factory.SpawnBallFactory;
import object.Ball;
import object.CatchPlayField;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class GameScreenNew extends ApplicationAdapter implements Screen {

    private final Stage stage;
    private final Game game;
    private final CatchPlayField catchPlayField;
    Array<Ball> balls;
    private final OrthographicCamera camera;
    public GameScreenNew(Game game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        stage = new Stage(new ScreenViewport());
        catchPlayField = new CatchPlayField();
        stage.addActor(catchPlayField);
        balls = new Array<>();
        Array<Ball> ballArray = SpawnBallFactory.spawnBall(100,2);
        for(Ball ball : ballArray){
            new Timer().schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            balls.add(ball);
                        }
                    }
                    , (long) ball.delay);
        }
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean keyDown(int keyCode){
                if(keyCode == Input.Keys.ESCAPE){
                    pause();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        camera.update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        stage.act(Gdx.graphics.getDeltaTime());
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        catchPlayField.drawDebug(shapeRenderer); // Draw the catcher rectangle
        for(Iterator<Ball> iter = balls.iterator(); iter.hasNext(); ){
            Ball ball = iter.next();
            stage.addActor(ball);
            if(ball.ball.y + ball.ball.radius < 0) iter.remove();
            if(catchPlayField.overLaps(ball.ball)){
                iter.remove();
            }
        }
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        game.setScreen(new PauseGameScreen((MyGame) game));
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

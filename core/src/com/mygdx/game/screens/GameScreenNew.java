package com.mygdx.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGame;
import factory.SpawnBallFactory;
import object.Ball;
import object.CatchPlayField;

import java.util.*;
import java.util.concurrent.*;

public class GameScreenNew extends ApplicationAdapter implements Screen {

    private final Stage stage;
    private final Game game;
    private final CatchPlayField catchPlayField;
    private ConcurrentLinkedQueue<Ball> spawnedBalls;
    private final OrthographicCamera camera;
    public GameScreenNew(Game game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        stage = new Stage(new ScreenViewport());
        catchPlayField = new CatchPlayField();
        stage.addActor(catchPlayField);
        spawnedBalls = new ConcurrentLinkedQueue<>();
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
        Array<Ball> mapObjects = SpawnBallFactory.spawnBall(100,2);
        System.out.println(mapObjects.size);
        for(Ball ball : mapObjects){
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            spawnBall(ball);
                        }
                    },
                    (long) ball.startTime
            );
        }
    }

    private void spawnBall(Ball ball) {
        spawnedBalls.add(ball);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        camera.update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        catchPlayField.drawDebug(shapeRenderer); // Draw the catcher rectangle
        for(Iterator<Ball> iter = spawnedBalls.iterator(); iter.hasNext();){
            Ball ball = iter.next();
            stage.addActor(ball);
            ball.drawDebug(shapeRenderer);
            if(catchPlayField.overLaps(ball.ball)) {
                ball.remove();
                iter.remove();
            }
        }
        shapeRenderer.end();
        stage.draw();
        stage.act(Gdx.graphics.getDeltaTime());
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
    private void debugBall(){
        for(Ball b:spawnedBalls){
            System.out.println(spawnedBalls.size());
            System.out.println(b.toString());
        }
    }
}

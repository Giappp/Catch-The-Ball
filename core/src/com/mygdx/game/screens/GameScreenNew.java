package com.mygdx.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    private final int playerHealth = 10;
    private final Game game;
    private final CatchPlayField catchPlayField;
    private final ConcurrentLinkedQueue<Ball> spawnedBalls;
    private final OrthographicCamera camera;
    private final Label ballCountLabel;
    private int ballCollected;
    private Array<Ball> mapObjects;
    private boolean isComplete;
    public GameScreenNew(Game game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        stage = new Stage(new ScreenViewport());
        catchPlayField = new CatchPlayField();
        stage.addActor(catchPlayField);
        spawnedBalls = new ConcurrentLinkedQueue<>();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.BLACK;
        labelStyle.font = MyGame.smallFont;

        ballCollected = 0;

        ballCountLabel = new Label("Total Balls Collected: 0", labelStyle);
        ballCountLabel.setPosition(10, Gdx.graphics.getHeight() - 40); // Adjust position as needed
        stage.addActor(ballCountLabel);
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
        mapObjects = SpawnBallFactory.spawnBall(50,1.5f);
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
        Gdx.gl.glClearColor(255, 255, 255, 1);
        camera.update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        for(Iterator<Ball> iter = spawnedBalls.iterator(); iter.hasNext();){
            Ball ball = iter.next();
            stage.addActor(ball);
            if(catchPlayField.overLaps(ball.ball)) {
                iter.remove();
                ball.remove();
                ballCollected++;
                updateBallCountLabel();
            }
            if(isComplete){
                game.setScreen(new VictoryScreen((MyGame) game));
//                game.dispose();
            }
        }
        //shapeRenderer.end();
        stage.draw();
        stage.act(Gdx.graphics.getDeltaTime());
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
        Gdx.app.exit();
    }
    private void updateBallCountLabel() {
        ballCountLabel.setText("Total Balls Collected: " + ballCollected);
        if(ballCollected == 5){
            isComplete = true;
        }
    }
}

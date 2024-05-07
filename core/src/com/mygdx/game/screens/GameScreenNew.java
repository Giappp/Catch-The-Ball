package com.mygdx.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGame;
import object.CatchPlayField;

public class GameScreenNew extends ApplicationAdapter implements Screen {

    private Stage stage;
    private Game game;
    private final CatchPlayField catchPlayField;
    private OrthographicCamera camera;
    public GameScreenNew(Game game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        stage = new Stage(new ScreenViewport());
        catchPlayField = new CatchPlayField();
        stage.addActor(catchPlayField);
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

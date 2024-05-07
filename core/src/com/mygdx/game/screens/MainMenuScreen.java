package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGame;

public class MainMenuScreen implements Screen {
    private Stage stage;
    private Game game;

    public MainMenuScreen(final MyGame gam) {
        game = gam;
        stage = new Stage(new ScreenViewport());
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = MyGame.bigFont;
        Label title = new Label("Catch The Ball",labelStyle);
        title.setAlignment(Align.center);
        title.setY((float) (Gdx.graphics.getHeight() * 2) /3);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = MyGame.smallFont;
        TextButton playButton = new TextButton("Play Game",buttonStyle);
        playButton.setPosition((float) Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2,
                (float) Gdx.graphics.getHeight() / 2 - playButton.getHeight() / 2);
        playButton.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreenNew(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(playButton);

        TextButton optionsButton = new TextButton("Options",buttonStyle);
        optionsButton.setWidth((float) Gdx.graphics.getWidth() / 2);
        optionsButton.setPosition((float) Gdx.graphics.getWidth() / 2 - optionsButton.getWidth() / 2,
                (float) Gdx.graphics.getHeight() / 4 - optionsButton.getHeight() / 2);
        optionsButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new OptionScreen(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(optionsButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
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
        stage.dispose();
    }
}

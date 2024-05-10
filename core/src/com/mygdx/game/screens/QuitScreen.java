package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGame;

public class QuitScreen implements Screen {
    private final Stage stage;
    private final Game game;

    public QuitScreen(final MyGame gam) {
        game = gam;
        stage = new Stage(new ScreenViewport());

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = MyGame.bigFont;
        Label messageLabel = new Label("Are you sure you want to quit?", labelStyle);
        messageLabel.setAlignment(Align.center);
        messageLabel.setY((float) (Gdx.graphics.getHeight() * 2) / 3);
        messageLabel.setWidth(Gdx.graphics.getWidth());
        stage.addActor(messageLabel);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = MyGame.smallFont;

        TextButton yesButton = new TextButton("Yes", buttonStyle);
        yesButton.setPosition((float) Gdx.graphics.getWidth() / 2 - yesButton.getWidth() / 2,
                (float) Gdx.graphics.getHeight() / 2 - yesButton.getHeight() / 2);
        yesButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit(); // Quit the application
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(yesButton);

        TextButton noButton = new TextButton("No", buttonStyle);
        noButton.setWidth((float) Gdx.graphics.getWidth() / 2);
        noButton.setPosition((float) Gdx.graphics.getWidth() / 2 - noButton.getWidth() / 2,
                (float) Gdx.graphics.getHeight() / 4 - noButton.getHeight() / 2);
        noButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenuScreen((MyGame) game)); // Go back to main menu
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(noButton);
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

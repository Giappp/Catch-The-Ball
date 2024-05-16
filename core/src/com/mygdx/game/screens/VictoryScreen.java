package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class VictoryScreen implements Screen {
    private final Stage stage;
    private final Game game;

    public VictoryScreen(final MyGame gam) {
        game = gam;
        stage = new Stage(new ScreenViewport());
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = MyGame.bigFont;
        Label title = new Label("Victory!", labelStyle);
        title.setAlignment(Align.center);
        title.setY((float) (Gdx.graphics.getHeight() * 2) / 3);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = MyGame.smallFont;
        textFieldStyle.fontColor = Color.BLUE;

        final TextField usernameField = new TextField("", textFieldStyle);
        usernameField.setMessageText("Username");
        usernameField.setPosition((float) Gdx.graphics.getWidth() / 2 - usernameField.getWidth() / 2,
                (float) Gdx.graphics.getHeight() / 2 + 50);
        stage.addActor(usernameField);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = MyGame.smallFont;
        TextButton submitButton = new TextButton("Submit", buttonStyle);
        submitButton.setWidth((float) Gdx.graphics.getWidth() / 2);
        submitButton.setPosition((float) Gdx.graphics.getWidth() / 2 - submitButton.getWidth() / 2,
                (float) Gdx.graphics.getHeight() / 2 - submitButton.getHeight() / 2 - 100);
        submitButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                String username = usernameField.getText();
                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Gdx.files.local("highScores").file()));
                    StringBuilder stringBuilder = new StringBuilder(username + " : ");
                    bufferedWriter.write(stringBuilder.toString());
                } catch (IOException e) {
                    System.out.println("Failed to write file");
                }
                game.setScreen(new MainMenuScreen((MyGame) game));
            }
        });
        stage.addActor(submitButton);
    }

    private boolean valid(String username) {
        if(username.isEmpty()){
            return false;
        }
        return true;
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

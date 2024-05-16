package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGame;

import java.io.*;

public class HighScoreScreen implements Screen {
    private final Stage stage;
    private final Game game;
    private int highScores;

    public HighScoreScreen(final MyGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        this.highScores = highScores;

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
                if(!valid(username)){
                    return;
                }
                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(String.valueOf(Gdx.files.internal("highScores.txt"))));
                    StringBuilder stringBuilder = new StringBuilder(username + " : " + highScores);
                    bufferedWriter.write(stringBuilder.toString());
                } catch (IOException e) {
                    System.out.println("Failed to write file");
                }
            }
        });
        stage.addActor(submitButton);
    }

    @Override
    public void show() {

    }

    private boolean valid(String username) {
        if(username.isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

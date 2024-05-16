package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGame;
import dao.JDBCConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginScreen implements Screen {
    private final Stage stage;
    private final Game game;

    public LoginScreen(final Game gam) {
        game = gam;
        stage = new Stage(new ScreenViewport());

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = MyGame.bigFont;

        // Title
        Label title = new Label("Login", labelStyle);
        title.setAlignment(Align.center);
        title.setY((float) (Gdx.graphics.getHeight() * 2) / 3);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        // Username field
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = MyGame.smallFont;
        textFieldStyle.fontColor = Color.BLUE;

        final TextField usernameField = new TextField("", textFieldStyle);
        usernameField.setMessageText("Username");
        usernameField.setPosition((float) Gdx.graphics.getWidth() / 2 - usernameField.getWidth() / 2,
                (float) Gdx.graphics.getHeight() / 2 + 50);
        stage.addActor(usernameField);

        // Password field
        final TextField passwordField = new TextField("", textFieldStyle);
        passwordField.setMessageText("Password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordField.setPosition((float) Gdx.graphics.getWidth() / 2 - passwordField.getWidth() / 2,
                (float) Gdx.graphics.getHeight() / 2);
        stage.addActor(passwordField);

        // Login button
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = MyGame.smallFont;
        TextButton loginButton = new TextButton("Login", buttonStyle);
        loginButton.setWidth((float) Gdx.graphics.getWidth() / 2);
        loginButton.setPosition((float) Gdx.graphics.getWidth() / 2 - loginButton.getWidth() / 2,
                (float) Gdx.graphics.getHeight() / 2 - loginButton.getHeight() / 2 - 100);
        loginButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                String username = usernameField.getText();
                String password = passwordField.getText();

                // Check username and password
                if (isValidLogin(username, password)) {
                    // Navigate to main menu or game screen
                    game.setScreen(new MainMenuScreen((MyGame) game));
                } else {
                    // Display error message or handle invalid login
                    System.out.println("Invalid username or password");
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(loginButton);
    }

    private boolean isValidLogin(String username, String password) {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (Connection connection = JDBCConnect.getJDBCConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Returns true if there is a match, false otherwise
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

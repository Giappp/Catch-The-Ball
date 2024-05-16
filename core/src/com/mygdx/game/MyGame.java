package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.screens.LoginScreen;
import com.mygdx.game.screens.MainMenuScreen;

public class MyGame extends Game  {
    static public BitmapFont smallFont;
    static public BitmapFont bigFont;

    public void create(){
        smallFont = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
        bigFont = new BitmapFont(Gdx.files.internal("fonts/font_big.fnt"));
        //this.setScreen(new LoginScreen(this));
        this.setScreen(new MainMenuScreen(this));
    }
    public void render(){
        super.render(); // important!
    }
    public void dispose(){

    }
}

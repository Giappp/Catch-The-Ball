package object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class CatchPlayField extends Image {
    private static final float DEFAULT_SPEED = 800;
    Rectangle catcher;

    public CatchPlayField(){
        super(new Texture("player.png"));
        catcher = new Rectangle();
        catcher.width = 306f / 2;
        catcher.height = 320f / 2;
        catcher.x = Gdx.graphics.getWidth() / 2f - catcher.width / 2f;
        catcher.y = -catcher.height / 2f;
        this.setPosition(catcher.x,catcher.y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        setPosition(catcher.x,catcher.y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        int speedModifier = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) ? 2 : 1;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            catcher.x -= DEFAULT_SPEED * speedModifier * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            catcher.x += DEFAULT_SPEED * speedModifier * Gdx.graphics.getDeltaTime();

        if (catcher.x < 0) catcher.x = 0;
        if (catcher.x > Gdx.graphics.getWidth() - catcher.width) catcher.x = Gdx.graphics.getWidth() - catcher.width;
    }
}

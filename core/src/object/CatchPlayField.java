package object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import movement.PlayerMovementListener;
import movement.PlayerState;
import utils.BodyEditorLoader;

public class CatchPlayField extends Image {
    private Body body;
    private World world;
    private float angle;

    public CatchPlayField(World world, float posX, float posY, float width, float height){
        super(new Texture("player.png"));
        this.setSize(width,height);
        this.setPosition(posX,posY);

        this.world = world;
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("testproject.json"));

        BodyDef bd = new BodyDef();
        bd.position.set((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        bd.type = BodyDef.BodyType.KinematicBody;
        bd.position.x = this.getX();
        bd.position.y = this.getY();
        body = world.createBody(bd);

        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 0.5f;
        fd.restitution = 0.3f;

        float scale = this.getWidth();
        loader.attachFixture(body,"player",fd,scale);
        this.setOrigin(this.getWidth()/2,this.getHeight()/2);
        body.setAngularVelocity(1);
        body.setUserData(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.addListener(new PlayerMovementListener(this));
    }

    public void setStateAndVelocity(PlayerState state) {

    }
}

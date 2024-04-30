package movement;

import com.badlogic.gdx.math.Vector2;

public enum PlayerState {
    MOVELEFT,
    MOVERIGHT,
    STANDING,
    SPEEDUP;
    public Vector2 calculateDirection(){
        return switch(this){
            case MOVELEFT -> new Vector2(-1,0);
            case MOVERIGHT -> new Vector2(1,0);
            case STANDING, SPEEDUP -> new Vector2(0,0);
        };
    }
}

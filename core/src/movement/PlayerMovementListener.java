package movement;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import object.CatchPlayField;

import java.util.HashSet;
import java.util.Set;

public class PlayerMovementListener extends InputListener {
    private final Set<Integer> pressedKeyCodes = new HashSet<>();
    private final CatchPlayField catchPlayField;
    public PlayerMovementListener(CatchPlayField catchPlayField) {
        this.catchPlayField = catchPlayField;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        pressedKeyCodes.add(keycode);

        // Step 1: Determining the state
        PlayerState state = getPlayerStateBasedOnCurrentlyPressedKeys();
        if(state == null){
            pressedKeyCodes.remove(keycode);
            return false;
        }

        catchPlayField.setStateAndVelocity(state);

        return true;
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        pressedKeyCodes.remove(keycode);

        PlayerState state = getPlayerStateBasedOnCurrentlyPressedKeys();
        Vector2 direction = Vector2.Zero;
        if(state != null){
            direction = state.calculateDirection();
        }
        updatePlayerState(state,direction);
        return true;
    }

    private void updatePlayerState(PlayerState state, Vector2 direction) {

    }

    private PlayerState getPlayerStateBasedOnCurrentlyPressedKeys() {
        if(pressedKeyCodes.contains(Input.Keys.LEFT)){
            if(pressedKeyCodes.contains(Input.Keys.RIGHT)){
                return PlayerState.STANDING;
            }else{
                return PlayerState.MOVELEFT;
            }
        }else if(pressedKeyCodes.contains(Input.Keys.RIGHT)){
            if(pressedKeyCodes.contains(Input.Keys.LEFT)){
                return PlayerState.STANDING;
            }else{
                return PlayerState.MOVERIGHT;
            }
        }else if(pressedKeyCodes.contains(Input.Keys.SHIFT_LEFT)){
            return PlayerState.SPEEDUP;
        }
        return null;
    }
}

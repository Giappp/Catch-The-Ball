package factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import object.Ball;
import object.CatchPlayField;

public class SpawnBallFactory {
    // delay time in second
    public static Array<Ball> spawnBall(int total, int delay){
        Array<Ball> balls = new Array<>();
        for(int i = 1; i <= total; i++){
            Ball ball = new Ball(MathUtils.random(64,
                    Gdx.graphics.getWidth() - 64),Gdx.graphics.getHeight(), 64,
                    i * 1000 * MathUtils.random(1,delay));
            balls.add(ball);
        }
        return CalculateHyperDash(balls);
    }

    private static Array<Ball> CalculateHyperDash(Array<Ball> balls) {
        for(int i = 0; i < balls.size - 1; i++){
            Ball currentBall = balls.get(i);
            Ball nextBall = balls.get(i+1);

            float distanceToNext = Math.abs(currentBall.ball.x - nextBall.ball.x) - CatchPlayField.ALLOWED_CATCH_RANGE;
            float timeToNext = nextBall.startTime - currentBall.startTime - Gdx.graphics.getDeltaTime();

            float distanceToHyper = Math.round(timeToNext * CatchPlayField.catcherSpeed - distanceToNext);

            if(distanceToHyper < 0){
                currentBall.hyperDashTarget = nextBall;
            }else{
                currentBall.distanceToHyperDash = distanceToHyper;
            }
        }
        return balls;
    }
}

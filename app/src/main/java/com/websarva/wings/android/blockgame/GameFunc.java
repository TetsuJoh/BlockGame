package com.websarva.wings.android.blockgame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

public class GameFunc {

    public static void drawItem(Canvas canvas, int x, int y, int width, int height, int color){
        if (canvas != null){
            Paint paint = new Paint();
            Rect rect = new Rect(x, y, x + width, y + height);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(color);
            canvas.drawRect(rect, paint);
        }
    }

    public static void drawBall(Canvas canvas, GameObject ball){
        if (canvas != null){
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(ball.color);
            double rad = Math.toRadians(ball.theta);
            double deltaX = ball.moveSpeed * Math.cos(rad);
            double deltaY = ball.moveSpeed * Math.sin(rad);
            ball.setX(ball.xPoint + (int)deltaX);
            ball.setY(ball.yPoint + (int)deltaY);
            canvas.drawCircle(ball.xPoint, ball.yPoint, ball.radius, paint);
        }
    }


    public static int blockCollisionCheck(GameObject ball, ArrayList<GameObject> blockList) {
        int collBlockIndex = -1;
        Rect ballRect = new Rect(ball.xPoint-ball.radius/2, ball.yPoint-ball.radius/2, ball.xPoint+ball.radius/2, ball.yPoint+ball.radius/2);
        for (int i = 0; i < blockList.size(); i++) {
            Rect blockRect = new Rect(blockList.get(i).xPoint, blockList.get(i).yPoint, blockList.get(i).xPoint + 150, blockList.get(i).yPoint + 100);
            if (Rect.intersects(ballRect, blockRect)) {
                collBlockIndex = i;
                break;
            }
        }
        return collBlockIndex;
    }

    public static boolean barCollisionCheck(GameObject ball, GameObject bar){
        boolean judge = false;
        Rect ballRect = new Rect(ball.xPoint-ball.radius/2, ball.yPoint-ball.radius/2, ball.xPoint+ball.radius/2, ball.yPoint+ball.radius/2);
        Rect barRect = new Rect(bar.xPoint, bar.yPoint, bar.xPoint + bar.xPoint, bar.yPoint + 50);
        if(Rect.intersects(ballRect, barRect)){
            judge = true;
        }
        return judge;
    }

}

package com.websarva.wings.android.blockgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.Log;
import android.view.View;
import android.view.MotionEvent;

import java.util.ArrayList;


public class MainSurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder holder;
    private static final String Tag = "MainSurfaceView";
    private Thread thread;
    private static final long FPS =30;// FPSを60に設定
    private static int surfaceWidth, surfaceHeight;
    float touchX, touchY;
    boolean flag = false;

    public  MainSurfaceView(Context context, SurfaceView surfaceview){
        surfaceview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                touchX = event.getX();
                touchY= event.getY();
                /*
                int touchAction = event.getAction();

                switch (touchAction) {
                    case MotionEvent.ACTION_DOWN:
                        flag = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        flag = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        flag = false;
                        break;
                }*/
                flag = true;
                return true;
            }
        });
        holder = surfaceview.getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int format, int width, int height){
        Log.d(Tag, "surfaceChanged");
        surfaceWidth = width;
        surfaceHeight = height;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0){
        Log.d(Tag, "surfaceCreated");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0){
        Log.d(Tag, "surfaceDestroyed");
        thread=null;
    }

    @Override
    public void run(){
        Log.d(Tag, "run");
        int ball_x = 500 , ball_y = 800, ball_radius = 30;

        //初期難易度(normalと仮定する
        int mode = 2; //1:easy, 2:normal, 3:hard
        int blockRow = 5, blockCol = 6, blockWidth = 100, blockHeight = 50;
        int bar_x = (int)surfaceWidth/3, bar_y = surfaceHeight-60;

        //Block初期配置マトリックス作成
        ArrayList<GameObject> blockList = new ArrayList<>();
        for (int i = 0; i < blockCol; i++){
            for (int j = 0; j < blockRow; j++){
                GameObject blocks = new GameObject(i*150+50, j*100 + 50, -16776961);
                blockList.add(blocks);
            }
        }

        //Ball初期配置
        GameObject ball = new GameObject(ball_x, ball_y, -65536, 20, 330, ball_radius);

        //Bar初期配置
        GameObject bar = new GameObject(bar_x, bar_y, -16776961);

        //main処理
        while(thread != null){
            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.BLACK); //背景描写
            int ballLen;
            Rect blockRect, ballRect;

            //Block描画
            for(int i=0; i < blockList.size(); i++){
                GameObject block = blockList.get(i);
                GameFunc.drawItem(canvas, block.xPoint, block.yPoint,blockWidth, blockHeight, block.color);
            }

            //Bar描画
            if(flag){
                bar.xPoint = (int)touchX;
                flag = false;
            }
            GameFunc.drawItem(canvas, bar.xPoint, bar.yPoint, bar_x, 50, bar.color);

            //Ball描画
            GameFunc.drawBall(canvas, ball);

            /*
            各種判定
             */
            //Block衝突判定
            int blockCollIndex = GameFunc.blockCollisionCheck(ball, blockList);
            if(blockCollIndex >= 0){
                blockList.remove(blockCollIndex);
                ball.theta *= -1;
            }

            //壁衝突判定
//            if(0 > ball.xPoint || viewWidth < ball.xPoint){
            if(ball.xPoint < 0 || ball.xPoint > surfaceWidth){
                ball.theta = 180 - ball.theta;
            } else if(ball.yPoint < 0){
                ball.theta *= -1;
            }

            //bar衝突判定
            if(GameFunc.barCollisionCheck(ball, bar)){
                ball.theta *= -1;
            }

            holder.unlockCanvasAndPost(canvas);

            try{
                thread.sleep(1000/FPS);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

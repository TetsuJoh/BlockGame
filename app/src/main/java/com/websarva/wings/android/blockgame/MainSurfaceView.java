package com.websarva.wings.android.blockgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.Log;

import java.util.ArrayList;

public class MainSurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder holder;
    private static final String Tag = "MainSurfaceView";
    private Thread thread;
    private static final long FPS =10;// FPSを60に設定

    public  MainSurfaceView(Context context, SurfaceView sv){
        holder = sv.getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3){
        Log.d(Tag, "surfaceChanged");

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0){
        Log.d(Tag, "surfaceCreated");
        //Canvas canvas = arg0.lockCanvas();
        //canvas.drawColor(Color.RED);
        //arg0.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0){
        Log.d(Tag, "surfaceDestroyed");
        thread=null;
    }

    @Override
    public void run(){
        Log.d(Tag, "run");
        int ball_x = 300 , ball_y = 300;
        int block_row = 6;

        //初期難易度(normalと仮定する
        int mode = 2; //1:easy, 2:normal, 3:hard
        int blockRow = 5, blockCol = 6;


        //Block初期配置マトリックス作成
        ArrayList<DrawItem> blockList = new ArrayList<DrawItem>();
        for (int i = 0; i < blockCol; i++){
            for (int j = 0; j < blockRow; j++){
                DrawItem blocks = new DrawItem(i*150+50, j*100 + 50, -16776961);
                blockList.add(blocks);
            }
        }

        while(thread != null){
            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.BLACK); //背景描写
            doDrawBall(canvas, ball_x, ball_y);
            for(int i=0; i < blockList.size(); i++){
                DrawItem block = blockList.get(i);
                doDrawBlock(canvas, block.xPoint, block.yPoint, block.color);
            }
            //ball_x += 10;
            //ball_y += 10;
            holder.unlockCanvasAndPost(canvas);

            try{
                thread.sleep(1000/FPS);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }
    }

    private void doDrawBall(Canvas canvas, int x, int y){
        if (canvas != null){
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLUE);
            canvas.drawCircle(x, y, 30, paint);
        }
    }

    private void doDrawBlock(Canvas canvas, int x, int y, int color){
        if (canvas != null){
            Paint paint = new Paint();
            Rect rect = new Rect(x, y, x+100, y+50);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(color);
            canvas.drawRect(rect, paint);
            /*
            rect.offset(100, 0);
            canvas.drawRect(rect, paint);
            rect.offset(100, 0);
            canvas.drawRect(rect, paint);
            */
        }
    }

    //星形描写コード
    /*private void doDraw(Canvas canvas){
        if(canvas != null){
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);

            Path path = new Path();
            float theta = (float)(Math.PI * 72/180);
            float r = 50f;
            PointF center = new PointF(200f, 80f);
            float dx1 = (float)(r*Math.sin(theta));
            float dx2 = (float)(r*Math.sin(2*theta));
            float dy1 = (float)(r*Math.cos(theta));
            float dy2 = (float)(r*Math.cos(2*theta));
            path.moveTo(center.x, center.y-r);
            path.lineTo(center.x-dx2, center.y-dy2);
            path.lineTo(center.x+dx1, center.y-dy1);
            path.lineTo(center.x-dx1, center.y-dy1);
            path.lineTo(center.x+dx2, center.y-dy2);
            path.lineTo(center.x, center.y-r);
            canvas.drawPath(path, paint);

        }

    }*/

}

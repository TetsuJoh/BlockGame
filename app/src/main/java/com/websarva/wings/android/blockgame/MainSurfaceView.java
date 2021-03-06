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

        //Block配置初期化
        ArrayList<ArrayList<Integer>> blockList = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < block_row; i++){
            ArrayList<Integer> blockSubList = new ArrayList<Integer>();
            blockSubList.add(i*150 + 50);
            blockSubList.add(100);
            blockList.add(blockSubList);
        }

        while(thread != null){

            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.BLACK); //背景描写
            doDrawBall(canvas, ball_x, ball_y);
            for(int i=0; i < block_row; i++){
                int block_x = blockList.get(i).get(0);
                int block_y = blockList.get(i).get(1);
                doDrawBlock(canvas, block_x, block_y);
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

    private void doDrawBlock(Canvas canvas, int x, int y){
        if (canvas != null){
            Paint paint = new Paint();
            Rect rect = new Rect(x, y, x+100, y+50);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.YELLOW);
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

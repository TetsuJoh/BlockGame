package com.websarva.wings.android.blockgame;

public class GameObject {
    /*
変数定義
 */
    int xPoint, yPoint, color, moveSpeed, theta, radius;

    //Block設置時
    public GameObject(int x, int y, int color){
        this.xPoint = x;
        this.yPoint = y;
        this.color = color;
        this.moveSpeed = 0;
        this.theta = 0;
    }

    //Ball設置時
    public GameObject(int x, int y, int color, int speed, int theta, int radius){
        this.xPoint = x;
        this.yPoint = y;
        this.color = color;
        this.moveSpeed = speed;
        this.theta = theta;
        this.radius = radius;
    }

    public void setX(int xPoint) {
        this.xPoint = xPoint;
    }

    public void setY(int y){
        this.yPoint = y;
    }
}

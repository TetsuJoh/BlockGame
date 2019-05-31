package com.websarva.wings.android.blockgame;

public class DrawItem {

    /*
    変数定義
     */
    int xPoint, yPoint, color, moveSpeed;;

    //Block設置時
    public DrawItem(int x, int y, int color){
        this.xPoint = x;
        this.yPoint = y;
        this.color = color;
        this.moveSpeed = 0;
    }

    //Ball設置時
    public DrawItem(int x, int y, int color, int speed){
        this.xPoint = x;
        this.yPoint = y;
        this.color = color;
        this.moveSpeed = speed;
    }
}

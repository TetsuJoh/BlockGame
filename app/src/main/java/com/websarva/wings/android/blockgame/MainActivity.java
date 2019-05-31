package com.websarva.wings.android.blockgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.SurfaceView;

public class MainActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private MainSurfaceView mainSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surfaceView = (SurfaceView)findViewById(R.id.SurfaceViewMain);
        mainSurfaceView = new MainSurfaceView(this, surfaceView);
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //getMenuInflater().inflate(R., menu);
        return true;
    }

    }
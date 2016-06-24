package com.yumingqin.garbagemonster;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class MainGamePanel extends SurfaceView implements

        SurfaceHolder.Callback {
    private MainThread thread;
    Paint p;
    private static final String TAG = MainGamePanel.class.getSimpleName();
    private Player player;
    private Bin trash;
    private Bin paper;
    private Bin cansBottles;
    private Bin biodegradable;
    private Bin biohazard;

    public MainGamePanel(Context context) {

        super(context);

        // adding the callback (this) to the surface holder to intercept events

        getHolder().addCallback(this);

        //create a player and load bitmap
        player = new Player(BitmapFactory.decodeResource(getResources(),R.drawable.garbage2penguin), 250, 250);
        trash = new Bin(BitmapFactory.decodeResource(getResources(), R.drawable.trash), 10, 10);

        //create the game loop thread
        thread = new MainThread(getHolder(), this);

        // make the GamePanel focusable so it can handle events

        setFocusable(true);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format,
                               int width, int height) {

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //surface created, can safely start game loop
        thread.setRunning(true);
        thread.start();
    }


    @Override

    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Surface is being destroyed");
        //tell the thread to shut down and wait for it to finish
        //this is a clean shutdown
        boolean retry = true;
        while(retry) {
            try {
                thread.join();
                retry = false;
            } catch(InterruptedException e) {
                //try again shutting down the thread
            }
        }
        Log.d(TAG, "Thread was shut down cleanly");
    }


    @Override

    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //delegate event handling to player class
            player.handleActionDown((int) event.getX(), (int) event.getY());
            if(event.getY() > getHeight() - 50) {
                thread.setRunning(false);
                ((Activity) getContext()).finish();
            } else {
                Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
            }
        } if (event.getAction() == MotionEvent.ACTION_MOVE) {
            //the gestures
            if (player.isTouched()) {
                //the player is touched and is being dragged
                player.setX((int) event.getX());
                player.setY((int) event.getY());
            }
        } if (event.getAction() == MotionEvent.ACTION_UP) {
            //touch was released
            if(player.isTouched()) {
                player.setTouched(false);
            }
        }
        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {

        //fills canvas with blue
        canvas.drawColor(Color.BLUE);
        player.draw(canvas);
//        super.onDraw(canvas);
//        p = new Paint();
//        BitmapFactory.Options opts = new BitmapFactory.Options();
//        opts.inSampleSize = 4;
//        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.garbage0penguin, opts);
//        canvas.drawBitmap(b, 0, 0, null);
// canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.garbage0penguin), 100, 100, null);

    }

}

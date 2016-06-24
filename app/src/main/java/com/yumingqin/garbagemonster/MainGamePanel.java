package com.yumingqin.garbagemonster;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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

    public MainGamePanel(Context context) {

        super(context);

        // adding the callback (this) to the surface holder to intercept events

        getHolder().addCallback(this);

        //create the game loop thread
        thread = new MainThread(getHolder(), this);

        // make the GamePanel focusable so it can handle events

        setFocusable(true);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }


    @Override

    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry) {
            try {
                thread.join();
                retry = false;
            } catch(InterruptedException e) {
                //try again shutting down the thread
            }
        }
    }


    @Override

    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if(event.getY() > getHeight() - 50) {
                thread.setRunning(false);
                ((Activity) getContext()).finish();
            } else {
                Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
            }
        }
        return super.onTouchEvent(event);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        p = new Paint();
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 4;
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.garbage0penguin, opts);
        canvas.drawBitmap(b, 0, 0, null);
// canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.garbage0penguin), 100, 100, null);

    }

}

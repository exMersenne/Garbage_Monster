package com.yumingqin.garbagemonster;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by yumin on 6/24/2016.
 */
public class MainThread extends Thread {
    //flag to hold game state
    private SurfaceHolder surfaceHolder;
    private MainGamePanel gamePanel;
    private boolean running;
    private static final String TAG = MainThread.class.getSimpleName();

    public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override public void run() {
        Canvas canvas;
//        long tickCount = 0L;
        Log.d(TAG, "Starting game loop");
        while(running) {
            canvas = null;
            //try locking the canvas for exclusive pixel editing on the surface
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    //update game state
                    //draws canvas on the panel
                    this.gamePanel.onDraw(canvas);
                }
            } finally {
                //in case of an exception the surface is not left in an inconsistent state
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
//            tickCount++;

            //render state to screen
        }
//        Log.d(TAG, "Game loop executed " + tickCount + " times");
    }
}

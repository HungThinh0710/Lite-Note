package com.ozoz.hungthinh.breakappgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MyView extends View implements Runnable{
    private int x1=100, y1=100, dx1=20, dy1=20;
    private int x2=600, y2=600, dx2=20, dy2=20;

    private Bitmap ball;
    private Bitmap ballResize;
    private Bitmap bgBitmap;
    private Bitmap bar;
    private Bitmap barResize;
    private int xBar,yBar;
    ArrayList<Brick> lists;


    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ball = BitmapFactory.decodeResource(getResources(), R.drawable.ic_ball);
        ballResize = Bitmap.createScaledBitmap(ball,50,50,false);
        bgBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bg_bitmap);
        bar = BitmapFactory.decodeResource(getResources(),R.drawable.ic_thanh_ne);
        barResize = Bitmap.createScaledBitmap(bar,300,50,false);
        xBar = 1080-500;
        lists = new ArrayList<Brick>();
        for (int i = 0; i < 10; i++) {
            Brick brick = new Brick(110 * i , 0, 105, 70);
            Brick brick2 = new Brick(110 * i , 80, 105, 70);
            Brick brick3 = new Brick(110 * i , 0, 105, 70);
            Brick brick4 = new Brick(110 * i , 0, 105, 70);
            Brick brick5 = new Brick(110 * i , 0, 105, 70);

            lists.add(brick);
            lists.add(brick2);
        }
    }


    @Override
    public void run() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int x = getWidth();
        int y = getHeight();
        int radius = 100;


        Paint paint = new Paint();
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.WHITE);
//        canvas.drawPaint(paint);
//        paint.setColor(Color.parseColor("#CD5C5C"));

        canvas.drawBitmap(bgBitmap, 0, 0, null);

//        canvas.drawCircle(x1/,y1,20,paint);
        canvas.drawBitmap(ballResize, x2, y2, null);
        yBar = 1584 - 150;
        canvas.drawBitmap(barResize,xBar,yBar,null);

            if(y2 > yBar-50 && x2 > xBar && x2 < xBar + 305){
                dy2 = -dy2;
            }
            if(y2 > yBar+20){
                Toast.makeText(getContext(), "GAME OVER", Toast.LENGTH_LONG).show();
                return;
            }

        for(Brick element : lists){
            element.drawBrick(canvas, paint);
            if(element.getVisibility()) {
                if(y2 < element.getY() && x2 > element.getX() && x2 < (element.getX() + element.getWidth())){
                    element.setInVisible();
                    dy2 = -dy2;
                }
                //kiểm tra ball va chạm với gạch
                // viên nào bể thì set visible = false
            }
        }


        update();
        invalidate();
    }



    private void update() {
//        x1 += dx1;
//        y1 += dy1;
//        //Block Frame
//        if(x1 >= this.getWidth() || x1 < 0)
//            dx1 = -dx1;
//        if(y1 >= this.getHeight() || y1 < 0)
//            dy1 = -dy1;


        x2 += dx2;
        y2 += dy2;
        //Block Frame
        if(x2 >= this.getWidth() || x2 < 0) {
            dx2 = -dx2;
        }
//        y2 >= this.getHeight() ||
        if(y2 < 0) {
            dy2 = -dy2;
        }
        //End Block



    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean handled = false;

        int xTouch;
        int yTouch;
        int actionIndex = event.getActionIndex();


        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                xTouch = (int) event.getX(0);
                yTouch = (int) event.getY(0);



                handled = true;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                xTouch = (int) event.getX(actionIndex);
                yTouch = (int) event.getY(actionIndex);


                handled = true;
                break;

            case MotionEvent.ACTION_MOVE:
                final int pointerCount = event.getPointerCount();

                for (actionIndex = 0; actionIndex < pointerCount; actionIndex++) {

                    xTouch = (int) event.getX(actionIndex);
                    yTouch = (int) event.getY(actionIndex);
                    xBar = xTouch;
                    Log.d("debb", String.valueOf(xBar));
                }


                invalidate();
                handled = true;
                break;

            case MotionEvent.ACTION_UP:

                invalidate();
                handled = true;
                break;

            case MotionEvent.ACTION_POINTER_UP:

                invalidate();
                handled = true;
                break;

            case MotionEvent.ACTION_CANCEL:

                handled = true;
                break;

            default:
                // do nothing
                break;
        }

        return super.onTouchEvent(event) || handled;

    }



}

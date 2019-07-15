package com.example.theflyingfishgameapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingFishView extends View {
    private Bitmap fish[] = new Bitmap[2];
    private int fishX=10;
    private int fishY;
    private int fishSpeed;
    private int canvasWidth,canvasHeight;

    private int yellowX,yellowY,yellowSpeed=8;
    private Paint yellowPaint =new Paint();

    private int blueX , blueY , blueSpeed=10;
    private Paint bluePaint = new Paint();

    private int redX , redY , redSpeed=12;
    private Paint redPaint = new Paint();

    private int score , lifeCounterofFish;

    private boolean touch = false;

    private Bitmap backgroundImage;
    private Paint scorePaint = new Paint();
    private Bitmap life[] = new Bitmap[2];

    public FlyingFishView(Context context) {
        super(context);
        fish[0] = BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(),R.drawable.fish2);
        backgroundImage = BitmapFactory.decodeResource(getResources(),R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        bluePaint.setColor(Color.BLUE);
        bluePaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);


        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(40);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);
        life[0]=BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1]=BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);
        fishY = 550;
        score=0;
        lifeCounterofFish=3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        canvas.drawBitmap(backgroundImage,0,0,null);
        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHeight - fish[0].getHeight();
        fishY = fishY + fishSpeed;
        if(fishY<minFishY)
        {
            fishY = minFishY;
        }
        if(fishY>maxFishY)
        {
            fishY = maxFishY;
        }
        fishSpeed = fishSpeed + 2;

        if(touch)
        {
            canvas.drawBitmap(fish[1],fishX,fishY,null);
            touch = false;
        }

        yellowX=yellowX-yellowSpeed;
        if(hitBallChecker(yellowX ,yellowY))
        {
            score = score + 10;
            yellowX = -100;
        }
        else {
            canvas.drawBitmap(fish[0],fishX,fishY,null);
        }

        if(yellowX <0)
        {
            yellowX = canvasWidth +21;
            yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(yellowX,yellowY, 15,yellowPaint);


        blueX=blueX-blueSpeed;
        if(hitBallChecker(blueX ,blueY))
        {
            score = score + 20;
            blueX = -100;
        }
        else {
            canvas.drawBitmap(fish[0],fishX,fishY,null);
        }

        if(blueX <0)
        {
            blueX = canvasWidth +21;
            blueY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(blueX,blueY,15,bluePaint);


        redX=redX-redSpeed;
        if(hitBallChecker(redX ,redY))
        {
            redX = -100;
            lifeCounterofFish--;
            if(lifeCounterofFish==0){
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();
                Intent gameOverIntent = new Intent(getContext(), GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverIntent.putExtra("score",score);
                getContext().startActivity(gameOverIntent);

            }
        }
        else {
            canvas.drawBitmap(fish[0],fishX,fishY,null);
        }

        if(redX <0)
        {
            redX = canvasWidth +21;
            redY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(redX,blueY,15,redPaint);

        canvas.drawText("Score : " + score,20,60,scorePaint);

        for (int i=0;i<3;i++){
            int x=(int) (330+life[0].getWidth() * 1.5 *i);
            int y=30;
            if (i<lifeCounterofFish){
                canvas.drawBitmap(life[0],x,y,null);
            }
            else {
                canvas.drawBitmap(life[1],x,y,null);
            }
        }

    }

    public boolean hitBallChecker(int x , int y)
    {
        if(fishX <x && x<(fishX + fish[0].getWidth()) && fishY <y && y<(fishY +fish[0].getHeight()))
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN)
        {
            touch=true;

            fishSpeed = -22;
        }
        return true;
    }
}

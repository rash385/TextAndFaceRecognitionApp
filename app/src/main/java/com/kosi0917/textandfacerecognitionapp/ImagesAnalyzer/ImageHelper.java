package com.kosi0917.textandfacerecognitionapp.ImagesAnalyzer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.microsoft.projectoxford.emotion.contract.FaceRectangle;

import java.util.List;

/**
 * Created by sivko on 23.01.2018.
 */

public class ImageHelper {
    public static Bitmap drawRectOnBitmap(Bitmap mBitmap, FaceRectangle faceRectangle, List<String> emotionList, String emotionStatus){
        Bitmap bitmap = mBitmap.copy(Bitmap.Config.ARGB_8888,true);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(8);

        canvas.drawRect(
                faceRectangle.left,
                faceRectangle.top,
                faceRectangle.left + faceRectangle.width,
                faceRectangle.top + faceRectangle.height,
                paint
        );

        int cX = faceRectangle.left + faceRectangle.width;
        int cY = faceRectangle.top + faceRectangle.height;

        drawTextOnBitmap(canvas,30,faceRectangle.left/2 + cX/5,cY+70,Color.WHITE,"Emotion here: "+ emotionStatus);
        cY=70;
        //cY= faceRectangle.top - 40;
        for (String emotion: emotionList) {
            drawTextOnBitmap(canvas,20,50,cY,Color.WHITE, emotion);
            cY=cY+25;
        }

        return bitmap;
    }

    private static void drawTextOnBitmap(Canvas canvas, int textSize, int cX, int cY, int color, String status) {

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        paint.setTextSize(textSize);

        canvas.drawText(status,cX,cY,paint);
    }
}

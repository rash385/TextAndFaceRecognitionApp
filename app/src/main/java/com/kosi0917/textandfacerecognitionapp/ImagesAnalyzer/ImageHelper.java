package com.kosi0917.textandfacerecognitionapp.ImagesAnalyzer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.microsoft.projectoxford.emotion.contract.FaceRectangle;
import com.microsoft.projectoxford.emotion.contract.RecognizeResult;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;

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
            drawTextOnBitmap(canvas,20,50,cY,Color.BLACK, emotion);
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

    public static void drawStatics(RecognizeResult result, ColumnChartView chart) {
        int numSubcolumns = 1;
        // Column can have many stacked subcolumns, here I use 4 stacke subcolumn in each of 4 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < 8; ++i) {
            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue((float) (result.scores.anger * 10e+6), ChartUtils.pickColor()));
                values.add(new SubcolumnValue((float) (result.scores.contempt * 10e+6), ChartUtils.pickColor()));
                values.add(new SubcolumnValue((float) (result.scores.disgust * 10e+6), ChartUtils.pickColor()));
                values.add(new SubcolumnValue((float) (result.scores.fear * 10e+6), ChartUtils.pickColor()));
                values.add(new SubcolumnValue((float) (result.scores.happiness * 10e+6), ChartUtils.pickColor()));
                values.add(new SubcolumnValue((float) (result.scores.neutral * 10e+6), ChartUtils.pickColor()));
                values.add(new SubcolumnValue((float) (result.scores.sadness * 10e+6), ChartUtils.pickColor()));
                values.add(new SubcolumnValue((float) (result.scores.surprise * 10e+6), ChartUtils.pickColor()));
            }

            Column column = new Column(values);
            columns.add(column);
        }


        ColumnChartData data = new ColumnChartData(columns);
        chart.setColumnChartData(data);
    }
}

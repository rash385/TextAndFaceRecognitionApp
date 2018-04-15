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

import lecho.lib.hellocharts.model.Axis;
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

    public static void drawStatics(RecognizeResult result, ColumnChartView chart) {
        ColumnChartData data;
        boolean hasAxes = true;
        // Column can have many stacked subcolumns, here I use 4 stacke subcolumn in each of 4 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;

            values = new ArrayList<SubcolumnValue>();

            values.add(new SubcolumnValue((float) result.scores.anger * 20f + 5, ChartUtils.pickColor()));
            Column column = new Column(values);
            columns.add(column);

            values = new ArrayList<SubcolumnValue>();
            values.add(new SubcolumnValue((float) result.scores.contempt * 20f + 5, ChartUtils.pickColor()));
            Column column1 = new Column(values);
            columns.add(column1);


            values = new ArrayList<SubcolumnValue>();
            values.add(new SubcolumnValue((float) result.scores.happiness * 20f + 5, ChartUtils.pickColor()));
            column = new Column(values);
            columns.add(column);

            values = new ArrayList<SubcolumnValue>();
            values.add(new SubcolumnValue((float) result.scores.disgust * 20f + 5, ChartUtils.pickColor()));
            column = new Column(values);
            columns.add(column);

            values = new ArrayList<SubcolumnValue>();
            values.add(new SubcolumnValue((float) result.scores.fear * 20f + 5, ChartUtils.pickColor()));
            column = new Column(values);
            columns.add(column);

            values = new ArrayList<SubcolumnValue>();
            values.add(new SubcolumnValue((float) result.scores.neutral * 20f + 5, ChartUtils.pickColor()));
            column = new Column(values);
            columns.add(column);

            values = new ArrayList<SubcolumnValue>();
            values.add(new SubcolumnValue((float) result.scores.sadness * 20f + 5, ChartUtils.pickColor()));
            column = new Column(values);
            columns.add(column);

            values = new ArrayList<SubcolumnValue>();
            values.add(new SubcolumnValue((float) result.scores.surprise * 20f + 5, ChartUtils.pickColor()));
            column = new Column(values);
            columns.add(column);

            // column.setHasLabels(hasLabels);
            //column.setHasLabelsOnlyForSelected(hasLabelForSelected);



        data = new ColumnChartData(columns);

        // Set stacked flag.
        data.setStacked(true);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            //if (hasAxesNames) {
             axisX.setName("Emotion type");
             axisY.setName("Emotion value");
            // }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        chart.setColumnChartData(data);
    }

    public static void drawStaticsForAll(LineChartView chart, List<Double> emotionTypeList){
        List<PointValue> values = new ArrayList<PointValue>();
        boolean hasAxes = true;
        int i=0;
        for (Double emValue: emotionTypeList)
            values.add(new PointValue(i++, Float.valueOf(String.valueOf(emValue))));

        //In most cased you can call data model methods in builder-pattern-like manner.
        Line line = new Line(values).setColor(Color.BLUE).setCubic(true);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            //if (hasAxesNames) {
            axisX.setName("Emotion type");
            axisY.setName("Emotion value");
            // }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }
        emotionTypeList.removeAll(emotionTypeList);
        chart.setLineChartData(data);
    }
}

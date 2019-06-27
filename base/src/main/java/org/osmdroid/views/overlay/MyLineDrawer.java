package org.osmdroid.views.overlay;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import org.osmdroid.util.LineBuilder;

public class MyLineDrawer extends LineBuilder {

    private Canvas mCanvas;
    private Paint mPaint;

    public MyLineDrawer(int pMaxSize) {
        super(pMaxSize);
    }

    public void setCanvas(final Canvas pCanvas) {
        mCanvas = pCanvas;
    }

    public void setPaint(final Paint pPaint) {
        mPaint = pPaint;
    }

    @Override
    public void flush() {
        if (getSize() >= 4) {
            float sx=getLines()[0];
            float sy=getLines()[1];
            float ex=getLines()[2];
            float ey=getLines()[3];
            drawAL(sx,sy,ex,ey);
            mCanvas.drawLines(getLines(), 0, getSize(), mPaint);
        }
    }

    public void drawAL(float fx, float fy, float sx, float sy) {
        double H = 8;
        double L = 3.5;
        int x3 = 0;
        int y3 = 0;
        int x4 = 0;
        int y4 = 0;
        double awrad = Math.atan(L / H);
        double arraow_len = Math.sqrt(L * L + H * H);
        double[] arrXY_1 = rotateVec(sx - fx, sy - fy, awrad, true, arraow_len);
        double[] arrXY_2 = rotateVec(sx - fx, sy - fy, -awrad, true, arraow_len);
        double x_3 = sx - arrXY_1[0];
        double y_3 = sy - arrXY_1[1];
        double x_4 = sx - arrXY_2[0];
        double y_4 = sy - arrXY_2[1];
        Double X3 = new Double(x_3);
        x3 = X3.intValue();
        Double Y3 = new Double(y_3);
        y3 = Y3.intValue();
        Double X4 = new Double(x_4);
        x4 = X4.intValue();
        Double Y4 = new Double(y_4);
        y4 = Y4.intValue();
        mCanvas.drawLine(fx, fy, sx, sy, mPaint);
        Path triangle = new Path();
        triangle.moveTo(sx, sy);
        triangle.lineTo(x3, y3);
        triangle.lineTo(x4, y4);
        triangle.close();
        mCanvas.drawPath(triangle, mPaint);
    }

    public double[] rotateVec(float px, float py, double ang, boolean isChLen, double newLen) {
        double mathstr[] = new double[2];
        double vx = px * Math.cos(ang) - py * Math.sin(ang);
        double vy = px * Math.sin(ang) + py * Math.cos(ang);
        if (isChLen) {
            double d = Math.sqrt(vx * vx + vy * vy);
            vx = vx / d * newLen;
            vy = vy / d * newLen;
            mathstr[0] = vx;
            mathstr[1] = vy;
        }
        return mathstr;
    }
}

package leu.laboratoare.test_senzori;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class JoystickView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float xValue = 0;
    private float yValue = 0;

    public JoystickView(Context context) {
        super(context);
    }

    public JoystickView(Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    public void updateJoystick(float x, float y) {
        xValue = Math.max(-1, Math.min(1, x));
        yValue = Math.max(-1, Math.min(1, y));
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();

        float centerX = width / 2;
        float centerY = height / 2;

        float radius = Math.min(width, height) / 3;
        float knobRadius = radius / 4;

        float knobX = centerX + xValue * radius;
        float knobY = centerY + yValue * radius;

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        canvas.drawCircle(centerX, centerY, radius, paint);

        canvas.drawLine(centerX - radius, centerY, centerX + radius, centerY, paint);
        canvas.drawLine(centerX, centerY - radius, centerX, centerY + radius, paint);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(knobX, knobY, knobRadius, paint);
    }
}
package leu.laboratoare.lab11;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class PieChartView extends View {

    private final Paint slicePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF chartBounds = new RectF();
    private final int[] colors = {
            Color.rgb(76, 217, 207),
            Color.rgb(0, 0, 0),
            Color.rgb(24, 98, 201),
            Color.rgb(47, 161, 57),
            Color.rgb(245, 166, 35),
            Color.rgb(235, 87, 87),
            Color.rgb(155, 81, 224),
            Color.rgb(0, 163, 255),
            Color.rgb(127, 140, 141),
            Color.rgb(241, 196, 15)
    };

    private int[] values = new int[0];

    public PieChartView(Context context) {
        super(context);
        init();
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        slicePaint.setStyle(Paint.Style.FILL);

        outlinePaint.setColor(Color.WHITE);
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setStrokeWidth(4f);
    }

    public void setValues(int[] bundleValues) {
        if (bundleValues == null || bundleValues.length <= 1) {
            values = new int[0];
        } else {
            values = new int[bundleValues.length - 1];
            System.arraycopy(bundleValues, 1, values, 0, values.length);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (values.length == 0) {
            return;
        }

        int total = 0;
        for (int value : values) {
            total += Math.max(value, 0);
        }

        if (total == 0) {
            return;
        }

        float width = getWidth();
        float height = getHeight();
        float padding = 32f;
        float diameter = Math.min(width, height) - 2 * padding;

        if (diameter <= 0) {
            return;
        }

        float left = (width - diameter) / 2f;
        float top = (height - diameter) / 2f;
        chartBounds.set(left, top, left + diameter, top + diameter);

        float startAngle = -90f;
        for (int i = 0; i < values.length; i++) {
            float sweepAngle = (values[i] * 360f) / total;
            if (i == values.length - 1) {
                sweepAngle = 270f - startAngle;
            }
            slicePaint.setColor(colors[i % colors.length]);
            canvas.drawArc(chartBounds, startAngle, sweepAngle, true, slicePaint);
            canvas.drawArc(chartBounds, startAngle, sweepAngle, true, outlinePaint);
            startAngle += sweepAngle;
        }
    }
}

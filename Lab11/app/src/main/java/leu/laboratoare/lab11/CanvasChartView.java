package leu.laboratoare.lab11;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CanvasChartView extends View {

    public static final int CHART_PIE = 0;
    public static final int CHART_COLUMN = 1;
    public static final int CHART_BAR = 2;

    private final Paint shapePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF chartBounds = new RectF();
    private final int[] colors = {
            Color.rgb(76, 217, 207),
            Color.rgb(98, 28, 28),
            Color.rgb(76, 133, 213),
            Color.rgb(3, 152, 15),
            Color.rgb(245, 166, 35),
            Color.rgb(235, 87, 87),
            Color.rgb(155, 81, 224),
            Color.rgb(0, 163, 255),
            Color.rgb(127, 140, 141),
            Color.rgb(241, 196, 15)
    };

    private int[] values = new int[0];
    private int chartType = CHART_PIE;

    public CanvasChartView(Context context) {
        super(context);
        init();
    }

    public CanvasChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CanvasChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        shapePaint.setStyle(Paint.Style.FILL);

        outlinePaint.setColor(Color.WHITE);
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setStrokeWidth(4f);

        axisPaint.setColor(Color.DKGRAY);
        axisPaint.setStyle(Paint.Style.STROKE);
        axisPaint.setStrokeWidth(3f);
    }

    public void setValues(int[] bundleValues) {
        if (bundleValues == null || bundleValues.length == 0) {
            values = new int[0];
        } else {
            values = new int[bundleValues.length];
            System.arraycopy(bundleValues, 0, values, 0, bundleValues.length);
        }
        invalidate();
    }

    public void setChartType(int chartType) {
        this.chartType = chartType;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (values.length == 0) {
            return;
        }

        switch (chartType) {
            case CHART_COLUMN:
                drawColumnChart(canvas);
                break;
            case CHART_BAR:
                drawBarChart(canvas);
                break;
            default:
                drawPieChart(canvas);
                break;
        }
    }

    private void drawPieChart(Canvas canvas) {
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
            shapePaint.setColor(colors[i % colors.length]);
            canvas.drawArc(chartBounds, startAngle, sweepAngle, true, shapePaint);
            canvas.drawArc(chartBounds, startAngle, sweepAngle, true, outlinePaint);
            startAngle += sweepAngle;
        }
    }

    private void drawColumnChart(Canvas canvas) {
        float width = getWidth();
        float height = getHeight();
        float leftPadding = 72f;
        float rightPadding = 32f;
        float topPadding = 32f;
        float bottomPadding = 48f;

        float chartLeft = leftPadding;
        float chartTop = topPadding;
        float chartRight = width - rightPadding;
        float chartBottom = height - bottomPadding;

        if (chartRight <= chartLeft || chartBottom <= chartTop) {
            return;
        }

        canvas.drawLine(chartLeft, chartTop, chartLeft, chartBottom, axisPaint);
        canvas.drawLine(chartLeft, chartBottom, chartRight, chartBottom, axisPaint);

        int maxValue = getMaxValue();
        if (maxValue <= 0) {
            return;
        }

        float chartWidth = chartRight - chartLeft;
        float chartHeight = chartBottom - chartTop;
        float slotWidth = chartWidth / values.length;
        float barWidth = slotWidth * 0.55f;

        for (int i = 0; i < values.length; i++) {
            float barHeight = (values[i] / (float) maxValue) * chartHeight;
            float left = chartLeft + i * slotWidth + (slotWidth - barWidth) / 2f;
            float top = chartBottom - barHeight;
            float right = left + barWidth;

            shapePaint.setColor(colors[i % colors.length]);
            canvas.drawRect(left, top, right, chartBottom, shapePaint);
        }
    }

    private void drawBarChart(Canvas canvas) {
        float width = getWidth();
        float height = getHeight();
        float leftPadding = 32f;
        float rightPadding = 32f;
        float topPadding = 32f;
        float bottomPadding = 32f;
        float labelArea = 48f;

        float chartLeft = leftPadding + labelArea;
        float chartTop = topPadding;
        float chartRight = width - rightPadding;
        float chartBottom = height - bottomPadding;

        if (chartRight <= chartLeft || chartBottom <= chartTop) {
            return;
        }

        canvas.drawLine(chartLeft, chartTop, chartLeft, chartBottom, axisPaint);
        canvas.drawLine(chartLeft, chartBottom, chartRight, chartBottom, axisPaint);

        int maxValue = getMaxValue();
        if (maxValue <= 0) {
            return;
        }

        float chartWidth = chartRight - chartLeft;
        float chartHeight = chartBottom - chartTop;
        float slotHeight = chartHeight / values.length;
        float barHeight = slotHeight * 0.55f;

        for (int i = 0; i < values.length; i++) {
            float barWidth = (values[i] / (float) maxValue) * chartWidth;
            float top = chartTop + i * slotHeight + (slotHeight - barHeight) / 2f;
            float bottom = top + barHeight;

            shapePaint.setColor(colors[i % colors.length]);
            canvas.drawRect(chartLeft, top, chartLeft + barWidth, bottom, shapePaint);
        }
    }

    private int getMaxValue() {
        int maxValue = 0;
        for (int value : values) {
            maxValue = Math.max(maxValue, value);
        }
        return maxValue;
    }
}

package Custom_UI;

import Custom_UI.SlidingTabLayout.TabColorizer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

class SlidingTabStrip extends LinearLayout {
    private static final byte DEFAULT_BOTTOM_BORDER_COLOR_ALPHA = (byte) 38;
    private static final int DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 0;
    private static final int DEFAULT_SELECTED_INDICATOR_COLOR = -13388315;
    private static final int SELECTED_INDICATOR_THICKNESS_DIPS = 3;
    private final Paint mBottomBorderPaint;
    private final int mBottomBorderThickness;
    private TabColorizer mCustomTabColorizer;
    private final SimpleTabColorizer mDefaultTabColorizer;
    private final Paint mSelectedIndicatorPaint;
    private final int mSelectedIndicatorThickness;
    private int mSelectedPosition;
    private float mSelectionOffset;

    private static class SimpleTabColorizer implements TabColorizer {
        private int[] mIndicatorColors;

        private SimpleTabColorizer() {
        }

        public final int getIndicatorColor(int position) {
            return this.mIndicatorColors[position % this.mIndicatorColors.length];
        }

        void setIndicatorColors(int... colors) {
            this.mIndicatorColors = colors;
        }
    }

    SlidingTabStrip(Context context) {
        this(context, null);
    }

    private SlidingTabStrip(Context context, AttributeSet attrs) {
        super(context, null);
        setWillNotDraw(false);
        float density = getResources().getDisplayMetrics().density;
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(16842800, outValue, true);
        int mDefaultBottomBorderColor = setColorAlpha(outValue.data);
        this.mDefaultTabColorizer = new SimpleTabColorizer();
        this.mDefaultTabColorizer.setIndicatorColors(DEFAULT_SELECTED_INDICATOR_COLOR);
        this.mBottomBorderThickness = (int) (0.0f * density);
        this.mBottomBorderPaint = new Paint();
        this.mBottomBorderPaint.setColor(mDefaultBottomBorderColor);
        this.mSelectedIndicatorThickness = (int) (3.0f * density);
        this.mSelectedIndicatorPaint = new Paint();
    }

    void setCustomTabColorizer(TabColorizer customTabColorizer) {
        this.mCustomTabColorizer = customTabColorizer;
        invalidate();
    }

    void setSelectedIndicatorColors(int... colors) {
        this.mCustomTabColorizer = null;
        this.mDefaultTabColorizer.setIndicatorColors(colors);
        invalidate();
    }

    void onViewPagerPageChanged(int position, float positionOffset) {
        this.mSelectedPosition = position;
        this.mSelectionOffset = positionOffset;
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        int height = getHeight();
        int childCount = getChildCount();
        TabColorizer tabColorizer = this.mCustomTabColorizer != null ? r0.mCustomTabColorizer : r0.mDefaultTabColorizer;
        if (childCount > 0) {
            View selectedTitle = getChildAt(r0.mSelectedPosition);
            int left = selectedTitle.getLeft();
            int right = selectedTitle.getRight();
            int color = tabColorizer.getIndicatorColor(r0.mSelectedPosition);
            if (r0.mSelectionOffset > 0.0f && r0.mSelectedPosition < getChildCount() - 1) {
                int nextColor = tabColorizer.getIndicatorColor(r0.mSelectedPosition + 1);
                if (color != nextColor) {
                    color = blendColors(nextColor, color, r0.mSelectionOffset);
                }
                View nextTitle = getChildAt(r0.mSelectedPosition + 1);
                left = (int) ((r0.mSelectionOffset * ((float) nextTitle.getLeft())) + ((1.0f - r0.mSelectionOffset) * ((float) left)));
                right = (int) ((r0.mSelectionOffset * ((float) nextTitle.getRight())) + ((1.0f - r0.mSelectionOffset) * ((float) right)));
            }
            r0.mSelectedIndicatorPaint.setColor(color);
            canvas.drawRect((float) left, (float) (height - r0.mSelectedIndicatorThickness), (float) right, (float) height, r0.mSelectedIndicatorPaint);
        }
        canvas.drawRect(0.0f, (float) (height - r0.mBottomBorderThickness), (float) getWidth(), (float) height, r0.mBottomBorderPaint);
    }

    private static int setColorAlpha(int color) {
        return Color.argb(38, Color.red(color), Color.green(color), Color.blue(color));
    }

    private static int blendColors(int color1, int color2, float ratio) {
        float inverseRation = 1.0f - ratio;
        return Color.rgb((int) ((((float) Color.red(color1)) * ratio) + (((float) Color.red(color2)) * inverseRation)), (int) ((((float) Color.green(color1)) * ratio) + (((float) Color.green(color2)) * inverseRation)), (int) ((((float) Color.blue(color1)) * ratio) + (((float) Color.blue(color2)) * inverseRation)));
    }
}

package uk.co.myapplication.Drawables;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * Drawable that generates the two pieces of text in the track of the switch, one of each
 * side of the positions of the thumb.
 */
public class SwitchTrackTextDrawable extends Drawable {

    private final Context mContext;

    private final String mPermenantext;

    private final String mTemporaryText;

    private final Paint mTextPaint;

    public SwitchTrackTextDrawable(@NonNull Context context,
                                   @StringRes int permanentText,
                                   @StringRes int temporaryText) {
        mContext = context;

        mTemporaryText = context.getString(temporaryText);
        mTextPaint = createTextPaint();

        mPermenantext = context.getString(permanentText);
    }

    private Paint createTextPaint() {
        Paint textPaint = new Paint();
        //noinspection deprecation
        textPaint.setColor(mContext.getResources().getColor(android.R.color.white));
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Paint.Align.CENTER);
        // Set textSize, typeface, etc, as you wish
        return textPaint;
    }

    @Override
    public void draw(Canvas canvas) {
        final Rect textBounds = new Rect();
        mTextPaint.getTextBounds(mTemporaryText, 0, mTemporaryText.length(), textBounds);
        // The baseline for the text: centered, including the height of the text itself
        final int heightBaseline = canvas.getClipBounds().height() / 2 + textBounds.height() / 2;

        // This is one quarter of the full width, to measure the centers of the texts
        final int widthQuarter = canvas.getClipBounds().width() / 4;
        canvas.drawText(mTemporaryText, 0, mTemporaryText.length(),
                widthQuarter * 3, heightBaseline,
                mTextPaint);
        canvas.drawText(mPermenantext, 0, mPermenantext.length(),
                widthQuarter , heightBaseline,
                mTextPaint);
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}

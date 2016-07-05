/*
 * Copyright 2016 Alexandre H.P. Chaves
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.androidplot.logplotandroid;

//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.graphics.RectF;
//import android.support.annotation.Nullable;
import android.util.TypedValue;

import static com.example.androidplot.logplotandroid.SavedMetrics.Metrics;

/**
 * Created by achaves on 13-05-2016.
 *
 */
public class Utils {

    public Utils() {}

    /**
     * Converts a dp value to pixels.
     * @param dp dependent pixel size
     * @return correspondent pixel value of dp.
     */
    public static float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Metrics.metrics);
    }

    /**
     * Converts sp units into pixels. Use this to define font size.
     * @param sp value in sp units
     * @return value of sp in correspondent pixels
     */
    public static float spToPx(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Metrics.metrics);
    }

//    /**
//     * @param paint  definitions where the text are set
//     * @return the height of the tallest character that can be drawn by paint.
//     */
//    public static float getFontHeight(Paint paint) {
//        Paint.FontMetrics metrics = paint.getFontMetrics();
//        return (-metrics.ascent) + metrics.descent;
//    }
//
//    /**
//     * @param text the text to be printed
//     * @param paint definitions where the text are set
//     * @return a rectangle with the dimensions to contain the text
//     */
//    @Nullable
//    public static Rect getRectDimensions(String text, Paint paint) {
//
//        Rect rect = new Rect();
//        if(text == null || text.length() == 0) {
//            return null;
//        }
//        paint.getTextBounds(text, 0, text.length(), rect);
//        rect.bottom = rect.top + (int) getFontHeight(paint);
//        return rect;
//    }
}

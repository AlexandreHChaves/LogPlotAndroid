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

/**
 * Created by achaves on 12-05-2016.
 *
 */
public class Ruler {

    private final String TAG = "Ruler";

    private int color;
    /** width of the ruler in dp */
    private float width;
    private double position;
    private float pxPosition;
    private float dash;
    private float blank;
    public Label label;

    public Ruler() {
        width = 1f;
        position = 0d;
        pxPosition = 0f;
        dash = 0f;
        blank = 0f;
        color = ChartColor.StandardColor.minorRuler;
        label = new Label();
//        label = new Label() {
//            @Override
//            public String getLabel() {
//                if(super.getLabel().isEmpty()) {
//                    return String.valueOf(position);
//                } else {
//                    return super.getLabel();
//                }
//            }
//        };
//        label.setColor(ChartColor.StandardColor.axisLabel);
    }

    public int getColor() {
        return color;
    }

    /**
     * color to set the text
     * @param color integer representing the color. It can be set by Color.parseColor(#AARRGGBB),
     *              AA - alpha channel; RR - red; GG - green; BB - blue
     *              ex: Color.parseColor("#607d8b"), alpha is not set
     */
    public void setColor(int color) {
        this.color = color;
    }

    public float getWidth() {
        return width;
    }

    /** @param width set the width for this ruler */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * @return position for this ruler in data coordinate in axis
     */
    public double getPosition() {
        return position;
    }

    /**
     * @param position position for this ruler in data coordinate in axis
     */
    public void setPosition(double position) {
        this.position = position;
    }

    /**
     * @return Return a Label label from ruler
     */
    public Label getLabel() {
        return label;
    }

    /**
     * @param label set the label for ruler in Text form
     */
    public void setLabel(Label label) {
        this.label = label;
    }

    /** @return get position for this ruler in pixels */
    public float getPxPosition() {
        return pxPosition;
    }

    /** @param pxPosition  position for this ruler in pixels */
    public void setPxPosition(float pxPosition) {
        this.pxPosition = pxPosition;
    }

    /**
     * @return length of the dash in dp units
     */
    public float getDash() {
        return dash;
    }

    /**
     * Definition of the length of dashes for this ruler like: <br>
     *     (--&emsp;--&emsp;--&emsp;--) or (-&emsp;-&emsp;-&emsp;-&emsp;-)<br>
     *         Zero is defined by default defining a continuous line along the plot.
     *         Negative number are illegal.
     * @param dash the dash length in dp units
     */
    public void setDash(float dash) {
        if (dash < 0f) {
            throw new IllegalArgumentException(TAG + ": dash length must be a positive number or zero");
        }
        this.dash = dash;
    }

    public float getBlank() {
        return blank;
    }

    /**
     * Defines the blank length between the dashes: <br>
     *     (-&emsp;&emsp;&emsp;-&emsp;&emsp;&emsp;-&emsp;&emsp;&emsp;-) or (-&emsp;-&emsp;-&emsp;-&emsp;-) <br>
     *         Zero is defined by default meaning no space at all between dashes<br>
     *         If you want a continuous line do: <br>
     *         <code>setDash(0)</code> <br>
     *         and ignore <code>setBlank(blank)</code>
     * @param blank the blank length in dp units between dashes
     */
    public void setBlank(float blank) {
        if (blank < 0f) {
            throw new IllegalArgumentException(TAG + ": blank length must be a positive number or zero");
        }
        this.blank = blank;
    }
}

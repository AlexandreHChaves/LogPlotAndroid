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
    public Label label;

    public Ruler() {
        width = 1f;
        position = 0d;
        pxPosition = 0f;
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

}

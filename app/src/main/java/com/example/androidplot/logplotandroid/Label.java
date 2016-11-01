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

import android.util.Log;

/**
 * Created by achaves on 17-05-2016.
 *
 */
public class Label {

    private final String TAG = "Label";

    private String text = "";
    private int color = ChartColor.StandardColor.axisLabel;
    private float size = 10f;
    private float angle = 0f;
    private float hOffset = 0f;
    private float vOffset = 0f;

    public Label() { }

    public Label(String text) {
        this.text = text;
    }

    public String getText() {
//        if (text == null) {
//            return "";
//        }
        return text;
    }

    public void setText(String text) {
        this.text = text.trim();
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

    /**
     * @return text size in sp units
     */
    public float getSize() {
        return size;
    }

    /** size of the text in sp units */
    public void setSize(float size) {
        this.size = size;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public boolean hasText(){
        return text != null && !text.isEmpty();
    }

    public float getHOffset() {
        return hOffset;
    }

    /**
     * push text horizontally by offset amount
     * @param hOffset dimension in dp units
     */
    public void setHOffset(float hOffset) {
        this.hOffset = hOffset;
    }

    public float getVOffset() {
        return vOffset;
    }

    /**
     * push text vertically by offset amount
     * @param vOffset dimension in dp units
     */
    public void setVOffset(float vOffset) {
        this.vOffset = vOffset;
    }
}

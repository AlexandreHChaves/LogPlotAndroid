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

import java.util.ArrayList;

/**
 * Created by achaves on 23-05-2016.
 *
 */
public class Rulers extends Ruler{

    private final String TAG = "Rulers";

    private ArrayList<Ruler> rulers = new ArrayList<>();

    public Rulers() {
        label.setColor(0);
        setColor(0);
        setWidth(0);
        setPosition(Double.NEGATIVE_INFINITY);
        setPxPosition(Float.NEGATIVE_INFINITY);
    }

    public void setAllRulersColor() {
        if (getColor() != 0) {
            for (Ruler ruler : getRulers()) {
                ruler.setColor(getColor());
            }
        }
    }

    public void setAllRulersWidth() {
        if (getWidth() > 0) {
            for (Ruler ruler : getRulers()) {
                ruler.setWidth(getWidth());
            }
        }
    }

    public void setAllRulersLabelSize() {
        if (label.getSize() > 0) {
            for (Ruler ruler : getRulers()) {
                ruler.label.setSize(label.getSize());
            }
        }
    }

    public void setAllRulersLabelColor() {
        if (label.getColor() != 0 ) {
            for (Ruler ruler : getRulers()) {
                Log.d(TAG, "color: " + label.getColor());
                ruler.label.setColor(label.getColor());
            }
        }
    }

    public void setAllRulersHOffsetLabel() {
        if (label.getHOffset() != 0f ) {
            for (Ruler ruler : getRulers()) {
                ruler.label.setHOffset(label.getHOffset());
            }
        }
    }

    public void setAllRulersVOffsetLabel() {
        if (label.getVOffset() != 0f) {
            for (Ruler ruler : getRulers()) {
                ruler.label.setVOffset(label.getVOffset());
            }
        }
    }

    public void setAllRulersLabelAngle() {
        if (label.getAngle() != 0f) {
            for (Ruler ruler : getRulers()) {
                ruler.label.setAngle(label.getAngle());
            }
        }
    }

    public ArrayList<Ruler> getRulers() {
        return rulers;
    }

    public Ruler getRuler(int rulerPos) {
        return getRulers().get(rulerPos);
    }

    public void setRulers(ArrayList<Ruler> rulers) {
        this.rulers = rulers;
    }

    public void addRuler(Ruler ruler) {
        rulers.add(ruler);
    }
}
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
 * Created by achaves on 12-05-2016.
 *
 */
public class AxisLog10 extends Axis{

    private final String TAG = "AxisLog10";

    private RulerStep firstMajorRulerStep = new RulerStep();

    public AxisLog10() {
        Log.d(TAG, "creating AxisLog10");
        setLinear(false);
    }

    public RulerStep getFirstMajorRulerStep() {
        return firstMajorRulerStep;
    }

    /** @param firstMajorStepUnit the value you want for the first "bigger ruler" to be represented.
     * For instance, if you want to represent the response from an audio monitor, the value for the
     * first major ruler might be 20 (as 20Hz). Posterior minor rulers will be 40, 60, ... and
     * anterior and first minor rulers will be 4, 6, ...<br>
     *
     *  @param precision power of ten of decimal places. ex: <br>
     *                   0.1 -> precision: 10<br>
     *                   0.03 -> precision: 100
     *
     * */
    public void setFirstMajorRulerStep(double firstMajorStepUnit, int precision) {
        this.firstMajorRulerStep.setValue(firstMajorStepUnit);
        this.firstMajorRulerStep.setPrecision(precision);
        setMinAxisRange(firstMajorStepUnit/10);
    }

    @Override
    public float scale(double value) {
        float answer = super.scale(value);
        if (answer == -1) {
            return answer;
        }
        // px = m*log10(value) + b
        return (float) (getPxLength()/(Math.log10(getMaxAxisRange()) - Math.log10(getMinAxisRange())) *
                Math.log10(value) -
                getPxLength()/(Math.log10(getMaxAxisRange()) - Math.log10(getMinAxisRange())) * Math.log10(getMinAxisRange()));
    }

    @Override
    public void apply() {

//        Log.d(TAG, "....... apply() .......");

        if (getMinAxisRange() >= getMaxAxisRange()) {
            Log.e(TAG, "range axis limits badly defined; review minimum and maximum axis range");
            throw new IllegalArgumentException("minimum axis value is higher than maximum axis value");
        }

        if (!isLinear()) {

            final int MAX_INTERACTIONS = 1000;

            RulerStep bigRulerStep = new RulerStep();
            RulerStep littleRulerStep = new RulerStep();

            bigRulerStep.setValue(firstMajorRulerStep.getValue());
            bigRulerStep.setPrecision(firstMajorRulerStep.getPrecision());

            littleRulerStep.setValue(firstMajorRulerStep.getValue()/10d);
            littleRulerStep.setPrecision(bigRulerStep.getPrecision() + 1);

            float rulerPxPosition = 0;

            if (getAxisRange() <= littleRulerStep.getValue()) {
                Log.e(TAG, "the rulerStep chosen for the AxisLog10 is to large");
                return;
            }

            int fuse = 0;
            int k = 0;
            int j = 1;

            while (rulerPxPosition <= getPxLength()) {

                if (fuse++ == MAX_INTERACTIONS) { // cycle fuse
                    gridRulers.getRulers().clear();
                    Log.e(TAG, "cycle to assign rulers blew up; \n" +
                                    "maybe rulerStep to short for the axis;\n" +
                                    "rulers cleared out"
                    );
                    break;
                }

                Ruler ruler;

                if (j == 10) { // each tenth ruler the scale is changed
                    j = 1;
                    ruler = new MajorRuler();
                    ruler.setPxPosition(rulerPxPosition);

                    k++;
                    bigRulerStep.setValue(firstMajorRulerStep.getValue() * Math.pow(10, k));
                    bigRulerStep.setPrecision(bigRulerStep.getPrecision() + 1);
                    littleRulerStep.setValue(bigRulerStep.getValue() / 10);

                    if (littleRulerStep.getPrecision() < 1) {
                        littleRulerStep.setPrecision(0);
                    } else {
                        littleRulerStep.setPrecision(littleRulerStep.getPrecision() - 1);
                    }

                    if (littleRulerStep.getValue() > getMaxAxisRange()) {
                        Log.w(TAG, "ruler position stopped by outer of range value");
                        break;
                    }

                } else {
                    ruler = new MinorRuler();
                    ruler.setPxPosition(rulerPxPosition);
                }

                ruler.label.setText(littleRulerStep.getStringValue(littleRulerStep.getValue() * j));
                gridRulers.addRuler(ruler);

                rulerPxPosition = scale(littleRulerStep.getValue() * ++j);
            }

            if (hasUserRulers()) { // user defined rulers
                for (Ruler ruler : userRulers.getRulers()) {
                    ruler.setPxPosition(scale(ruler.getPosition()));
                }
            }

            if (hasGridRulers()) {
                super.apply();
            }

//            if (hasGridRulers()) {
//
//                for (Ruler ruler : getGridRulers()) {
//                    ruler.setPxPosition(scale(ruler.getPosition()));
//                }
//
//                for (Ruler ruler : newRulers) {
//                    Log.d(TAG, "ruler text: " + ruler.label.getLabel());
//                    addGridRuler(ruler);
//                }
//            } else {
//                setGridRulers(newRulers);
//            }

        }
    }
}

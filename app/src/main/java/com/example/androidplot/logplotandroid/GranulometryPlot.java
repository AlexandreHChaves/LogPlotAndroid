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

/** Created by achaves on 10-05-2016. */
@Deprecated
public class GranulometryPlot extends Plot{

    private final String TAG = "GranulometryPlot";

    public GranulometryPlot(){
    }

    @Override
    public void gridDefinition() {
        Log.i(TAG, ".... gridDefinition.....");

//        getGrid().getXAxis().setMaxAxisRange(getGrid().getXAxis().getMaxAxisRange()); //

        getGrid().apply(); // defines axis, rulers and labels

    }

    @Override
    public void plotDefinition() {
        if (getSeriesList().size() > 0) {
            for (SeriesXY series : getSeriesList()) {

                ChartLineType lineType = series.getLineType();

                switch (lineType){
                    case LINE:
                        getGrid().plotSeriesLine(series);
                        break;
                    case DASHED_LINE:
                        getGrid().plotSeriesDashedLine(series);
                        break;
                    case BEZIER_QUADRATIC:
                        getGrid().plotSeriesBezierQuadratic(series);
                        break;
                    case BEZIER_CUBIC:
                        getGrid().plotSeriesBezierCubic(series);
                        break;
                    case POINTS:
                        getGrid().plotSeriesPoints(series);
                }

                if (series.isToWritePointCoordinate()) { // the point coordinate
                    getGrid().writeSeriesPointCoordinate(series);
                }

                if (series.isToWritePointLabel()) { // the point label
                    getGrid().writeSeriesPointText(series);
                }
            }
        }
    }
}

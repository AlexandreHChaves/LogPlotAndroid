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

import android.graphics.Color;

/**
 * Created by achaves on 10-05-2016.
 *
 */
public interface ChartColor {

    class StandardColor {
        /** point color */
        static int point = Color.parseColor("#f57c00"); // orange_700
        /** text color to describe point */
        static int pointTextColor = Color.parseColor("#263238"); // blue_gray_900
        /** reference axis color */
        static int axis = Color.parseColor("#607d8b"); // blue_gray_500
        /** grid background color */
        static int background = Color.parseColor("#eceff1"); // blue_gray_50;
        /** major ruler color */
        static int majorRuler = Color.parseColor("#78909c"); // blue_gray_400
        /** minor ruler color */
        static int minorRuler = Color.parseColor("#cfd8dc"); // blue_gray_100
        /** axis label color */
        static int axisLegend = Color.parseColor("#1976d2"); // blue_700
        /** marker identification for rulers color */
        static int axisLabel = Color.parseColor("#b0bec5"); // blue_gray_200
        /** line color for the series */
        static int line = Color.parseColor("#2196f3"); // blue_500
    }
}

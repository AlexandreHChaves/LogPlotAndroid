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

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private ImageView imagePlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "............ onCreate .............");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagePlot = (ImageView) findViewById(R.id.iv_log_plot);

//        String path = "/system/fonts"; // find system font files
//        File file = new File(path);
//        File ff[] = file.listFiles();
//
//        for (File file1 : ff) {
//            Log.i(TAG, file1.getName());
//        }

//        new Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        loadPlot();
//                    }
//                },
//                100);
    }

    // you need to wait for activity window to be attached and then call getWidth() and getHeight() on ImageView.
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        Log.i(TAG, "............ onWindowFocusChanged .............");
        super.onWindowFocusChanged(hasFocus);

        if (getXDataArray().length != getYDataArray().length) {
            Log.e(TAG, "incompatible data lengths");
        }

        loadPlot();
    }

    private void loadPlot(){
        Log.i(TAG, "............ loadPlot .............");

        Log.i(TAG, "image width: " + imagePlot.getWidth());
        Log.i(TAG, "image height: " + imagePlot.getHeight());

        Log.d(TAG, "x array dimension: " + getXDataArray().length);
        Log.d(TAG, "y array dimension: " + getYDataArray().length);

        SeriesXY series = new SeriesXY(getXDataArray(), getYDataArray());
        series.setLineWidth(0);
        series.setLineColor(ContextCompat.getColor(this, R.color.indigo_200));
//        series.setPointSize(2);

        SeriesXY series_copy = series.clone();
        series_copy.setLineType(ChartLineType.POINTS);
        series_copy.setPointSize(1);

        PointPlot p1 = new PointPlot(0.5, 0.7);
        p1.setSize(5);
        p1.setText("bla bla");
        p1.setTextVOffset(10);

        PointPlot p2 = p1.clone();
        p2.setTextVOffset(-10 - 5);

        SeriesXY s1 = new SeriesXY();
        s1.addPoint(p1);
        s1.setToWritePointText(true);
        s1.setLineType(ChartLineType.POINTS);

        SeriesXY s2 = new SeriesXY();
        s2.addPoint(p2);
        s2.setToWritePointText(true);
        s2.setLineType(ChartLineType.POINTS);

        Ruler yRuler055 = new Ruler();
        yRuler055.setPosition(0.5);
        yRuler055.label.setText("median");
        yRuler055.label.setVOffset(3);
        yRuler055.label.setHOffset(3);
        yRuler055.setColor(ContextCompat.getColor(this, R.color.deep_orange_100));
        yRuler055.label.setColor(ContextCompat.getColor(this, R.color.deep_orange_300));

        Ruler xpRuler = new Ruler();
        xpRuler.setColor(ContextCompat.getColor(this, R.color.teal_300));
        xpRuler.label.setColor(ContextCompat.getColor(this, R.color.teal_600));
        xpRuler.label.setHOffset(3);
        xpRuler.label.setVOffset(3);
//        xpRuler.label.setSize(10);
        xpRuler.setPosition(0.35);

        AxisLog xAxis = new AxisLog();
        xAxis.getGridRulers().label.setVOffset(-15);
        xAxis.getGridRulers().label.setHOffset(-6);
        xAxis.label.setVOffset(-5);
//        xAxis.getGridRulers().label.setSize(10);
//        xAxis.label.setVOffset(-12);
//        xAxis.setWidth(5);
        xAxis.setFirstMajorStepUnit(0.01); // 1cm
        xAxis.label.setSize(12); // text size sp
        xAxis.label.setText("Granulometry (m)");
        xAxis.addUserRuler(xpRuler);

        Axis yAxis = new AxisLinear();
        yAxis.label.setHOffset(-5);
        yAxis.label.setSize(12);
        yAxis.setStep(0.1);
        yAxis.setWidth(2);
        yAxis.label.setText("Probability [0..1]");
        yAxis.getGridRulers().label.setVOffset(-4);
        yAxis.getGridRulers().label.setHOffset(-20);
//        yAxis.getGridRulers().label.setSize(15);
//        yAxis.getGridRulers().label.setColor(ContextCompat.getColor(this, R.color.red_700));
        yAxis.setMinAxisRange(0d);
        yAxis.setMaxAxisRange(1d);
        yAxis.addUserRuler(yRuler055);

        Grid grid = new LogNormGrid(this, imagePlot);
//        grid.setBackGroundColor(ContextCompat.getColor(this, R.color.blue_50));
        grid.setTopTextBorder(10);
        grid.setRightTextBorder(10);
        grid.setXAxis(xAxis);
        grid.setYAxis(yAxis);

        GranulometryPlot logPlot = new GranulometryPlot();
        logPlot.setGrid(grid);
        logPlot.addSeries(series);
        logPlot.addSeries(series_copy);
        logPlot.addSeries(s1);
        logPlot.addSeries(s2);
//        logPlot.addSeries(series_copy);

        logPlot.plot();
    }

    private double[] getXDataArray(){
        double[] x = {1.00000000e-03,   1.10000000e-02,   2.10000000e-02,   3.10000000e-02,
                4.10000000e-02,   5.10000000e-02,  6.10000000e-02,   7.10000000e-02,
                8.10000000e-02,   9.10000000e-02,   1.01000000e-01,   1.11000000e-01,
                1.21000000e-01,   1.31000000e-01,   1.41000000e-01,   1.51000000e-01,
                1.61000000e-01,   1.71000000e-01,   1.81000000e-01,   1.91000000e-01,
                2.01000000e-01,   2.11000000e-01,   2.21000000e-01,   2.31000000e-01,
                2.41000000e-01,   2.51000000e-01,   2.61000000e-01,   2.71000000e-01,
                2.81000000e-01,   2.91000000e-01,   3.01000000e-01,   3.11000000e-01,
                3.21000000e-01,   3.31000000e-01,   3.41000000e-01,   3.51000000e-01,
                3.61000000e-01,   3.71000000e-01,   3.81000000e-01,   3.91000000e-01,
                4.01000000e-01,   4.11000000e-01,   4.21000000e-01,   4.31000000e-01,
                4.41000000e-01,   4.51000000e-01,   4.61000000e-01,   4.71000000e-01,
                4.81000000e-01,   4.91000000e-01,   5.01000000e-01,   5.11000000e-01,
                5.21000000e-01,   5.31000000e-01,   5.41000000e-01,   5.51000000e-01,
                5.61000000e-01,   5.71000000e-01,   5.81000000e-01,   5.91000000e-01,
                6.01000000e-01,   6.11000000e-01,   6.21000000e-01,   6.31000000e-01,
                6.41000000e-01,   6.51000000e-01,   6.61000000e-01,   6.71000000e-01,
                6.81000000e-01,   6.91000000e-01,   7.01000000e-01,   7.11000000e-01,
                7.21000000e-01,   7.31000000e-01,   7.41000000e-01,   7.51000000e-01,
                7.61000000e-01,   7.71000000e-01,   7.81000000e-01,   7.91000000e-01,
                8.01000000e-01,   8.11000000e-01,   8.21000000e-01,   8.31000000e-01,
                8.41000000e-01,   8.51000000e-01,   8.61000000e-01,   8.71000000e-01,
                8.81000000e-01,   8.91000000e-01,   9.01000000e-01,   9.11000000e-01,
                9.21000000e-01,   9.31000000e-01,  9.41000000e-01,   9.51000000e-01,
                9.61000000e-01,   9.71000000e-01,   9.81000000e-01,   9.91000000e-01,
                1.00100000e+00,   1.01100000e+00,   1.02100000e+00,   1.03100000e+00,
                1.04100000e+00,  1.05100000e+00,   1.06100000e+00,   1.07100000e+00,
                1.08100000e+00,  1.09100000e+00,   1.10100000e+00,   1.11100000e+00,
                1.12100000e+00,   1.13100000e+00,   1.14100000e+00,   1.15100000e+00,
                1.16100000e+00,  1.17100000e+00,   1.18100000e+00,   1.19100000e+00,
                1.20100000e+00,   1.21100000e+00,   1.22100000e+00,   1.23100000e+00,
                1.24100000e+00,   1.25100000e+00,  1.26100000e+00,   1.27100000e+00,
                1.28100000e+00,   1.29100000e+00,   1.30100000e+00,   1.31100000e+00,
                1.32100000e+00,   1.33100000e+00,   1.34100000e+00,  1.35100000e+00,
                1.36100000e+00,  1.37100000e+00,   1.38100000e+00,   1.39100000e+00,
                1.40100000e+00,   1.41100000e+00,   1.42100000e+00,   1.43100000e+00,
                1.44100000e+00,   1.45100000e+00,   1.46100000e+00};

        return x;
    }

    private double[] getYDataArray(){
        double[] y = {0.034763039603412413, 0.075721107274586766, 0.098869284955846376,
                0.11799102139033794, 0.13511100644799598, 0.15100212260919862,
                0.16605243142312476, 0.18048716407679255, 0.19444946768042864,
                0.20803614803617271, 0.22131566998985461, 0.23433809884421952,
                0.24714098712797669, 0.25975305630820827, 0.27219660335503437,
                0.28448913223580163, 0.29664449443624924, 0.30867370745003619,
                0.32058555565919195, 0.33238704032571431, 0.34408372256977432,
                0.35567998892427433, 0.36717925987102712, 0.37858415571220144,
                0.38989663005492731, 0.40111807838738706, 0.41224942726724817,
                0.42329120825236827, 0.43424361970070224, 0.44510657883327559,
                0.45587976591165197, 0.46656266197533636, 0.47715458127750021,
                0.48765469932289296, 0.49806207723107176, 0.50837568300761427,
                0.51859441019596175, 0.52871709429573177, 0.53874252726438687,
                0.54866947036402103, 0.55849666557069055, 0.56822284572788195,
                0.5778467435965523, 0.58736709993040148, 0.59678267068548463,
                0.6060922334571992, 0.61529459322435565, 0.62438858746899151,
                0.63337309073136572, 0.64224701865186828, 0.65100933154510987,
                0.65965903754600919, 0.66819519536310379, 0.67661691667041102,
                0.68492336816585764, 0.69311377332148105, 0.70118741384818462,
                0.70914363089576715, 0.71698182600716265, 0.72470146184428064,
                0.73230206270150566, 0.73978321482174048, 0.74714456652886208,
                0.75438582818954736, 0.76150677201663641, 0.76850723172547863,
                0.77538710205407457, 0.78214633815724044, 0.78878495488450096,
                0.79530302595092917, 0.80170068300970743, 0.80797811463476987,
                0.81413556522149588, 0.82017333381306035, 0.8260917728597007,
                0.83189128691782843, 0.83757233129559816, 0.84313541065124653,
                0.84858107755021384, 0.85390993098678691, 0.85912261487571973,
                0.86421981651902602, 0.86920226505287801, 0.8740707298792928,
                0.87882601908703972, 0.8834689778659669, 0.88800048691870892,
                0.89242146087351004, 0.89673284670167719, 0.90093562214296408,
                0.90503079414197407, 0.90901939729847281, 0.91290249233430343,
                0.91668116457940618, 0.92035652247926769, 0.923929696125943,
                0.92740183581463176, 0.93077411062762472, 0.93404770704728546,
                0.93722382759958811, 0.94030368952958931, 0.94328852351009052,
                0.94617957238462125, 0.9489780899457595, 0.95168533974971148,
                0.95430259396796779, 0.95683113227677918, 0.95927224078511675,
                0.96162721100172333, 0.96389733884180651, 0.96608392367389873,
                0.96818826740737618, 0.97021167362112981, 0.97215544673389165,
                0.97402089121675217, 0.97580931084846168, 0.97752200801419153,
                0.97916028304855129, 0.98072543362380948, 0.98221875418447302,
                0.98364153542964194, 0.98499506384488733, 0.98628062128583238,
                0.98749948461614878, 0.98865292540337679, 0.98974220967685445,
                0.99076859775317627, 0.99173334413607883, 0.9926376974995692,
                0.99348290076567858, 0.99427019129163197, 0.99500080118589174,
                0.99567595777895079, 0.99629688428380236, 0.99686480069399064,
                0.99738092498621445, 0.99784647472323595, 0.99826266919758222,
                0.99863073232870081, 0.99895189664759432, 0.99922740891788697,
                0.99945853834761689, 0.99964658917424387, 0.99979292128595942,
                0.99989898749084538, 0.99996641237846973, 0.99999722078454423};

        return y;
    }

}

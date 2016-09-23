package com.example.androidplot.logplotandroid;

import android.util.Log;
import android.util.SparseBooleanArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by achaves on 18-09-2016.
 *
 */
public class RulerStepFinder {

    private static final String TAG = "RulerStepFinder";

    public static double find(final double range) {

        if (range <= 0d) {
            Log.e(TAG, "range value must be a positive number bigger than 0 (zero)");
            return -1;
        }

        /** minimum number of rulers in the plot */
        final int N_RULERS = 5;
        final int MAX_CYCLES = 100;
        final double step = range/N_RULERS;

        double s = 1d; // singler
        double d = 2d; // doubler
        double f = 5d; // fiver
        StepType stepType;

        List<Double> steppingList = new ArrayList<>();
        steppingList.add(0, s);
        steppingList.add(1, d);
        steppingList.add(2, f);

        if (step == s) {
            return s;
        }

        if (step == d) {
            return d;
        }

        if (step == f) {
            return f;
        }

        if (step > s) {
            stepType = StepType.BIGGER_1;
        } else {
            stepType = StepType.MINOR_1;
        }

        int fuse = 0;

        switch (stepType) {
            case BIGGER_1:
                while (true) {
                    // returning the biggest stepping number that is smaller than step
                    if (step > Collections.max(steppingList)) {
                        s = s*10d;
                        d = d*10d;
                        f = f*10d;
                        steppingList.add(s);
                        steppingList.add(d);
                        steppingList.add(f);
                    } else {

                        if (step == steppingList.get(steppingList.size() - 1)) {
                            return steppingList.get(steppingList.size() - 1);
                        }
                        if (step == steppingList.get(steppingList.size() - 2)) {
                            return steppingList.get(steppingList.size() - 2);
                        }
                        if (step == steppingList.get(steppingList.size() - 3)) {
                            return steppingList.get(steppingList.size() - 3);
                        }


                        if(step > steppingList.get(steppingList.size() - 1 - 2)){ // compare with singler
                            if (step > steppingList.get(steppingList.size() - 1 - 1)) { // compare with doubler
                                // return a doubler because is not bigger than maximum (the next fiver)
                                return  steppingList.get(steppingList.size() - 1 - 1);
                            } else {
                                return steppingList.get(steppingList.size() - 1 - 2); // return singler
                            }
                        } else {
                            return steppingList.get(steppingList.size() - 1 - 3); // return a fiver
                        }
                    }
                    fuse += 1;

                    if (fuse == MAX_CYCLES) {
                        Log.e(TAG, "number of cycles has been surpassed in BIGGER_1 cycle: returning ZERO");
                        return 0;
                    }
                }

            case MINOR_1:

                steppingList.clear();
                s = s/10d;
                d = d/10d;
                f = f/10d;
                steppingList.add(f);
                steppingList.add(d);
                steppingList.add(s);

                while (true) {
                    if (step < Collections.min(steppingList)) {
                        s = s/10d;
                        d = d/10d;
                        f = f/10d;
                        steppingList.add(f);
                        steppingList.add(d);
                        steppingList.add(s);

                    } else {

                        if (step == steppingList.get(steppingList.size() - 1)) {
                            return steppingList.get(steppingList.size() - 1);
                        }
                        if (step == steppingList.get(steppingList.size() - 2)) {
                            return steppingList.get(steppingList.size() - 2);
                        }
                        if (step == steppingList.get(steppingList.size() - 3)) {
                            return steppingList.get(steppingList.size() - 3);
                        }

                        if (step > steppingList.get(steppingList.size() - 1 - 1)) { // compare with doubler
                            if (step > steppingList.get(steppingList.size() - 1 - 2)) { // compare with fiver
                                return steppingList.get(steppingList.size() - 1 - 2); // return fiver
                            } else {
                                return steppingList.get(steppingList.size() - 1 - 1); // return doubler
                            }
                        } else {
                            return steppingList.get(steppingList.size() - 1); // return singler
                        }
                    }

                    fuse += 1;

                    if (fuse == MAX_CYCLES) {
                        Log.e(TAG, "number of cycles has been surpassed in MINOR_1 cycle: returning ZERO");
                        return 0;
                    }
                }
        }

        return 0;
    }

    private enum StepType{
        BIGGER_1,
        MINOR_1
    }
}

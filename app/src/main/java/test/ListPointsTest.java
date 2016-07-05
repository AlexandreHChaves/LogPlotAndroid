/*
 * Copyright 2016 Alexandre Chaves
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

package test;

import java.util.ArrayList;

/**
 * Created by achaves on 20-05-2016.
 *
 */
public class ListPointsTest implements Cloneable{
    private ArrayList<PointTest> pointsLists = new ArrayList<>();
    private int color;
    private String name;
    private int[] xPoints;

    public ListPointsTest() {
    }

    private ListPointsTest(ListPointsTest list) {
        for (PointTest point : list.getPointsLists()) {
            getPointsLists().add(point.clone());
        }

        setColor(list.getColor());
        setName(list.getName());
        setxPoints(list.getxPoints().clone());

    }

    /** implements clonable but doesn't do nothing with it; returns a new constructor */
    @Override
    public ListPointsTest clone() {

//        ListPointsTest listClone;

        try {
            super.clone();
//            listClone = (ListPointsTest) super.clone();
//            listClone.setxPoints(this.getxPoints().clone());
//            for (PointTest point : this.getPointsLists()) { // ConcurrentModificationException
//                listClone.getPointsLists().add(point.clone());
//            }
//            return listClone;

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
//            return new ListPointsTest(this);
        }
        return new ListPointsTest(this);
    }

    public int[] getxPoints() {
        return xPoints;
    }

    public void setxPoints(int[] xPoints) {
        this.xPoints = xPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addPoint(PointTest point) {
        pointsLists.add(point);
    }

    public ArrayList<PointTest> getPointsLists() {
        return pointsLists;
    }

    public void setPointsLists(ArrayList<PointTest> pointsLists) {
        this.pointsLists = pointsLists;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void print(){
        System.out.println("list name: " + getName());
        for (PointTest point : getPointsLists()) {
            point.print();
        }
        System.out.println("printing array....");
        for (int i : getxPoints()) {
            System.out.println("" + i);
        }
    }

    public PointTest getPoint(int position) {
        return getPointsLists().get(position);
    }
}

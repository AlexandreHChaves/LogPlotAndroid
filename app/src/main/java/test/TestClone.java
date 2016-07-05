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

import java.util.Arrays;
import java.util.List;

/**
 * Created by achaves on 20-05-2016.
 *
 */
public class TestClone {

    public static void main(String... args) {
        PointTest point1 = new PointTest(3, 4);
        point1.setType(Type.COW);
        point1.setName("point 1");

        PointTest point2 = new PointTest(4, 5);
        PointTest point3 = new PointTest(5, 6);

        ListPointsTest pointsList = new ListPointsTest();
        pointsList.setName("list one");
        pointsList.addPoint(point1);
        pointsList.addPoint(point2);
        pointsList.addPoint(point3);
        int[] x = {2, 3};
        pointsList.setxPoints(x);

        pointsList.print();

        ListPointsTest pointsListCopy = pointsList.clone();
        pointsListCopy.setName("list two");
        pointsListCopy.getxPoints()[0] = 3;

        PointTest pointTemp = pointsListCopy.getPoint(0);
        pointTemp.setX(0);
        pointTemp.setY(0);
        pointTemp.setName("point temp");
        pointTemp.setType(Type.ZEBRA);

        System.out.println("#####################");

        pointsList.print();
        System.out.println("---------------------");
        pointsListCopy.print();









        PointTest p1 = new PointTest(0, 1);
        PointTest p2 = new PointTest(1, 2);
        PointTest p3 = new PointTest(2, 3);

        PointTest[] a1 = {p1, p2, p3};

        PointTest[] a2 = new PointTest[a1.length];

//        System.arraycopy(a1, 0, a2, 0, a1.length); // fails to clone/copy

        for (int i = 0; i < a1.length; i++) {
            a2[i] = a1[i].clone();
        }

        a2[1].setType(Type.COW);
        a2[1].setX(10);
        a2[1].setY(20);

        List<PointTest> l1 = Arrays.asList(a1);
        List<PointTest> l2 = Arrays.asList(a2);

        System.out.println("---------------------");
        System.out.println("------ printing l1---------");

        for (PointTest point : l1) {
            point.print();
        }

        System.out.println("------- printing l2 ---");

        for (PointTest point : l2) {
            point.print();
        }
    }
}

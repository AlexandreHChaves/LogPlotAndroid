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

/**
 * Created by achaves on 20-05-2016.
 *
 */
public class PointTest implements Cloneable{
    private String name = "";
    private int x;
    private int y;
    private Type type = Type.CHIMP;

    public PointTest(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private PointTest(PointTest point) {
        setX(point.getX());
        setY(point.getY());
        setName(point.getName());
        setType(point.getType());
    }

    @Override
    public PointTest clone(){
        try {
            super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return new PointTest(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void print(){
        System.out.println("point name: " + name);
        System.out.println("point type: " + type.toString());
        System.out.println("(" + x + ", " + y + ")");
    }
}

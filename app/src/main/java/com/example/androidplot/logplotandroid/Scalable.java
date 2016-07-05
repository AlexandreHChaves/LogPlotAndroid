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

/**
 * Created by achaves on 12-05-2016.
 *
 */
public interface Scalable {
    /**
     * <code>value</code> should never surpass the <code>axisRange</code> value
     *
     * @param value value representing the data
     * @return transformed coordinate in the axis representing <code>value</code> in pixels.
     */
    float scale(double value) ;
}

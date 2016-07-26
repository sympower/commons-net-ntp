package org.apache.commons.net.ntp;
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Copies of some methods from Apache Harmony JDK that are missing from Java ME.
 */
public class MicroMath {

  // from https://github.com/apache/harmony/blob/java6/classlib/modules/luni/src/main/java/java/lang/Math.java
  public static long round(double d) {
    // check for NaN
    if (d != d) {
      return 0L;
    }
    return (long) Math.floor(d + 0.5d);
  }

  // from http://stackoverflow.com/questions/6516103/how-to-get-the-power-of-a-number-in-j2me
  public static double pow(final double a, final double b) {
    int x = (int) (Double.doubleToLongBits(a) >> 32);
    int y = (int) (b * (x - 1072632447) + 1072632447);
    return Double.longBitsToDouble(((long) y) << 32);
  }

}

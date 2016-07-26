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
// from https://github.com/apache/harmony/blob/java6/classlib/modules/luni/src/main/java/java/lang/Long.java
public class MicroLong {

  /**
   * Converts the specified long value into its hexadecimal string
   * representation. The returned string is a concatenation of characters from
   * '0' to '9' and 'a' to 'f'.
   *
   * @param l
   *            the long value to convert.
   * @return the hexadecimal string representation of {@code l}.
   */
  public static String toHexString(long l) {
    int count = 1;
    long j = l;

    if (l < 0) {
      count = 16;
    } else {
      while ((j >>= 4) != 0) {
        count++;
      }
    }

    char[] buffer = new char[count];
    do {
      int t = (int) (l & 15);
      if (t > 9) {
        t = t - 10 + 'a';
      } else {
        t += '0';
      }
      buffer[--count] = (char) t;
      l >>= 4;
    } while (count > 0);
    return new String(buffer, 0, buffer.length);
  }

}

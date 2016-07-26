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

package org.apache.commons.net;

import javax.microedition.io.Connector;
import javax.microedition.io.DatagramConnection;
import java.io.IOException;

/***
 * MicroDatagramSocketFactory implements the DatagramSocketFactory
 * interface by wrapping javax.microedition.io.Connector calls.
 *
 * @see DatagramSocketFactory
 * @see DatagramSocketClient
 * @see DatagramSocketClient#setDatagramSocketFactory
 ***/
public class MicroDatagramSocketFactory implements DatagramSocketFactory
{

    /***
     * Creates a DatagramSocket on the local host at the first available port.
     * @return a new DatagramSocket
     * @exception IOException If the socket could not be created.
     ***/
    public DatagramConnection createDatagramSocket() throws IOException
    {
        return (DatagramConnection) Connector.open("datagram://:", Connector.READ_WRITE, true);
    }

    /***
     * Creates a DatagramSocket at a specified host and port.
     *
     * @param host The host to use for the socket.
     * @param port The port to use for the socket.
     * @return a new DatagramSocket
     * @exception IOException If the socket could not be created.
     ***/
    public DatagramConnection createDatagramSocket(String host, int port) throws IOException
    {
        return (DatagramConnection) Connector.open(
          "datagram://" + host + ":" + port, Connector.READ_WRITE, true);
    }

}

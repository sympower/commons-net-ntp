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

import javax.microedition.io.Datagram;
import javax.microedition.io.DatagramConnection;
import java.io.IOException;

/***
 * The DatagramSocketClient provides the basic operations that are required
 * of client objects accessing datagram sockets.  It is meant to be
 * subclassed to avoid having to rewrite the same code over and over again
 * to open a socket, close a socket, set timeouts, etc.  Of special note
 * is the {@link #setDatagramSocketFactory  setDatagramSocketFactory }
 * method, which allows you to control the type of DatagramSocket the
 * DatagramSocketClient creates for network communications.  This is
 * especially useful for adding things like proxy support as well as better
 * support for applets.  For
 * example, you could create a
 * {@link org.apache.commons.net.DatagramSocketFactory}
 *  that
 * requests browser security capabilities before creating a socket.
 * All classes derived from DatagramSocketClient should use the
 * {@link #_socketFactory_  _socketFactory_ } member variable to
 * create DatagramSocket instances rather than instantiating
 * them by directly invoking a constructor.  By honoring this contract
 * you guarantee that a user will always be able to provide his own
 * Socket implementations by substituting his own SocketFactory.
 *
 *
 * @see DatagramSocketFactory
 ***/

public abstract class DatagramSocketClient
{
    /**
     * Charset to use for byte IO.
     */
    private String charsetName = "UTF-8";

    /*** The datagram socket used for the connection. ***/
    protected DatagramConnection _socket_;

    /***
     * A status variable indicating if the client's socket is currently open.
     ***/
    protected boolean _isOpen_;

    /*** The datagram socket's DatagramSocketFactory. ***/
    protected DatagramSocketFactory _socketFactory_;

    /***
     * Default constructor for DatagramSocketClient.  Initializes
     * _socket_ to null, _timeout_ to 0, and _isOpen_ to false.
     ***/
    public DatagramSocketClient()
    {
        _socket_ = null;
        _isOpen_ = false;
        _socketFactory_ = new MicroDatagramSocketFactory();
    }


    /***
     * Opens a DatagramSocket on the local host at the first available port.
     * <p>
     * _isOpen_ is set to true after calling this method and _socket_
     * is set to the newly opened socket.
     *
     * @exception IOException If the socket could not be opened or the
     *   timeout could not be set.
     ***/
    public void open() throws IOException
    {
        _socket_ = _socketFactory_.createDatagramSocket();
        _isOpen_ = true;
    }


    /***
     * Opens a DatagramSocket on the local host at a specified port.
     * <p>
     * _isOpen_ is set to true after calling this method and _socket_
     * is set to the newly opened socket.
     *
     * @param host The host to use for the socket.
     * @param port The port to use for the socket.
     * @exception IOException If the socket could not be opened or the
     *   timeout could not be set.
     ***/
    public void open(String host, int port) throws IOException
    {
        _socket_ = _socketFactory_.createDatagramSocket(host, port);
        _isOpen_ = true;
    }

    /***
     * Closes the DatagramSocket used for the connection.
     * You should call this method after you've finished using the class
     * instance and also before you call {@link #open open() }
     * again.   _isOpen_ is set to false and  _socket_ is set to null.
     * If you call this method when the client socket is not open,
     * a NullPointerException is thrown.
     ***/
    public void close() throws IOException
    {
        if (_socket_ != null) {
            _socket_.close();
        }
        _socket_ = null;
        _isOpen_ = false;
    }


    /***
     * Returns true if the client has a currently open socket.
     *
     * @return True if the client has a curerntly open socket, false otherwise.
     ***/
    public boolean isOpen()
    {
        return _isOpen_;
    }

    /***
     * Sets the DatagramSocketFactory used by the DatagramSocketClient
     * to open DatagramSockets.  If the factory value is null, then a default
     * factory is used (only do this to reset the factory after having
     * previously altered it).
     *
     * @param factory  The new DatagramSocketFactory the DatagramSocketClient
     * should use.
     ***/
    public void setDatagramSocketFactory(DatagramSocketFactory factory)
    {
        _socketFactory_ = factory;
    }

    /**
     * Gets the charset name.
     *
     * @return the charset name.
     * @since 3.3
     */
    public String getCharsetName() {
        return charsetName;
    }

    /**
     * Sets the charset.
     *
     * @param charsetName the charset.
     * @since 3.3
     */
    public void setCharsetName(String charsetName) {
        this.charsetName = charsetName;
    }

    public Datagram newDatagram(byte[] buf, int size) throws IOException {
        return _socket_.newDatagram(buf, size);
    }

    public Datagram newDatagram(byte[] buf, int size, String addr) throws IOException {
        return _socket_.newDatagram(buf, size, addr);
    }

    public Datagram newDatagram(int size) throws IOException {
        return _socket_.newDatagram(size);
    }

    public Datagram newDatagram(int size, String addr) throws IOException {
        return _socket_.newDatagram(size, addr);
    }

}

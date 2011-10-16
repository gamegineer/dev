/*
 * ConnectorTest.java
 * Copyright 2008-2011 Gamegineer.org
 * All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Created on Jan 13, 2011 at 10:08:14 PM.
 */

package org.gamegineer.table.internal.net.transport.tcp;

import java.io.IOException;
import java.util.concurrent.Callable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.transport.tcp.Connector} class.
 */
public final class ConnectorTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The connector under test in the fixture. */
    private volatile Connector connector_;

    /** The transport layer for use in the fixture. */
    private volatile AbstractTransportLayer transportLayer_;

    /** The transport layer runner for use in the fixture. */
    private TransportLayerRunner transportLayerRunner_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ConnectorTest} class.
     */
    public ConnectorTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        transportLayer_ = new FakeTransportLayer();
        transportLayerRunner_ = new TransportLayerRunner( transportLayer_ );

        transportLayerRunner_.run( new Callable<Void>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Void call()
                throws Exception
            {
                transportLayer_.open( "localhost", 8888 ); //$NON-NLS-1$
                connector_ = new Connector( transportLayer_ );
                return null;
            }
        } );
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        transportLayerRunner_.run( new Callable<Void>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Void call()
            {
                connector_.close();
                return null;
            }
        } );
        transportLayerRunner_.close();
    }

    /**
     * Ensures the {@code connect} method throws an exception if the connector
     * has been closed.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testConnect_AfterClose()
        throws Exception
    {
        transportLayerRunner_.run( new Callable<Void>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Void call()
                throws Exception
            {
                connector_.close();

                connector_.connect( "localhost", 8888 ); //$NON-NLS-1$

                return null;
            }
        } );
    }

    /**
     * Ensures the {@code connect} method throws an exception when attempting to
     * connect to the peer host more than once.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testConnect_MultipleInvocations()
        throws Exception
    {
        transportLayerRunner_.run( new Callable<Void>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Void call()
                throws Exception
            {
                try
                {
                    connector_.connect( "localhost", 8888 ); //$NON-NLS-1$
                }
                catch( final IOException e )
                {
                    // ignore I/O errors
                }

                connector_.connect( "localhost", 8888 ); //$NON-NLS-1$

                return null;
            }
        } );
    }
}

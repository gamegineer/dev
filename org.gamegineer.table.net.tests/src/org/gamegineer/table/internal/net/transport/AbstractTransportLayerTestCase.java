/*
 * AbstractTransportLayerTestCase.java
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
 * Created on Jan 18, 2011 at 8:50:02 PM.
 */

package org.gamegineer.table.internal.net.transport;

import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.transport.ITransportLayer}
 * interface.
 */
public abstract class AbstractTransportLayerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The transport layer under test in the fixture. */
    private ITransportLayer transportLayer_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractTransportLayerTestCase}
     * class.
     */
    protected AbstractTransportLayerTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the transport layer to be tested.
     * 
     * @return The transport layer to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ITransportLayer createTransportLayer()
        throws Exception;

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
        transportLayer_ = createTransportLayer();
        assertNotNull( transportLayer_ );
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
        transportLayer_.close();
    }

    /**
     * Ensures the {@code open} method throws an exception if the transport
     * layer has been closed.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testOpen_AfterClose()
        throws Exception
    {
        transportLayer_.close();

        transportLayer_.open( "localhost", 8888 ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code open} method throws an exception when passed a {@code
     * null} host name.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testOpen_HostName_Null()
        throws Exception
    {
        transportLayer_.open( null, 8888 );
    }

    /**
     * Ensures the {@code open} method throws an exception when attempting to
     * open the transport layer more than once.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testOpen_MultipleInvocations()
        throws Exception
    {
        try
        {
            transportLayer_.open( "localhost", 8888 ); //$NON-NLS-1$
        }
        catch( final TransportException e )
        {
            // ignore transport layer errors
        }

        transportLayer_.open( "localhost", 8888 ); //$NON-NLS-1$
    }
}

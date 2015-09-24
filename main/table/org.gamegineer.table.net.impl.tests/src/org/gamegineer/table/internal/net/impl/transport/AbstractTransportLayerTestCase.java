/*
 * AbstractTransportLayerTestCase.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.net.impl.transport;

import static org.junit.Assert.fail;
import java.util.Optional;
import java.util.concurrent.Future;
import org.eclipse.jdt.annotation.Nullable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link ITransportLayer} interface.
 */
public abstract class AbstractTransportLayerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The transport layer under test in the fixture. */
    private Optional<ITransportLayer> transportLayer_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractTransportLayerTestCase}
     * class.
     */
    protected AbstractTransportLayerTestCase()
    {
        transportLayer_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the transport layer to be tested.
     * 
     * @return The transport layer to be tested.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract ITransportLayer createTransportLayer()
        throws Exception;

    /**
     * Gets the transport layer under test in the fixture.
     * 
     * @return The transport layer under test in the fixture.
     */
    protected final ITransportLayer getTransportLayer()
    {
        return transportLayer_.get();
    }

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
        transportLayer_ = Optional.of( createTransportLayer() );
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
        final ITransportLayer transportLayer = getTransportLayer();
        transportLayer.endClose( transportLayer.beginClose() );
    }

    /**
     * Ensures the {@link ITransportLayer#endOpen} method throws an exception if
     * the transport layer has been closed.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testEndOpen_AfterClose()
        throws Exception
    {
        final ITransportLayer transportLayer = getTransportLayer();
        transportLayer.endClose( transportLayer.beginClose() );

        final Future<@Nullable Void> future = transportLayer.beginOpen( "localhost", 8888 ); //$NON-NLS-1$
        try
        {
            transportLayer.endOpen( future );
            fail( "expected IllegalStateException" ); //$NON-NLS-1$
        }
        catch( @SuppressWarnings( "unused" ) final IllegalStateException e )
        {
            // expected
        }
    }

    /**
     * Ensures the {@link ITransportLayer#endOpen} method throws an exception
     * when attempting to open the transport layer more than once.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testEndOpen_MultipleInvocations()
        throws Exception
    {
        final ITransportLayer transportLayer = getTransportLayer();

        try
        {
            transportLayer.endOpen( transportLayer.beginOpen( "localhost", 8888 ) ); //$NON-NLS-1$
        }
        catch( @SuppressWarnings( "unused" ) final TransportException e )
        {
            // ignore transport layer errors
        }

        final Future<@Nullable Void> future = transportLayer.beginOpen( "localhost", 8888 ); //$NON-NLS-1$
        try
        {
            transportLayer.endOpen( future );
            fail( "expected IllegalStateException" ); //$NON-NLS-1$
        }
        catch( @SuppressWarnings( "unused" ) final IllegalStateException e )
        {
            // expected
        }
    }
}

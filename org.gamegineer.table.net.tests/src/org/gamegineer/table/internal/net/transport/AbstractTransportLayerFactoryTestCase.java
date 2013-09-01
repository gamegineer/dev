/*
 * AbstractTransportLayerFactoryTestCase.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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
 * Created on Jan 15, 2011 at 11:52:40 PM.
 */

package org.gamegineer.table.internal.net.transport;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.transport.ITransportLayerFactory}
 * interface.
 */
public abstract class AbstractTransportLayerFactoryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The transport layer factory under test in the fixture. */
    private ITransportLayerFactory transportLayerFactory_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractTransportLayerFactoryTestCase} class.
     */
    protected AbstractTransportLayerFactoryTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the transport layer factory to be tested.
     * 
     * @return The transport layer factory to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ITransportLayerFactory createTransportLayerFactory()
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
        transportLayerFactory_ = createTransportLayerFactory();
        assertNotNull( transportLayerFactory_ );
    }

    /**
     * Ensures the {@link ITransportLayerFactory#createActiveTransportLayer}
     * method throws an exception when passed a {@code null} context.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateActiveTransportLayer_Context_Null()
        throws Exception
    {
        transportLayerFactory_.createActiveTransportLayer( null );
    }

    /**
     * Ensures the {@link ITransportLayerFactory#createPassiveTransportLayer}
     * method throws an exception when passed a {@code null} context.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreatePassiveTransportLayer_Context_Null()
        throws Exception
    {
        transportLayerFactory_.createPassiveTransportLayer( null );
    }
}

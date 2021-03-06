/*
 * AbstractTransportLayerFactoryTestCase.java
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
 * Created on Jan 15, 2011 at 11:52:40 PM.
 */

package org.gamegineer.table.internal.net.impl.transport;

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link ITransportLayerFactory} interface.
 */
public abstract class AbstractTransportLayerFactoryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The transport layer factory under test in the fixture. */
    private Optional<ITransportLayerFactory> transportLayerFactory_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractTransportLayerFactoryTestCase} class.
     */
    protected AbstractTransportLayerFactoryTestCase()
    {
        transportLayerFactory_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the transport layer factory to be tested.
     * 
     * @return The transport layer factory to be tested.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract ITransportLayerFactory createTransportLayerFactory()
        throws Exception;

    /**
     * Gets the transport layer factory under test in the fixture.
     * 
     * @return The transport layer factory under test in the fixture.
     */
    protected final ITransportLayerFactory getTransportLayerFactory()
    {
        return transportLayerFactory_.get();
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
        transportLayerFactory_ = Optional.of( createTransportLayerFactory() );
    }

    /**
     * Placeholder for future interface tests.
     */
    @Test
    public void testDummy()
    {
        // do nothing
    }
}

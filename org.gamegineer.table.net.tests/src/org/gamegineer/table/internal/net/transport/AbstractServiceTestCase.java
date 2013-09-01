/*
 * AbstractServiceTestCase.java
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
 * Created on Apr 1, 2011 at 9:27:25 PM.
 */

package org.gamegineer.table.internal.net.transport;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.transport.IService} interface.
 */
public abstract class AbstractServiceTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The service under test in the fixture. */
    private IService service_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractServiceTestCase} class.
     */
    protected AbstractServiceTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the service to be tested.
     * 
     * @return The service to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IService createService()
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
        service_ = createService();
        assertNotNull( service_ );
    }

    /**
     * Ensures the {@link IService#messageReceived} method throws an exception
     * when passed a {@code null} message envelope.
     */
    @Test( expected = NullPointerException.class )
    public void testMessageReceived_MessageEnvelope_Null()
    {
        service_.messageReceived( null );
    }

    /**
     * Ensures the {@link IService#started} method throws an exception when
     * passed a {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testStarted_Context_Null()
    {
        service_.started( null );
    }
}

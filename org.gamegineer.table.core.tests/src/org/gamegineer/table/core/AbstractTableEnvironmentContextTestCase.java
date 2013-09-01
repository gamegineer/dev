/*
 * AbstractTableEnvironmentContextTestCase.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on May 28, 2013 at 8:22:43 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertNotNull;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.ITableEnvironmentContext} interface.
 */
public abstract class AbstractTableEnvironmentContextTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table environment context under test in the fixture. */
    private ITableEnvironmentContext tableEnvironmentContext_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractTableEnvironmentContextTestCase} class.
     */
    protected AbstractTableEnvironmentContextTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table environment context to be tested.
     * 
     * @return The table environment context to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ITableEnvironmentContext createTableEnvironmentContext()
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
        tableEnvironmentContext_ = createTableEnvironmentContext();
        assertNotNull( tableEnvironmentContext_ );
    }

    /**
     * Ensures the {@link ITableEnvironmentContext#fireEventNotification} method
     * throws an exception when passed a {@code null} event notification.
     */
    @Test( expected = NullPointerException.class )
    public void testFireEventNotification_EventNotification_Null()
    {
        tableEnvironmentContext_.getLock().lock();
        try
        {
            tableEnvironmentContext_.fireEventNotification( null );
        }
        finally
        {
            tableEnvironmentContext_.getLock().unlock();
        }
    }

    /**
     * Ensures the {@link ITableEnvironmentContext#fireEventNotification} method
     * throws an exception when invoked while the table environment lock is not
     * held.
     */
    @Test( expected = IllegalStateException.class )
    public void testFireEventNotification_ThrowsExceptionWhenLockNotHeld()
    {
        tableEnvironmentContext_.fireEventNotification( EasyMock.createMock( Runnable.class ) );
    }

    /**
     * Ensures the {@link ITableEnvironmentContext#getLock} method does not
     * return {@code null}.
     */
    @Test
    public void testGetLock_ReturnValue_NonNull()
    {
        assertNotNull( tableEnvironmentContext_.getLock() );
    }
}

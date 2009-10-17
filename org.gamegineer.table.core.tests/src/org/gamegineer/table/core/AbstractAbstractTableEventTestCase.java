/*
 * AbstractAbstractTableEventTestCase.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Oct 16, 2009 at 10:47:38 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link org.gamegineer.table.core.TableEvent} class.
 * 
 * @param <T>
 *        The type of the table event.
 */
public abstract class AbstractAbstractTableEventTestCase<T extends TableEvent>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table event under test in the fixture. */
    private T event_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractAbstractTableEventTestCase} class.
     */
    protected AbstractAbstractTableEventTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table event to be tested.
     * 
     * @return The table event to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract T createTableEvent()
        throws Exception;

    /**
     * Gets the table event under test in the fixture.
     * 
     * @return The table event under test in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final T getTableEvent()
    {
        assertNotNull( event_ );
        return event_;
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
        event_ = createTableEvent();
        assertNotNull( event_ );
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
        event_ = null;
    }

    /**
     * Ensures the {@code getSource} method returns the same instance as the
     * {@code getTable} method.
     */
    @Test
    public void testGetSource_ReturnValue_SameTable()
    {
        assertSame( event_.getTable(), event_.getSource() );
    }
}

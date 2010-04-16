/*
 * MainModelTest.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Oct 6, 2009 at 11:57:40 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.gamegineer.table.ui.TableAdvisor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.MainModel} class.
 */
public final class MainModelTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The main model under test in the fixture. */
    private MainModel model_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MainModelTest} class.
     */
    public MainModelTest()
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
        model_ = new MainModel( new TableAdvisor() );
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
        model_ = null;
    }

    /**
     * Ensures the {@code addMainModelListener} method throws an exception when
     * passed a listener that is absent from the main model listener collection
     * but another listener is present.
     */
    @Test( expected = IllegalStateException.class )
    public void testAddMainModelListener_Listener_Absent_OtherListenerPresent()
    {
        model_.addMainModelListener( new MockMainModelListener() );

        model_.addMainModelListener( new MockMainModelListener() );
    }

    /**
     * Ensures the {@code addMainModelListener} method throws an exception when
     * passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddMainModelListener_Listener_Null()
    {
        model_.addMainModelListener( null );
    }

    /**
     * Ensures the {@code addMainModelListener} method throws an exception when
     * passed a listener that is present in the main model listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddMainModelListener_Listener_Present()
    {
        final IMainModelListener listener = new MockMainModelListener();
        model_.addMainModelListener( listener );

        model_.addMainModelListener( listener );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * table advisor.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Advisor_Null()
    {
        new MainModel( null );
    }

    /**
     * Ensures the {@code getVersion} method does not return {@code null}.
     */
    @Test
    public void testGetVersion_ReturnValue_NonNull()
    {
        assertNotNull( model_.getVersion() );
    }

    /**
     * Ensures the {@code openTable} method catches any exception thrown by the
     * {@code tableClosed} method of a main model listener.
     */
    @Test
    public void testOpenTable_TableClosed_CatchesListenerException()
    {
        final MockMainModelListener listener = new MockMainModelListener()
        {
            @Override
            public void tableClosed(
                final MainModelContentChangedEvent event )
            {
                super.tableClosed( event );

                throw new RuntimeException();
            }
        };
        model_.openTable();
        model_.addMainModelListener( listener );

        model_.openTable();
    }

    /**
     * Ensures the {@code openTable} method fires a table closed event.
     */
    @Test
    public void testOpenTable_TableClosed_FiresTableClosedEvent()
    {
        final MockMainModelListener listener = new MockMainModelListener();
        model_.openTable();
        model_.addMainModelListener( listener );

        model_.openTable();

        assertEquals( 1, listener.getTableClosedEventCount() );
    }

    /**
     * Ensures the {@code openTable} method catches any exception thrown by the
     * {@code tableOpened} method of a main model listener.
     */
    @Test
    public void testOpenTable_TableOpened_CatchesListenerException()
    {
        final MockMainModelListener listener = new MockMainModelListener()
        {
            @Override
            public void tableOpened(
                final MainModelContentChangedEvent event )
            {
                super.tableOpened( event );

                throw new RuntimeException();
            }
        };
        model_.addMainModelListener( listener );

        model_.openTable();
    }

    /**
     * Ensures the {@code openTable} method fires a table opened event.
     */
    @Test
    public void testOpenTable_TableOpened_FiresTableOpenedEvent()
    {
        final MockMainModelListener listener = new MockMainModelListener();
        model_.addMainModelListener( listener );

        model_.openTable();

        assertEquals( 1, listener.getTableOpenedEventCount() );
    }

    /**
     * Ensures the {@code removeMainModelListener} method throws an exception
     * when passed a listener that is absent from the main model listener
     * collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveMainModelListener_Listener_Absent()
    {
        model_.removeMainModelListener( new MockMainModelListener() );
    }

    /**
     * Ensures the {@code removeMainModelListener} method throws an exception
     * when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveMainModelListener_Listener_Null()
    {
        model_.removeMainModelListener( null );
    }

    /**
     * Ensures the {@code removeMainModelListener} removes a listener that is
     * present in the main model listener collection.
     */
    @Test
    public void testRemoveMainModelListener_Listener_Present()
    {
        final MockMainModelListener listener = new MockMainModelListener();
        model_.addMainModelListener( listener );
        model_.openTable();

        model_.removeMainModelListener( listener );

        model_.openTable();
        assertEquals( 1, listener.getTableOpenedEventCount() );
    }
}

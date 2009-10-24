/*
 * BasicActionTest.java
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
 * Created on Oct 9, 2009 at 11:30:17 PM.
 */

package org.gamegineer.table.internal.ui.action;

import static org.junit.Assert.assertEquals;
import java.awt.event.ActionEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.action.BasicAction} class.
 */
public final class BasicActionTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The basic action under test in the fixture. */
    private BasicAction action_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BasicActionTest} class.
     */
    public BasicActionTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates an action event suitable for testing.
     * 
     * @return An action event suitable for testing; never {@code null}.
     */
    /* @NonNull */
    private static ActionEvent createActionEvent()
    {
        return new ActionEvent( new Object(), 0, "" ); //$NON-NLS-1$
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
        action_ = new BasicAction();
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
        action_ = null;
    }

    /**
     * Ensures the {@code addActionEnabledPredicate} method adds a predicate
     * this is absent.
     */
    @Test
    public void testAddActionEnabledPredicate_Predicate_Absent()
    {
        final MockActionEnabledPredicate predicate = new MockActionEnabledPredicate();

        action_.addActionEnabledPredicate( predicate );

        action_.update();
        assertEquals( 1, predicate.getIsActionEnabledCallCount() );
    }

    /**
     * Ensures the {@code addActionEnabledPredicate} method throws an exception
     * when passed a {@code null} predicate.
     */
    @Test( expected = NullPointerException.class )
    public void testAddActionEnabledPredicate_Predicate_Null()
    {
        action_.addActionEnabledPredicate( null );
    }

    /**
     * Ensures the {@code addActionEnabledPredicate} method throws an exception
     * when passed a predicate that is present.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddActionEnabledPredicate_Predicate_Present()
    {
        final MockActionEnabledPredicate predicate = new MockActionEnabledPredicate();
        action_.addActionEnabledPredicate( predicate );

        action_.addActionEnabledPredicate( predicate );
    }

    /**
     * Ensures the {@code addActionListener} method adds a listener that is
     * absent.
     */
    @Test
    public void testAddActionListener_Listener_Absent()
    {
        final MockActionListener listener = new MockActionListener();

        action_.addActionListener( listener );

        action_.actionPerformed( createActionEvent() );
        assertEquals( 1, listener.getActionPerformedEventCount() );
    }

    /**
     * Ensures the {@code addActionListener} method throws an exception when
     * passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddActionListener_Listener_Null()
    {
        action_.addActionListener( null );
    }

    /**
     * Ensures the {@code addActionListener} method throws an exception when
     * passed a listener that is present.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddActionListener_Listener_Present()
    {
        final MockActionListener listener = new MockActionListener();
        action_.addActionListener( listener );

        action_.addActionListener( listener );
    }

    /**
     * Ensures the {@code removeActionEnabledPredicate} method throws an
     * exception when passed a predicate that is absent.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveActionEnabledPredicate_Predicate_Absent()
    {
        final MockActionEnabledPredicate predicate = new MockActionEnabledPredicate();

        action_.removeActionEnabledPredicate( predicate );
    }

    /**
     * Ensures the {@code removeActionEnabledPredicate} method throws an
     * exception when passed a {@code null} predicate.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveActionEnabledPredicate_Predicate_Null()
    {
        action_.removeActionEnabledPredicate( null );
    }

    /**
     * Ensures the {@code removeActionEnabledPredicate} method removes a
     * predicate that is present.
     */
    @Test
    public void testRemoveActionEnabledPredicate_Predicate_Present()
    {
        final MockActionEnabledPredicate predicate = new MockActionEnabledPredicate();
        action_.addActionEnabledPredicate( predicate );

        action_.removeActionEnabledPredicate( predicate );

        action_.update();
        assertEquals( 0, predicate.getIsActionEnabledCallCount() );
    }

    /**
     * Ensures the {@code removeActionListener} method throws an exception when
     * passed a listener that is absent.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveActionListener_Listener_Absent()
    {
        final MockActionListener listener = new MockActionListener();

        action_.removeActionListener( listener );
    }

    /**
     * Ensures the {@code removeActionListener} method throws an exception when
     * passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveActionListener_Listener_Null()
    {
        action_.removeActionListener( null );
    }

    /**
     * Ensures the {@code removeActionListener} method removes a listener that
     * is present.
     */
    @Test
    public void testRemoveActionListener_Listener_Present()
    {
        final MockActionListener listener = new MockActionListener();
        action_.addActionListener( listener );

        action_.removeActionListener( listener );

        action_.actionPerformed( createActionEvent() );
        assertEquals( 0, listener.getActionPerformedEventCount() );
    }
}

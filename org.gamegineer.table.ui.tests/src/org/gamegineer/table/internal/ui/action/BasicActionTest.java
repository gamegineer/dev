/*
 * BasicActionTest.java
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
 * Created on Oct 9, 2009 at 11:30:17 PM.
 */

package org.gamegineer.table.internal.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.common.core.util.IPredicate;
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

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;


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
     * Creates a new mock predicate associated with the fixture mocks control.
     * 
     * @return A new mock predicate; never {@code null}.
     */
    /* @NonNull */
    private IPredicate<Action> createMockPredicate()
    {
        @SuppressWarnings( "unchecked" )
        final IPredicate<Action> mockPredicate = mocksControl_.createMock( IPredicate.class );
        return mockPredicate;
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
        mocksControl_ = EasyMock.createControl();
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
        mocksControl_ = null;
    }

    /**
     * Ensures the {@code addActionListener} method adds a listener that is
     * absent from the action listener collection.
     */
    @Test
    public void testAddActionListener_Listener_Absent()
    {
        final ActionListener listener = mocksControl_.createMock( ActionListener.class );
        listener.actionPerformed( EasyMock.notNull( ActionEvent.class ) );
        mocksControl_.replay();

        action_.addActionListener( listener );
        action_.actionPerformed( createActionEvent() );

        mocksControl_.verify();
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
     * passed a listener that is present in the action listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddActionListener_Listener_Present()
    {
        final ActionListener listener = mocksControl_.createMock( ActionListener.class );
        action_.addActionListener( listener );

        action_.addActionListener( listener );
    }

    /**
     * Ensures the {@code addShouldEnablePredicate} method adds a predicate this
     * is absent from the should enable predicate collection.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testAddShouldEnablePredicate_Predicate_Absent()
    {
        final IPredicate<Action> predicate = createMockPredicate();
        EasyMock.expect( predicate.evaluate( EasyMock.notNull( Action.class ) ) ).andReturn( false );
        mocksControl_.replay();

        action_.addShouldEnablePredicate( predicate );
        action_.update();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code addShouldEnablePredicate} method throws an exception
     * when passed a {@code null} predicate.
     */
    @Test( expected = NullPointerException.class )
    public void testAddShouldEnablePredicate_Predicate_Null()
    {
        action_.addShouldEnablePredicate( null );
    }

    /**
     * Ensures the {@code addShouldEnablePredicate} method throws an exception
     * when passed a predicate that is present in the should enable predicate
     * collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddShouldEnablePredicate_Predicate_Present()
    {
        final IPredicate<Action> predicate = createMockPredicate();
        action_.addShouldEnablePredicate( predicate );

        action_.addShouldEnablePredicate( predicate );
    }

    /**
     * Ensures the {@code addShouldSelectPredicate} method adds a predicate this
     * is absent from the should select predicate collection.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testAddShouldSelectPredicate_Predicate_Absent()
    {
        final IPredicate<Action> predicate = createMockPredicate();
        EasyMock.expect( predicate.evaluate( EasyMock.notNull( Action.class ) ) ).andReturn( false );
        mocksControl_.replay();

        action_.addShouldSelectPredicate( predicate );
        action_.update();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code addShouldSelectPredicate} method throws an exception
     * when passed a {@code null} predicate.
     */
    @Test( expected = NullPointerException.class )
    public void testAddShouldSelectPredicate_Predicate_Null()
    {
        action_.addShouldSelectPredicate( null );
    }

    /**
     * Ensures the {@code addShouldSelectPredicate} method throws an exception
     * when passed a predicate that is present in the should select predicate
     * collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddShouldSelectPredicate_Predicate_Present()
    {
        final IPredicate<Action> predicate = createMockPredicate();
        action_.addShouldSelectPredicate( predicate );

        action_.addShouldSelectPredicate( predicate );
    }

    /**
     * Ensures the {@code removeActionListener} method throws an exception when
     * passed a listener that is absent from the action listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveActionListener_Listener_Absent()
    {
        action_.removeActionListener( mocksControl_.createMock( ActionListener.class ) );
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
     * is present in the action listener collection.
     */
    @Test
    public void testRemoveActionListener_Listener_Present()
    {
        final ActionListener listener = mocksControl_.createMock( ActionListener.class );
        mocksControl_.replay();
        action_.addActionListener( listener );

        action_.removeActionListener( listener );
        action_.actionPerformed( createActionEvent() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeShouldEnablePredicate} method throws an
     * exception when passed a predicate that is absent from the should enable
     * predicate collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveShouldEnablePredicate_Predicate_Absent()
    {
        action_.removeShouldEnablePredicate( createMockPredicate() );
    }

    /**
     * Ensures the {@code removeShouldEnablePredicate} method throws an
     * exception when passed a {@code null} predicate.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveShouldEnablePredicate_Predicate_Null()
    {
        action_.removeShouldEnablePredicate( null );
    }

    /**
     * Ensures the {@code removeShouldEnablePredicate} method removes a
     * predicate that is present in the should enable predicate collection.
     */
    @Test
    public void testRemoveShouldEnablePredicate_Predicate_Present()
    {
        final IPredicate<Action> predicate = createMockPredicate();
        mocksControl_.replay();
        action_.addShouldEnablePredicate( predicate );

        action_.removeShouldEnablePredicate( predicate );
        action_.update();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeShouldSelectPredicate} method throws an
     * exception when passed a predicate that is absent from the should select
     * predicate collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveShouldSelectPredicate_Predicate_Absent()
    {
        action_.removeShouldSelectPredicate( createMockPredicate() );
    }

    /**
     * Ensures the {@code removeShouldSelectPredicate} method throws an
     * exception when passed a {@code null} predicate.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveShouldSelectPredicate_Predicate_Null()
    {
        action_.removeShouldSelectPredicate( null );
    }

    /**
     * Ensures the {@code removeShouldSelectPredicate} method removes a
     * predicate that is present in the should select predicate collection.
     */
    @Test
    public void testRemoveShouldSelectPredicate_Predicate_Present()
    {
        final IPredicate<Action> predicate = createMockPredicate();
        mocksControl_.replay();
        action_.addShouldSelectPredicate( predicate );

        action_.removeShouldSelectPredicate( predicate );
        action_.update();

        mocksControl_.verify();
    }
}

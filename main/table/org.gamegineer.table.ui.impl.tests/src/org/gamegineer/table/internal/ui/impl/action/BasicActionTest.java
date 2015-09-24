/*
 * BasicActionTest.java
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
 * Created on Oct 9, 2009 at 11:30:17 PM.
 */

package org.gamegineer.table.internal.ui.impl.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import javax.swing.Action;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.common.core.util.IPredicate;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link BasicAction} class.
 */
public final class BasicActionTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The basic action under test in the fixture. */
    private Optional<BasicAction> basicAction_;

    /** The mocks control for use in the fixture. */
    private Optional<IMocksControl> mocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BasicActionTest} class.
     */
    public BasicActionTest()
    {
        basicAction_ = Optional.empty();
        mocksControl_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates an action event suitable for testing.
     * 
     * @return An action event suitable for testing.
     */
    private static ActionEvent createActionEvent()
    {
        return new ActionEvent( new Object(), 0, "" ); //$NON-NLS-1$
    }

    /**
     * Gets the basic action under test in the fixture.
     * 
     * @return The basic action under test in the fixture.
     */
    private BasicAction getBasicAction()
    {
        return basicAction_.get();
    }

    /**
     * Gets the fixture mocks control.
     * 
     * @return The fixture mocks control.
     */
    private IMocksControl getMocksControl()
    {
        return mocksControl_.get();
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
        mocksControl_ = Optional.of( EasyMock.createControl() );
        basicAction_ = Optional.of( new BasicAction( "id" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link BasicAction#addActionListener} method adds a listener
     * that is absent from the action listener collection.
     */
    @Test
    public void testAddActionListener_Listener_Absent()
    {
        final BasicAction basicAction = getBasicAction();
        final IMocksControl mocksControl = getMocksControl();
        final ActionListener listener = mocksControl.createMock( ActionListener.class );
        listener.actionPerformed( EasyMock.<ActionEvent>notNull() );
        mocksControl.replay();

        basicAction.addActionListener( listener );
        basicAction.actionPerformed( createActionEvent() );

        mocksControl.verify();
    }

    /**
     * Ensures the {@link BasicAction#addActionListener} method throws an
     * exception when passed a listener that is present in the action listener
     * collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddActionListener_Listener_Present()
    {
        final BasicAction basicAction = getBasicAction();
        final ActionListener listener = getMocksControl().createMock( ActionListener.class );
        basicAction.addActionListener( listener );

        basicAction.addActionListener( listener );
    }

    /**
     * Ensures the {@link BasicAction#addShouldEnablePredicate} method adds a
     * predicate this is absent from the should enable predicate collection.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testAddShouldEnablePredicate_Predicate_Absent()
    {
        final BasicAction basicAction = getBasicAction();
        final IMocksControl mocksControl = getMocksControl();
        final IPredicate<Action> predicate = mocksControl.createMock( IPredicate.class );
        EasyMock.expect( predicate.evaluate( EasyMock.<Action>notNull() ) ).andReturn( false );
        mocksControl.replay();

        basicAction.addShouldEnablePredicate( predicate );
        basicAction.update();

        mocksControl.verify();
    }

    /**
     * Ensures the {@link BasicAction#addShouldEnablePredicate} method throws an
     * exception when passed a predicate that is present in the should enable
     * predicate collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddShouldEnablePredicate_Predicate_Present()
    {
        final BasicAction basicAction = getBasicAction();
        final IPredicate<Action> predicate = getMocksControl().createMock( IPredicate.class );
        basicAction.addShouldEnablePredicate( predicate );

        basicAction.addShouldEnablePredicate( predicate );
    }

    /**
     * Ensures the {@link BasicAction#addShouldSelectPredicate} method adds a
     * predicate this is absent from the should select predicate collection.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testAddShouldSelectPredicate_Predicate_Absent()
    {
        final BasicAction basicAction = getBasicAction();
        final IMocksControl mocksControl = getMocksControl();
        final IPredicate<Action> predicate = mocksControl.createMock( IPredicate.class );
        EasyMock.expect( predicate.evaluate( EasyMock.<Action>notNull() ) ).andReturn( false );
        mocksControl.replay();

        basicAction.addShouldSelectPredicate( predicate );
        basicAction.update();

        mocksControl.verify();
    }

    /**
     * Ensures the {@link BasicAction#addShouldSelectPredicate} method throws an
     * exception when passed a predicate that is present in the should select
     * predicate collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddShouldSelectPredicate_Predicate_Present()
    {
        final BasicAction basicAction = getBasicAction();
        final IPredicate<Action> predicate = getMocksControl().createMock( IPredicate.class );
        basicAction.addShouldSelectPredicate( predicate );

        basicAction.addShouldSelectPredicate( predicate );
    }

    /**
     * Ensures the {@link BasicAction#removeActionListener} method throws an
     * exception when passed a listener that is absent from the action listener
     * collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveActionListener_Listener_Absent()
    {
        getBasicAction().removeActionListener( getMocksControl().createMock( ActionListener.class ) );
    }

    /**
     * Ensures the {@link BasicAction#removeActionListener} method removes a
     * listener that is present in the action listener collection.
     */
    @Test
    public void testRemoveActionListener_Listener_Present()
    {
        final BasicAction basicAction = getBasicAction();
        final IMocksControl mocksControl = getMocksControl();
        final ActionListener listener = mocksControl.createMock( ActionListener.class );
        mocksControl.replay();
        basicAction.addActionListener( listener );

        basicAction.removeActionListener( listener );
        basicAction.actionPerformed( createActionEvent() );

        mocksControl.verify();
    }

    /**
     * Ensures the {@link BasicAction#removeShouldEnablePredicate} method throws
     * an exception when passed a predicate that is absent from the should
     * enable predicate collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveShouldEnablePredicate_Predicate_Absent()
    {
        getBasicAction().removeShouldEnablePredicate( getMocksControl().createMock( IPredicate.class ) );
    }

    /**
     * Ensures the {@link BasicAction#removeShouldEnablePredicate} method
     * removes a predicate that is present in the should enable predicate
     * collection.
     */
    @Test
    public void testRemoveShouldEnablePredicate_Predicate_Present()
    {
        final BasicAction basicAction = getBasicAction();
        final IMocksControl mocksControl = getMocksControl();
        final IPredicate<Action> predicate = mocksControl.createMock( IPredicate.class );
        mocksControl.replay();
        basicAction.addShouldEnablePredicate( predicate );

        basicAction.removeShouldEnablePredicate( predicate );
        basicAction.update();

        mocksControl.verify();
    }

    /**
     * Ensures the {@link BasicAction#removeShouldSelectPredicate} method throws
     * an exception when passed a predicate that is absent from the should
     * select predicate collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveShouldSelectPredicate_Predicate_Absent()
    {
        getBasicAction().removeShouldSelectPredicate( getMocksControl().createMock( IPredicate.class ) );
    }

    /**
     * Ensures the {@link BasicAction#removeShouldSelectPredicate} method
     * removes a predicate that is present in the should select predicate
     * collection.
     */
    @Test
    public void testRemoveShouldSelectPredicate_Predicate_Present()
    {
        final BasicAction basicAction = getBasicAction();
        final IMocksControl mocksControl = getMocksControl();
        final IPredicate<Action> predicate = mocksControl.createMock( IPredicate.class );
        mocksControl.replay();
        basicAction.addShouldSelectPredicate( predicate );

        basicAction.removeShouldSelectPredicate( predicate );
        basicAction.update();

        mocksControl.verify();
    }
}

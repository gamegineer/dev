/*
 * ActionMediatorTest.java
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
 * Created on Oct 18, 2009 at 10:06:59 PM.
 */

package org.gamegineer.table.internal.ui.action;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertEquals;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.action.ActionMediator} class.
 */
public final class ActionMediatorTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The action mediator under test in the fixture. */
    private ActionMediator mediator_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ActionMediatorTest} class.
     */
    public ActionMediatorTest()
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
        mediator_ = new ActionMediator();
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
        mediator_ = null;
    }

    /**
     * Ensures the {@code bind(BasicAction, IActionEnabledPredicate)} method
     * throws an exception when passed a {@code null} action.
     */
    @Test( expected = NullPointerException.class )
    public void testBindActionEnabledPredicate_Action_Null()
    {
        mediator_.bind( null, createDummy( IActionEnabledPredicate.class ) );
    }

    /**
     * Ensures the {@code bind(BasicAction, IActionEnabledPredicate)} method
     * throws an exception when a predicate is already bound to the action.
     * 
     */
    @Test( expected = IllegalStateException.class )
    public void testBindActionEnabledPredicate_Predicate_Bound()
    {
        final BasicAction action = new BasicAction();
        final MockActionEnabledPredicate predicate = new MockActionEnabledPredicate();
        mediator_.bind( action, predicate );

        mediator_.bind( action, predicate );
    }

    /**
     * Ensures the {@code bind(BasicAction, IActionEnabledPredicate)} method
     * throws an exception when passed a {@code null} predicate.
     */
    @Test( expected = NullPointerException.class )
    public void testBindActionEnabledPredicate_Predicate_Null()
    {
        mediator_.bind( new BasicAction(), (IActionEnabledPredicate)null );
    }

    /**
     * Ensures the {@code bind(BasicAction, IActionEnabledPredicate)} method
     * binds a predicate when no predicate is already bound to the action.
     */
    @Test
    public void testBindActionEnabledPredicate_Predicate_Unbound()
    {
        final BasicAction action = new BasicAction();
        final MockActionEnabledPredicate predicate = new MockActionEnabledPredicate();

        mediator_.bind( action, predicate );

        final int expectedIsActionEnabledCallCount = predicate.getIsActionEnabledCallCount() + 1;
        action.update();
        assertEquals( expectedIsActionEnabledCallCount, predicate.getIsActionEnabledCallCount() );
    }

    /**
     * Ensures the {@code bind(BasicAction, IActionEnabledPredicate)} method
     * updates the action after binding the predicate.
     */
    @Test
    public void testBindActionEnabledPredicate_UpdatesAction()
    {
        final BasicAction action = new BasicAction();
        final MockActionEnabledPredicate predicate = new MockActionEnabledPredicate();

        mediator_.bind( action, predicate );

        assertEquals( 1, predicate.getIsActionEnabledCallCount() );
    }

    /**
     * Ensures the {@code bind(BasicAction, ActionListener)} method throws an
     * exception when passed a {@code null} action.
     */
    @Test( expected = NullPointerException.class )
    public void testBindActionListener_Action_Null()
    {
        mediator_.bind( null, createDummy( ActionListener.class ) );
    }

    /**
     * Ensures the {@code bind(BasicAction, ActionListener)} method throws an
     * exception when a listener is already bound to the action.
     * 
     */
    @Test( expected = IllegalStateException.class )
    public void testBindActionListener_Listener_Bound()
    {
        final BasicAction action = new BasicAction();
        final MockActionListener listener = new MockActionListener();
        mediator_.bind( action, listener );

        mediator_.bind( action, listener );
    }

    /**
     * Ensures the {@code bind(BasicAction, ActionListener)} method throws an
     * exception when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testBindActionListener_Listener_Null()
    {
        mediator_.bind( new BasicAction(), (ActionListener)null );
    }

    /**
     * Ensures the {@code bind(BasicAction, ActionListener)} method binds a
     * listener when no listener is already bound to the action.
     */
    @Test
    public void testBindActionListener_Listener_Unbound()
    {
        final BasicAction action = new BasicAction();
        final MockActionListener listener = new MockActionListener();

        mediator_.bind( action, listener );

        action.actionPerformed( createActionEvent() );
        assertEquals( 1, listener.getActionPerformedEventCount() );
    }

    /**
     * Ensures the {@code unbind} method throws an exception when passed an
     * action that has no bound attachments.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUpdate_Action_NoBoundAttachments()
    {
        mediator_.unbind( new BasicAction() );
    }

    /**
     * Ensures the {@code unbind} method throws an exception when passed a
     * {@code null} action.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbind_Action_Null()
    {
        mediator_.unbind( null );
    }

    /**
     * Ensures the {@code unbind} method unbinds all action enabled predicates
     * from the action.
     */
    @Test
    public void testUnbind_UnbindsActionEnabledPredicates()
    {
        final BasicAction action = new BasicAction();
        final MockActionEnabledPredicate predicate = new MockActionEnabledPredicate();
        mediator_.bind( action, predicate );
        final int expectedIsActionEnabledCallCount = predicate.getIsActionEnabledCallCount();

        mediator_.unbind( action );

        action.update();
        assertEquals( expectedIsActionEnabledCallCount, predicate.getIsActionEnabledCallCount() );
    }

    /**
     * Ensures the {@code unbind} method unbinds all action listeners from the
     * action.
     */
    @Test
    public void testUnbind_UnbindsActionListeners()
    {
        final BasicAction action = new BasicAction();
        final MockActionListener listener = new MockActionListener();
        mediator_.bind( action, listener );

        mediator_.unbind( action );

        action.actionPerformed( createActionEvent() );
        assertEquals( 0, listener.getActionPerformedEventCount() );
    }

    /**
     * Ensures the {@code unbind} method updates the action after unbinding a
     * bound predicate.
     */
    @Test
    public void testUnbind_UpdatesAction()
    {
        final BasicAction action = new BasicAction();
        mediator_.bind( action, new MockActionEnabledPredicate() );
        final MockActionEnabledPredicate unmanagedPredicate = new MockActionEnabledPredicate();
        action.addActionEnabledPredicate( unmanagedPredicate );

        mediator_.unbind( action );

        assertEquals( 1, unmanagedPredicate.getIsActionEnabledCallCount() );
    }

    /**
     * Ensures the {@code unbindAll} method unbinds all action enabled
     * predicates from all actions.
     */
    @Test
    public void testUnbindAll_UnbindsActionEnabledPredicates()
    {
        final BasicAction action1 = new BasicAction();
        final MockActionEnabledPredicate predicate1 = new MockActionEnabledPredicate();
        mediator_.bind( action1, predicate1 );
        final int expectedIsActionEnabledCallCount1 = predicate1.getIsActionEnabledCallCount();
        final BasicAction action2 = new BasicAction();
        final MockActionEnabledPredicate predicate2 = new MockActionEnabledPredicate();
        mediator_.bind( action2, predicate2 );
        final int expectedIsActionEnabledCallCount2 = predicate2.getIsActionEnabledCallCount();

        mediator_.unbindAll();

        action1.update();
        assertEquals( expectedIsActionEnabledCallCount1, predicate1.getIsActionEnabledCallCount() );
        action2.update();
        assertEquals( expectedIsActionEnabledCallCount2, predicate2.getIsActionEnabledCallCount() );
    }

    /**
     * Ensures the {@code unbindAll} method unbinds all action listeners from
     * all actions.
     */
    @Test
    public void testUnbindAll_UnbindsActionListeners()
    {
        final BasicAction action1 = new BasicAction();
        final MockActionListener listener1 = new MockActionListener();
        mediator_.bind( action1, listener1 );
        final BasicAction action2 = new BasicAction();
        final MockActionListener listener2 = new MockActionListener();
        mediator_.bind( action2, listener2 );

        mediator_.unbindAll();

        action1.actionPerformed( createActionEvent() );
        assertEquals( 0, listener1.getActionPerformedEventCount() );
        action2.actionPerformed( createActionEvent() );
        assertEquals( 0, listener2.getActionPerformedEventCount() );
    }

    /**
     * Ensures the {@code unbindAll} method updates all actions after unbinding
     * a bound predicate.
     */
    @Test
    public void testUnbindAll_UpdatesAction()
    {
        final BasicAction action1 = new BasicAction();
        mediator_.bind( action1, new MockActionEnabledPredicate() );
        final MockActionEnabledPredicate unmanagedPredicate1 = new MockActionEnabledPredicate();
        action1.addActionEnabledPredicate( unmanagedPredicate1 );
        final BasicAction action2 = new BasicAction();
        mediator_.bind( action2, new MockActionEnabledPredicate() );
        final MockActionEnabledPredicate unmanagedPredicate2 = new MockActionEnabledPredicate();
        action2.addActionEnabledPredicate( unmanagedPredicate2 );

        mediator_.unbindAll();

        assertEquals( 1, unmanagedPredicate1.getIsActionEnabledCallCount() );
        assertEquals( 1, unmanagedPredicate2.getIsActionEnabledCallCount() );
    }

    /**
     * Ensures the {@code update} method throws an exception when passed an
     * action that has no bound predicates.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUpdate_Action_NoBoundPredicates()
    {
        mediator_.update( new BasicAction() );
    }

    /**
     * Ensures the {@code update} method throws an exception when passed a
     * {@code null} action.
     */
    @Test( expected = NullPointerException.class )
    public void testUpdate_Action_Null()
    {
        mediator_.update( null );
    }

    /**
     * Ensures the {@code update} method invokes the action enabled predicate of
     * the action.
     */
    @Test
    public void testUpdate_InvokesActionEnabledPredicate()
    {
        final BasicAction action = new BasicAction();
        final MockActionEnabledPredicate predicate = new MockActionEnabledPredicate();
        mediator_.bind( action, predicate );
        final int expectedIsActionEnabledCallCount = predicate.getIsActionEnabledCallCount() + 1;

        mediator_.update( action );

        assertEquals( expectedIsActionEnabledCallCount, predicate.getIsActionEnabledCallCount() );
    }

    /**
     * Ensures the {@code updateAll} method invokes the action enabled predicate
     * of all actions.
     */
    @Test
    public void testUpdateAll_InvokesActionEnabledPredicate()
    {
        final BasicAction action1 = new BasicAction();
        final MockActionEnabledPredicate predicate1 = new MockActionEnabledPredicate();
        mediator_.bind( action1, predicate1 );
        final int expectedIsActionEnabledCallCount1 = predicate1.getIsActionEnabledCallCount() + 1;
        final BasicAction action2 = new BasicAction();
        final MockActionEnabledPredicate predicate2 = new MockActionEnabledPredicate();
        mediator_.bind( action2, predicate2 );
        final int expectedIsActionEnabledCallCount2 = predicate2.getIsActionEnabledCallCount() + 1;

        mediator_.updateAll();

        assertEquals( expectedIsActionEnabledCallCount1, predicate1.getIsActionEnabledCallCount() );
        assertEquals( expectedIsActionEnabledCallCount2, predicate2.getIsActionEnabledCallCount() );
    }
}

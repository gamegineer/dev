/*
 * ActionMediatorTest.java
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
 * Created on Oct 18, 2009 at 10:06:59 PM.
 */

package org.gamegineer.table.internal.ui.action;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import org.gamegineer.common.core.util.IPredicate;
import org.gamegineer.common.core.util.MockPredicate;
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
     * Ensures the {@code bindActionListener} method throws an exception when
     * passed a {@code null} action.
     */
    @Test( expected = NullPointerException.class )
    public void testBindActionListener_Action_Null()
    {
        mediator_.bindActionListener( null, createMock( ActionListener.class ) );
    }

    /**
     * Ensures the {@code bindActionListener} method throws an exception when a
     * listener is already bound to the action.
     * 
     */
    @Test( expected = IllegalStateException.class )
    public void testBindActionListener_Listener_Bound()
    {
        final BasicAction action = new BasicAction();
        final MockActionListener listener = new MockActionListener();
        mediator_.bindActionListener( action, listener );

        mediator_.bindActionListener( action, listener );
    }

    /**
     * Ensures the {@code bindActionListener} method throws an exception when
     * passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testBindActionListener_Listener_Null()
    {
        mediator_.bindActionListener( new BasicAction(), (ActionListener)null );
    }

    /**
     * Ensures the {@code bindActionListener} method binds a listener when no
     * listener is already bound to the action.
     */
    @Test
    public void testBindActionListener_Listener_Unbound()
    {
        final BasicAction action = new BasicAction();
        final MockActionListener listener = new MockActionListener();

        mediator_.bindActionListener( action, listener );

        action.actionPerformed( createActionEvent() );
        assertEquals( 1, listener.getActionPerformedEventCount() );
    }

    /**
     * Ensures the {@code bindShouldEnablePredicate} method throws an exception
     * when passed a {@code null} action.
     */
    @Test( expected = NullPointerException.class )
    public void testBindShouldEnablePredicate_Action_Null()
    {
        mediator_.bindShouldEnablePredicate( null, new MockPredicate<Action>() );
    }

    /**
     * Ensures the {@code bindShouldEnablePredicate} method throws an exception
     * when a predicate is already bound to the action.
     * 
     */
    @Test( expected = IllegalStateException.class )
    public void testBindShouldEnablePredicate_Predicate_Bound()
    {
        final BasicAction action = new BasicAction();
        final MockPredicate<Action> predicate = new MockPredicate<Action>();
        mediator_.bindShouldEnablePredicate( action, predicate );

        mediator_.bindShouldEnablePredicate( action, predicate );
    }

    /**
     * Ensures the {@code bindShouldEnablePredicate} method throws an exception
     * when passed a {@code null} predicate.
     */
    @Test( expected = NullPointerException.class )
    public void testBindShouldEnablePredicate_Predicate_Null()
    {
        mediator_.bindShouldEnablePredicate( new BasicAction(), (IPredicate<Action>)null );
    }

    /**
     * Ensures the {@code bindShouldEnablePredicate} method binds a predicate
     * when no predicate is already bound to the action.
     */
    @Test
    public void testBindShouldEnablePredicate_Predicate_Unbound()
    {
        final BasicAction action = new BasicAction();
        final MockPredicate<Action> predicate = new MockPredicate<Action>();

        mediator_.bindShouldEnablePredicate( action, predicate );

        final int expectedEvaluateCallCount = predicate.getEvaluateCallCount() + 1;
        action.update();
        assertEquals( expectedEvaluateCallCount, predicate.getEvaluateCallCount() );
    }

    /**
     * Ensures the {@code bindShouldEnablePredicate} method updates the action
     * after binding the predicate.
     */
    @Test
    public void testBindShouldEnablePredicate_UpdatesAction()
    {
        final BasicAction action = new BasicAction();
        final MockPredicate<Action> predicate = new MockPredicate<Action>();

        mediator_.bindShouldEnablePredicate( action, predicate );

        assertEquals( 1, predicate.getEvaluateCallCount() );
    }

    //

    /**
     * Ensures the {@code bindShouldSelectPredicate} method throws an exception
     * when passed a {@code null} action.
     */
    @Test( expected = NullPointerException.class )
    public void testBindShouldSelectPredicate_Action_Null()
    {
        mediator_.bindShouldSelectPredicate( null, new MockPredicate<Action>() );
    }

    /**
     * Ensures the {@code bindShouldSelectPredicate} method throws an exception
     * when a predicate is already bound to the action.
     * 
     */
    @Test( expected = IllegalStateException.class )
    public void testBindShouldSelectPredicate_Predicate_Bound()
    {
        final BasicAction action = new BasicAction();
        final MockPredicate<Action> predicate = new MockPredicate<Action>();
        mediator_.bindShouldSelectPredicate( action, predicate );

        mediator_.bindShouldSelectPredicate( action, predicate );
    }

    /**
     * Ensures the {@code bindShouldSelectPredicate} method throws an exception
     * when passed a {@code null} predicate.
     */
    @Test( expected = NullPointerException.class )
    public void testBindShouldSelectPredicate_Predicate_Null()
    {
        mediator_.bindShouldSelectPredicate( new BasicAction(), (IPredicate<Action>)null );
    }

    /**
     * Ensures the {@code bindShouldSelectPredicate} method binds a predicate
     * when no predicate is already bound to the action.
     */
    @Test
    public void testBindShouldSelectPredicate_Predicate_Unbound()
    {
        final BasicAction action = new BasicAction();
        final MockPredicate<Action> predicate = new MockPredicate<Action>();

        mediator_.bindShouldSelectPredicate( action, predicate );

        final int expectedEvaluateCallCount = predicate.getEvaluateCallCount() + 1;
        action.update();
        assertEquals( expectedEvaluateCallCount, predicate.getEvaluateCallCount() );
    }

    /**
     * Ensures the {@code bindShouldSelectPredicate} method updates the action
     * after binding the predicate.
     */
    @Test
    public void testBindShouldSelectPredicate_UpdatesAction()
    {
        final BasicAction action = new BasicAction();
        final MockPredicate<Action> predicate = new MockPredicate<Action>();

        mediator_.bindShouldSelectPredicate( action, predicate );

        assertEquals( 1, predicate.getEvaluateCallCount() );
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
     * Ensures the {@code unbind} method unbinds all action listeners from the
     * action.
     */
    @Test
    public void testUnbind_UnbindsActionListeners()
    {
        final BasicAction action = new BasicAction();
        final MockActionListener listener = new MockActionListener();
        mediator_.bindActionListener( action, listener );

        mediator_.unbind( action );

        action.actionPerformed( createActionEvent() );
        assertEquals( 0, listener.getActionPerformedEventCount() );
    }

    /**
     * Ensures the {@code unbind} method unbinds all should enable predicates
     * from the action.
     */
    @Test
    public void testUnbind_UnbindsShouldEnablePredicates()
    {
        final BasicAction action = new BasicAction();
        final MockPredicate<Action> predicate = new MockPredicate<Action>();
        mediator_.bindShouldEnablePredicate( action, predicate );
        final int expectedEvaluateCallCount = predicate.getEvaluateCallCount();

        mediator_.unbind( action );

        action.update();
        assertEquals( expectedEvaluateCallCount, predicate.getEvaluateCallCount() );
    }

    /**
     * Ensures the {@code unbind} method unbinds all should select predicates
     * from the action.
     */
    @Test
    public void testUnbind_UnbindsShouldSelectPredicates()
    {
        final BasicAction action = new BasicAction();
        final MockPredicate<Action> predicate = new MockPredicate<Action>();
        mediator_.bindShouldSelectPredicate( action, predicate );
        final int expectedEvaluateCallCount = predicate.getEvaluateCallCount();

        mediator_.unbind( action );

        action.update();
        assertEquals( expectedEvaluateCallCount, predicate.getEvaluateCallCount() );
    }

    /**
     * Ensures the {@code unbind} method updates the action after unbinding a
     * bound predicate.
     */
    @Test
    public void testUnbind_UpdatesAction()
    {
        final BasicAction action = new BasicAction();
        mediator_.bindShouldEnablePredicate( action, new MockPredicate<Action>() );
        final MockPredicate<Action> unmanagedPredicate = new MockPredicate<Action>();
        action.addShouldEnablePredicate( unmanagedPredicate );

        mediator_.unbind( action );

        assertEquals( 1, unmanagedPredicate.getEvaluateCallCount() );
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
        mediator_.bindActionListener( action1, listener1 );
        final BasicAction action2 = new BasicAction();
        final MockActionListener listener2 = new MockActionListener();
        mediator_.bindActionListener( action2, listener2 );

        mediator_.unbindAll();

        action1.actionPerformed( createActionEvent() );
        assertEquals( 0, listener1.getActionPerformedEventCount() );
        action2.actionPerformed( createActionEvent() );
        assertEquals( 0, listener2.getActionPerformedEventCount() );
    }

    /**
     * Ensures the {@code unbindAll} method unbinds all should enable predicates
     * from all actions.
     */
    @Test
    public void testUnbindAll_UnbindsShouldEnablePredicates()
    {
        final BasicAction action1 = new BasicAction();
        final MockPredicate<Action> predicate1 = new MockPredicate<Action>();
        mediator_.bindShouldEnablePredicate( action1, predicate1 );
        final int expectedEvaluateCallCount1 = predicate1.getEvaluateCallCount();
        final BasicAction action2 = new BasicAction();
        final MockPredicate<Action> predicate2 = new MockPredicate<Action>();
        mediator_.bindShouldEnablePredicate( action2, predicate2 );
        final int expectedEvaluateCallCount2 = predicate2.getEvaluateCallCount();

        mediator_.unbindAll();

        action1.update();
        assertEquals( expectedEvaluateCallCount1, predicate1.getEvaluateCallCount() );
        action2.update();
        assertEquals( expectedEvaluateCallCount2, predicate2.getEvaluateCallCount() );
    }

    /**
     * Ensures the {@code unbindAll} method unbinds all should select predicates
     * from all actions.
     */
    @Test
    public void testUnbindAll_UnbindsShouldSelectPredicates()
    {
        final BasicAction action1 = new BasicAction();
        final MockPredicate<Action> predicate1 = new MockPredicate<Action>();
        mediator_.bindShouldSelectPredicate( action1, predicate1 );
        final int expectedEvaluateCallCount1 = predicate1.getEvaluateCallCount();
        final BasicAction action2 = new BasicAction();
        final MockPredicate<Action> predicate2 = new MockPredicate<Action>();
        mediator_.bindShouldSelectPredicate( action2, predicate2 );
        final int expectedEvaluateCallCount2 = predicate2.getEvaluateCallCount();

        mediator_.unbindAll();

        action1.update();
        assertEquals( expectedEvaluateCallCount1, predicate1.getEvaluateCallCount() );
        action2.update();
        assertEquals( expectedEvaluateCallCount2, predicate2.getEvaluateCallCount() );
    }

    /**
     * Ensures the {@code unbindAll} method updates all actions after unbinding
     * a bound predicate.
     */
    @Test
    public void testUnbindAll_UpdatesAction()
    {
        final BasicAction action1 = new BasicAction();
        mediator_.bindShouldEnablePredicate( action1, new MockPredicate<Action>() );
        final MockPredicate<Action> unmanagedPredicate1 = new MockPredicate<Action>();
        action1.addShouldEnablePredicate( unmanagedPredicate1 );
        final BasicAction action2 = new BasicAction();
        mediator_.bindShouldEnablePredicate( action2, new MockPredicate<Action>() );
        final MockPredicate<Action> unmanagedPredicate2 = new MockPredicate<Action>();
        action2.addShouldEnablePredicate( unmanagedPredicate2 );

        mediator_.unbindAll();

        assertEquals( 1, unmanagedPredicate1.getEvaluateCallCount() );
        assertEquals( 1, unmanagedPredicate2.getEvaluateCallCount() );
    }
}

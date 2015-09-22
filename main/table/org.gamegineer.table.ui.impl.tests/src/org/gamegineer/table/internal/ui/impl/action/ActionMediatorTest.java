/*
 * ActionMediatorTest.java
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
 * Created on Oct 18, 2009 at 10:06:59 PM.
 */

package org.gamegineer.table.internal.ui.impl.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.gamegineer.common.core.util.IPredicate;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link ActionMediator} class.
 */
@NonNullByDefault( {
    DefaultLocation.PARAMETER, //
    DefaultLocation.RETURN_TYPE, //
    DefaultLocation.TYPE_BOUND, //
    DefaultLocation.TYPE_ARGUMENT
} )
public final class ActionMediatorTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The action mediator under test in the fixture. */
    private ActionMediator mediator_;

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ActionMediatorTest} class.
     */
    public ActionMediatorTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates an action event suitable for testing.
     * 
     * @return An action event suitable for testing; never {@code null}.
     */
    private static ActionEvent createActionEvent()
    {
        return new ActionEvent( new Object(), 0, "" ); //$NON-NLS-1$
    }

    /**
     * Creates a new basic action suitable for testing.
     * 
     * @return A new basic action suitable for testing; never {@code null}.
     */
    private static BasicAction createBasicAction()
    {
        return new BasicAction( "id" ); //$NON-NLS-1$
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
        mediator_ = new ActionMediator();
    }

    /**
     * Ensures the {@link ActionMediator#bindActionListener} method throws an
     * exception when a listener is already bound to the action.
     * 
     */
    @Test( expected = IllegalStateException.class )
    public void testBindActionListener_Listener_Bound()
    {
        final BasicAction action = createBasicAction();
        final ActionListener listener = mocksControl_.createMock( ActionListener.class );
        mediator_.bindActionListener( action, listener );

        mediator_.bindActionListener( action, listener );
    }

    /**
     * Ensures the {@link ActionMediator#bindActionListener} method binds a
     * listener when no listener is already bound to the action.
     */
    @Test
    public void testBindActionListener_Listener_Unbound()
    {
        final BasicAction action = createBasicAction();
        final ActionListener listener = mocksControl_.createMock( ActionListener.class );
        listener.actionPerformed( EasyMock.<ActionEvent>notNull() );
        mocksControl_.replay();

        mediator_.bindActionListener( action, listener );
        action.actionPerformed( createActionEvent() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link ActionMediator#bindShouldEnablePredicate} method
     * throws an exception when a predicate is already bound to the action.
     * 
     */
    @Test( expected = IllegalStateException.class )
    public void testBindShouldEnablePredicate_Predicate_Bound()
    {
        final BasicAction action = createBasicAction();
        final IPredicate<Action> predicate = mocksControl_.createMock( IPredicate.class );
        mediator_.bindShouldEnablePredicate( action, predicate );

        mediator_.bindShouldEnablePredicate( action, predicate );
    }

    /**
     * Ensures the {@link ActionMediator#bindShouldEnablePredicate} method binds
     * a predicate when no predicate is already bound to the action.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testBindShouldEnablePredicate_Predicate_Unbound()
    {
        final BasicAction action = createBasicAction();
        final IPredicate<Action> predicate = mocksControl_.createMock( IPredicate.class );
        EasyMock.expect( predicate.evaluate( EasyMock.<Action>notNull() ) ).andReturn( false ).times( 2 );
        mocksControl_.replay();

        mediator_.bindShouldEnablePredicate( action, predicate );
        action.update();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link ActionMediator#bindShouldEnablePredicate} method
     * updates the action after binding the predicate.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testBindShouldEnablePredicate_UpdatesAction()
    {
        final BasicAction action = createBasicAction();
        final IPredicate<Action> predicate = mocksControl_.createMock( IPredicate.class );
        EasyMock.expect( predicate.evaluate( EasyMock.<Action>notNull() ) ).andReturn( false );
        mocksControl_.replay();

        mediator_.bindShouldEnablePredicate( action, predicate );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link ActionMediator#bindShouldSelectPredicate} method
     * throws an exception when a predicate is already bound to the action.
     * 
     */
    @Test( expected = IllegalStateException.class )
    public void testBindShouldSelectPredicate_Predicate_Bound()
    {
        final BasicAction action = createBasicAction();
        final IPredicate<Action> predicate = mocksControl_.createMock( IPredicate.class );
        mediator_.bindShouldSelectPredicate( action, predicate );

        mediator_.bindShouldSelectPredicate( action, predicate );
    }

    /**
     * Ensures the {@link ActionMediator#bindShouldSelectPredicate} method binds
     * a predicate when no predicate is already bound to the action.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testBindShouldSelectPredicate_Predicate_Unbound()
    {
        final BasicAction action = createBasicAction();
        final IPredicate<Action> predicate = mocksControl_.createMock( IPredicate.class );
        EasyMock.expect( predicate.evaluate( EasyMock.<Action>notNull() ) ).andReturn( false ).times( 2 );
        mocksControl_.replay();

        mediator_.bindShouldSelectPredicate( action, predicate );
        action.update();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link ActionMediator#bindShouldSelectPredicate} method
     * updates the action after binding the predicate.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testBindShouldSelectPredicate_UpdatesAction()
    {
        final BasicAction action = createBasicAction();
        final IPredicate<Action> predicate = mocksControl_.createMock( IPredicate.class );
        EasyMock.expect( predicate.evaluate( EasyMock.<Action>notNull() ) ).andReturn( false );
        mocksControl_.replay();

        mediator_.bindShouldSelectPredicate( action, predicate );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link ActionMediator#unbind} method throws an exception when
     * passed an action that has no bound attachments.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUpdate_Action_NoBoundAttachments()
    {
        mediator_.unbind( createBasicAction() );
    }

    /**
     * Ensures the {@link ActionMediator#unbind} method unbinds all action
     * listeners from the action.
     */
    @Test
    public void testUnbind_UnbindsActionListeners()
    {
        final BasicAction action = createBasicAction();
        final ActionListener listener = mocksControl_.createMock( ActionListener.class );
        mocksControl_.replay();
        mediator_.bindActionListener( action, listener );

        mediator_.unbind( action );
        action.actionPerformed( createActionEvent() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link ActionMediator#unbind} method unbinds all should
     * enable predicates from the action.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testUnbind_UnbindsShouldEnablePredicates()
    {
        final BasicAction action = createBasicAction();
        final IPredicate<Action> predicate = mocksControl_.createMock( IPredicate.class );
        EasyMock.expect( predicate.evaluate( EasyMock.<Action>notNull() ) ).andReturn( false );
        mocksControl_.replay();
        mediator_.bindShouldEnablePredicate( action, predicate );

        mediator_.unbind( action );
        action.update();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link ActionMediator#unbind} method unbinds all should
     * select predicates from the action.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testUnbind_UnbindsShouldSelectPredicates()
    {
        final BasicAction action = createBasicAction();
        final IPredicate<Action> predicate = mocksControl_.createMock( IPredicate.class );
        EasyMock.expect( predicate.evaluate( EasyMock.<Action>notNull() ) ).andReturn( false );
        mocksControl_.replay();
        mediator_.bindShouldSelectPredicate( action, predicate );

        mediator_.unbind( action );
        action.update();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link ActionMediator#unbind} method updates the action after
     * unbinding a bound predicate.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testUnbind_UpdatesAction()
    {
        final BasicAction action = createBasicAction();
        final IPredicate<Action> managedPredicate = mocksControl_.createMock( IPredicate.class );
        EasyMock.expect( managedPredicate.evaluate( EasyMock.<Action>notNull() ) ).andReturn( false );
        final IPredicate<Action> unmanagedPredicate = mocksControl_.createMock( IPredicate.class );
        EasyMock.expect( unmanagedPredicate.evaluate( EasyMock.<Action>notNull() ) ).andReturn( false );
        mocksControl_.replay();
        mediator_.bindShouldEnablePredicate( action, managedPredicate );
        action.addShouldEnablePredicate( unmanagedPredicate );

        mediator_.unbind( action );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link ActionMediator#unbindAll} method unbinds all action
     * listeners from all actions.
     */
    @Test
    public void testUnbindAll_UnbindsActionListeners()
    {
        final BasicAction action1 = createBasicAction();
        final ActionListener listener1 = mocksControl_.createMock( ActionListener.class );
        final BasicAction action2 = createBasicAction();
        final ActionListener listener2 = mocksControl_.createMock( ActionListener.class );
        mocksControl_.replay();
        mediator_.bindActionListener( action1, listener1 );
        mediator_.bindActionListener( action2, listener2 );

        mediator_.unbindAll();
        action1.actionPerformed( createActionEvent() );
        action2.actionPerformed( createActionEvent() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link ActionMediator#unbindAll} method unbinds all should
     * enable predicates from all actions.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testUnbindAll_UnbindsShouldEnablePredicates()
    {
        final BasicAction action1 = createBasicAction();
        final IPredicate<Action> predicate1 = mocksControl_.createMock( IPredicate.class );
        EasyMock.expect( predicate1.evaluate( EasyMock.<Action>notNull() ) ).andReturn( false );
        final BasicAction action2 = createBasicAction();
        final IPredicate<Action> predicate2 = mocksControl_.createMock( IPredicate.class );
        EasyMock.expect( predicate2.evaluate( EasyMock.<Action>notNull() ) ).andReturn( false );
        mocksControl_.replay();
        mediator_.bindShouldEnablePredicate( action1, predicate1 );
        mediator_.bindShouldEnablePredicate( action2, predicate2 );

        mediator_.unbindAll();
        action1.update();
        action2.update();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link ActionMediator#unbindAll} method unbinds all should
     * select predicates from all actions.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testUnbindAll_UnbindsShouldSelectPredicates()
    {
        final BasicAction action1 = createBasicAction();
        final IPredicate<Action> predicate1 = mocksControl_.createMock( IPredicate.class );
        EasyMock.expect( predicate1.evaluate( EasyMock.<Action>notNull() ) ).andReturn( false );
        final BasicAction action2 = createBasicAction();
        final IPredicate<Action> predicate2 = mocksControl_.createMock( IPredicate.class );
        EasyMock.expect( predicate2.evaluate( EasyMock.<Action>notNull() ) ).andReturn( false );
        mocksControl_.replay();
        mediator_.bindShouldSelectPredicate( action1, predicate1 );
        mediator_.bindShouldSelectPredicate( action2, predicate2 );

        mediator_.unbindAll();
        action1.update();
        action2.update();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link ActionMediator#unbindAll} method updates all actions
     * after unbinding a bound predicate.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testUnbindAll_UpdatesAction()
    {
        final BasicAction action1 = createBasicAction();
        final IPredicate<Action> managedPredicate1 = mocksControl_.createMock( IPredicate.class );
        EasyMock.expect( managedPredicate1.evaluate( EasyMock.<Action>notNull() ) ).andReturn( false );
        final IPredicate<Action> unmanagedPredicate1 = mocksControl_.createMock( IPredicate.class );
        EasyMock.expect( unmanagedPredicate1.evaluate( EasyMock.<Action>notNull() ) ).andReturn( false );
        final BasicAction action2 = createBasicAction();
        final IPredicate<Action> managedPredicate2 = mocksControl_.createMock( IPredicate.class );
        EasyMock.expect( managedPredicate2.evaluate( EasyMock.<Action>notNull() ) ).andReturn( false );
        final IPredicate<Action> unmanagedPredicate2 = mocksControl_.createMock( IPredicate.class );
        EasyMock.expect( unmanagedPredicate2.evaluate( EasyMock.<Action>notNull() ) ).andReturn( false );
        mocksControl_.replay();
        mediator_.bindShouldEnablePredicate( action1, managedPredicate1 );
        action1.addShouldEnablePredicate( unmanagedPredicate1 );
        mediator_.bindShouldEnablePredicate( action2, managedPredicate2 );
        action2.addShouldEnablePredicate( unmanagedPredicate2 );

        mediator_.unbindAll();

        mocksControl_.verify();
    }
}

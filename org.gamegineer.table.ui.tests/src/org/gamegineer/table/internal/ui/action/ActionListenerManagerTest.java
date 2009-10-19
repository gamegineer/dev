/*
 * ActionListenerManagerTest.java
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
 * {@link org.gamegineer.table.internal.ui.action.ActionListenerManager} class.
 */
public final class ActionListenerManagerTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The action listener manager under test in the fixture. */
    private ActionListenerManager manager_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ActionListenerManagerTest}
     * class.
     */
    public ActionListenerManagerTest()
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
        manager_ = new ActionListenerManager();
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
        manager_ = null;
    }

    /**
     * Ensures the {@code bind} method throws an exception when passed a {@code
     * null} action.
     */
    @Test( expected = NullPointerException.class )
    public void testBind_Action_Null()
    {
        manager_.bind( null, createDummy( ActionListener.class ) );
    }

    /**
     * Ensures the {@code bind} method throws an exception when passed a
     * listener that is already bound to the action.
     * 
     */
    @Test( expected = IllegalArgumentException.class )
    public void testBind_Listener_Bound()
    {
        final BasicAction action = new BasicAction();
        final MockActionListener listener = new MockActionListener();
        manager_.bind( action, listener );

        manager_.bind( action, listener );
    }

    /**
     * Ensures the {@code bind} method throws an exception when passed a {@code
     * null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testBind_Listener_Null()
    {
        manager_.bind( new BasicAction(), null );
    }

    /**
     * Ensures the {@code bind} method binds a listener this is not bound to the
     * action.
     */
    @Test
    public void testBind_Listener_Unbound()
    {
        final BasicAction action = new BasicAction();
        final MockActionListener listener = new MockActionListener();

        manager_.bind( action, listener );

        action.actionPerformed( createActionEvent() );
        assertEquals( 1, listener.getActionPerformedEventCount() );
    }

    /**
     * Ensures the {@code unbind} method throws an exception when passed a
     * {@code null} action.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbind_Action_Null()
    {
        manager_.unbind( null, createDummy( ActionListener.class ) );
    }

    /**
     * Ensures the {@code unbind} method unbinds a listener that is bound to the
     * action.
     * 
     */
    @Test
    public void testUnbind_Listener_Bound()
    {
        final BasicAction action = new BasicAction();
        final MockActionListener listener = new MockActionListener();
        manager_.bind( action, listener );

        manager_.unbind( action, listener );

        action.actionPerformed( createActionEvent() );
        assertEquals( 0, listener.getActionPerformedEventCount() );
    }

    /**
     * Ensures the {@code unbind} method throws an exception when passed a
     * {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbind_Listener_Null()
    {
        manager_.unbind( new BasicAction(), null );
    }

    /**
     * Ensures the {@code unbind} method throws an exception when passed a
     * listener that is not bound to the action.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnbind_Listener_Unbound()
    {
        final BasicAction action = new BasicAction();
        final ActionListener listener = new MockActionListener();

        manager_.unbind( action, listener );
    }

    /**
     * Ensures the {@code unbindAll()} method unbinds all listeners from all
     * actions.
     */
    @Test
    public void testUnbindAll()
    {
        final BasicAction action1 = new BasicAction();
        final MockActionListener listener1 = new MockActionListener();
        manager_.bind( action1, listener1 );
        final BasicAction action2 = new BasicAction();
        final MockActionListener listener2 = new MockActionListener();
        manager_.bind( action2, listener2 );

        manager_.unbindAll();

        action1.actionPerformed( createActionEvent() );
        assertEquals( 0, listener1.getActionPerformedEventCount() );
        action2.actionPerformed( createActionEvent() );
        assertEquals( 0, listener2.getActionPerformedEventCount() );
    }

    /**
     * Ensures the {@code unbindAll(BasicAction)} method unbinds all listeners
     * from the action.
     */
    @Test
    public void testUnbindAllForAction()
    {
        final BasicAction action = new BasicAction();
        final MockActionListener listener1 = new MockActionListener();
        final MockActionListener listener2 = new MockActionListener();
        manager_.bind( action, listener1 );
        manager_.bind( action, listener2 );

        manager_.unbindAll( action );

        action.actionPerformed( createActionEvent() );
        assertEquals( 0, listener1.getActionPerformedEventCount() );
        assertEquals( 0, listener2.getActionPerformedEventCount() );
    }

    /**
     * Ensures the {@code unbindAll(BasicAction)} method throws an exception
     * when passed a {@code null} action.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbindAllForAction_Action_Null()
    {
        manager_.unbindAll( null );
    }
}

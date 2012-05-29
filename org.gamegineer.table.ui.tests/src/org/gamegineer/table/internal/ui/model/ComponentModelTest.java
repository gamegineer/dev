/*
 * ComponentModelTest.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Dec 25, 2009 at 11:02:59 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.junit.Assert.assertNotNull;
import java.lang.reflect.Method;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.core.Cards;
import org.gamegineer.table.core.TableEnvironmentFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.ComponentModel} class.
 */
public final class ComponentModelTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;

    /** The component model under test in the fixture. */
    private ComponentModel model_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentModelTest} class.
     */
    public ComponentModelTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Fires a component changed event for the component model under test in the
     * fixture.
     */
    private void fireComponentChangedEvent()
    {
        try
        {
            final Method method = ComponentModel.class.getDeclaredMethod( "fireComponentChanged" ); //$NON-NLS-1$
            method.setAccessible( true );
            method.invoke( model_ );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
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
        model_ = new ComponentModel( Cards.createUniqueCard( TableEnvironmentFactory.createTableEnvironment() ) );
    }

    /**
     * Ensures the {@code addComponentModelListener} method adds a listener that
     * is absent from the component model listener collection.
     */
    @Test
    public void testAddComponentModelListener_Listener_Absent()
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        mocksControl_.replay();

        fireComponentChangedEvent();
        model_.addComponentModelListener( listener );
        fireComponentChangedEvent();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code addComponentModelListener} method throws an exception
     * when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddComponentModelListener_Listener_Null()
    {
        model_.addComponentModelListener( null );
    }

    /**
     * Ensures the {@code addComponentModelListener} method throws an exception
     * when passed a listener that is present in the component model listener
     * collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddComponentModelListener_Listener_Present()
    {
        final IComponentModelListener listener = EasyMock.createMock( IComponentModelListener.class );
        model_.addComponentModelListener( listener );

        model_.addComponentModelListener( listener );
    }

    /**
     * Ensures a change to the underlying component state fires a component
     * changed event.
     */
    @Test
    public void testComponent_StateChanged_FiresComponentChangedEvent()
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        mocksControl_.replay();
        model_.addComponentModelListener( listener );

        model_.getComponent().setOrientation( model_.getComponent().getOrientation().inverse() );

        mocksControl_.verify();
    }

    /**
     * Ensures the component changed event catches any exception thrown by the
     * {@code componentChanged} method of a component model listener.
     */
    @Test
    public void testComponentChanged_CatchesListenerException()
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        mocksControl_.replay();
        model_.addComponentModelListener( listener );

        fireComponentChangedEvent();

        mocksControl_.verify();
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * component.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Component_Null()
    {
        new ComponentModel( null );
    }

    /**
     * Ensures the {@code getComponent} method does not return {@code null}.
     */
    @Test
    public void testGetComponent_ReturnValue_NonNull()
    {
        assertNotNull( model_.getComponent() );
    }

    /**
     * Ensures the {@code removeComponentModelListener} method throws an
     * exception when passed a listener that is absent from the component model
     * listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveComponentModelListener_Listener_Absent()
    {
        model_.removeComponentModelListener( EasyMock.createMock( IComponentModelListener.class ) );
    }

    /**
     * Ensures the {@code removeComponentModelListener} method throws an
     * exception when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveComponentModelListener_Listener_Null()
    {
        model_.removeComponentModelListener( null );
    }

    /**
     * Ensures the {@code removeComponentModelListener} removes a listener that
     * is present in the component model listener collection.
     */
    @Test
    public void testRemoveComponentModelListener_Listener_Present()
    {
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        mocksControl_.replay();
        model_.addComponentModelListener( listener );

        fireComponentChangedEvent();
        model_.removeComponentModelListener( listener );
        fireComponentChangedEvent();

        mocksControl_.verify();
    }
}

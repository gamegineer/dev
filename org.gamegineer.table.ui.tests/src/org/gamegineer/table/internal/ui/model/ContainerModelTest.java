/*
 * ContainerModelTest.java
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
 * Created on Jan 26, 2010 at 10:57:19 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.junit.Assert.assertNotNull;
import java.awt.Point;
import java.lang.reflect.Method;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.core.CardPiles;
import org.gamegineer.table.core.Cards;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.TableEnvironmentFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.ContainerModel} class.
 */
public final class ContainerModelTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;

    /** The container model under test in the fixture. */
    private ContainerModel model_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerModelTest} class.
     */
    public ContainerModelTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Fires a container changed event for the container model under test in the
     * fixture.
     */
    private void fireContainerChangedEvent()
    {
        fireContainerModelEvent( "fireContainerChanged" ); //$NON-NLS-1$
    }

    /**
     * Fires the specified container model event for the container model under
     * test in the fixture.
     * 
     * @param methodName
     *        The name of the method that fires the container model event; must
     *        not be {@code null}.
     */
    private void fireContainerModelEvent(
        /* @NonNull */
        final String methodName )
    {
        assert methodName != null;

        try
        {
            final Method method = ContainerModel.class.getDeclaredMethod( methodName );
            method.setAccessible( true );
            method.invoke( model_ );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }

    /**
     * Fires a container model focus changed event for the container model under
     * test in the fixture.
     */
    private void fireContainerModelFocusChangedEvent()
    {
        fireContainerModelEvent( "fireContainerModelFocusChanged" ); //$NON-NLS-1$
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
        model_ = new ContainerModel( CardPiles.createUniqueCardPile( TableEnvironmentFactory.createTableEnvironment() ) );
    }

    /**
     * Ensures the {@code addContainerModelListener} method adds a listener that
     * is absent from the container model listener collection.
     */
    @Test
    public void testAddContainerModelListener_Listener_Absent()
    {
        final IContainerModelListener listener = mocksControl_.createMock( IContainerModelListener.class );
        listener.containerChanged( EasyMock.notNull( ContainerModelEvent.class ) );
        mocksControl_.replay();

        fireContainerChangedEvent();
        model_.addContainerModelListener( listener );
        fireContainerChangedEvent();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code addContainerModelListener} method throws an exception
     * when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddContainerModelListener_Listener_Null()
    {
        model_.addContainerModelListener( null );
    }

    /**
     * Ensures the {@code addContainerModelListener} method throws an exception
     * when passed a listener that is present in the container model listener
     * collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddContainerModelListener_Listener_Present()
    {
        final IContainerModelListener listener = EasyMock.createMock( IContainerModelListener.class );
        model_.addContainerModelListener( listener );

        model_.addContainerModelListener( listener );
    }

    /**
     * Ensures a change to a component associated with a component model owned
     * by the container model fires a container changed event.
     */
    @Test
    public void testComponentModel_ComponentChanged_FiresContainerChangedEvent()
    {
        final IComponent component = Cards.createUniqueCard( model_.getContainer().getTableEnvironment() );
        model_.getContainer().addComponent( component );
        final IContainerModelListener listener = mocksControl_.createMock( IContainerModelListener.class );
        listener.containerChanged( EasyMock.notNull( ContainerModelEvent.class ) );
        mocksControl_.replay();
        model_.addContainerModelListener( listener );

        component.setOrientation( component.getOrientation().inverse() );

        mocksControl_.verify();
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * container.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Container_Null()
    {
        new ContainerModel( null );
    }

    /**
     * Ensures a change to the underlying container state fires a container
     * changed event.
     */
    @Test
    public void testContainer_StateChanged_FiresContainerChangedEvent()
    {
        final IContainerModelListener listener = mocksControl_.createMock( IContainerModelListener.class );
        listener.containerChanged( EasyMock.notNull( ContainerModelEvent.class ) );
        mocksControl_.replay();
        model_.addContainerModelListener( listener );

        model_.getContainer().setLocation( new Point( 101, 102 ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the container changed event catches any exception thrown by the
     * {@code containerChanged} method of a container model listener.
     */
    @Test
    public void testContainerChanged_CatchesListenerException()
    {
        final IContainerModelListener listener = mocksControl_.createMock( IContainerModelListener.class );
        listener.containerChanged( EasyMock.notNull( ContainerModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        mocksControl_.replay();
        model_.addContainerModelListener( listener );

        fireContainerChangedEvent();

        mocksControl_.verify();
    }

    /**
     * Ensures the container model focus changed event catches any exception
     * thrown by the {@code containerModelFocusChanged} method of a container
     * model listener.
     */
    @Test
    public void testContainerModelFocusChanged_CatchesListenerException()
    {
        final IContainerModelListener listener = mocksControl_.createMock( IContainerModelListener.class );
        listener.containerModelFocusChanged( EasyMock.notNull( ContainerModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        mocksControl_.replay();
        model_.addContainerModelListener( listener );

        fireContainerModelFocusChangedEvent();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code getComponentModel} throws an exception when passed a
     * component that is absent from the container.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetComponentModel_Component_Absent()
    {
        model_.getComponentModel( EasyMock.createMock( IComponent.class ) );
    }

    /**
     * Ensures the {@code getComponentModel} throws an exception when passed a
     * {@code null} component.
     */
    @Test( expected = NullPointerException.class )
    public void testGetComponentModel_Component_Null()
    {
        model_.getComponentModel( null );
    }

    /**
     * Ensures the {@code getContainer} method does not return {@code null}.
     */
    @Test
    public void testGetContainer_ReturnValue_NonNull()
    {
        assertNotNull( model_.getContainer() );
    }

    /**
     * Ensures the {@code removeContainerModelListener} method throws an
     * exception when passed a listener that is absent from the container model
     * listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveContainerModelListener_Listener_Absent()
    {
        model_.removeContainerModelListener( EasyMock.createMock( IContainerModelListener.class ) );
    }

    /**
     * Ensures the {@code removeContainerModelListener} method throws an
     * exception when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveContainerModelListener_Listener_Null()
    {
        model_.removeContainerModelListener( null );
    }

    /**
     * Ensures the {@code removeContainerModelListener} removes a listener that
     * is present in the container model listener collection.
     */
    @Test
    public void testRemoveContainerModelListener_Listener_Present()
    {
        final IContainerModelListener listener = mocksControl_.createMock( IContainerModelListener.class );
        listener.containerChanged( EasyMock.notNull( ContainerModelEvent.class ) );
        mocksControl_.replay();
        model_.addContainerModelListener( listener );

        fireContainerChangedEvent();
        model_.removeContainerModelListener( listener );
        fireContainerChangedEvent();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code setFocused} method fires a container model focus
     * changed event after the container model gained the focus.
     */
    @Test
    public void testSetFocused_GainedFocus_FiresContainerModelFocusChangedEvent()
    {
        final IContainerModelListener listener = mocksControl_.createMock( IContainerModelListener.class );
        listener.containerModelFocusChanged( EasyMock.notNull( ContainerModelEvent.class ) );
        mocksControl_.replay();
        model_.addContainerModelListener( listener );

        model_.setFocused( true );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code setFocused} method fires a container model focus
     * changed event after the container model lost the focus.
     */
    @Test
    public void testSetFocused_LostFocus_FiresContainerModelFocusChangedEvent()
    {
        final IContainerModelListener listener = mocksControl_.createMock( IContainerModelListener.class );
        listener.containerModelFocusChanged( EasyMock.notNull( ContainerModelEvent.class ) );
        mocksControl_.replay();
        model_.addContainerModelListener( listener );

        model_.setFocused( false );
    }
}

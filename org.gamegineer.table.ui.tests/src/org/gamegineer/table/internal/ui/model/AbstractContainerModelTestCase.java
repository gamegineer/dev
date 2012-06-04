/*
 * AbstractContainerModelTestCase.java
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
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.core.Cards;
import org.gamegineer.table.core.IComponent;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.ContainerModel} class.
 */
public abstract class AbstractContainerModelTestCase
    extends AbstractComponentModelTestCase<ContainerModel>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractContainerModelTestCase}
     * class.
     */
    protected AbstractContainerModelTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Fires a container model focus changed event for the container model under
     * test in the fixture.
     */
    private void fireContainerModelFocusChangedEvent()
    {
        fireContainerModelFocusChangedEvent( getContainerModel() );
    }

    /**
     * Fires a container model focus changed event for the specified container
     * model.
     * 
     * @param containerModel
     *        The container model; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     */
    protected abstract void fireContainerModelFocusChangedEvent(
        /* @NonNull */
        ContainerModel containerModel );

    /**
     * Gets the container model under test in the fixture.
     * 
     * @return The container model under test in the fixture; never {@code null}
     *         .
     */
    /* @NonNull */
    protected final ContainerModel getContainerModel()
    {
        return getComponentModel();
    }

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    @Override
    public void setUp()
        throws Exception
    {
        mocksControl_ = EasyMock.createControl();

        super.setUp();
    }

    /**
     * Ensures the {@code addContainerModelListener} method adds a listener that
     * is absent from the container model listener collection.
     */
    @Test
    public void testAddContainerModelListener_Listener_Absent()
    {
        final IContainerModelListener listener = mocksControl_.createMock( IContainerModelListener.class );
        listener.containerModelFocusChanged( EasyMock.notNull( ContainerModelEvent.class ) );
        mocksControl_.replay();

        fireContainerModelFocusChangedEvent();
        getContainerModel().addContainerModelListener( listener );
        fireContainerModelFocusChangedEvent();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code addContainerModelListener} method throws an exception
     * when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddContainerModelListener_Listener_Null()
    {
        getContainerModel().addContainerModelListener( null );
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
        getContainerModel().addContainerModelListener( listener );

        getContainerModel().addContainerModelListener( listener );
    }

    /**
     * Ensures a change to a component associated with a component model owned
     * by the container model fires a component changed event.
     */
    @Test
    public void testComponentModel_ComponentChanged_FiresComponentChangedEvent()
    {
        final IComponent component = Cards.createUniqueCard( getContainerModel().getContainer().getTableEnvironment() );
        getContainerModel().getContainer().addComponent( component );
        final IComponentModelListener listener = mocksControl_.createMock( IComponentModelListener.class );
        listener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        mocksControl_.replay();
        getContainerModel().addComponentModelListener( listener );

        component.setOrientation( component.getOrientation().inverse() );

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
        getContainerModel().addContainerModelListener( listener );

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
        getContainerModel().getComponentModel( EasyMock.createMock( IComponent.class ) );
    }

    /**
     * Ensures the {@code getComponentModel} throws an exception when passed a
     * {@code null} component.
     */
    @Test( expected = NullPointerException.class )
    public void testGetComponentModel_Component_Null()
    {
        getContainerModel().getComponentModel( null );
    }

    /**
     * Ensures the {@code getContainer} method does not return {@code null}.
     */
    @Test
    public void testGetContainer_ReturnValue_NonNull()
    {
        assertNotNull( getContainerModel().getContainer() );
    }

    /**
     * Ensures the {@code removeContainerModelListener} method throws an
     * exception when passed a listener that is absent from the container model
     * listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveContainerModelListener_Listener_Absent()
    {
        getContainerModel().removeContainerModelListener( EasyMock.createMock( IContainerModelListener.class ) );
    }

    /**
     * Ensures the {@code removeContainerModelListener} method throws an
     * exception when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveContainerModelListener_Listener_Null()
    {
        getContainerModel().removeContainerModelListener( null );
    }

    /**
     * Ensures the {@code removeContainerModelListener} removes a listener that
     * is present in the container model listener collection.
     */
    @Test
    public void testRemoveContainerModelListener_Listener_Present()
    {
        final IContainerModelListener listener = mocksControl_.createMock( IContainerModelListener.class );
        listener.containerModelFocusChanged( EasyMock.notNull( ContainerModelEvent.class ) );
        mocksControl_.replay();
        getContainerModel().addContainerModelListener( listener );

        fireContainerModelFocusChangedEvent();
        getContainerModel().removeContainerModelListener( listener );
        fireContainerModelFocusChangedEvent();

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
        getContainerModel().addContainerModelListener( listener );

        getContainerModel().setFocused( true );

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
        getContainerModel().addContainerModelListener( listener );

        getContainerModel().setFocused( false );
    }
}

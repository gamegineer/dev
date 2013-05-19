/*
 * AbstractContainerModelTestCase.java
 * Copyright 2008-2013 Gamegineer.org
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

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.TestContainerLayouts;
import org.gamegineer.table.ui.TestComponents;
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

    /** The nice mocks control for use in the fixture. */
    private IMocksControl niceMocksControl_;


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
     * Adds the specified container model listener to the fixture container
     * model.
     * 
     * <p>
     * This method ensures all pending table environment events have fired
     * before adding the listener.
     * </p>
     * 
     * @param listener
     *        The container model listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered container model
     *         listener.
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    protected final void addContainerModelListener(
        /* @NonNull */
        final IContainerModelListener listener )
        throws InterruptedException
    {
        awaitPendingTableEnvironmentEvents();
        getContainerModel().addContainerModelListener( listener );
    }

    /**
     * Fires a component model added event for the fixture container model.
     */
    private void fireComponentModelAddedEvent()
    {
        fireComponentModelAddedEvent( getContainerModel() );
    }

    /**
     * Fires a component model added event for the specified container model.
     * 
     * @param containerModel
     *        The container model; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code containerModel} is {@code null}.
     */
    protected abstract void fireComponentModelAddedEvent(
        /* @NonNull */
        ContainerModel containerModel );

    /**
     * Fires a component model removed event for the fixture container model.
     */
    private void fireComponentModelRemovedEvent()
    {
        fireComponentModelRemovedEvent( getContainerModel() );
    }

    /**
     * Fires a component model removed event for the specified container model.
     * 
     * @param containerModel
     *        The container model; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code containerModel} is {@code null}.
     */
    protected abstract void fireComponentModelRemovedEvent(
        /* @NonNull */
        ContainerModel containerModel );

    /**
     * Fires a container layout changed event for the fixture container model.
     */
    private void fireContainerLayoutChangedEvent()
    {
        fireContainerLayoutChangedEvent( getContainerModel() );
    }

    /**
     * Fires a container layout changed event for the specified container model.
     * 
     * @param containerModel
     *        The container model; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code containerModel} is {@code null}.
     */
    protected abstract void fireContainerLayoutChangedEvent(
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
     * Switches the fixture nice mocks control from record mode to replay mode.
     */
    protected final void replayNiceMocks()
    {
        niceMocksControl_.replay();
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
        niceMocksControl_ = EasyMock.createNiceControl();

        super.setUp();
    }

    /**
     * Ensures the {@link ContainerModel#addContainerModelListener} method adds
     * a listener that is absent from the container model listener collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testAddContainerModelListener_Listener_Absent()
        throws Exception
    {
        final IContainerModelListener listener = niceMocksControl_.createMock( IContainerModelListener.class );
        listener.containerLayoutChanged( EasyMock.notNull( ContainerModelEvent.class ) );
        replayNiceMocks();

        fireContainerLayoutChangedEvent();
        addContainerModelListener( listener );
        fireContainerLayoutChangedEvent();

        verifyNiceMocks();
    }

    /**
     * Ensures the {@link ContainerModel#addContainerModelListener} method
     * throws an exception when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddContainerModelListener_Listener_Null()
    {
        getContainerModel().addContainerModelListener( null );
    }

    /**
     * Ensures the {@link ContainerModel#addContainerModelListener} method
     * throws an exception when passed a listener that is present in the
     * container model listener collection.
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
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testComponentModel_ComponentChanged_FiresComponentChangedEvent()
        throws Exception
    {
        final IComponentModelListener listener = niceMocksControl_.createMock( IComponentModelListener.class );
        listener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        replayNiceMocks();

        final IComponent component = TestComponents.createUniqueComponent( getContainerModel().getComponent().getTableEnvironment() );
        getContainerModel().getComponent().addComponent( component );
        addComponentModelListener( listener );

        component.setOrientation( component.getOrientation().inverse() );

        verifyNiceMocks();
    }

    /**
     * Ensures the component model added event catches any exception thrown by
     * the {@link IContainerModelListener#componentModelAdded} method of a
     * container model listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testComponentModelAdded_CatchesListenerException()
        throws Exception
    {
        final IContainerModelListener listener1 = niceMocksControl_.createMock( IContainerModelListener.class );
        listener1.componentModelAdded( EasyMock.notNull( ContainerModelContentChangedEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IContainerModelListener listener2 = niceMocksControl_.createMock( IContainerModelListener.class );
        listener2.componentModelAdded( EasyMock.notNull( ContainerModelContentChangedEvent.class ) );
        replayNiceMocks();

        addContainerModelListener( listener1 );
        addContainerModelListener( listener2 );

        fireComponentModelAddedEvent();

        verifyNiceMocks();
    }

    /**
     * Ensures the component model removed event catches any exception thrown by
     * the {@link IContainerModelListener#componentModelRemoved} method of a
     * container model listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testComponentModelRemoved_CatchesListenerException()
        throws Exception
    {
        final IContainerModelListener listener = niceMocksControl_.createMock( IContainerModelListener.class );
        listener.componentModelRemoved( EasyMock.notNull( ContainerModelContentChangedEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        replayNiceMocks();

        addContainerModelListener( listener );

        fireComponentModelRemovedEvent();

        verifyNiceMocks();
    }

    /**
     * Ensures the addition of a component to the container associated with the
     * container model fires a component model added event and a component
     * changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testContainer_ComponentAdded_FiresComponentModelAddedEventAndComponentChangedEvent()
        throws Exception
    {
        final IComponentModelListener componentModelListener = niceMocksControl_.createMock( IComponentModelListener.class );
        componentModelListener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        final IContainerModelListener containerModelListener = niceMocksControl_.createMock( IContainerModelListener.class );
        containerModelListener.componentModelAdded( EasyMock.notNull( ContainerModelContentChangedEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        replayNiceMocks();

        addComponentModelListener( componentModelListener );
        addContainerModelListener( containerModelListener );

        getContainerModel().getComponent().addComponent( TestComponents.createUniqueComponent( getContainerModel().getComponent().getTableEnvironment() ) );

        verifyNiceMocks();
    }

    /**
     * Ensures the removal of a component from the container associated with the
     * container model fires a component model removed event and a component
     * changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testContainer_ComponentRemoved_FiresComponentModelRemovedEventAndComponentChangedEvent()
        throws Exception
    {
        final IComponentModelListener componentModelListener = niceMocksControl_.createMock( IComponentModelListener.class );
        componentModelListener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        final IContainerModelListener containerModelListener = niceMocksControl_.createMock( IContainerModelListener.class );
        containerModelListener.componentModelRemoved( EasyMock.notNull( ContainerModelContentChangedEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        replayNiceMocks();

        final IComponent component = TestComponents.createUniqueComponent( getContainerModel().getComponent().getTableEnvironment() );
        getContainerModel().getComponent().addComponent( component );
        addComponentModelListener( componentModelListener );
        addContainerModelListener( containerModelListener );

        getContainerModel().getComponent().removeComponent( component );

        verifyNiceMocks();
    }

    /**
     * Ensures a change to the underlying container layout fires a container
     * layout changed event and a component changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testContainer_LayoutChanged_FiresContainerLayoutChangedEventAndComponentChangedEvent()
        throws Exception
    {
        final IComponentModelListener componentModelListener = niceMocksControl_.createMock( IComponentModelListener.class );
        componentModelListener.componentChanged( EasyMock.notNull( ComponentModelEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        final IContainerModelListener containerModelListener = niceMocksControl_.createMock( IContainerModelListener.class );
        containerModelListener.containerLayoutChanged( EasyMock.notNull( ContainerModelEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        replayNiceMocks();

        addComponentModelListener( componentModelListener );
        addContainerModelListener( containerModelListener );

        getContainerModel().getComponent().setLayout( TestContainerLayouts.createHorizontalContainerLayout() );

        verifyNiceMocks();
    }

    /**
     * Ensures the container layout changed event catches any exception thrown
     * by the {@link IContainerModelListener#containerLayoutChanged} method of a
     * container model listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testContainerLayoutChanged_CatchesListenerException()
        throws Exception
    {
        final IContainerModelListener listener1 = niceMocksControl_.createMock( IContainerModelListener.class );
        listener1.containerLayoutChanged( EasyMock.notNull( ContainerModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IContainerModelListener listener2 = niceMocksControl_.createMock( IContainerModelListener.class );
        listener2.containerLayoutChanged( EasyMock.notNull( ContainerModelEvent.class ) );
        replayNiceMocks();

        addContainerModelListener( listener1 );
        addContainerModelListener( listener2 );

        fireContainerLayoutChangedEvent();

        verifyNiceMocks();
    }

    /**
     * Ensures the {@link ContainerModel#removeContainerModelListener} method
     * throws an exception when passed a listener that is absent from the
     * container model listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveContainerModelListener_Listener_Absent()
    {
        getContainerModel().removeContainerModelListener( EasyMock.createMock( IContainerModelListener.class ) );
    }

    /**
     * Ensures the {@link ContainerModel#removeContainerModelListener} method
     * throws an exception when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveContainerModelListener_Listener_Null()
    {
        getContainerModel().removeContainerModelListener( null );
    }

    /**
     * Ensures the {@link ContainerModel#removeContainerModelListener} removes a
     * listener that is present in the container model listener collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemoveContainerModelListener_Listener_Present()
        throws Exception
    {
        final IContainerModelListener listener = niceMocksControl_.createMock( IContainerModelListener.class );
        listener.containerLayoutChanged( EasyMock.notNull( ContainerModelEvent.class ) );
        replayNiceMocks();

        addContainerModelListener( listener );

        fireContainerLayoutChangedEvent();
        getContainerModel().removeContainerModelListener( listener );
        fireContainerLayoutChangedEvent();

        verifyNiceMocks();
    }

    /**
     * Verifies that all expectations were met in the fixture nice mocks
     * control.
     * 
     * <p>
     * This method waits for all asynchronous answers registered with the
     * fixture mocks support to complete before verifying expectations.
     * </p>
     * 
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted.
     */
    protected final void verifyNiceMocks()
        throws InterruptedException
    {
        getMocksSupport().awaitAsyncAnswers();
        niceMocksControl_.verify();
    }
}

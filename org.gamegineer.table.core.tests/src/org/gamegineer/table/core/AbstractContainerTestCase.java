/*
 * AbstractContainerTestCase.java
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
 * Created on Mar 26, 2012 at 8:31:54 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.gamegineer.common.core.util.memento.IMementoOriginator;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.IContainer} interface.
 * 
 * @param <TableEnvironmentType>
 *        The type of the table environment.
 * @param <ContainerType>
 *        The type of the container.
 */
public abstract class AbstractContainerTestCase<TableEnvironmentType extends ITableEnvironment, ContainerType extends IContainer>
    extends AbstractComponentTestCase<TableEnvironmentType, ContainerType>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractContainerTestCase}
     * class.
     */
    protected AbstractContainerTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified container listener to the specified container.
     * 
     * <p>
     * This method ensures all pending table environment events have fired
     * before adding the listener.
     * </p>
     * 
     * @param container
     *        The container; must not be {@code null}.
     * @param listener
     *        The container listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered container listener.
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted.
     * @throws java.lang.NullPointerException
     *         If {@code container} or {@code listener} is {@code null}.
     */
    private void addContainerListener(
        /* @NonNull */
        final IContainer container,
        /* @NonNull */
        final IContainerListener listener )
        throws InterruptedException
    {
        awaitPendingTableEnvironmentEvents();
        container.addContainerListener( listener );
    }

    /**
     * Creates a new component with unique attributes using the fixture table
     * environment.
     * 
     * @return A new component; never {@code null}.
     */
    /* @NonNull */
    private IComponent createUniqueComponent()
    {
        return createUniqueComponent( getTableEnvironment() );
    }

    /**
     * Creates a new component with unique attributes using the specified table
     * environment.
     * 
     * @param tableEnvironment
     *        The table environment used to create the new component; must not
     *        be {@code null}.
     * 
     * @return A new component; never {@code null}.
     */
    /* @NonNull */
    private IComponent createUniqueComponent(
        /* @NonNull */
        final TableEnvironmentType tableEnvironment )
    {
        assert tableEnvironment != null;

        return TestComponents.createUniqueComponent( tableEnvironment );
    }

    /**
     * Creates a new container with unique attributes using the fixture table
     * environment.
     * 
     * @return A new container; never {@code null}.
     */
    /* @NonNull */
    private IContainer createUniqueContainer()
    {
        return createUniqueContainer( getTableEnvironment() );
    }

    /**
     * Creates a new container with unique attributes using the specified table
     * environment.
     * 
     * @param tableEnvironment
     *        The table environment used to create the new container; must not
     *        be {@code null}.
     * 
     * @return A new container; never {@code null}.
     */
    /* @NonNull */
    private IContainer createUniqueContainer(
        /* @NonNull */
        final TableEnvironmentType tableEnvironment )
    {
        assert tableEnvironment != null;

        return TestComponents.createUniqueContainer( tableEnvironment );
    }

    /**
     * Fires a component added event for the specified container.
     * 
     * @param container
     *        The container; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code container} is {@code null}.
     */
    protected abstract void fireComponentAdded(
        /* @NonNull */
        ContainerType container );

    /**
     * Fires a component removed event for the specified container.
     * 
     * @param container
     *        The container; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code container} is {@code null}.
     */
    protected abstract void fireComponentRemoved(
        /* @NonNull */
        ContainerType container );

    /**
     * Fires a container layout changed event for the specified container.
     * 
     * @param container
     *        The container; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code container} is {@code null}.
     */
    protected abstract void fireContainerLayoutChanged(
        /* @NonNull */
        ContainerType container );

    /**
     * Gets the container under test in the fixture.
     * 
     * @return The container under test in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final ContainerType getContainer()
    {
        return getComponent();
    }

    /*
     * @see org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase#initializeMementoOriginator(org.gamegineer.common.core.util.memento.IMementoOriginator)
     */
    @Override
    protected void initializeMementoOriginator(
        final IMementoOriginator mementoOriginator )
    {
        super.initializeMementoOriginator( mementoOriginator );

        final IContainer container = (IContainer)mementoOriginator;
        container.setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        container.addComponent( createUniqueComponent() );
    }

    /**
     * Ensures the {@link IContainer#addComponent(IComponent)} method adds a
     * component to the top of the container.
     */
    @Test
    public void testAddComponent_AddsComponentToTop()
    {
        final IComponent component = createUniqueComponent();

        getContainer().addComponent( component );

        final List<IComponent> components = getContainer().getComponents();
        assertSame( component, components.get( components.size() - 1 ) );
        assertSame( getContainer(), component.getContainer() );
    }

    /**
     * Ensures the {@link IContainer#addComponent(IComponent)} method throws an
     * exception when passed an illegal component that was created by a
     * different table environment.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddComponent_Component_Illegal_CreatedByDifferentTableEnvironment()
    {
        final TableEnvironmentType otherTableEnvironment = createTableEnvironment();
        try
        {
            final IComponent otherComponent = createUniqueComponent( otherTableEnvironment );

            getContainer().addComponent( otherComponent );
        }
        finally
        {
            otherTableEnvironment.dispose();
        }
    }

    /**
     * Ensures the {@link IContainer#addComponent(IComponent)} method throws an
     * exception when passed an illegal component that is already contained in a
     * container.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddComponent_Component_Illegal_Owned()
    {
        final IContainer otherContainer = createUniqueContainer();
        final IComponent component = createUniqueComponent();
        otherContainer.addComponent( component );

        getContainer().addComponent( component );
    }

    /**
     * Ensures the {@link IContainer#addComponent(IComponent)} method throws an
     * exception when passed a {@code null} component.
     */
    @Test( expected = NullPointerException.class )
    public void testAddComponent_Component_Null()
    {
        getContainer().addComponent( null );
    }

    /**
     * Ensures the {@link IContainer#addComponent(IComponent)} method changes
     * the location the component to reflect the container location.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testAddComponent_ChangesComponentLocation()
        throws Exception
    {
        final IComponentListener listener = getMocksControl().createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        replayMocks();

        final IComponent component = createUniqueComponent();
        addComponentListener( component, listener );

        getContainer().addComponent( component );

        verifyMocks();
    }

    /**
     * Ensures the {@link IContainer#addComponent(IComponent)} method changes
     * the container bounds.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testAddComponent_ChangesContainerBounds()
        throws Exception
    {
        final IComponentListener listener = getMocksControl().createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        replayMocks();

        getContainer().setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        getContainer().setSurfaceDesign( getContainer().getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        addComponentListener( getContainer(), listener );
        final Rectangle originalContainerBounds = getContainer().getBounds();

        do
        {
            getContainer().addComponent( createUniqueComponent() );

        } while( originalContainerBounds.equals( getContainer().getBounds() ) );

        verifyMocks();
    }

    /**
     * Ensures the {@link IContainer#addComponent(IComponent)} method fires a
     * component added event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testAddComponent_FiresComponentAddedEvent()
        throws Exception
    {
        final IContainerListener listener = getMocksControl().createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture = new Capture<ContainerContentChangedEvent>();
        listener.componentAdded( EasyMock.capture( eventCapture ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        replayMocks();

        getContainer().addComponent( createUniqueComponent() );
        getContainer().addComponent( createUniqueComponent() );
        final IComponent component = createUniqueComponent();
        addContainerListener( getContainer(), listener );

        getContainer().addComponent( component );

        verifyMocks();
        assertSame( getContainer(), eventCapture.getValue().getContainer() );
        assertSame( component, eventCapture.getValue().getComponent() );
        assertEquals( 2, eventCapture.getValue().getComponentIndex() );
    }

    /**
     * Ensures the {@link IContainer#addComponent(IComponent, int)} method adds
     * a component to the container at the specified index.
     */
    @Test
    public void testAddComponentAtIndex_AddsComponentAtIndex()
    {
        final IComponent component1 = createUniqueComponent();
        getContainer().addComponent( component1 );
        final IComponent component2 = createUniqueComponent();
        final IComponent component3 = createUniqueComponent();
        getContainer().addComponent( component3 );
        final List<IComponent> expectedValue = Arrays.asList( component1, component2, component3 );

        getContainer().addComponent( component2, 1 );

        assertEquals( expectedValue, getContainer().getComponents() );
        assertSame( getContainer(), component2.getContainer() );
    }

    /**
     * Ensures the {@link IContainer#addComponent(IComponent, int)} method
     * throws an exception when passed an illegal component that was created by
     * a different table environment.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddComponentAtIndex_Component_Illegal_CreatedByDifferentTableEnvironment()
    {
        final TableEnvironmentType otherTableEnvironment = createTableEnvironment();
        try
        {
            final IComponent otherComponent = createUniqueComponent( otherTableEnvironment );

            getContainer().addComponent( otherComponent, 0 );
        }
        finally
        {
            otherTableEnvironment.dispose();
        }
    }

    /**
     * Ensures the {@link IContainer#addComponent(IComponent, int)} method
     * throws an exception when passed an illegal component that is already
     * contained in a container.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddComponentAtIndex_Component_Illegal_Owned()
    {
        final IContainer otherContainer = createUniqueContainer();
        final IComponent component = createUniqueComponent();
        otherContainer.addComponent( component );

        getContainer().addComponent( component, 0 );
    }

    /**
     * Ensures the {@link IContainer#addComponent(IComponent, int)} method
     * throws an exception when passed a {@code null} component.
     */
    @Test( expected = NullPointerException.class )
    public void testAddComponentAtIndex_Component_Null()
    {
        getContainer().addComponent( null, 0 );
    }

    /**
     * Ensures the {@link IContainer#addComponent(IComponent, int)} method
     * changes the location the component to reflect the container location.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testAddComponentAtIndex_ChangesComponentLocation()
        throws Exception
    {
        final IComponentListener listener = getMocksControl().createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        replayMocks();

        final IComponent component = createUniqueComponent();
        addComponentListener( component, listener );

        getContainer().addComponent( component, 0 );

        verifyMocks();
    }

    /**
     * Ensures the {@link IContainer#addComponent(IComponent, int)} method
     * changes the container bounds.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testAddComponentAtIndex_ChangesContainerBounds()
        throws Exception
    {
        final IComponentListener listener = getMocksControl().createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        replayMocks();

        getContainer().setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        getContainer().setSurfaceDesign( getContainer().getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        addComponentListener( getContainer(), listener );
        final Rectangle originalContainerBounds = getContainer().getBounds();

        do
        {
            getContainer().addComponent( createUniqueComponent(), getContainer().getComponentCount() );

        } while( originalContainerBounds.equals( getContainer().getBounds() ) );

        verifyMocks();
    }

    /**
     * Ensures the {@link IContainer#addComponent(IComponent, int)} method fires
     * a component added event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testAddComponentAtIndex_FiresComponentAddedEvent()
        throws Exception
    {
        final IContainerListener listener = getMocksControl().createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture = new Capture<ContainerContentChangedEvent>();
        listener.componentAdded( EasyMock.capture( eventCapture ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        replayMocks();

        getContainer().addComponent( createUniqueComponent() );
        getContainer().addComponent( createUniqueComponent() );
        final IComponent component = createUniqueComponent();
        addContainerListener( getContainer(), listener );

        getContainer().addComponent( component, 1 );

        verifyMocks();
        assertSame( getContainer(), eventCapture.getValue().getContainer() );
        assertSame( component, eventCapture.getValue().getComponent() );
        assertEquals( 1, eventCapture.getValue().getComponentIndex() );
    }

    /**
     * Ensures the {@link IContainer#addComponent(IComponent, int)} method
     * throws an exception when passed an index that is out of bounds because it
     * is greater than the component count.
     */
    @Test( expected = IndexOutOfBoundsException.class )
    public void testAddComponentAtIndex_Index_OutOfBounds_GreaterThanComponentCount()
    {
        getContainer().addComponent( createUniqueComponent(), getContainer().getComponentCount() + 1 );
    }

    /**
     * Ensures the {@link IContainer#addComponent(IComponent, int)} method
     * throws an exception when passed an index that is out of bounds because it
     * is less than zero.
     */
    @Test( expected = IndexOutOfBoundsException.class )
    public void testAddComponentAtIndex_Index_OutOfBounds_LessThanZero()
    {
        getContainer().addComponent( createUniqueComponent(), -1 );
    }

    /**
     * Ensures the {@link IContainer#addComponents(List)} method adds components
     * to the top of the container.
     */
    @Test
    public void testAddComponents_AddsComponentsToTop()
    {
        final IComponent component1 = createUniqueComponent();
        final IComponent component2 = createUniqueComponent();

        getContainer().addComponents( Arrays.asList( component1, component2 ) );

        final List<IComponent> components = getContainer().getComponents();
        assertSame( component1, components.get( 0 ) );
        assertSame( getContainer(), component1.getContainer() );
        assertSame( component2, components.get( 1 ) );
        assertSame( getContainer(), component2.getContainer() );
    }

    /**
     * Ensures the {@link IContainer#addComponents(List)} method throws an
     * exception when passed an illegal component collection that contains at
     * least one component that was created by a different table environment.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddComponents_Components_Illegal_ContainsComponentCreatedByDifferentTableEnvironment()
    {
        final TableEnvironmentType otherTableEnvironment = createTableEnvironment();
        try
        {
            final IComponent otherComponent = createUniqueComponent( otherTableEnvironment );

            getContainer().addComponents( Arrays.asList( createUniqueComponent(), otherComponent ) );
        }
        finally
        {
            otherTableEnvironment.dispose();
        }
    }

    /**
     * Ensures the {@link IContainer#addComponents(List)} method throws an
     * exception when passed an illegal component collection that contains at
     * least one component already contained in a container.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddComponents_Components_Illegal_ContainsOwnedComponent()
    {
        final IContainer otherContainer = createUniqueContainer();
        final IComponent component = createUniqueComponent();
        otherContainer.addComponent( component );

        getContainer().addComponents( Arrays.asList( createUniqueComponent(), component ) );
    }

    /**
     * Ensures the {@link IContainer#addComponents(List)} method throws an
     * exception when passed an illegal component collection that contains a
     * {@code null} element.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddComponents_Components_Illegal_ContainsNullElement()
    {
        getContainer().addComponents( Collections.<IComponent>singletonList( null ) );
    }

    /**
     * Ensures the {@link IContainer#addComponents(List)} method throws an
     * exception when passed a {@code null} component collection.
     */
    @Test( expected = NullPointerException.class )
    public void testAddComponents_Components_Null()
    {
        getContainer().addComponents( null );
    }

    /**
     * Ensures the {@link IContainer#addComponents(List)} method changes the
     * location of the components to reflect the container location.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testAddComponents_ChangesComponentLocation()
        throws Exception
    {
        final IComponentListener listener = getMocksControl().createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        replayMocks();

        final IComponent component = createUniqueComponent();
        addComponentListener( component, listener );

        getContainer().addComponents( Collections.singletonList( component ) );

        verifyMocks();
    }

    /**
     * Ensures the {@link IContainer#addComponents(List)} method changes the
     * container bounds.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testAddComponents_ChangesContainerBounds()
        throws Exception
    {
        final IComponentListener listener = getMocksControl().createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        replayMocks();

        getContainer().setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        getContainer().setSurfaceDesign( getContainer().getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        addComponentListener( getContainer(), listener );
        final Rectangle originalContainerBounds = getContainer().getBounds();

        do
        {
            getContainer().addComponents( Collections.singletonList( createUniqueComponent() ) );

        } while( originalContainerBounds.equals( getContainer().getBounds() ) );

        verifyMocks();
    }

    /**
     * Ensures the {@link IContainer#addComponents(List)} method fires a
     * component added event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testAddComponents_FiresComponentAddedEvent()
        throws Exception
    {
        final IContainerListener listener = getMocksControl().createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture1 = new Capture<ContainerContentChangedEvent>();
        listener.componentAdded( EasyMock.capture( eventCapture1 ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        final Capture<ContainerContentChangedEvent> eventCapture2 = new Capture<ContainerContentChangedEvent>();
        listener.componentAdded( EasyMock.capture( eventCapture2 ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        replayMocks();

        getContainer().addComponent( createUniqueComponent() );
        getContainer().addComponent( createUniqueComponent() );
        final IComponent component1 = createUniqueComponent();
        final IComponent component2 = createUniqueComponent();
        addContainerListener( getContainer(), listener );

        getContainer().addComponents( Arrays.asList( component1, component2 ) );

        verifyMocks();
        assertSame( getContainer(), eventCapture1.getValue().getContainer() );
        assertSame( component1, eventCapture1.getValue().getComponent() );
        assertEquals( 2, eventCapture1.getValue().getComponentIndex() );
        assertSame( getContainer(), eventCapture2.getValue().getContainer() );
        assertSame( component2, eventCapture2.getValue().getComponent() );
        assertEquals( 3, eventCapture2.getValue().getComponentIndex() );
    }

    /**
     * Ensures the {@link IContainer#addComponents(List, int)} method adds
     * components to the container at the specified index.
     */
    @Test
    public void testAddComponentsAtIndex_AddsComponentsAtIndex()
    {
        final IComponent component1 = createUniqueComponent();
        getContainer().addComponent( component1 );
        final IComponent component2 = createUniqueComponent();
        final IComponent component3 = createUniqueComponent();
        final IComponent component4 = createUniqueComponent();
        getContainer().addComponent( component4 );
        final List<IComponent> expectedValue = Arrays.asList( component1, component2, component3, component4 );

        getContainer().addComponents( Arrays.asList( component2, component3 ), 1 );

        assertEquals( expectedValue, getContainer().getComponents() );
        assertSame( getContainer(), component2.getContainer() );
        assertSame( getContainer(), component3.getContainer() );
    }

    /**
     * Ensures the {@link IContainer#addComponents(List, int)} method throws an
     * exception when passed an illegal component collection that contains at
     * least one component that was created by a different table environment.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddComponentsAtIndex_Components_Illegal_ContainsComponentCreatedByDifferentTableEnvironment()
    {
        final TableEnvironmentType otherTableEnvironment = createTableEnvironment();
        try
        {
            final IComponent otherComponent = createUniqueComponent( otherTableEnvironment );

            getContainer().addComponents( Arrays.asList( createUniqueComponent(), otherComponent ), 0 );
        }
        finally
        {
            otherTableEnvironment.dispose();
        }
    }

    /**
     * Ensures the {@link IContainer#addComponents(List, int)} method throws an
     * exception when passed an illegal component collection that contains at
     * least one component already contained in a container.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddComponentsAtIndex_Components_Illegal_ContainsOwnedComponent()
    {
        final IContainer otherContainer = createUniqueContainer();
        final IComponent component = createUniqueComponent();
        otherContainer.addComponent( component );

        getContainer().addComponents( Arrays.asList( createUniqueComponent(), component ), 0 );
    }

    /**
     * Ensures the {@link IContainer#addComponents(List, int)} method throws an
     * exception when passed an illegal component collection that contains a
     * {@code null} element.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddComponentsAtIndex_Components_Illegal_ContainsNullElement()
    {
        getContainer().addComponents( Collections.<IComponent>singletonList( null ), 0 );
    }

    /**
     * Ensures the {@link IContainer#addComponents(List, int)} method throws an
     * exception when passed a {@code null} component collection.
     */
    @Test( expected = NullPointerException.class )
    public void testAddComponentsAtIndex_Components_Null()
    {
        getContainer().addComponents( null, 0 );
    }

    /**
     * Ensures the {@link IContainer#addComponents(List, int)} method changes
     * the location of the components to reflect the container location.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testAddComponentsAtIndex_ChangesComponentLocation()
        throws Exception
    {
        final IComponentListener listener = getMocksControl().createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        replayMocks();

        final IComponent component = createUniqueComponent();
        addComponentListener( component, listener );

        getContainer().addComponents( Collections.singletonList( component ), 0 );

        verifyMocks();
    }

    /**
     * Ensures the {@link IContainer#addComponents(List, int)} method changes
     * the container bounds.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testAddComponentsAtIndex_ChangesContainerBounds()
        throws Exception
    {
        final IComponentListener listener = getMocksControl().createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        replayMocks();

        getContainer().setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        getContainer().setSurfaceDesign( getContainer().getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        addComponentListener( getContainer(), listener );
        final Rectangle originalContainerBounds = getContainer().getBounds();

        do
        {
            getContainer().addComponents( Collections.singletonList( createUniqueComponent() ), getContainer().getComponentCount() );

        } while( originalContainerBounds.equals( getContainer().getBounds() ) );

        verifyMocks();
    }

    /**
     * Ensures the {@link IContainer#addComponents(List, int)} method fires a
     * component added event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testAddComponentsAtIndex_FiresComponentAddedEvent()
        throws Exception
    {
        final IContainerListener listener = getMocksControl().createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture1 = new Capture<ContainerContentChangedEvent>();
        listener.componentAdded( EasyMock.capture( eventCapture1 ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        final Capture<ContainerContentChangedEvent> eventCapture2 = new Capture<ContainerContentChangedEvent>();
        listener.componentAdded( EasyMock.capture( eventCapture2 ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        replayMocks();

        getContainer().addComponent( createUniqueComponent() );
        getContainer().addComponent( createUniqueComponent() );
        final IComponent component1 = createUniqueComponent();
        final IComponent component2 = createUniqueComponent();
        addContainerListener( getContainer(), listener );

        getContainer().addComponents( Arrays.asList( component1, component2 ), 1 );

        verifyMocks();
        assertSame( getContainer(), eventCapture1.getValue().getContainer() );
        assertSame( component1, eventCapture1.getValue().getComponent() );
        assertEquals( 1, eventCapture1.getValue().getComponentIndex() );
        assertSame( getContainer(), eventCapture2.getValue().getContainer() );
        assertSame( component2, eventCapture2.getValue().getComponent() );
        assertEquals( 2, eventCapture2.getValue().getComponentIndex() );
    }

    /**
     * Ensures the {@link IContainer#addComponents(List, int)} method throws an
     * exception when passed an index that is out of bounds because it is
     * greater than the component count.
     */
    @Test( expected = IndexOutOfBoundsException.class )
    public void testAddComponentsAtIndex_Index_OutOfBounds_GreaterThanComponentCount()
    {
        getContainer().addComponents( Arrays.asList( createUniqueComponent() ), getContainer().getComponentCount() + 1 );
    }

    /**
     * Ensures the {@link IContainer#addComponents(List, int)} method throws an
     * exception when passed an index that is out of bounds because it is less
     * than zero.
     */
    @Test( expected = IndexOutOfBoundsException.class )
    public void testAddComponentsAtIndex_Index_OutOfBounds_LessThanZero()
    {
        getContainer().addComponents( Arrays.asList( createUniqueComponent() ), -1 );
    }

    /**
     * Ensures the {@link IContainer#addContainerListener} method throws an
     * exception when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddContainerListener_Listener_Null()
    {
        getContainer().addContainerListener( null );
    }

    /**
     * Ensures the {@link IContainer#addContainerListener} method throws an
     * exception when passed a listener that is present in the container
     * listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddContainerListener_Listener_Present()
    {
        final IContainerListener listener = getMocksControl().createMock( IContainerListener.class );
        getContainer().addContainerListener( listener );

        getContainer().addContainerListener( listener );
    }

    /**
     * Ensures the component added event catches any exception thrown by the
     * {@link IContainerListener#componentAdded} method of a container listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testComponentAdded_CatchesListenerException()
        throws Exception
    {
        final IContainerListener listener1 = getMocksControl().createMock( IContainerListener.class );
        listener1.componentAdded( EasyMock.notNull( ContainerContentChangedEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer( new IAnswer<Void>()
        {
            @Override
            public Void answer()
            {
                throw new RuntimeException();
            }
        } ) );
        final IContainerListener listener2 = getMocksControl().createMock( IContainerListener.class );
        listener2.componentAdded( EasyMock.notNull( ContainerContentChangedEvent.class ) );
        replayMocks();

        addContainerListener( getContainer(), listener1 );
        addContainerListener( getContainer(), listener2 );

        fireComponentAdded( getContainer() );

        verifyMocks();
    }

    /**
     * Ensures the component removed event catches any exception thrown by the
     * {@link IContainerListener#componentRemoved} method of a container
     * listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testComponentRemoved_CatchesListenerException()
        throws Exception
    {
        final IContainerListener listener1 = getMocksControl().createMock( IContainerListener.class );
        listener1.componentRemoved( EasyMock.notNull( ContainerContentChangedEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer( new IAnswer<Void>()
        {
            @Override
            public Void answer()
            {
                throw new RuntimeException();
            }
        } ) );
        final IContainerListener listener2 = getMocksControl().createMock( IContainerListener.class );
        listener2.componentRemoved( EasyMock.notNull( ContainerContentChangedEvent.class ) );
        replayMocks();

        addContainerListener( getContainer(), listener1 );
        addContainerListener( getContainer(), listener2 );

        fireComponentRemoved( getContainer() );

        verifyMocks();
    }

    /**
     * Ensures the container layout changed event catches any exception thrown
     * by the {@link IContainerListener#containerLayoutChanged} method of a
     * container listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testContainerLayoutChanged_CatchesListenerException()
        throws Exception
    {
        final IContainerListener listener1 = getMocksControl().createMock( IContainerListener.class );
        listener1.containerLayoutChanged( EasyMock.notNull( ContainerEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer( new IAnswer<Void>()
        {
            @Override
            public Void answer()
            {
                throw new RuntimeException();
            }
        } ) );
        final IContainerListener listener2 = getMocksControl().createMock( IContainerListener.class );
        listener2.containerLayoutChanged( EasyMock.notNull( ContainerEvent.class ) );
        replayMocks();

        addContainerListener( getContainer(), listener1 );
        addContainerListener( getContainer(), listener2 );

        fireContainerLayoutChanged( getContainer() );

        verifyMocks();
    }

    /**
     * Ensures the {@link IContainer#getComponent} method throws an exception
     * when passed an illegal index greater than the maximum legal value.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetComponent_Index_Illegal_GreaterThanMaximumLegalValue()
    {
        getContainer().getComponent( 0 );
    }

    /**
     * Ensures the {@link IContainer#getComponent} method throws an exception
     * when passed an illegal index less than the minimum legal value.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetComponent_Index_Illegal_LessThanMinimumLegalValue()
    {
        getContainer().getComponent( -1 );
    }

    /**
     * Ensures the {@link IContainer#getComponent} method returns the correct
     * component when passed a legal index.
     */
    @Test
    public void testGetComponent_Index_Legal()
    {
        final IComponent expectedValue = createUniqueComponent();
        getContainer().addComponent( expectedValue );

        final IComponent actualValue = getContainer().getComponent( 0 );

        assertSame( expectedValue, actualValue );
    }

    /**
     * Ensures the {@link IContainer#getComponentCount} method returns the
     * correct value.
     */
    @Test
    public void testGetComponentCount()
    {
        getContainer().addComponent( createUniqueComponent() );
        getContainer().addComponent( createUniqueComponent() );
        getContainer().addComponent( createUniqueComponent() );

        final int actualValue = getContainer().getComponentCount();

        assertEquals( 3, actualValue );
    }

    /**
     * Ensures the {@link IContainer#getComponents} method returns a copy of the
     * component collection.
     */
    @Test
    public void testGetComponents_ReturnValue_Copy()
    {
        final List<IComponent> components = getContainer().getComponents();
        final int expectedComponentsSize = components.size();

        getContainer().addComponent( createUniqueComponent() );

        assertEquals( expectedComponentsSize, components.size() );
    }

    /**
     * Ensures the {@link IContainer#getComponents} method does not return
     * {@code null}.
     */
    @Test
    public void testGetComponents_ReturnValue_NonNull()
    {
        assertNotNull( getContainer().getComponents() );
    }

    /**
     * Ensures the {@link IContainer#getLayout} method does not return
     * {@code null}.
     */
    @Test
    public void testGetLayout_ReturnValue_NonNull()
    {
        assertNotNull( getContainer().getLayout() );
    }

    /**
     * Ensures the {@link IContainer#removeAllComponents} method does not fire a
     * component removed event when the container is empty.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemoveAllComponents_Empty_DoesNotFireComponentRemovedEvent()
        throws Exception
    {
        final IContainerListener listener = getMocksControl().createMock( IContainerListener.class );
        replayMocks();

        addContainerListener( getContainer(), listener );

        getContainer().removeAllComponents();

        awaitPendingTableEnvironmentEvents();
        verifyMocks();
    }

    /**
     * Ensures the {@link IContainer#removeAllComponents} method returns an
     * empty collection when the container is empty.
     */
    @Test
    public void testRemoveAllComponents_Empty_DoesNotRemoveComponents()
    {
        final List<IComponent> components = getContainer().removeAllComponents();

        assertNotNull( components );
        assertEquals( 0, components.size() );
    }

    /**
     * Ensures the {@link IContainer#removeAllComponents} method causes a layout
     * when the container is not empty.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemoveAllComponents_NotEmpty_CausesLayout()
        throws Exception
    {
        final IContainerLayout layout = getMocksControl().createMock( IContainerLayout.class );
        layout.layout( getContainer() );
        EasyMock.expectLastCall().times( 2 );
        replayMocks();

        getContainer().addComponent( createUniqueComponent() );
        getContainer().addComponent( createUniqueComponent() );
        getContainer().setLayout( layout );

        getContainer().removeAllComponents();

        verifyMocks();
    }

    /**
     * Ensures the {@link IContainer#removeAllComponents} method changes the
     * container bounds when the container is not empty.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemoveAllComponents_NotEmpty_ChangesContainerBounds()
        throws Exception
    {
        final IComponentListener listener = getMocksControl().createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer( 2 ) ).times( 2 );
        replayMocks();

        getContainer().setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        getContainer().setSurfaceDesign( getContainer().getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        addComponentListener( getContainer(), listener );
        final Rectangle originalContainerBounds = getContainer().getBounds();

        do
        {
            getContainer().addComponent( createUniqueComponent() );

        } while( originalContainerBounds.equals( getContainer().getBounds() ) );
        getContainer().removeAllComponents();

        verifyMocks();
    }

    /**
     * Ensures the {@link IContainer#removeAllComponents} method fires a
     * component removed event when the container is not empty.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemoveAllComponents_NotEmpty_FiresComponentRemovedEvent()
        throws Exception
    {
        final IContainerListener listener = getMocksControl().createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture1 = new Capture<ContainerContentChangedEvent>();
        listener.componentRemoved( EasyMock.capture( eventCapture1 ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        final Capture<ContainerContentChangedEvent> eventCapture2 = new Capture<ContainerContentChangedEvent>();
        listener.componentRemoved( EasyMock.capture( eventCapture2 ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        replayMocks();

        final List<IComponent> components = Arrays.asList( createUniqueComponent(), createUniqueComponent() );
        getContainer().addComponents( components );
        addContainerListener( getContainer(), listener );

        getContainer().removeAllComponents();

        verifyMocks();
        assertSame( getContainer(), eventCapture1.getValue().getContainer() );
        assertSame( components.get( 1 ), eventCapture1.getValue().getComponent() );
        assertEquals( 1, eventCapture1.getValue().getComponentIndex() );
        assertSame( getContainer(), eventCapture2.getValue().getContainer() );
        assertSame( components.get( 0 ), eventCapture2.getValue().getComponent() );
        assertEquals( 0, eventCapture2.getValue().getComponentIndex() );
    }

    /**
     * Ensures the {@link IContainer#removeAllComponents} method removes all
     * components in the container when the container is not empty.
     */
    @Test
    public void testRemoveAllComponents_NotEmpty_RemovesAllComponents()
    {
        final List<IComponent> expectedComponents = new ArrayList<IComponent>();
        expectedComponents.add( createUniqueComponent() );
        expectedComponents.add( createUniqueComponent() );
        expectedComponents.add( createUniqueComponent() );
        getContainer().addComponents( expectedComponents );

        final List<IComponent> actualComponents = getContainer().removeAllComponents();

        assertEquals( expectedComponents, actualComponents );
        assertEquals( 0, getContainer().getComponentCount() );
        for( final IComponent actualComponent : actualComponents )
        {
            assertNull( actualComponent.getContainer() );
        }
    }

    /**
     * Ensures the {@link IContainer#removeComponent(IComponent)} method causes
     * a layout after a component has been removed.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemoveComponent_CausesLayout()
        throws Exception
    {
        final IContainerLayout layout = getMocksControl().createMock( IContainerLayout.class );
        layout.layout( getContainer() );
        EasyMock.expectLastCall().times( 2 );
        replayMocks();

        final IComponent component = createUniqueComponent();
        getContainer().addComponent( component );
        getContainer().addComponent( createUniqueComponent() );
        getContainer().setLayout( layout );

        getContainer().removeComponent( component );

        verifyMocks();
    }

    /**
     * Ensures the {@link IContainer#removeComponent(IComponent)} method changes
     * the container bounds.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemoveComponent_ChangesContainerBounds()
        throws Exception
    {
        final IComponentListener listener = getMocksControl().createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer( 2 ) ).times( 2 );
        replayMocks();

        getContainer().setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        getContainer().setSurfaceDesign( getContainer().getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        addComponentListener( getContainer(), listener );
        final Rectangle originalContainerBounds = getContainer().getBounds();

        IComponent component = null;
        do
        {
            component = createUniqueComponent();
            getContainer().addComponent( component );

        } while( originalContainerBounds.equals( getContainer().getBounds() ) );
        getContainer().removeComponent( component );

        verifyMocks();
    }

    /**
     * Ensures the {@link IContainer#removeComponent(IComponent)} method throws
     * an exception when passed an illegal component that is not owned by the
     * container.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveComponent_Component_Illegal_NotOwned()
    {
        getContainer().removeComponent( createUniqueComponent() );
    }

    /**
     * Ensures the {@link IContainer#removeComponent(IComponent)} method throws
     * an exception when passed a {@code null} component.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveComponent_Component_Null()
    {
        getContainer().removeComponent( null );
    }

    /**
     * Ensures the {@link IContainer#removeComponent(IComponent)} method fires a
     * component removed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemoveComponent_FiresComponentRemovedEvent()
        throws Exception
    {
        final IContainerListener listener = getMocksControl().createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture = new Capture<ContainerContentChangedEvent>();
        listener.componentRemoved( EasyMock.capture( eventCapture ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        replayMocks();

        final IComponent component = createUniqueComponent();
        getContainer().addComponent( component );
        addContainerListener( getContainer(), listener );

        getContainer().removeComponent( component );

        verifyMocks();
        assertSame( getContainer(), eventCapture.getValue().getContainer() );
        assertSame( component, eventCapture.getValue().getComponent() );
        assertEquals( 0, eventCapture.getValue().getComponentIndex() );
    }

    /**
     * Ensures the {@link IContainer#removeComponent(IComponent)} method removes
     * a component.
     */
    @Test
    public void testRemoveComponent_RemovesComponent()
    {
        final IComponent component = createUniqueComponent();
        getContainer().addComponent( component );

        getContainer().removeComponent( component );

        final List<IComponent> components = getContainer().getComponents();
        assertFalse( components.contains( component ) );
        assertEquals( 0, components.size() );
        assertNull( component.getContainer() );
    }

    /**
     * Ensures the {@link IContainer#removeComponent(int)} method causes a
     * layout after a component has been removed.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemoveComponentAtIndex_CausesLayout()
        throws Exception
    {
        final IContainerLayout layout = getMocksControl().createMock( IContainerLayout.class );
        layout.layout( getContainer() );
        EasyMock.expectLastCall().times( 2 );
        replayMocks();

        getContainer().addComponent( createUniqueComponent() );
        getContainer().addComponent( createUniqueComponent() );
        getContainer().setLayout( layout );

        getContainer().removeComponent( 0 );

        verifyMocks();
    }

    /**
     * Ensures the {@link IContainer#removeComponent(int)} method changes the
     * container bounds.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemoveComponentAtIndex_ChangesContainerBounds()
        throws Exception
    {
        final IComponentListener listener = getMocksControl().createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer( 2 ) ).times( 2 );
        replayMocks();

        getContainer().setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        getContainer().setSurfaceDesign( getContainer().getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        addComponentListener( getContainer(), listener );
        final Rectangle originalContainerBounds = getContainer().getBounds();

        IComponent component = null;
        do
        {
            component = createUniqueComponent();
            getContainer().addComponent( component );

        } while( originalContainerBounds.equals( getContainer().getBounds() ) );
        getContainer().removeComponent( getContainer().getComponentCount() - 1 );

        verifyMocks();
    }

    /**
     * Ensures the {@link IContainer#removeComponent(int)} method fires a
     * component removed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemoveComponentAtIndex_FiresComponentRemovedEvent()
        throws Exception
    {
        final IContainerListener listener = getMocksControl().createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture = new Capture<ContainerContentChangedEvent>();
        listener.componentRemoved( EasyMock.capture( eventCapture ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        replayMocks();

        final IComponent component = createUniqueComponent();
        getContainer().addComponent( component );
        addContainerListener( getContainer(), listener );

        getContainer().removeComponent( 0 );

        verifyMocks();
        assertSame( getContainer(), eventCapture.getValue().getContainer() );
        assertSame( component, eventCapture.getValue().getComponent() );
        assertEquals( 0, eventCapture.getValue().getComponentIndex() );
    }

    /**
     * Ensures the {@link IContainer#removeComponent(int)} method throws an
     * exception when passed an index that is out of bounds because it is
     * greater than the component count.
     */
    @Test( expected = IndexOutOfBoundsException.class )
    public void testRemoveComponentAtIndex_Index_OutOfBounds_GreaterThanComponentCount()
    {
        getContainer().removeComponent( getContainer().getComponentCount() + 1 );
    }

    /**
     * Ensures the {@link IContainer#removeComponent(int)} method throws an
     * exception when passed an index that is out of bounds because it is less
     * than zero.
     */
    @Test( expected = IndexOutOfBoundsException.class )
    public void testRemoveComponentAtIndex_Index_OutOfBounds_LessThanZero()
    {
        getContainer().removeComponent( -1 );
    }

    /**
     * Ensures the {@link IContainer#removeComponent(int)} method removes a
     * component.
     */
    @Test
    public void testRemoveComponentAtIndex_RemovesComponent()
    {
        final IComponent component = createUniqueComponent();
        getContainer().addComponent( component );

        assertSame( component, getContainer().removeComponent( 0 ) );

        final List<IComponent> components = getContainer().getComponents();
        assertFalse( components.contains( component ) );
        assertEquals( 0, components.size() );
        assertNull( component.getContainer() );
    }

    /**
     * Ensures the {@link IContainer#removeContainerListener} method throws an
     * exception when passed a listener that is absent from the container
     * listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveContainerListener_Listener_Absent()
    {
        getContainer().removeContainerListener( getMocksControl().createMock( IContainerListener.class ) );
    }

    /**
     * Ensures the {@link IContainer#removeContainerListener} method throws an
     * exception when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveContainerListener_Listener_Null()
    {
        getContainer().removeContainerListener( null );
    }

    /**
     * Ensures the {@link IContainer#removeContainerListener} removes a listener
     * that is present in the container listener collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemoveContainerListener_Listener_Present()
        throws Exception
    {
        final IContainerListener listener = getMocksControl().createMock( IContainerListener.class );
        listener.componentAdded( EasyMock.notNull( ContainerContentChangedEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        replayMocks();

        addContainerListener( getContainer(), listener );
        getContainer().addComponent( createUniqueComponent() );

        getMocksSupport().awaitAsyncAnswers();
        getContainer().removeContainerListener( listener );
        fireContainerLayoutChanged( getContainer() );

        awaitPendingTableEnvironmentEvents();
        verifyMocks();
    }

    /**
     * Ensures the {@link IContainer#setLayout} method changes the container
     * bounds when appropriate.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testSetLayout_ChangesContainerBounds()
        throws Exception
    {
        final IComponentListener componentListener = getMocksControl().createMock( IComponentListener.class );
        componentListener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        final IContainerListener containerListener = getMocksControl().createMock( IContainerListener.class );
        containerListener.containerLayoutChanged( EasyMock.notNull( ContainerEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        replayMocks();

        getContainer().setSurfaceDesign( getContainer().getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        getContainer().setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        getContainer().addComponent( createUniqueComponent() );
        getContainer().addComponent( createUniqueComponent() );
        addComponentListener( getContainer(), componentListener );
        addContainerListener( getContainer(), containerListener );

        getContainer().setLayout( TestContainerLayouts.createVerticalContainerLayout() );

        verifyMocks();
    }

    /**
     * Ensures the {@link IContainer#setLayout} method fires a container layout
     * changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testSetLayout_FiresContainerLayoutChangedEvent()
        throws Exception
    {
        final IContainerListener listener = getMocksControl().createMock( IContainerListener.class );
        listener.containerLayoutChanged( EasyMock.notNull( ContainerEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        replayMocks();

        getContainer().setLayout( TestContainerLayouts.createUniqueContainerLayout() );
        addContainerListener( getContainer(), listener );

        getContainer().setLayout( TestContainerLayouts.createUniqueContainerLayout() );

        verifyMocks();
    }

    /**
     * Ensures the {@link IContainer#setLayout} method throws an exception when
     * passed a {@code null} layout.
     */
    @Test( expected = NullPointerException.class )
    public void testSetLayout_Layout_Null()
    {
        getContainer().setLayout( null );
    }

    /**
     * Ensures the {@link IContainer#setLocation} method changes the location of
     * all child components to reflect the new container location.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testSetLocation_ChangesChildComponentLocation()
        throws Exception
    {
        final IComponentListener listener = getMocksControl().createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        replayMocks();

        final IComponent component = createUniqueComponent();
        getContainer().addComponent( component );
        addComponentListener( component, listener );

        getContainer().setLocation( new Point( 1010, 2020 ) );

        verifyMocks();
    }

    /**
     * Ensures the {@link IContainer#setOrigin} method changes the location of
     * all child components to reflect the new container origin.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testSetOrigin_ChangesChildComponentLocation()
        throws Exception
    {
        final IComponentListener listener = getMocksControl().createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        EasyMock.expectLastCall().andAnswer( getMocksSupport().asyncAnswer() );
        replayMocks();

        final IComponent component = createUniqueComponent();
        getContainer().addComponent( component );
        addComponentListener( component, listener );

        getContainer().setOrigin( new Point( 1010, 2020 ) );

        verifyMocks();
    }
}

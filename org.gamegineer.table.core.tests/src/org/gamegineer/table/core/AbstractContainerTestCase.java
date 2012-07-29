/*
 * AbstractContainerTestCase.java
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
import org.easymock.IMocksControl;
import org.gamegineer.common.core.util.memento.IMementoOriginator;
import org.junit.Before;
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
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;


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

        return Components.createUniqueComponent( tableEnvironment );
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

        return Components.createUniqueContainer( tableEnvironment );
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
        container.setLayout( ContainerLayouts.createHorizontalContainerLayout() );
        container.addComponent( createUniqueComponent() );
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#setUp()
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
     * Ensures the {@code addComponent} method adds a component to the
     * container.
     */
    @Test
    public void testAddComponent_AddsComponent()
    {
        final IComponent component = createUniqueComponent();

        getContainer().addComponent( component );

        final List<IComponent> components = getContainer().getComponents();
        assertSame( component, components.get( components.size() - 1 ) );
        assertSame( getContainer(), component.getContainer() );
    }

    /**
     * Ensures the {@code addComponent} method throws an exception when passed
     * an illegal component that was created by a different table environment.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddComponent_Component_Illegal_CreatedByDifferentTableEnvironment()
    {
        final TableEnvironmentType otherTableEnvironment = createTableEnvironment();
        final IComponent otherComponent = createUniqueComponent( otherTableEnvironment );

        getContainer().addComponent( otherComponent );
    }

    /**
     * Ensures the {@code addComponent} method throws an exception when passed
     * an illegal component that is already contained in a container.
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
     * Ensures the {@code addComponent} method throws an exception when passed a
     * {@code null} component.
     */
    @Test( expected = NullPointerException.class )
    public void testAddComponent_Component_Null()
    {
        getContainer().addComponent( null );
    }

    /**
     * Ensures the {@code addComponent} method changes the location the
     * component to reflect the container location.
     */
    @Test
    public void testAddComponent_ChangesComponentLocation()
    {
        final IComponent component = createUniqueComponent();
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        component.addComponentListener( listener );

        getContainer().addComponent( component );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code addComponent} method changes the container bounds.
     */
    @Test( timeout = 1000 )
    public void testAddComponent_ChangesContainerBounds()
    {
        getContainer().setSurfaceDesign( getContainer().getOrientation(), ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        getContainer().addComponentListener( listener );
        final Rectangle originalContainerBounds = getContainer().getBounds();

        do
        {
            getContainer().addComponent( createUniqueComponent() );

        } while( originalContainerBounds.equals( getContainer().getBounds() ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code addComponent} method fires a component added event.
     */
    @Test
    public void testAddComponent_FiresComponentAddedEvent()
    {
        final IComponent component = createUniqueComponent();
        final IContainerListener listener = mocksControl_.createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture = new Capture<ContainerContentChangedEvent>();
        listener.componentAdded( EasyMock.capture( eventCapture ) );
        mocksControl_.replay();
        getContainer().addContainerListener( listener );

        getContainer().addComponent( component );

        mocksControl_.verify();
        assertSame( getContainer(), eventCapture.getValue().getContainer() );
        assertSame( component, eventCapture.getValue().getComponent() );
        assertEquals( 0, eventCapture.getValue().getComponentIndex() );
    }

    /**
     * Ensures the {@code addComponents} method adds components to the
     * container.
     */
    @Test
    public void testAddComponents_AddsComponents()
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
     * Ensures the {@code addComponents} method throws an exception when passed
     * an illegal component collection that contains at least one component that
     * was created by a different table environment.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddComponents_Components_Illegal_ContainsComponentCreatedByDifferentTableEnvironment()
    {
        final TableEnvironmentType otherTableEnvironment = createTableEnvironment();
        final IComponent otherComponent = createUniqueComponent( otherTableEnvironment );

        getContainer().addComponents( Arrays.asList( createUniqueComponent(), otherComponent ) );
    }

    /**
     * Ensures the {@code addComponents} method throws an exception when passed
     * an illegal component collection that contains at least one component
     * already contained in a container.
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
     * Ensures the {@code addComponents} method throws an exception when passed
     * an illegal component collection that contains a {@code null} element.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddComponents_Components_Illegal_ContainsNullElement()
    {
        getContainer().addComponents( Collections.<IComponent>singletonList( null ) );
    }

    /**
     * Ensures the {@code addComponents} method throws an exception when passed
     * a {@code null} component collection.
     */
    @Test( expected = NullPointerException.class )
    public void testAddComponents_Components_Null()
    {
        getContainer().addComponents( null );
    }

    /**
     * Ensures the {@code addComponents} method changes the location of the
     * components to reflect the container location.
     */
    @Test
    public void testAddComponents_ChangesComponentLocation()
    {
        final IComponent component = createUniqueComponent();
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        component.addComponentListener( listener );

        getContainer().addComponents( Collections.singletonList( component ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code addComponents} method changes the container bounds.
     */
    @Test( timeout = 1000 )
    public void testAddComponents_ChangesContainerBounds()
    {
        getContainer().setSurfaceDesign( getContainer().getOrientation(), ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        getContainer().addComponentListener( listener );
        final Rectangle originalContainerBounds = getContainer().getBounds();

        do
        {
            getContainer().addComponents( Collections.singletonList( createUniqueComponent() ) );

        } while( originalContainerBounds.equals( getContainer().getBounds() ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code addComponents} method fires a component added event.
     */
    @Test
    public void testAddComponents_FiresComponentAddedEvent()
    {
        final IContainerListener listener = mocksControl_.createMock( IContainerListener.class );
        listener.componentAdded( EasyMock.notNull( ContainerContentChangedEvent.class ) );
        EasyMock.expectLastCall().times( 2 );
        mocksControl_.replay();
        getContainer().addContainerListener( listener );

        getContainer().addComponents( Arrays.asList( createUniqueComponent(), createUniqueComponent() ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code addContainerListener} method throws an exception when
     * passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddContainerListener_Listener_Null()
    {
        getContainer().addContainerListener( null );
    }

    /**
     * Ensures the {@code addContainerListener} method throws an exception when
     * passed a listener that is present in the container listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddContainerListener_Listener_Present()
    {
        final IContainerListener listener = mocksControl_.createMock( IContainerListener.class );
        getContainer().addContainerListener( listener );

        getContainer().addContainerListener( listener );
    }

    /**
     * Ensures the component added event catches any exception thrown by the
     * {@code componentAdded} method of a container listener.
     */
    @Test
    public void testComponentAdded_CatchesListenerException()
    {
        final IContainerListener listener1 = mocksControl_.createMock( IContainerListener.class );
        listener1.componentAdded( EasyMock.notNull( ContainerContentChangedEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IContainerListener listener2 = mocksControl_.createMock( IContainerListener.class );
        listener2.componentAdded( EasyMock.notNull( ContainerContentChangedEvent.class ) );
        mocksControl_.replay();
        getContainer().addContainerListener( listener1 );
        getContainer().addContainerListener( listener2 );

        fireComponentAdded( getContainer() );

        mocksControl_.verify();
    }

    /**
     * Ensures the component removed event catches any exception thrown by the
     * {@code componentRemoved} method of a container listener.
     */
    @Test
    public void testComponentRemoved_CatchesListenerException()
    {
        final IContainerListener listener1 = mocksControl_.createMock( IContainerListener.class );
        listener1.componentRemoved( EasyMock.notNull( ContainerContentChangedEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IContainerListener listener2 = mocksControl_.createMock( IContainerListener.class );
        listener2.componentRemoved( EasyMock.notNull( ContainerContentChangedEvent.class ) );
        mocksControl_.replay();
        getContainer().addContainerListener( listener1 );
        getContainer().addContainerListener( listener2 );

        fireComponentRemoved( getContainer() );

        mocksControl_.verify();
    }

    /**
     * Ensures the container layout changed event catches any exception thrown
     * by the {@code containerLayoutChanged} method of a container listener.
     */
    @Test
    public void testContainerLayoutChanged_CatchesListenerException()
    {
        final IContainerListener listener1 = mocksControl_.createMock( IContainerListener.class );
        listener1.containerLayoutChanged( EasyMock.notNull( ContainerEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IContainerListener listener2 = mocksControl_.createMock( IContainerListener.class );
        listener2.containerLayoutChanged( EasyMock.notNull( ContainerEvent.class ) );
        mocksControl_.replay();
        getContainer().addContainerListener( listener1 );
        getContainer().addContainerListener( listener2 );

        fireContainerLayoutChanged( getContainer() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code getComponent} method throws an exception when passed
     * an illegal index greater than the maximum legal value.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetComponent_Index_Illegal_GreaterThanMaximumLegalValue()
    {
        getContainer().getComponent( 0 );
    }

    /**
     * Ensures the {@code getComponent} method throws an exception when passed
     * an illegal index less than the minimum legal value.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetComponent_Index_Illegal_LessThanMinimumLegalValue()
    {
        getContainer().getComponent( -1 );
    }

    /**
     * Ensures the {@code getComponent} method returns the correct component
     * when passed a legal index.
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
     * Ensures the {@code getComponentCount} method returns the correct value.
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
     * Ensures the {@code getComponents} method returns a copy of the component
     * collection.
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
     * Ensures the {@code getComponents} method does not return {@code null}.
     */
    @Test
    public void testGetComponents_ReturnValue_NonNull()
    {
        assertNotNull( getContainer().getComponents() );
    }

    /**
     * Ensures the {@code getLayout} method does not return {@code null}.
     */
    @Test
    public void testGetLayout_ReturnValue_NonNull()
    {
        assertNotNull( getContainer().getLayout() );
    }

    /**
     * Ensures the {@code removeComponent} method throws an exception when
     * passed an illegal component that is not owned by the container.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveComponent_Component_Illegal_NotOwned()
    {
        getContainer().removeComponent( createUniqueComponent() );
    }

    /**
     * Ensures the {@code removeComponent} method throws an exception when
     * passed a {@code null} component.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveComponent_Component_Null()
    {
        getContainer().removeComponent( null );
    }

    /**
     * Ensures the {@code removeComponent} method fires a component removed
     * event.
     */
    @Test
    public void testRemoveComponent_FiresComponentRemovedEvent()
    {
        final IComponent component = createUniqueComponent();
        getContainer().addComponent( component );
        final IContainerListener listener = mocksControl_.createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture = new Capture<ContainerContentChangedEvent>();
        listener.componentRemoved( EasyMock.capture( eventCapture ) );
        mocksControl_.replay();
        getContainer().addContainerListener( listener );

        getContainer().removeComponent( component );

        mocksControl_.verify();
        assertSame( getContainer(), eventCapture.getValue().getContainer() );
        assertSame( component, eventCapture.getValue().getComponent() );
        assertEquals( 0, eventCapture.getValue().getComponentIndex() );
    }

    /**
     * Ensures the {@code removeComponent} method removes a component.
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
     * Ensures the {@code removeComponents()} method does not fire a component
     * removed event when the container is empty.
     */
    @Test
    public void testRemoveComponents_Empty_DoesNotFireComponentRemovedEvent()
    {
        final IContainerListener listener = mocksControl_.createMock( IContainerListener.class );
        mocksControl_.replay();
        getContainer().addContainerListener( listener );

        getContainer().removeComponents();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeComponents()} method returns an empty collection
     * when the container is empty.
     */
    @Test
    public void testRemoveComponents_Empty_DoesNotRemoveComponents()
    {
        final List<IComponent> components = getContainer().removeComponents();

        assertNotNull( components );
        assertEquals( 0, components.size() );
    }

    /**
     * Ensures the {@code removeComponents()} method changes the container
     * bounds when the container is not empty.
     */
    @Test( timeout = 1000 )
    public void testRemoveComponents_NotEmpty_ChangesContainerBounds()
    {
        getContainer().setSurfaceDesign( getContainer().getOrientation(), ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        EasyMock.expectLastCall().times( 2 );
        mocksControl_.replay();
        getContainer().addComponentListener( listener );
        final Rectangle originalContainerBounds = getContainer().getBounds();

        do
        {
            getContainer().addComponent( createUniqueComponent() );

        } while( originalContainerBounds.equals( getContainer().getBounds() ) );
        getContainer().removeComponents();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeComponents()} method fires a component removed
     * event when the container is not empty.
     */
    @Test
    public void testRemoveComponents_NotEmpty_FiresComponentRemovedEvent()
    {
        final List<IComponent> components = Arrays.asList( createUniqueComponent(), createUniqueComponent() );
        getContainer().addComponents( components );
        final IContainerListener listener = mocksControl_.createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture1 = new Capture<ContainerContentChangedEvent>();
        listener.componentRemoved( EasyMock.capture( eventCapture1 ) );
        final Capture<ContainerContentChangedEvent> eventCapture2 = new Capture<ContainerContentChangedEvent>();
        listener.componentRemoved( EasyMock.capture( eventCapture2 ) );
        mocksControl_.replay();
        getContainer().addContainerListener( listener );

        getContainer().removeComponents();

        mocksControl_.verify();
        assertSame( getContainer(), eventCapture1.getValue().getContainer() );
        assertSame( components.get( 1 ), eventCapture1.getValue().getComponent() );
        assertEquals( 1, eventCapture1.getValue().getComponentIndex() );
        assertSame( getContainer(), eventCapture2.getValue().getContainer() );
        assertSame( components.get( 0 ), eventCapture2.getValue().getComponent() );
        assertEquals( 0, eventCapture2.getValue().getComponentIndex() );
    }

    /**
     * Ensures the {@code removeComponents()} method removes all components in
     * the container when the container is not empty.
     */
    @Test
    public void testRemoveComponents_NotEmpty_RemovesAllComponents()
    {
        final List<IComponent> expectedComponents = new ArrayList<IComponent>();
        expectedComponents.add( createUniqueComponent() );
        expectedComponents.add( createUniqueComponent() );
        expectedComponents.add( createUniqueComponent() );
        getContainer().addComponents( expectedComponents );

        final List<IComponent> actualComponents = getContainer().removeComponents();

        assertEquals( expectedComponents, actualComponents );
        assertEquals( 0, getContainer().getComponentCount() );
        for( final IComponent actualComponent : actualComponents )
        {
            assertNull( actualComponent.getContainer() );
        }
    }

    /**
     * Ensures the {@code removeComponents(Point)} method does not fire a
     * component removed event when a component is absent at the specified
     * location.
     */
    @Test
    public void testRemoveComponentsFromPoint_Location_ComponentAbsent_DoesNotFireComponentRemovedEvent()
    {
        final IContainerListener listener = mocksControl_.createMock( IContainerListener.class );
        mocksControl_.replay();
        getContainer().addContainerListener( listener );

        getContainer().removeComponents( new Point( 0, 0 ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeComponents(Point)} method returns an empty
     * collection when a component is absent at the specified location.
     */
    @Test
    public void testRemoveComponentsFromPoint_Location_ComponentAbsent_DoesNotRemoveComponents()
    {
        final List<IComponent> components = getContainer().removeComponents( new Point( 0, 0 ) );

        assertNotNull( components );
        assertEquals( 0, components.size() );
    }

    /**
     * Ensures the {@code removeComponents(Point)} method changes the container
     * bounds when a component is present at the specified location.
     */
    @Test( timeout = 1000 )
    public void testRemoveComponentsFromPoint_Location_ComponentPresent_ChangesContainerBounds()
    {
        getContainer().setSurfaceDesign( getContainer().getOrientation(), ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        EasyMock.expectLastCall().times( 2 );
        mocksControl_.replay();
        getContainer().addComponentListener( listener );
        final Rectangle originalContainerBounds = getContainer().getBounds();

        do
        {
            getContainer().addComponent( createUniqueComponent() );

        } while( originalContainerBounds.equals( getContainer().getBounds() ) );
        final List<IComponent> components = getContainer().getComponents();
        getContainer().removeComponents( components.get( components.size() - 1 ).getLocation() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeComponents(Point)} method fires a component
     * removed event when a component is present at the specified location.
     */
    @Test
    public void testRemoveComponentsFromPoint_Location_ComponentPresent_FiresComponentRemovedEvent()
    {
        final List<IComponent> components = Arrays.asList( createUniqueComponent(), createUniqueComponent() );
        getContainer().setLayout( ContainerLayouts.createHorizontalContainerLayout() );
        getContainer().addComponents( components );
        final IContainerListener listener = mocksControl_.createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture1 = new Capture<ContainerContentChangedEvent>();
        listener.componentRemoved( EasyMock.capture( eventCapture1 ) );
        final Capture<ContainerContentChangedEvent> eventCapture2 = new Capture<ContainerContentChangedEvent>();
        listener.componentRemoved( EasyMock.capture( eventCapture2 ) );
        mocksControl_.replay();
        getContainer().addContainerListener( listener );

        getContainer().removeComponents( getContainer().getComponents().get( 0 ).getLocation() );

        mocksControl_.verify();
        assertSame( getContainer(), eventCapture1.getValue().getContainer() );
        assertSame( components.get( 1 ), eventCapture1.getValue().getComponent() );
        assertEquals( 1, eventCapture1.getValue().getComponentIndex() );
        assertSame( getContainer(), eventCapture2.getValue().getContainer() );
        assertSame( components.get( 0 ), eventCapture2.getValue().getComponent() );
        assertEquals( 0, eventCapture2.getValue().getComponentIndex() );
    }

    /**
     * Ensures the {@code removeComponents(Point)} method removes the correct
     * components from the container when a component is present at the
     * specified location.
     */
    @Test
    public void testRemoveComponentsFromPoint_Location_ComponentPresent_RemovesComponents()
    {
        final List<IComponent> components = new ArrayList<IComponent>();
        components.add( createUniqueComponent() );
        components.add( createUniqueComponent() );
        components.add( createUniqueComponent() );
        getContainer().setLayout( ContainerLayouts.createHorizontalContainerLayout() );
        getContainer().addComponents( components );
        final List<IComponent> expectedComponents = components.subList( 1, components.size() );

        final List<IComponent> actualComponents = getContainer().removeComponents( components.get( 1 ).getLocation() );

        assertEquals( expectedComponents, actualComponents );
        assertEquals( components.size() - expectedComponents.size(), getContainer().getComponentCount() );
        for( final IComponent actualComponent : actualComponents )
        {
            assertNull( actualComponent.getContainer() );
        }
    }

    /**
     * Ensures the {@code removeComponents(Point)} method throws an exception
     * when passed a {@code null} location.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveComponentsFromPoint_Location_Null()
    {
        getContainer().removeComponents( null );
    }

    /**
     * Ensures the {@code removeContainerListener} method throws an exception
     * when passed a listener that is absent from the container listener
     * collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveContainerListener_Listener_Absent()
    {
        getContainer().removeContainerListener( mocksControl_.createMock( IContainerListener.class ) );
    }

    /**
     * Ensures the {@code removeContainerListener} method throws an exception
     * when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveContainerListener_Listener_Null()
    {
        getContainer().removeContainerListener( null );
    }

    /**
     * Ensures the {@code removeContainerListener} removes a listener that is
     * present in the container listener collection.
     */
    @Test
    public void testRemoveContainerListener_Listener_Present()
    {
        final IContainerListener listener = mocksControl_.createMock( IContainerListener.class );
        listener.componentAdded( EasyMock.notNull( ContainerContentChangedEvent.class ) );
        mocksControl_.replay();
        getContainer().addContainerListener( listener );
        getContainer().addComponent( createUniqueComponent() );

        getContainer().removeContainerListener( listener );
        getContainer().addComponent( createUniqueComponent() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeTopComponent} method does not fire a component
     * removed event when the container is empty.
     */
    @Test
    public void testRemoveTopComponent_Empty_DoesNotFireComponentRemovedEvent()
    {
        final IContainerListener listener = mocksControl_.createMock( IContainerListener.class );
        mocksControl_.replay();
        getContainer().addContainerListener( listener );

        getContainer().removeTopComponent();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeTopComponent} method returns {@code null} when
     * the container is empty.
     */
    @Test
    public void testRemoveTopComponent_Empty_DoesNotRemoveComponent()
    {
        final IComponent component = getContainer().removeTopComponent();

        assertNull( component );
    }

    /**
     * Ensures the {@code removeTopComponent} method changes the container
     * bounds when the container is not empty.
     */
    @Test( timeout = 1000 )
    public void testRemoveTopComponent_NotEmpty_ChangesContainerBounds()
    {
        getContainer().setSurfaceDesign( getContainer().getOrientation(), ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        EasyMock.expectLastCall().times( 2 );
        mocksControl_.replay();
        getContainer().addComponentListener( listener );
        final Rectangle originalContainerBounds = getContainer().getBounds();

        do
        {
            getContainer().addComponent( createUniqueComponent() );

        } while( originalContainerBounds.equals( getContainer().getBounds() ) );
        getContainer().removeTopComponent();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code removeTopComponent} method fires a component removed
     * event when the container is not empty.
     */
    @Test
    public void testRemoveTopComponent_NotEmpty_FiresComponentRemovedEvent()
    {
        final IComponent component = createUniqueComponent();
        getContainer().addComponent( component );
        final IContainerListener listener = mocksControl_.createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture = new Capture<ContainerContentChangedEvent>();
        listener.componentRemoved( EasyMock.capture( eventCapture ) );
        mocksControl_.replay();
        getContainer().addContainerListener( listener );

        getContainer().removeTopComponent();

        mocksControl_.verify();
        assertSame( getContainer(), eventCapture.getValue().getContainer() );
        assertSame( component, eventCapture.getValue().getComponent() );
        assertEquals( 0, eventCapture.getValue().getComponentIndex() );
    }

    /**
     * Ensures the {@code removeTopComponent} method removes the component at
     * the top of the container when the container is not empty.
     */
    @Test
    public void testRemoveTopComponent_NotEmpty_RemovesComponent()
    {
        final IComponent expectedComponent = createUniqueComponent();
        getContainer().addComponent( expectedComponent );

        final IComponent actualComponent = getContainer().removeTopComponent();

        assertSame( expectedComponent, actualComponent );
        assertEquals( 0, getContainer().getComponentCount() );
        assertNull( actualComponent.getContainer() );
    }

    /**
     * Ensures the {@code setLayout} method changes the container bounds when
     * appropriate.
     */
    @Test
    public void testSetLayout_ChangesContainerBounds()
    {
        getContainer().setSurfaceDesign( getContainer().getOrientation(), ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        getContainer().setLayout( ContainerLayouts.createHorizontalContainerLayout() );
        getContainer().addComponent( createUniqueComponent() );
        getContainer().addComponent( createUniqueComponent() );
        final IComponentListener componentListener = mocksControl_.createMock( IComponentListener.class );
        componentListener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        final IContainerListener containerListener = mocksControl_.createMock( IContainerListener.class );
        containerListener.containerLayoutChanged( EasyMock.notNull( ContainerEvent.class ) );
        mocksControl_.replay();
        getContainer().addComponentListener( componentListener );
        getContainer().addContainerListener( containerListener );

        getContainer().setLayout( ContainerLayouts.createVerticalContainerLayout() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code setLayout} method fires a container layout changed
     * event.
     */
    @Test
    public void testSetLayout_FiresContainerLayoutChangedEvent()
    {
        getContainer().setLayout( ContainerLayouts.createUniqueContainerLayout() );
        final IContainerListener listener = mocksControl_.createMock( IContainerListener.class );
        listener.containerLayoutChanged( EasyMock.notNull( ContainerEvent.class ) );
        mocksControl_.replay();
        getContainer().addContainerListener( listener );

        getContainer().setLayout( ContainerLayouts.createUniqueContainerLayout() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code setLayout} method throws an exception when passed a
     * {@code null} layout.
     */
    @Test( expected = NullPointerException.class )
    public void testSetLayout_Layout_Null()
    {
        getContainer().setLayout( null );
    }

    /**
     * Ensures the {@code setLocation} method changes the location of all child
     * components to reflect the new container location.
     */
    @Test
    public void testSetLocation_ChangesChildComponentLocation()
    {
        final IComponent component = createUniqueComponent();
        getContainer().addComponent( component );
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        component.addComponentListener( listener );

        getContainer().setLocation( new Point( 1010, 2020 ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code setOrigin} method changes the location of all child
     * components to reflect the new container origin.
     */
    @Test
    public void testSetOrigin_ChangesChildComponentLocation()
    {
        final IComponent component = createUniqueComponent();
        getContainer().addComponent( component );
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.notNull( ComponentEvent.class ) );
        mocksControl_.replay();
        component.addComponentListener( listener );

        getContainer().setOrigin( new Point( 1010, 2020 ) );

        mocksControl_.verify();
    }
}

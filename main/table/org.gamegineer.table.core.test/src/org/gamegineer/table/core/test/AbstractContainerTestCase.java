/*
 * AbstractContainerTestCase.java
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
 * Created on Mar 26, 2012 at 8:31:54 PM.
 */

package org.gamegineer.table.core.test;

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
import java.util.Optional;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.NonNull;
import org.gamegineer.common.core.util.memento.IMementoOriginator;
import org.gamegineer.table.core.ComponentEvent;
import org.gamegineer.table.core.ContainerContentChangedEvent;
import org.gamegineer.table.core.ContainerEvent;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IComponentListener;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.IContainerLayout;
import org.gamegineer.table.core.IContainerListener;
import org.gamegineer.table.core.ITableEnvironment;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IContainer} interface.
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

    /** The timeout for all tests in the fixture. */
    @Rule
    public final Timeout TIMEOUT = Timeout.millis( 1000L );

    /** The mocks control for use in the fixture. */
    private Optional<IMocksControl> mocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractContainerTestCase}
     * class.
     */
    protected AbstractContainerTestCase()
    {
        mocksControl_ = Optional.empty();
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
    private IComponent createUniqueComponent(
        final TableEnvironmentType tableEnvironment )
    {
        return TestComponents.createUniqueComponent( tableEnvironment );
    }

    /**
     * Creates a new container with unique attributes using the fixture table
     * environment.
     * 
     * @return A new container; never {@code null}.
     */
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
    private IContainer createUniqueContainer(
        final TableEnvironmentType tableEnvironment )
    {
        return TestComponents.createUniqueContainer( tableEnvironment );
    }

    /**
     * Fires a component added event for the specified container.
     * 
     * @param container
     *        The container; must not be {@code null}.
     */
    protected abstract void fireComponentAdded(
        ContainerType container );

    /**
     * Fires a component removed event for the specified container.
     * 
     * @param container
     *        The container; must not be {@code null}.
     */
    protected abstract void fireComponentRemoved(
        ContainerType container );

    /**
     * Fires a container layout changed event for the specified container.
     * 
     * @param container
     *        The container; must not be {@code null}.
     */
    protected abstract void fireContainerLayoutChanged(
        ContainerType container );

    /**
     * Gets the container under test in the fixture.
     * 
     * @return The container under test in the fixture; never {@code null}.
     */
    protected final ContainerType getContainer()
    {
        return getComponent();
    }

    /**
     * Gets the fixture mocks control.
     * 
     * @return The fixture mocks control; never {@code null}.
     */
    private IMocksControl getMocksControl()
    {
        return mocksControl_.get();
    }

    /*
     * @see org.gamegineer.table.core.test.AbstractComponentTestCase#initializeMementoOriginator(org.gamegineer.common.core.util.memento.IMementoOriginator)
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

    /*
     * @see org.gamegineer.table.core.test.AbstractComponentTestCase#setUp()
     */
    @Before
    @Override
    public void setUp()
        throws Exception
    {
        mocksControl_ = Optional.of( EasyMock.createControl() );

        super.setUp();
    }

    /**
     * Ensures the {@link IContainer#addComponent(IComponent)} method adds a
     * component to the top of the container.
     */
    @Test
    public void testAddComponent_AddsComponentToTop()
    {
        final ContainerType container = getContainer();
        final IComponent component = createUniqueComponent();

        container.addComponent( component );

        final List<IComponent> components = container.getComponents();
        assertSame( component, components.get( components.size() - 1 ) );
        assertSame( container, component.getContainer() );
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
        final IComponent otherComponent = createUniqueComponent( otherTableEnvironment );

        getContainer().addComponent( otherComponent );
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
     * Ensures the {@link IContainer#addComponent(IComponent)} method changes
     * the location the component to reflect the container location.
     */
    @Test
    public void testAddComponent_ChangesComponentLocation()
    {
        final IMocksControl mocksControl = getMocksControl();
        final IComponent component = createUniqueComponent();
        final IComponentListener listener = mocksControl.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl.replay();
        component.addComponentListener( listener );

        getContainer().addComponent( component );

        mocksControl.verify();
    }

    /**
     * Ensures the {@link IContainer#addComponent(IComponent)} method changes
     * the container bounds.
     */
    @Test
    public void testAddComponent_ChangesContainerBounds()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        container.setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        container.setSurfaceDesign( container.getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        final IComponentListener listener = mocksControl.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl.replay();
        container.addComponentListener( listener );
        final Rectangle originalContainerBounds = container.getBounds();

        do
        {
            container.addComponent( createUniqueComponent() );

        } while( originalContainerBounds.equals( container.getBounds() ) );

        mocksControl.verify();
    }

    /**
     * Ensures the {@link IContainer#addComponent(IComponent)} method fires a
     * component added event.
     */
    @Test
    public void testAddComponent_FiresComponentAddedEvent()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        container.addComponent( createUniqueComponent() );
        container.addComponent( createUniqueComponent() );
        final IComponent component = createUniqueComponent();
        final IContainerListener listener = mocksControl.createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture = new Capture<>();
        listener.componentAdded( EasyMock.capture( eventCapture ) );
        mocksControl.replay();
        container.addContainerListener( listener );

        container.addComponent( component );

        mocksControl.verify();
        assertSame( container, eventCapture.getValue().getContainer() );
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
        final ContainerType container = getContainer();
        final IComponent component1 = createUniqueComponent();
        container.addComponent( component1 );
        final IComponent component2 = createUniqueComponent();
        final IComponent component3 = createUniqueComponent();
        container.addComponent( component3 );
        final List<IComponent> expectedValue = Arrays.asList( component1, component2, component3 );

        container.addComponent( component2, 1 );

        assertEquals( expectedValue, container.getComponents() );
        assertSame( container, component2.getContainer() );
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
        final IComponent otherComponent = createUniqueComponent( otherTableEnvironment );

        getContainer().addComponent( otherComponent, 0 );
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
     * changes the location the component to reflect the container location.
     */
    @Test
    public void testAddComponentAtIndex_ChangesComponentLocation()
    {
        final IMocksControl mocksControl = getMocksControl();
        final IComponent component = createUniqueComponent();
        final IComponentListener listener = mocksControl.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl.replay();
        component.addComponentListener( listener );

        getContainer().addComponent( component, 0 );

        mocksControl.verify();
    }

    /**
     * Ensures the {@link IContainer#addComponent(IComponent, int)} method
     * changes the container bounds.
     */
    @Test
    public void testAddComponentAtIndex_ChangesContainerBounds()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        container.setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        container.setSurfaceDesign( container.getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        final IComponentListener listener = mocksControl.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl.replay();
        container.addComponentListener( listener );
        final Rectangle originalContainerBounds = container.getBounds();

        do
        {
            container.addComponent( createUniqueComponent(), container.getComponentCount() );

        } while( originalContainerBounds.equals( container.getBounds() ) );

        mocksControl.verify();
    }

    /**
     * Ensures the {@link IContainer#addComponent(IComponent, int)} method fires
     * a component added event.
     */
    @Test
    public void testAddComponentAtIndex_FiresComponentAddedEvent()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        container.addComponent( createUniqueComponent() );
        container.addComponent( createUniqueComponent() );
        final IComponent component = createUniqueComponent();
        final IContainerListener listener = mocksControl.createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture = new Capture<>();
        listener.componentAdded( EasyMock.capture( eventCapture ) );
        mocksControl.replay();
        container.addContainerListener( listener );

        container.addComponent( component, 1 );

        mocksControl.verify();
        assertSame( container, eventCapture.getValue().getContainer() );
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
        final ContainerType container = getContainer();

        container.addComponent( createUniqueComponent(), container.getComponentCount() + 1 );
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
        final ContainerType container = getContainer();
        final IComponent component1 = createUniqueComponent();
        final IComponent component2 = createUniqueComponent();

        container.addComponents( Arrays.asList( component1, component2 ) );

        final List<IComponent> components = container.getComponents();
        assertSame( component1, components.get( 0 ) );
        assertSame( container, component1.getContainer() );
        assertSame( component2, components.get( 1 ) );
        assertSame( container, component2.getContainer() );
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
        final IComponent otherComponent = createUniqueComponent( otherTableEnvironment );

        getContainer().addComponents( Arrays.asList( createUniqueComponent(), otherComponent ) );
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
     * Ensures the {@link IContainer#addComponents(List)} method changes the
     * location of the components to reflect the container location.
     */
    @Test
    public void testAddComponents_ChangesComponentLocation()
    {
        final IMocksControl mocksControl = getMocksControl();
        final IComponent component = createUniqueComponent();
        final IComponentListener listener = mocksControl.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl.replay();
        component.addComponentListener( listener );

        getContainer().addComponents( Collections.singletonList( component ) );

        mocksControl.verify();
    }

    /**
     * Ensures the {@link IContainer#addComponents(List)} method changes the
     * container bounds.
     */
    @Test
    public void testAddComponents_ChangesContainerBounds()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        container.setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        container.setSurfaceDesign( container.getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        final IComponentListener listener = mocksControl.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl.replay();
        container.addComponentListener( listener );
        final Rectangle originalContainerBounds = container.getBounds();

        do
        {
            container.addComponents( Collections.singletonList( createUniqueComponent() ) );

        } while( originalContainerBounds.equals( container.getBounds() ) );

        mocksControl.verify();
    }

    /**
     * Ensures the {@link IContainer#addComponents(List)} method fires a
     * component added event.
     */
    @Test
    public void testAddComponents_FiresComponentAddedEvent()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        container.addComponent( createUniqueComponent() );
        container.addComponent( createUniqueComponent() );
        final IComponent component1 = createUniqueComponent();
        final IComponent component2 = createUniqueComponent();
        final IContainerListener listener = mocksControl.createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture1 = new Capture<>();
        listener.componentAdded( EasyMock.capture( eventCapture1 ) );
        final Capture<ContainerContentChangedEvent> eventCapture2 = new Capture<>();
        listener.componentAdded( EasyMock.capture( eventCapture2 ) );
        mocksControl.replay();
        container.addContainerListener( listener );

        container.addComponents( Arrays.asList( component1, component2 ) );

        mocksControl.verify();
        assertSame( container, eventCapture1.getValue().getContainer() );
        assertSame( component1, eventCapture1.getValue().getComponent() );
        assertEquals( 2, eventCapture1.getValue().getComponentIndex() );
        assertSame( container, eventCapture2.getValue().getContainer() );
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
        final ContainerType container = getContainer();
        final IComponent component1 = createUniqueComponent();
        container.addComponent( component1 );
        final IComponent component2 = createUniqueComponent();
        final IComponent component3 = createUniqueComponent();
        final IComponent component4 = createUniqueComponent();
        container.addComponent( component4 );
        final List<IComponent> expectedValue = Arrays.asList( component1, component2, component3, component4 );

        container.addComponents( Arrays.asList( component2, component3 ), 1 );

        assertEquals( expectedValue, container.getComponents() );
        assertSame( container, component2.getContainer() );
        assertSame( container, component3.getContainer() );
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
        final IComponent otherComponent = createUniqueComponent( otherTableEnvironment );

        getContainer().addComponents( Arrays.asList( createUniqueComponent(), otherComponent ), 0 );
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
     * Ensures the {@link IContainer#addComponents(List, int)} method changes
     * the location of the components to reflect the container location.
     */
    @Test
    public void testAddComponentsAtIndex_ChangesComponentLocation()
    {
        final IMocksControl mocksControl = getMocksControl();
        final IComponent component = createUniqueComponent();
        final IComponentListener listener = mocksControl.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl.replay();
        component.addComponentListener( listener );

        getContainer().addComponents( Collections.singletonList( component ), 0 );

        mocksControl.verify();
    }

    /**
     * Ensures the {@link IContainer#addComponents(List, int)} method changes
     * the container bounds.
     */
    @Test
    public void testAddComponentsAtIndex_ChangesContainerBounds()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        container.setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        container.setSurfaceDesign( container.getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        final IComponentListener listener = mocksControl.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl.replay();
        container.addComponentListener( listener );
        final Rectangle originalContainerBounds = container.getBounds();

        do
        {
            container.addComponents( Collections.singletonList( createUniqueComponent() ), container.getComponentCount() );

        } while( originalContainerBounds.equals( container.getBounds() ) );

        mocksControl.verify();
    }

    /**
     * Ensures the {@link IContainer#addComponents(List, int)} method fires a
     * component added event.
     */
    @Test
    public void testAddComponentsAtIndex_FiresComponentAddedEvent()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        container.addComponent( createUniqueComponent() );
        container.addComponent( createUniqueComponent() );
        final IComponent component1 = createUniqueComponent();
        final IComponent component2 = createUniqueComponent();
        final IContainerListener listener = mocksControl.createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture1 = new Capture<>();
        listener.componentAdded( EasyMock.capture( eventCapture1 ) );
        final Capture<ContainerContentChangedEvent> eventCapture2 = new Capture<>();
        listener.componentAdded( EasyMock.capture( eventCapture2 ) );
        mocksControl.replay();
        container.addContainerListener( listener );

        container.addComponents( Arrays.asList( component1, component2 ), 1 );

        mocksControl.verify();
        assertSame( container, eventCapture1.getValue().getContainer() );
        assertSame( component1, eventCapture1.getValue().getComponent() );
        assertEquals( 1, eventCapture1.getValue().getComponentIndex() );
        assertSame( container, eventCapture2.getValue().getContainer() );
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
        final ContainerType container = getContainer();

        container.addComponents( Arrays.asList( createUniqueComponent() ), container.getComponentCount() + 1 );
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
     * exception when passed a listener that is present in the container
     * listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddContainerListener_Listener_Present()
    {
        final ContainerType container = getContainer();
        final IContainerListener listener = getMocksControl().createMock( IContainerListener.class );
        container.addContainerListener( listener );

        container.addContainerListener( listener );
    }

    /**
     * Ensures the component added event catches any exception thrown by the
     * {@link IContainerListener#componentAdded} method of a container listener.
     */
    @Test
    public void testComponentAdded_CatchesListenerException()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        final IContainerListener listener1 = mocksControl.createMock( IContainerListener.class );
        listener1.componentAdded( EasyMock.<@NonNull ContainerContentChangedEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IContainerListener listener2 = mocksControl.createMock( IContainerListener.class );
        listener2.componentAdded( EasyMock.<@NonNull ContainerContentChangedEvent>notNull() );
        mocksControl.replay();
        container.addContainerListener( listener1 );
        container.addContainerListener( listener2 );

        fireComponentAdded( container );

        mocksControl.verify();
    }

    /**
     * Ensures the component removed event catches any exception thrown by the
     * {@link IContainerListener#componentRemoved} method of a container
     * listener.
     */
    @Test
    public void testComponentRemoved_CatchesListenerException()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        final IContainerListener listener1 = mocksControl.createMock( IContainerListener.class );
        listener1.componentRemoved( EasyMock.<@NonNull ContainerContentChangedEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IContainerListener listener2 = mocksControl.createMock( IContainerListener.class );
        listener2.componentRemoved( EasyMock.<@NonNull ContainerContentChangedEvent>notNull() );
        mocksControl.replay();
        container.addContainerListener( listener1 );
        container.addContainerListener( listener2 );

        fireComponentRemoved( container );

        mocksControl.verify();
    }

    /**
     * Ensures the container layout changed event catches any exception thrown
     * by the {@link IContainerListener#containerLayoutChanged} method of a
     * container listener.
     */
    @Test
    public void testContainerLayoutChanged_CatchesListenerException()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        final IContainerListener listener1 = mocksControl.createMock( IContainerListener.class );
        listener1.containerLayoutChanged( EasyMock.<@NonNull ContainerEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IContainerListener listener2 = mocksControl.createMock( IContainerListener.class );
        listener2.containerLayoutChanged( EasyMock.<@NonNull ContainerEvent>notNull() );
        mocksControl.replay();
        container.addContainerListener( listener1 );
        container.addContainerListener( listener2 );

        fireContainerLayoutChanged( container );

        mocksControl.verify();
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
        final ContainerType container = getContainer();
        final IComponent expectedValue = createUniqueComponent();
        container.addComponent( expectedValue );

        final IComponent actualValue = container.getComponent( 0 );

        assertSame( expectedValue, actualValue );
    }

    /**
     * Ensures the {@link IContainer#getComponentCount} method returns the
     * correct value.
     */
    @Test
    public void testGetComponentCount()
    {
        final ContainerType container = getContainer();
        container.addComponent( createUniqueComponent() );
        container.addComponent( createUniqueComponent() );
        container.addComponent( createUniqueComponent() );

        final int actualValue = container.getComponentCount();

        assertEquals( 3, actualValue );
    }

    /**
     * Ensures the {@link IContainer#getComponents} method returns a copy of the
     * component collection.
     */
    @Test
    public void testGetComponents_ReturnValue_Copy()
    {
        final ContainerType container = getContainer();
        final List<IComponent> components = container.getComponents();
        final int expectedComponentsSize = components.size();

        container.addComponent( createUniqueComponent() );

        assertEquals( expectedComponentsSize, components.size() );
    }

    /**
     * Ensures the {@link IContainer#removeAllComponents} method does not fire a
     * component removed event when the container is empty.
     */
    @Test
    public void testRemoveAllComponents_Empty_DoesNotFireComponentRemovedEvent()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        final IContainerListener listener = mocksControl.createMock( IContainerListener.class );
        mocksControl.replay();
        container.addContainerListener( listener );

        container.removeAllComponents();

        mocksControl.verify();
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
     */
    @Test
    public void testRemoveAllComponents_NotEmpty_CausesLayout()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        container.addComponent( createUniqueComponent() );
        container.addComponent( createUniqueComponent() );
        final IContainerLayout layout = mocksControl.createMock( IContainerLayout.class );
        layout.layout( container );
        EasyMock.expectLastCall().times( 2 );
        mocksControl.replay();
        container.setLayout( layout );

        container.removeAllComponents();

        mocksControl.verify();
    }

    /**
     * Ensures the {@link IContainer#removeAllComponents} method changes the
     * container bounds when the container is not empty.
     */
    @Test
    public void testRemoveAllComponents_NotEmpty_ChangesContainerBounds()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        container.setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        container.setSurfaceDesign( container.getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        final IComponentListener listener = mocksControl.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        EasyMock.expectLastCall().times( 2 );
        mocksControl.replay();
        container.addComponentListener( listener );
        final Rectangle originalContainerBounds = container.getBounds();

        do
        {
            container.addComponent( createUniqueComponent() );

        } while( originalContainerBounds.equals( container.getBounds() ) );
        container.removeAllComponents();

        mocksControl.verify();
    }

    /**
     * Ensures the {@link IContainer#removeAllComponents} method fires a
     * component removed event when the container is not empty.
     */
    @Test
    public void testRemoveAllComponents_NotEmpty_FiresComponentRemovedEvent()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        final List<IComponent> components = Arrays.asList( createUniqueComponent(), createUniqueComponent() );
        container.addComponents( components );
        final IContainerListener listener = mocksControl.createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture1 = new Capture<>();
        listener.componentRemoved( EasyMock.capture( eventCapture1 ) );
        final Capture<ContainerContentChangedEvent> eventCapture2 = new Capture<>();
        listener.componentRemoved( EasyMock.capture( eventCapture2 ) );
        mocksControl.replay();
        container.addContainerListener( listener );

        container.removeAllComponents();

        mocksControl.verify();
        assertSame( container, eventCapture1.getValue().getContainer() );
        assertSame( components.get( 1 ), eventCapture1.getValue().getComponent() );
        assertEquals( 1, eventCapture1.getValue().getComponentIndex() );
        assertSame( container, eventCapture2.getValue().getContainer() );
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
        final ContainerType container = getContainer();
        final List<IComponent> expectedComponents = new ArrayList<>();
        expectedComponents.add( createUniqueComponent() );
        expectedComponents.add( createUniqueComponent() );
        expectedComponents.add( createUniqueComponent() );
        container.addComponents( expectedComponents );

        final List<IComponent> actualComponents = container.removeAllComponents();

        assertEquals( expectedComponents, actualComponents );
        assertEquals( 0, container.getComponentCount() );
        for( final IComponent actualComponent : actualComponents )
        {
            assertNull( actualComponent.getContainer() );
        }
    }

    /**
     * Ensures the {@link IContainer#removeComponent(IComponent)} method causes
     * a layout after a component has been removed.
     */
    @Test
    public void testRemoveComponent_CausesLayout()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        final IComponent component = createUniqueComponent();
        container.addComponent( component );
        container.addComponent( createUniqueComponent() );
        final IContainerLayout layout = mocksControl.createMock( IContainerLayout.class );
        layout.layout( container );
        EasyMock.expectLastCall().times( 2 );
        mocksControl.replay();
        container.setLayout( layout );

        container.removeComponent( component );

        mocksControl.verify();
    }

    /**
     * Ensures the {@link IContainer#removeComponent(IComponent)} method changes
     * the container bounds.
     */
    @Test
    public void testRemoveComponent_ChangesContainerBounds()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        container.setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        container.setSurfaceDesign( container.getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        final IComponentListener listener = mocksControl.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        EasyMock.expectLastCall().times( 2 );
        mocksControl.replay();
        container.addComponentListener( listener );
        final Rectangle originalContainerBounds = container.getBounds();

        IComponent component = null;
        do
        {
            component = createUniqueComponent();
            container.addComponent( component );

        } while( originalContainerBounds.equals( container.getBounds() ) );
        container.removeComponent( component );

        mocksControl.verify();
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
     * Ensures the {@link IContainer#removeComponent(IComponent)} method fires a
     * component removed event.
     */
    @Test
    public void testRemoveComponent_FiresComponentRemovedEvent()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        final IComponent component = createUniqueComponent();
        container.addComponent( component );
        final IContainerListener listener = mocksControl.createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture = new Capture<>();
        listener.componentRemoved( EasyMock.capture( eventCapture ) );
        mocksControl.replay();
        container.addContainerListener( listener );

        container.removeComponent( component );

        mocksControl.verify();
        assertSame( container, eventCapture.getValue().getContainer() );
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
        final ContainerType container = getContainer();
        final IComponent component = createUniqueComponent();
        container.addComponent( component );

        container.removeComponent( component );

        final List<IComponent> components = container.getComponents();
        assertFalse( components.contains( component ) );
        assertEquals( 0, components.size() );
        assertNull( component.getContainer() );
    }

    /**
     * Ensures the {@link IContainer#removeComponent(int)} method causes a
     * layout after a component has been removed.
     */
    @Test
    public void testRemoveComponentAtIndex_CausesLayout()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        container.addComponent( createUniqueComponent() );
        container.addComponent( createUniqueComponent() );
        final IContainerLayout layout = mocksControl.createMock( IContainerLayout.class );
        layout.layout( container );
        EasyMock.expectLastCall().times( 2 );
        mocksControl.replay();
        container.setLayout( layout );

        container.removeComponent( 0 );

        mocksControl.verify();
    }

    /**
     * Ensures the {@link IContainer#removeComponent(int)} method changes the
     * container bounds.
     */
    @Test
    public void testRemoveComponentAtIndex_ChangesContainerBounds()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        container.setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        container.setSurfaceDesign( container.getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        final IComponentListener listener = mocksControl.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        EasyMock.expectLastCall().times( 2 );
        mocksControl.replay();
        container.addComponentListener( listener );
        final Rectangle originalContainerBounds = container.getBounds();

        IComponent component = null;
        do
        {
            component = createUniqueComponent();
            container.addComponent( component );

        } while( originalContainerBounds.equals( container.getBounds() ) );
        container.removeComponent( container.getComponentCount() - 1 );

        mocksControl.verify();
    }

    /**
     * Ensures the {@link IContainer#removeComponent(int)} method fires a
     * component removed event.
     */
    @Test
    public void testRemoveComponentAtIndex_FiresComponentRemovedEvent()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        final IComponent component = createUniqueComponent();
        container.addComponent( component );
        final IContainerListener listener = mocksControl.createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture = new Capture<>();
        listener.componentRemoved( EasyMock.capture( eventCapture ) );
        mocksControl.replay();
        container.addContainerListener( listener );

        container.removeComponent( 0 );

        mocksControl.verify();
        assertSame( container, eventCapture.getValue().getContainer() );
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
        final ContainerType container = getContainer();

        container.removeComponent( container.getComponentCount() + 1 );
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
        final ContainerType container = getContainer();
        final IComponent component = createUniqueComponent();
        container.addComponent( component );

        assertSame( component, container.removeComponent( 0 ) );

        final List<IComponent> components = container.getComponents();
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
     * Ensures the {@link IContainer#removeContainerListener} removes a listener
     * that is present in the container listener collection.
     */
    @Test
    public void testRemoveContainerListener_Listener_Present()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        final IContainerListener listener = mocksControl.createMock( IContainerListener.class );
        listener.componentAdded( EasyMock.<@NonNull ContainerContentChangedEvent>notNull() );
        mocksControl.replay();
        container.addContainerListener( listener );
        container.addComponent( createUniqueComponent() );

        container.removeContainerListener( listener );
        container.addComponent( createUniqueComponent() );

        mocksControl.verify();
    }

    /**
     * Ensures the {@link IContainer#setLayout} method changes the container
     * bounds when appropriate.
     */
    @Test
    public void testSetLayout_ChangesContainerBounds()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        container.setSurfaceDesign( container.getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        container.setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        container.addComponent( createUniqueComponent() );
        container.addComponent( createUniqueComponent() );
        final IComponentListener componentListener = mocksControl.createMock( IComponentListener.class );
        componentListener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        final IContainerListener containerListener = mocksControl.createMock( IContainerListener.class );
        containerListener.containerLayoutChanged( EasyMock.<@NonNull ContainerEvent>notNull() );
        mocksControl.replay();
        container.addComponentListener( componentListener );
        container.addContainerListener( containerListener );

        container.setLayout( TestContainerLayouts.createVerticalContainerLayout() );

        mocksControl.verify();
    }

    /**
     * Ensures the {@link IContainer#setLayout} method fires a container layout
     * changed event.
     */
    @Test
    public void testSetLayout_FiresContainerLayoutChangedEvent()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        container.setLayout( TestContainerLayouts.createUniqueContainerLayout() );
        final IContainerListener listener = mocksControl.createMock( IContainerListener.class );
        listener.containerLayoutChanged( EasyMock.<@NonNull ContainerEvent>notNull() );
        mocksControl.replay();
        container.addContainerListener( listener );

        container.setLayout( TestContainerLayouts.createUniqueContainerLayout() );

        mocksControl.verify();
    }

    /**
     * Ensures the {@link IContainer#setLocation} method changes the location of
     * all child components to reflect the new container location.
     */
    @Test
    public void testSetLocation_ChangesChildComponentLocation()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        final IComponent component = createUniqueComponent();
        container.addComponent( component );
        final IComponentListener listener = mocksControl.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl.replay();
        component.addComponentListener( listener );

        container.setLocation( new Point( 1010, 2020 ) );

        mocksControl.verify();
    }

    /**
     * Ensures the {@link IContainer#setOrigin} method changes the location of
     * all child components to reflect the new container origin.
     */
    @Test
    public void testSetOrigin_ChangesChildComponentLocation()
    {
        final ContainerType container = getContainer();
        final IMocksControl mocksControl = getMocksControl();
        final IComponent component = createUniqueComponent();
        container.addComponent( component );
        final IComponentListener listener = mocksControl.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl.replay();
        component.addComponentListener( listener );

        container.setOrigin( new Point( 1010, 2020 ) );

        mocksControl.verify();
    }
}

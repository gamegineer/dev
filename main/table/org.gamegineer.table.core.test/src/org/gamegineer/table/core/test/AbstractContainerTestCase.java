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
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
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
@NonNullByDefault( {
    DefaultLocation.PARAMETER, //
    DefaultLocation.RETURN_TYPE, //
    DefaultLocation.TYPE_BOUND, //
    DefaultLocation.TYPE_ARGUMENT
} )
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
        mocksControl_ = EasyMock.createControl();

        super.setUp();
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
        final IComponent component = createUniqueComponent();
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl_.replay();
        component.addComponentListener( listener );

        getContainer().addComponent( component );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link IContainer#addComponent(IComponent)} method changes
     * the container bounds.
     */
    @Test
    public void testAddComponent_ChangesContainerBounds()
    {
        getContainer().setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        getContainer().setSurfaceDesign( getContainer().getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
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
     * Ensures the {@link IContainer#addComponent(IComponent)} method fires a
     * component added event.
     */
    @Test
    public void testAddComponent_FiresComponentAddedEvent()
    {
        getContainer().addComponent( createUniqueComponent() );
        getContainer().addComponent( createUniqueComponent() );
        final IComponent component = createUniqueComponent();
        final IContainerListener listener = mocksControl_.createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture = new Capture<>();
        listener.componentAdded( EasyMock.capture( eventCapture ) );
        mocksControl_.replay();
        getContainer().addContainerListener( listener );

        getContainer().addComponent( component );

        mocksControl_.verify();
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
        final IComponent component = createUniqueComponent();
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl_.replay();
        component.addComponentListener( listener );

        getContainer().addComponent( component, 0 );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link IContainer#addComponent(IComponent, int)} method
     * changes the container bounds.
     */
    @Test
    public void testAddComponentAtIndex_ChangesContainerBounds()
    {
        getContainer().setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        getContainer().setSurfaceDesign( getContainer().getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl_.replay();
        getContainer().addComponentListener( listener );
        final Rectangle originalContainerBounds = getContainer().getBounds();

        do
        {
            getContainer().addComponent( createUniqueComponent(), getContainer().getComponentCount() );

        } while( originalContainerBounds.equals( getContainer().getBounds() ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link IContainer#addComponent(IComponent, int)} method fires
     * a component added event.
     */
    @Test
    public void testAddComponentAtIndex_FiresComponentAddedEvent()
    {
        getContainer().addComponent( createUniqueComponent() );
        getContainer().addComponent( createUniqueComponent() );
        final IComponent component = createUniqueComponent();
        final IContainerListener listener = mocksControl_.createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture = new Capture<>();
        listener.componentAdded( EasyMock.capture( eventCapture ) );
        mocksControl_.replay();
        getContainer().addContainerListener( listener );

        getContainer().addComponent( component, 1 );

        mocksControl_.verify();
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
        final IComponent component = createUniqueComponent();
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl_.replay();
        component.addComponentListener( listener );

        getContainer().addComponents( Collections.singletonList( component ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link IContainer#addComponents(List)} method changes the
     * container bounds.
     */
    @Test
    public void testAddComponents_ChangesContainerBounds()
    {
        getContainer().setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        getContainer().setSurfaceDesign( getContainer().getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
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
     * Ensures the {@link IContainer#addComponents(List)} method fires a
     * component added event.
     */
    @Test
    public void testAddComponents_FiresComponentAddedEvent()
    {
        getContainer().addComponent( createUniqueComponent() );
        getContainer().addComponent( createUniqueComponent() );
        final IComponent component1 = createUniqueComponent();
        final IComponent component2 = createUniqueComponent();
        final IContainerListener listener = mocksControl_.createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture1 = new Capture<>();
        listener.componentAdded( EasyMock.capture( eventCapture1 ) );
        final Capture<ContainerContentChangedEvent> eventCapture2 = new Capture<>();
        listener.componentAdded( EasyMock.capture( eventCapture2 ) );
        mocksControl_.replay();
        getContainer().addContainerListener( listener );

        getContainer().addComponents( Arrays.asList( component1, component2 ) );

        mocksControl_.verify();
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
        final IComponent component = createUniqueComponent();
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl_.replay();
        component.addComponentListener( listener );

        getContainer().addComponents( Collections.singletonList( component ), 0 );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link IContainer#addComponents(List, int)} method changes
     * the container bounds.
     */
    @Test
    public void testAddComponentsAtIndex_ChangesContainerBounds()
    {
        getContainer().setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        getContainer().setSurfaceDesign( getContainer().getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl_.replay();
        getContainer().addComponentListener( listener );
        final Rectangle originalContainerBounds = getContainer().getBounds();

        do
        {
            getContainer().addComponents( Collections.singletonList( createUniqueComponent() ), getContainer().getComponentCount() );

        } while( originalContainerBounds.equals( getContainer().getBounds() ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link IContainer#addComponents(List, int)} method fires a
     * component added event.
     */
    @Test
    public void testAddComponentsAtIndex_FiresComponentAddedEvent()
    {
        getContainer().addComponent( createUniqueComponent() );
        getContainer().addComponent( createUniqueComponent() );
        final IComponent component1 = createUniqueComponent();
        final IComponent component2 = createUniqueComponent();
        final IContainerListener listener = mocksControl_.createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture1 = new Capture<>();
        listener.componentAdded( EasyMock.capture( eventCapture1 ) );
        final Capture<ContainerContentChangedEvent> eventCapture2 = new Capture<>();
        listener.componentAdded( EasyMock.capture( eventCapture2 ) );
        mocksControl_.replay();
        getContainer().addContainerListener( listener );

        getContainer().addComponents( Arrays.asList( component1, component2 ), 1 );

        mocksControl_.verify();
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
     * exception when passed a listener that is present in the container
     * listener collection.
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
     * {@link IContainerListener#componentAdded} method of a container listener.
     */
    @Test
    public void testComponentAdded_CatchesListenerException()
    {
        final IContainerListener listener1 = mocksControl_.createMock( IContainerListener.class );
        listener1.componentAdded( EasyMock.<@NonNull ContainerContentChangedEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IContainerListener listener2 = mocksControl_.createMock( IContainerListener.class );
        listener2.componentAdded( EasyMock.<@NonNull ContainerContentChangedEvent>notNull() );
        mocksControl_.replay();
        getContainer().addContainerListener( listener1 );
        getContainer().addContainerListener( listener2 );

        fireComponentAdded( getContainer() );

        mocksControl_.verify();
    }

    /**
     * Ensures the component removed event catches any exception thrown by the
     * {@link IContainerListener#componentRemoved} method of a container
     * listener.
     */
    @Test
    public void testComponentRemoved_CatchesListenerException()
    {
        final IContainerListener listener1 = mocksControl_.createMock( IContainerListener.class );
        listener1.componentRemoved( EasyMock.<@NonNull ContainerContentChangedEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IContainerListener listener2 = mocksControl_.createMock( IContainerListener.class );
        listener2.componentRemoved( EasyMock.<@NonNull ContainerContentChangedEvent>notNull() );
        mocksControl_.replay();
        getContainer().addContainerListener( listener1 );
        getContainer().addContainerListener( listener2 );

        fireComponentRemoved( getContainer() );

        mocksControl_.verify();
    }

    /**
     * Ensures the container layout changed event catches any exception thrown
     * by the {@link IContainerListener#containerLayoutChanged} method of a
     * container listener.
     */
    @Test
    public void testContainerLayoutChanged_CatchesListenerException()
    {
        final IContainerListener listener1 = mocksControl_.createMock( IContainerListener.class );
        listener1.containerLayoutChanged( EasyMock.<@NonNull ContainerEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IContainerListener listener2 = mocksControl_.createMock( IContainerListener.class );
        listener2.containerLayoutChanged( EasyMock.<@NonNull ContainerEvent>notNull() );
        mocksControl_.replay();
        getContainer().addContainerListener( listener1 );
        getContainer().addContainerListener( listener2 );

        fireContainerLayoutChanged( getContainer() );

        mocksControl_.verify();
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
     * Ensures the {@link IContainer#removeAllComponents} method does not fire a
     * component removed event when the container is empty.
     */
    @Test
    public void testRemoveAllComponents_Empty_DoesNotFireComponentRemovedEvent()
    {
        final IContainerListener listener = mocksControl_.createMock( IContainerListener.class );
        mocksControl_.replay();
        getContainer().addContainerListener( listener );

        getContainer().removeAllComponents();

        mocksControl_.verify();
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
        getContainer().addComponent( createUniqueComponent() );
        getContainer().addComponent( createUniqueComponent() );
        final IContainerLayout layout = mocksControl_.createMock( IContainerLayout.class );
        layout.layout( getContainer() );
        EasyMock.expectLastCall().times( 2 );
        mocksControl_.replay();
        getContainer().setLayout( layout );

        getContainer().removeAllComponents();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link IContainer#removeAllComponents} method changes the
     * container bounds when the container is not empty.
     */
    @Test
    public void testRemoveAllComponents_NotEmpty_ChangesContainerBounds()
    {
        getContainer().setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        getContainer().setSurfaceDesign( getContainer().getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        EasyMock.expectLastCall().times( 2 );
        mocksControl_.replay();
        getContainer().addComponentListener( listener );
        final Rectangle originalContainerBounds = getContainer().getBounds();

        do
        {
            getContainer().addComponent( createUniqueComponent() );

        } while( originalContainerBounds.equals( getContainer().getBounds() ) );
        getContainer().removeAllComponents();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link IContainer#removeAllComponents} method fires a
     * component removed event when the container is not empty.
     */
    @Test
    public void testRemoveAllComponents_NotEmpty_FiresComponentRemovedEvent()
    {
        final List<IComponent> components = Arrays.asList( createUniqueComponent(), createUniqueComponent() );
        getContainer().addComponents( components );
        final IContainerListener listener = mocksControl_.createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture1 = new Capture<>();
        listener.componentRemoved( EasyMock.capture( eventCapture1 ) );
        final Capture<ContainerContentChangedEvent> eventCapture2 = new Capture<>();
        listener.componentRemoved( EasyMock.capture( eventCapture2 ) );
        mocksControl_.replay();
        getContainer().addContainerListener( listener );

        getContainer().removeAllComponents();

        mocksControl_.verify();
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
        final List<IComponent> expectedComponents = new ArrayList<>();
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
     */
    @Test
    public void testRemoveComponent_CausesLayout()
    {
        final IComponent component = createUniqueComponent();
        getContainer().addComponent( component );
        getContainer().addComponent( createUniqueComponent() );
        final IContainerLayout layout = mocksControl_.createMock( IContainerLayout.class );
        layout.layout( getContainer() );
        EasyMock.expectLastCall().times( 2 );
        mocksControl_.replay();
        getContainer().setLayout( layout );

        getContainer().removeComponent( component );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link IContainer#removeComponent(IComponent)} method changes
     * the container bounds.
     */
    @Test
    public void testRemoveComponent_ChangesContainerBounds()
    {
        getContainer().setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        getContainer().setSurfaceDesign( getContainer().getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        EasyMock.expectLastCall().times( 2 );
        mocksControl_.replay();
        getContainer().addComponentListener( listener );
        final Rectangle originalContainerBounds = getContainer().getBounds();

        IComponent component = null;
        do
        {
            component = createUniqueComponent();
            getContainer().addComponent( component );

        } while( originalContainerBounds.equals( getContainer().getBounds() ) );
        getContainer().removeComponent( component );

        mocksControl_.verify();
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
        final IComponent component = createUniqueComponent();
        getContainer().addComponent( component );
        final IContainerListener listener = mocksControl_.createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture = new Capture<>();
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
     */
    @Test
    public void testRemoveComponentAtIndex_CausesLayout()
    {
        getContainer().addComponent( createUniqueComponent() );
        getContainer().addComponent( createUniqueComponent() );
        final IContainerLayout layout = mocksControl_.createMock( IContainerLayout.class );
        layout.layout( getContainer() );
        EasyMock.expectLastCall().times( 2 );
        mocksControl_.replay();
        getContainer().setLayout( layout );

        getContainer().removeComponent( 0 );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link IContainer#removeComponent(int)} method changes the
     * container bounds.
     */
    @Test
    public void testRemoveComponentAtIndex_ChangesContainerBounds()
    {
        getContainer().setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        getContainer().setSurfaceDesign( getContainer().getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        EasyMock.expectLastCall().times( 2 );
        mocksControl_.replay();
        getContainer().addComponentListener( listener );
        final Rectangle originalContainerBounds = getContainer().getBounds();

        IComponent component = null;
        do
        {
            component = createUniqueComponent();
            getContainer().addComponent( component );

        } while( originalContainerBounds.equals( getContainer().getBounds() ) );
        getContainer().removeComponent( getContainer().getComponentCount() - 1 );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link IContainer#removeComponent(int)} method fires a
     * component removed event.
     */
    @Test
    public void testRemoveComponentAtIndex_FiresComponentRemovedEvent()
    {
        final IComponent component = createUniqueComponent();
        getContainer().addComponent( component );
        final IContainerListener listener = mocksControl_.createMock( IContainerListener.class );
        final Capture<ContainerContentChangedEvent> eventCapture = new Capture<>();
        listener.componentRemoved( EasyMock.capture( eventCapture ) );
        mocksControl_.replay();
        getContainer().addContainerListener( listener );

        getContainer().removeComponent( 0 );

        mocksControl_.verify();
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
        getContainer().removeContainerListener( mocksControl_.createMock( IContainerListener.class ) );
    }

    /**
     * Ensures the {@link IContainer#removeContainerListener} removes a listener
     * that is present in the container listener collection.
     */
    @Test
    public void testRemoveContainerListener_Listener_Present()
    {
        final IContainerListener listener = mocksControl_.createMock( IContainerListener.class );
        listener.componentAdded( EasyMock.<@NonNull ContainerContentChangedEvent>notNull() );
        mocksControl_.replay();
        getContainer().addContainerListener( listener );
        getContainer().addComponent( createUniqueComponent() );

        getContainer().removeContainerListener( listener );
        getContainer().addComponent( createUniqueComponent() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link IContainer#setLayout} method changes the container
     * bounds when appropriate.
     */
    @Test
    public void testSetLayout_ChangesContainerBounds()
    {
        getContainer().setSurfaceDesign( getContainer().getOrientation(), TestComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        getContainer().setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        getContainer().addComponent( createUniqueComponent() );
        getContainer().addComponent( createUniqueComponent() );
        final IComponentListener componentListener = mocksControl_.createMock( IComponentListener.class );
        componentListener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        final IContainerListener containerListener = mocksControl_.createMock( IContainerListener.class );
        containerListener.containerLayoutChanged( EasyMock.<@NonNull ContainerEvent>notNull() );
        mocksControl_.replay();
        getContainer().addComponentListener( componentListener );
        getContainer().addContainerListener( containerListener );

        getContainer().setLayout( TestContainerLayouts.createVerticalContainerLayout() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link IContainer#setLayout} method fires a container layout
     * changed event.
     */
    @Test
    public void testSetLayout_FiresContainerLayoutChangedEvent()
    {
        getContainer().setLayout( TestContainerLayouts.createUniqueContainerLayout() );
        final IContainerListener listener = mocksControl_.createMock( IContainerListener.class );
        listener.containerLayoutChanged( EasyMock.<@NonNull ContainerEvent>notNull() );
        mocksControl_.replay();
        getContainer().addContainerListener( listener );

        getContainer().setLayout( TestContainerLayouts.createUniqueContainerLayout() );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link IContainer#setLocation} method changes the location of
     * all child components to reflect the new container location.
     */
    @Test
    public void testSetLocation_ChangesChildComponentLocation()
    {
        final IComponent component = createUniqueComponent();
        getContainer().addComponent( component );
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl_.replay();
        component.addComponentListener( listener );

        getContainer().setLocation( new Point( 1010, 2020 ) );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@link IContainer#setOrigin} method changes the location of
     * all child components to reflect the new container origin.
     */
    @Test
    public void testSetOrigin_ChangesChildComponentLocation()
    {
        final IComponent component = createUniqueComponent();
        getContainer().addComponent( component );
        final IComponentListener listener = mocksControl_.createMock( IComponentListener.class );
        listener.componentBoundsChanged( EasyMock.<@NonNull ComponentEvent>notNull() );
        mocksControl_.replay();
        component.addComponentListener( listener );

        getContainer().setOrigin( new Point( 1010, 2020 ) );

        mocksControl_.verify();
    }
}

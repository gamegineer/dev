/*
 * AbstractDragContextTestCase.java
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
 * Created on Feb 22, 2013 at 9:56:41 PM.
 */

package org.gamegineer.table.core.dnd.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.dnd.IDragContext;
import org.gamegineer.table.core.dnd.IDragSource;
import org.gamegineer.table.core.dnd.IDragStrategy;
import org.gamegineer.table.core.dnd.IDragStrategyFactory;
import org.gamegineer.table.core.dnd.PassiveDragStrategyFactory;
import org.gamegineer.table.core.test.TestComponents;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IDragContext} interface.
 */
public abstract class AbstractDragContextTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default component surface design for use in the fixture. */
    private static final ComponentSurfaceDesign DEFAULT_SURFACE_DESIGN = new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "defaultSurfaceDesign" ), 10, 10 ); //$NON-NLS-1$

    /** The location where the drag-and-drop operation is begun. */
    private Optional<Point> beginDragLocation_;

    /** The drag context under test in the fixture. */
    private Optional<IDragContext> dragContext_;

    /**
     * The drag source associated with the drag context under test in the
     * fixture.
     */
    private Optional<IDragSource> dragSource_;

    /**
     * The container used to hold the components being dragged during the
     * drag-and-drop operation.
     */
    private Optional<IContainer> mobileContainer_;

    /**
     * The collection of component states prior to the beginning of the
     * drag-and-drop operation.
     */
    private Optional<List<PreDragComponentState>> preDragComponentStates_;

    /** The container that ultimately receives the component being dragged. */
    private Optional<IContainer> targetContainer_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractDragContextTestCase}
     * class.
     */
    protected AbstractDragContextTestCase()
    {
        beginDragLocation_ = Optional.empty();
        dragContext_ = Optional.empty();
        dragSource_ = Optional.empty();
        mobileContainer_ = Optional.empty();
        preDragComponentStates_ = Optional.empty();
        targetContainer_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new drag strategy factory that creates drag strategies which do
     * the following:
     * 
     * <ul>
     * <li>Allow the dragged components to be dropped on any container.</li>
     * <li>Initiates the drag operation with the target component and the
     * sibling immediately above it, if present.</li>
     * </ul>
     * 
     * @return A new drag strategy factory; never {@code null}.
     */
    private static IDragStrategyFactory createFixtureDragStrategyFactory()
    {
        return new IDragStrategyFactory()
        {
            @Override
            public IDragStrategy createDragStrategy(
                final IComponent component,
                final IDragStrategy successorDragStrategy )
            {
                return new IDragStrategy()
                {
                    @Override
                    public boolean canDrop(
                        final IContainer dropContainer )
                    {
                        return true;
                    }

                    @Override
                    public List<IComponent> getDragComponents()
                    {
                        // drag the target component and the sibling immediately above it, if present
                        final IContainer container = component.getContainer();
                        assertNotNull( container );
                        final List<IComponent> components = container.getComponents();
                        final ComponentPath componentPath = component.getPath();
                        assertNotNull( componentPath );
                        final int beginIndex = componentPath.getIndex();
                        final int endIndex = ((beginIndex + 1) < components.size()) ? (beginIndex + 1) : (components.size() - 1);
                        return components.subList( beginIndex, endIndex + 1 );
                    }
                };
            }
        };
    }

    /**
     * Creates a new component with unique attributes using the fixture table
     * environment.
     * 
     * @return A new component; never {@code null}.
     */
    private IComponent createUniqueComponent()
    {
        return createUniqueComponent( 0, 0 );
    }

    /**
     * Creates a new component with unique attributes using the fixture table
     * environment and the specified origin.
     * 
     * @param x
     *        The x-coordinate of the component origin.
     * @param y
     *        The y-coordinate of the component origin.
     * 
     * @return A new component; never {@code null}.
     */
    private IComponent createUniqueComponent(
        final int x,
        final int y )
    {
        final IComponent component = TestComponents.createUniqueComponent( getTable().getTableEnvironment() );
        component.setOrigin( new Point( x, y ) );
        component.setSurfaceDesign( component.getOrientation(), DEFAULT_SURFACE_DESIGN );
        return component;
    }

    /**
     * Creates a new container with unique attributes using the fixture table
     * environment with the specified origin.
     * 
     * @param x
     *        The x-coordinate of the container origin.
     * @param y
     *        The y-coordinate of the container origin.
     * 
     * @return A new container; never {@code null}.
     */
    private IContainer createUniqueContainer(
        final int x,
        final int y )
    {
        final IContainer container = TestComponents.createUniqueContainer( getTable().getTableEnvironment() );
        container.setOrigin( new Point( x, y ) );
        container.setSurfaceDesign( container.getOrientation(), DEFAULT_SURFACE_DESIGN );
        return container;
    }

    /**
     * Gets the fixture location where the drag-and-drop operation is begun.
     * 
     * @return The fixture location where the drag-and-drop operation is begun; never {@code null}.
     */
    private Point getBeginDragLocation()
    {
        return beginDragLocation_.get();
    }

    /**
     * Gets the drag context under test in the fixture.
     * 
     * @return The drag context under test in the fixture; never {@code null}.
     */
    protected final IDragContext getDragContext()
    {
        return dragContext_.get();
    }

    /**
     * Gets the fixture drag source.
     * 
     * @return The fixture drag source; never {@code null}.
     */
    private IDragSource getDragSource()
    {
        return dragSource_.get();
    }

    /**
     * Gets the fixture container used to hold the components being dragged during the
     * drag-and-drop operation.
     * 
     * @return The fixture container used to hold the components being dragged during the
     * drag-and-drop operation; never {@code null}.
     */
    private IContainer getMobileContainer()
    {
        return mobileContainer_.get();
    }

    /**
     * Gets the fixture collection of component states prior to the beginning of the
     * drag-and-drop operation.
     * 
     * @return The fixture collection of component states prior to the beginning of the
     * drag-and-drop operation; never {@code null}.
     */
    private List<PreDragComponentState> getPreDragComponentStates()
    {
        return preDragComponentStates_.get();
    }

    /**
     * Gets the table associated with the fixture.
     * 
     * @return The table associated with the fixture; never {@code null}.
     */
    protected abstract ITable getTable();

    /**
     * Gets the fixture container that ultimately receives the component being
     * dragged.
     * 
     * @return The fixture container that ultimately receives the component
     *         being dragged; never {@code null}.
     */
    private IContainer getTargetContainer()
    {
        return targetContainer_.get();
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
        final ITable table = getTable();
        final IDragSource dragSource = table.getExtension( IDragSource.class );
        assertNotNull( dragSource );
        dragSource_ = Optional.of( dragSource );

        final IContainer sourceContainer = createUniqueContainer( 0, 0 );
        sourceContainer.addComponent( createUniqueComponent( 0, 0 ) );
        final IComponent dragComponent1 = createUniqueComponent( 5, 0 );
        sourceContainer.addComponent( dragComponent1 );
        final IComponent dragComponent2 = createUniqueComponent( 10, 0 );
        sourceContainer.addComponent( dragComponent2 );
        sourceContainer.addComponent( createUniqueComponent( 15, 0 ) );
        table.getTabletop().addComponent( sourceContainer );

        final List<PreDragComponentState> preDragComponentStates = new ArrayList<>();
        preDragComponentStates.add( new PreDragComponentState( dragComponent1 ) );
        preDragComponentStates.add( new PreDragComponentState( dragComponent2 ) );
        preDragComponentStates_ = Optional.of( preDragComponentStates );

        final IContainer targetContainer = createUniqueContainer( 50, 50 );
        targetContainer.addComponent( createUniqueComponent( 50, 50 ) );
        targetContainer.addComponent( createUniqueComponent( 55, 50 ) );
        targetContainer_ = Optional.of( targetContainer );
        table.getTabletop().addComponent( targetContainer );

        final Point beginDragLocation = new Point( 0, 0 );
        beginDragLocation_ = Optional.of( beginDragLocation );

        final IDragContext dragContext = dragSource.beginDrag( beginDragLocation, dragComponent1, createFixtureDragStrategyFactory() );
        assertNotNull( dragContext );
        dragContext_ = Optional.of( dragContext );
        final IContainer mobileContainer = dragComponent1.getContainer();
        assertNotNull( mobileContainer );
        mobileContainer_ = Optional.of( mobileContainer );
    }

    /**
     * Ensures the {@link IDragContext#cancel} method adds the components being
     * dragged to their original container at their original indexes.
     */
    @Test
    public void testCancel_AddsComponentsToOriginalContainerAtOriginalIndex()
    {
        final List<PreDragComponentState> preDragComponentStates = getPreDragComponentStates();

        getDragContext().cancel();

        for( final PreDragComponentState preDragComponentState : preDragComponentStates )
        {
            assertSame( preDragComponentState.container, preDragComponentState.component.getContainer() );
            final ComponentPath componentPath = preDragComponentState.component.getPath();
            assertNotNull( componentPath );
            assertEquals( preDragComponentState.index, componentPath.getIndex() );
        }
    }

    /**
     * Ensures the {@link IDragContext#cancel} method ends the active
     * drag-and-drop operation.
     */
    @Test
    public void testCancel_EndsActiveDragAndDropOperation()
    {
        final IComponent component = createUniqueComponent();
        getTable().getTabletop().addComponent( component );

        getDragContext().cancel();

        assertNotNull( getDragSource().beginDrag( new Point( 0, 0 ), component, PassiveDragStrategyFactory.INSTANCE ) );
    }

    /**
     * Ensures the {@link IDragContext#cancel} method removes the mobile
     * container from the table.
     */
    @Test
    public void testCancel_RemovesMobileContainer()
    {
        getDragContext().cancel();

        assertNull( getMobileContainer().getTable() );
    }

    /**
     * Ensures the {@link IDragContext#cancel} method restores the original
     * location of the components being dragged.
     */
    @Test
    public void testCancel_RestoresOriginalDragComponentLocations()
    {
        final IDragContext dragContext = getDragContext();
        final Point beginDragLocation = getBeginDragLocation();
        dragContext.drag( new Point( beginDragLocation.x + 100, beginDragLocation.y + 100 ) );

        dragContext.cancel();

        for( final PreDragComponentState preDragComponentState : getPreDragComponentStates() )
        {
            assertEquals( preDragComponentState.location, preDragComponentState.component.getLocation() );
        }
    }

    /**
     * Ensures the {@link IDragContext#cancel} method throws an exception when
     * the drag context state is illegal because a drag-and-drop operation is
     * not active.
     */
    @Test( expected = IllegalStateException.class )
    public void testCancel_State_Illegal_DragAndDropOperationNotActive()
    {
        final IDragContext dragContext = getDragContext();
        dragContext.cancel();

        dragContext.cancel();
    }

    /**
     * Ensures the {@link IDragContext#drag} method changes the location of the
     * components being dragged by the appropriate amount.
     */
    @Test
    public void testDrag_ChangesDragComponentLocations()
    {
        final IDragContext dragContext = getDragContext();
        final Point beginDragLocation = getBeginDragLocation();

        dragContext.drag( new Point( beginDragLocation.x + 11, beginDragLocation.y + 33 ) );
        dragContext.drag( new Point( beginDragLocation.x + 22, beginDragLocation.y + 44 ) );

        for( final PreDragComponentState preDragComponentState : getPreDragComponentStates() )
        {
            assertEquals( new Point( preDragComponentState.location.x + 22, preDragComponentState.location.y + 44 ), preDragComponentState.component.getLocation() );
        }
    }

    /**
     * Ensures the {@link IDragContext#drag} method throws an exception when the
     * drag context state is illegal because a drag-and-drop operation is not
     * active.
     */
    @Test( expected = IllegalStateException.class )
    public void testDrag_State_Illegal_DragAndDropOperationNotActive()
    {
        final IDragContext dragContext = getDragContext();
        dragContext.cancel();

        dragContext.drag( new Point( 0, 0 ) );
    }

    /**
     * Ensures the {@link IDragContext#drop} method adds the components being
     * dragged to the target container at the top index.
     */
    @Test
    public void testDrop_AddsComponentsToTargetContainerAtTopIndex()
    {
        final IContainer targetContainer = getTargetContainer();
        final List<PreDragComponentState> preDragComponentStates = getPreDragComponentStates();

        getDragContext().drop( new Point( 51, 51 ) );

        int index = 0;
        for( final PreDragComponentState preDragComponentState : preDragComponentStates )
        {
            assertSame( targetContainer, preDragComponentState.component.getContainer() );
            final ComponentPath componentPath = preDragComponentState.component.getPath();
            assertNotNull( componentPath );
            assertEquals( targetContainer.getComponentCount() - (preDragComponentStates.size() - index), componentPath.getIndex() );
            ++index;
        }
    }

    /**
     * Ensures the {@link IDragContext#drop} method changes the location of the
     * components being dragged by the appropriate amount.
     */
    @Test
    public void testDrop_ChangesDragComponentLocations()
    {
        final IDragContext dragContext = getDragContext();
        final Point beginDragLocation = getBeginDragLocation();

        dragContext.drag( new Point( beginDragLocation.x + 11, beginDragLocation.y + 33 ) );
        dragContext.drop( new Point( beginDragLocation.x + 22, beginDragLocation.y + 44 ) );

        for( final PreDragComponentState preDragComponentState : getPreDragComponentStates() )
        {
            assertEquals( new Point( preDragComponentState.location.x + 22, preDragComponentState.location.y + 44 ), preDragComponentState.component.getLocation() );
        }
    }

    /**
     * Ensures the {@link IDragContext#drop} method cancels the drag-and-drop
     * operation when the underlying drag strategy indicates a drop is not
     * allowed on the target container.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDrop_DropNotAllowedOnTargetContainer()
        throws Exception
    {
        IDragContext dragContext = getDragContext();
        dragContext.cancel();
        final IDragStrategyFactory dragStrategyFactory = new IDragStrategyFactory()
        {
            @Override
            public IDragStrategy createDragStrategy(
                final IComponent component,
                final IDragStrategy successorDragStrategy )
            {
                return new IDragStrategy()
                {
                    @Override
                    public boolean canDrop(
                        final IContainer dropContainer )
                    {
                        return false;
                    }

                    @Override
                    public List<IComponent> getDragComponents()
                    {
                        return Collections.singletonList( component );
                    }
                };
            }
        };
        final IComponent component = createUniqueComponent( 1000, 1000 );
        getTable().getTabletop().addComponent( component );
        final PreDragComponentState preDragComponentState = new PreDragComponentState( component );

        dragContext = getDragSource().beginDrag( component.getLocation(), component, dragStrategyFactory );
        assertNotNull( dragContext );
        dragContext.drop( new Point( 0, 0 ) );

        assertEquals( preDragComponentState.container, component.getContainer() );
        final ComponentPath componentPath = component.getPath();
        assertNotNull( componentPath );
        assertEquals( preDragComponentState.index, componentPath.getIndex() );
        assertEquals( preDragComponentState.location, component.getLocation() );
    }

    /**
     * Ensures the {@link IDragContext#drop} method ends the active
     * drag-and-drop operation.
     */
    @Test
    public void testDrop_EndsActiveDragAndDropOperation()
    {
        final IComponent component = createUniqueComponent();
        getTable().getTabletop().addComponent( component );

        getDragContext().drop( new Point( 0, 0 ) );

        assertNotNull( getDragSource().beginDrag( new Point( 0, 0 ), component, PassiveDragStrategyFactory.INSTANCE ) );
    }

    /**
     * Ensures the {@link IDragContext#drop} method removes the mobile container
     * from the table.
     */
    @Test
    public void testDrop_RemovesMobileContainer()
    {
        final Point beginDragLocation = getBeginDragLocation();

        getDragContext().drop( new Point( beginDragLocation.x + 100, beginDragLocation.y + 100 ) );

        assertNull( getMobileContainer().getTable() );
    }

    /**
     * Ensures the {@link IDragContext#drop} method throws an exception when the
     * drag context state is illegal because a drag-and-drop operation is not
     * active.
     */
    @Test( expected = IllegalStateException.class )
    public void testDrop_State_Illegal_DragAndDropOperationNotActive()
    {
        final IDragContext dragContext = getDragContext();
        dragContext.cancel();

        dragContext.drop( new Point( 0, 0 ) );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The state of a component being dragged prior to beginning the
     * drag-and-drop operation.
     */
    @Immutable
    private static final class PreDragComponentState
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The component being dragged. */
        final IComponent component;

        /**
         * The component container before the drag-and-drop operation began.
         */
        final IContainer container;

        /**
         * The component index within its container before the drag-and-drop
         * operation began.
         */
        final int index;

        /**
         * The component location in table coordinates before the drag-and-drop
         * operation began.
         */
        final Point location;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code PreDragComponentState}
         * class.
         * 
         * @param component
         *        The component being dragged; must not be {@code null}.
         */
        PreDragComponentState(
            final IComponent component )
        {
            this.component = component;
            @SuppressWarnings( "hiding" )
            final IContainer container = component.getContainer();
            assertNotNull( container );
            this.container = container;
            final ComponentPath componentPath = component.getPath();
            assertNotNull( componentPath );
            this.index = componentPath.getIndex();
            this.location = component.getLocation();
        }
    }
}

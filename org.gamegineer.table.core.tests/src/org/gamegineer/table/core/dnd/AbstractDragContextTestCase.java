/*
 * AbstractDragContextTestCase.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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

package org.gamegineer.table.core.dnd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.TestComponents;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.dnd.IDragContext} interface.
 */
public abstract class AbstractDragContextTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default component surface design for use in the fixture. */
    private static final ComponentSurfaceDesign DEFAULT_SURFACE_DESIGN = new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "defaultSurfaceDesign" ), 10, 10 ); //$NON-NLS-1$

    /** The location where the drag-and-drop operation is begun. */
    private Point beginDragLocation_;

    /** The drag context under test in the fixture. */
    private IDragContext dragContext_;

    /**
     * The drag source associated with the drag context under test in the
     * fixture.
     */
    private IDragSource dragSource_;

    /**
     * The container used to hold the components being dragged during the
     * drag-and-drop operation.
     */
    private IContainer mobileContainer_;

    /**
     * The collection of component states prior to the beginning of the
     * drag-and-drop operation.
     */
    private List<PreDragComponentState> preDragComponentStates_;

    /** The table associated with the drag context under test in the fixture. */
    private ITable table_;

    /** The container that ultimately receives the component being dragged. */
    private IContainer targetContainer_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractDragContextTestCase}
     * class.
     */
    protected AbstractDragContextTestCase()
    {
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
    /* @NonNull */
    private IDragStrategyFactory createFixtureDragStrategyFactory()
    {
        return new IDragStrategyFactory()
        {
            @Override
            public IDragStrategy createDragStrategy(
                final IComponent component,
                @SuppressWarnings( "unused" )
                final IDragStrategy successorDragStrategy )
            {
                return new IDragStrategy()
                {
                    @Override
                    public boolean canDrop(
                        @SuppressWarnings( "unused" )
                        final IContainer dropContainer )
                    {
                        return true;
                    }

                    @Override
                    public List<IComponent> getDragComponents()
                    {
                        // drag the target component and the sibling immediately above it, if present
                        final List<IComponent> components = component.getContainer().getComponents();
                        final int beginIndex = component.getPath().getIndex();
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
    /* @NonNull */
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
    /* @NonNull */
    private IComponent createUniqueComponent(
        final int x,
        final int y )
    {
        final IComponent component = TestComponents.createUniqueComponent( table_.getTableEnvironment() );
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
    /* @NonNull */
    private IContainer createUniqueContainer(
        final int x,
        final int y )
    {
        final IContainer container = TestComponents.createUniqueContainer( table_.getTableEnvironment() );
        container.setOrigin( new Point( x, y ) );
        container.setSurfaceDesign( container.getOrientation(), DEFAULT_SURFACE_DESIGN );
        return container;
    }

    /**
     * Gets the table associated with the fixture.
     * 
     * @return The table associated with the fixture; never {@code null}.
     */
    /* @NonNull */
    protected abstract ITable getTable();

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
        table_ = getTable();
        assertNotNull( table_ );
        dragSource_ = table_.getExtension( IDragSource.class );
        assertNotNull( dragSource_ );

        final IContainer sourceContainer = createUniqueContainer( 0, 0 );
        sourceContainer.addComponent( createUniqueComponent( 0, 0 ) );
        final IComponent dragComponent1 = createUniqueComponent( 5, 0 );
        sourceContainer.addComponent( dragComponent1 );
        final IComponent dragComponent2 = createUniqueComponent( 10, 0 );
        sourceContainer.addComponent( dragComponent2 );
        sourceContainer.addComponent( createUniqueComponent( 15, 0 ) );
        table_.getTabletop().addComponent( sourceContainer );

        preDragComponentStates_ = new ArrayList<>();
        preDragComponentStates_.add( new PreDragComponentState( dragComponent1 ) );
        preDragComponentStates_.add( new PreDragComponentState( dragComponent2 ) );

        targetContainer_ = createUniqueContainer( 50, 50 );
        targetContainer_.addComponent( createUniqueComponent( 50, 50 ) );
        targetContainer_.addComponent( createUniqueComponent( 55, 50 ) );
        table_.getTabletop().addComponent( targetContainer_ );

        beginDragLocation_ = new Point( 0, 0 );

        dragContext_ = dragSource_.beginDrag( beginDragLocation_, dragComponent1, createFixtureDragStrategyFactory() );
        assertNotNull( dragContext_ );
        mobileContainer_ = dragComponent1.getContainer();
    }

    /**
     * Ensures the {@link IDragContext#cancel} method adds the components being
     * dragged to their original container at their original indexes.
     */
    @Test
    public void testCancel_AddsComponentsToOriginalContainerAtOriginalIndex()
    {
        dragContext_.cancel();

        for( final PreDragComponentState preDragComponentState : preDragComponentStates_ )
        {
            assertSame( preDragComponentState.container, preDragComponentState.component.getContainer() );
            assertEquals( preDragComponentState.index, preDragComponentState.component.getPath().getIndex() );
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
        table_.getTabletop().addComponent( component );

        dragContext_.cancel();

        assertNotNull( dragSource_.beginDrag( new Point( 0, 0 ), component, PassiveDragStrategyFactory.INSTANCE ) );
    }

    /**
     * Ensures the {@link IDragContext#cancel} method removes the mobile
     * container from the table.
     */
    @Test
    public void testCancel_RemovesMobileContainer()
    {
        dragContext_.cancel();

        assertNull( mobileContainer_.getTable() );
    }

    /**
     * Ensures the {@link IDragContext#cancel} method restores the original
     * location of the components being dragged.
     */
    @Test
    public void testCancel_RestoresOriginalDragComponentLocations()
    {
        dragContext_.drag( new Point( beginDragLocation_.x + 100, beginDragLocation_.y + 100 ) );

        dragContext_.cancel();

        for( final PreDragComponentState preDragComponentState : preDragComponentStates_ )
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
        dragContext_.cancel();

        dragContext_.cancel();
    }

    /**
     * Ensures the {@link IDragContext#drag} method changes the location of the
     * components being dragged by the appropriate amount.
     */
    @Test
    public void testDrag_ChangesDragComponentLocations()
    {
        dragContext_.drag( new Point( beginDragLocation_.x + 11, beginDragLocation_.y + 33 ) );
        dragContext_.drag( new Point( beginDragLocation_.x + 22, beginDragLocation_.y + 44 ) );

        for( final PreDragComponentState preDragComponentState : preDragComponentStates_ )
        {
            assertEquals( new Point( preDragComponentState.location.x + 22, preDragComponentState.location.y + 44 ), preDragComponentState.component.getLocation() );
        }
    }

    /**
     * Ensures the {@link IDragContext#drag} method throws an exception when
     * passed a {@code null} location.
     */
    @Test( expected = NullPointerException.class )
    public void testDrag_Location_Null()
    {
        dragContext_.drag( null );
    }

    /**
     * Ensures the {@link IDragContext#drag} method throws an exception when the
     * drag context state is illegal because a drag-and-drop operation is not
     * active.
     */
    @Test( expected = IllegalStateException.class )
    public void testDrag_State_Illegal_DragAndDropOperationNotActive()
    {
        dragContext_.cancel();

        dragContext_.drag( new Point( 0, 0 ) );
    }

    /**
     * Ensures the {@link IDragContext#drop} method adds the components being
     * dragged to the target container at the top index.
     */
    @Test
    public void testDrop_AddsComponentsToTargetContainerAtTopIndex()
    {
        dragContext_.drop( new Point( 51, 51 ) );

        int index = 0;
        for( final PreDragComponentState preDragComponentState : preDragComponentStates_ )
        {
            assertSame( targetContainer_, preDragComponentState.component.getContainer() );
            assertEquals( targetContainer_.getComponentCount() - (preDragComponentStates_.size() - index), preDragComponentState.component.getPath().getIndex() );
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
        dragContext_.drag( new Point( beginDragLocation_.x + 11, beginDragLocation_.y + 33 ) );
        dragContext_.drop( new Point( beginDragLocation_.x + 22, beginDragLocation_.y + 44 ) );

        for( final PreDragComponentState preDragComponentState : preDragComponentStates_ )
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
        dragContext_.cancel();
        final IDragStrategyFactory dragStrategyFactory = new IDragStrategyFactory()
        {
            @Override
            public IDragStrategy createDragStrategy(
                final IComponent component,
                @SuppressWarnings( "unused" )
                final IDragStrategy successorDragStrategy )
            {
                return new IDragStrategy()
                {
                    @Override
                    public boolean canDrop(
                        @SuppressWarnings( "unused" )
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
        table_.getTabletop().addComponent( component );
        final PreDragComponentState preDragComponentState = new PreDragComponentState( component );

        dragContext_ = dragSource_.beginDrag( component.getLocation(), component, dragStrategyFactory );
        dragContext_.drop( new Point( 0, 0 ) );

        assertEquals( preDragComponentState.container, component.getContainer() );
        assertEquals( preDragComponentState.index, component.getPath().getIndex() );
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
        table_.getTabletop().addComponent( component );

        dragContext_.drop( new Point( 0, 0 ) );

        assertNotNull( dragSource_.beginDrag( new Point( 0, 0 ), component, PassiveDragStrategyFactory.INSTANCE ) );
    }

    /**
     * Ensures the {@link IDragContext#drop} method throws an exception when
     * passed a {@code null} location.
     */
    @Test( expected = NullPointerException.class )
    public void testDrop_Location_Null()
    {
        dragContext_.drop( null );
    }

    /**
     * Ensures the {@link IDragContext#drop} method removes the mobile container
     * from the table.
     */
    @Test
    public void testDrop_RemovesMobileContainer()
    {
        dragContext_.drop( new Point( beginDragLocation_.x + 100, beginDragLocation_.y + 100 ) );

        assertNull( mobileContainer_.getTable() );
    }

    /**
     * Ensures the {@link IDragContext#drop} method throws an exception when the
     * drag context state is illegal because a drag-and-drop operation is not
     * active.
     */
    @Test( expected = IllegalStateException.class )
    public void testDrop_State_Illegal_DragAndDropOperationNotActive()
    {
        dragContext_.cancel();

        dragContext_.drop( new Point( 0, 0 ) );
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
            /* @NonNull */
            @SuppressWarnings( "hiding" )
            final IComponent component )
        {
            assert component != null;

            this.component = component;
            this.container = component.getContainer();
            this.index = component.getPath().getIndex();
            this.location = component.getLocation();
        }
    }
}

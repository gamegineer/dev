/*
 * AbstractDragContextTestCase.java
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
 * Created on Feb 22, 2013 at 9:56:41 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import java.awt.Point;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.IDragContext} interface.
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

    /** The component being dragged. */
    private IComponent dragComponent_;

    /** The drag context under test in the fixture. */
    private IDragContext dragContext_;

    /**
     * The container used to hold the component being dragged during the
     * drag-and-drop operation.
     */
    private IContainer mobileContainer_;

    /**
     * The original index of the component being dragged within the source
     * container.
     */
    @SuppressWarnings( "unused" )
    private int originalDragComponentIndex_;

    /** The original location of the component being dragged. */
    private Point originalDragComponentLocation_;

    /** The container that originally holds the component being dragged. */
    private IContainer sourceContainer_;

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
     * Creates the drag context to be tested.
     * 
     * @param location
     *        The beginning drag location in table coordinates; must not be
     *        {@code null}.
     * @param component
     *        The component from which the drag-and-drop operation will begin;
     *        must not be {@code null}.
     * 
     * @return The drag context to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code location} or {@code component} is {@code null}.
     */
    /* @NonNull */
    protected abstract IDragContext createDragContext(
        /* @NonNull */
        Point location,
        /* @NonNull */
        IComponent component )
        throws Exception;

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
     * environment with the specified origin.
     * 
     * @param x
     *        The x-coordinate of the container origin.
     * @param y
     *        The y-coordinate of the container origin.
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

        sourceContainer_ = createUniqueContainer( 0, 0 );
        sourceContainer_.addComponent( createUniqueComponent( 0, 0 ) );
        dragComponent_ = createUniqueComponent( 5, 0 );
        sourceContainer_.addComponent( dragComponent_ );
        sourceContainer_.addComponent( createUniqueComponent( 10, 0 ) );
        table_.getTabletop().addComponent( sourceContainer_ );

        targetContainer_ = createUniqueContainer( 50, 50 );
        targetContainer_.addComponent( createUniqueComponent( 50, 50 ) );
        targetContainer_.addComponent( createUniqueComponent( 55, 50 ) );
        table_.getTabletop().addComponent( targetContainer_ );

        beginDragLocation_ = new Point( 0, 0 );
        originalDragComponentIndex_ = dragComponent_.getPath().getIndex();
        originalDragComponentLocation_ = dragComponent_.getLocation();

        dragContext_ = createDragContext( beginDragLocation_, dragComponent_ );
        assertNotNull( dragContext_ );
        mobileContainer_ = dragComponent_.getContainer();
    }

    /**
     * Ensures the {@link IDragContext#cancel} method adds the component being
     * dragged to the source container at its original index.
     */
    @Test
    public void testCancel_AddsComponentToSourceContainerAtOriginalIndex()
    {
        dragContext_.cancel();

        assertSame( sourceContainer_, dragComponent_.getContainer() );

        // FIXME: Current table API doesn't allow for insertion of a component at an arbitrary index within a container.
        //assertEquals( originalDragComponentIndex_, dragComponent_.getPath().getIndex() );
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

        assertNotNull( table_.beginDrag( new Point( 0, 0 ), component ) );
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
     * location of the component being dragged.
     */
    @Test
    public void testCancel_RestoresOriginalDragComponentLocation()
    {
        dragContext_.drag( new Point( originalDragComponentLocation_.x + 100, originalDragComponentLocation_.y + 100 ) );

        dragContext_.cancel();

        assertEquals( originalDragComponentLocation_, dragComponent_.getLocation() );
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
     * component being dragged by the appropriate amount.
     */
    @Test
    public void testDrag_ChangesDragComponentLocation()
    {
        dragContext_.drag( new Point( beginDragLocation_.x + 11, beginDragLocation_.y + 33 ) );
        dragContext_.drag( new Point( beginDragLocation_.x + 22, beginDragLocation_.y + 44 ) );

        assertEquals( new Point( originalDragComponentLocation_.x + 22, originalDragComponentLocation_.y + 44 ), dragComponent_.getLocation() );
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
     * Ensures the {@link IDragContext#drop} method adds the component being
     * dragged to the target container at the top index.
     */
    @Test
    public void testDrop_AddsComponentToTargetContainerAtTopIndex()
    {
        dragContext_.drop( new Point( 51, 51 ) );

        assertSame( targetContainer_, dragComponent_.getContainer() );
        assertEquals( targetContainer_.getComponentCount() - 1, dragComponent_.getPath().getIndex() );
    }

    /**
     * Ensures the {@link IDragContext#drop} method changes the location of the
     * component being dragged by the appropriate amount.
     */
    @Test
    public void testDrop_ChangesDragComponentLocation()
    {
        dragContext_.drag( new Point( beginDragLocation_.x + 11, beginDragLocation_.y + 33 ) );
        dragContext_.drop( new Point( beginDragLocation_.x + 22, beginDragLocation_.y + 44 ) );

        assertEquals( new Point( originalDragComponentLocation_.x + 22, originalDragComponentLocation_.y + 44 ), dragComponent_.getLocation() );
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

        assertNotNull( table_.beginDrag( new Point( 0, 0 ), component ) );
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
}

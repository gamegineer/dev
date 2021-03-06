/*
 * AbstractDragSourceTestCase.java
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
 * Created on Mar 16, 2013 at 8:24:11 PM.
 */

package org.gamegineer.table.core.dnd.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import java.awt.Point;
import java.util.List;
import java.util.Optional;
import org.easymock.EasyMock;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.dnd.IDragSource;
import org.gamegineer.table.core.dnd.IDragStrategy;
import org.gamegineer.table.core.dnd.IDragStrategyFactory;
import org.gamegineer.table.core.dnd.NullDragStrategyFactory;
import org.gamegineer.table.core.dnd.PassiveDragStrategyFactory;
import org.gamegineer.table.core.test.TestComponents;
import org.gamegineer.table.core.test.TestContainerLayouts;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IDragSource} interface.
 */
public abstract class AbstractDragSourceTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The drag source under test in the fixture. */
    private Optional<IDragSource> dragSource_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractDragSourceTestCase}
     * class.
     */
    protected AbstractDragSourceTestCase()
    {
        dragSource_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new component with unique attributes using the fixture table
     * environment.
     * 
     * @return A new component.
     */
    private IComponent createUniqueComponent()
    {
        return TestComponents.createUniqueComponent( getTable().getTableEnvironment() );
    }

    /**
     * Creates a new container with unique attributes using the fixture table
     * environment.
     * 
     * @return A new container.
     */
    private IContainer createUniqueContainer()
    {
        return TestComponents.createUniqueContainer( getTable().getTableEnvironment() );
    }

    /**
     * Gets the drag source under test in the fixture.
     * 
     * @return The drag source under test in the fixture.
     */
    protected final IDragSource getDragSource()
    {
        return dragSource_.get();
    }

    /**
     * Gets the table associated with the fixture.
     * 
     * @return The table associated with the fixture.
     */
    protected abstract ITable getTable();

    /**
     * Gets the tabletop associated with the fixture.
     * 
     * @return The tabletop associated with the fixture.
     */
    private IContainer getTabletop()
    {
        return getTable().getTabletop();
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
        final IDragSource dragSource = getTable().getExtension( IDragSource.class );
        assertNotNull( dragSource );
        dragSource_ = Optional.of( dragSource );
    }

    /**
     * Ensures the {@link IDragSource#beginDrag} method begins a drag-and-drop
     * operation by moving the target component to a new container used
     * specifically for dragging but does not change the component location.
     */
    @Test
    public void testBeginDrag_BeginsDragAndDropOperation()
    {
        final IContainer tabletop = getTabletop();
        final IComponent component = createUniqueComponent();
        tabletop.addComponent( component );
        final Point originalComponentLocation = component.getLocation();

        assertNotNull( getDragSource().beginDrag( new Point( 0, 0 ), component, PassiveDragStrategyFactory.INSTANCE ) );

        assertEquals( originalComponentLocation, component.getLocation() );
        assertNotSame( tabletop, component.getContainer() );
    }

    /**
     * Ensures the {@link IDragSource#beginDrag} method throws an exception when
     * passed an illegal component that has no container.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testBeginDrag_Component_Illegal_NoContainer()
    {
        getDragSource().beginDrag( new Point( 0, 0 ), getTabletop(), EasyMock.createMock( IDragStrategyFactory.class ) );
    }

    /**
     * Ensures the {@link IDragSource#beginDrag} method throws an exception when
     * passed an illegal component that does not exist in the table.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testBeginDrag_Component_Illegal_NotExistsInTable()
    {
        getDragSource().beginDrag( new Point( 0, 0 ), createUniqueComponent(), EasyMock.createMock( IDragStrategyFactory.class ) );
    }

    /**
     * Ensures the {@link IDragSource#beginDrag} method preserves the location
     * of each component to be dragged when their original container uses a
     * dynamic layout.
     */
    @Test
    public void testBeginDrag_PreservesOriginalComponentLocationsWhenContainerUsesDynamicLayout()
    {
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
                        return true;
                    }

                    @Override
                    public List<IComponent> getDragComponents()
                    {
                        final IContainer container = component.getContainer();
                        assert container != null;
                        return container.getComponents();
                    }
                };
            }
        };
        final IContainer container = createUniqueContainer();
        container.setLayout( TestContainerLayouts.createHorizontalContainerLayout() );
        final IComponent component1 = createUniqueComponent();
        container.addComponent( component1 );
        final Point originalComponentLocation1 = component1.getLocation();
        final IComponent component2 = createUniqueComponent();
        container.addComponent( component2 );
        final Point originalComponentLocation2 = component2.getLocation();
        assertFalse( originalComponentLocation1.equals( originalComponentLocation2 ) );
        getTabletop().addComponent( container );

        assertNotNull( getDragSource().beginDrag( new Point( 0, 0 ), component1, dragStrategyFactory ) );

        assertEquals( originalComponentLocation1, component1.getLocation() );
        assertEquals( originalComponentLocation2, component2.getLocation() );
    }

    /**
     * Ensures the {@link IDragSource#beginDrag} method does not return
     * {@code null} when the underlying drag strategy indicates a drag-and-drop
     * operation is allowed.
     */
    @Test
    public void testBeginDrag_ReturnValue_NonNull()
    {
        final IComponent component = createUniqueComponent();
        getTabletop().addComponent( component );

        assertNotNull( getDragSource().beginDrag( new Point( 0, 0 ), component, PassiveDragStrategyFactory.INSTANCE ) );
    }

    /**
     * Ensures the {@link IDragSource#beginDrag} method returns {@code null}
     * when the underlying drag strategy indicates a drag-and-drop operation is
     * disallowed.
     */
    @Test
    public void testBeginDrag_ReturnValue_Null()
    {
        final IComponent component = createUniqueComponent();
        getTabletop().addComponent( component );

        assertNull( getDragSource().beginDrag( new Point( 0, 0 ), component, NullDragStrategyFactory.INSTANCE ) );
    }

    /**
     * Ensures the {@link IDragSource#beginDrag} method throws an exception when
     * the table state is illegal because a drag-and-drop operation is active.
     */
    @Test( expected = IllegalStateException.class )
    public void testBeginDrag_State_Illegal_DragAndDropOperationActive()
    {
        final IContainer tabletop = getTabletop();
        final IDragSource dragSource = getDragSource();
        final IComponent component1 = createUniqueComponent();
        tabletop.addComponent( component1 );
        final IComponent component2 = createUniqueComponent();
        tabletop.addComponent( component2 );

        assertNotNull( dragSource.beginDrag( new Point( 0, 0 ), component1, PassiveDragStrategyFactory.INSTANCE ) );
        dragSource.beginDrag( new Point( 0, 0 ), component2, PassiveDragStrategyFactory.INSTANCE );
    }
}

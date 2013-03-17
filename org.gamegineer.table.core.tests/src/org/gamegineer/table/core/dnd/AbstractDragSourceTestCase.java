/*
 * AbstractDragSourceTestCase.java
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
 * Created on Mar 16, 2013 at 8:24:11 PM.
 */

package org.gamegineer.table.core.dnd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import java.awt.Point;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.easymock.EasyMock;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IComponentStrategy;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.TestComponentStrategies;
import org.gamegineer.table.core.TestComponents;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.dnd.IDragSource} interface.
 */
public abstract class AbstractDragSourceTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The drag source under test in the fixture. */
    private IDragSource dragSource_;

    /** The table for use in the fixture. */
    private ITable table_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractDragSourceTestCase}
     * class.
     */
    protected AbstractDragSourceTestCase()
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
        return TestComponents.createUniqueComponent( table_.getTableEnvironment() );
    }

    /**
     * Creates a new component with unique attributes using the fixture table
     * environment and the specified drag strategy factory.
     * 
     * @param dragStrategyFactory
     *        The drag strategy factory; must not be {@code null}.
     * 
     * @return A new component; never {@code null}.
     */
    /* @NonNull */
    private IComponent createUniqueComponent(
        /* @NonNull */
        final IDragStrategyFactory dragStrategyFactory )
    {
        assert dragStrategyFactory != null;

        final IComponentStrategy delegate = TestComponentStrategies.createUniqueComponentStrategy();
        final IComponentStrategy componentStrategy = new IComponentStrategy()
        {
            @Override
            public Point getDefaultLocation()
            {
                return delegate.getDefaultLocation();
            }

            @Override
            public ComponentOrientation getDefaultOrientation()
            {
                return delegate.getDefaultOrientation();
            }

            @Override
            public Point getDefaultOrigin()
            {
                return delegate.getDefaultOrigin();
            }

            @Override
            public Map<ComponentOrientation, ComponentSurfaceDesign> getDefaultSurfaceDesigns()
            {
                return delegate.getDefaultSurfaceDesigns();
            }

            @Override
            public IDragStrategyFactory getDragStrategyFactory()
            {
                return dragStrategyFactory;
            }

            @Override
            public <T> T getExtension(
                final Class<T> type )
            {
                return delegate.getExtension( type );
            }

            @Override
            public ComponentStrategyId getId()
            {
                return delegate.getId();
            }

            @Override
            public Collection<ComponentOrientation> getSupportedOrientations()
            {
                return delegate.getSupportedOrientations();
            }
        };
        return TestComponents.createUniqueComponent( table_.getTableEnvironment(), componentStrategy );
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
    }

    /**
     * Ensures the {@link IDragSource#beginDrag} method begins a drag-and-drop
     * operation by moving the target component to a new container used
     * specifically for dragging but does not change the component location.
     */
    @Test
    public void testBeginDrag_BeginsDragAndDropOperation()
    {
        final IComponent component = createUniqueComponent();
        table_.getTabletop().addComponent( component );
        final Point originalComponentLocation = component.getLocation();

        assertNotNull( dragSource_.beginDrag( new Point( 0, 0 ), component ) );

        assertEquals( originalComponentLocation, component.getLocation() );
        assertNotSame( table_.getTabletop(), component.getContainer() );
    }

    /**
     * Ensures the {@link IDragSource#beginDrag} method throws an exception when
     * passed an illegal component that has no container.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testBeginDrag_Component_Illegal_NoContainer()
    {
        dragSource_.beginDrag( new Point( 0, 0 ), table_.getTabletop() );
    }

    /**
     * Ensures the {@link IDragSource#beginDrag} method throws an exception when
     * passed an illegal component that does not exist in the table.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testBeginDrag_Component_Illegal_NotExistsInTable()
    {
        dragSource_.beginDrag( new Point( 0, 0 ), createUniqueComponent() );
    }

    /**
     * Ensures the {@link IDragSource#beginDrag} method throws an exception when
     * passed a {@code null} component.
     */
    @Test( expected = NullPointerException.class )
    public void testBeginDrag_Component_Null()
    {
        dragSource_.beginDrag( new Point( 0, 0 ), null );
    }

    /**
     * Ensures the {@link IDragSource#beginDrag} method throws an exception when
     * passed a {@code null} location.
     */
    @Test( expected = NullPointerException.class )
    public void testBeginDrag_Location_Null()
    {
        dragSource_.beginDrag( null, EasyMock.createMock( IComponent.class ) );
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
        table_.getTabletop().addComponent( component );

        assertNotNull( dragSource_.beginDrag( new Point( 0, 0 ), component ) );
    }

    /**
     * Ensures the {@link IDragSource#beginDrag} method returns {@code null}
     * when the underlying drag strategy indicates a drag-and-drop operation is
     * disallowed.
     */
    @Test
    public void testBeginDrag_ReturnValue_Null()
    {
        final IDragStrategyFactory dragStrategyFactory = new IDragStrategyFactory()
        {
            @Override
            public IDragStrategy createDragStrategy(
                @SuppressWarnings( "unused" )
                final IComponent component )
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
                        return Collections.emptyList();
                    }
                };
            }
        };
        final IComponent component = createUniqueComponent( dragStrategyFactory );
        table_.getTabletop().addComponent( component );

        assertNull( dragSource_.beginDrag( new Point( 0, 0 ), component ) );
    }

    /**
     * Ensures the {@link IDragSource#beginDrag} method throws an exception when
     * the table state is illegal because a drag-and-drop operation is active.
     */
    @Test( expected = IllegalStateException.class )
    public void testBeginDrag_State_Illegal_DragAndDropOperationActive()
    {
        final IComponent component1 = createUniqueComponent();
        table_.getTabletop().addComponent( component1 );
        final IComponent component2 = createUniqueComponent();
        table_.getTabletop().addComponent( component2 );

        assertNotNull( dragSource_.beginDrag( new Point( 0, 0 ), component1 ) );
        dragSource_.beginDrag( new Point( 0, 0 ), component2 );
    }
}

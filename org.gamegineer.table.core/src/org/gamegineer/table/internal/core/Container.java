/*
 * Container.java
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
 * Created on Jul 4, 2012 at 8:47:42 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import java.awt.Point;
import java.util.List;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;

/**
 * Implementation of {@link org.gamegineer.table.core.IContainer}.
 */
@ThreadSafe
abstract class Container
    extends Component
    implements IContainer
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Container} class.
     * 
     * @param tableEnvironment
     *        The table environment associated with the container; must not be
     *        {@code null}.
     */
    Container(
        /* @NonNull */
        final TableEnvironment tableEnvironment )
    {
        super( tableEnvironment );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.IContainer#getComponent(int)
     */
    @Override
    public abstract Component getComponent(
        int index );

    /**
     * Gets the component within this container's bounds (including the
     * container itself) at the specified location.
     * 
     * <p>
     * If two or more components occupy the specified location, the top-most
     * component will be returned.
     * </p>
     * 
     * @param location
     *        The location in table coordinates; must not be {@code null}.
     * 
     * @return The component within this container's bounds at the specified
     *         location or {@code null} if no component is at that location.
     */
    @GuardedBy( "getLock()" )
    /* @Nullable */
    final IComponent getComponent(
        /* @NonNull */
        final Point location )
    {
        assert location != null;
        assert getLock().isHeldByCurrentThread();

        if( getBounds().contains( location ) )
        {
            if( !hasComponents() )
            {
                return this;
            }

            final int index = getComponentIndex( location );
            if( index != -1 )
            {
                final Component component = getComponent( index );
                return (component instanceof Container) ? ((Container)component).getComponent( location ) : component;
            }
        }

        return null;
    }

    /**
     * Gets the component in this container at the specified path.
     * 
     * @param paths
     *        The collection of constituent component paths of the overall
     *        component path; must not be {@code null} and must not be empty.
     * 
     * @return The component in this container at the specified path; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If no component exists at the specified path.
     */
    @GuardedBy( "getLock()" )
    /* @NonNull */
    final Component getComponent(
        /* @NonNull */
        final List<ComponentPath> paths )
    {
        assert paths != null;
        assert !paths.isEmpty();
        assert getLock().isHeldByCurrentThread();

        final ComponentPath path = paths.get( 0 );
        final Component component = getComponent( path.getIndex() );
        if( paths.size() == 1 )
        {
            return component;
        }

        assertArgumentLegal( component instanceof Container, "paths", NonNlsMessages.Container_getComponent_path_notExists ); //$NON-NLS-1$
        return ((Container)component).getComponent( paths.subList( 1, paths.size() ) );
    }

    /**
     * Gets the index of the specified component in this container.
     * 
     * @param component
     *        The component; must not be {@code null}.
     * 
     * @return The index of the specified component in this container.
     */
    @GuardedBy( "getLock()" )
    abstract int getComponentIndex(
        /* @NonNull */
        Component component );

    /**
     * Gets the index of the component in this container at the specified
     * location.
     * 
     * <p>
     * This method follows the same rules defined by
     * {@link #getComponent(Point)}.
     * </p>
     * 
     * @param location
     *        The location in table coordinates; must not be {@code null}.
     * 
     * @return The index of the component in this container at the specified
     *         location or -1 if no component in this container is at that
     *         location.
     */
    @GuardedBy( "getLock()" )
    abstract int getComponentIndex(
        /* @NonNull */
        Point location );

    /**
     * Indicates the container has at least one component.
     * 
     * @return {@code true} if the container has at least one component;
     *         otherwise {@code false}.
     */
    abstract boolean hasComponents();
}

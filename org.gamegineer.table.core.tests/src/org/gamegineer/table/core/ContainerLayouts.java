/*
 * ContainerLayouts.java
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
 * Created on May 10, 2012 at 9:51:49 PM.
 */

package org.gamegineer.table.core;

import java.awt.Point;
import java.util.List;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

/**
 * A factory for creating various types of container layouts suitable for
 * testing.
 */
@ThreadSafe
public final class ContainerLayouts
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerLayouts} class.
     */
    private ContainerLayouts()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new container layout that lays out each component in the
     * horizontal direction with the right edge of each component immediately
     * next to the left edge of the following component.
     * 
     * @return A new container layout; never {@code null}.
     */
    /* @NonNull */
    public static IContainerLayout createHorizontalContainerLayout()
    {
        return new AbstractContainerLayout()
        {
            @Override
            public void layout(
                final IContainer container )
            {
                final Point location = container.getOrigin();
                for( final IComponent component : container.getComponents() )
                {
                    component.setLocation( location );
                    location.translate( component.getSize().width, 0 );
                }
            }
        };
    }

    /**
     * Creates a new container layout that is unique.
     * 
     * @return A new container layout; never {@code null}.
     */
    /* @NonNull */
    public static IContainerLayout createUniqueContainerLayout()
    {
        return new AbstractContainerLayout()
        {
            @Override
            public void layout(
                @SuppressWarnings( "unused" )
                final IContainer container )
            {
                // do nothing
            }
        };
    }

    /**
     * Creates a new container layout that lays out each component in the
     * vertical direction with the bottom edge of each component immediately
     * next to the top edge of the following component.
     * 
     * @return A new container layout; never {@code null}.
     */
    /* @NonNull */
    public static IContainerLayout createVerticalContainerLayout()
    {
        return new AbstractContainerLayout()
        {
            @Override
            public void layout(
                final IContainer container )
            {
                final Point location = container.getOrigin();
                for( final IComponent component : container.getComponents() )
                {
                    component.setLocation( location );
                    location.translate( 0, component.getSize().height );
                }
            }
        };
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Superclass for implementations of {@link IContainerLayout} created by
     * this factory.
     */
    @Immutable
    private static abstract class AbstractContainerLayout
        implements IContainerLayout
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code AbstractContainerLayout}
         * class.
         */
        AbstractContainerLayout()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.IContainerLayout#getComponentIndex(org.gamegineer.table.core.IContainer, java.awt.Point)
         */
        @Override
        public int getComponentIndex(
            final IContainer container,
            final Point location )
        {
            final List<IComponent> components = container.getComponents();
            for( int index = components.size() - 1; index >= 0; --index )
            {
                if( components.get( index ).getBounds().contains( location ) )
                {
                    return index;
                }
            }

            return -1;
        }
    }
}

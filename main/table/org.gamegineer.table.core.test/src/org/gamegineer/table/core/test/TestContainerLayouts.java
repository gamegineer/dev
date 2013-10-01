/*
 * TestContainerLayouts.java
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
 * Created on May 10, 2012 at 9:51:49 PM.
 */

package org.gamegineer.table.core.test;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Point;
import java.util.concurrent.atomic.AtomicLong;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.IContainerLayout;
import org.gamegineer.table.core.IContainerLayoutRegistry;
import org.gamegineer.table.internal.core.test.Activator;

/**
 * A factory for creating various types of container layouts suitable for
 * testing.
 */
@ThreadSafe
public final class TestContainerLayouts
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The next unique container layout identifier. */
    private static final AtomicLong nextContainerLayoutId_ = new AtomicLong();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TestContainerLayouts} class.
     */
    private TestContainerLayouts()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Clones the specified container layout.
     * 
     * @param containerLayout
     *        The container layout to clone; must not be {@code null}.
     * 
     * @return A new container layout; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code containerLayout} is {@code null}.
     */
    /* @NonNull */
    public static IContainerLayout cloneContainerLayout(
        /* @NonNull */
        final IContainerLayout containerLayout )
    {
        assertArgumentNotNull( containerLayout, "containerLayout" ); //$NON-NLS-1$

        return new IContainerLayout()
        {
            @Override
            public ContainerLayoutId getId()
            {
                return containerLayout.getId();
            }

            @Override
            public void layout(
                final IContainer container )
            {
                containerLayout.layout( container );
            }
        };
    }

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
        return registerContainerLayout( new AbstractContainerLayout( getUniqueContainerLayoutId() )
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
        } );
    }

    /**
     * Creates a new container layout with a unique identifier.
     * 
     * @return A new container layout; never {@code null}.
     */
    /* @NonNull */
    public static IContainerLayout createUniqueContainerLayout()
    {
        return registerContainerLayout( new AbstractContainerLayout( getUniqueContainerLayoutId() )
        {
            @Override
            public void layout(
                @SuppressWarnings( "unused" )
                final IContainer container )
            {
                // do nothing
            }
        } );
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
        return registerContainerLayout( new AbstractContainerLayout( getUniqueContainerLayoutId() )
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
        } );
    }

    /**
     * Gets a unique container layout identifier.
     * 
     * @return A unique container layout identifier; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static ContainerLayoutId getUniqueContainerLayoutId()
    {
        return ContainerLayoutId.fromString( String.format( "container-layout-%1$d", nextContainerLayoutId_.incrementAndGet() ) ); //$NON-NLS-1$
    }

    /**
     * Registers the specified container layout with the container layout
     * registry.
     * 
     * @param containerLayout
     *        The container layout; must not be {@code null}.
     * 
     * @return The registered container layout; never {@code null}.
     */
    /* @NonNull */
    private static IContainerLayout registerContainerLayout(
        /* @NonNull */
        final IContainerLayout containerLayout )
    {
        assert containerLayout != null;

        final IContainerLayoutRegistry containerLayoutRegistry = Activator.getDefault().getContainerLayoutRegistry();
        assert containerLayoutRegistry != null;
        containerLayoutRegistry.registerObject( containerLayout );
        return containerLayout;
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
        // Fields
        // ==================================================================

        /** The container layout identifier. */
        private final ContainerLayoutId id_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code AbstractContainerLayout}
         * class.
         * 
         * @param id
         *        The container layout identifier; must not be {@code null}.
         */
        AbstractContainerLayout(
            /* @NonNull */
            final ContainerLayoutId id )
        {
            assert id != null;

            id_ = id;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.IContainerLayout#getId()
         */
        @Override
        public final ContainerLayoutId getId()
        {
            return id_;
        }
    }
}

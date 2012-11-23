/*
 * TestComponentSurfaceDesigns.java
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
 * Created on Apr 7, 2012 at 9:32:55 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.atomic.AtomicLong;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.core.Activator;

/**
 * A factory for creating various types of component surface designs suitable
 * for testing.
 */
@ThreadSafe
public final class TestComponentSurfaceDesigns
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The next unique component surface design identifier. */
    private static final AtomicLong nextComponentSurfaceDesignId_ = new AtomicLong();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TestComponentSurfaceDesigns}
     * class.
     */
    private TestComponentSurfaceDesigns()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Clones the specified component surface design.
     * 
     * @param componentSurfaceDesign
     *        The component surface design to clone; must not be {@code null}.
     * 
     * @return A new component surface design; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code componentSurfaceDesign} is {@code null}.
     */
    /* @NonNull */
    public static ComponentSurfaceDesign cloneComponentSurfaceDesign(
        /* @NonNull */
        final ComponentSurfaceDesign componentSurfaceDesign )
    {
        assertArgumentNotNull( componentSurfaceDesign, "componentSurfaceDesign" ); //$NON-NLS-1$

        return new ComponentSurfaceDesign( componentSurfaceDesign.getId(), componentSurfaceDesign.getSize() );
    }

    /**
     * Creates a new component surface design with a unique identifier and a
     * default size.
     * 
     * @return A new component surface design; never {@code null}.
     */
    /* @NonNull */
    public static ComponentSurfaceDesign createUniqueComponentSurfaceDesign()
    {
        return createUniqueComponentSurfaceDesign( 100, 100 );
    }

    /**
     * Creates a new component surface design with a unique identifier and the
     * specified size.
     * 
     * @param width
     *        The component surface design width in table coordinates; must not
     *        be negative.
     * @param height
     *        The component surface design height in table coordinates; must not
     *        be negative.
     * 
     * @return A new component surface design; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code width} or {@code height} is negative.
     */
    /* @NonNull */
    public static ComponentSurfaceDesign createUniqueComponentSurfaceDesign(
        final int width,
        final int height )
    {
        return registerComponentSurfaceDesign( new ComponentSurfaceDesign( getUniqueComponentSurfaceDesignId(), width, height ) );
    }

    /**
     * Gets a unique component surface design identifier.
     * 
     * @return A unique component surface design identifier; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static ComponentSurfaceDesignId getUniqueComponentSurfaceDesignId()
    {
        return ComponentSurfaceDesignId.fromString( String.format( "component-surface-design-%1$d", nextComponentSurfaceDesignId_.incrementAndGet() ) ); //$NON-NLS-1$
    }

    /**
     * Registers the specified component surface design with the component
     * surface design registry.
     * 
     * @param componentSurfaceDesign
     *        The component surface design; must not be {@code null}.
     * 
     * @return The registered component surface design; never {@code null}.
     */
    /* @NonNull */
    private static ComponentSurfaceDesign registerComponentSurfaceDesign(
        /* @NonNull */
        final ComponentSurfaceDesign componentSurfaceDesign )
    {
        assert componentSurfaceDesign != null;

        final IComponentSurfaceDesignRegistry componentSurfaceDesignRegistry = Activator.getDefault().getComponentSurfaceDesignRegistry();
        assert componentSurfaceDesignRegistry != null;
        componentSurfaceDesignRegistry.registerObject( componentSurfaceDesign );
        return componentSurfaceDesign;
    }
}

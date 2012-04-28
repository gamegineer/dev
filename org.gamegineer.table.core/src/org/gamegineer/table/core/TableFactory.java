/*
 * TableFactory.java
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
 * Created on Oct 6, 2009 at 11:05:05 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.core.ComponentSurfaceDesign;
import org.gamegineer.table.internal.core.Table;

/**
 * A factory for creating table components.
 */
@ThreadSafe
public final class TableFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableFactory} class.
     */
    private TableFactory()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new component surface design.
     * 
     * @param id
     *        The component surface design identifier; must not be {@code null}.
     * @param size
     *        The component surface design size in table coordinates; no
     *        component may be negative.
     * 
     * @return A new component surface design; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If any component of {@code size} is negative.
     * @throws java.lang.NullPointerException
     *         If {@code id} or {@code size} is {@code null}.
     */
    /* @NonNull */
    public static IComponentSurfaceDesign createComponentSurfaceDesign(
        /* @NonNull */
        final ComponentSurfaceDesignId id,
        /* @NonNull */
        final Dimension size )
    {
        assertArgumentNotNull( size, "size" ); //$NON-NLS-1$

        return createComponentSurfaceDesign( id, size.width, size.height );
    }

    /**
     * Creates a new component surface design.
     * 
     * @param id
     *        The component surface design identifier; must not be {@code null}.
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
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @NonNull */
    public static IComponentSurfaceDesign createComponentSurfaceDesign(
        /* @NonNull */
        final ComponentSurfaceDesignId id,
        final int width,
        final int height )
    {
        return new ComponentSurfaceDesign( id, width, height );
    }

    /**
     * Creates a new table.
     * 
     * @return A new table; never {@code null}.
     */
    /* @NonNull */
    public static ITable createTable()
    {
        return new Table();
    }
}

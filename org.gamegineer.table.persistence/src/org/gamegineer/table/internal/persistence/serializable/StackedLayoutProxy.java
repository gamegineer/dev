/*
 * StackedLayoutProxy.java
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
 * Created on May 12, 2012 at 11:30:23 PM.
 */

package org.gamegineer.table.internal.persistence.serializable;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import java.io.Serializable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.core.StackedLayout;

/**
 * A serializable proxy for the {@link StackedLayout} class.
 */
@NotThreadSafe
public final class StackedLayoutProxy
    implements Serializable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 8222381717882639580L;

    /**
     * The number of components per stack level.
     * 
     * @serial
     */
    private int componentsPerStackLevel_;

    /**
     * The offset in the x-direction of each stack level in table coordinates.
     * 
     * @serial
     */
    private int stackLevelOffsetX_;

    /**
     * The offset in the y-direction of each stack level in table coordinates.
     * 
     * @serial
     */
    private int stackLevelOffsetY_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StackedLayoutProxy} class.
     */
    @SuppressWarnings( "unused" )
    private StackedLayoutProxy()
    {
        componentsPerStackLevel_ = 0;
        stackLevelOffsetX_ = 0;
        stackLevelOffsetY_ = 0;
    }

    /**
     * Initializes a new instance of the {@code StackedLayoutProxy} class from
     * the specified {@code StackedLayout} instance.
     * 
     * @param containerLayout
     *        The {@code StackedLayout} instance; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code containerLayout} is {@code null}.
     */
    public StackedLayoutProxy(
        /* @NonNull */
        final StackedLayout containerLayout )
    {
        assertArgumentNotNull( containerLayout, "containerLayout" ); //$NON-NLS-1$

        componentsPerStackLevel_ = containerLayout.getComponentsPerStackLevel();
        final Dimension stackLevelOffset = containerLayout.getStackLevelOffset();
        stackLevelOffsetX_ = stackLevelOffset.width;
        stackLevelOffsetY_ = stackLevelOffset.height;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets a replacement object for this instance after it has been
     * deserialized.
     * 
     * @return A replacement object for this instance after it has been
     *         deserialized; never {@code null}.
     */
    /* @NonNull */
    private Object readResolve()
    {
        return new StackedLayout( componentsPerStackLevel_, stackLevelOffsetX_, stackLevelOffsetY_ );
    }
}

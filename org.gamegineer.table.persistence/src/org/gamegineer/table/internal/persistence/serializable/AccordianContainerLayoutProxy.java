/*
 * AccordianContainerLayoutProxy.java
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
 * Created on May 12, 2012 at 11:07:55 PM.
 */

package org.gamegineer.table.internal.persistence.serializable;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import java.io.Serializable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.core.AccordianContainerLayout;

/**
 * A serializable proxy for the {@link AccordianContainerLayout} class.
 */
@NotThreadSafe
public final class AccordianContainerLayoutProxy
    implements Serializable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 935885609483952880L;

    /**
     * The offset of each component in the x-direction in table coordinates.
     * 
     * @serial
     */
    private int offsetX_;

    /**
     * The offset of each component in the x-direction in table coordinates.
     * 
     * @serial
     */
    private int offsetY_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AccordianContainerLayoutProxy}
     * class.
     */
    @SuppressWarnings( "unused" )
    private AccordianContainerLayoutProxy()
    {
        offsetX_ = 0;
        offsetY_ = 0;
    }

    /**
     * Initializes a new instance of the {@code AccordianContainerLayoutProxy}
     * class from the specified {@code AccordianContainerLayout} instance.
     * 
     * @param containerLayout
     *        The {@code AccordianContainerLayout} instance; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code containerLayout} is {@code null}.
     */
    public AccordianContainerLayoutProxy(
        /* @NonNull */
        final AccordianContainerLayout containerLayout )
    {
        assertArgumentNotNull( containerLayout, "containerLayout" ); //$NON-NLS-1$

        final Dimension offset = containerLayout.getOffset();
        offsetX_ = offset.width;
        offsetY_ = offset.height;
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
        return new AccordianContainerLayout( offsetX_, offsetY_ );
    }
}

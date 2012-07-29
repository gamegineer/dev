/*
 * AbsoluteContainerLayoutProxy.java
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
 * Created on Jul 5, 2012 at 8:47:17 PM.
 */

package org.gamegineer.table.internal.persistence.serializable;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.io.Serializable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.core.AbsoluteContainerLayout;

/**
 * A serializable proxy for the {@link AbsoluteContainerLayout} class.
 */
@NotThreadSafe
public final class AbsoluteContainerLayoutProxy
    implements Serializable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -4653915858593619643L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbsoluteContainerLayoutProxy}
     * class.
     */
    @SuppressWarnings( "unused" )
    private AbsoluteContainerLayoutProxy()
    {
    }

    /**
     * Initializes a new instance of the {@code AbsoluteContainerLayoutProxy}
     * class from the specified {@code AbsoluteContainerLayout} instance.
     * 
     * @param containerLayout
     *        The {@code AbsoluteContainerLayout} instance; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code containerLayout} is {@code null}.
     */
    public AbsoluteContainerLayoutProxy(
        /* @NonNull */
        final AbsoluteContainerLayout containerLayout )
    {
        assertArgumentNotNull( containerLayout, "containerLayout" ); //$NON-NLS-1$
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
    @SuppressWarnings( "static-method" )
    private Object readResolve()
    {
        return new AbsoluteContainerLayout();
    }
}
/*
 * AbsoluteLayoutProxy.java
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
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.internal.core.AbsoluteLayout;

/**
 * A serializable proxy for the {@link AbsoluteLayout} class.
 */
@NotThreadSafe
public final class AbsoluteLayoutProxy
    implements Serializable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -4653915858593619643L;

    /**
     * The container layout identifier.
     * 
     * @serial
     */
    private String id_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbsoluteLayoutProxy} class.
     */
    @SuppressWarnings( "unused" )
    private AbsoluteLayoutProxy()
    {
        id_ = null;
    }

    /**
     * Initializes a new instance of the {@code AbsoluteLayoutProxy} class from
     * the specified {@code AbsoluteLayout} instance.
     * 
     * @param containerLayout
     *        The {@code AbsoluteLayout} instance; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code containerLayout} is {@code null}.
     */
    public AbsoluteLayoutProxy(
        /* @NonNull */
        final AbsoluteLayout containerLayout )
    {
        assertArgumentNotNull( containerLayout, "containerLayout" ); //$NON-NLS-1$

        id_ = containerLayout.getId().toString();
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
        return new AbsoluteLayout( ContainerLayoutId.fromString( id_ ) );
    }
}

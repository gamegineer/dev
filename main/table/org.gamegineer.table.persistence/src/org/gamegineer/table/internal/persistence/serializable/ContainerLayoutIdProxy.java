/*
 * ContainerLayoutIdProxy.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Aug 11, 2012 at 8:24:17 PM.
 */

package org.gamegineer.table.internal.persistence.serializable;

import java.io.Serializable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.core.ContainerLayoutId;

/**
 * A serializable proxy for the {@code ContainerLayoutId} class.
 */
@NotThreadSafe
public final class ContainerLayoutIdProxy
    implements Serializable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 3886747828720997230L;

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
     * Initializes a new instance of the {@code ContainerLayoutIdProxy} class.
     */
    @SuppressWarnings( "unused" )
    private ContainerLayoutIdProxy()
    {
        id_ = ""; //$NON-NLS-1$
    }

    /**
     * Initializes a new instance of the {@code ContainerLayoutIdProxy} class
     * from the specified {@code ContainerLayoutId} instance.
     * 
     * @param containerLayoutId
     *        The {@code ContainerLayoutId} instance; must not be {@code null}.
     */
    public ContainerLayoutIdProxy(
        final ContainerLayoutId containerLayoutId )
    {
        id_ = containerLayoutId.toString();
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
    private Object readResolve()
    {
        return ContainerLayoutId.fromString( id_ );
    }
}

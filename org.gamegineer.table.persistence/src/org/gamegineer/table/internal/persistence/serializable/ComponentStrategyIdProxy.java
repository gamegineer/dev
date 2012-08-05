/*
 * ComponentStrategyIdProxy.java
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
 * Created on Aug 4, 2012 at 11:02:29 PM.
 */

package org.gamegineer.table.internal.persistence.serializable;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.io.Serializable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.core.ComponentStrategyId;

/**
 * A serializable proxy for the {@code ComponentStrategyId} class.
 */
@NotThreadSafe
public final class ComponentStrategyIdProxy
    implements Serializable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -4046233409368655909L;

    /**
     * The component strategy identifier.
     * 
     * @serial
     */
    private String id_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentStrategyIdProxy} class.
     */
    @SuppressWarnings( "unused" )
    private ComponentStrategyIdProxy()
    {
        id_ = null;
    }

    /**
     * Initializes a new instance of the {@code ComponentStrategyIdProxy} class
     * from the specified {@code ComponentStrategyId} instance.
     * 
     * @param componentStrategyId
     *        The {@code ComponentStrategyId} instance; must not be {@code null}
     *        .
     * 
     * @throws java.lang.NullPointerException
     *         If {@code componentStrategyId} is {@code null}.
     */
    public ComponentStrategyIdProxy(
        /* @NonNull */
        final ComponentStrategyId componentStrategyId )
    {
        assertArgumentNotNull( componentStrategyId, "componentStrategyId" ); //$NON-NLS-1$

        id_ = componentStrategyId.toString();
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
        return ComponentStrategyId.fromString( id_ );
    }
}

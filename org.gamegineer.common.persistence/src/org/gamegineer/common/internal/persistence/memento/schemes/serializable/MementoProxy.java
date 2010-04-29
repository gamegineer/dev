/*
 * MementoProxy.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Jul 1, 2008 at 12:35:44 AM.
 */

package org.gamegineer.common.internal.persistence.memento.schemes.serializable;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.internal.persistence.memento.Memento;

/**
 * A serializable proxy for the {@code Memento} class.
 */
@NotThreadSafe
public final class MementoProxy
    implements Serializable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -6656702327504675392L;

    /**
     * The attribute collection.
     * 
     * @serial
     */
    private Map<String, Object> attributes_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MementoProxy} class.
     */
    @SuppressWarnings( "unused" )
    private MementoProxy()
    {
        attributes_ = null;
    }

    /**
     * Initializes a new instance of the {@code MementoProxy} class from the
     * specified {@code Memento} instance.
     * 
     * @param memento
     *        The {@code Memento} instance; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code memento} is {@code null}.
     */
    public MementoProxy(
        /* @NonNull */
        final Memento memento )
    {
        assertArgumentNotNull( memento, "memento" ); //$NON-NLS-1$

        attributes_ = new HashMap<String, Object>( memento.getAttributes() );
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
        return new Memento( attributes_ );
    }
}

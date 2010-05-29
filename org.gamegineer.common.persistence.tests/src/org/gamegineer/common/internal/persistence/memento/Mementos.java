/*
 * Mementos.java
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
 * Created on May 28, 2010 at 10:15:16 PM.
 */

package org.gamegineer.common.internal.persistence.memento;

import java.util.HashMap;
import java.util.Map;
import net.jcip.annotations.ThreadSafe;

/**
 * A factory for creating various types of mementos suitable for testing.
 */
@ThreadSafe
final class Mementos
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Mementos} class.
     */
    private Mementos()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates an attribute collection for a memento.
     * 
     * @param size
     *        The size of the attribute collection; must be greater than or
     *        equal to zero.
     * @param firstIndex
     *        The starting index of the counter that is appended to the name and
     *        value of each attribute; must be greater than or equal to zero.
     * 
     * @return An attribute collection for a memento; never {@code null}.
     */
    /* @NonNull */
    private static Map<String, Object> createAttributes(
        final int size,
        final int firstIndex )
    {
        assert size >= 0;
        assert firstIndex >= 0;

        final Map<String, Object> attributes = new HashMap<String, Object>();
        for( int index = 0; index < size; ++index )
        {
            final String name = "name" + (index + firstIndex); //$NON-NLS-1$
            final String value = "value" + (index + firstIndex); //$NON-NLS-1$
            attributes.put( name, value );
        }
        return attributes;
    }

    /**
     * Creates a new memento with an attribute collection defined by the
     * specified values.
     * 
     * @param size
     *        The size of the attribute collection; must be greater than or
     *        equal to zero.
     * @param firstIndex
     *        The starting index of the counter that is appended to the name and
     *        value of each attribute; must be greater than or equal to zero.
     * 
     * @return A new memento; never {@code null}.
     */
    /* @NonNull */
    static Memento createMemento(
        final int size,
        final int firstIndex )
    {
        return new Memento( createAttributes( size, firstIndex ) );
    }
}

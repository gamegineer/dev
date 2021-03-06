/*
 * IterableUtils.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Apr 25, 2013 at 10:30:19 PM.
 */

package org.gamegineer.common.core.util;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * A collection of useful methods for working with instances of {@link Iterable}
 * .
 */
@ThreadSafe
public final class IterableUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code IterableUtils} class.
     */
    private IterableUtils()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets a reversed view of the specified list.
     * 
     * @param <T>
     *        The type of the list elements.
     * 
     * @param list
     *        The list.
     * 
     * @return A reversed view of the specified list.
     */
    public static <T> Iterable<T> reverse(
        final List<T> list )
    {
        return new Iterable<T>()
        {
            @NonNullByDefault( {} )
            @Override
            public Iterator<T> iterator()
            {
                return new Iterator<T>()
                {
                    private final ListIterator<T> iterator_ = list.listIterator( list.size() );

                    @Override
                    public boolean hasNext()
                    {
                        return iterator_.hasPrevious();
                    }

                    @Override
                    public T next()
                    {
                        return iterator_.previous();
                    }

                    @Override
                    public void remove()
                    {
                        iterator_.remove();
                    }
                };
            }
        };
    }
}

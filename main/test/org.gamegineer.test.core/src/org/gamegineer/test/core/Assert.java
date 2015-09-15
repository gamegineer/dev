/*
 * Assert.java
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
 * Created on Nov 25, 2008 at 9:21:03 PM.
 */

package org.gamegineer.test.core;

import static org.junit.Assert.fail;
import java.util.Collection;
import net.jcip.annotations.ThreadSafe;

/**
 * A collection of custom assertion methods.
 */
@ThreadSafe
public final class Assert
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Assert} class.
     */
    private Assert()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Asserts that the specified collection is immutable.
     * 
     * @param <T> The type of the collection elements. 
     * 
     * @param c
     *        The collection to be tested; must not be {@code null}.
     * @param prototype 
     *        A prototype value that is legal to add to the collection; must
     *        not be {@code null}. 
     */
    public static <T> void assertImmutableCollection(
        final Collection<T> c,
        final T prototype )
    {
        try
        {
            c.add( prototype );
            fail( "Expected collection to be immutable." ); //$NON-NLS-1$
        }
        catch( @SuppressWarnings( "unused" ) final UnsupportedOperationException e )
        {
            // expected
        }
    }
}

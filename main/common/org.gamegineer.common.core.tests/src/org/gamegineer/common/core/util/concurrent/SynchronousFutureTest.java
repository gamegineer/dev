/*
 * SynchronousFutureTest.java
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
 * Created on Oct 10, 2011 at 8:11:39 PM.
 */

package org.gamegineer.common.core.util.concurrent;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.eclipse.jdt.annotation.Nullable;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of the {@link SynchronousFuture}
 * class.
 */
public final class SynchronousFutureTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code SynchronousFutureTest} class.
     */
    public SynchronousFutureTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link SynchronousFuture#get()} method throws an exception
     * when the future is constructed with an exception.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGet_HasException()
        throws Exception
    {
        final Throwable expectedValue = new Exception();
        final Future<@Nullable Object> future = new SynchronousFuture<>( expectedValue );

        try
        {
            future.get();
            fail( "expected ExecutionException" ); //$NON-NLS-1$
        }
        catch( final ExecutionException e )
        {
            assertSame( expectedValue, e.getCause() );
        }
    }

    /**
     * Ensures the {@link SynchronousFuture#get()} method returns the expected
     * value when the future is constructed with a result.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGet_HasResult()
        throws Exception
    {
        final Object expectedValue = new Object();
        final Future<@Nullable Object> future = new SynchronousFuture<>( expectedValue );

        final Object actualValue = future.get();

        assertSame( expectedValue, actualValue );
    }
}

/*
 * MockPredicate.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Oct 23, 2009 at 11:26:56 PM.
 */

package org.gamegineer.common.core.util;

import java.util.concurrent.atomic.AtomicInteger;
import net.jcip.annotations.ThreadSafe;

/**
 * Mock implementation of {@link org.gamegineer.common.core.util.IPredicate}.
 * 
 * @param <T>
 *        The type of object evaluated by the predicate.
 */
@ThreadSafe
public class MockPredicate<T>
    implements IPredicate<T>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The count of calls made to the {@code evaluate} method. */
    private final AtomicInteger evaluateCallCount_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockPredicate} class.
     */
    public MockPredicate()
    {
        evaluateCallCount_ = new AtomicInteger( 0 );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.util.IPredicate#evaluate(java.lang.Object)
     */
    public boolean evaluate(
        @SuppressWarnings( "unused" )
        final T obj )
    {
        evaluateCallCount_.incrementAndGet();

        return false;
    }

    /**
     * Gets the count of calls made to the {@code evaluate} method.
     * 
     * @return The count of calls made to the {@code evaluate} method.
     */
    public int getEvaluateCallCount()
    {
        return evaluateCallCount_.get();
    }
}

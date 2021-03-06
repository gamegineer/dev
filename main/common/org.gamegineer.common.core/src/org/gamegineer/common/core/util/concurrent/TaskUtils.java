/*
 * TaskUtils.java
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
 * Created on Jun 10, 2008 at 9:45:34 PM.
 */

package org.gamegineer.common.core.util.concurrent;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A collection of methods useful for working with asynchronous tasks.
 * 
 * @author Brian Goetz
 */
@ThreadSafe
public final class TaskUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TaskUtils} class.
     */
    private TaskUtils()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Launders the unknown cause of a task execution exception.
     * 
     * <p>
     * Many of the Java concurrent programming framework task execution methods
     * may throw an {@code ExecutionException}. In such cases, the cause of the
     * execution exception will fall into one of three categories: a checked
     * exception thrown by the task, a {@code RuntimeException}, or an
     * {@code Error}. Developers are expected to test for known checked
     * exceptions and re-throw them. Unknown causes are then passed to this
     * method to be properly handled.
     * </p>
     * 
     * <p>
     * The following example shows the typical usage of this method:
     * </p>
     * 
     * <pre>
     * try
     * {
     *     return future.get(); // 'future' is of type {@literal java.util.concurrent.Future&lt;V&gt;}
     * }
     * catch( ExecutionException e )
     * {
     *     Throwable cause = e.getCause();
     *     if( cause instanceof MyCheckedException )
     *     {
     *         throw (MyCheckedException)cause;
     *     }
     *     else
     *     {
     *         throw TaskUtils.launderThrowable( cause );
     *     }
     * }
     * </pre>
     * 
     * @param t
     *        The unknown cause of a task execution exception.
     * 
     * @return The unchecked exception which caused the task execution
     *         exception.
     * 
     * @throws java.lang.Error
     *         If the cause was an error.
     * @throws java.lang.IllegalStateException
     *         If the cause was a checked exception.
     */
    public static RuntimeException launderThrowable(
        final @Nullable Throwable t )
    {
        if( t instanceof RuntimeException )
        {
            return (RuntimeException)t;
        }
        else if( t instanceof Error )
        {
            throw (Error)t;
        }

        throw new IllegalStateException( NonNlsMessages.TaskUtils_launderThrowable_unexpectedCheckedException, t );
    }
}

/*
 * Debug.java
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
 * Created on Feb 29, 2008 at 10:38:03 PM.
 */

package org.gamegineer.common.core.runtime;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.service.debug.DebugOptions;
import org.gamegineer.common.internal.core.Services;

/**
 * A collection of methods useful for debug tracing.
 * 
 * <p>
 * This class is intended to be extended by clients. Typically, a client will
 * write their own bundle- or package-specific version of this class and simply
 * inherit the static methods it provides. For example, a client-specific
 * version might include fields to enumerate the names of debug options
 * available in the bundle.
 * </p>
 */
@ThreadSafe
public abstract class Debug
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Debug} class.
     */
    protected Debug()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Indicates the specified debug option is enabled.
     * 
     * @param option
     *        The debug option; may be {@code null}.
     * 
     * @return {@code true} if the specified debug option is enabled; otherwise
     *         {@code false}.
     */
    private static boolean isDebugOptionEnabled(
        /* @Nullable */
        final String option )
    {
        if( option == null )
        {
            return true;
        }

        final DebugOptions debugOptions = Services.getDebugOptions();
        if( debugOptions == null )
        {
            return false;
        }

        return debugOptions.getBooleanOption( option, false );
    }

    /**
     * Prints a message to standard output.
     * 
     * @param message
     *        The message; may be {@code null}.
     */
    private static void trace(
        /* @Nullable */
        final String message )
    {
        assert message != null;

        final StringBuilder sb = new StringBuilder();
        sb.append( new Date( System.currentTimeMillis() ) );
        sb.append( " - [" ); //$NON-NLS-1$
        sb.append( Thread.currentThread().getName() );
        sb.append( "] " ); //$NON-NLS-1$
        sb.append( message );
        System.out.println( sb.toString() );
    }

    /**
     * Prints a message to standard output for the specified debug option.
     * 
     * @param option
     *        The debug option that is used to control whether the trace message
     *        is printed or {@code null} to always print the trace message.
     * @param message
     *        The message; may be {@code null}.
     */
    public static void trace(
        /* @Nullable */
        final String option,
        /* @Nullable */
        final String message )
    {
        if( isDebugOptionEnabled( option ) )
        {
            trace( message );
        }
    }

    /**
     * Prints a message with an associated exception to standard output for the
     * specified debug option.
     * 
     * @param option
     *        The debug option that is used to control whether the trace message
     *        is printed or {@code null} to always print the trace message.
     * @param message
     *        The message to print before the exception; may be {@code null}.
     * @param exception
     *        The exception; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code exception} is {@code null}.
     */
    public static void trace(
        /* @Nullable */
        final String option,
        /* @Nullable */
        final String message,
        /* @NonNull */
        final Exception exception )
    {
        assertArgumentNotNull( exception, "exception" ); //$NON-NLS-1$

        if( isDebugOptionEnabled( option ) )
        {
            final StringWriter sw = new StringWriter();
            final PrintWriter pw = new PrintWriter( sw );
            if( message != null )
            {
                pw.print( message );
                pw.print( ": " ); //$NON-NLS-1$
            }
            exception.printStackTrace( pw );
            trace( sw.toString() );
        }
    }
}

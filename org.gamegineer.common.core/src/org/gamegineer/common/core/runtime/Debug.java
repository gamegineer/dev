/*
 * Debug.java
 * Copyright 2008 Gamegineer.org
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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import org.eclipse.osgi.service.debug.DebugOptions;
import org.gamegineer.common.internal.core.Services;

/**
 * A collection of methods useful for debug tracing.
 * 
 * <p>
 * This class is intended to be extended by clients. Typically, a client will
 * write their own bundle- or package-specific version of this class and simply
 * inherit the static methods it provides. For example, a client-specific
 * version might include fields to indicate which types of debug tracing are
 * currently enabled.
 * </p>
 */
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
     * Gets the platform debug options service.
     * 
     * @return The platform debug options service; never {@code null}.
     */
    /* @NonNull */
    protected static DebugOptions getDebugOptions()
    {
        return Services.getDefault().getDebugOptions();
    }

    /**
     * Prints a message to standard output.
     * 
     * @param message
     *        The message; may be {@code null}.
     */
    public static void trace(
        /* @Nullable */
        final String message )
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( new Date( System.currentTimeMillis() ) );
        sb.append( " - [" ); //$NON-NLS-1$
        sb.append( Thread.currentThread().getName() );
        sb.append( "] " ); //$NON-NLS-1$
        sb.append( message );
        System.out.println( sb.toString() );
    }

    /**
     * Prints an exception to standard output.
     * 
     * @param exception
     *        The exception; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code exception} is {@code null}.
     */
    public static void trace(
        /* @NonNull */
        final Exception exception )
    {
        trace( null, exception );
    }

    /**
     * Prints a message with an associated exception to standard output.
     * 
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
        final String message,
        /* @NonNull */
        final Exception exception )
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

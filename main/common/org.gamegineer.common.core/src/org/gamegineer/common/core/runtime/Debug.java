/*
 * Debug.java
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
 * Created on Feb 29, 2008 at 10:38:03 PM.
 */

package org.gamegineer.common.core.runtime;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.osgi.service.debug.DebugOptions;
import org.eclipse.osgi.service.debug.DebugTrace;
import org.gamegineer.common.internal.core.Activator;

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
    // Fields
    // ======================================================================

    /** The default debug trace used when no other debug trace is available. */
    private static final DebugTrace DEFAULT_DEBUG_TRACE = new NullDebugTrace();

    /** The symbolic name of the bundle associated with this instance. */
    private final String bundleSymbolicName_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Debug} class.
     * 
     * @param bundleSymbolicName
     *        The symbolic name of the bundle associated with this instance.
     */
    protected Debug(
        final String bundleSymbolicName )
    {
        bundleSymbolicName_ = bundleSymbolicName;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the debug trace for the bundle associated with this instance.
     * 
     * @return The debug trace for the bundle associated with this instance.
     */
    private DebugTrace getDebugTrace()
    {
        final DebugOptions debugOptions = Activator.getDefault().getDebugOptions();
        if( debugOptions == null )
        {
            return DEFAULT_DEBUG_TRACE;
        }

        return debugOptions.newDebugTrace( bundleSymbolicName_, Debug.class );
    }

    /**
     * Traces a message for the specified debug option.
     * 
     * @param option
     *        The debug option that is used to control whether the trace message
     *        is printed or {@code null} to always print the trace message.
     * @param message
     *        The message.
     */
    public final void trace(
        final @Nullable String option,
        final @Nullable String message )
    {
        getDebugTrace().trace( option, message );
    }

    /**
     * Traces a message and exception for the specified debug option.
     * 
     * @param option
     *        The debug option that is used to control whether the trace message
     *        is printed or {@code null} to always print the trace message.
     * @param message
     *        The message.
     * @param error
     *        The exception.
     */
    public final void trace(
        final @Nullable String option,
        final @Nullable String message,
        final @Nullable Throwable error )
    {
        getDebugTrace().trace( option, message, error );
    }

    /**
     * Traces a stack dump for the currently executing method for the specified
     * debug option.
     * 
     * @param option
     *        The debug option that is used to control whether the trace message
     *        is printed or {@code null} to always print the trace message.
     */
    public final void traceDumpStack(
        final @Nullable String option )
    {
        getDebugTrace().traceDumpStack( option );
    }

    /**
     * Traces an entry message for the currently executing method for the
     * specified debug option.
     * 
     * @param option
     *        The debug option that is used to control whether the trace message
     *        is printed or {@code null} to always print the trace message.
     */
    public final void traceEntry(
        final @Nullable String option )
    {
        getDebugTrace().traceEntry( option );
    }

    /**
     * Traces an entry message for the currently executing method with the
     * specified argument for the specified debug option.
     * 
     * @param option
     *        The debug option that is used to control whether the trace message
     *        is printed or {@code null} to always print the trace message.
     * @param methodArgument
     *        The method argument.
     */
    public final void traceEntry(
        final @Nullable String option,
        final @Nullable Object methodArgument )
    {
        getDebugTrace().traceEntry( option, methodArgument );
    }

    /**
     * Traces an entry message for the currently executing method with the
     * specified arguments for the specified debug option.
     * 
     * @param option
     *        The debug option that is used to control whether the trace message
     *        is printed or {@code null} to always print the trace message.
     * @param methodArguments
     *        The method arguments.
     */
    public final void traceEntry(
        final @Nullable String option,
        final @Nullable Object... methodArguments )
    {
        getDebugTrace().traceEntry( option, methodArguments );
    }

    /**
     * Traces an exit message for the currently executing method for the
     * specified debug option.
     * 
     * @param option
     *        The debug option that is used to control whether the trace message
     *        is printed or {@code null} to always print the trace message.
     */
    public final void traceExit(
        final @Nullable String option )
    {
        getDebugTrace().traceExit( option );
    }

    /**
     * Traces an exit message for the currently executing method with the
     * specified result for the specified debug option.
     * 
     * @param option
     *        The debug option that is used to control whether the trace message
     *        is printed or {@code null} to always print the trace message.
     * @param result
     *        The method result.
     */
    public final void traceExit(
        final @Nullable String option,
        final @Nullable Object result )
    {
        getDebugTrace().traceExit( option, result );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Null implementation of {@link DebugTrace}.
     */
    @Immutable
    private static final class NullDebugTrace
        implements DebugTrace
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code NullDebugTrace} class.
         */
        NullDebugTrace()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.eclipse.osgi.service.debug.DebugTrace#trace(java.lang.String, java.lang.String)
         */
        @Override
        public void trace(
            final @Nullable String option,
            final @Nullable String message )
        {
            warn();
        }

        /*
         * @see org.eclipse.osgi.service.debug.DebugTrace#trace(java.lang.String, java.lang.String, java.lang.Throwable)
         */
        @Override
        public void trace(
            final @Nullable String option,
            final @Nullable String message,
            final @Nullable Throwable error )
        {
            warn();
        }

        /*
         * @see org.eclipse.osgi.service.debug.DebugTrace#traceDumpStack(java.lang.String)
         */
        @Override
        public void traceDumpStack(
            final @Nullable String option )
        {
            warn();
        }

        /*
         * @see org.eclipse.osgi.service.debug.DebugTrace#traceEntry(java.lang.String)
         */
        @Override
        public void traceEntry(
            final @Nullable String option )
        {
            warn();
        }

        /*
         * @see org.eclipse.osgi.service.debug.DebugTrace#traceEntry(java.lang.String, java.lang.Object)
         */
        @Override
        public void traceEntry(
            final @Nullable String option,
            final @Nullable Object methodArgument )
        {
            warn();
        }

        /*
         * @see org.eclipse.osgi.service.debug.DebugTrace#traceEntry(java.lang.String, java.lang.Object[])
         */
        @Override
        public void traceEntry(
            final @Nullable String option,
            final Object @Nullable [] methodArguments )
        {
            warn();
        }

        /*
         * @see org.eclipse.osgi.service.debug.DebugTrace#traceExit(java.lang.String)
         */
        @Override
        public void traceExit(
            final @Nullable String option )
        {
            warn();
        }

        /*
         * @see org.eclipse.osgi.service.debug.DebugTrace#traceExit(java.lang.String, java.lang.Object)
         */
        @Override
        public void traceExit(
            final @Nullable String option,
            final @Nullable Object result )
        {
            warn();
        }

        /**
         * Prints a warning to the standard error stream indicating no debug
         * trace is available resulting in the loss of a trace message.
         */
        private static void warn()
        {
            System.err.println( "WARNING: no debug trace available; trace message lost" ); //$NON-NLS-1$
        }
    }
}

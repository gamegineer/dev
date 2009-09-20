/*
 * FrameworkLogHandler.java
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
 * Created on May 25, 2008 at 10:22:30 PM.
 */

package org.gamegineer.common.core.util.logging;

import java.util.logging.ErrorManager;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.framework.log.FrameworkLogEntry;
import org.gamegineer.common.internal.core.Services;

/**
 * A logging handler that publishes records to the Equinox framework log.
 * 
 * <p>
 * This handler only supports configuration through the Gamegineer logging
 * service. It supports the following properties with the specified default
 * values:
 * </p>
 * 
 * <ul>
 * <li>{@code filter} specifies the filter to use (defaults to no filter).</li>
 * <li>{@code level} specifies the default level for the handler (defaults to
 * {@code Level.INFO}).</li>
 * </ul>
 * 
 * <p>
 * Note that this handler does not use the associated formatter. Any formatter
 * set on this class will be remembered but not used.
 * </p>
 */
@NotThreadSafe
public class FrameworkLogHandler
    extends Handler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The hard-coded formatter used by the handler. */
    private final Formatter formatter_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FrameworkLogHandler} class.
     */
    public FrameworkLogHandler()
    {
        formatter_ = new FrameworkLogFormatter();

        setFilter( null );
        setLevel( Level.INFO );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.util.logging.Handler#close()
     */
    @Override
    public void close()
        throws SecurityException
    {
        // Do nothing
    }

    /*
     * @see java.util.logging.Handler#flush()
     */
    @Override
    public void flush()
    {
        // Do nothing
    }

    /**
     * Gets the framework log entry severity associated with the specified log
     * record.
     * 
     * @param record
     *        The log record; must not be {@code null}.
     * 
     * @return The framework log entry severity associated with the specified
     *         log record.
     * 
     * @see org.eclipse.osgi.framework.log.FrameworkLogEntry
     */
    private static int getSeverity(
        /* @NonNull */
        final LogRecord record )
    {
        assert record != null;

        if( record.getLevel() == Level.SEVERE )
        {
            return FrameworkLogEntry.ERROR;
        }
        else if( record.getLevel() == Level.WARNING )
        {
            return FrameworkLogEntry.WARNING;
        }
        else
        {
            return FrameworkLogEntry.INFO;
        }
    }

    /*
     * @see java.util.logging.Handler#publish(java.util.logging.LogRecord)
     */
    @Override
    public synchronized void publish(
        final LogRecord record )
    {
        if( !isLoggable( record ) )
        {
            return;
        }

        final String message;
        try
        {
            message = formatter_.format( record );
        }
        catch( final Exception e )
        {
            reportError( null, e, ErrorManager.FORMAT_FAILURE );
            return;
        }

        final FrameworkLogEntry logEntry = new FrameworkLogEntry( record.getLoggerName(), getSeverity( record ), 0, message, 0, record.getThrown(), null );
        Services.getDefault().getFrameworkLog().log( logEntry );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A log record formatter for the Equinox framework log.
     */
    @ThreadSafe
    private static final class FrameworkLogFormatter
        extends Formatter
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code FrameworkLogFormatter}
         * class.
         */
        FrameworkLogFormatter()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
         */
        @Override
        public String format(
            final LogRecord record )
        {
            final StringBuilder sb = new StringBuilder();
            sb.append( formatMessage( record ) );
            sb.append( " [" ); //$NON-NLS-1$
            if( record.getSourceClassName() != null )
            {
                sb.append( record.getSourceClassName() );
            }
            if( record.getSourceMethodName() != null )
            {
                sb.append( ", " ); //$NON-NLS-1$
                sb.append( record.getSourceMethodName() );
            }
            sb.append( "]" ); //$NON-NLS-1$
            return sb.toString();
        }
    }
}

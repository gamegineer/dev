/*
 * StandardOutputHandler.java
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
 * Created on May 24, 2008 at 11:53:07 PM.
 */

package org.gamegineer.common.core.util.logging;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;
import org.gamegineer.common.internal.core.Debug;

/**
 * A logging handler that publishes records to standard output ({@code System.out}).
 * 
 * <p>
 * This handler only supports configuration through the Gamegineer logging
 * service. It supports the following properties with the specified default
 * values:
 * </p>
 * 
 * <ul>
 * <li>{@code encoding} specifies the name of the character set encoding to use
 * (defaults to the default platform encoding).</li>
 * <li>{@code filter} specifies the filter to use (defaults to no filter).</li>
 * <li>{@code formatter} specifies the formatter to use (defaults to
 * {@code java.util.logging.SimpleFormatter}).</li>
 * <li>{@code level} specifies the default level for the handler (defaults to
 * {@code Level.INFO}).</li>
 * </ul>
 */
public class StandardOutputHandler
    extends StreamHandler
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StandardOutputHandler} class.
     */
    public StandardOutputHandler()
    {
        super( System.out, new SimpleFormatter() );

        try
        {
            setEncoding( null );
        }
        catch( final UnsupportedEncodingException e )
        {
            if( Debug.UTILITY_LOGGING )
            {
                Debug.trace( "Default platform encoding is not supported.", e ); //$NON-NLS-1$
            }
        }

        setFilter( null );
        setLevel( Level.INFO );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.util.logging.StreamHandler#close()
     */
    @Override
    public void close()
        throws SecurityException
    {
        flush();
    }

    /*
     * @see java.util.logging.StreamHandler#publish(java.util.logging.LogRecord)
     */
    @Override
    public void publish(
        final LogRecord record )
    {
        super.publish( record );
        flush();
    }
}

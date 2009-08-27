/*
 * DefaultDisplay.java
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
 * Created on Sep 27, 2008 at 9:42:09 PM.
 */

package org.gamegineer.client.internal.ui.console.displays;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.client.ui.console.IDisplay;

/**
 * Implementation of {@link org.gamegineer.client.ui.console.IDisplay} that uses
 * an arbitrary input stream reader and output stream writer.
 * 
 * <p>
 * Note that this implementation cannot guarantee that the
 * {@code readSecureLine} methods will not echo characters.
 * </p>
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
public final class DefaultDisplay
    implements IDisplay
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The input stream reader. */
    private final SecureBufferedReader m_reader;

    /** The output stream writer. */
    private final PrintWriter m_writer;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DefaultDisplay} class that uses
     * the standard input and output streams.
     */
    public DefaultDisplay()
    {
        this( new InputStreamReader( System.in ), new OutputStreamWriter( System.out ) );
    }

    /**
     * Initializes a new instance of the {@code DefaultDisplay} class using the
     * specified input stream reader and output stream writer.
     * 
     * @param reader
     *        The input stream reader; must not be {@code null}.
     * @param writer
     *        The output stream writer; must not be {@code null}.
     */
    DefaultDisplay(
        /* @NonNull */
        final Reader reader,
        /* @NonNull */
        final Writer writer )
    {
        assert reader != null;
        assert writer != null;

        m_reader = new SecureBufferedReader( reader );
        m_writer = new PrintWriter( writer, true )
        {
            @Override
            public void close()
            {
                // Do nothing
            }
        };
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#flush()
     */
    public void flush()
    {
        m_writer.flush();
    }

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#format(java.lang.String, java.lang.Object[])
     */
    public IDisplay format(
        final String format,
        final Object... args )
    {
        m_writer.format( format, args );
        return this;
    }

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#getReader()
     */
    public Reader getReader()
    {
        return m_reader;
    }

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#getWriter()
     */
    public PrintWriter getWriter()
    {
        return m_writer;
    }

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#readLine()
     */
    public String readLine()
    {
        try
        {
            return m_reader.readLine();
        }
        catch( final IOException e )
        {
            throw new IOError( e );
        }
    }

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#readLine(java.lang.String, java.lang.Object[])
     */
    public String readLine(
        final String format,
        final Object... args )
    {
        format( format, args );
        return readLine();
    }

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#readSecureLine()
     */
    public char[] readSecureLine()
    {
        try
        {
            return m_reader.readSecureLine();
        }
        catch( final IOException e )
        {
            throw new IOError( e );
        }
    }

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#readSecureLine(java.lang.String, java.lang.Object[])
     */
    public char[] readSecureLine(
        final String format,
        final Object... args )
    {
        format( format, args );
        return readSecureLine();
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A buffered reader that provides the ability to read lines in a secure
     * manner.
     */
    private static final class SecureBufferedReader
        extends BufferedReader
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Creates a new instance of the {@code SecureBufferedReader} class.
         * 
         * @param reader
         *        The underlying reader; must not be {@code null}.
         * 
         * @throws java.lang.NullPointerException
         *         If {@code reader} is {@code null}.
         */
        SecureBufferedReader(
            /* @NonNull */
            final Reader reader )
        {
            super( reader );
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see java.io.BufferedReader#close()
         */
        @Override
        public void close()
        {
            // Do nothing
        }

        /**
         * Reads a single line of text from this reader in a secure manner.
         * 
         * @return A character array containing the line read from this reader
         *         not including any line-termination characters, or
         *         {@code null} if the end of stream has been reached.
         * 
         * @throws java.io.IOException
         *         If an I/O error occurs.
         */
        /* @Nullable */
        char[] readSecureLine()
            throws IOException
        {
            final StringBuilder sb = new StringBuilder();

            synchronized( lock )
            {
                boolean isEndOfLine = false, wasLastCharCR = false;
                while( !isEndOfLine )
                {
                    final int ch = read();
                    if( ch == -1 )
                    {
                        if( sb.length() == 0 )
                        {
                            return null;
                        }

                        isEndOfLine = true;
                    }
                    else if( (ch == '\r') && !wasLastCharCR )
                    {
                        mark( 1 );
                        wasLastCharCR = true;
                    }
                    else if( ch == '\n' )
                    {
                        isEndOfLine = true;
                    }
                    else
                    {
                        if( wasLastCharCR )
                        {
                            reset();
                            isEndOfLine = true;
                        }
                        else
                        {
                            sb.append( (char)ch );
                        }
                    }
                }
            }

            final char[] line = new char[ sb.length() ];
            sb.getChars( 0, sb.length(), line, 0 );
            sb.setLength( 0 ); // clear password buffer before returning
            return line;
        }
    }
}

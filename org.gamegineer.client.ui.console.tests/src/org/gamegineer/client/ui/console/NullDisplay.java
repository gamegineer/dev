/*
 * NullDisplay.java
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
 * Created on Jan 16, 2009 at 9:54:07 PM.
 */

package org.gamegineer.client.ui.console;

import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import net.jcip.annotations.Immutable;

/**
 * An implementation of {@link org.gamegineer.client.ui.console.IDisplay} that
 * does nothing.
 * 
 * <p>
 * All input methods of this class always indicate the end of stream has been
 * reached. All output methods simply discard their data.
 * </p>
 * 
 * <p>
 * This class is immutable.
 * </p>
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 */
@Immutable
public final class NullDisplay
    implements IDisplay
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The input stream reader. */
    private final Reader m_reader;

    /** The output stream writer. */
    private final PrintWriter m_writer;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NullDisplay} class.
     */
    public NullDisplay()
    {
        m_reader = new NullReader();
        m_writer = new PrintWriter( new NullWriter() );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#flush()
     */
    public void flush()
    {
        // Do nothing
    }

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#format(java.lang.String, java.lang.Object[])
     */
    public IDisplay format(
        @SuppressWarnings( "unused" )
        final String format,
        @SuppressWarnings( "unused" )
        final Object... args )
    {
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
        return null;
    }

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#readLine(java.lang.String, java.lang.Object[])
     */
    public String readLine(
        @SuppressWarnings( "unused" )
        final String format,
        @SuppressWarnings( "unused" )
        final Object... args )
    {
        return null;
    }

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#readSecureLine()
     */
    public char[] readSecureLine()
    {
        return null;
    }

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#readSecureLine(java.lang.String, java.lang.Object[])
     */
    public char[] readSecureLine(
        @SuppressWarnings( "unused" )
        final String format,
        @SuppressWarnings( "unused" )
        final Object... args )
    {
        return null;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A reader that does nothing.
     */
    private static final class NullReader
        extends Reader
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code NullReader} class.
         */
        NullReader()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see java.io.Reader#close()
         */
        @Override
        public void close()
        {
            // Do nothing
        }

        /*
         * @see java.io.Reader#read(char[], int, int)
         */
        @Override
        public int read(
            @SuppressWarnings( "unused" )
            final char[] cbuf,
            @SuppressWarnings( "unused" )
            final int off,
            @SuppressWarnings( "unused" )
            final int len )
        {
            return -1;
        }
    }

    /**
     * A writer that does nothing.
     */
    private static final class NullWriter
        extends Writer
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code NullWriter} class.
         */
        NullWriter()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see java.io.Writer#close()
         */
        @Override
        public void close()
        {
            // Do nothing
        }

        /*
         * @see java.io.Writer#flush()
         */
        @Override
        public void flush()
        {
            // Do nothing
        }

        /*
         * @see java.io.Writer#write(char[], int, int)
         */
        @Override
        public void write(
            @SuppressWarnings( "unused" )
            final char[] cbuf,
            @SuppressWarnings( "unused" )
            final int off,
            @SuppressWarnings( "unused" )
            final int len )
        {
            // Do nothing
        }
    }
}

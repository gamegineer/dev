/*
 * FakeDisplay.java
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
 * Created on Oct 4, 2008 at 9:33:33 PM.
 */

package org.gamegineer.client.ui.console;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.io.Reader;
import net.jcip.annotations.ThreadSafe;

/**
 * Fake implementation of {@link org.gamegineer.client.ui.console.IDisplay}.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 */
@ThreadSafe
public class FakeDisplay
    implements IDisplay
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The buffer size of the pipe used to connect a reader and writer. */
    private static final int PIPE_SIZE = 8 * 1024;

    /** The input stream reader. */
    private final BufferedReader inputReader_;

    /** The input stream writer. */
    private final PrintWriter inputWriter_;

    /** The output stream reader. */
    private final BufferedReader outputReader_;

    /** The output stream writer. */
    private final PrintWriter outputWriter_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeDisplay} class.
     * 
     * @throws java.io.IOError
     *         If an I/O error occurs.
     */
    public FakeDisplay()
    {
        try
        {
            final PipedWriter inputWriter = new PipedWriter();
            final PipedReader inputReader = new PipedReader( inputWriter, PIPE_SIZE );
            inputWriter_ = new PrintWriter( inputWriter );
            inputReader_ = new BufferedReader( inputReader );
            final PipedReader outputReader = new PipedReader( PIPE_SIZE );
            final PipedWriter outputWriter = new PipedWriter( outputReader );
            outputReader_ = new BufferedReader( outputReader );
            outputWriter_ = new PrintWriter( outputWriter, true );
        }
        catch( final IOException e )
        {
            throw new IOError( e );
        }
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#flush()
     */
    public void flush()
    {
        outputWriter_.flush();
    }

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#format(java.lang.String, java.lang.Object[])
     */
    public IDisplay format(
        final String format,
        final Object... args )
    {
        outputWriter_.format( format, args );
        return this;
    }

    /**
     * Gets a reader that can be used to read characters from this display's
     * input stream.
     * 
     * @return A reader that can be used to read characters from this display's
     *         input stream; never {@code null}.
     * 
     * @see IDisplay#getReader()
     */
    /* @NonNull */
    public Reader getInputReader()
    {
        return getReader();
    }

    /**
     * Gets a writer that can be used to write characters to this display's
     * input stream.
     * 
     * @return A writer that can be used to write characters to this display's
     *         input stream; never {@code null}.
     */
    /* @NonNull */
    public PrintWriter getInputWriter()
    {
        return inputWriter_;
    }

    /**
     * Gets a reader than can be used to read characters from this display's
     * output stream.
     * 
     * @return A reader that can be used to read characters from this display's
     *         output stream; never {@code null}.
     */
    /* @NonNull */
    public Reader getOutputReader()
    {
        return outputReader_;
    }

    /**
     * Gets a writer that can be used to write characters to this display's
     * output stream.
     * 
     * @return A writer that can be used to write characters to this display's
     *         output stream; never {@code null}.
     * 
     * @see IDisplay#getWriter()
     */
    /* @NonNull */
    public PrintWriter getOutputWriter()
    {
        return getWriter();
    }

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#getReader()
     */
    public Reader getReader()
    {
        return inputReader_;
    }

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#getWriter()
     */
    public PrintWriter getWriter()
    {
        return outputWriter_;
    }

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#readLine()
     */
    public String readLine()
    {
        try
        {
            return inputReader_.readLine();
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
        return readLine().toCharArray();
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
}

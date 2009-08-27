/*
 * DefaultDisplayTest.java
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
 * Created on Sep 27, 2008 at 10:17:42 PM.
 */

package org.gamegineer.client.internal.ui.console.displays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.ui.console.displays.DefaultDisplay}
 * class.
 */
public final class DefaultDisplayTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** A character array that can be used as a line of input. */
    private static final char[] INPUT_LINE = new char[] {
        'i', 'n', 'p', 'u', 't'
    };


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DefaultDisplayTest} class.
     */
    public DefaultDisplayTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new display with the specified string as input.
     * 
     * @param input
     *        The display input; must not be {@code null}.
     * 
     * @return A new display; never {@code null}.
     */
    /* @NonNull */
    private static DefaultDisplay createDisplay(
        /* @NonNull */
        final String input )
    {
        assert input != null;

        return new DefaultDisplay( new StringReader( input ), new StringWriter() );
    }

    /**
     * Ensures the primary constructor throws an exception when passed a
     * {@code null} input stream reader.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_Primary_Reader_Null()
    {
        new DefaultDisplay( null, new PrintWriter( new StringWriter() ) );
    }

    /**
     * Ensures the primary constructor throws an exception when passed a
     * {@code null} output stream writer.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_Primary_Writer_Null()
    {
        new DefaultDisplay( new StringReader( "" ), null ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getReader} method returns a reader whose {@code close}
     * method does not close the reader.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetReader_Close_DoesNotCloseReader()
        throws Exception
    {
        final DefaultDisplay display = createDisplay( String.valueOf( INPUT_LINE ) );
        display.getReader().close();

        display.getReader().read();
    }

    /**
     * Ensures the {@code getWriter} method returns a writer whose {@code close}
     * method does not close the writer.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetWriter_Close_DoesNotCloseWriter()
        throws Exception
    {
        final DefaultDisplay display = createDisplay( String.valueOf( INPUT_LINE ) );
        display.getWriter().close();

        display.getWriter().println();

        assertFalse( display.getWriter().checkError() );
    }

    /**
     * Ensures the {@code readSecureLine} method returns {@code null} when it
     * reaches the end of stream.
     */
    @Test
    public void testReadSecureLine_EndOfStream()
    {
        final String input = String.format( "%1$s", String.valueOf( INPUT_LINE ) ); //$NON-NLS-1$
        final DefaultDisplay display = createDisplay( input );
        display.readSecureLine(); // read until end of stream

        final char[] actualInputLine = display.readSecureLine();

        assertNull( actualInputLine );
    }

    /**
     * Ensures the {@code readSecureLine} method correctly handles a line
     * separator consisting of a single carriage return.
     */
    @Test
    public void testReadSecureLine_LineSeparator_CarriageReturn()
    {
        final String input = String.format( "%1$s\r%1$s\r", String.valueOf( INPUT_LINE ) ); //$NON-NLS-1$
        final DefaultDisplay display = createDisplay( input );

        final char[] actualInputLine1 = display.readSecureLine();
        final char[] actualInputLine2 = display.readSecureLine();

        assertArrayEquals( INPUT_LINE, actualInputLine1 );
        assertArrayEquals( INPUT_LINE, actualInputLine2 );
    }

    /**
     * Ensures the {@code readSecureLine} method properly handles the case when
     * a carriage return line separator is adjacent to another carriage return.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testReadSecureLine_LineSeparator_CarriageReturn_HandlesAdjacentCarriageReturn()
    {
        final String input = String.format( "%1$s\r\r%1$s\r", String.valueOf( INPUT_LINE ) ); //$NON-NLS-1$
        final DefaultDisplay display = createDisplay( input );

        final char[] actualInputLine1 = display.readSecureLine();
        final char[] actualInputLine2 = display.readSecureLine();
        final char[] actualInputLine3 = display.readSecureLine();

        assertArrayEquals( INPUT_LINE, actualInputLine1 );
        assertEquals( 0, actualInputLine2.length );
        assertArrayEquals( INPUT_LINE, actualInputLine3 );
    }

    /**
     * Ensures the {@code readSecureLine} method correctly handles a line
     * separator consisting of a carriage return/line feed pair.
     */
    @Test
    public void testReadSecureLine_LineSeparator_CarriageReturnLineFeed()
    {
        final String input = String.format( "%1$s\r\n%1$s\r\n", String.valueOf( INPUT_LINE ) ); //$NON-NLS-1$
        final DefaultDisplay display = createDisplay( input );

        final char[] actualInputLine1 = display.readSecureLine();
        final char[] actualInputLine2 = display.readSecureLine();

        assertArrayEquals( INPUT_LINE, actualInputLine1 );
        assertArrayEquals( INPUT_LINE, actualInputLine2 );
    }

    /**
     * Ensures the {@code readSecureLine} method correctly handles the end of
     * stream as a line separator.
     */
    @Test
    public void testReadSecureLine_LineSeparator_EndOfStream()
    {
        final String input = String.format( "%1$s", String.valueOf( INPUT_LINE ) ); //$NON-NLS-1$
        final DefaultDisplay display = createDisplay( input );

        final char[] actualInputLine = display.readSecureLine();

        assertArrayEquals( INPUT_LINE, actualInputLine );
    }

    /**
     * Ensures the {@code readSecureLine} method correctly handles a line
     * separator consisting of a single line feed.
     */
    @Test
    public void testReadSecureLine_LineSeparator_LineFeed()
    {
        final String input = String.format( "%1$s\n%1$s\n", String.valueOf( INPUT_LINE ) ); //$NON-NLS-1$
        final DefaultDisplay display = createDisplay( input );

        final char[] actualInputLine1 = display.readSecureLine();
        final char[] actualInputLine2 = display.readSecureLine();

        assertArrayEquals( INPUT_LINE, actualInputLine1 );
        assertArrayEquals( INPUT_LINE, actualInputLine2 );
    }
}

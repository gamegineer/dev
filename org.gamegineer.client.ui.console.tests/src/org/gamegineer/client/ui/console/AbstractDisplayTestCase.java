/*
 * AbstractDisplayTestCase.java
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
 * Created on Sep 27, 2008 at 9:03:38 PM.
 */

package org.gamegineer.client.ui.console;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import java.util.IllegalFormatException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.client.ui.console.IDisplay} interface.
 */
public abstract class AbstractDisplayTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The platform line separator. */
    private static final String LINE_SEPARATOR = System.getProperty( "line.separator" ); //$NON-NLS-1$

    /** The display under test in the fixture. */
    private IDisplay display_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractDisplayTestCase} class.
     */
    protected AbstractDisplayTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the display to be tested.
     * 
     * @return The display to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IDisplay createDisplay()
        throws Exception;

    /**
     * Reads a line of text from the display output stream.
     * 
     * @param display
     *        The display; must not be {@code null}.
     * 
     * @return The line of text read from the display output stream not
     *         including any line-termination characters or {@code null} if the
     *         end of stream has been reached.
     * 
     * @throws java.io.IOError
     *         If an I/O error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code display} is {@code null}.
     */
    /* @Nullable */
    protected abstract String readDisplayOutput(
        /* @NonNull */
        IDisplay display );

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        display_ = createDisplay();
        assertNotNull( display_ );
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        display_ = null;
    }

    /**
     * Ensures the {@code format} method throws an exception when passed an
     * insufficient number of arguments.
     */
    @Test( expected = IllegalFormatException.class )
    public void testFormat_Args_InsufficientArguments()
    {
        display_.format( "%1$s %2$s", new Object() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code format} method does not throw an exception when passed
     * a {@code null} argument array.
     */
    @Test
    public void testFormat_Args_Null()
    {
        final Object[] args = null;
        display_.format( "", args ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code format} method throws an exception when passed a
     * {@code null} format string.
     */
    @Test( expected = NullPointerException.class )
    public void testFormat_Format_Null()
    {
        display_.format( null, new Object[ 0 ] );
    }

    /**
     * Ensures the {@code format} method throws an exception when passed an
     * illegal format string.
     */
    @Test( expected = IllegalFormatException.class )
    public void testFormat_Format_Illegal()
    {
        display_.format( "%1$@", new Object() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code format} method writes the correct output.
     */
    @Test
    public void testFormat_Output()
    {
        final String format = "You are %1$d %2$s old."; //$NON-NLS-1$
        final Object[] args = new Object[] {
            new Integer( 10 ), "years" //$NON-NLS-1$
        };
        final String expectedOutput = String.format( format, args );

        display_.format( format, args );
        display_.format( LINE_SEPARATOR );

        assertEquals( expectedOutput, readDisplayOutput( display_ ) );
    }

    /**
     * Ensures the {@code format} method returns the same display.
     */
    @Test
    public void testFormat_ReturnValue_SameDisplay()
    {
        assertSame( display_, display_.format( "" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getReader} method does not return {@code null}.
     */
    @Test
    public void testGetReader_ReturnValue_NonNull()
    {
        assertNotNull( display_.getReader() );
    }

    /**
     * Ensures the {@code getWriter} method does not return {@code null}.
     */
    @Test
    public void testGetWriter_ReturnValue_NonNull()
    {
        assertNotNull( display_.getWriter() );
    }

    /**
     * Ensures the {@code readLine()} method reads the correct input.
     */
    @Test
    public void testReadLine_Input()
    {
        final String expectedInput = "The quick brown fox jumped over the lazy dog."; //$NON-NLS-1$
        writeDisplayInput( display_, expectedInput + LINE_SEPARATOR );

        final String actualInput = display_.readLine();

        assertEquals( expectedInput, actualInput );
    }

    /**
     * Ensures the {@code readLine(String, Object...)} method throws an
     * exception when passed an insufficient number of arguments.
     */
    @Test( expected = IllegalFormatException.class )
    public void testReadLineWithPrompt_Args_InsufficientArguments()
    {
        display_.readLine( "%1$s %2$s", new Object() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code readLine(String, Object...)} method does not throw an
     * exception when passed a {@code null} argument array.
     */
    @Test
    public void testReadLineWithPrompt_Args_Null()
    {
        writeDisplayInput( display_, LINE_SEPARATOR );
        final Object[] args = null;
        display_.readLine( "", args ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code readLine(String, Object...)} method throws an
     * exception when passed a {@code null} format string.
     */
    @Test( expected = NullPointerException.class )
    public void testReadLineWithPrompt_Format_Null()
    {
        display_.readLine( null, new Object[ 0 ] );
    }

    /**
     * Ensures the {@code readLine(String, Object...)} method throws an
     * exception when passed an illegal format string.
     */
    @Test( expected = IllegalFormatException.class )
    public void testReadLineWithPrompt_Format_Illegal()
    {
        display_.readLine( "%1$@", new Object() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code readLine(String, Object...)} method reads the correct
     * input.
     */
    @Test
    public void testReadLineWithPrompt_Input()
    {
        final String expectedInput = "The quick brown fox jumped over the lazy dog."; //$NON-NLS-1$
        writeDisplayInput( display_, expectedInput + LINE_SEPARATOR );

        final String actualInput = display_.readLine( "Prompt:" ); //$NON-NLS-1$

        assertEquals( expectedInput, actualInput );
    }

    /**
     * Ensures the {@code readLine(String, Object...)} method writes the correct
     * output.
     */
    @Test
    public void testReadLineWithPrompt_Output()
    {
        final String format = "You are %1$d %2$s old: "; //$NON-NLS-1$
        final Object[] args = new Object[] {
            new Integer( 10 ), "years" //$NON-NLS-1$
        };
        final String expectedOutput = String.format( format, args );
        writeDisplayInput( display_, LINE_SEPARATOR );

        display_.readLine( format + LINE_SEPARATOR, args );

        assertEquals( expectedOutput, readDisplayOutput( display_ ) );
    }

    /**
     * Ensures the {@code readSecureLine()} method reads the correct input.
     */
    @Test
    public void testReadSecureLine_Input()
    {
        final char[] expectedInput = new char[] {
            'p', 'a', 's', 's', 'w', 'o', 'r', 'd'
        };
        writeDisplayInput( display_, new String( expectedInput ) + LINE_SEPARATOR );

        final char[] actualInput = display_.readSecureLine();

        assertArrayEquals( expectedInput, actualInput );
    }

    /**
     * Ensures the {@code readSecureLine(String, Object...)} method throws an
     * exception when passed an insufficient number of arguments.
     */
    @Test( expected = IllegalFormatException.class )
    public void testReadSecureLineWithPrompt_Args_InsufficientArguments()
    {
        display_.readSecureLine( "%1$s %2$s", new Object() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code readSecureLine(String, Object...)} method does not
     * throw an exception when passed a {@code null} argument array.
     */
    @Test
    public void testReadSecureLineWithPrompt_Args_Null()
    {
        writeDisplayInput( display_, LINE_SEPARATOR );
        final Object[] args = null;
        display_.readSecureLine( "", args ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code readSecureLine(String, Object...)} method throws an
     * exception when passed a {@code null} format string.
     */
    @Test( expected = NullPointerException.class )
    public void testReadSecureLineWithPrompt_Format_Null()
    {
        display_.readSecureLine( null, new Object[ 0 ] );
    }

    /**
     * Ensures the {@code readSecureLine(String, Object...)} method throws an
     * exception when passed an illegal format string.
     */
    @Test( expected = IllegalFormatException.class )
    public void testReadSecureLineWithPrompt_Format_Illegal()
    {
        display_.readSecureLine( "%1$@", new Object() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code readSecureLine(String, Object...)} method reads the
     * correct input.
     */
    @Test
    public void testReadSecureLineWithPrompt_Input()
    {
        final char[] expectedInput = new char[] {
            'p', 'a', 's', 's', 'w', 'o', 'r', 'd'
        };
        writeDisplayInput( display_, new String( expectedInput ) + LINE_SEPARATOR );

        final char[] actualInput = display_.readSecureLine( "Prompt:" ); //$NON-NLS-1$

        assertArrayEquals( expectedInput, actualInput );
    }

    /**
     * Ensures the {@code readSecureLine(String, Object...)} method writes the
     * correct output.
     */
    @Test
    public void testReadSecureLineWithPrompt_Output()
    {
        final String format = "You are %1$d %2$s old: "; //$NON-NLS-1$
        final Object[] args = new Object[] {
            new Integer( 10 ), "years" //$NON-NLS-1$
        };
        final String expectedOutput = String.format( format, args );
        writeDisplayInput( display_, LINE_SEPARATOR );

        display_.readSecureLine( format + LINE_SEPARATOR, args );

        assertEquals( expectedOutput, readDisplayOutput( display_ ) );
    }

    /**
     * Writes the specified string to the display input stream.
     * 
     * @param display
     *        The display; must not be {@code null}.
     * @param s
     *        The input string; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code display} or {@code s} is {@code null}.
     */
    protected abstract void writeDisplayInput(
        /* @NonNull */
        IDisplay display,
        /* @NonNull */
        String s );
}

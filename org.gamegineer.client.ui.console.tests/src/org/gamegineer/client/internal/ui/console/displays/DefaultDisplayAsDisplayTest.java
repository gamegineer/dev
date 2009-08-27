/*
 * DefaultDisplayAsDisplayTest.java
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
 * Created on Sep 27, 2008 at 9:43:33 PM.
 */

package org.gamegineer.client.internal.ui.console.displays;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.junit.Assert.assertSame;
import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import org.gamegineer.client.ui.console.AbstractDisplayTestCase;
import org.gamegineer.client.ui.console.IDisplay;
import org.junit.After;
import org.junit.Before;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.ui.console.displays.DefaultDisplay}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.client.ui.console.IDisplay} interface.
 */
public final class DefaultDisplayAsDisplayTest
    extends AbstractDisplayTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The display under test in the fixture. */
    private DefaultDisplay m_display;

    /** The writer that pipes input to the display input stream. */
    private PrintWriter m_inputWriter;

    /** The reader that pipes output from the display output stream. */
    private BufferedReader m_outputReader;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DefaultDisplayAsDisplayTest}
     * class.
     */
    public DefaultDisplayAsDisplayTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.ui.console.AbstractDisplayTestCase#createDisplay()
     */
    @Override
    protected IDisplay createDisplay()
        throws Exception
    {
        return m_display;
    }

    /*
     * @see org.gamegineer.client.ui.console.AbstractDisplayTestCase#readDisplayOutput(org.gamegineer.client.ui.console.IDisplay)
     */
    @Override
    protected String readDisplayOutput(
        final IDisplay display )
    {
        assertArgumentNotNull( display, "display" ); //$NON-NLS-1$
        assertSame( m_display, display );

        try
        {
            return m_outputReader.readLine();
        }
        catch( final IOException e )
        {
            throw new IOError( e );
        }
    }

    /*
     * @see org.gamegineer.client.ui.console.AbstractDisplayTestCase#setUp()
     */
    @Before
    @Override
    public void setUp()
        throws Exception
    {
        final PipedWriter inputWriter = new PipedWriter();
        final PipedReader inputReader = new PipedReader( inputWriter );
        final PipedReader outputReader = new PipedReader();
        final PipedWriter outputWriter = new PipedWriter( outputReader );
        m_outputReader = new BufferedReader( outputReader );
        m_inputWriter = new PrintWriter( inputWriter );
        m_display = new DefaultDisplay( inputReader, new PrintWriter( outputWriter ) );

        super.setUp();
    }

    /*
     * @see org.gamegineer.client.ui.console.AbstractDisplayTestCase#tearDown()
     */
    @After
    @Override
    public void tearDown()
        throws Exception
    {
        super.tearDown();

        m_display = null;
        m_inputWriter = null;
        m_outputReader = null;
    }

    /*
     * @see org.gamegineer.client.ui.console.AbstractDisplayTestCase#writeDisplayInput(org.gamegineer.client.ui.console.IDisplay, java.lang.String)
     */
    @Override
    protected void writeDisplayInput(
        final IDisplay display,
        final String s )
    {
        assertArgumentNotNull( display, "display" ); //$NON-NLS-1$
        assertSame( m_display, display );
        assertArgumentNotNull( s, "s" ); //$NON-NLS-1$

        m_inputWriter.print( s );
        m_inputWriter.flush();
    }
}

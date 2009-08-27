/*
 * PrintLineCommandletAsCommandletTest.java
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
 * Created on Oct 19, 2008 at 9:43:11 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlets.core;

import static org.junit.Assert.assertEquals;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Collections;
import org.gamegineer.client.ui.console.FakeConsole;
import org.gamegineer.client.ui.console.FakeDisplay;
import org.gamegineer.client.ui.console.commandlet.AbstractCommandletTestCase;
import org.gamegineer.client.ui.console.commandlet.CommandletException;
import org.gamegineer.client.ui.console.commandlet.ICommandlet;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.ui.console.commandlets.core.PrintLineCommandlet}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.client.ui.console.commandlet.ICommandlet} interface.
 */
public final class PrintLineCommandletAsCommandletTest
    extends AbstractCommandletTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code PrintLineCommandletAsCommandletTest} class.
     */
    public PrintLineCommandletAsCommandletTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.ui.console.commandlet.AbstractCommandletTestCase#createCommandlet()
     */
    @Override
    protected ICommandlet createCommandlet()
    {
        return new PrintLineCommandlet();
    }

    /**
     * Ensures the {@code execute} method throws an exception when passed an
     * argument list whose size is equal to the minimum illegal value.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = CommandletException.class )
    public void testExecute_ArgCount_MinIllegalValue()
        throws Exception
    {
        getCommandlet().execute( createCommandletContext( Arrays.asList( "1", "2" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Ensures the {@code execute} method outputs the expected text to the
     * console when passed an argument list with a single element.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( timeout = 1000 )
    public void testExecute_ArgCount_One()
        throws Exception
    {
        final FakeDisplay display = new FakeDisplay();
        final FakeConsole console = new FakeConsole( display );
        final String expectedLine = "the quick brown fox jumped over the lazy dog"; //$NON-NLS-1$

        getCommandlet().execute( createCommandletContext( console, Collections.singletonList( expectedLine ) ) );

        final BufferedReader reader = new BufferedReader( display.getOutputReader() );
        assertEquals( expectedLine, reader.readLine() );
    }

    /**
     * Ensures the {@code execute} method outputs the expected text to the
     * console when passed an empty argument.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( timeout = 1000 )
    public void testExecute_ArgCount_Zero()
        throws Exception
    {
        final FakeDisplay display = new FakeDisplay();
        final FakeConsole console = new FakeConsole( display );
        final String expectedLine = ""; //$NON-NLS-1$

        getCommandlet().execute( createCommandletContext( console, Collections.<String>emptyList() ) );

        final BufferedReader reader = new BufferedReader( display.getOutputReader() );
        assertEquals( expectedLine, reader.readLine() );
    }
}

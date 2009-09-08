/*
 * CommandletParserTest.java
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
 * Created on Oct 23, 2008 at 11:36:46 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlet;

import static org.junit.Assert.assertNotNull;
import java.util.HashSet;
import java.util.Set;
import org.gamegineer.common.core.runtime.Platform;
import org.gamegineer.common.core.services.component.IComponentFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.ui.console.commandlet.CommandletParser}
 * class.
 */
public final class CommandletParserTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The commandlet parser under test in the fixture. */
    private CommandletParser parser_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandletParserTest} class.
     */
    public CommandletParserTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
        parser_ = new CommandletParser();
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
        parser_ = null;
    }

    /**
     * Ensures the {@code parse} method returns a non-{@code null} executor when
     * passed a line that contains an ambiguous commandlet name.
     */
    @Test
    public void testParse_Line_Ambiguous()
    {
        final Set<String> commandletClassNames = new HashSet<String>();
        commandletClassNames.add( "HelloCommandlet" ); //$NON-NLS-1$
        commandletClassNames.add( "a.b.c.HelloCommandlet" ); //$NON-NLS-1$
        final IComponentFactory commandletFactory = new FakeCommandletFactory( commandletClassNames );
        Platform.getComponentService().registerComponentFactory( commandletFactory );

        try
        {
            assertNotNull( parser_.parse( "hello" ) ); //$NON-NLS-1$
        }
        finally
        {
            Platform.getComponentService().unregisterComponentFactory( commandletFactory );
        }
    }

    /**
     * Ensures the {@code parse} method returns a non-{@code null} executor when
     * passed an empty line.
     */
    @Test
    public void testParse_Line_Empty()
    {
        assertNotNull( parser_.parse( "" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code parse} method returns a non-{@code null} executor when
     * passed a malformed line.
     */
    @Test
    public void testParse_Line_Malformed()
    {
        assertNotNull( parser_.parse( "     " ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code parse} method throws an exception when passed a
     * {@code null} line.
     */
    @Test( expected = NullPointerException.class )
    public void testParse_Line_Null()
    {
        parser_.parse( null );
    }

    /**
     * Ensures the {@code parse} method returns a non-{@code null} executor when
     * passed a line that contains an unknown commandlet.
     */
    @Test
    public void testParse_Line_Unknown()
    {
        assertNotNull( parser_.parse( "__unknown__ arg1 arg2" ) ); //$NON-NLS-1$
    }
}

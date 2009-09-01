/*
 * CommandletAliasManagerTest.java
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
 * Created on Oct 22, 2008 at 10:26:16 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlet;

import static org.junit.Assert.assertEquals;
import java.util.HashSet;
import java.util.Set;
import org.gamegineer.common.core.runtime.Platform;
import org.gamegineer.common.core.services.component.IComponentFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.ui.console.commandlet.CommandletAliasManager}
 * class.
 */
public final class CommandletAliasManagerTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** A set of known commandlet class names for use in the fixture. */
    private static final Set<String> c_commandletClassNames;

    /** A commandlet factory for use in the fixture. */
    private IComponentFactory m_commandletFactory;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code CommandletAliasManagerTest} class.
     */
    static
    {
        c_commandletClassNames = new HashSet<String>();
        c_commandletClassNames.add( "HelloCommandlet" ); //$NON-NLS-1$
        c_commandletClassNames.add( "a.b.c.HelloCommandlet" ); //$NON-NLS-1$
        c_commandletClassNames.add( "d.e.f.GoodbyeCommandlet" ); //$NON-NLS-1$
    }

    /**
     * Initializes a new instance of the {@code CommandletAliasManagerTest}
     * class.
     */
    public CommandletAliasManagerTest()
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
        m_commandletFactory = new FakeCommandletFactory( c_commandletClassNames );
        Platform.getComponentService().registerComponentFactory( m_commandletFactory );
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
        Platform.getComponentService().unregisterComponentFactory( m_commandletFactory );
        m_commandletFactory = null;
    }

    /**
     * Ensures the {@code getCommandletClassNames} method returns the expected
     * set when the alias matches one commandlet.
     */
    @Test
    public void testGetCommandletClassNames_Alias_Match_One()
    {
        final Set<String> classNames = CommandletAliasManager.getCommandletClassNames( "goodbye" ); //$NON-NLS-1$

        assertEquals( 1, classNames.size() );
    }

    /**
     * Ensures the {@code getCommandletClassNames} method returns the expected
     * set when the alias matches two commandlets.
     */
    @Test
    public void testGetCommandletClassNames_Alias_Match_Two()
    {
        final Set<String> classNames = CommandletAliasManager.getCommandletClassNames( "hello" ); //$NON-NLS-1$

        assertEquals( 2, classNames.size() );
    }

    /**
     * Ensures the {@code getCommandletClassNames} method returns an empty set
     * when the alias does not match any commandlets.
     */
    @Test
    public void testGetCommandletClassNames_Alias_NoMatch()
    {
        final Set<String> classNames = CommandletAliasManager.getCommandletClassNames( "farewell" ); //$NON-NLS-1$

        assertEquals( 0, classNames.size() );
    }

    /**
     * Ensures the {@code getCommandletClassNames} method throws an exception
     * when passed a {@code null} alias.
     */
    @Test( expected = NullPointerException.class )
    public void testGetCommandletClassNames_Alias_Null()
    {
        CommandletAliasManager.getCommandletClassNames( null );
    }
}

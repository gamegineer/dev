/*
 * CommandletArgumentsTest.java
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
 * Created on Jan 31, 2009 at 11:06:39 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlet;

import static org.junit.Assert.assertEquals;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.ui.console.commandlet.CommandletArguments}
 * class.
 */
public final class CommandletArgumentsTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandletArgumentsTest} class.
     */
    public CommandletArgumentsTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code formatNamedValueList} method throws an exception when
     * passed a {@code null} named value collection.
     */
    @Test( expected = NullPointerException.class )
    public void testFormatNamedValueList_NamedValues_Null()
    {
        CommandletArguments.formatNamedValueList( null );
    }

    /**
     * Ensures the {@code formatNamedValueList} method correctly formats a named
     * value collection with one entry.
     */
    @Test
    public void testFormatNamedValueList_NamedValues_OneEntry()
    {
        final String name = "name"; //$NON-NLS-1$
        final String value = "value"; //$NON-NLS-1$
        final Map<String, String> entries = new HashMap<String, String>();
        entries.put( name, value );
        final String expectedArg = String.format( "%1$s=%2$s", name, value ); //$NON-NLS-1$

        final String actualArg = CommandletArguments.formatNamedValueList( entries );

        assertEquals( expectedArg, actualArg );
    }

    /**
     * Ensures the {@code formatNamedValueList} method correctly formats a named
     * value collection with two entries.
     */
    @Test
    public void testFormatNamedValueList_NamedValues_TwoEntries()
    {
        final String name1 = "name1"; //$NON-NLS-1$
        final String value1 = "value1"; //$NON-NLS-1$
        final String name2 = "name2"; //$NON-NLS-1$
        final String value2 = "value2"; //$NON-NLS-1$
        final Map<String, String> entries = new HashMap<String, String>();
        entries.put( name1, value1 );
        entries.put( name2, value2 );
        final String expectedArg = String.format( "%1$s=%2$s,%3$s=%4$s", name1, value1, name2, value2 ); //$NON-NLS-1$

        final String actualArg = CommandletArguments.formatNamedValueList( entries );

        assertEquals( expectedArg, actualArg );
    }

    /**
     * Ensures the {@code formatNamedValueList} method correctly formats a named
     * value collection with zero entries.
     */
    @Test
    public void testFormatNamedValueList_NamedValues_ZeroEntries()
    {
        final Map<String, String> entries = new HashMap<String, String>();
        final String expectedArg = ""; //$NON-NLS-1$

        final String actualArg = CommandletArguments.formatNamedValueList( entries );

        assertEquals( expectedArg, actualArg );
    }

    /**
     * Ensures the {@code parseNamedValueList} method throws an exception when
     * passed a named value list that contains a malformed entry.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testParseNamedValueList_Arg_MalformedEntry()
    {
        final String arg = "name1=value1,name2,name3=value3"; //$NON-NLS-1$

        CommandletArguments.parseNamedValueList( arg );
    }

    /**
     * Ensures the {@code parseNamedValueList} method throws an exception when
     * passed a malformed named value list.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testParseNamedValueList_Arg_MalformedList()
    {
        final String arg = "name=value,"; //$NON-NLS-1$

        CommandletArguments.parseNamedValueList( arg );
    }

    /**
     * Ensures the {@code parseNamedValueList} method throws an exception when
     * passed a {@code null} argument.
     */
    @Test( expected = NullPointerException.class )
    public void testParseNamedValueList_Arg_Null()
    {
        CommandletArguments.parseNamedValueList( null );
    }

    /**
     * Ensures the {@code parseNamedValueList} method correctly parses a named
     * value list with one entry.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testParseNamedValueList_Arg_OneEntry()
    {
        final String name = "name"; //$NON-NLS-1$
        final String value = "value"; //$NON-NLS-1$
        final String arg = String.format( "%1$s=%2$s", name, value ); //$NON-NLS-1$

        final Map<String, String> entries = CommandletArguments.parseNamedValueList( arg );

        assertEquals( 1, entries.size() );
        assertEquals( value, entries.get( name ) );
    }

    /**
     * Ensures the {@code parseNamedValueList} method correctly parses a named
     * value list with two entries.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testParseNamedValueList_Arg_TwoEntries()
    {
        final String name1 = "name1"; //$NON-NLS-1$
        final String value1 = "value1"; //$NON-NLS-1$
        final String name2 = "name2"; //$NON-NLS-1$
        final String value2 = "value2"; //$NON-NLS-1$
        final String arg = String.format( "%1$s=%2$s,%3$s=%4$s", name1, value1, name2, value2 ); //$NON-NLS-1$

        final Map<String, String> entries = CommandletArguments.parseNamedValueList( arg );

        assertEquals( 2, entries.size() );
        assertEquals( value1, entries.get( name1 ) );
        assertEquals( value2, entries.get( name2 ) );
    }

    /**
     * Ensures the {@code parseNamedValueList} method correctly parses a named
     * value list with zero entries.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testParseNamedValueList_Arg_ZeroEntries()
    {
        final Map<String, String> entries = CommandletArguments.parseNamedValueList( "" ); //$NON-NLS-1$

        assertEquals( 0, entries.size() );
    }
}

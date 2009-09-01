/*
 * CommandletLexerTest.java
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
 * Created on Oct 23, 2008 at 11:13:18 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlet;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.ui.console.commandlet.CommandletLexer}
 * class.
 */
public final class CommandletLexerTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandletLexerTest} class.
     */
    public CommandletLexerTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code tokenize} method throws an exception when passed an
     * all-whitespace line.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testTokenize_Line_Illegal_AllWhitespace()
    {
        CommandletLexer.tokenize( "      " ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code tokenize} method throws an exception when passed an
     * empty line.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testTokenize_Line_Illegal_Empty()
    {
        CommandletLexer.tokenize( "" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code tokenize} method correctly parses a legal line with
     * quoted commandlet arguments.
     */
    @Test
    public void testTokenize_Line_Legal_WithQuotedArgs()
    {
        final CommandletTokenCollection tokens = CommandletLexer.tokenize( "name \"arg 1\" arg2 \"arg 3\"" ); //$NON-NLS-1$

        assertEquals( "name", tokens.getName() ); //$NON-NLS-1$
        assertEquals( 3, tokens.getArguments().size() );
        assertEquals( "arg 1", tokens.getArguments().get( 0 ) ); //$NON-NLS-1$
        assertEquals( "arg2", tokens.getArguments().get( 1 ) ); //$NON-NLS-1$
        assertEquals( "arg 3", tokens.getArguments().get( 2 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code tokenize} method correctly parses a legal line with
     * quoted commandlet arguments which themselves contain quotes.
     */
    @Test
    public void testTokenize_Line_Legal_WithQuotedArgs_EmbeddedQuotes()
    {
        final CommandletTokenCollection tokens = CommandletLexer.tokenize( "name \"arg \"\"1\"\"\" arg2 \"arg \"\"3\"\"\"" ); //$NON-NLS-1$

        assertEquals( "name", tokens.getName() ); //$NON-NLS-1$
        assertEquals( 3, tokens.getArguments().size() );
        assertEquals( "arg \"1\"", tokens.getArguments().get( 0 ) ); //$NON-NLS-1$
        assertEquals( "arg2", tokens.getArguments().get( 1 ) ); //$NON-NLS-1$
        assertEquals( "arg \"3\"", tokens.getArguments().get( 2 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code tokenize} method correctly parses a legal line with
     * quoted commandlet arguments which themselves are empty.
     */
    @Test
    public void testTokenize_Line_Legal_WithQuotedArgs_Empty()
    {
        final CommandletTokenCollection tokens = CommandletLexer.tokenize( "name \"\" arg2 \"\"" ); //$NON-NLS-1$

        assertEquals( "name", tokens.getName() ); //$NON-NLS-1$
        assertEquals( 3, tokens.getArguments().size() );
        assertEquals( "", tokens.getArguments().get( 0 ) ); //$NON-NLS-1$
        assertEquals( "arg2", tokens.getArguments().get( 1 ) ); //$NON-NLS-1$
        assertEquals( "", tokens.getArguments().get( 2 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code tokenize} method correctly parses a legal line with
     * unquoted commandlet arguments.
     */
    @Test
    public void testTokenize_Line_Legal_WithUnquotedArgs()
    {
        final CommandletTokenCollection tokens = CommandletLexer.tokenize( "name arg1 arg2" ); //$NON-NLS-1$

        assertEquals( "name", tokens.getName() ); //$NON-NLS-1$
        assertEquals( 2, tokens.getArguments().size() );
        assertEquals( "arg1", tokens.getArguments().get( 0 ) ); //$NON-NLS-1$
        assertEquals( "arg2", tokens.getArguments().get( 1 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code tokenize} method correctly parses a legal line without
     * commandlet arguments.
     */
    @Test
    public void testTokenize_Line_Legal_WithoutArgs()
    {
        final CommandletTokenCollection tokens = CommandletLexer.tokenize( "name" ); //$NON-NLS-1$

        assertEquals( "name", tokens.getName() ); //$NON-NLS-1$
        assertEquals( 0, tokens.getArguments().size() );
    }

    /**
     * Ensures the {@code tokenize} method throws an exception when passed a
     * {@code null} line.
     */
    @Test( expected = AssertionError.class )
    public void testTokenize_Line_Null()
    {
        CommandletLexer.tokenize( null );
    }
}

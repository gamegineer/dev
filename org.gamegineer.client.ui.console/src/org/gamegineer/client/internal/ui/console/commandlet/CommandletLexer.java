/*
 * CommandletLexer.java
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
 * Created on Oct 23, 2008 at 10:48:43 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlet;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.jcip.annotations.ThreadSafe;

/**
 * A commandlet lexical analyzer.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
final class CommandletLexer
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The regular expression pattern used to tokenize a line of text.
     * 
     * <p>
     * Invoke this pattern repeatedly on a line of text to extract its tokens. A
     * quoted token is placed into group 1, and an unquoted token is placed into
     * group 2.
     * </p>
     */
    private static final Pattern LINE_PATTERN = Pattern.compile( "\\G(?:^|\\s+)(?:\"((?:[^\"]++|\"\")*+)\"|([^\"\\s]+))" ); //$NON-NLS-1$

    /** The regular expression pattern used to match a pair of quotes. */
    private static final Pattern QUOTE_PATTERN = Pattern.compile( "\"\"" ); //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandletLexer} class.
     */
    private CommandletLexer()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Tokenizes the specified line to produce a collection of commandlet tokens
     * that can be further parsed.
     * 
     * @param line
     *        The line to tokenize; must not be {@code null}.
     * 
     * @return A collection of commandlet tokens represented by the specified
     *         line; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code line} cannot be tokenized.
     */
    /* @NonNull */
    static CommandletTokenCollection tokenize(
        /* @NonNull */
        final String line )
    {
        assert line != null;

        final List<String> tokens = new ArrayList<String>();
        final Matcher lineMatcher = LINE_PATTERN.matcher( line );
        final Matcher quoteMatcher = QUOTE_PATTERN.matcher( "" ); //$NON-NLS-1$
        while( lineMatcher.find() )
        {
            if( lineMatcher.start( 1 ) >= 0 )
            {
                // quoted argument
                tokens.add( quoteMatcher.reset( lineMatcher.group( 1 ) ).replaceAll( "\"" ) ); //$NON-NLS-1$
            }
            else
            {
                // unquoted argument
                tokens.add( lineMatcher.group( 2 ) );
            }
        }

        assertArgumentLegal( !tokens.isEmpty(), "line" ); //$NON-NLS-1$
        final String name = tokens.get( 0 );
        assertArgumentLegal( !name.isEmpty(), "line" ); //$NON-NLS-1$
        final List<String> args = tokens.subList( 1, tokens.size() );
        return new CommandletTokenCollection( name, args );
    }
}

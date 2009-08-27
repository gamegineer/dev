/*
 * CommandletArguments.java
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
 * Created on Jan 31, 2009 at 11:01:57 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlet;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.jcip.annotations.ThreadSafe;

/**
 * A collection of methods useful for parsing various types of commandlet
 * arguments.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
public final class CommandletArguments
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The named value list entry delimiter. */
    private static final String NAMED_VALUE_LIST_ENTRY_DELIMITER = ","; //$NON-NLS-1$

    /** The regular expression pattern used to parse a named value list entry. */
    private static final Pattern NAMED_VALUE_LIST_ENTRY_PATTERN = Pattern.compile( "^([^=]+)=(.*)$" ); //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandletArguments} class.
     */
    private CommandletArguments()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Formats the specified collection of name-value pairs as a named value
     * list string suitable to be used as a commandlet argument.
     * 
     * @param namedValues
     *        The collection of name-value pairs; must not be {@code null}.
     * 
     * @return A named value list string suitable to be used as a commandlet
     *         argument; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code namedValues} is {@code null}.
     */
    /* @NonNull */
    public static String formatNamedValueList(
        /* @NonNull */
        final Map<String, String> namedValues )
    {
        assertArgumentNotNull( namedValues, "namedValues" ); //$NON-NLS-1$

        final StringBuilder sb = new StringBuilder();
        for( final Map.Entry<String, String> entry : namedValues.entrySet() )
        {
            sb.append( entry.getKey() );
            sb.append( '=' );
            sb.append( entry.getValue() );
            sb.append( NAMED_VALUE_LIST_ENTRY_DELIMITER );
        }

        // Remove trailing delimiter if present
        if( sb.length() > 0 )
        {
            sb.deleteCharAt( sb.length() - 1 );
        }

        return sb.toString();
    }

    /**
     * Parses the specified argument as a list of name-value pairs.
     * 
     * <p>
     * The format of a named value list is expected to be
     * <p>
     * 
     * <p>
     * <i>&lt;name&gt;</i>=<i>&lt;value&gt;</i>[,<i>&lt;name&gt;</i>=<i>&lt;value&gt;</i>[,
     * ...]]
     * </p>
     * 
     * @param arg
     *        The argument to parse; must not be {@code null}.
     * 
     * @return A collection of name-value pairs; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code arg} is not a well-formed named value list.
     * @throws java.lang.NullPointerException
     *         If {@code arg} is {@code null}.
     */
    /* @NonNull */
    public static Map<String, String> parseNamedValueList(
        /* @NonNull */
        final String arg )
    {
        assertArgumentNotNull( arg, "arg" ); //$NON-NLS-1$

        if( arg.isEmpty() )
        {
            return Collections.emptyMap();
        }

        final Map<String, String> entries = new HashMap<String, String>();
        final Matcher entryMatcher = NAMED_VALUE_LIST_ENTRY_PATTERN.matcher( "" ); //$NON-NLS-1$
        for( final String entry : arg.split( NAMED_VALUE_LIST_ENTRY_DELIMITER, -1 ) )
        {
            if( !entryMatcher.reset( entry ).matches() )
            {
                throw new IllegalArgumentException( Messages.CommandletArguments_parseNamedValueList_malformedEntry( entry ) );
            }
            final String name = entryMatcher.group( 1 );
            final String value = entryMatcher.group( 2 );
            entries.put( name, value );
        }

        return entries;
    }
}

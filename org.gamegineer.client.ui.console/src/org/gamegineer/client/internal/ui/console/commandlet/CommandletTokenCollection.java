/*
 * CommandletTokenCollection.java
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
 * Created on Oct 23, 2008 at 10:48:58 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.jcip.annotations.Immutable;

/**
 * A collection of commandlet tokens produced by the commandlet lexer.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
final class CommandletTokenCollection
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The commandlet argument list. */
    private final List<String> m_args;

    /** The commandlet name. */
    private final String m_name;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandletTokenCollection}
     * class.
     * 
     * @param name
     *        The commandlet name; must not be {@code null} or empty.
     * @param args
     *        The commandlet argument list; must not be {@code null}.
     */
    CommandletTokenCollection(
        /* @NonNull */
        final String name,
        /* @NonNull */
        final List<String> args )
    {
        assert name != null;
        assert !name.isEmpty();
        assert args != null;

        m_name = name;
        m_args = Collections.unmodifiableList( new ArrayList<String>( args ) );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets an immutable view of the commandlet argument list.
     * 
     * @return An immutable view of the commandlet argument list; never
     *         {@code null}.
     */
    /* @NonNull */
    List<String> getArguments()
    {
        return m_args;
    }

    /**
     * Gets the commandlet name.
     * 
     * @return The commandlet name; never {@code null}.
     */
    /* @NonNull */
    String getName()
    {
        return m_name;
    }
}

/*
 * CommandContextTest.java
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
 * Created on Mar 28, 2009 at 8:57:52 PM.
 */

package org.gamegineer.engine.internal.core.contexts.command;

import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.contexts.command.CommandContext}
 * class.
 */
public final class CommandContextTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandContextTest} class.
     */
    public CommandContextTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws makes a copy of the attribute collection.
     */
    @Test
    public void testConstructor_Attributes_Copy()
    {
        final String name = "name"; //$NON-NLS-1$
        final Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put( name, new Object() );

        final CommandContext context = new CommandContext( attributes );
        attributes.remove( name );

        assertTrue( context.containsAttribute( name ) );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * attribute collection.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Attributes_Null()
    {
        new CommandContext( null );
    }

    /**
     * Ensures the constructor throws an exception when passed an attribute
     * collection that contains a {@code null} value.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Attributes_NullValue()
    {
        final Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put( "name", null ); //$NON-NLS-1$

        new CommandContext( attributes );
    }
}

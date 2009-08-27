/*
 * FakeCommandContext.java
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
 * Created on Apr 11, 2009 at 9:59:27 PM.
 */

package org.gamegineer.engine.core.contexts.command;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Fake implementation of
 * {@link org.gamegineer.engine.core.contexts.command.ICommandContext}.
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 */
public class FakeCommandContext
    implements ICommandContext
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of attributes. */
    private final Map<String, Object> m_attributes;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeCommandContext} class with
     * an empty attribute collection.
     */
    public FakeCommandContext()
    {
        this( Collections.<String, Object>emptyMap() );
    }

    /**
     * Initializes a new instance of the {@code FakeCommandContext} class with
     * the specified attribute collection.
     * 
     * @param attributes
     *        The collection of attributes that define the context; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code attributes} is {@code null}.
     */
    public FakeCommandContext(
        /* @NonNull */
        final Map<String, Object> attributes )
    {
        assertArgumentNotNull( attributes, "attributes" ); //$NON-NLS-1$

        m_attributes = new HashMap<String, Object>( attributes );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.contexts.command.ICommandContext#containsAttribute(java.lang.String)
     */
    public boolean containsAttribute(
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        return m_attributes.containsKey( name );
    }

    /*
     * @see org.gamegineer.engine.core.contexts.command.ICommandContext#getAttribute(java.lang.String)
     */
    public Object getAttribute(
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        return m_attributes.get( name );
    }
}

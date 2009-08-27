/*
 * CommandContextBuilder.java
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
 * Created on May 3, 2009 at 10:17:06 PM.
 */

package org.gamegineer.engine.core.contexts.command;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.HashMap;
import java.util.Map;
import org.gamegineer.engine.internal.core.contexts.command.CommandContext;

/**
 * A factory for building instances of
 * {@link org.gamegineer.engine.core.contexts.command.ICommandContext}.
 * 
 * <p>
 * Each command context built by an instance of this class is immutable and thus
 * guaranteed to be thread-safe.
 * </p>
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public final class CommandContextBuilder
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of context attributes. */
    private final Map<String, Object> m_attributes;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandContextBuilder} class.
     */
    public CommandContextBuilder()
    {
        m_attributes = new HashMap<String, Object>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds an attribute.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * @param value
     *        The attribute value; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If an attribute with the same name already exists.
     * @throws java.lang.NullPointerException
     *         If {@code name} or {@code value} is {@code null}.
     */
    /* @NonNull */
    public CommandContextBuilder addAttribute(
        /* @NonNull */
        final String name,
        /* @NonNull */
        final Object value )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentLegal( !m_attributes.containsKey( name ), "name", Messages.CommandContextBuilder_attribute_exists( name ) ); //$NON-NLS-1$
        assertArgumentNotNull( value, "value" ); //$NON-NLS-1$

        m_attributes.put( name, value );

        return this;
    }

    /**
     * Creates a new command context based on the current state of this builder.
     * 
     * @return A new command context; never {@code null}.
     */
    /* @NonNull */
    public ICommandContext toCommandContext()
    {
        return new CommandContext( m_attributes );
    }
}

/*
 * ExtensionContext.java
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
 * Created on May 1, 2009 at 11:09:35 PM.
 */

package org.gamegineer.engine.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.HashMap;
import java.util.Map;
import org.gamegineer.engine.core.contexts.extension.IExtensionContext;

/**
 * Implementation of
 * {@link org.gamegineer.engine.core.contexts.extension.IExtensionContext}.
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
final class ExtensionContext
    implements IExtensionContext
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
     * Initializes a new instance of the {@code ExtensionContext} class with an
     * empty attribute collection.
     */
    ExtensionContext()
    {
        m_attributes = new HashMap<String, Object>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.contexts.extension.IExtensionContext#addAttribute(java.lang.String, java.lang.Object)
     */
    public void addAttribute(
        final String name,
        final Object value )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentLegal( !m_attributes.containsKey( name ), "name" ); //$NON-NLS-1$
        assertArgumentNotNull( value, "value" ); //$NON-NLS-1$

        m_attributes.put( name, value );
    }

    /*
     * @see org.gamegineer.engine.core.contexts.extension.IExtensionContext#containsAttribute(java.lang.String)
     */
    public boolean containsAttribute(
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        return m_attributes.containsKey( name );
    }

    /*
     * @see org.gamegineer.engine.core.contexts.extension.IExtensionContext#getAttribute(java.lang.String)
     */
    public Object getAttribute(
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        return m_attributes.get( name );
    }
}

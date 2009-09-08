/*
 * FakeExtensionContext.java
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
 * Created on May 2, 2009 at 8:50:01 PM.
 */

package org.gamegineer.engine.core.contexts.extension;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Fake implementation of
 * {@link org.gamegineer.engine.core.contexts.extension.IExtensionContext}.
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public class FakeExtensionContext
    implements IExtensionContext
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of attributes. */
    private final Map<String, Object> attributes_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeExtensionContext} class with
     * an empty attribute collection.
     */
    public FakeExtensionContext()
    {
        this( Collections.<String, Object>emptyMap() );
    }

    /**
     * Initializes a new instance of the {@code FakeExtensionContext} class with
     * the specified attribute collection.
     * 
     * @param attributes
     *        The collection of initial attributes; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code attributes} is {@code null}.
     */
    public FakeExtensionContext(
        /* @NonNull */
        final Map<String, Object> attributes )
    {
        assertArgumentNotNull( attributes, "attributes" ); //$NON-NLS-1$

        attributes_ = new HashMap<String, Object>( attributes );
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
        assertArgumentLegal( !attributes_.containsKey( name ), "name" ); //$NON-NLS-1$
        assertArgumentNotNull( value, "value" ); //$NON-NLS-1$

        attributes_.put( name, value );
    }

    /*
     * @see org.gamegineer.engine.core.contexts.extension.IExtensionContext#containsAttribute(java.lang.String)
     */
    public boolean containsAttribute(
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        return attributes_.containsKey( name );
    }

    /*
     * @see org.gamegineer.engine.core.contexts.extension.IExtensionContext#getAttribute(java.lang.String)
     */
    public Object getAttribute(
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        return attributes_.get( name );
    }
}

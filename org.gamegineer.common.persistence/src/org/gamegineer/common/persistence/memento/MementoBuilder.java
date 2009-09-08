/*
 * MementoBuilder.java
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
 * Created on Jul 1, 2008 at 9:21:08 PM.
 */

package org.gamegineer.common.persistence.memento;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.gamegineer.common.internal.persistence.memento.Memento;

/**
 * A factory for building instances of
 * {@link org.gamegineer.common.persistence.memento.IMemento}.
 * 
 * <p>
 * The memento implementation returned by this class is guaranteed to have a
 * persistence delegate for both the JavaBeans persistence framework and the
 * Java object serialization framework.
 * </p>
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 */
public final class MementoBuilder
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The attribute collection. */
    private final Map<String, Object> attributes_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MementoBuilder} class with no
     * attributes initially.
     */
    public MementoBuilder()
    {
        this( Collections.<String, Object>emptyMap() );
    }

    /**
     * Initializes a new instance of the {@code MementoBuilder} class with the
     * specified attribute collection initially.
     * 
     * @param attributes
     *        The collection of initialization attributes; must not be {@code
     *        null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code attributes} is {@code null}.
     */
    public MementoBuilder(
        /* @NonNull */
        final Map<String, Object> attributes )
    {
        assertArgumentNotNull( attributes, "attributes" ); //$NON-NLS-1$

        attributes_ = new HashMap<String, Object>( attributes );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds an attribute to this builder.
     * 
     * @param <T>
     *        The type of the attribute value.
     * @param name
     *        The attribute name; must not be {@code null}.
     * @param value
     *        The attribute value; may be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If an attribute with the same name already exists in this
     *         builder.
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    public <T> void addAttribute(
        /* @NonNull */
        final String name,
        /* @Nullable */
        final T value )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentLegal( !attributes_.containsKey( name ), "name", Messages.MementoBuilder_attribute_present( name ) ); //$NON-NLS-1$

        attributes_.put( name, value );
    }

    /**
     * Indicates this builder contains the specified attribute.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * 
     * @return {@code true} if this builder contains the specified attribute;
     *         otherwise {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    public boolean containsAttribute(
        /* @NonNull */
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        return attributes_.containsKey( name );
    }

    /**
     * Creates a new memento based on the current state of this builder.
     * 
     * @return A new memento; never {@code null}.
     */
    /* @NonNull */
    public IMemento toMemento()
    {
        return new Memento( attributes_ );
    }
}

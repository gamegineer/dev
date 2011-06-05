/*
 * Memento.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Jun 29, 2008 at 10:23:49 PM.
 */

package org.gamegineer.common.internal.core.util.memento;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.util.memento.IMemento;

/**
 * A basic implementation of
 * {@link org.gamegineer.common.core.util.memento.IMemento}.
 */
@Immutable
public final class Memento
    implements IMemento
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The attribute collection. The key is the attribute name. The value is the
     * attribute value.
     */
    private final Map<String, Object> attributes_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Memento} class.
     * 
     * @param attributes
     *        The attribute collection; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code attributes} is {@code null}.
     */
    public Memento(
        /* @NonNull */
        final Map<String, Object> attributes )
    {
        assertArgumentNotNull( attributes, "attributes" ); //$NON-NLS-1$

        attributes_ = Collections.unmodifiableMap( new HashMap<String, Object>( attributes ) );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.util.memento.IMemento#containsAttribute(java.lang.String)
     */
    @Override
    public boolean containsAttribute(
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        return attributes_.containsKey( name );
    }

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(
        final Object obj )
    {
        if( obj == this )
        {
            return true;
        }

        if( !(obj instanceof IMemento) )
        {
            return false;
        }

        final IMemento other = (IMemento)obj;
        final Map<String, Object> otherAttributes = new HashMap<String, Object>();
        for( final String name : other.getAttributeNames() )
        {
            otherAttributes.put( name, other.getAttribute( name ) );
        }
        return attributes_.equals( otherAttributes );
    }

    /*
     * @see org.gamegineer.common.core.util.memento.IMemento#getAttribute(java.lang.String)
     */
    @Override
    public <T> T getAttribute(
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentLegal( attributes_.containsKey( name ), "name", Messages.Memento_attribute_absent( name ) ); //$NON-NLS-1$

        @SuppressWarnings( "unchecked" )
        final T value = (T)attributes_.get( name );
        return value;
    }

    /*
     * @see org.gamegineer.common.core.util.memento.IMemento#getAttributeNames()
     */
    @Override
    public Set<String> getAttributeNames()
    {
        return new HashSet<String>( attributes_.keySet() );
    }

    /**
     * Gets an unmodifiable map view of the attributes contained in this
     * memento.
     * 
     * @return An unmodifiable map view of the attributes contained in this
     *         memento; never {@code null}.
     */
    /* @NonNull */
    public Map<String, Object> getAttributes()
    {
        return attributes_;
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        int result = 17;
        result = result * 31 + attributes_.hashCode();
        return result;
    }
}

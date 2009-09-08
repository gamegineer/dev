/*
 * Memento.java
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
 * Created on Jun 29, 2008 at 10:23:49 PM.
 */

package org.gamegineer.common.internal.persistence.memento;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.persistence.memento.IMemento;

/**
 * A basic implementation of
 * {@link org.gamegineer.common.persistence.memento.IMemento}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 */
@Immutable
public final class Memento
    implements IMemento
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The attribute map. */
    private final Map<String, Object> attributeMap_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Memento} class.
     * 
     * @param attributeMap
     *        The attribute map; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code attributeMap} is {@code null}.
     */
    public Memento(
        /* @NonNull */
        final Map<String, Object> attributeMap )
    {
        assertArgumentNotNull( attributeMap, "attributeMap" ); //$NON-NLS-1$

        attributeMap_ = Collections.unmodifiableMap( new HashMap<String, Object>( attributeMap ) );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.persistence.memento.IMemento#containsAttribute(java.lang.String)
     */
    public boolean containsAttribute(
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        return attributeMap_.containsKey( name );
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
        final Map<String, Object> otherAttributeMap = new HashMap<String, Object>();
        for( final String name : other.getAttributeNames() )
        {
            otherAttributeMap.put( name, other.getAttribute( name ) );
        }
        return attributeMap_.equals( otherAttributeMap );
    }

    /*
     * @see org.gamegineer.common.persistence.memento.IMemento#getAttribute(java.lang.String)
     */
    public <T> T getAttribute(
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentLegal( attributeMap_.containsKey( name ), "name", Messages.Memento_attribute_absent( name ) ); //$NON-NLS-1$

        @SuppressWarnings( "unchecked" )
        final T value = (T)attributeMap_.get( name );
        return value;
    }

    /*
     * @see org.gamegineer.common.persistence.memento.IMemento#getAttributeNames()
     */
    public Set<String> getAttributeNames()
    {
        return attributeMap_.keySet();
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
        return attributeMap_;
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        int result = 17;
        result = result * 31 + attributeMap_.hashCode();
        return result;
    }
}

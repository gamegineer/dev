/*
 * MementoUtils.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Apr 9, 2010 at 10:29:45 PM.
 */

package org.gamegineer.table.internal.core.impl;

import java.util.Map;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;

/**
 * A collection of methods useful for working with mementos represented by a
 * {@code Map<String, Object>}.
 */
@ThreadSafe
final class MementoUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MementoUtils} class.
     */
    private MementoUtils()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the specified non-nullable attribute from the specified memento.
     * 
     * @param <T>
     *        The type of the attribute value.
     * @param memento
     *        The memento.
     * @param name
     *        The attribute name.
     * @param type
     *        The type of the attribute value.
     * 
     * @return The attribute value.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If the memento is not of type {@code Map}, the memento does not
     *         contain the attribute, the attribute value is {@code null}, or
     *         the attribute value is of the wrong type.
     */
    static <T> T getAttribute(
        final Object memento,
        final String name,
        final Class<T> type )
        throws MementoException
    {
        if( !(memento instanceof Map<?, ?>) )
        {
            throw new MementoException( NonNlsMessages.MementoUtils_memento_wrongType );
        }

        @SuppressWarnings( "unchecked" )
        final Map<String, Object> attributes = (Map<String, Object>)memento;
        if( !attributes.containsKey( name ) )
        {
            throw new MementoException( NonNlsMessages.MementoUtils_attribute_absent( name ) );
        }

        final T value;
        try
        {
            value = type.cast( attributes.get( name ) );
        }
        catch( final ClassCastException e )
        {
            throw new MementoException( NonNlsMessages.MementoUtils_attributeValue_wrongType( name ), e );
        }

        if( value == null )
        {
            throw new MementoException( NonNlsMessages.MementoUtils_attributeValue_null( name ) );
        }

        return value;
    }

    /**
     * Indicates the specified memento contains an attribute with the specified
     * name.
     * 
     * @param memento
     *        The memento.
     * @param name
     *        The attribute name.
     * 
     * @return {@code true} if the memento contains an attribute with the
     *         specified name; otherwise {@code false}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If the memento is not of type {@code Map}.
     */
    static boolean hasAttribute(
        final Object memento,
        final String name )
        throws MementoException
    {
        if( !(memento instanceof Map<?, ?>) )
        {
            throw new MementoException( NonNlsMessages.MementoUtils_memento_wrongType );
        }

        @SuppressWarnings( "unchecked" )
        final Map<String, Object> attributes = (Map<String, Object>)memento;
        return attributes.containsKey( name );
    }
}

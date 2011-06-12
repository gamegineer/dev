/*
 * MementoUtils.java
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
 * Created on Apr 9, 2010 at 10:29:45 PM.
 */

package org.gamegineer.table.internal.core;

import java.util.Map;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;

/**
 * A collection of methods useful for working with mementos.
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
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the specified non-nullable optional attribute from the specified
     * memento.
     * 
     * @param <T>
     *        The type of the attribute value.
     * @param memento
     *        The memento; must not be {@code null}.
     * @param name
     *        The attribute name; must not be {@code null}.
     * @param type
     *        The type of the attribute value; must not be {@code null}.
     * 
     * @return The attribute value or {@code null} if the attribute is not
     *         present in the memento.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If the memento is of the wrong type, the attribute value is
     *         {@code null}, or the attribute value is of the wrong type.
     */
    /* @Nullable */
    static <T> T getOptionalAttribute(
        /* @NonNull */
        final Object memento,
        /* @NonNull */
        final String name,
        /* @NonNull */
        final Class<T> type )
        throws MementoException
    {
        assert memento != null;
        assert name != null;
        assert type != null;

        if( !(memento instanceof Map<?, ?>) )
        {
            throw new MementoException( Messages.MementoUtils_memento_wrongType );
        }

        @SuppressWarnings( "unchecked" )
        final Map<String, Object> attributes = (Map<String, Object>)memento;
        if( !attributes.containsKey( name ) )
        {
            return null;
        }

        final T value;
        try
        {
            value = type.cast( attributes.get( name ) );
        }
        catch( final ClassCastException e )
        {
            throw new MementoException( Messages.MementoUtils_attributeValue_wrongType( name ), e );
        }

        if( value == null )
        {
            throw new MementoException( Messages.MementoUtils_attributeValue_null( name ) );
        }

        return value;
    }

    /**
     * Gets the specified non-nullable required attribute from the specified
     * memento.
     * 
     * @param <T>
     *        The type of the attribute value.
     * @param memento
     *        The memento; must not be {@code null}.
     * @param name
     *        The attribute name; must not be {@code null}.
     * @param type
     *        The type of the attribute value; must not be {@code null}.
     * 
     * @return The attribute value; never {@code null}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If the memento is of the wrong type, the memento does not contain
     *         the attribute, the attribute value is {@code null}, or the
     *         attribute value is of the wrong type.
     */
    /* @NonNull */
    static <T> T getRequiredAttribute(
        /* @NonNull */
        final Object memento,
        /* @NonNull */
        final String name,
        /* @NonNull */
        final Class<T> type )
        throws MementoException
    {
        final T value = getOptionalAttribute( memento, name, type );
        if( value == null )
        {
            throw new MementoException( Messages.MementoUtils_attribute_absent( name ) );
        }

        return value;
    }
}

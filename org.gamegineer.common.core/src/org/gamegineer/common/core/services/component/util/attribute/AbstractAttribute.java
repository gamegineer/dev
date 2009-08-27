/*
 * AbstractAttribute.java
 * Copyright 2008 Gamegineer.org
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
 * Created on May 18, 2008 at 9:42:43 PM.
 */

package org.gamegineer.common.core.services.component.util.attribute;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;

/**
 * Superclass for all classes that implement
 * {@link org.gamegineer.common.core.services.component.util.attribute.IAttribute}.
 * 
 * @param <T>
 *        The type of the attribute.
 */
public abstract class AbstractAttribute<T>
    implements IAttribute<T>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The attribute name. */
    private final String m_name;

    /**
     * The attribute type.
     * 
     * <p>
     * Note that we must use a wildcard here because {@code T} may not be a
     * reifiable type. For example, if {@code T} were {@code List<String>}, it
     * is not reifiable and thus {@code Class<T>} is illegal.
     * </p>
     */
    private final Class<?> m_type;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractAttribute} class.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * @param type
     *        The attribute type; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code name} or {@code type} is {@code null}.
     */
    protected AbstractAttribute(
        /* @NonNull */
        final String name,
        /* @NonNull */
        final Class<?> type )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentNotNull( type, "type" ); //$NON-NLS-1$

        m_type = type;
        m_name = name;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.services.component.util.attribute.IAttribute#getName()
     */
    public String getName()
    {
        return m_name;
    }

    /*
     * @see org.gamegineer.common.core.services.component.util.attribute.IAttribute#getValue(org.gamegineer.common.core.services.component.util.attribute.IAttributeAccessor)
     */
    public T getValue(
        final IAttributeAccessor accessor )
    {
        return getValue( accessor, true );
    }

    /**
     * Gets the value of the attribute from an object.
     * 
     * @param accessor
     *        The accessor used to read the attribute from the object; must not
     *        be {@code null}.
     * @param isRequired
     *        {@code true} if the attribute value is required to be present;
     *        otherwise {@code false}. If {@code true}, an exception will be
     *        thrown if the attribute is not present. If {@code false}, a
     *        {@code null} value will be returned.
     * 
     * @return The value of the attribute or {@code null} if the attribute is
     *         not present and {@code isRequired} is {@code false}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the attribute is not present and {@code isRequired} is
     *         {@code true}, or if the attribute value is illegal.
     * @throws java.lang.NullPointerException
     *         If {@code accessor} is {@code null}.
     */
    /* @Nullable */
    private T getValue(
        /* @NonNull */
        final IAttributeAccessor accessor,
        final boolean isRequired )
    {
        assertArgumentNotNull( accessor, "accessor" ); //$NON-NLS-1$

        final Object value = accessor.getAttribute( m_name );
        if( value == null )
        {
            if( isRequired )
            {
                throw new IllegalArgumentException( m_name );
            }

            return null;
        }
        if( !m_type.isInstance( value ) )
        {
            throw new IllegalArgumentException( m_name );
        }

        @SuppressWarnings( "unchecked" )
        final T typedValue = (T)value;
        if( !isLegalValue( typedValue ) )
        {
            throw new IllegalArgumentException( m_name );
        }

        return typedValue;
    }

    /**
     * Indicates the specified attribute value is legal.
     * 
     * <p>
     * The default implementation considers all non-{@code null} values legal.
     * </p>
     * 
     * @param value
     *        The attribute value; must not be {@code null}.
     * 
     * @return {@code true} if the attribute value is legal; otherwise
     *         {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code value} is {@code null}.
     */
    protected boolean isLegalValue(
        /* @NonNull */
        final T value )
    {
        assertArgumentNotNull( value, "value" ); //$NON-NLS-1$

        return true;
    }

    /*
     * @see org.gamegineer.common.core.services.component.util.attribute.IAttribute#isPresent(org.gamegineer.common.core.services.component.util.attribute.IAttributeAccessor)
     */
    public boolean isPresent(
        final IAttributeAccessor accessor )
    {
        assertArgumentNotNull( accessor, "accessor" ); //$NON-NLS-1$

        return accessor.containsAttribute( m_name );
    }

    /*
     * @see org.gamegineer.common.core.services.component.util.attribute.IAttribute#setValue(org.gamegineer.common.core.services.component.util.attribute.IAttributeMutator, java.lang.Object)
     */
    public void setValue(
        final IAttributeMutator mutator,
        final T value )
    {
        setValue( mutator, value, true );
    }

    /**
     * Sets the value of the attribute on an object.
     * 
     * @param mutator
     *        The mutator used to write the attribute to the object; must not be
     *        {@code null}.
     * @param value
     *        The value of the attribute; may be {@code null} if no attribute
     *        value is to be written.
     * @param isRequired
     *        {@code true} if the attribute value is required to be non-{@code null};
     *        otherwise {@code false}. If {@code true}, an exception will be
     *        thrown if the attribute value is {@code null}. If {@code false},
     *        the underlying attribute collection will be unmodified.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the attribute value is illegal.
     * @throws java.lang.NullPointerException
     *         If {@code accessor} is {@code null} or if {@code value} is
     *         {@code null} and {@code isRequired} is {@code true}.
     */
    private void setValue(
        /* @NonNull */
        final IAttributeMutator mutator,
        /* @Nullable */
        final T value,
        final boolean isRequired )
    {
        assertArgumentNotNull( mutator, "mutator" ); //$NON-NLS-1$
        if( (value == null) && !isRequired )
        {
            return;
        }
        assertArgumentNotNull( value, "value" ); //$NON-NLS-1$

        if( !isLegalValue( value ) )
        {
            throw new IllegalArgumentException( m_name );
        }

        mutator.setAttribute( m_name, value );
    }

    /*
     * @see org.gamegineer.common.core.services.component.util.attribute.IAttribute#tryGetValue(org.gamegineer.common.core.services.component.util.attribute.IAttributeAccessor)
     */
    public T tryGetValue(
        final IAttributeAccessor accessor )
    {
        return getValue( accessor, false );
    }

    /*
     * @see org.gamegineer.common.core.services.component.util.attribute.IAttribute#trySetValue(org.gamegineer.common.core.services.component.util.attribute.IAttributeMutator, java.lang.Object)
     */
    public void trySetValue(
        final IAttributeMutator mutator,
        final T value )
    {
        setValue( mutator, value, false );
    }
}

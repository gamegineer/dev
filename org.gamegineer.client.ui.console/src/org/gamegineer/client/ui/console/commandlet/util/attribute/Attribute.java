/*
 * Attribute.java
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
 * Created on May 18, 2009 at 10:19:09 PM.
 */

package org.gamegineer.client.ui.console.commandlet.util.attribute;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.client.ui.console.commandlet.IStatelet;

/**
 * Implementation of
 * {@link org.gamegineer.client.ui.console.commandlet.util.attribute.IAttribute}
 * with support for enforcing legal values.
 * 
 * <p>
 * This implementation considers any non-{@code null} value legal.
 * </p>
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 * 
 * <p>
 * This class is immutable.
 * </p>
 * 
 * @param <T>
 *        The type of the attribute value.
 */
@Immutable
public abstract class Attribute<T>
    implements IAttribute<T>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The attribute name. */
    private final String name_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Attribute} class.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    public Attribute(
        /* @NonNull */
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        name_ = name;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.ui.console.commandlet.util.attribute.IAttribute#add(org.gamegineer.client.ui.console.commandlet.IStatelet, java.lang.Object)
     */
    public void add(
        final IStatelet statelet,
        final T value )
    {
        assertArgumentNotNull( statelet, "statelet" ); //$NON-NLS-1$
        assertArgumentLegal( isLegalValue( value ), "value" ); //$NON-NLS-1$

        statelet.addAttribute( name_, value );
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.util.attribute.IAttribute#ensureGetValue(org.gamegineer.client.ui.console.commandlet.IStatelet)
     */
    public T ensureGetValue(
        final IStatelet statelet )
    {
        assertArgumentNotNull( statelet, "statelet" ); //$NON-NLS-1$

        if( !isPresent( statelet ) )
        {
            add( statelet, getDefaultValue() );
        }

        return getValue( statelet );
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.util.attribute.IAttribute#ensureSetValue(org.gamegineer.client.ui.console.commandlet.IStatelet, java.lang.Object)
     */
    public void ensureSetValue(
        final IStatelet statelet,
        final T value )
    {
        assertArgumentNotNull( statelet, "statelet" ); //$NON-NLS-1$

        if( isPresent( statelet ) )
        {
            setValue( statelet, value );
        }
        else
        {
            add( statelet, value );
        }
    }

    /**
     * Gets the default attribute value.
     * 
     * <p>
     * Implementors must ensure they return a new instance from this method each
     * time it is invoked if the type {@code T} is mutable (e.g. a collection).
     * </p>
     * 
     * @return The default attribute value; may be {@code null}.
     */
    /* @Nullable */
    protected abstract T getDefaultValue();

    /*
     * @see org.gamegineer.client.ui.console.commandlet.util.attribute.IAttribute#getName()
     */
    public String getName()
    {
        return name_;
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.util.attribute.IAttribute#getValue(org.gamegineer.client.ui.console.commandlet.IStatelet)
     */
    @SuppressWarnings( "unchecked" )
    public T getValue(
        final IStatelet statelet )
    {
        assertArgumentNotNull( statelet, "statelet" ); //$NON-NLS-1$

        return (T)statelet.getAttribute( name_ );
    }

    /**
     * Indicates the specified attribute value is legal.
     * 
     * <p>
     * The default implementation considers all non-{@code null} values legal.
     * </p>
     * 
     * @param value
     *        The attribute value; may be {@code null}.
     * 
     * @return {@code true} if the specified attribute value is legal; otherwise
     *         {@code false}.
     */
    protected boolean isLegalValue(
        /* @Nullable */
        final T value )
    {
        return value != null;
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.util.attribute.IAttribute#isPresent(org.gamegineer.client.ui.console.commandlet.IStatelet)
     */
    public boolean isPresent(
        final IStatelet statelet )
    {
        assertArgumentNotNull( statelet, "statelet" ); //$NON-NLS-1$

        return statelet.containsAttribute( name_ );
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.util.attribute.IAttribute#remove(org.gamegineer.client.ui.console.commandlet.IStatelet)
     */
    public void remove(
        final IStatelet statelet )
    {
        assertArgumentNotNull( statelet, "statelet" ); //$NON-NLS-1$

        statelet.removeAttribute( name_ );
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.util.attribute.IAttribute#setValue(org.gamegineer.client.ui.console.commandlet.IStatelet, java.lang.Object)
     */
    public void setValue(
        final IStatelet statelet,
        final T value )
    {
        assertArgumentNotNull( statelet, "statelet" ); //$NON-NLS-1$
        assertArgumentLegal( isLegalValue( value ), "value" ); //$NON-NLS-1$

        statelet.setAttribute( name_, value );
    }
}

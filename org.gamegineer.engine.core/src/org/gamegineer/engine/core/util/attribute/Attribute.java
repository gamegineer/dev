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
 * Created on Sep 5, 2008 at 11:10:21 PM.
 */

package org.gamegineer.engine.core.util.attribute;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.IState;
import org.gamegineer.engine.core.IState.Scope;

/**
 * Implementation of
 * {@link org.gamegineer.engine.core.util.attribute.IAttribute} with support for
 * enforcing legal values.
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
public class Attribute<T>
    implements IAttribute<T>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The attribute name. */
    private final AttributeName name_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Attribute} class using the
     * specified attribute name.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    public Attribute(
        /* @NonNull */
        final AttributeName name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        name_ = name;
    }

    /**
     * Initializes a new instance of the {@code Attribute} class using the
     * specified attribute name components.
     * 
     * @param scope
     *        The attribute scope; must not be {@code null}.
     * @param localName
     *        The local name of the attribute; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the local name is an empty string.
     * @throws java.lang.NullPointerException
     *         If {@code scope} or {@code localName} is {@code null}.
     */
    public Attribute(
        /* @NonNull */
        final Scope scope,
        /* @NonNull */
        final String localName )
    {
        this( new AttributeName( scope, localName ) );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.util.attribute.IAttribute#add(org.gamegineer.engine.core.IState, java.lang.Object)
     */
    public void add(
        final IState state,
        final T value )
    {
        assertArgumentNotNull( state, "state" ); //$NON-NLS-1$
        assertArgumentLegal( isLegalValue( value ), "value" ); //$NON-NLS-1$

        state.addAttribute( name_, decorateValue( value ) );
    }

    /**
     * Optionally decorates the specified attribute value before storing it in
     * the engine state.
     * 
     * <p>
     * This method allows child classes to decorate a new attribute value if
     * necessary. For example, an attribute implementation that accepts a
     * {@code List} may choose to decorate the incoming list with an immutable
     * list wrapper.
     * </p>
     * 
     * <p>
     * The default implementation does nothing and simply returns the incoming
     * value.
     * </p>
     * 
     * @param value
     *        The attribute value; may be {@code null}. The value is guaranteed
     *        to be legal according to the implementation of the
     *        {@link #isLegalValue(Object)} method.
     * 
     * @return The decorated attribute value; may be {@code null}.
     */
    /* @Nullable */
    protected T decorateValue(
        /* @Nullable */
        final T value )
    {
        return value;
    }

    /*
     * @see org.gamegineer.engine.core.util.IAttribute#getName()
     */
    public AttributeName getName()
    {
        return name_;
    }

    /*
     * @see org.gamegineer.engine.core.util.IAttribute#getValue(org.gamegineer.engine.core.IState)
     */
    @SuppressWarnings( "unchecked" )
    public T getValue(
        final IState state )
    {
        assertArgumentNotNull( state, "state" ); //$NON-NLS-1$

        return (T)state.getAttribute( name_ );
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
     * @see org.gamegineer.engine.core.util.attribute.IAttribute#isPresent(org.gamegineer.engine.core.IState)
     */
    public boolean isPresent(
        final IState state )
    {
        assertArgumentNotNull( state, "state" ); //$NON-NLS-1$

        return state.containsAttribute( name_ );
    }

    /*
     * @see org.gamegineer.engine.core.util.attribute.IAttribute#remove(org.gamegineer.engine.core.IState)
     */
    public void remove(
        final IState state )
    {
        assertArgumentNotNull( state, "state" ); //$NON-NLS-1$

        state.removeAttribute( name_ );
    }

    /*
     * @see org.gamegineer.engine.core.util.IAttribute#setValue(org.gamegineer.engine.core.IState, java.lang.Object)
     */
    public void setValue(
        final IState state,
        final T value )
    {
        assertArgumentNotNull( state, "state" ); //$NON-NLS-1$
        assertArgumentLegal( isLegalValue( value ), "value" ); //$NON-NLS-1$

        state.setAttribute( name_, decorateValue( value ) );
    }
}

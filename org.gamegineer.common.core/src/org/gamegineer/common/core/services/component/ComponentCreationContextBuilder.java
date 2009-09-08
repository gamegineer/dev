/*
 * ComponentCreationContextBuilder.java
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
 * Created on Apr 10, 2008 at 10:32:44 PM.
 */

package org.gamegineer.common.core.services.component;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.gamegineer.common.core.services.component.util.attribute.IAttributeMutator;
import org.gamegineer.common.internal.core.services.component.ComponentCreationContext;

/**
 * A factory for building instances of
 * {@link org.gamegineer.common.core.services.component.IComponentCreationContext}
 * .
 * 
 * <p>
 * Each component creation context built by an instance of this class is
 * immutable and thus guaranteed to be thread-safe.
 * </p>
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public final class ComponentCreationContextBuilder
    implements IAttributeMutator
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The empty component creation context. */
    private static final IComponentCreationContext EMPTY_CONTEXT = new ComponentCreationContext( Collections.<String, Object>emptyMap() );

    /** The collection of initialization attributes. */
    private final Map<String, Object> attributes_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentCreationContextBuilder}
     * class with no attributes initially.
     */
    public ComponentCreationContextBuilder()
    {
        this( Collections.<String, Object>emptyMap() );
    }

    /**
     * Initializes a new instance of the {@code ComponentCreationContextBuilder}
     * class with the specified attribute collection initially.
     * 
     * @param attributes
     *        The collection of initialization attributes; must not be {@code
     *        null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If any value in {@code attributes} is {@code null}.
     * @throws java.lang.NullPointerException
     *         If {@code attributes} is {@code null}.
     */
    public ComponentCreationContextBuilder(
        /* @NonNull */
        final Map<String, Object> attributes )
    {
        assertArgumentNotNull( attributes, "attributes" ); //$NON-NLS-1$
        assertArgumentLegal( !attributes.containsValue( null ), "attributes", Messages.ComponentCreationContextBuilder_attributes_containsNullValue ); //$NON-NLS-1$

        attributes_ = new HashMap<String, Object>( attributes );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Indicates this builder contains the specified attribute.
     * 
     * @param name
     *        The name of the attribute; must not be {@code null}.
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
     * Creates a new component creation context with the specified attribute
     * collection.
     * 
     * <p>
     * This is a simple factory method provided as a convenience if the
     * initialization attributes for the component creation context are
     * completely contained within an existing collection.
     * </p>
     * 
     * @param attributes
     *        The collection of initialization attributes; must not be {@code
     *        null}.
     * 
     * @return A new component creation context; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code attributes} is {@code null}.
     */
    /* @NonNull */
    public static IComponentCreationContext createComponentCreationContext(
        /* @NonNull */
        final Map<String, Object> attributes )
    {
        assertArgumentNotNull( attributes, "attributes" ); //$NON-NLS-1$

        return attributes.isEmpty() ? EMPTY_CONTEXT : new ComponentCreationContext( attributes );
    }

    /**
     * Creates an empty component creation context.
     * 
     * <p>
     * An empty component creation context contains no initialization
     * attributes.
     * </p>
     * 
     * @return An empty component creation context; never {@code null}.
     */
    /* @NonNull */
    public static IComponentCreationContext emptyComponentCreationContext()
    {
        return EMPTY_CONTEXT;
    }

    /**
     * Gets the value of the attribute with the specified name.
     * 
     * @param name
     *        The name of the attribute; must not be {@code null}.
     * 
     * @return The value of the specified attribute or {@code null} if the
     *         attribute does not exist in this builder.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    /* @Nullable */
    public Object getAttribute(
        /* @NonNull */
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        return attributes_.get( name );
    }

    /*
     * @see org.gamegineer.common.core.services.component.util.attribute.IAttributeMutator#setAttribute(java.lang.String, java.lang.Object)
     */
    public void setAttribute(
        final String name,
        final Object value )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentNotNull( value, "value" ); //$NON-NLS-1$

        attributes_.put( name, value );
    }

    /**
     * Creates a new component creation context based on the current state of
     * this builder.
     * 
     * @return A new component creation context; never {@code null}.
     */
    /* @NonNull */
    public IComponentCreationContext toComponentCreationContext()
    {
        return createComponentCreationContext( attributes_ );
    }
}

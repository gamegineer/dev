/*
 * ComponentCreationContextAttributeAccessor.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on May 17, 2008 at 11:18:04 PM.
 */

package org.gamegineer.common.core.services.component.util.attribute;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.services.component.IComponentCreationContext;

/**
 * Implementation of
 * {@link org.gamegineer.common.core.services.component.util.attribute.IAttributeAccessor}
 * for classes that implement
 * {@link org.gamegineer.common.core.services.component.IComponentCreationContext}
 * .
 */
@Immutable
public final class ComponentCreationContextAttributeAccessor
    implements IAttributeAccessor
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component creation context. */
    private final IComponentCreationContext context_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * ComponentCreationContextAttributeAccessor} class.
     * 
     * @param context
     *        The component creation context; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    public ComponentCreationContextAttributeAccessor(
        /* @NonNull */
        final IComponentCreationContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        context_ = context;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.services.component.util.attribute.IAttributeAccessor#containsAttribute(java.lang.String)
     */
    @Override
    public boolean containsAttribute(
        final String name )
    {
        return context_.containsAttribute( name );
    }

    /*
     * @see org.gamegineer.common.core.services.component.util.attribute.IAttributeAccessor#getAttribute(java.lang.String)
     */
    @Override
    public Object getAttribute(
        final String name )
    {
        return context_.getAttribute( name );
    }
}

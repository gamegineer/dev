/*
 * ComponentFactoryAttributeAccessor.java
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
 * Created on May 17, 2008 at 10:36:11 PM.
 */

package org.gamegineer.common.core.services.component.util.attribute;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.services.component.IComponentFactory;

/**
 * Implementation of
 * {@link org.gamegineer.common.core.services.component.util.attribute.IAttributeAccessor}
 * for classes that implement
 * {@link org.gamegineer.common.core.services.component.IComponentFactory}.
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
public final class ComponentFactoryAttributeAccessor
    implements IAttributeAccessor
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component factory. */
    private final IComponentFactory factory_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * ComponentFactoryAttributeAccessor} class.
     * 
     * @param factory
     *        The component factory; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code factory} is {@code null}.
     */
    public ComponentFactoryAttributeAccessor(
        /* @NonNull */
        final IComponentFactory factory )
    {
        assertArgumentNotNull( factory, "factory" ); //$NON-NLS-1$

        factory_ = factory;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.services.component.util.attribute.IAttributeAccessor#containsAttribute(java.lang.String)
     */
    public boolean containsAttribute(
        final String name )
    {
        return factory_.containsAttribute( name );
    }

    /*
     * @see org.gamegineer.common.core.services.component.util.attribute.IAttributeAccessor#getAttribute(java.lang.String)
     */
    public Object getAttribute(
        final String name )
    {
        return factory_.getAttribute( name );
    }
}

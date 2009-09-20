/*
 * ClassNameComponentSpecification.java
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
 * Created on May 17, 2008 at 1:18:08 PM.
 */

package org.gamegineer.common.core.services.component.specs;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.services.component.IComponentFactory;
import org.gamegineer.common.core.services.component.IComponentSpecification;
import org.gamegineer.common.core.services.component.attributes.SupportedClassNamesAttribute;
import org.gamegineer.common.core.services.component.util.attribute.ComponentFactoryAttributeAccessor;

/**
 * Implementation of
 * {@link org.gamegineer.common.core.services.component.IComponentSpecification}
 * that specifies the class name of the component to create.
 * 
 * <p>
 * A component factory that supports this specification must publish the
 * {@link org.gamegineer.common.core.services.component.attributes.SupportedClassNamesAttribute}
 * attribute.
 * </p>
 */
@Immutable
public final class ClassNameComponentSpecification
    implements IComponentSpecification
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the component class. */
    private final String name_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ClassNameComponentSpecification}
     * class.
     * 
     * @param name
     *        The name of the component class; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    public ClassNameComponentSpecification(
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
     * @see org.gamegineer.common.core.services.component.IComponentSpecification#matches(org.gamegineer.common.core.services.component.IComponentFactory)
     */
    public boolean matches(
        final IComponentFactory factory )
    {
        assertArgumentNotNull( factory, "factory" ); //$NON-NLS-1$

        final Iterable<String> supportedClassNames = SupportedClassNamesAttribute.INSTANCE.tryGetValue( new ComponentFactoryAttributeAccessor( factory ) );
        if( supportedClassNames != null )
        {
            for( final String name : supportedClassNames )
            {
                if( name_.equals( name ) )
                {
                    return true;
                }
            }
        }

        return false;
    }
}

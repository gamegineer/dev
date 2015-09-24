/*
 * ComponentPrototypesExtensionPointFacade.java
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
 * Created on Oct 12, 2012 at 8:36:55 PM.
 */

package org.gamegineer.table.internal.ui.impl.prototype;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.IConfigurationElement;
import org.gamegineer.common.core.util.concurrent.TaskUtils;

/**
 * A facade for accessing private members of the
 * {@link ComponentPrototypesExtensionPoint} class that are required for
 * testing.
 */
@ThreadSafe
final class ComponentPrototypesExtensionPointFacade
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentPrototypesExtensionPointFacade} class.
     */
    private ComponentPrototypesExtensionPointFacade()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new component prototype from the specified component prototype
     * configuration element.
     * 
     * <p>
     * This method is a facade for invoking the
     * {@link ComponentPrototypesExtensionPoint#createComponentPrototype}
     * method.
     * </p>
     * 
     * @param configurationElement
     *        The component prototype configuration element.
     * 
     * @return A new component prototype.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code configurationElement} does not represent a legal
     *         component prototype.
     */
    static ComponentPrototype createComponentPrototype(
        final IConfigurationElement configurationElement )
    {
        try
        {
            final Method method = ComponentPrototypesExtensionPoint.class.getDeclaredMethod( "createComponentPrototype", IConfigurationElement.class ); //$NON-NLS-1$
            method.setAccessible( true );
            final ComponentPrototype componentPrototype = (ComponentPrototype)method.invoke( null, configurationElement );
            assert componentPrototype != null;
            return componentPrototype;
        }
        catch( final InvocationTargetException e )
        {
            throw TaskUtils.launderThrowable( e.getCause() );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }

    /**
     * Creates a new component prototype category from the specified component
     * prototype category configuration element.
     * 
     * <p>
     * This method is a facade for invoking the
     * {@link ComponentPrototypesExtensionPoint#createComponentPrototypeCategory}
     * method.
     * </p>
     * 
     * @param configurationElement
     *        The component prototype category configuration element.
     * 
     * @return A new component prototype category.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code configurationElement} does not represent a legal
     *         component prototype category.
     */
    static ComponentPrototypeCategory createComponentPrototypeCategory(
        final IConfigurationElement configurationElement )
    {
        try
        {
            final Method method = ComponentPrototypesExtensionPoint.class.getDeclaredMethod( "createComponentPrototypeCategory", IConfigurationElement.class ); //$NON-NLS-1$
            method.setAccessible( true );
            final ComponentPrototypeCategory componentPrototypeCategory = (ComponentPrototypeCategory)method.invoke( null, configurationElement );
            assert componentPrototypeCategory != null;
            return componentPrototypeCategory;
        }
        catch( final InvocationTargetException e )
        {
            throw TaskUtils.launderThrowable( e.getCause() );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }
}

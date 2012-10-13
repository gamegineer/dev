/*
 * ComponentFactoriesExtensionPointFacade.java
 * Copyright 2008-2012 Gamegineer.org
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

package org.gamegineer.table.internal.ui.view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.IConfigurationElement;
import org.gamegineer.common.core.util.concurrent.TaskUtils;

/**
 * A facade for accessing private members of the
 * {@link ComponentFactoriesExtensionPoint} class that are required for testing.
 */
@ThreadSafe
final class ComponentFactoriesExtensionPointFacade
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentFactoriesExtensionPointFacade} class.
     */
    private ComponentFactoriesExtensionPointFacade()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new component factory category from the specified component
     * factory category configuration element.
     * 
     * <p>
     * This method is a facade for invoking the
     * {@link ComponentFactoriesExtensionPoint#createCategory} method.
     * </p>
     * 
     * @param configurationElement
     *        The component factory category configuration element; must not be
     *        {@code null}.
     * 
     * @return A new component factory category; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code configurationElement} does not represent a legal
     *         component factory category.
     */
    /* @NonNull */
    static ComponentFactoryCategory createCategory(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        try
        {
            final Method method = ComponentFactoriesExtensionPoint.class.getDeclaredMethod( "createCategory", IConfigurationElement.class ); //$NON-NLS-1$
            method.setAccessible( true );
            return (ComponentFactoryCategory)method.invoke( null, configurationElement );
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
     * Creates a new component factory from the specified component factory
     * configuration element.
     * 
     * <p>
     * This method is a facade for invoking the
     * {@link ComponentFactoriesExtensionPoint#createComponentFactory} method.
     * </p>
     * 
     * @param configurationElement
     *        The component factory configuration element; must not be
     *        {@code null}.
     * 
     * @return A new component factory; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code configurationElement} does not represent a legal
     *         component factory.
     */
    /* @NonNull */
    static ComponentFactory createComponentFactory(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        try
        {
            final Method method = ComponentFactoriesExtensionPoint.class.getDeclaredMethod( "createComponentFactory", IConfigurationElement.class ); //$NON-NLS-1$
            method.setAccessible( true );
            return (ComponentFactory)method.invoke( null, configurationElement );
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

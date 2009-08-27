/*
 * IComponentService.java
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
 * Created on Apr 10, 2008 at 9:24:56 PM.
 */

package org.gamegineer.common.core.services.component;

import java.util.Collection;

/**
 * A service used to create components based on a specification without
 * knowledge of their concrete type.
 * 
 * <p>
 * This interface represents the Product Trader participant in the Product
 * Trader pattern.
 * </p>
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IComponentService
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new component instance whose type matches the specified
     * specification using the specified creation context.
     * 
     * <p>
     * If multiple component factories satisfy the specification, an
     * implementation-defined algorithm will be used to select a factory to use
     * for component creation.
     * </p>
     * 
     * @param specification
     *        The specification that defines the type of component to create;
     *        must not be {@code null}.
     * @param context
     *        The context that defines how the new component instance is to be
     *        initialized; must not be {@code null}.
     * 
     * @return A new component instance; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code context} does not contain the required attributes or
     *         one of the attributes is of the wrong type.
     * @throws java.lang.NullPointerException
     *         If {@code specification} or {@code context} is {@code null}.
     * @throws
     *         org.gamegineer.common.core.services.component.ComponentCreationException
     *         If an error occurred during component creation.
     * @throws
     *         org.gamegineer.common.core.services.component.UnsupportedComponentSpecificationException
     *         If a component factory that matches the component specification
     *         could not be found.
     */
    /* @NonNull */
    public Object createComponent(
        /* @NonNull */
        IComponentSpecification specification,
        /* @NonNull */
        IComponentCreationContext context )
        throws ComponentCreationException, UnsupportedComponentSpecificationException;

    /**
     * Gets an immutable collection of all component factories registered with
     * this service.
     * 
     * @return An immutable collection of component factories registered with
     *         this service; never {@code null}. This collection is a snapshot
     *         of the factories available at the time of the call.
     */
    /* @NonNull */
    public Collection<IComponentFactory> getComponentFactories();

    /**
     * Gets an immutable collection of component factories that satisfy the
     * specified specification.
     * 
     * @param specification
     *        The specification that defines the component type of interest;
     *        must not be {@code null}.
     * 
     * @return An immutable collection of component factories that satisfy the
     *         specified specification; never {@code null}. This collection is
     *         a snapshot of the factories available at the time of the call.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code specification} is {@code null}.
     */
    /* @NonNull */
    public Collection<IComponentFactory> getComponentFactories(
        /* @NonNull */
        IComponentSpecification specification );

    /**
     * Registers the specified component factory.
     * 
     * <p>
     * This method does nothing if the specified component factory was
     * previously registered.
     * </p>
     * 
     * @param factory
     *        The component factory; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code factory} is {@code null}.
     */
    public void registerComponentFactory(
        /* @NonNull */
        IComponentFactory factory );

    /**
     * Unregisters the specified component factory.
     * 
     * <p>
     * This method does nothing if the specified component factory was not
     * previously registered.
     * </p>
     * 
     * @param factory
     *        The component factory; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code factory} is {@code null}.
     */
    public void unregisterComponentFactory(
        /* @NonNull */
        IComponentFactory factory );
}

/*
 * IComponentFactory.java
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
 * Created on Apr 10, 2008 at 9:25:04 PM.
 */

package org.gamegineer.common.core.services.component;

/**
 * A factory for creating components of a specific type.
 * 
 * <p>
 * A component factory defines a collection of attributes which are used by
 * component specifications to determine if the factory can satisfy a request
 * for the creation of a specific type of component. In this context, type may
 * not refer solely to the Java types implemented by the component but may
 * constitute other characteristics of the component such as speed guarantees
 * for a particular algorithm. Factories must <i>not</i> allow {@code null}
 * values to be stored in the attribute collection as this value is used to
 * indicate an undefined attribute.
 * </p>
 * 
 * <p>
 * The only thread-safety guarantee a component factory implementation must make
 * is that each method will execute atomically. A test-and-get-style sequence of
 * calls to {@code containsAttribute} and {@code getAttribute} will only be
 * thread-safe if the underlying attribute collection is immutable. However,
 * such a call sequence is unnecessary as {@code getAttribute} will return
 * {@code null} if the attribute does not exist. There can be no confusion as to
 * whether {@code null} represents the attribute value or signals an attribute
 * that does not exist because factories do not allow {@code null} attribute
 * values.
 * </p>
 * 
 * <p>
 * This interface represents the Creator participant in the Product Trader
 * pattern.
 * </p>
 * 
 * <p>
 * This interface is intended to be implemented but not extended by clients.
 * </p>
 */
public interface IComponentFactory
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Indicates this factory contains the specified attribute.
     * 
     * @param name
     *        The name of the attribute; must not be {@code null}.
     * 
     * @return {@code true} if this factory contains the specified attribute;
     *         otherwise {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    public boolean containsAttribute(
        /* @NonNull */
        String name );

    /**
     * Creates a new component instance using the specified creation context.
     * 
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
     *         If {@code context} is {@code null}.
     * @throws
     *         org.gamegineer.common.core.services.component.ComponentCreationException
     *         If an error occurred during component creation.
     */
    /* @NonNull */
    public Object createComponent(
        /* @NonNull */
        IComponentCreationContext context )
        throws ComponentCreationException;

    /**
     * Gets the value of the attribute with the specified name.
     * 
     * @param name
     *        The name of the attribute; must not be {@code null}.
     * 
     * @return The value of the specified attribute or {@code null} if the
     *         attribute does not exist in this factory.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    /* @Nullable */
    public Object getAttribute(
        /* @NonNull */
        String name );
}

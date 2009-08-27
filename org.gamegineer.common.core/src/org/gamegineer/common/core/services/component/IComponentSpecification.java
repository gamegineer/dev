/*
 * IComponentSpecification.java
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
 * Created on Apr 10, 2008 at 9:24:46 PM.
 */

package org.gamegineer.common.core.services.component;

/**
 * A specification for the type of component a client wishes to instantiate.
 * 
 * <p>
 * Clients create and submit a specification to the Component Service in order
 * to instantiate a particular type of component. The role of the specification
 * is to examine a collection of component factories to determine which ones
 * satisfy the requirements of the specification.
 * </p>
 * 
 * <p>
 * A specification only serves to define the type of component. A component
 * creation context (see {@link IComponentCreationContext}) defines the
 * attributes used to initialize a component instance.
 * </p>
 * 
 * <p>
 * This interface represents the Specification participant in the Product Trader
 * pattern.
 * </p>
 * 
 * <p>
 * This interface is intended to be implemented and extended by clients.
 * </p>
 */
public interface IComponentSpecification
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Indicates the specified component factory matches this specification.
     * 
     * @param factory
     *        The component factory to test; must not be {@code null}.
     * 
     * @return {@code true} if the specified component factory matches this
     *         specification; otherwise {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code factory} is {@code null}.
     */
    public boolean matches(
        /* @NonNull */
        IComponentFactory factory );
}

/*
 * IAttributeAccessor.java
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
 * Created on May 17, 2008 at 9:52:06 PM.
 */

package org.gamegineer.common.core.services.component.util.attribute;

/**
 * Provides the ability to access the attributes of any component service
 * participant that exposes a generic attribute collection.
 * 
 * <p>
 * The only thread-safety guarantee an implementation must make is that each
 * method will execute atomically with respect to the underlying attribute
 * collection.
 * </p>
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IAttributeAccessor
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Indicates the underlying collection contains the specified attribute.
     * 
     * @param name
     *        The name of the attribute; must not be {@code null}.
     * 
     * @return {@code true} if the underlying collection contains the specified
     *         attribute; otherwise {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    public boolean containsAttribute(
        /* @NonNull */
        String name );

    /**
     * Gets the value of the attribute with the specified name from the
     * underlying collection.
     * 
     * @param name
     *        The name of the attribute; must not be {@code null}.
     * 
     * @return The value of the attribute or {@code null} if the attribute does
     *         not exist in the underlying collection.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    /* @Nullable */
    public Object getAttribute(
        /* @NonNull */
        String name );
}

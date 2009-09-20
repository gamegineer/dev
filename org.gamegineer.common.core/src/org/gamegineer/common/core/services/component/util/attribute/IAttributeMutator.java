/*
 * IAttributeMutator.java
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
 * Created on May 17, 2008 at 9:45:28 PM.
 */

package org.gamegineer.common.core.services.component.util.attribute;

/**
 * Provides the ability to modify the attributes of any component service
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
public interface IAttributeMutator
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Sets the value of the attribute with the specified name.
     * 
     * <p>
     * If the attribute does not exist, it will be added. If the attribute does
     * exist, its current value will be replaced with the new value.
     * </p>
     * 
     * @param name
     *        The name of the attribute; must not be {@code null}.
     * @param value
     *        The value of the attribute; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code name} or {@code value} is {@code null}.
     */
    public void setAttribute(
        /* @NonNull */
        String name,
        /* @NonNull */
        Object value );
}

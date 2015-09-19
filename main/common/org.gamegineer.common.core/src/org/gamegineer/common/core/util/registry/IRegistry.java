/*
 * IRegistry.java
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
 * Created on Nov 15, 2012 at 9:33:58 PM.
 */

package org.gamegineer.common.core.util.registry;

import java.util.Collection;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A registry for the management of objects.
 * 
 * @param <ObjectIdType>
 *        The type of object used to identify an object managed by the registry.
 * @param <ObjectType>
 *        The type of object managed by the registry.
 */
public interface IRegistry<ObjectIdType, ObjectType>
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the object with the specified identifier.
     * 
     * @param id
     *        The object identifier; must not be {@code null}.
     * 
     * @return The object with the specified identifier or {@code null} if no
     *         such identifier is registered.
     */
    public @Nullable ObjectType getObject(
        ObjectIdType id );

    /**
     * Gets a collection of all objects managed by this registry.
     * 
     * @return A collection of all objects managed by this registry; never
     *         {@code null}. This collection is a snapshot of the objects
     *         registered at the time of the call.
     */
    public Collection<ObjectType> getObjects();

    /**
     * Registers the specified object.
     * 
     * @param object
     *        The object; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If an object with the same identifier is already registered.
     */
    public void registerObject(
        ObjectType object );

    /**
     * Unregisters the specified object.
     * 
     * @param object
     *        The object; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the specified object was not previously registered.
     */
    public void unregisterObject(
        ObjectType object );
}

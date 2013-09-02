/*
 * IPersistenceDelegateRegistry.java
 * Copyright 2008-2011 Gamegineer contributors and others.
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
 * Created on Apr 29, 2010 at 11:39:31 PM.
 */

package org.gamegineer.common.persistence.serializable;

import java.util.Set;

/**
 * A service for the management and discovery of Java object serialization
 * framework persistence delegates.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IPersistenceDelegateRegistry
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the persistence delegate associated with the specified type.
     * 
     * @param type
     *        The type associated with the persistence delegate; must not be
     *        {@code null}.
     * 
     * @return The persistence delegate associated with the specified type or
     *         {@code null} if no such type is registered.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code type} is {@code null}.
     */
    /* @Nullable */
    public IPersistenceDelegate getPersistenceDelegate(
        /* @NonNull */
        Class<?> type );

    /**
     * Gets the persistence delegate associated with the specified type name.
     * 
     * @param typeName
     *        The name of the type associated with the persistence delegate;
     *        must not be {@code null}.
     * 
     * @return The persistence delegate associated with the specified type name
     *         or {@code null} if no such type name is registered.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code typeName} is {@code null}.
     */
    /* @Nullable */
    public IPersistenceDelegate getPersistenceDelegate(
        /* @NonNull */
        String typeName );

    /**
     * Gets a collection of all type names for which a persistence delegate has
     * been registered with this service.
     * 
     * @return A collection of all type names for which a persistence delegate
     *         has been registered with this service; never {@code null}. This
     *         collection is a snapshot of the persistence delegates registered
     *         at the time of the call.
     */
    /* @NonNull */
    public Set<String> getTypeNames();

    /**
     * Registers the specified persistence delegate for the specified type.
     * 
     * @param type
     *        The type associated with the persistence delegate; must not be
     *        {@code null}.
     * @param persistenceDelegate
     *        The persistence delegate; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If a persistence delegate is already registered for the specified
     *         type.
     * @throws java.lang.NullPointerException
     *         If {@code type} or {@code persistenceDelegate} is {@code null}.
     */
    public void registerPersistenceDelegate(
        /* @NonNull */
        Class<?> type,
        /* @NonNull */
        IPersistenceDelegate persistenceDelegate );

    /**
     * Unregisters the persistence delegate for the specified type.
     * 
     * @param type
     *        The type associated with the persistence delegate; must not be
     *        {@code null}.
     * @param persistenceDelegate
     *        The persistence delegate; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the specified persistence delegate was not previously
     *         registered for the specified type.
     * @throws java.lang.NullPointerException
     *         If {@code type} or {@code persistenceDelegate} is {@code null}.
     */
    public void unregisterPersistenceDelegate(
        /* @NonNull */
        Class<?> type,
        /* @NonNull */
        IPersistenceDelegate persistenceDelegate );
}

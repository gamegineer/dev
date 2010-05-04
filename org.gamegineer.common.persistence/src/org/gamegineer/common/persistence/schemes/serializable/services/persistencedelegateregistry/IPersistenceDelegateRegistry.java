/*
 * IPersistenceDelegateRegistry.java
 * Copyright 2008-2010 Gamegineer.org
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

package org.gamegineer.common.persistence.schemes.serializable.services.persistencedelegateregistry;

import java.util.Set;
import org.gamegineer.common.persistence.schemes.serializable.IPersistenceDelegate;

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
     * Gets a collection of all class names for which a persistence delegate has
     * been registered with this service.
     * 
     * @return A collection of all class names for which a persistence delegate
     *         has been registered with this server; never {@code null}. This
     *         collection is a snapshot of the persistence delegates registered
     *         at the time of the call.
     */
    /* @NonNull */
    public Set<String> getClassNames();

    /**
     * Gets the persistence delegate associated with the specified class name.
     * 
     * @param className
     *        The name of the class associated with the persistence delegate;
     *        must not be {@code null}.
     * 
     * @return The persistence delegate associated with the specified class name
     *         or {@code null} if no such class name is registered.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code className} is {@code null}.
     */
    /* @Nullable */
    public IPersistenceDelegate getPersistenceDelegate(
        /* @NonNull */
        String className );

    /**
     * Registers the specified persistence delegate.
     * 
     * <p>
     * This method does nothing if a persistence delegate associated with the
     * same class name was previously registered.
     * </p>
     * 
     * @param className
     *        The name of the class associated with the persistence delegate;
     *        must not be {@code null}.
     * @param persistenceDelegate
     *        The persistence delegate; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code className} or {@code persistenceDelegate} is {@code
     *         null}.
     */
    public void registerPersistenceDelegate(
        /* @NonNull */
        String className,
        /* @NonNull */
        IPersistenceDelegate persistenceDelegate );

    /**
     * Unregisters the persistence delegate associated with the specified class
     * name.
     * 
     * <p>
     * This method does nothing if the specified persistence delegate was not
     * previously registered for the specified class name.
     * </p>
     * 
     * @param className
     *        The name of the class associated with the persistence delegate;
     *        must not be {@code null}.
     * @param persistenceDelegate
     *        The persistence delegate; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code className} or {@code persistenceDelegate} is {@code
     *         null}.
     */
    public void unregisterPersistenceDelegate(
        /* @NonNull */
        String className,
        /* @NonNull */
        IPersistenceDelegate persistenceDelegate );
}

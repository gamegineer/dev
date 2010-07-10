/*
 * Messages.java
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
 * Created on May 1, 2010 at 10:30:20 PM.
 */

package org.gamegineer.common.internal.persistence.schemes.serializable.services.persistencedelegateregistry;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.ServiceReference;

/**
 * A utility class to manage localized messages for the package.
 */
@ThreadSafe
final class Messages
    extends NLS
{
    // ======================================================================
    // Fields
    // ======================================================================

    // --- PersistenceDelegateProxy -----------------------------------------

    /** The actual object is not available. */
    public static String PersistenceDelegateProxy_getActualPersistenceDelegate_actualObjectNotAvailable;

    /** The proxy has been disposed. */
    public static String PersistenceDelegateProxy_getActualPersistenceDelegate_proxyDisposed;

    // --- PersistenceDelegateRegistry --------------------------------------

    /**
     * The persistence delegate associated with the specified service reference
     * has no delegators.
     */
    public static String PersistenceDelegateRegistry_getDelegatorTypeNames_noDelegators;

    /** A persistence delegate is already registered for the specified type. */
    public static String PersistenceDelegateRegistry_registerPersistenceDelegate_type_registered;

    /**
     * The persistence delegate associated with the specified service reference
     * cannot be registered for the specified type.
     */
    public static String PersistenceDelegateRegistry_registerPersistenceDelegateFromServiceReference_registrationFailed;

    /** The persistence delegate is not registered for the specified type. */
    public static String PersistenceDelegateRegistry_unregisterPersistenceDelegate_type_unregistered;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code Messages} class.
     */
    static
    {
        NLS.initializeMessages( Messages.class.getName(), Messages.class );
    }

    /**
     * Initializes a new instance of the {@code Messages} class.
     */
    private Messages()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    // --- PersistenceDelegateRegistry --------------------------------------

    /**
     * Gets the formatted message indicating the persistence delegate associated
     * with the specified service reference has no delegators.
     * 
     * @param persistenceDelegateReference
     *        The persistence delegate reference; must not be {@code null}.
     * 
     * @return The formatted message indicating the persistence delegate
     *         associated with the specified service reference has no
     *         delegators; never {@code null}.
     */
    /* @NonNull */
    static String PersistenceDelegateRegistry_getDelegatorTypeNames_noDelegators(
        /* @NonNull */
        final ServiceReference persistenceDelegateReference )
    {
        return bind( PersistenceDelegateRegistry_getDelegatorTypeNames_noDelegators, persistenceDelegateReference );
    }

    /**
     * Gets the formatted message indicating a persistence delegate is already
     * registered for the specified type.
     * 
     * @param typeName
     *        The name of the type associated with the persistence delegate;
     *        must not be {@code null}.
     * 
     * @return The formatted message indicating a persistence delegate is
     *         already registered for the specified type; never {@code null}.
     */
    /* @NonNull */
    static String PersistenceDelegateRegistry_registerPersistenceDelegate_type_registered(
        /* @NonNull */
        final String typeName )
    {
        return bind( PersistenceDelegateRegistry_registerPersistenceDelegate_type_registered, typeName );
    }

    /**
     * Gets the formatted message indicating the persistence delegate associated
     * with the specified service reference cannot be registered for the
     * specified type.
     * 
     * @param typeName
     *        The name of the type associated with the persistence delegate;
     *        must not be {@code null}.
     * @param persistenceDelegateReference
     *        The persistence delegate reference; must not be {@code null}.
     * 
     * @return The formatted message indicating the persistence delegate
     *         associated with the specified service reference cannot be
     *         registered for the specified type; never {@code null}.
     */
    /* @NonNull */
    static String PersistenceDelegateRegistry_registerPersistenceDelegateFromServiceReference_registrationFailed(
        /* @NonNull */
        final String typeName,
        /* @NonNull */
        final ServiceReference persistenceDelegateReference )
    {
        return bind( PersistenceDelegateRegistry_registerPersistenceDelegateFromServiceReference_registrationFailed, persistenceDelegateReference, typeName );
    }

    /**
     * Gets the formatted message indicating the persistence delegate is not
     * registered for the specified type.
     * 
     * @param typeName
     *        The name of the type associated with the persistence delegate;
     *        must not be {@code null}.
     * 
     * @return The formatted message indicating the persistence delegate is not
     *         registered for the specified type; never {@code null}.
     */
    /* @NonNull */
    static String PersistenceDelegateRegistry_unregisterPersistenceDelegate_type_unregistered(
        /* @NonNull */
        final String typeName )
    {
        return bind( PersistenceDelegateRegistry_unregisterPersistenceDelegate_type_unregistered, typeName );
    }
}

/*
 * PersistenceDelegateRegistry.java
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
 * Created on May 1, 2010 at 10:19:31 PM.
 */

package org.gamegineer.common.internal.persistence.serializable.services.persistencedelegateregistry;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.internal.persistence.Debug;
import org.gamegineer.common.internal.persistence.Loggers;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegate;
import org.gamegineer.common.persistence.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry;
import org.gamegineer.common.persistence.serializable.services.persistencedelegateregistry.PersistenceDelegateRegistryConstants;
import org.osgi.framework.ServiceReference;

/**
 * Implementation of
 * {@link org.gamegineer.common.persistence.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry}
 * .
 */
@ThreadSafe
public final class PersistenceDelegateRegistry
    implements IPersistenceDelegateRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The instance lock. */
    private final Object lock_;

    /**
     * The collection of persistence delegates registered from the service
     * registry. The key is the persistence delegate service reference; the
     * value is the persistence delegate proxy registration token.
     */
    @GuardedBy( "lock_" )
    private final Map<ServiceReference, PersistenceDelegateProxyRegistration> persistenceDelegateProxyRegistrations_;

    /**
     * The collection of registered persistence delegates. The key is the type
     * name; the value is the associated persistence delegate.
     */
    @GuardedBy( "lock_" )
    private final Map<String, IPersistenceDelegate> persistenceDelegates_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PersistenceDelegateRegistry}
     * class.
     */
    public PersistenceDelegateRegistry()
    {
        lock_ = new Object();
        persistenceDelegateProxyRegistrations_ = new HashMap<ServiceReference, PersistenceDelegateProxyRegistration>();
        persistenceDelegates_ = new HashMap<String, IPersistenceDelegate>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the collection of delegator type names associated with the specified
     * persistence delegate service reference.
     * 
     * @param persistenceDelegateReference
     *        The persistence delegate service reference; must not be {@code
     *        null}.
     * 
     * @return The collection of delegator type names associated with the
     *         specified persistence delegate service reference; never {@code
     *         null}.
     */
    /* @NonNull */
    private static Set<String> getDelegatorTypeNames(
        /* @NonNull */
        final ServiceReference persistenceDelegateReference )
    {
        assert persistenceDelegateReference != null;

        final Object propertyValue = persistenceDelegateReference.getProperty( PersistenceDelegateRegistryConstants.PROPERTY_DELEGATORS );
        if( propertyValue instanceof String )
        {
            return Collections.singleton( (String)propertyValue );
        }
        else if( propertyValue instanceof String[] )
        {
            return new HashSet<String>( Arrays.asList( (String[])propertyValue ) );
        }

        Loggers.getDefaultLogger().warning( Messages.PersistenceDelegateRegistry_getDelegatorTypeNames_noDelegators( persistenceDelegateReference ) );
        return Collections.emptySet();
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry#getPersistenceDelegate(java.lang.Class)
     */
    @Override
    public IPersistenceDelegate getPersistenceDelegate(
        final Class<?> type )
    {
        assertArgumentNotNull( type, "type" ); //$NON-NLS-1$

        return getPersistenceDelegate( type.getName() );
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry#getPersistenceDelegate(java.lang.String)
     */
    @Override
    public IPersistenceDelegate getPersistenceDelegate(
        final String typeName )
    {
        assertArgumentNotNull( typeName, "typeName" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            return persistenceDelegates_.get( typeName );
        }
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry#getTypeNames()
     */
    @Override
    public Set<String> getTypeNames()
    {
        synchronized( lock_ )
        {
            return new HashSet<String>( persistenceDelegates_.keySet() );
        }
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry#registerPersistenceDelegate(java.lang.Class, org.gamegineer.common.persistence.serializable.IPersistenceDelegate)
     */
    @Override
    public void registerPersistenceDelegate(
        final Class<?> type,
        final IPersistenceDelegate persistenceDelegate )
    {
        assertArgumentNotNull( type, "type" ); //$NON-NLS-1$
        assertArgumentNotNull( persistenceDelegate, "persistenceDelegate" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            registerPersistenceDelegate( type.getName(), persistenceDelegate );
        }
    }

    /**
     * Registers the persistence delegate associated with the specified service
     * reference.
     * 
     * <p>
     * This method attempts to register the persistence delegate associated with
     * the specified service reference for each type specified in the {@code
     * PersistenceDelegateRegistryConstants.PROPERTY_DELEGATORS} property of the
     * service registration. If a persistence delegate has already been
     * registered for a type associated with the service registration, this
     * method logs the collision but otherwise continues on normally attempting
     * to register the persistence delegate for any remaining types.
     * </p>
     * 
     * @param persistenceDelegateReference
     *        The persistence delegate service reference; must not be {@code
     *        null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code persistenceDelegateReference} is {@code null}.
     */
    public void registerPersistenceDelegate(
        /* @NonNull */
        final ServiceReference persistenceDelegateReference )
    {
        assertArgumentNotNull( persistenceDelegateReference, "persistenceDelegateReference" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            final PersistenceDelegateProxy persistenceDelegateProxy = new PersistenceDelegateProxy( persistenceDelegateReference );
            final Set<String> typeNames = new HashSet<String>();
            for( final String typeName : getDelegatorTypeNames( persistenceDelegateReference ) )
            {
                try
                {
                    registerPersistenceDelegate( typeName, persistenceDelegateProxy );
                    typeNames.add( typeName );
                }
                catch( final IllegalArgumentException e )
                {
                    Loggers.getDefaultLogger().log( Level.WARNING, Messages.PersistenceDelegateRegistry_registerPersistenceDelegateFromServiceReference_registrationFailed( typeName, persistenceDelegateReference ), e );
                }
            }

            if( !typeNames.isEmpty() )
            {
                final PersistenceDelegateProxyRegistration persistenceDelegateProxyRegistration = new PersistenceDelegateProxyRegistration( typeNames, persistenceDelegateProxy );
                persistenceDelegateProxyRegistrations_.put( persistenceDelegateReference, persistenceDelegateProxyRegistration );
            }
        }
    }

    /**
     * Registers the specified persistence delegate for the specified type.
     * 
     * @param typeName
     *        The name of the type associated with the persistence delegate;
     *        must not be {@code null}.
     * @param persistenceDelegate
     *        The persistence delegate; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If a persistence delegate is already registered for the specified
     *         type.
     */
    @GuardedBy( "lock_" )
    private void registerPersistenceDelegate(
        /* @NonNull */
        final String typeName,
        /* @NonNull */
        final IPersistenceDelegate persistenceDelegate )
    {
        assert typeName != null;
        assert persistenceDelegate != null;
        assert Thread.holdsLock( lock_ );

        assertArgumentLegal( !persistenceDelegates_.containsKey( typeName ), "typeName", Messages.PersistenceDelegateRegistry_registerPersistenceDelegate_type_registered( typeName ) ); //$NON-NLS-1$
        persistenceDelegates_.put( typeName, persistenceDelegate );
        Debug.getDefault().trace( Debug.OPTION_SERIALIZABLE, String.format( "Registered persistence delegate '%1$s' for type '%2$s'", persistenceDelegate, typeName ) ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry#unregisterPersistenceDelegate(java.lang.Class, org.gamegineer.common.persistence.serializable.IPersistenceDelegate)
     */
    @Override
    public void unregisterPersistenceDelegate(
        final Class<?> type,
        final IPersistenceDelegate persistenceDelegate )
    {
        assertArgumentNotNull( type, "type" ); //$NON-NLS-1$
        assertArgumentNotNull( persistenceDelegate, "persistenceDelegate" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            unregisterPersistenceDelegate( type.getName(), persistenceDelegate );
        }
    }

    /**
     * Unregisters the persistence delegate associated with the specified
     * service reference.
     * 
     * <p>
     * This method attempts to unregister the persistence delegate associated
     * with the specified service reference for each type for which it was
     * registered in a previous call to
     * {@link #registerPersistenceDelegate(ServiceReference)}.
     * </p>
     * 
     * @param persistenceDelegateReference
     *        The persistence delegate service reference; must not be {@code
     *        null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code persistenceDelegateReference} is {@code null}.
     */
    public void unregisterPersistenceDelegate(
        /* @NonNull */
        final ServiceReference persistenceDelegateReference )
    {
        assertArgumentNotNull( persistenceDelegateReference, "persistenceDelegateReference" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            final PersistenceDelegateProxyRegistration persistenceDelegateProxyRegistration = persistenceDelegateProxyRegistrations_.remove( persistenceDelegateReference );
            if( persistenceDelegateProxyRegistration != null )
            {
                final PersistenceDelegateProxy persistenceDelegateProxy = persistenceDelegateProxyRegistration.persistenceDelegateProxy;
                for( final String typeName : persistenceDelegateProxyRegistration.typeNames )
                {
                    unregisterPersistenceDelegate( typeName, persistenceDelegateProxy );
                }
                persistenceDelegateProxy.dispose();
            }
        }
    }

    /**
     * Unregisters the persistence delegate for the specified type.
     * 
     * @param typeName
     *        The name of the type associated with the persistence delegate;
     *        must not be {@code null}.
     * @param persistenceDelegate
     *        The persistence delegate; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the specified persistence delegate was not previously
     *         registered for the specified type.
     */
    @GuardedBy( "lock_" )
    private void unregisterPersistenceDelegate(
        /* @NonNull */
        final String typeName,
        /* @NonNull */
        final IPersistenceDelegate persistenceDelegate )
    {
        assert typeName != null;
        assert persistenceDelegate != null;
        assert Thread.holdsLock( lock_ );

        assertArgumentLegal( persistenceDelegate.equals( persistenceDelegates_.get( typeName ) ), "typeName", Messages.PersistenceDelegateRegistry_unregisterPersistenceDelegate_type_unregistered( typeName ) ); //$NON-NLS-1$
        persistenceDelegates_.remove( typeName );
        Debug.getDefault().trace( Debug.OPTION_SERIALIZABLE, String.format( "Unregistered persistence delegate '%1$s' for type '%2$s'", persistenceDelegate, typeName ) ); //$NON-NLS-1$
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A registration token for a persistence delegate registered from the
     * service registry.
     */
    @Immutable
    private static final class PersistenceDelegateProxyRegistration
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The persistence delegate proxy. */
        final PersistenceDelegateProxy persistenceDelegateProxy;

        /**
         * The collection of type names under which the persistence delegate is
         * registered.
         */
        final Set<String> typeNames;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code
         * PersistenceDelegateProxyRegistration} class.
         * 
         * @param typeNames
         *        The collection of type names under which the persistence
         *        delegate is registered; must not be {@code null}.
         * @param persistenceDelegateProxy
         *        The persistence delegate proxy; must not be {@code null}.
         */
        PersistenceDelegateProxyRegistration(
            /* @NonNull */
            @SuppressWarnings( "hiding" )
            final Set<String> typeNames,
            /* @NonNull */
            @SuppressWarnings( "hiding" )
            final PersistenceDelegateProxy persistenceDelegateProxy )
        {
            assert typeNames != null;
            assert persistenceDelegateProxy != null;

            this.typeNames = Collections.unmodifiableSet( new HashSet<String>( typeNames ) );
            this.persistenceDelegateProxy = persistenceDelegateProxy;
        }
    }
}
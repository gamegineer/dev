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

package org.gamegineer.common.internal.persistence.schemes.serializable.services.persistencedelegateregistry;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.gamegineer.common.core.runtime.Platform;
import org.gamegineer.common.internal.persistence.Activator;
import org.gamegineer.common.internal.persistence.Loggers;
import org.gamegineer.common.persistence.schemes.serializable.IPersistenceDelegate;
import org.gamegineer.common.persistence.schemes.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry;

/**
 * Implementation of
 * {@link org.gamegineer.common.persistence.schemes.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry}
 * .
 */
@ThreadSafe
public final class PersistenceDelegateRegistry
    implements IPersistenceDelegateRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The extension point attribute specifying the persistence delegate and
     * delegator classes.
     */
    private static final String ATTR_CLASS = "class"; //$NON-NLS-1$

    /**
     * The extension point element specifying the class that delegates
     * responsibility for its persistence to a persistence delegate.
     */
    private static final String ELEMENT_DELEGATOR = "delegator"; //$NON-NLS-1$

    /** The collection of persistence delegates directly managed by this object. */
    private final ConcurrentMap<String, IPersistenceDelegate> persistenceDelegates_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PersistenceDelegateRegistry}
     * class.
     */
    public PersistenceDelegateRegistry()
    {
        persistenceDelegates_ = new ConcurrentHashMap<String, IPersistenceDelegate>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets a map view of all foreign persistence delegates not directly managed
     * by this object.
     * 
     * @return A map view of all foreign persistence delegates not directly
     *         managed by this object; never {@code null}.
     */
    /* @NonNull */
    private static Map<String, IPersistenceDelegate> getForeignPersistenceDelegateMap()
    {
        final Map<String, IPersistenceDelegate> persistenceDelegates = new HashMap<String, IPersistenceDelegate>();
        for( final IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor( Activator.SYMBOLIC_NAME, Activator.EXTENSION_SERIALIZABLE_PERSISTENCE_DELEGATES ) )
        {
            try
            {
                final IPersistenceDelegate persistenceDelegate = (IPersistenceDelegate)configurationElement.createExecutableExtension( ATTR_CLASS );
                for( final IConfigurationElement childConfigurationElement : configurationElement.getChildren( ELEMENT_DELEGATOR ) )
                {
                    final String delegatorClassName = childConfigurationElement.getAttribute( ATTR_CLASS );
                    persistenceDelegates.put( delegatorClassName, persistenceDelegate );
                }
            }
            catch( final CoreException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.PersistenceDelegateRegistry_getForeignPersistenceDelegateMap_parseError( configurationElement.getAttribute( ATTR_CLASS ) ), e );
            }
        }
        return persistenceDelegates;
    }

    /*
     * @see org.gamegineer.common.persistence.schemes.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry#getPersistenceDelegate(java.lang.Class)
     */
    @Override
    public IPersistenceDelegate getPersistenceDelegate(
        final Class<?> type )
    {
        assertArgumentNotNull( type, "type" ); //$NON-NLS-1$

        return getPersistenceDelegate( type.getName() );
    }

    /*
     * @see org.gamegineer.common.persistence.schemes.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry#getPersistenceDelegate(java.lang.String)
     */
    @Override
    public IPersistenceDelegate getPersistenceDelegate(
        final String typeName )
    {
        assertArgumentNotNull( typeName, "typeName" ); //$NON-NLS-1$

        return getPersistenceDelegateMap().get( typeName );
    }

    /**
     * Gets a map view of all persistence delegates known by this object.
     * 
     * @return A map view of all persistence delegates known by this object;
     *         never {@code null}.
     */
    /* @NonNull */
    private Map<String, IPersistenceDelegate> getPersistenceDelegateMap()
    {
        final Map<String, IPersistenceDelegate> persistenceDelegates = new HashMap<String, IPersistenceDelegate>( persistenceDelegates_ );
        for( final Map.Entry<String, IPersistenceDelegate> entry : getForeignPersistenceDelegateMap().entrySet() )
        {
            final String className = entry.getKey();
            final IPersistenceDelegate persistenceDelegate = entry.getValue();
            if( persistenceDelegates.containsKey( className ) )
            {
                Loggers.DEFAULT.warning( Messages.PersistenceDelegateRegistry_getPersistenceDelegateMap_duplicateClassName( className ) );
            }
            else
            {
                persistenceDelegates.put( className, persistenceDelegate );
            }
        }
        return persistenceDelegates;
    }

    /*
     * @see org.gamegineer.common.persistence.schemes.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry#getTypeNames()
     */
    @Override
    public Set<String> getTypeNames()
    {
        return new HashSet<String>( persistenceDelegates_.keySet() );
    }

    /*
     * @see org.gamegineer.common.persistence.schemes.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry#registerPersistenceDelegate(java.lang.Class, org.gamegineer.common.persistence.schemes.serializable.IPersistenceDelegate)
     */
    @Override
    public void registerPersistenceDelegate(
        final Class<?> type,
        final IPersistenceDelegate persistenceDelegate )
    {
        assertArgumentNotNull( type, "type" ); //$NON-NLS-1$
        assertArgumentNotNull( persistenceDelegate, "persistenceDelegate" ); //$NON-NLS-1$

        persistenceDelegates_.putIfAbsent( type.getName(), persistenceDelegate );
    }

    @Override
    public void unregisterPersistenceDelegate(
        final Class<?> type,
        final IPersistenceDelegate persistenceDelegate )
    {
        assertArgumentNotNull( type, "type" ); //$NON-NLS-1$
        assertArgumentNotNull( persistenceDelegate, "persistenceDelegate" ); //$NON-NLS-1$

        persistenceDelegates_.remove( type.getName(), persistenceDelegate );
    }
}

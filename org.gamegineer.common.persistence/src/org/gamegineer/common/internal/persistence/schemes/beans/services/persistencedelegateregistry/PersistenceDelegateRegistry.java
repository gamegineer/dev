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
 * Created on May 6, 2010 at 11:45:43 PM.
 */

package org.gamegineer.common.internal.persistence.schemes.beans.services.persistencedelegateregistry;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.beans.PersistenceDelegate;
import java.util.Collections;
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
import org.eclipse.core.runtime.IExtensionRegistry;
import org.gamegineer.common.core.runtime.Platform;
import org.gamegineer.common.internal.persistence.BundleConstants;
import org.gamegineer.common.internal.persistence.Loggers;
import org.gamegineer.common.persistence.schemes.beans.services.persistencedelegateregistry.IPersistenceDelegateRegistry;

/**
 * Implementation of
 * {@link org.gamegineer.common.persistence.schemes.beans.services.persistencedelegateregistry.IPersistenceDelegateRegistry}
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
    private final ConcurrentMap<String, PersistenceDelegate> persistenceDelegates_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PersistenceDelegateRegistry}
     * class.
     */
    public PersistenceDelegateRegistry()
    {
        persistenceDelegates_ = new ConcurrentHashMap<String, PersistenceDelegate>();
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
    private static Map<String, PersistenceDelegate> getForeignPersistenceDelegateMap()
    {
        final IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
        if( extensionRegistry == null )
        {
            Loggers.getDefaultLogger().warning( Messages.PersistenceDelegateRegistry_getForeignPersistenceDelegateMap_noExtensionRegistry );
            return Collections.emptyMap();
        }

        final Map<String, PersistenceDelegate> persistenceDelegates = new HashMap<String, PersistenceDelegate>();
        for( final IConfigurationElement configurationElement : extensionRegistry.getConfigurationElementsFor( BundleConstants.SYMBOLIC_NAME, BundleConstants.EXTENSION_BEANS_PERSISTENCE_DELEGATES ) )
        {
            try
            {
                final PersistenceDelegate persistenceDelegate = (PersistenceDelegate)configurationElement.createExecutableExtension( ATTR_CLASS );
                for( final IConfigurationElement childConfigurationElement : configurationElement.getChildren( ELEMENT_DELEGATOR ) )
                {
                    final String delegatorClassName = childConfigurationElement.getAttribute( ATTR_CLASS );
                    persistenceDelegates.put( delegatorClassName, persistenceDelegate );
                }
            }
            catch( final CoreException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.PersistenceDelegateRegistry_getForeignPersistenceDelegateMap_parseError( configurationElement.getAttribute( ATTR_CLASS ) ), e );
            }
        }
        return persistenceDelegates;
    }

    /*
     * @see org.gamegineer.common.persistence.schemes.beans.services.persistencedelegateregistry.IPersistenceDelegateRegistry#getPersistenceDelegate(java.lang.Class)
     */
    @Override
    public PersistenceDelegate getPersistenceDelegate(
        final Class<?> type )
    {
        assertArgumentNotNull( type, "type" ); //$NON-NLS-1$

        return getPersistenceDelegate( type.getName() );
    }

    /*
     * @see org.gamegineer.common.persistence.schemes.beans.services.persistencedelegateregistry.IPersistenceDelegateRegistry#getPersistenceDelegate(java.lang.String)
     */
    @Override
    public PersistenceDelegate getPersistenceDelegate(
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
    private Map<String, PersistenceDelegate> getPersistenceDelegateMap()
    {
        final Map<String, PersistenceDelegate> persistenceDelegates = new HashMap<String, PersistenceDelegate>( persistenceDelegates_ );
        for( final Map.Entry<String, PersistenceDelegate> entry : getForeignPersistenceDelegateMap().entrySet() )
        {
            final String className = entry.getKey();
            final PersistenceDelegate persistenceDelegate = entry.getValue();
            if( persistenceDelegates.containsKey( className ) )
            {
                Loggers.getDefaultLogger().warning( Messages.PersistenceDelegateRegistry_getPersistenceDelegateMap_duplicateClassName( className ) );
            }
            else
            {
                persistenceDelegates.put( className, persistenceDelegate );
            }
        }
        return persistenceDelegates;
    }

    /*
     * @see org.gamegineer.common.persistence.schemes.beans.services.persistencedelegateregistry.IPersistenceDelegateRegistry#getTypeNames()
     */
    @Override
    public Set<String> getTypeNames()
    {
        return new HashSet<String>( persistenceDelegates_.keySet() );
    }

    /*
     * @see org.gamegineer.common.persistence.schemes.beans.services.persistencedelegateregistry.IPersistenceDelegateRegistry#registerPersistenceDelegate(java.lang.Class, java.beans.PersistenceDelegate)
     */
    @Override
    public void registerPersistenceDelegate(
        final Class<?> type,
        final PersistenceDelegate persistenceDelegate )
    {
        assertArgumentNotNull( type, "type" ); //$NON-NLS-1$
        assertArgumentNotNull( persistenceDelegate, "persistenceDelegate" ); //$NON-NLS-1$

        persistenceDelegates_.putIfAbsent( type.getName(), persistenceDelegate );
    }

    /*
     * @see org.gamegineer.common.persistence.schemes.beans.services.persistencedelegateregistry.IPersistenceDelegateRegistry#unregisterPersistenceDelegate(java.lang.Class, java.beans.PersistenceDelegate)
     */
    @Override
    public void unregisterPersistenceDelegate(
        final Class<?> type,
        final PersistenceDelegate persistenceDelegate )
    {
        assertArgumentNotNull( type, "type" ); //$NON-NLS-1$
        assertArgumentNotNull( persistenceDelegate, "persistenceDelegate" ); //$NON-NLS-1$

        persistenceDelegates_.remove( type.getName(), persistenceDelegate );
    }
}

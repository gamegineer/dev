/*
 * FakePersistenceDelegateRegistry.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Jul 2, 2010 at 9:27:30 PM.
 */

package org.gamegineer.common.persistence.serializable;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.jcip.annotations.ThreadSafe;

/**
 * Fake implementation of {@link IPersistenceDelegateRegistry}.
 */
@ThreadSafe
public final class FakePersistenceDelegateRegistry
    implements IPersistenceDelegateRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of persistence delegates managed by this object. */
    private final ConcurrentMap<String, IPersistenceDelegate> persistenceDelegates_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakePersistenceDelegateRegistry}
     * class.
     */
    public FakePersistenceDelegateRegistry()
    {
        persistenceDelegates_ = new ConcurrentHashMap<>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.persistence.serializable.persistencedelegateregistry.IPersistenceDelegateRegistry#getPersistenceDelegate(java.lang.Class)
     */
    @Override
    public IPersistenceDelegate getPersistenceDelegate(
        final Class<?> type )
    {
        assertArgumentNotNull( type, "type" ); //$NON-NLS-1$

        return getPersistenceDelegate( type.getName() );
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.persistencedelegateregistry.IPersistenceDelegateRegistry#getPersistenceDelegate(java.lang.String)
     */
    @Override
    public IPersistenceDelegate getPersistenceDelegate(
        final String typeName )
    {
        assertArgumentNotNull( typeName, "typeName" ); //$NON-NLS-1$

        return persistenceDelegates_.get( typeName );
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.persistencedelegateregistry.IPersistenceDelegateRegistry#getTypeNames()
     */
    @Override
    public Set<String> getTypeNames()
    {
        return new HashSet<>( persistenceDelegates_.keySet() );
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.persistencedelegateregistry.IPersistenceDelegateRegistry#registerPersistenceDelegate(java.lang.Class, org.gamegineer.common.persistence.serializable.IPersistenceDelegate)
     */
    @Override
    public void registerPersistenceDelegate(
        final Class<?> type,
        final IPersistenceDelegate persistenceDelegate )
    {
        assertArgumentNotNull( type, "type" ); //$NON-NLS-1$
        assertArgumentNotNull( persistenceDelegate, "persistenceDelegate" ); //$NON-NLS-1$
        assertArgumentLegal( persistenceDelegates_.putIfAbsent( type.getName(), persistenceDelegate ) == null, "type" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.persistencedelegateregistry.IPersistenceDelegateRegistry#unregisterPersistenceDelegate(java.lang.Class, org.gamegineer.common.persistence.serializable.IPersistenceDelegate)
     */
    @Override
    public void unregisterPersistenceDelegate(
        final Class<?> type,
        final IPersistenceDelegate persistenceDelegate )
    {
        assertArgumentNotNull( type, "type" ); //$NON-NLS-1$
        assertArgumentNotNull( persistenceDelegate, "persistenceDelegate" ); //$NON-NLS-1$
        assertArgumentLegal( persistenceDelegates_.remove( type.getName(), persistenceDelegate ), "type" ); //$NON-NLS-1$
    }
}

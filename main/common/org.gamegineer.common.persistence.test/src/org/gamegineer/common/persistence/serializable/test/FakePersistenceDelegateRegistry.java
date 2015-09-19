/*
 * FakePersistenceDelegateRegistry.java
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
 * Created on Jul 2, 2010 at 9:27:30 PM.
 */

package org.gamegineer.common.persistence.serializable.test;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegate;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegateRegistry;

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
     * @see org.gamegineer.common.persistence.serializable.IPersistenceDelegateRegistry#getPersistenceDelegate(java.lang.Class)
     */
    @Override
    public @Nullable IPersistenceDelegate getPersistenceDelegate(
        final Class<?> type )
    {
        return getPersistenceDelegate( type.getName() );
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.IPersistenceDelegateRegistry#getPersistenceDelegate(java.lang.String)
     */
    @Override
    public @Nullable IPersistenceDelegate getPersistenceDelegate(
        final String typeName )
    {
        return persistenceDelegates_.get( typeName );
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.IPersistenceDelegateRegistry#getTypeNames()
     */
    @Override
    public Set<String> getTypeNames()
    {
        return new HashSet<>( persistenceDelegates_.keySet() );
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.IPersistenceDelegateRegistry#registerPersistenceDelegate(java.lang.Class, org.gamegineer.common.persistence.serializable.IPersistenceDelegate)
     */
    @Override
    public void registerPersistenceDelegate(
        final Class<?> type,
        final IPersistenceDelegate persistenceDelegate )
    {
        assertArgumentLegal( persistenceDelegates_.putIfAbsent( type.getName(), persistenceDelegate ) == null, "type" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.IPersistenceDelegateRegistry#unregisterPersistenceDelegate(java.lang.Class, org.gamegineer.common.persistence.serializable.IPersistenceDelegate)
     */
    @Override
    public void unregisterPersistenceDelegate(
        final Class<?> type,
        final IPersistenceDelegate persistenceDelegate )
    {
        assertArgumentLegal( persistenceDelegates_.remove( type.getName(), persistenceDelegate ), "type" ); //$NON-NLS-1$
    }
}

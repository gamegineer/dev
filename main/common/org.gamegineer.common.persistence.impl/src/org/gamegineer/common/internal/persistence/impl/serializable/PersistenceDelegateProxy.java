/*
 * PersistenceDelegateProxy.java
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
 * Created on Jul 5, 2010 at 8:14:27 PM.
 */

package org.gamegineer.common.internal.persistence.impl.serializable;

import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.io.IOException;
import java.io.ObjectStreamClass;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.internal.persistence.impl.Activator;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegate;
import org.gamegineer.common.persistence.serializable.ObjectInputStream;
import org.gamegineer.common.persistence.serializable.ObjectOutputStream;
import org.osgi.framework.ServiceReference;

/**
 * A proxy for lazily loading persistence delegates published via the service
 * registry.
 */
@ThreadSafe
final class PersistenceDelegateProxy
    implements IPersistenceDelegate
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Indicates this instance has been disposed. */
    @GuardedBy( "lock_" )
    private boolean isDisposed_;

    /** The instance lock. */
    private final Object lock_;

    /** The actual persistence delegate. */
    @GuardedBy( "lock_" )
    private @Nullable IPersistenceDelegate persistenceDelegate_;

    /** The service registry reference to the persistence delegate. */
    private final ServiceReference<IPersistenceDelegate> persistenceDelegateReference_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PersistenceDelegateProxy} class.
     * 
     * @param persistenceDelegateReference
     *        The service registry reference to the persistence delegate; must
     *        not be {@code null}.
     */
    PersistenceDelegateProxy(
        final ServiceReference<IPersistenceDelegate> persistenceDelegateReference )
    {
        lock_ = new Object();
        persistenceDelegateReference_ = persistenceDelegateReference;
        isDisposed_ = false;
        persistenceDelegate_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.persistence.serializable.IPersistenceDelegate#annotateClass(org.gamegineer.common.persistence.serializable.ObjectOutputStream, java.lang.Class)
     */
    @Override
    public void annotateClass(
        final ObjectOutputStream stream,
        final Class<?> cl )
        throws IOException
    {
        getActualPersistenceDelegate().annotateClass( stream, cl );
    }

    /**
     * Disposes of the resources used by this instance.
     */
    void dispose()
    {
        synchronized( lock_ )
        {
            assert !isDisposed_;

            if( persistenceDelegate_ != null )
            {
                isDisposed_ = true;
                persistenceDelegate_ = null;
                Activator.getDefault().getBundleContext().ungetService( persistenceDelegateReference_ );
            }
        }
    }

    /**
     * Gets the actual persistence delegate associated with this proxy.
     * 
     * @return The actual persistence delegate associated with this proxy; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the actual persistence delegate cannot be obtained.
     */
    private IPersistenceDelegate getActualPersistenceDelegate()
    {
        synchronized( lock_ )
        {
            assertStateLegal( !isDisposed_, NonNlsMessages.PersistenceDelegateProxy_getActualPersistenceDelegate_proxyDisposed );

            if( persistenceDelegate_ == null )
            {
                persistenceDelegate_ = Activator.getDefault().getBundleContext().getService( persistenceDelegateReference_ );
            }

            assertStateLegal( persistenceDelegate_ != null, NonNlsMessages.PersistenceDelegateProxy_getActualPersistenceDelegate_actualObjectNotAvailable );
            assert persistenceDelegate_ != null;
            return persistenceDelegate_;
        }
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.IPersistenceDelegate#replaceObject(java.lang.Object)
     */
    @Override
    public @Nullable Object replaceObject(
        final @Nullable Object obj )
        throws IOException
    {
        return getActualPersistenceDelegate().replaceObject( obj );
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.IPersistenceDelegate#resolveClass(org.gamegineer.common.persistence.serializable.ObjectInputStream, java.io.ObjectStreamClass)
     */
    @Override
    public Class<?> resolveClass(
        final ObjectInputStream stream,
        final ObjectStreamClass desc )
        throws IOException, ClassNotFoundException
    {
        return getActualPersistenceDelegate().resolveClass( stream, desc );
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.IPersistenceDelegate#resolveObject(java.lang.Object)
     */
    @Override
    public @Nullable Object resolveObject(
        final @Nullable Object obj )
        throws IOException
    {
        return getActualPersistenceDelegate().resolveObject( obj );
    }
}

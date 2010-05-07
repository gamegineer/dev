/*
 * XMLEncoder.java
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
 * Created on Jun 21, 2008 at 11:07:32 PM.
 */

package org.gamegineer.common.persistence.schemes.beans;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.beans.PersistenceDelegate;
import java.io.OutputStream;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.internal.persistence.Services;
import org.gamegineer.common.persistence.schemes.beans.services.persistencedelegateregistry.IPersistenceDelegateRegistry;

/**
 * An encoder used for persisting objects using an XML representation.
 * 
 * <p>
 * Before encoding an object, the encoder will query the platform for an
 * associated persistence delegate that will assume the responsibility for
 * encoding the object state. The persistence delegate registered with the
 * platform will take precedence over any other persistence delegate registered
 * as outlined in {@link java.beans.Encoder#getPersistenceDelegate(Class)}.
 * </p>
 * 
 * <p>
 * To contribute a persistence delegate for a specific class, register it with
 * the platform's {@code IPersistenceDelegateRegistry}.
 * </p>
 */
@NotThreadSafe
public final class XMLEncoder
    extends java.beans.XMLEncoder
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The persistence delegate registry. */
    private final IPersistenceDelegateRegistry persistenceDelegateRegistry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code XMLEncoder} class.
     * 
     * @param out
     *        The output stream on which to write; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code out} is {@code null}.
     */
    public XMLEncoder(
        /* @NonNull */
        final OutputStream out )
    {
        super( out );

        assertArgumentNotNull( out, "out" ); //$NON-NLS-1$

        persistenceDelegateRegistry_ = Services.getDefault().getBeansPersistenceDelegateRegistry();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.beans.Encoder#getPersistenceDelegate(java.lang.Class)
     */
    @Override
    public PersistenceDelegate getPersistenceDelegate(
        final Class<?> type )
    {
        if( type != null )
        {
            final PersistenceDelegate persistenceDelegate = persistenceDelegateRegistry_.getPersistenceDelegate( type.getName() );
            if( persistenceDelegate != null )
            {
                return persistenceDelegate;
            }
        }

        return super.getPersistenceDelegate( type );
    }

    /**
     * Sets the context class loader to use the class loader for the specified
     * object if it has a registered framework persistence delegate.
     * 
     * @param o
     *        An object; may be {@code null}.
     * 
     * @return The previous context class loader or {@code null} if the context
     *         class loader was not changed. The caller is expected to restore
     *         this context class loader when the relevant operation is
     *         complete.
     */
    /* @Nullable */
    private ClassLoader setContextClassLoader(
        /* @Nullable */
        final Object o )
    {
        if( o == null )
        {
            return null;
        }

        final PersistenceDelegate persistenceDelegate = persistenceDelegateRegistry_.getPersistenceDelegate( o.getClass().getName() );
        if( persistenceDelegate == null )
        {
            return null;
        }

        final Thread thread = Thread.currentThread();
        final ClassLoader oldContextClassLoader = thread.getContextClassLoader();
        thread.setContextClassLoader( persistenceDelegate.getClass().getClassLoader() );
        return oldContextClassLoader;
    }

    /*
     * @see java.beans.XMLEncoder#writeObject(java.lang.Object)
     */
    @Override
    public void writeObject(
        final Object o )
    {
        final ClassLoader oldContextClassLoader = setContextClassLoader( o );
        try
        {
            super.writeObject( o );
        }
        finally
        {
            if( oldContextClassLoader != null )
            {
                Thread.currentThread().setContextClassLoader( oldContextClassLoader );
            }
        }
    }
}

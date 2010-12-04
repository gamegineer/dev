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

package org.gamegineer.common.persistence.beans;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.beans.PersistenceDelegate;
import java.io.OutputStream;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.persistence.beans.services.persistencedelegateregistry.IPersistenceDelegateRegistry;

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
 * the {@code IPersistenceDelegateRegistry} passed to the encoder.
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
     * @param persistenceDelegateRegistry
     *        The persistence delegate registry; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code out} or {@code persistenceDelegateRegistry} is {@code
     *         null}.
     */
    public XMLEncoder(
        /* @NonNull */
        final OutputStream out,
        /* @NonNull */
        final IPersistenceDelegateRegistry persistenceDelegateRegistry )
    {
        super( out );

        assertArgumentNotNull( out, "out" ); //$NON-NLS-1$
        assertArgumentNotNull( persistenceDelegateRegistry, "persistenceDelegateRegistry" ); //$NON-NLS-1$

        persistenceDelegateRegistry_ = persistenceDelegateRegistry;

        setPersistenceDelegate( ClassLoaderContext.class, new ClassLoaderContextPersistenceDelegate() );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the class loader context for the specified object.
     * 
     * @param o
     *        An object; may be {@code null}.
     * 
     * @return The class loader context for the specified object or {@code null}
     *         if no class loader context is available.
     */
    /* @Nullable */
    private ClassLoaderContext getClassLoaderContext(
        /* @Nullable */
        final Object o )
    {
        final PersistenceDelegate servicePersistenceDelegate = getServicePersistenceDelegate( o );
        if( servicePersistenceDelegate == null )
        {
            return null;
        }

        return new ClassLoaderContext( o.getClass().getName() );
    }

    /*
     * @see java.beans.Encoder#getPersistenceDelegate(java.lang.Class)
     */
    @Override
    public PersistenceDelegate getPersistenceDelegate(
        final Class<?> type )
    {
        final PersistenceDelegate servicePersistenceDelegate = getServicePersistenceDelegate( type );
        if( servicePersistenceDelegate != null )
        {
            return servicePersistenceDelegate;
        }

        return super.getPersistenceDelegate( type );
    }

    /**
     * Gets the persistence delegate for the specified class that is registered
     * with the persistence delegate registry service.
     * 
     * @param type
     *        A class; may be {@code null}.
     * 
     * @return The persistence delegate for the specified class that is
     *         registered with the persistence delegate registry service or
     *         {@code null} if no persistence delegate has been registered.
     */
    /* @Nullable */
    private PersistenceDelegate getServicePersistenceDelegate(
        /* @Nullable */
        final Class<?> type )
    {
        if( type == null )
        {
            return null;
        }

        return persistenceDelegateRegistry_.getPersistenceDelegate( type.getName() );
    }

    /**
     * Gets the persistence delegate for the specified object that is registered
     * with the persistence delegate registry service.
     * 
     * @param o
     *        An object; may be {@code null}.
     * 
     * @return The persistence delegate for the specified object that is
     *         registered with the persistence delegate registry service or
     *         {@code null} if no persistence delegate has been registered.
     */
    /* @Nullable */
    private PersistenceDelegate getServicePersistenceDelegate(
        /* @Nullable */
        final Object o )
    {
        if( o == null )
        {
            return null;
        }

        return getServicePersistenceDelegate( o.getClass() );
    }

    /**
     * Allows the persistence delegate registered for the specified object to
     * replace it with another object before it is written by
     * {@link #writeObject(Object)}.
     * 
     * @param o
     *        The object to replace; may be {@code null}.
     * 
     * @return The replaced object; may be {@code null}.
     */
    /* @Nullable */
    private Object replaceObject(
        /* @Nullable */
        final Object o )
    {
        Object object = o;
        while( true )
        {
            final PersistenceDelegate persistenceDelegate = getServicePersistenceDelegate( object );
            final Object replacedObject;
            if( persistenceDelegate instanceof IAdvancedPersistenceDelegate )
            {
                replacedObject = ((IAdvancedPersistenceDelegate)persistenceDelegate).replaceObject( object );
            }
            else
            {
                replacedObject = object;
            }

            if( object != replacedObject )
            {
                object = replacedObject;
            }
            else
            {
                break;
            }
        }

        return object;
    }

    /*
     * @see java.beans.XMLEncoder#writeObject(java.lang.Object)
     */
    @Override
    public void writeObject(
        final Object o )
    {
        final Object replacedObject = replaceObject( o );

        final ClassLoaderContext classLoaderContext = getClassLoaderContext( replacedObject );
        try
        {
            if( classLoaderContext != null )
            {
                classLoaderContext.open( persistenceDelegateRegistry_ );
                super.writeObject( classLoaderContext );
            }

            super.writeObject( replacedObject );
        }
        finally
        {
            if( classLoaderContext != null )
            {
                classLoaderContext.close();
            }
        }
    }
}

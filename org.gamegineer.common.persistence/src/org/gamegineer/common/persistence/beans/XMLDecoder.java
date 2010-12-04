/*
 * XMLDecoder.java
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
 * Created on May 7, 2010 at 1:00:00 AM.
 */

package org.gamegineer.common.persistence.beans;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.beans.ExceptionListener;
import java.beans.PersistenceDelegate;
import java.io.InputStream;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.persistence.beans.services.persistencedelegateregistry.IPersistenceDelegateRegistry;

/**
 * A decoder used for persisting objects using an XML representation.
 */
@NotThreadSafe
public final class XMLDecoder
    extends java.beans.XMLDecoder
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
     * Initializes a new instance of the {@code XMLDecoder} class using the
     * specified input stream.
     * 
     * @param in
     *        The input stream from which to read; may be {@code null} but the
     *        resulting decoder will be useless.
     * @param persistenceDelegateRegistry
     *        The persistence delegate registry; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code persistenceDelegateRegistry} is {@code null}.
     */
    public XMLDecoder(
        /* @Nullable */
        final InputStream in,
        /* @NonNull */
        final IPersistenceDelegateRegistry persistenceDelegateRegistry )
    {
        this( in, null, persistenceDelegateRegistry );
    }

    /**
     * Initializes a new instance of the {@code XMLDecoder} class using the
     * specified input stream and owner.
     * 
     * @param in
     *        The input stream from which to read; may be {@code null} but the
     *        resulting decoder will be useless.
     * @param owner
     *        The owner of this decoder; may be {@code null}.
     * @param persistenceDelegateRegistry
     *        The persistence delegate registry; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code persistenceDelegateRegistry} is {@code null}.
     */
    public XMLDecoder(
        /* @Nullable */
        final InputStream in,
        /* @Nullable */
        final Object owner,
        /* @NonNull */
        final IPersistenceDelegateRegistry persistenceDelegateRegistry )
    {
        this( in, owner, null, persistenceDelegateRegistry );
    }

    /**
     * Initializes a new instance of the {@code XMLDecoder} class using the
     * specified input stream, owner, and exception listener.
     * 
     * @param in
     *        The input stream from which to read; may be {@code null} but the
     *        resulting decoder will be useless.
     * @param owner
     *        The owner of this decoder; may be {@code null}.
     * @param exceptionListener
     *        The exception listener for this decoder; may be {@code null} to
     *        use the default exception listener.
     * @param persistenceDelegateRegistry
     *        The persistence delegate registry; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code persistenceDelegateRegistry} is {@code null}.
     */
    public XMLDecoder(
        /* @Nullable */
        final InputStream in,
        /* @Nullable */
        final Object owner,
        /* @Nullable */
        final ExceptionListener exceptionListener,
        /* @NonNull */
        final IPersistenceDelegateRegistry persistenceDelegateRegistry )
    {
        this( in, owner, exceptionListener, null, persistenceDelegateRegistry );
    }

    /**
     * Initializes a new instance of the {@code XMLDecoder} class using the
     * specified input stream, owner, exception listener, and class loader.
     * 
     * @param in
     *        The input stream from which to read; may be {@code null} but the
     *        resulting decoder will be useless.
     * @param owner
     *        The owner of this decoder; may be {@code null}.
     * @param exceptionListener
     *        The exception listener for this decoder; may be {@code null} to
     *        use the default exception listener.
     * @param cl
     *        The class loader to use for instantiating objects; may be {@code
     *        null} to use the default class loader.
     * @param persistenceDelegateRegistry
     *        The persistence delegate registry; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code persistenceDelegateRegistry} is {@code null}.
     */
    public XMLDecoder(
        /* @Nullable */
        final InputStream in,
        /* @Nullable */
        final Object owner,
        /* @Nullable */
        final ExceptionListener exceptionListener,
        /* @Nullable */
        final ClassLoader cl,
        /* @NonNull */
        final IPersistenceDelegateRegistry persistenceDelegateRegistry )
    {
        super( in, owner, exceptionListener, cl );

        assertArgumentNotNull( persistenceDelegateRegistry, "persistenceDelegateRegistry" ); //$NON-NLS-1$

        persistenceDelegateRegistry_ = persistenceDelegateRegistry;
    }


    // ======================================================================
    // Methods
    // ======================================================================

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

    /*
     * @see java.beans.XMLDecoder#readObject()
     */
    @Override
    public Object readObject()
    {
        final Object o = super.readObject();
        if( !(o instanceof ClassLoaderContext) )
        {
            return o;
        }

        final ClassLoaderContext classLoaderContext = (ClassLoaderContext)o;
        classLoaderContext.open( persistenceDelegateRegistry_ );
        try
        {
            return resolveObject( super.readObject() );
        }
        finally
        {
            classLoaderContext.close();
        }
    }

    /**
     * Allows the persistence delegate registered for the specified object to
     * resolve it with another object before it is returned from
     * {@link #readObject()}.
     * 
     * @param o
     *        The object to resolve; may be {@code null}.
     * 
     * @return The resolved object; may be {@code null}.
     */
    /* @Nullable */
    private Object resolveObject(
        /* @Nullable */
        final Object o )
    {
        Object object = o;
        while( true )
        {
            final PersistenceDelegate persistenceDelegate = getServicePersistenceDelegate( object );
            final Object resolvedObject;
            if( persistenceDelegate instanceof IAdvancedPersistenceDelegate )
            {
                resolvedObject = ((IAdvancedPersistenceDelegate)persistenceDelegate).resolveObject( object );
            }
            else
            {
                resolvedObject = object;
            }

            if( object != resolvedObject )
            {
                object = resolvedObject;
            }
            else
            {
                break;
            }
        }

        return object;
    }
}

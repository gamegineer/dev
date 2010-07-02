/*
 * Coders.java
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
 * Created on Jun 29, 2010 at 10:41:43 PM.
 */

package org.gamegineer.common.persistence.schemes.beans;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.beans.ExceptionListener;
import java.beans.PersistenceDelegate;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Set;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.internal.persistence.Activator;
import org.gamegineer.common.internal.persistence.Loggers;
import org.gamegineer.common.persistence.schemes.beans.services.persistencedelegateregistry.IPersistenceDelegateRegistry;

/**
 * A factory for creating various types of encoders and decoders for the
 * Gamegineer extensions to the JavaBeans persistence framework.
 */
@ThreadSafe
public final class Coders
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The null persistence delegate registry. */
    private static final IPersistenceDelegateRegistry NULL_PERSISTENCE_DELEGATE_REGISTRY = new NullPersistenceDelegateRegistry();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Coders} class.
     */
    private Coders()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code XMLDecoder} class using the
     * specified input stream, and the platform's persistence delegate registry.
     * 
     * @param in
     *        The input stream from which to read; may be {@code null} but the
     *        resulting decoder will be useless.
     * 
     * @return A new instance of the {@code XMLDecoder} class; never {@code
     *         null}.
     */
    /* @NonNull */
    public static XMLDecoder createPlatformXMLDecoder(
        /* @Nullable */
        final InputStream in )
    {
        return createPlatformXMLDecoder( in, null );
    }

    /**
     * Creates a new instance of the {@code XMLDecoder} class using the
     * specified input stream, owner, and the platform's persistence delegate
     * registry.
     * 
     * @param in
     *        The input stream from which to read; may be {@code null} but the
     *        resulting decoder will be useless.
     * @param owner
     *        The owner of the decoder; may be {@code null}.
     * 
     * @return A new instance of the {@code XMLDecoder} class; never {@code
     *         null}.
     */
    /* @NonNull */
    public static XMLDecoder createPlatformXMLDecoder(
        /* @Nullable */
        final InputStream in,
        /* @Nullable */
        final Object owner )
    {
        return createPlatformXMLDecoder( in, owner, null );
    }

    /**
     * Creates a new instance of the {@code XMLDecoder} class using the
     * specified input stream, owner, exception listener, and the platform's
     * persistence delegate registry.
     * 
     * @param in
     *        The input stream from which to read; may be {@code null} but the
     *        resulting decoder will be useless.
     * @param owner
     *        The owner of the decoder; may be {@code null}.
     * @param exceptionListener
     *        The exception listener for the decoder; may be {@code null} to use
     *        the default exception listener.
     * 
     * @return A new instance of the {@code XMLDecoder} class; never {@code
     *         null}.
     */
    /* @NonNull */
    public static XMLDecoder createPlatformXMLDecoder(
        /* @Nullable */
        final InputStream in,
        /* @Nullable */
        final Object owner,
        /* @Nullable */
        final ExceptionListener exceptionListener )
    {
        return createPlatformXMLDecoder( in, owner, exceptionListener, null );
    }

    /**
     * Creates a new instance of the {@code XMLDecoder} class using the
     * specified input stream, owner, exception listener, class loader, and the
     * platform's persistence delegate registry.
     * 
     * @param in
     *        The input stream from which to read; may be {@code null} but the
     *        resulting decoder will be useless.
     * @param owner
     *        The owner of the decoder; may be {@code null}.
     * @param exceptionListener
     *        The exception listener for the decoder; may be {@code null} to use
     *        the default exception listener.
     * @param cl
     *        The class loader to use for instantiating objects; may be {@code
     *        null} to use the default class loader.
     * 
     * @return A new instance of the {@code XMLDecoder} class; never {@code
     *         null}.
     */
    /* @NonNull */
    public static XMLDecoder createPlatformXMLDecoder(
        /* @Nullable */
        final InputStream in,
        /* @Nullable */
        final Object owner,
        /* @Nullable */
        final ExceptionListener exceptionListener,
        /* @Nullable */
        final ClassLoader cl )
    {
        return new XMLDecoder( in, owner, exceptionListener, cl, getPlatformPersistenceDelegateRegistry() );
    }

    /**
     * Creates a new instance of the {@code XMLEncoder} class using the
     * platform's persistence delegate registry.
     * 
     * @param out
     *        The output stream on which to write; must not be {@code null}.
     * 
     * @return A new instance of the {@code XMLEncoder} class; never {@code
     *         null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code out} is {@code null}.
     */
    /* @NonNull */
    public static XMLEncoder createPlatformXMLEncoder(
        /* @NonNull */
        final OutputStream out )
    {
        return new XMLEncoder( out, getPlatformPersistenceDelegateRegistry() );
    }

    /**
     * Gets the platform JavaBeans persistence delegate registry.
     * 
     * @return The platform JavaBeans persistence delegate registry or a null
     *         implementation if the platform JavaBeans persistence delegate
     *         registry is not available.
     */
    /* @NonNull */
    private static IPersistenceDelegateRegistry getPlatformPersistenceDelegateRegistry()
    {
        final IPersistenceDelegateRegistry persistenceDelegateRegistry = Activator.getDefault().getBeansPersistenceDelegateRegistry();
        if( persistenceDelegateRegistry != null )
        {
            return persistenceDelegateRegistry;
        }

        Loggers.getDefaultLogger().warning( Messages.Coders_platformPersistenceDelegateRegistry_notAvailable );
        return NULL_PERSISTENCE_DELEGATE_REGISTRY;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Null implementation of
     * {@link org.gamegineer.common.persistence.schemes.beans.services.persistencedelegateregistry.IPersistenceDelegateRegistry}
     * .
     */
    @Immutable
    private static final class NullPersistenceDelegateRegistry
        implements IPersistenceDelegateRegistry
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code
         * NullPersistenceDelegateRegistry} class.
         */
        NullPersistenceDelegateRegistry()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.common.persistence.schemes.beans.services.persistencedelegateregistry.IPersistenceDelegateRegistry#getPersistenceDelegate(java.lang.Class)
         */
        @Override
        public PersistenceDelegate getPersistenceDelegate(
            final Class<?> type )
        {
            assertArgumentNotNull( type, "type" ); //$NON-NLS-1$

            return null;
        }

        /*
         * @see org.gamegineer.common.persistence.schemes.beans.services.persistencedelegateregistry.IPersistenceDelegateRegistry#getPersistenceDelegate(java.lang.String)
         */
        @Override
        public PersistenceDelegate getPersistenceDelegate(
            final String typeName )
        {
            assertArgumentNotNull( typeName, "typeName" ); //$NON-NLS-1$

            return null;
        }

        /*
         * @see org.gamegineer.common.persistence.schemes.beans.services.persistencedelegateregistry.IPersistenceDelegateRegistry#getTypeNames()
         */
        @Override
        public Set<String> getTypeNames()
        {
            return Collections.emptySet();
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

            throw new UnsupportedOperationException();
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

            throw new UnsupportedOperationException();
        }
    }
}

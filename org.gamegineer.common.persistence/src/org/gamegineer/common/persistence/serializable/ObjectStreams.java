/*
 * ObjectStreams.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Jul 2, 2010 at 12:00:29 AM.
 */

package org.gamegineer.common.persistence.serializable;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Set;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.internal.persistence.Activator;
import org.gamegineer.common.internal.persistence.Loggers;

/**
 * A factory for creating various types of input and output object streams for
 * the Gamegineer extensions to the Java object serialization framework.
 */
@ThreadSafe
public final class ObjectStreams
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
     * Initializes a new instance of the {@code ObjectStreams} class.
     */
    private ObjectStreams()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ObjectInputStream} class using
     * the platform's persistence delegate registry.
     * 
     * @param in
     *        The input stream from which to read; must not be {@code null}.
     * 
     * @return A new instance of the {@code ObjectInputStream} class; never
     *         {@code null}.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs while reading the stream header.
     * @throws java.io.StreamCorruptedException
     *         If the stream header is incorrect.
     * @throws java.lang.NullPointerException
     *         If {@code in} is {@code null}.
     */
    /* @NonNull */
    public static ObjectInputStream createPlatformObjectInputStream(
        /* @NonNull */
        final InputStream in )
        throws IOException
    {
        return new ObjectInputStream( in, getPlatformPersistenceDelegateRegistry() );
    }

    /**
     * Initializes a new instance of the {@code ObjectOutputStream} class using
     * the platform's persistence delegate registry.
     * 
     * @param out
     *        The output stream on which to write; must not be {@code null}.
     * 
     * @return A new instance of the {@code ObjectOutputStream} class; never
     *         {@code null}.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs while writing the stream header.
     * @throws java.lang.NullPointerException
     *         If {@code out} is {@code null}.
     */
    /* @NonNull */
    public static ObjectOutputStream createPlatformObjectOutputStream(
        /* @NonNull */
        final OutputStream out )
        throws IOException
    {
        return new ObjectOutputStream( out, getPlatformPersistenceDelegateRegistry() );
    }

    /**
     * Gets the platform Java Serialization Framework persistence delegate
     * registry.
     * 
     * @return The platform Java Serialization Framework persistence delegate
     *         registry or a null implementation if the platform Java
     *         Serialization Framework persistence delegate registry is not
     *         available.
     */
    /* @NonNull */
    private static IPersistenceDelegateRegistry getPlatformPersistenceDelegateRegistry()
    {
        final IPersistenceDelegateRegistry persistenceDelegateRegistry = Activator.getDefault().getSerializablePersistenceDelegateRegistry();
        if( persistenceDelegateRegistry != null )
        {
            return persistenceDelegateRegistry;
        }

        Loggers.getDefaultLogger().warning( NonNlsMessages.ObjectStreams_platformPersistenceDelegateRegistry_notAvailable );
        return NULL_PERSISTENCE_DELEGATE_REGISTRY;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Null implementation of
     * {@link org.gamegineer.common.persistence.serializable.IPersistenceDelegateRegistry}
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
         * Initializes a new instance of the
         * {@code NullPersistenceDelegateRegistry} class.
         */
        NullPersistenceDelegateRegistry()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.common.persistence.serializable.persistencedelegateregistry.IPersistenceDelegateRegistry#getPersistenceDelegate(java.lang.Class)
         */
        @Override
        public IPersistenceDelegate getPersistenceDelegate(
            final Class<?> type )
        {
            assertArgumentNotNull( type, "type" ); //$NON-NLS-1$

            return null;
        }

        /*
         * @see org.gamegineer.common.persistence.serializable.persistencedelegateregistry.IPersistenceDelegateRegistry#getPersistenceDelegate(java.lang.String)
         */
        @Override
        public IPersistenceDelegate getPersistenceDelegate(
            final String typeName )
        {
            assertArgumentNotNull( typeName, "typeName" ); //$NON-NLS-1$

            return null;
        }

        /*
         * @see org.gamegineer.common.persistence.serializable.persistencedelegateregistry.IPersistenceDelegateRegistry#getTypeNames()
         */
        @Override
        public Set<String> getTypeNames()
        {
            return Collections.emptySet();
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

            throw new UnsupportedOperationException();
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

            throw new UnsupportedOperationException();
        }
    }
}

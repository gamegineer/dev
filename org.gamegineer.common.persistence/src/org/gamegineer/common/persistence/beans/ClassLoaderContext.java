/*
 * ClassLoaderContext.java
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
 * Created on May 7, 2010 at 12:58:08 AM.
 */

package org.gamegineer.common.persistence.beans;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.beans.PersistenceDelegate;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.persistence.beans.services.persistencedelegateregistry.IPersistenceDelegateRegistry;

/**
 * Defines a context in which a specific class loader is active during a
 * JavaBeans persistence framework encoding or decoding operation.
 * 
 * <p>
 * An instance of this class must have its {@code open} method called before an
 * object is encoded or decoded in order to activate the class loader associated
 * with the context. The class loader must be deactivated by closing the context
 * via the {@code close} method once the object has been encoded or decoded.
 * </p>
 * 
 * <p>
 * This class is intended to support the JavaBeans persistence framework and is
 * not intended to be used by clients.
 * </p>
 */
@NotThreadSafe
public final class ClassLoaderContext
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The persistence delegate registry to use on the current thread. */
    private static final ThreadLocal<IPersistenceDelegateRegistry> persistenceDelegateRegistry_ = new ThreadLocal<IPersistenceDelegateRegistry>();

    /** The name of the class whose loader is associated with this context. */
    private final String className_;

    /** The thread context class loader that was previously active. */
    private ClassLoader oldContextClassLoader_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ClassLoaderContext} class.
     * 
     * @param className
     *        The name of the class whose loader is associated with this
     *        context; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code className} is {@code null}.
     */
    public ClassLoaderContext(
        /* @NonNull */
        final String className )
    {
        assertArgumentNotNull( className, "className" ); //$NON-NLS-1$

        className_ = className;
        oldContextClassLoader_ = null;

        open( persistenceDelegateRegistry_.get() );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Closes this context and restores the class loader that was active before
     * this context was opened.
     */
    void close()
    {
        if( oldContextClassLoader_ != null )
        {
            Thread.currentThread().setContextClassLoader( oldContextClassLoader_ );
        }
    }

    /**
     * Gets the name of the class whose loader is associated with this context.
     * 
     * @return The name of the class whose loader is associated with this
     *         context; never {@code null}.
     */
    /* @NonNull */
    String getClassName()
    {
        return className_;
    }

    /**
     * Gets the context class loader to use for the specified persistence
     * delegate.
     * 
     * @param persistenceDelegate
     *        The persistence delegate; must not be {@code null}.
     * 
     * @return The context class loader to use for the specified persistence
     *         delegate; never {@code null}.
     */
    /* @NonNull */
    private static ClassLoader getContextClassLoader(
        /* @NonNull */
        final PersistenceDelegate persistenceDelegate )
    {
        assert persistenceDelegate != null;

        if( persistenceDelegate instanceof IAdvancedPersistenceDelegate )
        {
            final ClassLoader contextClassLoader = ((IAdvancedPersistenceDelegate)persistenceDelegate).getContextClassLoader();
            if( contextClassLoader != null )
            {
                return contextClassLoader;
            }
        }

        return persistenceDelegate.getClass().getClassLoader();
    }

    /**
     * Opens this context and activates the class loader associated with the
     * context.
     * 
     * @param persistenceDelegateRegistry
     *        The persistence delegate registry; must not be {@code null}.
     */
    private void open(
        /* @NonNull */
        final IPersistenceDelegateRegistry persistenceDelegateRegistry )
    {
        assert persistenceDelegateRegistry != null;
        assert oldContextClassLoader_ == null;

        final PersistenceDelegate persistenceDelegate = persistenceDelegateRegistry.getPersistenceDelegate( className_ );
        if( persistenceDelegate != null )
        {
            final Thread thread = Thread.currentThread();
            oldContextClassLoader_ = thread.getContextClassLoader();
            thread.setContextClassLoader( getContextClassLoader( persistenceDelegate ) );
        }
    }

    /**
     * Sets the persistence delegate registry to use on the current thread.
     * 
     * <p>
     * This method is intended to be invoked by instances of {@link XMLEncoder}
     * and {@link XMLDecoder} immediately before an instance of {@code
     * ClassLoaderContext} is created and immediately after invoking the
     * {@link #close()} method. Before creating a class loader context, the
     * coder should call this method passing its persistence delegate registry.
     * After closing a class loader context, the coder should call this method
     * passing the previously active persistence delegate registry that was
     * returned from the first invocation of this method.
     * </p>
     * 
     * @param persistenceDelegateRegistry
     *        The persistence delegate registry to use on the current thread or
     *        {@code null} to clear the persistence delegate registry.
     * 
     * @return The persistence delegate registry to use on the current thread or
     *         {@code null} if no persistence delegate registry is available.
     */
    /* @Nullable */
    static IPersistenceDelegateRegistry setPersistenceDelegateRegistry(
        /* @Nullable */
        final IPersistenceDelegateRegistry persistenceDelegateRegistry )
    {
        final IPersistenceDelegateRegistry oldPersistenceDelegateRegistry = persistenceDelegateRegistry_.get();
        persistenceDelegateRegistry_.set( persistenceDelegateRegistry );
        return oldPersistenceDelegateRegistry;
    }
}

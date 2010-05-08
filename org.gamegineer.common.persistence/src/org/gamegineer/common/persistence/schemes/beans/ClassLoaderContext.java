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

package org.gamegineer.common.persistence.schemes.beans;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.beans.PersistenceDelegate;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.internal.persistence.Services;
import org.gamegineer.common.persistence.schemes.beans.services.persistencedelegateregistry.IPersistenceDelegateRegistry;

/**
 * Defines a context in which a specific class loader is active during a
 * JavaBeans persistence framework encoding or decoding operation.
 * 
 * <p>
 * This class is intended to support the JavaBeans persistence framework and is
 * not intended to be used by clients.
 * </p>
 */
@Immutable
public final class ClassLoaderContext
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the class whose loader is active within this context. */
    private final String className_;

    /** The thread context class loader that was previously active. */
    private final ClassLoader oldContextClassLoader_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ClassLoaderContext} class.
     * 
     * <p>
     * If a persistence delegate for the specified class name is registered with
     * the {@code IPersistenceDelegateRegistry} service, the associated class
     * loader will be activated before this method returns. It must be
     * deactivated by closing this context via the {@code close} method once the
     * object has been encoded or decoded.
     * </p>
     * 
     * @param className
     *        The name of the class whose loader is active within this context;
     *        must not be {@code null}.
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

        final IPersistenceDelegateRegistry persistenceDelegateRegistry = Services.getDefault().getBeansPersistenceDelegateRegistry();
        final PersistenceDelegate persistenceDelegate = persistenceDelegateRegistry.getPersistenceDelegate( className );
        if( persistenceDelegate != null )
        {
            final Thread thread = Thread.currentThread();
            oldContextClassLoader_ = thread.getContextClassLoader();
            thread.setContextClassLoader( persistenceDelegate.getClass().getClassLoader() );
        }
        else
        {
            oldContextClassLoader_ = null;
        }
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Closes this context and restores the class loader that was active before
     * this context was created.
     */
    void close()
    {
        if( oldContextClassLoader_ != null )
        {
            Thread.currentThread().setContextClassLoader( oldContextClassLoader_ );
        }
    }

    /**
     * Gets the name of the class whose loader is active within this context.
     * 
     * @return The name of the class whose loader is active within this context;
     *         never {@code null}.
     */
    /* @NonNull */
    String getClassName()
    {
        return className_;
    }
}

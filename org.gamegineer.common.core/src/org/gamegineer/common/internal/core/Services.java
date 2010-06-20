/*
 * Services.java
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
 * Created on Feb 28, 2008 at 11:43:06 PM.
 */

package org.gamegineer.common.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.IExtensionRegistry;

/**
 * Manages the OSGi services used by the bundle.
 */
@ThreadSafe
public final class Services
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The extension registry service. */
    @GuardedBy( "lock_" )
    private static IExtensionRegistry extensionRegistry_ = null;

    /** The class lock. */
    private static Object lock_ = new Object();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Services} class.
     */
    public Services()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Binds the specified extension registry service.
     * 
     * <p>
     * This method will unbind the currently bound extension registry service
     * before binding the new extension registry service.
     * </p>
     * 
     * @param extensionRegistry
     *        The extension registry service to bind; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code extensionRegistry} is {@code null}.
     */
    public void bindExtensionRegistry(
        /* @NonNull */
        final IExtensionRegistry extensionRegistry )
    {
        assertArgumentNotNull( extensionRegistry, "extensionRegistry" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            if( extensionRegistry_ != null )
            {
                unbindExtensionRegistry( extensionRegistry_ );
            }
            extensionRegistry_ = extensionRegistry;
        }
    }

    /**
     * Gets the extension registry service.
     * 
     * @return The extension registry service or {@code null} if no extension
     *         registry service is available.
     */
    /* @Nullable */
    public static IExtensionRegistry getExtensionRegistry()
    {
        synchronized( lock_ )
        {
            return extensionRegistry_;
        }
    }

    /**
     * Unbinds the specified extension registry service.
     * 
     * <p>
     * This method does nothing if the specified extension registry service is
     * not bound.
     * </p>
     * 
     * @param extensionRegistry
     *        The extension registry service to unbind; must not be {@code null}
     *        .
     * 
     * @throws java.lang.NullPointerException
     *         If {@code extensionRegistry} is {@code null}.
     */
    public void unbindExtensionRegistry(
        /* @NonNull */
        final IExtensionRegistry extensionRegistry )
    {
        assertArgumentNotNull( extensionRegistry, "extensionRegistry" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            if( extensionRegistry_ == extensionRegistry )
            {
                extensionRegistry_ = null;
            }
        }
    }
}

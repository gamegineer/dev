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
import org.eclipse.osgi.service.debug.DebugOptions;
import org.gamegineer.common.core.services.logging.ILoggingService;

/**
 * Manages the OSGi services used by the bundle.
 */
@ThreadSafe
public final class Services
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The debug options service. */
    @GuardedBy( "lock_" )
    private static DebugOptions debugOptions_ = null;

    /** The extension registry service. */
    @GuardedBy( "lock_" )
    private static IExtensionRegistry extensionRegistry_ = null;

    /** The logging service. */
    @GuardedBy( "lock_" )
    private static ILoggingService loggingService_ = null;

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
     * Binds the specified debug options service.
     * 
     * <p>
     * This method will unbind the currently bound debug options service before
     * binding the new debug options service.
     * </p>
     * 
     * @param debugOptions
     *        The debug options service to bind; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code debugOptions} is {@code null}.
     */
    public void bindDebugOptions(
        /* @NonNull */
        final DebugOptions debugOptions )
    {
        assertArgumentNotNull( debugOptions, "debugOptions" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            if( debugOptions_ != null )
            {
                unbindDebugOptions( debugOptions_ );
            }
            debugOptions_ = debugOptions;
        }
    }

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
     * Binds the specified logging service.
     * 
     * <p>
     * This method will unbind the currently bound logging service before
     * binding the new logging service.
     * </p>
     * 
     * @param loggingService
     *        The logging service to bind; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code loggingService} is {@code null}.
     */
    public void bindLoggingService(
        /* @NonNull */
        final ILoggingService loggingService )
    {
        assertArgumentNotNull( loggingService, "loggingService" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            if( loggingService_ != null )
            {
                unbindLoggingService( loggingService_ );
            }
            loggingService_ = loggingService;
        }
    }

    /**
     * Gets the debug options service.
     * 
     * @return The debug options service or {@code null} if no debug options
     *         service is available.
     */
    /* @Nullable */
    public static DebugOptions getDebugOptions()
    {
        synchronized( lock_ )
        {
            return debugOptions_;
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
     * Gets the logging service.
     * 
     * @return The logging service or {@code null} if no logging service is
     *         available.
     */
    /* @Nullable */
    public static ILoggingService getLoggingService()
    {
        synchronized( lock_ )
        {
            return loggingService_;
        }
    }

    /**
     * Unbinds the specified debug options service.
     * 
     * <p>
     * This method does nothing if the specified debug options service is not
     * bound.
     * </p>
     * 
     * @param debugOptions
     *        The debug options service to unbind; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code debugOptions} is {@code null}.
     */
    public void unbindDebugOptions(
        /* @NonNull */
        final DebugOptions debugOptions )
    {
        assertArgumentNotNull( debugOptions, "debugOptions" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            if( debugOptions_ == debugOptions )
            {
                debugOptions_ = null;
            }
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

    /**
     * Unbinds the specified logging service.
     * 
     * <p>
     * This method does nothing if the specified logging service is not bound.
     * </p>
     * 
     * @param loggingService
     *        The logging service to unbind; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code loggingService} is {@code null}.
     */
    public void unbindLoggingService(
        /* @NonNull */
        final ILoggingService loggingService )
    {
        assertArgumentNotNull( loggingService, "loggingService" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            if( loggingService_ == loggingService )
            {
                loggingService_ = null;
            }
        }
    }
}

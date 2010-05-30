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
 * Created on Aug 31, 2009 at 11:46:37 PM.
 */

package org.gamegineer.test.internal.core;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.osgi.service.packageadmin.PackageAdmin;

/**
 * Manages the OSGi services used by the bundle.
 */
@ThreadSafe
public final class Services
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The class lock. */
    private static final Object lock_ = new Object();

    /** The package administration service. */
    @GuardedBy( "lock_" )
    private static PackageAdmin packageAdministrationService_ = null;


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
     * Binds the specified package administration service.
     * 
     * <p>
     * This method will unbind the currently bound package administration
     * service before binding the new package administration service.
     * </p>
     * 
     * @param packageAdministrationService
     *        The package administration service to bind; must not be {@code
     *        null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code packageAdministrationService} is {@code null}.
     */
    public static void bindPackageAdministrationService(
        /* @NonNull */
        final PackageAdmin packageAdministrationService )
    {
        if( packageAdministrationService == null )
        {
            throw new NullPointerException( "packageAdministrationService" ); //$NON-NLS-1$
        }

        synchronized( lock_ )
        {
            if( packageAdministrationService_ != null )
            {
                unbindPackageAdministrationService( packageAdministrationService_ );
            }
            packageAdministrationService_ = packageAdministrationService;
        }
    }

    /**
     * Gets the package administration service.
     * 
     * @return The package administration service or {@code null} if the package
     *         administration service is not available.
     */
    /* @Nullable */
    public static PackageAdmin getPackageAdministrationService()
    {
        synchronized( lock_ )
        {
            return packageAdministrationService_;
        }
    }

    /**
     * Unbinds the specified package administration service.
     * 
     * <p>
     * This method does nothing if the specified package administration service
     * is not bound.
     * </p>
     * 
     * @param packageAdministrationService
     *        The package administration service to unbind; must not be {@code
     *        null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code packageAdministrationService} is {@code null}.
     */
    public static void unbindPackageAdministrationService(
        /* @NonNull */
        final PackageAdmin packageAdministrationService )
    {
        if( packageAdministrationService == null )
        {
            throw new NullPointerException( "packageAdministrationService" ); //$NON-NLS-1$
        }

        synchronized( lock_ )
        {
            if( packageAdministrationService_ == packageAdministrationService )
            {
                packageAdministrationService_ = null;
            }
        }
    }
}

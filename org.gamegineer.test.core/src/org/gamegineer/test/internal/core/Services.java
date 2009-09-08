/*
 * Services.java
 * Copyright 2008-2009 Gamegineer.org
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

import org.osgi.framework.BundleContext;
import org.osgi.service.packageadmin.PackageAdmin;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Manages the OSGi services used by the bundle.
 * 
 * <p>
 * The {@code close} method should be called before the bundle is stopped.
 * </p>
 */
public final class Services
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance. */
    private static final Services instance_ = new Services();

    /** The package administration service tracker. */
    private ServiceTracker packageAdminServiceTracker_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Services} class.
     */
    private Services()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Closes the services managed by this object.
     */
    void close()
    {
        // Close bundle-specific services
        if( packageAdminServiceTracker_ != null )
        {
            packageAdminServiceTracker_.close();
            packageAdminServiceTracker_ = null;
        }

        // Unregister package-specific services

        // Unregister bundle-specific services
    }

    /**
     * Gets the default instance of the {@code Services} class.
     * 
     * @return The default instance of the {@code Services} class; never {@code
     *         null}.
     */
    /* @NonNull */
    public static Services getDefault()
    {
        return instance_;
    }

    /**
     * Gets the package administration service managed by this object.
     * 
     * @return The package administration service managed by this object; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If this object is not open.
     */
    /* @NonNull */
    public PackageAdmin getPackageAdministrationService()
    {
        if( packageAdminServiceTracker_ == null )
        {
            throw new IllegalStateException( "package admin service tracker not set" ); //$NON-NLS-1$
        }

        return (PackageAdmin)packageAdminServiceTracker_.getService();
    }

    /**
     * Opens the services managed by this object.
     * 
     * @param context
     *        The execution context of the bundle; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    void open(
        /* @NonNull */
        final BundleContext context )
    {
        if( context == null )
        {
            throw new NullPointerException( "context" ); //$NON-NLS-1$
        }

        // Register bundle-specific services

        // Register package-specific services

        // Open bundle-specific services
        packageAdminServiceTracker_ = new ServiceTracker( context, PackageAdmin.class.getName(), null );
        packageAdminServiceTracker_.open();
    }
}

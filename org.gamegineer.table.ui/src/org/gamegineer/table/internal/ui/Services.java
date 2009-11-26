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
 * Created on Sep 16, 2009 at 10:42:24 PM.
 */

package org.gamegineer.table.internal.ui;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.ui.services.carddesignuiregistry.CardDesignUIRegistry;
import org.gamegineer.table.ui.services.carddesignuiregistry.ICardDesignUIRegistry;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.packageadmin.PackageAdmin;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Manages the OSGi services used by the bundle.
 * 
 * <p>
 * The {@code close} method should be called before the bundle is stopped.
 * </p>
 */
@ThreadSafe
public final class Services
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance. */
    private static final Services instance_ = new Services();

    /** The card design user interface registry service registration token. */
    private ServiceRegistration cardDesignUIRegistryServiceRegistration_;

    /** The card design user interface registry service tracker. */
    private ServiceTracker cardDesignUIRegistryServiceTracker_;

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
        // Unregister package-specific adapters

        // Close bundle-specific services
        if( packageAdminServiceTracker_ != null )
        {
            packageAdminServiceTracker_.close();
            packageAdminServiceTracker_ = null;
        }
        if( cardDesignUIRegistryServiceTracker_ != null )
        {
            cardDesignUIRegistryServiceTracker_.close();
            cardDesignUIRegistryServiceTracker_ = null;
        }

        // Unregister package-specific services

        // Unregister bundle-specific services
        if( cardDesignUIRegistryServiceRegistration_ != null )
        {
            cardDesignUIRegistryServiceRegistration_.unregister();
            cardDesignUIRegistryServiceRegistration_ = null;
        }
    }

    /**
     * Gets the card design user interface registry service managed by this
     * object.
     * 
     * @return The card design user interface registry service managed by this
     *         object; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If this object is not open.
     */
    /* @NonNull */
    public ICardDesignUIRegistry getCardDesignUIRegistry()
    {
        assertStateLegal( cardDesignUIRegistryServiceTracker_ != null, Messages.Services_cardDesignUIRegistryServiceTracker_notSet );

        return (ICardDesignUIRegistry)cardDesignUIRegistryServiceTracker_.getService();
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
        assertStateLegal( packageAdminServiceTracker_ != null, Messages.Services_packageAdminServiceTracker_notSet );

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
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        // Register bundle-specific services
        cardDesignUIRegistryServiceRegistration_ = context.registerService( ICardDesignUIRegistry.class.getName(), new CardDesignUIRegistry(), null );

        // Register package-specific services

        // Open bundle-specific services
        cardDesignUIRegistryServiceTracker_ = new ServiceTracker( context, cardDesignUIRegistryServiceRegistration_.getReference(), null );
        cardDesignUIRegistryServiceTracker_.open();
        packageAdminServiceTracker_ = new ServiceTracker( context, PackageAdmin.class.getName(), null );
        packageAdminServiceTracker_.open();

        // Register package-specific adapters
    }
}

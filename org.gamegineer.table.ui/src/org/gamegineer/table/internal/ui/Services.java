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
 * Created on Sep 16, 2009 at 10:42:24 PM.
 */

package org.gamegineer.table.internal.ui;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.services.carddesignregistry.ICardDesignRegistry;
import org.gamegineer.table.internal.ui.services.carddesignuiregistry.CardDesignUIRegistry;
import org.gamegineer.table.internal.ui.services.cardpiledesignuiregistry.CardPileDesignUIRegistry;
import org.gamegineer.table.ui.services.carddesignuiregistry.ICardDesignUIRegistry;
import org.gamegineer.table.ui.services.cardpiledesignuiregistry.ICardPileDesignUIRegistry;
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

    /** The card design registry service tracker. */
    private ServiceTracker cardDesignRegistryServiceTracker_;

    /** The card design user interface registry service registration token. */
    private ServiceRegistration cardDesignUIRegistryServiceRegistration_;

    /** The card design user interface registry service tracker. */
    private ServiceTracker cardDesignUIRegistryServiceTracker_;

    /** The card pile design user interface registry service registration token. */
    private ServiceRegistration cardPileDesignUIRegistryServiceRegistration_;

    /** The card pile design user interface registry service tracker. */
    private ServiceTracker cardPileDesignUIRegistryServiceTracker_;

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
        if( cardPileDesignUIRegistryServiceTracker_ != null )
        {
            cardPileDesignUIRegistryServiceTracker_.close();
            cardPileDesignUIRegistryServiceTracker_ = null;
        }
        if( cardDesignUIRegistryServiceTracker_ != null )
        {
            cardDesignUIRegistryServiceTracker_.close();
            cardDesignUIRegistryServiceTracker_ = null;
        }
        if( cardDesignRegistryServiceTracker_ != null )
        {
            cardDesignRegistryServiceTracker_.close();
            cardDesignRegistryServiceTracker_ = null;
        }

        // Unregister package-specific services

        // Unregister bundle-specific services
        if( cardPileDesignUIRegistryServiceRegistration_ != null )
        {
            cardPileDesignUIRegistryServiceRegistration_.unregister();
            cardPileDesignUIRegistryServiceRegistration_ = null;
        }
        if( cardDesignUIRegistryServiceRegistration_ != null )
        {
            cardDesignUIRegistryServiceRegistration_.unregister();
            cardDesignUIRegistryServiceRegistration_ = null;
        }
    }

    /**
     * Gets the card design registry service managed by this object.
     * 
     * @return The card design registry service managed by this object; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If this object is not open.
     */
    /* @NonNull */
    public ICardDesignRegistry getCardDesignRegistry()
    {
        assertStateLegal( cardDesignRegistryServiceTracker_ != null, Messages.Services_cardDesignRegistryServiceTracker_notSet );

        return (ICardDesignRegistry)cardDesignRegistryServiceTracker_.getService();
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
     * Gets the card pile design user interface registry service managed by this
     * object.
     * 
     * @return The card pile design user interface registry service managed by
     *         this object; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If this object is not open.
     */
    /* @NonNull */
    public ICardPileDesignUIRegistry getCardPileDesignUIRegistry()
    {
        assertStateLegal( cardPileDesignUIRegistryServiceTracker_ != null, Messages.Services_cardPileDesignUIRegistryServiceTracker_notSet );

        return (ICardPileDesignUIRegistry)cardPileDesignUIRegistryServiceTracker_.getService();
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
        cardPileDesignUIRegistryServiceRegistration_ = context.registerService( ICardPileDesignUIRegistry.class.getName(), new CardPileDesignUIRegistry(), null );

        // Register package-specific services

        // Open bundle-specific services
        cardDesignRegistryServiceTracker_ = new ServiceTracker( context, ICardDesignRegistry.class.getName(), null );
        cardDesignRegistryServiceTracker_.open();
        cardDesignUIRegistryServiceTracker_ = new ServiceTracker( context, cardDesignUIRegistryServiceRegistration_.getReference(), null );
        cardDesignUIRegistryServiceTracker_.open();
        cardPileDesignUIRegistryServiceTracker_ = new ServiceTracker( context, cardPileDesignUIRegistryServiceRegistration_.getReference(), null );
        cardPileDesignUIRegistryServiceTracker_.open();
        packageAdminServiceTracker_ = new ServiceTracker( context, PackageAdmin.class.getName(), null );
        packageAdminServiceTracker_.open();

        // Register package-specific adapters
    }
}

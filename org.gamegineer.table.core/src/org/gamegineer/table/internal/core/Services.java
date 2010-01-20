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
 * Created on Sep 15, 2009 at 11:11:42 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.services.carddesignregistry.ICardDesignRegistry;
import org.gamegineer.table.core.services.cardpiledesignregistry.ICardPileDesignRegistry;
import org.gamegineer.table.internal.core.services.carddesignregistry.CardDesignRegistry;
import org.gamegineer.table.internal.core.services.cardpiledesignregistry.CardPileDesignRegistry;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
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

    /** The card design registry service registration token. */
    private ServiceRegistration cardDesignRegistryServiceRegistration_;

    /** The card design registry service tracker. */
    private ServiceTracker cardDesignRegistryServiceTracker_;

    /** The card pile design registry service registration token. */
    private ServiceRegistration cardPileDesignRegistryServiceRegistration_;

    /** The card pile design registry service tracker. */
    private ServiceTracker cardPileDesignRegistryServiceTracker_;


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
        if( cardPileDesignRegistryServiceTracker_ != null )
        {
            cardPileDesignRegistryServiceTracker_.close();
            cardPileDesignRegistryServiceTracker_ = null;
        }
        if( cardDesignRegistryServiceTracker_ != null )
        {
            cardDesignRegistryServiceTracker_.close();
            cardDesignRegistryServiceTracker_ = null;
        }

        // Unregister package-specific services

        // Unregister bundle-specific services
        if( cardPileDesignRegistryServiceRegistration_ != null )
        {
            cardPileDesignRegistryServiceRegistration_.unregister();
            cardPileDesignRegistryServiceRegistration_ = null;
        }
        if( cardDesignRegistryServiceRegistration_ != null )
        {
            cardDesignRegistryServiceRegistration_.unregister();
            cardDesignRegistryServiceRegistration_ = null;
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
     * Gets the card pile design registry service managed by this object.
     * 
     * @return The card pile design registry service managed by this object;
     *         never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If this object is not open.
     */
    /* @NonNull */
    public ICardPileDesignRegistry getCardPileDesignRegistry()
    {
        assertStateLegal( cardPileDesignRegistryServiceTracker_ != null, Messages.Services_cardPileDesignRegistryServiceTracker_notSet );

        return (ICardPileDesignRegistry)cardPileDesignRegistryServiceTracker_.getService();
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
        cardDesignRegistryServiceRegistration_ = context.registerService( ICardDesignRegistry.class.getName(), new CardDesignRegistry(), null );
        cardPileDesignRegistryServiceRegistration_ = context.registerService( ICardPileDesignRegistry.class.getName(), new CardPileDesignRegistry(), null );

        // Register package-specific services

        // Open bundle-specific services
        cardDesignRegistryServiceTracker_ = new ServiceTracker( context, cardDesignRegistryServiceRegistration_.getReference(), null );
        cardDesignRegistryServiceTracker_.open();
        cardPileDesignRegistryServiceTracker_ = new ServiceTracker( context, cardPileDesignRegistryServiceRegistration_.getReference(), null );
        cardPileDesignRegistryServiceTracker_.open();

        // Register package-specific adapters
    }
}

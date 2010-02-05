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
import org.gamegineer.table.core.services.cardpilebasedesignregistry.ICardPileBaseDesignRegistry;
import org.gamegineer.table.core.services.cardsurfacedesignregistry.ICardSurfaceDesignRegistry;
import org.gamegineer.table.internal.core.services.cardpilebasedesignregistry.CardPileBaseDesignRegistry;
import org.gamegineer.table.internal.core.services.cardsurfacedesignregistry.CardSurfaceDesignRegistry;
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

    /** The card pile base design registry service registration token. */
    private ServiceRegistration cardPileBaseDesignRegistryServiceRegistration_;

    /** The card pile base design registry service tracker. */
    private ServiceTracker cardPileBaseDesignRegistryServiceTracker_;

    /** The card surface design registry service registration token. */
    private ServiceRegistration cardSurfaceDesignRegistryServiceRegistration_;

    /** The card surface design registry service tracker. */
    private ServiceTracker cardSurfaceDesignRegistryServiceTracker_;


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
        if( cardSurfaceDesignRegistryServiceTracker_ != null )
        {
            cardSurfaceDesignRegistryServiceTracker_.close();
            cardSurfaceDesignRegistryServiceTracker_ = null;
        }
        if( cardPileBaseDesignRegistryServiceTracker_ != null )
        {
            cardPileBaseDesignRegistryServiceTracker_.close();
            cardPileBaseDesignRegistryServiceTracker_ = null;
        }

        // Unregister package-specific services

        // Unregister bundle-specific services
        if( cardSurfaceDesignRegistryServiceRegistration_ != null )
        {
            cardSurfaceDesignRegistryServiceRegistration_.unregister();
            cardSurfaceDesignRegistryServiceRegistration_ = null;
        }
        if( cardPileBaseDesignRegistryServiceRegistration_ != null )
        {
            cardPileBaseDesignRegistryServiceRegistration_.unregister();
            cardPileBaseDesignRegistryServiceRegistration_ = null;
        }
    }

    /**
     * Gets the card pile base design registry service managed by this object.
     * 
     * @return The card pile base design registry service managed by this
     *         object; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If this object is not open.
     */
    /* @NonNull */
    public ICardPileBaseDesignRegistry getCardPileBaseDesignRegistry()
    {
        assertStateLegal( cardPileBaseDesignRegistryServiceTracker_ != null, Messages.Services_cardPileBaseDesignRegistryServiceTracker_notSet );

        return (ICardPileBaseDesignRegistry)cardPileBaseDesignRegistryServiceTracker_.getService();
    }

    /**
     * Gets the card surface design registry service managed by this object.
     * 
     * @return The card surface design registry service managed by this object;
     *         never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If this object is not open.
     */
    /* @NonNull */
    public ICardSurfaceDesignRegistry getCardSurfaceDesignRegistry()
    {
        assertStateLegal( cardSurfaceDesignRegistryServiceTracker_ != null, Messages.Services_cardSurfaceDesignRegistryServiceTracker_notSet );

        return (ICardSurfaceDesignRegistry)cardSurfaceDesignRegistryServiceTracker_.getService();
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
        cardPileBaseDesignRegistryServiceRegistration_ = context.registerService( ICardPileBaseDesignRegistry.class.getName(), new CardPileBaseDesignRegistry(), null );
        cardSurfaceDesignRegistryServiceRegistration_ = context.registerService( ICardSurfaceDesignRegistry.class.getName(), new CardSurfaceDesignRegistry(), null );

        // Register package-specific services

        // Open bundle-specific services
        cardPileBaseDesignRegistryServiceTracker_ = new ServiceTracker( context, cardPileBaseDesignRegistryServiceRegistration_.getReference(), null );
        cardPileBaseDesignRegistryServiceTracker_.open();
        cardSurfaceDesignRegistryServiceTracker_ = new ServiceTracker( context, cardSurfaceDesignRegistryServiceRegistration_.getReference(), null );
        cardSurfaceDesignRegistryServiceTracker_.open();

        // Register package-specific adapters
    }
}

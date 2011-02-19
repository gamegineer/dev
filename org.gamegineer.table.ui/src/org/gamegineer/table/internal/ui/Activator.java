/*
 * Activator.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Sep 16, 2009 at 10:40:53 PM.
 */

package org.gamegineer.table.internal.ui;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ICardPileBaseDesignRegistry;
import org.gamegineer.table.core.ICardSurfaceDesignRegistry;
import org.gamegineer.table.ui.services.cardpilebasedesignuiregistry.ICardPileBaseDesignUIRegistry;
import org.gamegineer.table.ui.services.cardsurfacedesignuiregistry.ICardSurfaceDesignUIRegistry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.packageadmin.PackageAdmin;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;
import org.osgi.service.prefs.PreferencesService;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The bundle activator for the org.gamegineer.table.ui bundle.
 */
@ThreadSafe
public final class Activator
    implements BundleActivator
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance of the bundle activator. */
    private static final AtomicReference<Activator> instance_ = new AtomicReference<Activator>();

    /** The bundle context. */
    @GuardedBy( "lock_" )
    private BundleContext bundleContext_;

    /** The card pile base design registry service tracker. */
    @GuardedBy( "lock_" )
    private ServiceTracker cardPileBaseDesignRegistryTracker_;

    /** The card pile base design user interface registry service tracker. */
    @GuardedBy( "lock_" )
    private ServiceTracker cardPileBaseDesignUIRegistryTracker_;

    /** The card surface design registry service tracker. */
    @GuardedBy( "lock_" )
    private ServiceTracker cardSurfaceDesignRegistryTracker_;

    /** The card surface design user interface registry service tracker. */
    @GuardedBy( "lock_" )
    private ServiceTracker cardSurfaceDesignUIRegistryTracker_;

    /** The instance lock. */
    private final Object lock_;

    /** The package administration service tracker. */
    @GuardedBy( "lock_" )
    private ServiceTracker packageAdminTracker_;

    /** The preferences service tracker. */
    @GuardedBy( "lock_" )
    private ServiceTracker preferencesServiceTracker_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Activator} class.
     */
    public Activator()
    {
        lock_ = new Object();
        bundleContext_ = null;
        cardPileBaseDesignRegistryTracker_ = null;
        cardPileBaseDesignUIRegistryTracker_ = null;
        cardSurfaceDesignRegistryTracker_ = null;
        cardSurfaceDesignUIRegistryTracker_ = null;
        packageAdminTracker_ = null;
        preferencesServiceTracker_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the bundle context.
     * 
     * @return The bundle context; never {@code null}.
     */
    /* @NonNull */
    public BundleContext getBundleContext()
    {
        synchronized( lock_ )
        {
            assert bundleContext_ != null;
            return bundleContext_;
        }
    }

    /**
     * Gets the card pile base design registry service.
     * 
     * @return The card pile base design registry service or {@code null} if no
     *         card pile base design registry service is available.
     */
    /* @Nullable */
    public ICardPileBaseDesignRegistry getCardPileBaseDesignRegistry()
    {
        synchronized( lock_ )
        {
            assert bundleContext_ != null;

            if( cardPileBaseDesignRegistryTracker_ == null )
            {
                cardPileBaseDesignRegistryTracker_ = new ServiceTracker( bundleContext_, ICardPileBaseDesignRegistry.class.getName(), null );
                cardPileBaseDesignRegistryTracker_.open();
            }

            return (ICardPileBaseDesignRegistry)cardPileBaseDesignRegistryTracker_.getService();
        }
    }

    /**
     * Gets the card pile base design user interface registry service.
     * 
     * @return The card pile base design user interface registry service or
     *         {@code null} if no card pile base design user interface registry
     *         service is available.
     */
    /* @Nullable */
    public ICardPileBaseDesignUIRegistry getCardPileBaseDesignUIRegistry()
    {
        synchronized( lock_ )
        {
            assert bundleContext_ != null;

            if( cardPileBaseDesignUIRegistryTracker_ == null )
            {
                cardPileBaseDesignUIRegistryTracker_ = new ServiceTracker( bundleContext_, ICardPileBaseDesignUIRegistry.class.getName(), null );
                cardPileBaseDesignUIRegistryTracker_.open();
            }

            return (ICardPileBaseDesignUIRegistry)cardPileBaseDesignUIRegistryTracker_.getService();
        }
    }

    /**
     * Gets the card surface design registry service.
     * 
     * @return The card surface design registry service or {@code null} if no
     *         card surface design registry service is available.
     */
    /* @Nullable */
    public ICardSurfaceDesignRegistry getCardSurfaceDesignRegistry()
    {
        synchronized( lock_ )
        {
            assert bundleContext_ != null;

            if( cardSurfaceDesignRegistryTracker_ == null )
            {
                cardSurfaceDesignRegistryTracker_ = new ServiceTracker( bundleContext_, ICardSurfaceDesignRegistry.class.getName(), null );
                cardSurfaceDesignRegistryTracker_.open();
            }

            return (ICardSurfaceDesignRegistry)cardSurfaceDesignRegistryTracker_.getService();
        }
    }

    /**
     * Gets the card surface design user interface registry service.
     * 
     * @return The card surface design user interface registry service or
     *         {@code null} if no card surface design user interface registry
     *         service is available.
     */
    /* @Nullable */
    public ICardSurfaceDesignUIRegistry getCardSurfaceDesignUIRegistry()
    {
        synchronized( lock_ )
        {
            assert bundleContext_ != null;

            if( cardSurfaceDesignUIRegistryTracker_ == null )
            {
                cardSurfaceDesignUIRegistryTracker_ = new ServiceTracker( bundleContext_, ICardSurfaceDesignUIRegistry.class.getName(), null );
                cardSurfaceDesignUIRegistryTracker_.open();
            }

            return (ICardSurfaceDesignUIRegistry)cardSurfaceDesignUIRegistryTracker_.getService();
        }
    }

    /**
     * Gets the default instance of the bundle activator.
     * 
     * @return The default instance of the bundle activator; never {@code null}.
     */
    /* @NonNull */
    public static Activator getDefault()
    {
        final Activator instance = instance_.get();
        assert instance != null;
        return instance;
    }

    /**
     * Gets the package administration service.
     * 
     * @return The package administration service or {@code null} if no package
     *         administration service is available.
     */
    /* @Nullable */
    public PackageAdmin getPackageAdmin()
    {
        synchronized( lock_ )
        {
            assert bundleContext_ != null;

            if( packageAdminTracker_ == null )
            {
                packageAdminTracker_ = new ServiceTracker( bundleContext_, PackageAdmin.class.getName(), null );
                packageAdminTracker_.open();
            }

            return (PackageAdmin)packageAdminTracker_.getService();
        }
    }

    /**
     * Gets the preferences service.
     * 
     * @return The preferences service or {@code null} if no preferences service
     *         is available.
     */
    /* @Nullable */
    public PreferencesService getPreferencesService()
    {
        synchronized( lock_ )
        {
            assert bundleContext_ != null;

            if( preferencesServiceTracker_ == null )
            {
                preferencesServiceTracker_ = new ServiceTracker( bundleContext_, PreferencesService.class.getName(), null );
                preferencesServiceTracker_.open();
            }

            return (PreferencesService)preferencesServiceTracker_.getService();
        }
    }

    /**
     * Gets the root node from the user preferences for this bundle.
     * 
     * @return The root node from the user preferences for this bundle or
     *         {@code null} if no preferences service is available.
     */
    /* @Nullable */
    public Preferences getUserPreferences()
    {
        final PreferencesService preferencesService = getPreferencesService();
        if( preferencesService == null )
        {
            return null;
        }

        return preferencesService.getUserPreferences( System.getProperty( "user.name" ) ); //$NON-NLS-1$
    }

    /**
     * Gets the node for the specified type from the user preferences for this
     * bundle.
     * 
     * @param type
     *        The type whose user preference node is desired; must not be
     *        {@code null}.
     * 
     * @return The node for the specified type from the user preferences for
     *         this bundle or {@code null} if no preferences service is
     *         available.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code type} is {@code null}.
     */
    /* @Nullable */
    public Preferences getUserPreferences(
        /* @NonNull */
        final Class<?> type )
    {
        assertArgumentNotNull( type, "type" ); //$NON-NLS-1$

        final Preferences userPreferences = getUserPreferences();
        if( userPreferences == null )
        {
            return null;
        }

        return userPreferences.node( type.getName().replace( '.', '/' ) );
    }

    /**
     * Saves the current user's preferences for this bundle.
     */
    private void saveUserPreferences()
    {
        final Preferences userPreferences = getUserPreferences();
        if( userPreferences != null )
        {
            try
            {
                userPreferences.flush();
            }
            catch( final BackingStoreException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.Activator_saveUserPreferenecs_error, e );
            }
        }
    }

    /*
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(
        final BundleContext bundleContext )
    {
        assertArgumentNotNull( bundleContext, "bundleContext" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assert bundleContext_ == null;
            bundleContext_ = bundleContext;
        }

        final boolean wasInstanceNull = instance_.compareAndSet( null, this );
        assert wasInstanceNull;
    }

    /*
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(
        final BundleContext bundleContext )
    {
        assertArgumentNotNull( bundleContext, "bundleContext" ); //$NON-NLS-1$

        saveUserPreferences();

        final boolean wasInstanceNonNull = instance_.compareAndSet( this, null );
        assert wasInstanceNonNull;

        synchronized( lock_ )
        {
            assert bundleContext_ != null;
            bundleContext_ = null;

            if( cardPileBaseDesignRegistryTracker_ != null )
            {
                cardPileBaseDesignRegistryTracker_.close();
                cardPileBaseDesignRegistryTracker_ = null;
            }
            if( cardPileBaseDesignUIRegistryTracker_ != null )
            {
                cardPileBaseDesignUIRegistryTracker_.close();
                cardPileBaseDesignUIRegistryTracker_ = null;
            }
            if( cardSurfaceDesignRegistryTracker_ != null )
            {
                cardSurfaceDesignRegistryTracker_.close();
                cardSurfaceDesignRegistryTracker_ = null;
            }
            if( cardSurfaceDesignUIRegistryTracker_ != null )
            {
                cardSurfaceDesignUIRegistryTracker_.close();
                cardSurfaceDesignUIRegistryTracker_ = null;
            }
            if( packageAdminTracker_ != null )
            {
                packageAdminTracker_.close();
                packageAdminTracker_ = null;
            }
            if( preferencesServiceTracker_ != null )
            {
                preferencesServiceTracker_.close();
                preferencesServiceTracker_ = null;
            }
        }
    }
}

/*
 * Activator.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.gamegineer.common.core.app.IBranding;
import org.gamegineer.common.ui.help.IHelpSystem;
import org.gamegineer.table.core.ITableEnvironmentFactory;
import org.gamegineer.table.net.ITableNetworkFactory;
import org.gamegineer.table.ui.IComponentStrategyUIRegistry;
import org.gamegineer.table.ui.IComponentSurfaceDesignUIRegistry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
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
    private static final AtomicReference<Activator> instance_ = new AtomicReference<>();

    /** The application branding service tracker. */
    @GuardedBy( "lock_" )
    private ServiceTracker<IBranding, IBranding> brandingTracker_;

    /** The bundle context. */
    @GuardedBy( "lock_" )
    private BundleContext bundleContext_;

    /** The bundle image manager. */
    @GuardedBy( "lock_" )
    private BundleImages bundleImages_;

    /** The component strategy user interface registry service tracker. */
    @GuardedBy( "lock_" )
    private ServiceTracker<IComponentStrategyUIRegistry, IComponentStrategyUIRegistry> componentStrategyUIRegistryTracker_;

    /** The component surface design user interface registry service tracker. */
    @GuardedBy( "lock_" )
    private ServiceTracker<IComponentSurfaceDesignUIRegistry, IComponentSurfaceDesignUIRegistry> componentSurfaceDesignUIRegistryTracker_;

    /** The executor service tracker. */
    @GuardedBy( "lock_" )
    private ServiceTracker<ExecutorService, ExecutorService> executorServiceTracker_;

    /** The extension registry service tracker. */
    @GuardedBy( "lock_" )
    private ServiceTracker<IExtensionRegistry, IExtensionRegistry> extensionRegistryTracker_;

    /** The help system service tracker. */
    @GuardedBy( "lock_" )
    private ServiceTracker<IHelpSystem, IHelpSystem> helpSystemTracker_;

    /** The instance lock. */
    private final Object lock_;

    /** The preferences service tracker. */
    @GuardedBy( "lock_" )
    private ServiceTracker<PreferencesService, PreferencesService> preferencesServiceTracker_;

    /** The table environment factory service tracker. */
    @GuardedBy( "lock_" )
    private ServiceTracker<ITableEnvironmentFactory, ITableEnvironmentFactory> tableEnvironmentFactoryTracker_;

    /** The table network factory service tracker. */
    @GuardedBy( "lock_" )
    private ServiceTracker<ITableNetworkFactory, ITableNetworkFactory> tableNetworkFactoryTracker_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Activator} class.
     */
    public Activator()
    {
        lock_ = new Object();
        brandingTracker_ = null;
        bundleContext_ = null;
        bundleImages_ = null;
        componentStrategyUIRegistryTracker_ = null;
        componentSurfaceDesignUIRegistryTracker_ = null;
        executorServiceTracker_ = null;
        extensionRegistryTracker_ = null;
        helpSystemTracker_ = null;
        preferencesServiceTracker_ = null;
        tableEnvironmentFactoryTracker_ = null;
        tableNetworkFactoryTracker_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the application branding service.
     * 
     * @return The application branding service or {@code null} if no
     *         application branding service is available.
     */
    /* @Nullable */
    public IBranding getBranding()
    {
        synchronized( lock_ )
        {
            assert bundleContext_ != null;

            if( brandingTracker_ == null )
            {
                brandingTracker_ = new ServiceTracker<>( bundleContext_, IBranding.class, null );
                brandingTracker_.open();
            }

            return brandingTracker_.getService();
        }
    }

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
     * Gets the bundle image manager.
     * 
     * @return The bundle image manager; never {@code null}.
     */
    /* @NonNull */
    public BundleImages getBundleImages()
    {
        synchronized( lock_ )
        {
            assert bundleContext_ != null;

            if( bundleImages_ == null )
            {
                bundleImages_ = new BundleImages( bundleContext_ );
            }

            return bundleImages_;
        }
    }

    /**
     * Gets the component strategy user interface registry service.
     * 
     * @return The component strategy user interface registry service or
     *         {@code null} if no component strategy user interface registry
     *         service is available.
     */
    /* @Nullable */
    public IComponentStrategyUIRegistry getComponentStrategyUIRegistry()
    {
        synchronized( lock_ )
        {
            assert bundleContext_ != null;

            if( componentStrategyUIRegistryTracker_ == null )
            {
                componentStrategyUIRegistryTracker_ = new ServiceTracker<>( bundleContext_, IComponentStrategyUIRegistry.class, null );
                componentStrategyUIRegistryTracker_.open();
            }

            return componentStrategyUIRegistryTracker_.getService();
        }
    }

    /**
     * Gets the component surface design user interface registry service.
     * 
     * @return The component surface design user interface registry service or
     *         {@code null} if no component surface design user interface
     *         registry service is available.
     */
    /* @Nullable */
    public IComponentSurfaceDesignUIRegistry getComponentSurfaceDesignUIRegistry()
    {
        synchronized( lock_ )
        {
            assert bundleContext_ != null;

            if( componentSurfaceDesignUIRegistryTracker_ == null )
            {
                componentSurfaceDesignUIRegistryTracker_ = new ServiceTracker<>( bundleContext_, IComponentSurfaceDesignUIRegistry.class, null );
                componentSurfaceDesignUIRegistryTracker_.open();
            }

            return componentSurfaceDesignUIRegistryTracker_.getService();
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
     * Gets the executor service.
     * 
     * @return The executor service; never {@code null}.
     */
    /* @NonNull */
    public ExecutorService getExecutorService()
    {
        final ExecutorService executorService;
        synchronized( lock_ )
        {
            assert bundleContext_ != null;

            if( executorServiceTracker_ == null )
            {
                executorServiceTracker_ = new ServiceTracker<>( bundleContext_, ExecutorService.class, null );
                executorServiceTracker_.open();
            }

            executorService = executorServiceTracker_.getService();
        }

        if( executorService == null )
        {
            throw new AssertionError( "the executor service is not available" ); //$NON-NLS-1$
        }

        return executorService;
    }

    /**
     * Gets the extension registry service.
     * 
     * @return The extension registry service; never {@code null}.
     */
    /* @Nullable */
    public IExtensionRegistry getExtensionRegistry()
    {
        synchronized( lock_ )
        {
            assert bundleContext_ != null;

            if( extensionRegistryTracker_ == null )
            {
                extensionRegistryTracker_ = new ServiceTracker<>( bundleContext_, IExtensionRegistry.class, null );
                extensionRegistryTracker_.open();
            }

            return extensionRegistryTracker_.getService();
        }
    }

    /**
     * Gets the help system service.
     * 
     * @return The help system service or {@code null} if no help system service
     *         is available.
     */
    /* @Nullable */
    public IHelpSystem getHelpSystem()
    {
        synchronized( lock_ )
        {
            assert bundleContext_ != null;

            if( helpSystemTracker_ == null )
            {
                helpSystemTracker_ = new ServiceTracker<>( bundleContext_, IHelpSystem.class, null );
                helpSystemTracker_.open();
            }

            return helpSystemTracker_.getService();
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
                preferencesServiceTracker_ = new ServiceTracker<>( bundleContext_, PreferencesService.class, null );
                preferencesServiceTracker_.open();
            }

            return preferencesServiceTracker_.getService();
        }
    }

    /**
     * Gets the table environment factory service.
     * 
     * @return The table environment factory service or {@code null} if the
     *         table environment factory service is not available.
     */
    /* @Nullable */
    public ITableEnvironmentFactory getTableEnvironmentFactory()
    {
        synchronized( lock_ )
        {
            assert bundleContext_ != null;

            if( tableEnvironmentFactoryTracker_ == null )
            {
                tableEnvironmentFactoryTracker_ = new ServiceTracker<>( bundleContext_, ITableEnvironmentFactory.class, null );
                tableEnvironmentFactoryTracker_.open();
            }

            return tableEnvironmentFactoryTracker_.getService();
        }
    }

    /**
     * Gets the table network factory service.
     * 
     * @return The table network factory service or {@code null} if the table
     *         network factory service is not available.
     */
    /* @Nullable */
    public ITableNetworkFactory getTableNetworkFactory()
    {
        synchronized( lock_ )
        {
            assert bundleContext_ != null;

            if( tableNetworkFactoryTracker_ == null )
            {
                tableNetworkFactoryTracker_ = new ServiceTracker<>( bundleContext_, ITableNetworkFactory.class, null );
                tableNetworkFactoryTracker_.open();
            }

            return tableNetworkFactoryTracker_.getService();
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
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Activator_saveUserPreferenecs_error, e );
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

            if( brandingTracker_ != null )
            {
                brandingTracker_.close();
                brandingTracker_ = null;
            }
            if( bundleImages_ != null )
            {
                bundleImages_.dispose();
                bundleImages_ = null;
            }
            if( componentStrategyUIRegistryTracker_ != null )
            {
                componentStrategyUIRegistryTracker_.close();
                componentStrategyUIRegistryTracker_ = null;
            }
            if( componentSurfaceDesignUIRegistryTracker_ != null )
            {
                componentSurfaceDesignUIRegistryTracker_.close();
                componentSurfaceDesignUIRegistryTracker_ = null;
            }
            if( executorServiceTracker_ != null )
            {
                executorServiceTracker_.close();
                executorServiceTracker_ = null;
            }
            if( extensionRegistryTracker_ != null )
            {
                extensionRegistryTracker_.close();
                extensionRegistryTracker_ = null;
            }
            if( helpSystemTracker_ != null )
            {
                helpSystemTracker_.close();
                helpSystemTracker_ = null;
            }
            if( preferencesServiceTracker_ != null )
            {
                preferencesServiceTracker_.close();
                preferencesServiceTracker_ = null;
            }
            if( tableEnvironmentFactoryTracker_ != null )
            {
                tableEnvironmentFactoryTracker_.close();
                tableEnvironmentFactoryTracker_ = null;
            }
            if( tableNetworkFactoryTracker_ != null )
            {
                tableNetworkFactoryTracker_.close();
                tableNetworkFactoryTracker_ = null;
            }
        }
    }
}

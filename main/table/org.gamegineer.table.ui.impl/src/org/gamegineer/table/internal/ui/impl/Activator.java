/*
 * Activator.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Nov 29, 2013 at 8:15:40 PM.
 */

package org.gamegineer.table.internal.ui.impl;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.app.IBranding;
import org.gamegineer.common.core.util.osgi.ServiceTrackerUtils;
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
 * The bundle activator for the org.gamegineer.table.ui.impl bundle.
 */
@ThreadSafe
public final class Activator
    implements BundleActivator
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance of the bundle activator. */
    private static final AtomicReference<@Nullable Activator> instance_ = new AtomicReference<>();

    /** A reference to the application branding service tracker. */
    @GuardedBy( "lock_" )
    private final AtomicReference<@Nullable ServiceTracker<IBranding, IBranding>> brandingTrackerRef_;

    /** The bundle context. */
    @GuardedBy( "lock_" )
    private @Nullable BundleContext bundleContext_;

    /** The bundle image manager. */
    @GuardedBy( "lock_" )
    private @Nullable BundleImages bundleImages_;

    /**
     * A reference to the component strategy user interface registry service
     * tracker.
     */
    @GuardedBy( "lock_" )
    private final AtomicReference<@Nullable ServiceTracker<IComponentStrategyUIRegistry, IComponentStrategyUIRegistry>> componentStrategyUIRegistryTrackerRef_;

    /**
     * A reference to the component surface design user interface registry
     * service tracker.
     */
    @GuardedBy( "lock_" )
    private final AtomicReference<@Nullable ServiceTracker<IComponentSurfaceDesignUIRegistry, IComponentSurfaceDesignUIRegistry>> componentSurfaceDesignUIRegistryTrackerRef_;

    /** A reference to the executor service tracker. */
    @GuardedBy( "lock_" )
    private final AtomicReference<@Nullable ServiceTracker<ExecutorService, ExecutorService>> executorServiceTrackerRef_;

    /** A reference to the extension registry service tracker. */
    @GuardedBy( "lock_" )
    private final AtomicReference<@Nullable ServiceTracker<IExtensionRegistry, IExtensionRegistry>> extensionRegistryTrackerRef_;

    /** A reference to the help system service tracker. */
    @GuardedBy( "lock_" )
    private final AtomicReference<@Nullable ServiceTracker<IHelpSystem, IHelpSystem>> helpSystemTrackerRef_;

    /** The instance lock. */
    private final Object lock_;

    /** A reference to the preferences service tracker. */
    @GuardedBy( "lock_" )
    private final AtomicReference<@Nullable ServiceTracker<PreferencesService, PreferencesService>> preferencesServiceTrackerRef_;

    /** A reference to the table environment factory service tracker. */
    @GuardedBy( "lock_" )
    private final AtomicReference<@Nullable ServiceTracker<ITableEnvironmentFactory, ITableEnvironmentFactory>> tableEnvironmentFactoryTrackerRef_;

    /** A reference to the table network factory service tracker. */
    @GuardedBy( "lock_" )
    private final AtomicReference<@Nullable ServiceTracker<ITableNetworkFactory, ITableNetworkFactory>> tableNetworkFactoryTrackerRef_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Activator} class.
     */
    public Activator()
    {
        lock_ = new Object();
        brandingTrackerRef_ = new AtomicReference<>();
        bundleContext_ = null;
        bundleImages_ = null;
        componentStrategyUIRegistryTrackerRef_ = new AtomicReference<>();
        componentSurfaceDesignUIRegistryTrackerRef_ = new AtomicReference<>();
        executorServiceTrackerRef_ = new AtomicReference<>();
        extensionRegistryTrackerRef_ = new AtomicReference<>();
        helpSystemTrackerRef_ = new AtomicReference<>();
        preferencesServiceTrackerRef_ = new AtomicReference<>();
        tableEnvironmentFactoryTrackerRef_ = new AtomicReference<>();
        tableNetworkFactoryTrackerRef_ = new AtomicReference<>();
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
    public @Nullable IBranding getBranding()
    {
        synchronized( lock_ )
        {
            return ServiceTrackerUtils.openService( brandingTrackerRef_, getBundleContextInternal(), nonNull( IBranding.class ) );
        }
    }

    /**
     * Gets the bundle context.
     * 
     * @return The bundle context.
     */
    public BundleContext getBundleContext()
    {
        synchronized( lock_ )
        {
            return getBundleContextInternal();
        }
    }

    /**
     * Gets the bundle context.
     * 
     * @return The bundle context.
     */
    @GuardedBy( "lock_" )
    private BundleContext getBundleContextInternal()
    {
        assert bundleContext_ != null;
        return bundleContext_;
    }

    /**
     * Gets the bundle image manager.
     * 
     * @return The bundle image manager.
     */
    public BundleImages getBundleImages()
    {
        synchronized( lock_ )
        {
            assert bundleContext_ != null;

            if( bundleImages_ == null )
            {
                bundleImages_ = new BundleImages( bundleContext_ );
            }

            assert bundleImages_ != null;
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
    public @Nullable IComponentStrategyUIRegistry getComponentStrategyUIRegistry()
    {
        synchronized( lock_ )
        {
            return ServiceTrackerUtils.openService( componentStrategyUIRegistryTrackerRef_, getBundleContextInternal(), nonNull( IComponentStrategyUIRegistry.class ) );
        }
    }

    /**
     * Gets the component surface design user interface registry service.
     * 
     * @return The component surface design user interface registry service or
     *         {@code null} if no component surface design user interface
     *         registry service is available.
     */
    public @Nullable IComponentSurfaceDesignUIRegistry getComponentSurfaceDesignUIRegistry()
    {
        synchronized( lock_ )
        {
            return ServiceTrackerUtils.openService( componentSurfaceDesignUIRegistryTrackerRef_, getBundleContextInternal(), nonNull( IComponentSurfaceDesignUIRegistry.class ) );
        }
    }

    /**
     * Gets the default instance of the bundle activator.
     * 
     * @return The default instance of the bundle activator.
     */
    public static Activator getDefault()
    {
        final Activator instance = instance_.get();
        assert instance != null;
        return instance;
    }

    /**
     * Gets the executor service.
     * 
     * @return The executor service.
     */
    public ExecutorService getExecutorService()
    {
        final ExecutorService executorService;
        synchronized( lock_ )
        {
            executorService = ServiceTrackerUtils.openService( executorServiceTrackerRef_, getBundleContextInternal(), nonNull( ExecutorService.class ) );
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
     * @return The extension registry service.
     */
    public @Nullable IExtensionRegistry getExtensionRegistry()
    {
        synchronized( lock_ )
        {
            return ServiceTrackerUtils.openService( extensionRegistryTrackerRef_, getBundleContextInternal(), nonNull( IExtensionRegistry.class ) );
        }
    }

    /**
     * Gets the help system service.
     * 
     * @return The help system service or {@code null} if no help system service
     *         is available.
     */
    public @Nullable IHelpSystem getHelpSystem()
    {
        synchronized( lock_ )
        {
            return ServiceTrackerUtils.openService( helpSystemTrackerRef_, getBundleContextInternal(), nonNull( IHelpSystem.class ) );
        }
    }

    /**
     * Gets the preferences service.
     * 
     * @return The preferences service or {@code null} if no preferences service
     *         is available.
     */
    public @Nullable PreferencesService getPreferencesService()
    {
        synchronized( lock_ )
        {
            return ServiceTrackerUtils.openService( preferencesServiceTrackerRef_, getBundleContextInternal(), nonNull( PreferencesService.class ) );
        }
    }

    /**
     * Gets the table environment factory service.
     * 
     * @return The table environment factory service or {@code null} if the
     *         table environment factory service is not available.
     */
    public @Nullable ITableEnvironmentFactory getTableEnvironmentFactory()
    {
        synchronized( lock_ )
        {
            return ServiceTrackerUtils.openService( tableEnvironmentFactoryTrackerRef_, getBundleContextInternal(), nonNull( ITableEnvironmentFactory.class ) );
        }
    }

    /**
     * Gets the table network factory service.
     * 
     * @return The table network factory service or {@code null} if the table
     *         network factory service is not available.
     */
    public @Nullable ITableNetworkFactory getTableNetworkFactory()
    {
        synchronized( lock_ )
        {
            return ServiceTrackerUtils.openService( tableNetworkFactoryTrackerRef_, getBundleContextInternal(), nonNull( ITableNetworkFactory.class ) );
        }
    }

    /**
     * Gets the root node from the user preferences for this bundle.
     * 
     * @return The root node from the user preferences for this bundle or
     *         {@code null} if no preferences service is available.
     */
    public @Nullable Preferences getUserPreferences()
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
     *        The type whose user preference node is desired.
     * 
     * @return The node for the specified type from the user preferences for
     *         this bundle or {@code null} if no preferences service is
     *         available.
     */
    public @Nullable Preferences getUserPreferences(
        final Class<?> type )
    {
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
        final @Nullable BundleContext bundleContext )
    {
        if( bundleContext == null )
        {
            throw new NullPointerException( "bundleContext" ); //$NON-NLS-1$
        }

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
        final @Nullable BundleContext bundleContext )
    {
        if( bundleContext == null )
        {
            throw new NullPointerException( "bundleContext" ); //$NON-NLS-1$
        }

        saveUserPreferences();

        final boolean wasInstanceNonNull = instance_.compareAndSet( this, null );
        assert wasInstanceNonNull;

        synchronized( lock_ )
        {
            assert bundleContext_ != null;
            bundleContext_ = null;

            if( bundleImages_ != null )
            {
                bundleImages_.dispose();
                bundleImages_ = null;
            }

            ServiceTrackerUtils.closeService( brandingTrackerRef_ );
            ServiceTrackerUtils.closeService( componentStrategyUIRegistryTrackerRef_ );
            ServiceTrackerUtils.closeService( componentSurfaceDesignUIRegistryTrackerRef_ );
            ServiceTrackerUtils.closeService( executorServiceTrackerRef_ );
            ServiceTrackerUtils.closeService( extensionRegistryTrackerRef_ );
            ServiceTrackerUtils.closeService( helpSystemTrackerRef_ );
            ServiceTrackerUtils.closeService( preferencesServiceTrackerRef_ );
            ServiceTrackerUtils.closeService( tableEnvironmentFactoryTrackerRef_ );
            ServiceTrackerUtils.closeService( tableNetworkFactoryTrackerRef_ );
        }
    }
}

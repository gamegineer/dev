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
 * Created on Sep 27, 2013 at 9:44:23 PM.
 */

package org.gamegineer.table.internal.core.test;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import java.util.concurrent.atomic.AtomicReference;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.util.osgi.ServiceTrackerUtils;
import org.gamegineer.table.core.IComponentStrategyRegistry;
import org.gamegineer.table.core.IComponentSurfaceDesignRegistry;
import org.gamegineer.table.core.IContainerLayoutRegistry;
import org.gamegineer.table.core.ITableEnvironmentFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The bundle activator for the org.gamegineer.table.core.test bundle.
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

    /** The bundle context. */
    @GuardedBy( "lock_" )
    private @Nullable BundleContext bundleContext_;

    /** A reference to the component strategy registry service tracker. */
    @GuardedBy( "lock_" )
    private final AtomicReference<@Nullable ServiceTracker<IComponentStrategyRegistry, IComponentStrategyRegistry>> componentStrategyRegistryTrackerRef_;

    /** A reference to the component surface design registry service tracker. */
    @GuardedBy( "lock_" )
    private final AtomicReference<@Nullable ServiceTracker<IComponentSurfaceDesignRegistry, IComponentSurfaceDesignRegistry>> componentSurfaceDesignRegistryTrackerRef_;

    /** A reference to the container layout registry service tracker. */
    @GuardedBy( "lock_" )
    private final AtomicReference<@Nullable ServiceTracker<IContainerLayoutRegistry, IContainerLayoutRegistry>> containerLayoutRegistryTrackerRef_;

    /** The instance lock. */
    private final Object lock_;

    /** A reference to the table environment factory service tracker. */
    @GuardedBy( "lock_" )
    private final AtomicReference<@Nullable ServiceTracker<ITableEnvironmentFactory, ITableEnvironmentFactory>> tableEnvironmentFactoryTrackerRef_;


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
        componentStrategyRegistryTrackerRef_ = new AtomicReference<>();
        componentSurfaceDesignRegistryTrackerRef_ = new AtomicReference<>();
        containerLayoutRegistryTrackerRef_ = new AtomicReference<>();
        tableEnvironmentFactoryTrackerRef_ = new AtomicReference<>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the bundle context.
     * 
     * @return The bundle context; never {@code null}.
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
     * @return The bundle context; never {@code null}.
     */
    @GuardedBy( "lock_" )
    private BundleContext getBundleContextInternal()
    {
        assert bundleContext_ != null;
        return bundleContext_;
    }

    /**
     * Gets the default instance of the bundle activator.
     * 
     * @return The default instance of the bundle activator; never {@code null}.
     */
    public static Activator getDefault()
    {
        final Activator instance = instance_.get();
        assert instance != null;
        return instance;
    }

    /**
     * Gets the component strategy registry service.
     * 
     * @return The component strategy registry service or {@code null} if no
     *         component strategy registry service is available.
     */
    public @Nullable IComponentStrategyRegistry getComponentStrategyRegistry()
    {
        synchronized( lock_ )
        {
            return ServiceTrackerUtils.openService( componentStrategyRegistryTrackerRef_, getBundleContextInternal(), nonNull( IComponentStrategyRegistry.class ) );
        }
    }

    /**
     * Gets the component surface design registry service.
     * 
     * @return The component surface design registry service or {@code null} if
     *         no component surface design registry service is available.
     */
    public @Nullable IComponentSurfaceDesignRegistry getComponentSurfaceDesignRegistry()
    {
        synchronized( lock_ )
        {
            return ServiceTrackerUtils.openService( componentSurfaceDesignRegistryTrackerRef_, getBundleContextInternal(), nonNull( IComponentSurfaceDesignRegistry.class ) );
        }
    }

    /**
     * Gets the container layout registry service.
     * 
     * @return The container layout registry service or {@code null} if no
     *         container layout registry service is available.
     */
    public @Nullable IContainerLayoutRegistry getContainerLayoutRegistry()
    {
        synchronized( lock_ )
        {
            return ServiceTrackerUtils.openService( containerLayoutRegistryTrackerRef_, getBundleContextInternal(), nonNull( IContainerLayoutRegistry.class ) );
        }
    }

    /**
     * Gets the table environment factory service.
     * 
     * @return The table environment factory service or {@code null} if no table
     *         environment factory service is available.
     */
    public @Nullable ITableEnvironmentFactory getTableEnvironmentFactory()
    {
        synchronized( lock_ )
        {
            return ServiceTrackerUtils.openService( tableEnvironmentFactoryTrackerRef_, getBundleContextInternal(), nonNull( ITableEnvironmentFactory.class ) );
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

        final boolean wasInstanceNonNull = instance_.compareAndSet( this, null );
        assert wasInstanceNonNull;

        synchronized( lock_ )
        {
            assert bundleContext_ != null;
            bundleContext_ = null;

            ServiceTrackerUtils.closeService( componentStrategyRegistryTrackerRef_ );
            ServiceTrackerUtils.closeService( componentSurfaceDesignRegistryTrackerRef_ );
            ServiceTrackerUtils.closeService( containerLayoutRegistryTrackerRef_ );
            ServiceTrackerUtils.closeService( tableEnvironmentFactoryTrackerRef_ );
        }
    }
}

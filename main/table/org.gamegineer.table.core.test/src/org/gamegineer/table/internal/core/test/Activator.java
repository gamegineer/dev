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

import java.util.concurrent.atomic.AtomicReference;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
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

    /** The component strategy registry service tracker. */
    @GuardedBy( "lock_" )
    private @Nullable ServiceTracker<IComponentStrategyRegistry, IComponentStrategyRegistry> componentStrategyRegistryTracker_;

    /** The component surface design registry service tracker. */
    @GuardedBy( "lock_" )
    private @Nullable ServiceTracker<IComponentSurfaceDesignRegistry, IComponentSurfaceDesignRegistry> componentSurfaceDesignRegistryTracker_;

    /** The container layout registry service tracker. */
    @GuardedBy( "lock_" )
    private @Nullable ServiceTracker<IContainerLayoutRegistry, IContainerLayoutRegistry> containerLayoutRegistryTracker_;

    /** The instance lock. */
    private final Object lock_;

    /** The table environment factory service tracker. */
    @GuardedBy( "lock_" )
    private @Nullable ServiceTracker<ITableEnvironmentFactory, ITableEnvironmentFactory> tableEnvironmentFactoryTracker_;


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
        componentStrategyRegistryTracker_ = null;
        componentSurfaceDesignRegistryTracker_ = null;
        containerLayoutRegistryTracker_ = null;
        tableEnvironmentFactoryTracker_ = null;
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
            assert bundleContext_ != null;
            return bundleContext_;
        }
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
            assert bundleContext_ != null;

            if( componentStrategyRegistryTracker_ == null )
            {
                componentStrategyRegistryTracker_ = new ServiceTracker<>( bundleContext_, IComponentStrategyRegistry.class, null );
                componentStrategyRegistryTracker_.open();
            }

            assert componentStrategyRegistryTracker_ != null;
            return componentStrategyRegistryTracker_.getService();
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
            assert bundleContext_ != null;

            if( componentSurfaceDesignRegistryTracker_ == null )
            {
                componentSurfaceDesignRegistryTracker_ = new ServiceTracker<>( bundleContext_, IComponentSurfaceDesignRegistry.class, null );
                componentSurfaceDesignRegistryTracker_.open();
            }

            assert componentSurfaceDesignRegistryTracker_ != null;
            return componentSurfaceDesignRegistryTracker_.getService();
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
            assert bundleContext_ != null;

            if( containerLayoutRegistryTracker_ == null )
            {
                containerLayoutRegistryTracker_ = new ServiceTracker<>( bundleContext_, IContainerLayoutRegistry.class, null );
                containerLayoutRegistryTracker_.open();
            }

            assert containerLayoutRegistryTracker_ != null;
            return containerLayoutRegistryTracker_.getService();
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
            assert bundleContext_ != null;

            if( tableEnvironmentFactoryTracker_ == null )
            {
                tableEnvironmentFactoryTracker_ = new ServiceTracker<>( bundleContext_, ITableEnvironmentFactory.class, null );
                tableEnvironmentFactoryTracker_.open();
            }

            assert tableEnvironmentFactoryTracker_ != null;
            return tableEnvironmentFactoryTracker_.getService();
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

            if( componentStrategyRegistryTracker_ != null )
            {
                componentStrategyRegistryTracker_.close();
                componentStrategyRegistryTracker_ = null;
            }
            if( componentSurfaceDesignRegistryTracker_ != null )
            {
                componentSurfaceDesignRegistryTracker_.close();
                componentSurfaceDesignRegistryTracker_ = null;
            }
            if( containerLayoutRegistryTracker_ != null )
            {
                containerLayoutRegistryTracker_.close();
                containerLayoutRegistryTracker_ = null;
            }
            if( tableEnvironmentFactoryTracker_ != null )
            {
                tableEnvironmentFactoryTracker_.close();
                tableEnvironmentFactoryTracker_ = null;
            }
        }
    }
}

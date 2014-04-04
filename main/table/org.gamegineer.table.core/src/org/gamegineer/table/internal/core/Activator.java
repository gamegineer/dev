/*
 * Activator.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Sep 15, 2009 at 11:09:22 PM.
 */

package org.gamegineer.table.internal.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.core.IComponentStrategyRegistry;
import org.gamegineer.table.core.IComponentSurfaceDesignRegistry;
import org.gamegineer.table.core.IContainerLayoutRegistry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The bundle activator for the org.gamegineer.table.core bundle.
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

    /** The bundle context. */
    @GuardedBy( "lock_" )
    @Nullable
    private BundleContext bundleContext_;

    /** The component strategy registry service tracker. */
    @GuardedBy( "lock_" )
    @Nullable
    private ServiceTracker<IComponentStrategyRegistry, IComponentStrategyRegistry> componentStrategyRegistryTracker_;

    /** The component surface design registry service tracker. */
    @GuardedBy( "lock_" )
    @Nullable
    private ServiceTracker<IComponentSurfaceDesignRegistry, IComponentSurfaceDesignRegistry> componentSurfaceDesignRegistryTracker_;

    /** The container layout registry service tracker. */
    @GuardedBy( "lock_" )
    @Nullable
    private ServiceTracker<IContainerLayoutRegistry, IContainerLayoutRegistry> containerLayoutRegistryTracker_;

    /** The executor service tracker. */
    @GuardedBy( "lock_" )
    @Nullable
    private ServiceTracker<ExecutorService, ExecutorService> executorServiceTracker_;

    /** The instance lock. */
    private final Object lock_;


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
        executorServiceTracker_ = null;
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
     * Gets the component strategy registry service.
     * 
     * @return The component strategy registry service or {@code null} if no
     *         component strategy registry service is available.
     */
    @Nullable
    public IComponentStrategyRegistry getComponentStrategyRegistry()
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
    @Nullable
    public IComponentSurfaceDesignRegistry getComponentSurfaceDesignRegistry()
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
    @Nullable
    public IContainerLayoutRegistry getContainerLayoutRegistry()
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
     * Gets the executor service.
     * 
     * @return The executor service; never {@code null}.
     */
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

            assert executorServiceTracker_ != null;
            executorService = executorServiceTracker_.getService();
        }

        if( executorService == null )
        {
            throw new AssertionError( "the executor service is not available" ); //$NON-NLS-1$
        }

        return executorService;
    }

    /*
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(
        @Nullable
        final BundleContext bundleContext )
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
        @Nullable
        final BundleContext bundleContext )
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
            if( executorServiceTracker_ != null )
            {
                executorServiceTracker_.close();
                executorServiceTracker_ = null;
            }
        }
    }
}

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
 * Created on Sep 16, 2009 at 10:40:53 PM.
 */

package org.gamegineer.table.internal.ui;

import java.util.concurrent.atomic.AtomicReference;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.ui.IComponentStrategyUIRegistry;
import org.gamegineer.table.ui.IComponentSurfaceDesignUIRegistry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
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

    /** The bundle context. */
    @GuardedBy( "lock_" )
    @Nullable
    private BundleContext bundleContext_;

    /** The component strategy user interface registry service tracker. */
    @GuardedBy( "lock_" )
    @Nullable
    private ServiceTracker<IComponentStrategyUIRegistry, IComponentStrategyUIRegistry> componentStrategyUIRegistryTracker_;

    /** The component surface design user interface registry service tracker. */
    @GuardedBy( "lock_" )
    @Nullable
    private ServiceTracker<IComponentSurfaceDesignUIRegistry, IComponentSurfaceDesignUIRegistry> componentSurfaceDesignUIRegistryTracker_;

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
        componentStrategyUIRegistryTracker_ = null;
        componentSurfaceDesignUIRegistryTracker_ = null;
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
     * Gets the component strategy user interface registry service.
     * 
     * @return The component strategy user interface registry service or
     *         {@code null} if no component strategy user interface registry
     *         service is available.
     */
    @Nullable
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

            assert componentStrategyUIRegistryTracker_ != null;
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
    @Nullable
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

            assert componentSurfaceDesignUIRegistryTracker_ != null;
            return componentSurfaceDesignUIRegistryTracker_.getService();
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
        }
    }
}

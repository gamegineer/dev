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
 * Created on Nov 15, 2013 at 8:23:33 PM.
 */

package org.gamegineer.table.internal.net.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.core.ITableEnvironmentFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The bundle activator for the org.gamegineer.table.net.impl bundle.
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

    /** The executor service tracker. */
    @GuardedBy( "lock_" )
    private @Nullable ServiceTracker<ExecutorService, ExecutorService> executorServiceTracker_;

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
        executorServiceTracker_ = null;
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

            if( executorServiceTracker_ != null )
            {
                executorServiceTracker_.close();
                executorServiceTracker_ = null;
            }
            if( tableEnvironmentFactoryTracker_ != null )
            {
                tableEnvironmentFactoryTracker_.close();
                tableEnvironmentFactoryTracker_ = null;
            }
        }
    }
}

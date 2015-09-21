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
 * Created on Feb 26, 2008 at 12:18:03 AM.
 */

package org.gamegineer.common.internal.core;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import java.util.concurrent.atomic.AtomicReference;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.osgi.service.debug.DebugOptions;
import org.gamegineer.common.core.logging.ILoggingService;
import org.gamegineer.common.core.util.osgi.ServiceTrackerUtils;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The bundle activator for the org.gamegineer.common.core bundle.
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

    /** A reference to the debug options service tracker. */
    @GuardedBy( "lock_" )
    private final AtomicReference<@Nullable ServiceTracker<DebugOptions, DebugOptions>> debugOptionsTrackerRef_;

    /** The instance lock. */
    private final Object lock_;

    /** A reference to the logging service tracker. */
    @GuardedBy( "lock_" )
    private final AtomicReference<@Nullable ServiceTracker<ILoggingService, ILoggingService>> loggingServiceTrackerRef_;


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
        debugOptionsTrackerRef_ = new AtomicReference<>();
        loggingServiceTrackerRef_ = new AtomicReference<>();
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
     * Gets the debug options service.
     * 
     * @return The debug options service or {@code null} if no debug options
     *         service is available.
     */
    public @Nullable DebugOptions getDebugOptions()
    {
        synchronized( lock_ )
        {
            return ServiceTrackerUtils.openService( debugOptionsTrackerRef_, getBundleContextInternal(), nonNull( DebugOptions.class ) );
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
     * Gets the logging service.
     * 
     * @return The logging service or {@code null} if no logging service is
     *         available.
     */
    public @Nullable ILoggingService getLoggingService()
    {
        synchronized( lock_ )
        {
            return ServiceTrackerUtils.openService( loggingServiceTrackerRef_, getBundleContextInternal(), nonNull( ILoggingService.class ) );
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

            ServiceTrackerUtils.closeService( loggingServiceTrackerRef_ );
            ServiceTrackerUtils.closeService( debugOptionsTrackerRef_ );
        }
    }
}

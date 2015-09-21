/*
 * ServiceTrackerUtils.java
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
 * Created on Sep 21, 2015 at 12:49:13 PM.
 */

package org.gamegineer.common.core.util.osgi;

import java.util.concurrent.atomic.AtomicReference;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * A collection of methods useful for working with service trackers.
 */
@ThreadSafe
public final class ServiceTrackerUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ServiceTrackerUtils} class.
     */
    private ServiceTrackerUtils()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Closes the service associated with the specified service tracker.
     * 
     * @param <T>
     *        The type of the service.
     * 
     * @param serviceTrackerRef
     *        A reference to the service tracker; must not be {@code null}.
     */
    public static <@NonNull T> void closeService(
        final AtomicReference<@Nullable ServiceTracker<T, T>> serviceTrackerRef )
    {
        final ServiceTracker<?, ?> serviceTracker = serviceTrackerRef.getAndSet( null );
        if( serviceTracker != null )
        {
            serviceTracker.close();
        }
    }

    /**
     * Opens the service associated with the specified service tracker if
     * necessary.
     * 
     * @param <T>
     *        The type of the service.
     * 
     * @param bundleContext
     *        The bundle context; must not be {@code null}.
     * @param type
     *        The type of the service; must not be {@code null}.
     * @param serviceTrackerRef
     *        A reference to the service tracker; must not be {@code null}.
     * 
     * @return The service or {@code null} if the service is not available.
     */
    public static <@NonNull T> @Nullable T openService(
        final AtomicReference<@Nullable ServiceTracker<T, T>> serviceTrackerRef,
        final BundleContext bundleContext,
        final Class<T> type )
    {
        ServiceTracker<T, T> serviceTracker = serviceTrackerRef.get();
        if( serviceTracker == null )
        {
            serviceTracker = new ServiceTracker<>( bundleContext, type, null );
            serviceTrackerRef.set( serviceTracker );
            serviceTracker.open();
        }

        assert serviceTracker != null;
        return serviceTracker.getService();
    }
}

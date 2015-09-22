/*
 * BundleContextUtils.java
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
 * Created on Sep 21, 2015 at 9:18:16 PM.
 */

package org.gamegineer.common.core.util.osgi;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * A collection of methods useful for working with a bundle context.
 */
@ThreadSafe
public final class BundleContextUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BundleContextUtils} class.
     */
    private BundleContextUtils()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the service associated with the specified reference.
     * 
     * <p>
     * This method is provided as simply a wrapper for
     * {@link BundleContext#getService} with the null annotations. Due to
     * Eclipse Bug
     * <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=436469">436469</a>
     * , external null annotations cannot be added to the {@link BundleContext}
     * interface. Once this bug has been fixed, this method can be removed and
     * call sites can invoke {@link BundleContext#getService} directly.
     * </p>
     *
     * @param <S>
     *        The type of the service.
     * 
     * @param bundleContext
     *        The bundle context; must not be {@code null}.
     * @param serviceReference
     *        The service reference; must not be {@code null}.
     * 
     * @return The service associated with the specified reference or
     *         {@code null} if the service is not available.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the specified service reference was not created by the same
     *         framework instance as the specified bundle context.
     * @throws java.lang.IllegalStateException
     *         If the specified bundle context is no longer valid.
     * @throws java.lang.SecurityException
     *         If the caller does not have the {@code ServicePermission} to get
     *         the service using at least one of the named classes the service
     *         was registered under and the Java Runtime Environment supports
     *         permissions.
     * 
     * @see org.osgi.framework.BundleContext#getService(ServiceReference)
     */
    public static <S> @Nullable S getService(
        final BundleContext bundleContext,
        final ServiceReference<S> serviceReference )
    {
        return bundleContext.getService( serviceReference );
    }
}

/*
 * Services.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Mar 1, 2008 at 11:50:30 PM.
 */

package org.gamegineer.engine.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.common.core.services.component.IComponentFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * Manages the OSGi services used by the bundle.
 * 
 * <p>
 * The {@code close} method should be called before the bundle is stopped.
 * </p>
 */
public final class Services
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance. */
    private static final Services c_instance = new Services();

    /** The EngineFactory registration token. */
    private ServiceRegistration m_engineFactoryRegistration;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Services} class.
     */
    private Services()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Closes the services managed by this object.
     */
    void close()
    {
        // Close bundle-specific services

        // Unregister package-specific services

        // Unregister bundle-specific services
        if( m_engineFactoryRegistration != null )
        {
            m_engineFactoryRegistration.unregister();
            m_engineFactoryRegistration = null;
        }
    }

    /**
     * Gets the default instance of the {@code Services} class.
     * 
     * @return The default instance of the {@code Services} class; never
     *         {@code null}.
     */
    /* @NonNull */
    public static Services getDefault()
    {
        return c_instance;
    }

    /**
     * Opens the services managed by this object.
     * 
     * @param context
     *        The execution context of the bundle; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    void open(
        /* @NonNull */
        final BundleContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        // Register bundle-specific services
        m_engineFactoryRegistration = context.registerService( IComponentFactory.class.getName(), new EngineFactory(), null );

        // Register package-specific services

        // Open bundle-specific services
    }
}
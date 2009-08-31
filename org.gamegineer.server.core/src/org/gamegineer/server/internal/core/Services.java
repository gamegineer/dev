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
 * Created on Sep 13, 2008 at 9:52:48 PM.
 */

package org.gamegineer.server.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import org.gamegineer.common.core.services.component.IComponentFactory;
import org.gamegineer.game.core.services.systemregistry.IGameSystemRegistry;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

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

    /** The GameServerFactory registration token. */
    private ServiceRegistration m_gameServerFactoryRegistration;

    /** The game system registry service tracker. */
    private ServiceTracker m_gameSystemRegistryServiceTracker;


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
        if( m_gameSystemRegistryServiceTracker != null )
        {
            m_gameSystemRegistryServiceTracker.close();
            m_gameSystemRegistryServiceTracker = null;
        }

        // Unregister package-specific services

        // Unregister bundle-specific services
        if( m_gameServerFactoryRegistration != null )
        {
            m_gameServerFactoryRegistration.unregister();
            m_gameServerFactoryRegistration = null;
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
     * Gets the game system registry service managed by this object.
     * 
     * @return The game system registry service managed by this object; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If this object is not open.
     */
    /* @NonNull */
    public IGameSystemRegistry getGameSystemRegistry()
    {
        assertStateLegal( m_gameSystemRegistryServiceTracker != null, Messages.Services_gameSystemRegistryServiceTracker_notSet );

        return (IGameSystemRegistry)m_gameSystemRegistryServiceTracker.getService();
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
        m_gameServerFactoryRegistration = context.registerService( IComponentFactory.class.getName(), new GameServerFactory(), null );

        // Register package-specific services

        // Open bundle-specific services
        m_gameSystemRegistryServiceTracker = new ServiceTracker( context, IGameSystemRegistry.class.getName(), null );
        m_gameSystemRegistryServiceTracker.open();
    }
}
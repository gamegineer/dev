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
 * Created on Feb 14, 2009 at 11:15:43 PM.
 */

package org.gamegineer.game.internal.ui;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.gamegineer.game.internal.ui.services.systemuiregistry.GameSystemUiRegistry;
import org.gamegineer.game.ui.services.systemuiregistry.IGameSystemUiRegistry;
import org.gamegineer.game.ui.system.IGameSystemUi;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.packageadmin.PackageAdmin;
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

    /** The game system user interface registry service registration token. */
    private ServiceRegistration m_gameSystemUiRegistryServiceRegistration;

    /** The game system user interface registry service tracker. */
    private ServiceTracker m_gameSystemUiRegistryServiceTracker;

    /** The game system user interface service tracker. */
    private ServiceTracker m_gameSystemUiServiceTracker;

    /** The package administration service tracker. */
    private ServiceTracker m_packageAdminServiceTracker;


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
        if( m_packageAdminServiceTracker != null )
        {
            m_packageAdminServiceTracker.close();
            m_packageAdminServiceTracker = null;
        }
        if( m_gameSystemUiServiceTracker != null )
        {
            m_gameSystemUiServiceTracker.close();
            m_gameSystemUiServiceTracker = null;
        }
        if( m_gameSystemUiRegistryServiceTracker != null )
        {
            m_gameSystemUiRegistryServiceTracker.close();
            m_gameSystemUiRegistryServiceTracker = null;
        }

        // Unregister package-specific services

        // Unregister bundle-specific services
        if( m_gameSystemUiRegistryServiceRegistration != null )
        {
            m_gameSystemUiRegistryServiceRegistration.unregister();
            m_gameSystemUiRegistryServiceRegistration = null;
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
     * Gets the game system user interface registry service managed by this
     * object.
     * 
     * @return The game system user interface registry service managed by this
     *         object; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If this object is not open.
     */
    /* @NonNull */
    public IGameSystemUiRegistry getGameSystemUiRegistry()
    {
        assertStateLegal( m_gameSystemUiRegistryServiceTracker != null, Messages.Services_gameSystemUiRegistryServiceTracker_notSet );

        return (IGameSystemUiRegistry)m_gameSystemUiRegistryServiceTracker.getService();
    }

    /**
     * Gets an immutable collection of all game system user interfaces
     * registered with the OSGi Framework.
     * 
     * @return An immutable collection of all game system user interfaces
     *         registered with the OSGi Framework; never {@code null}. This
     *         collection is a snapshot of the game system user interfaces
     *         available at the time of the call.
     * 
     * @throws java.lang.IllegalStateException
     *         If this object is not open.
     */
    /* @NonNull */
    public Collection<IGameSystemUi> getGameSystemUis()
    {
        assertStateLegal( m_gameSystemUiServiceTracker != null, Messages.Services_gameSystemUiServiceTracker_notSet );

        final Object[] services = m_gameSystemUiServiceTracker.getServices();
        if( (services == null) || (services.length == 0) )
        {
            return Collections.emptyList();
        }

        final List<IGameSystemUi> gameSystemUis = new ArrayList<IGameSystemUi>( services.length );
        for( final Object service : services )
        {
            gameSystemUis.add( (IGameSystemUi)service );
        }
        return Collections.unmodifiableList( gameSystemUis );
    }

    /**
     * Gets the package administration service managed by this object.
     * 
     * @return The package administration service managed by this object; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If this object is not open.
     */
    /* @NonNull */
    public PackageAdmin getPackageAdministrationService()
    {
        assertStateLegal( m_packageAdminServiceTracker != null, Messages.Services_packageAdminServiceTracker_notSet );

        return (PackageAdmin)m_packageAdminServiceTracker.getService();
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
        m_gameSystemUiRegistryServiceRegistration = context.registerService( IGameSystemUiRegistry.class.getName(), new GameSystemUiRegistry(), null );

        // Register package-specific services

        // Open bundle-specific services
        m_gameSystemUiRegistryServiceTracker = new ServiceTracker( context, m_gameSystemUiRegistryServiceRegistration.getReference(), null );
        m_gameSystemUiRegistryServiceTracker.open();
        m_gameSystemUiServiceTracker = new ServiceTracker( context, IGameSystemUi.class.getName(), null );
        m_gameSystemUiServiceTracker.open();
        m_packageAdminServiceTracker = new ServiceTracker( context, PackageAdmin.class.getName(), null );
        m_packageAdminServiceTracker.open();
    }
}

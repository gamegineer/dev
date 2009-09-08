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
 * Created on Jul 5, 2008 at 9:57:29 PM.
 */

package org.gamegineer.game.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.gamegineer.common.core.services.component.IComponentFactory;
import org.gamegineer.game.core.services.systemregistry.IGameSystemRegistry;
import org.gamegineer.game.core.system.IGameSystem;
import org.gamegineer.game.internal.core.services.systemregistry.GameSystemRegistry;
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
    private static final Services instance_ = new Services();

    /** The GameFactory registration token. */
    private ServiceRegistration gameFactoryRegistration_;

    /** The game system registry service registration token. */
    private ServiceRegistration gameSystemRegistryServiceRegistration_;

    /** The game system registry service tracker. */
    private ServiceTracker gameSystemRegistryServiceTracker_;

    /** The game system service tracker. */
    private ServiceTracker gameSystemServiceTracker_;

    /** The package administration service tracker. */
    private ServiceTracker packageAdminServiceTracker_;


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
        if( packageAdminServiceTracker_ != null )
        {
            packageAdminServiceTracker_.close();
            packageAdminServiceTracker_ = null;
        }
        if( gameSystemServiceTracker_ != null )
        {
            gameSystemServiceTracker_.close();
            gameSystemServiceTracker_ = null;
        }
        if( gameSystemRegistryServiceTracker_ != null )
        {
            gameSystemRegistryServiceTracker_.close();
            gameSystemRegistryServiceTracker_ = null;
        }

        // Unregister package-specific services

        // Unregister bundle-specific services
        if( gameFactoryRegistration_ != null )
        {
            gameFactoryRegistration_.unregister();
            gameFactoryRegistration_ = null;
        }
        if( gameSystemRegistryServiceRegistration_ != null )
        {
            gameSystemRegistryServiceRegistration_.unregister();
            gameSystemRegistryServiceRegistration_ = null;
        }
    }

    /**
     * Gets the default instance of the {@code Services} class.
     * 
     * @return The default instance of the {@code Services} class; never {@code
     *         null}.
     */
    /* @NonNull */
    public static Services getDefault()
    {
        return instance_;
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
        assertStateLegal( gameSystemRegistryServiceTracker_ != null, Messages.Services_gameSystemRegistryServiceTracker_notSet );

        return (IGameSystemRegistry)gameSystemRegistryServiceTracker_.getService();
    }

    /**
     * Gets an immutable collection of all game systems registered with the OSGi
     * Framework.
     * 
     * @return An immutable collection of all game systems registered with the
     *         OSGi Framework; never {@code null}. This collection is a snapshot
     *         of the game systems available at the time of the call.
     * 
     * @throws java.lang.IllegalStateException
     *         If this object is not open.
     */
    /* @NonNull */
    public Collection<IGameSystem> getGameSystems()
    {
        assertStateLegal( gameSystemServiceTracker_ != null, Messages.Services_gameSystemServiceTracker_notSet );

        final Object[] services = gameSystemServiceTracker_.getServices();
        if( (services == null) || (services.length == 0) )
        {
            return Collections.emptyList();
        }

        final List<IGameSystem> gameSystems = new ArrayList<IGameSystem>( services.length );
        for( final Object service : services )
        {
            gameSystems.add( (IGameSystem)service );
        }
        return Collections.unmodifiableList( gameSystems );
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
        assertStateLegal( packageAdminServiceTracker_ != null, Messages.Services_packageAdminServiceTracker_notSet );

        return (PackageAdmin)packageAdminServiceTracker_.getService();
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
        gameFactoryRegistration_ = context.registerService( IComponentFactory.class.getName(), new GameFactory(), null );
        gameSystemRegistryServiceRegistration_ = context.registerService( IGameSystemRegistry.class.getName(), new GameSystemRegistry(), null );

        // Register package-specific services

        // Open bundle-specific services
        gameSystemRegistryServiceTracker_ = new ServiceTracker( context, gameSystemRegistryServiceRegistration_.getReference(), null );
        gameSystemRegistryServiceTracker_.open();
        gameSystemServiceTracker_ = new ServiceTracker( context, IGameSystem.class.getName(), null );
        gameSystemServiceTracker_.open();
        packageAdminServiceTracker_ = new ServiceTracker( context, PackageAdmin.class.getName(), null );
        packageAdminServiceTracker_.open();
    }
}

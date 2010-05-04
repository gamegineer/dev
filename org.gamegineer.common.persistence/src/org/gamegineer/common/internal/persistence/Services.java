/*
 * Services.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Jun 20, 2008 at 9:28:45 PM.
 */

package org.gamegineer.common.internal.persistence;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.IAdapterManager;
import org.gamegineer.common.internal.persistence.schemes.serializable.services.persistencedelegateregistry.PersistenceDelegateRegistry;
import org.gamegineer.common.persistence.schemes.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry;
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
@ThreadSafe
public final class Services
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance. */
    private static final Services instance_ = new Services();

    /** The adapter manager service tracker. */
    private ServiceTracker adapterManagerServiceTracker_;

    /**
     * The Serializable persistence delegate registry service registration
     * token.
     */
    private ServiceRegistration serializablePersistenceDelegateRegistryServiceRegistration_;

    /** The Serializable persistence delegate registry service tracker. */
    private ServiceTracker serializablePersistenceDelegateRegistryServiceTracker_;


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
        // Unregister package-specific adapters
        org.gamegineer.common.internal.persistence.memento.Adapters.getDefault().unregister( getAdapterManager() );

        // Close bundle-specific services
        if( serializablePersistenceDelegateRegistryServiceTracker_ != null )
        {
            serializablePersistenceDelegateRegistryServiceTracker_.close();
            serializablePersistenceDelegateRegistryServiceTracker_ = null;
        }
        if( adapterManagerServiceTracker_ != null )
        {
            adapterManagerServiceTracker_.close();
            adapterManagerServiceTracker_ = null;
        }

        // Unregister package-specific services

        // Unregister bundle-specific services
        if( serializablePersistenceDelegateRegistryServiceRegistration_ != null )
        {
            serializablePersistenceDelegateRegistryServiceRegistration_.unregister();
            serializablePersistenceDelegateRegistryServiceRegistration_ = null;
        }
    }

    /**
     * Gets the adapter manager service managed by this object.
     * 
     * @return The adapter manager service managed by this object; never {@code
     *         null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If this object is not open.
     */
    /* @NonNull */
    public IAdapterManager getAdapterManager()
    {
        assertStateLegal( adapterManagerServiceTracker_ != null, Messages.Services_adapterManagerServiceTracker_notSet );

        return (IAdapterManager)adapterManagerServiceTracker_.getService();
    }

    /**
     * Gets the Serializable persistence delegate registry service managed by
     * this object.
     * 
     * @return The Serializable persistence delegate registry service managed by
     *         this object; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If this object is not open.
     */
    /* @NonNull */
    public IPersistenceDelegateRegistry getSerializablePersistenceDelegateRegistry()
    {
        assertStateLegal( serializablePersistenceDelegateRegistryServiceTracker_ != null, Messages.Services_serializablePersistenceDelegateRegistryServiceTracker_notSet );

        return (IPersistenceDelegateRegistry)serializablePersistenceDelegateRegistryServiceTracker_.getService();
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
        serializablePersistenceDelegateRegistryServiceRegistration_ = context.registerService( IPersistenceDelegateRegistry.class.getName(), new PersistenceDelegateRegistry(), null );

        // Register package-specific services

        // Open bundle-specific services
        adapterManagerServiceTracker_ = new ServiceTracker( context, IAdapterManager.class.getName(), null );
        adapterManagerServiceTracker_.open();
        serializablePersistenceDelegateRegistryServiceTracker_ = new ServiceTracker( context, serializablePersistenceDelegateRegistryServiceRegistration_.getReference(), null );
        serializablePersistenceDelegateRegistryServiceTracker_.open();

        // Register package-specific adapters
        org.gamegineer.common.internal.persistence.memento.Adapters.getDefault().register( getAdapterManager() );
    }
}

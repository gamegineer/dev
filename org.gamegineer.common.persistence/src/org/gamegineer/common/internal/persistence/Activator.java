/*
 * Activator.java
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
 * Created on Jun 20, 2008 at 9:22:30 PM.
 */

package org.gamegineer.common.internal.persistence;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.atomic.AtomicReference;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The bundle activator for the org.gamegineer.common.persistence bundle.
 */
@ThreadSafe
public final class Activator
    implements BundleActivator
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance of the bundle activator. */
    private static final AtomicReference<Activator> instance_ = new AtomicReference<Activator>();

    /** The bundle context. */
    @GuardedBy( "lock_" )
    private BundleContext bundleContext_;

    /** The instance lock. */
    private final Object lock_;

    /** The Serializable persistence delegate registry service tracker. */
    @GuardedBy( "lock_" )
    private ServiceTracker serializablePersistenceDelegateRegistryTracker_;


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
        serializablePersistenceDelegateRegistryTracker_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the bundle context.
     * 
     * @return The bundle context; never {@code null}.
     */
    /* @NonNull */
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
    /* @NonNull */
    public static Activator getDefault()
    {
        final Activator instance = instance_.get();
        assert instance != null;
        return instance;
    }

    /**
     * Gets the Serializable persistence delegate registry service.
     * 
     * @return The Serializable persistence delegate registry service or {@code
     *         null} if no Serializable persistence delegate registry service is
     *         available.
     */
    /* @Nullable */
    public org.gamegineer.common.persistence.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry getSerializablePersistenceDelegateRegistry()
    {
        synchronized( lock_ )
        {
            assert bundleContext_ != null;

            if( serializablePersistenceDelegateRegistryTracker_ == null )
            {
                serializablePersistenceDelegateRegistryTracker_ = new ServiceTracker( bundleContext_, org.gamegineer.common.persistence.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry.class.getName(), null );
                serializablePersistenceDelegateRegistryTracker_.open();
            }

            return (org.gamegineer.common.persistence.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry)serializablePersistenceDelegateRegistryTracker_.getService();
        }
    }

    /*
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(
        final BundleContext bundleContext )
    {
        assertArgumentNotNull( bundleContext, "bundleContext" ); //$NON-NLS-1$

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
        final BundleContext bundleContext )
    {
        assertArgumentNotNull( bundleContext, "bundleContext" ); //$NON-NLS-1$

        final boolean wasInstanceNonNull = instance_.compareAndSet( this, null );
        assert wasInstanceNonNull;

        synchronized( lock_ )
        {
            assert bundleContext_ != null;
            bundleContext_ = null;

            if( serializablePersistenceDelegateRegistryTracker_ != null )
            {
                serializablePersistenceDelegateRegistryTracker_.close();
                serializablePersistenceDelegateRegistryTracker_ = null;
            }
        }
    }
}

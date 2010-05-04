/*
 * Adapters.java
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
 * Created on Jul 1, 2008 at 11:39:26 PM.
 */

package org.gamegineer.common.internal.persistence.memento;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.IAdapterManager;

/**
 * Manages the adapters provided by this package.
 * 
 * <p>
 * The {@code unregister} method should be called before the bundle is stopped.
 * </p>
 */
@ThreadSafe
public final class Adapters
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance. */
    private static final Adapters instance_ = new Adapters();

    /**
     * The adapter factory for the JavaBeans persistence framework persistence
     * delegate.
     */
    private IAdapterFactory beansAdapterFactory_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Adapters} class.
     */
    private Adapters()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the default instance of the {@code Adapters} class.
     * 
     * @return The default instance of the {@code Adapters} class; never {@code
     *         null}.
     */
    /* @NonNull */
    public static Adapters getDefault()
    {
        return instance_;
    }

    /**
     * Registers the adapters managed by this object.
     * 
     * @param manager
     *        The adapter manager; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code manager} is {@code null}.
     */
    public void register(
        /* @NonNull */
        final IAdapterManager manager )
    {
        assertArgumentNotNull( manager, "manager" ); //$NON-NLS-1$

        beansAdapterFactory_ = new org.gamegineer.common.internal.persistence.memento.schemes.beans.MementoPersistenceDelegate.AdapterFactory();
        manager.registerAdapters( beansAdapterFactory_, Memento.class );
    }

    /**
     * Unregisters the adapters managed by this object.
     * 
     * @param manager
     *        The adapter manager; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code manager} is {@code null}.
     */
    public void unregister(
        /* @NonNull */
        final IAdapterManager manager )
    {
        assertArgumentNotNull( manager, "manager" ); //$NON-NLS-1$

        manager.unregisterAdapters( beansAdapterFactory_ );
        beansAdapterFactory_ = null;
    }
}

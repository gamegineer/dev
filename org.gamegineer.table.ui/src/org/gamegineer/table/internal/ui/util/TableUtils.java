/*
 * TableUtils.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Aug 30, 2012 at 9:19:52 PM.
 */

package org.gamegineer.table.internal.ui.util;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.List;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.IContainerListener;

/**
 * A collection of useful methods for working with tables and table components.
 */
@ThreadSafe
public final class TableUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableUtils} class.
     */
    private TableUtils()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Atomically adds the specified listener to the specified container and
     * returns its child components.
     * 
     * @param container
     *        The container; must not be {@code null}.
     * @param containerListener
     *        The container listener; must not be {@code null}.
     * 
     * @return The collection of child components of the specified container;
     *         never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code containerListener} is already a registered listener of
     *         {@code container}.
     * @throws java.lang.NullPointerException
     *         If {@code container} or {@code containerListener} is {@code null}
     *         .
     */
    /* @NonNull */
    public static List<IComponent> addContainerListenerAndGetComponents(
        /* @NonNull */
        final IContainer container,
        /* @NonNull */
        final IContainerListener containerListener )
    {
        assertArgumentNotNull( container, "container" ); //$NON-NLS-1$
        assertArgumentNotNull( containerListener, "containerListener" ); //$NON-NLS-1$

        container.getTableEnvironment().getLock().lock();
        try
        {
            container.addContainerListener( containerListener );
            return container.getComponents();
        }
        finally
        {
            container.getTableEnvironment().getLock().unlock();
        }
    }
}

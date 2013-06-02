/*
 * TableModelUtils.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Sep 14, 2012 at 11:02:08 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.List;
import net.jcip.annotations.ThreadSafe;

/**
 * A collection of useful methods for working with table models and component
 * models.
 */
@ThreadSafe
public final class TableModelUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableModelUtils} class.
     */
    private TableModelUtils()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Atomically adds the specified listener to the specified container model
     * and returns its child component models.
     * 
     * @param containerModel
     *        The container model; must not be {@code null}.
     * @param containerModelListener
     *        The container model listener; must not be {@code null}.
     * 
     * @return The collection of child component models of the specified
     *         container model; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code containerModelListener} is already a registered
     *         listener of {@code containerModel}.
     * @throws java.lang.NullPointerException
     *         If {@code containerModel} or {@code containerModelListener} is
     *         {@code null}.
     */
    /* @NonNull */
    public static List<ComponentModel> addContainerModelListenerAndGetComponentModels(
        /* @NonNull */
        final ContainerModel containerModel,
        /* @NonNull */
        final IContainerModelListener containerModelListener )
    {
        assertArgumentNotNull( containerModel, "containerModel" ); //$NON-NLS-1$
        assertArgumentNotNull( containerModelListener, "containerModelListener" ); //$NON-NLS-1$

        containerModel.getLock().lock();
        try
        {
            containerModel.addContainerModelListener( containerModelListener );
            return containerModel.getComponentModels();
        }
        finally
        {
            containerModel.getLock().unlock();
        }
    }
}

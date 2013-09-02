/*
 * ContainerLayoutRegistry.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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
 * Created on Aug 11, 2012 at 9:09:39 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.core.Activator;

/**
 * A facade for accessing the container layout registry.
 */
@ThreadSafe
public final class ContainerLayoutRegistry
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerLayoutRegistry} class.
     */
    private ContainerLayoutRegistry()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the container layout with the specified identifier.
     * 
     * @param id
     *        The container layout identifier; must not be {@code null}.
     * 
     * @return The container layout with the specified identifier; never
     *         {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     * @throws org.gamegineer.table.core.NoSuchContainerLayoutException
     *         If {@code id} is not registered.
     */
    /* @NonNull */
    public static IContainerLayout getContainerLayout(
        /* @NonNull */
        final ContainerLayoutId id )
        throws NoSuchContainerLayoutException
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        final IContainerLayoutRegistry containerLayoutRegistry = Activator.getDefault().getContainerLayoutRegistry();
        if( containerLayoutRegistry == null )
        {
            throw new NoSuchContainerLayoutException( NonNlsMessages.ContainerLayoutRegistry_getContainerLayout_containerLayoutRegistryNotAvailable );
        }

        final IContainerLayout containerLayout = containerLayoutRegistry.getObject( id );
        if( containerLayout == null )
        {
            throw new NoSuchContainerLayoutException( NonNlsMessages.ContainerLayoutRegistry_getContainerLayout_unknownContainerLayoutId( id ) );
        }

        return containerLayout;
    }
}

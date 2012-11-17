/*
 * ComponentStrategyUIRegistry.java
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
 * Created on Sep 29, 2012 at 9:30:47 PM.
 */

package org.gamegineer.table.ui;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.internal.ui.Activator;

/**
 * A facade for accessing the component strategy user interface registry.
 */
@ThreadSafe
public final class ComponentStrategyUIRegistry
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentStrategyUIRegistry}
     * class.
     */
    private ComponentStrategyUIRegistry()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the component strategy user interface with the specified identifier.
     * 
     * @param id
     *        The component strategy identifier; must not be {@code null}.
     * 
     * @return The component strategy user interface with the specified
     *         identifier; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     * @throws org.gamegineer.table.ui.NoSuchComponentStrategyUIException
     *         If {@code id} is not registered.
     */
    /* @NonNull */
    public static IComponentStrategyUI getComponentStrategyUI(
        /* @NonNull */
        final ComponentStrategyId id )
        throws NoSuchComponentStrategyUIException
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        final IComponentStrategyUIRegistry componentStrategyUIRegistry = Activator.getDefault().getComponentStrategyUIRegistry();
        if( componentStrategyUIRegistry == null )
        {
            throw new NoSuchComponentStrategyUIException( NonNlsMessages.ComponentStrategyUIRegistry_getComponentStrategyUI_componentStrategyUIRegistryNotAvailable );
        }

        final IComponentStrategyUI componentStrategyUI = componentStrategyUIRegistry.get( id );
        if( componentStrategyUI == null )
        {
            throw new NoSuchComponentStrategyUIException( NonNlsMessages.ComponentStrategyUIRegistry_getComponentStrategyUI_unknownComponentStrategyId( id ) );
        }

        return componentStrategyUI;
    }
}

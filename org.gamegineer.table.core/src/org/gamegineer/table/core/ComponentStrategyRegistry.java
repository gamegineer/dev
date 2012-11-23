/*
 * ComponentStrategyRegistry.java
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
 * Created on Aug 11, 2012 at 10:07:14 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.core.Activator;

/**
 * A facade for accessing the component strategy registry.
 */
@ThreadSafe
public final class ComponentStrategyRegistry
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentStrategyRegistry}
     * class.
     */
    private ComponentStrategyRegistry()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the component strategy with the specified identifier.
     * 
     * @param id
     *        The component strategy identifier; must not be {@code null}.
     * 
     * @return The component strategy with the specified identifier; never
     *         {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     * @throws org.gamegineer.table.core.NoSuchComponentStrategyException
     *         If {@code id} is not registered.
     */
    /* @NonNull */
    public static IComponentStrategy getComponentStrategy(
        /* @NonNull */
        final ComponentStrategyId id )
        throws NoSuchComponentStrategyException
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        final IComponentStrategyRegistry componentStrategyRegistry = Activator.getDefault().getComponentStrategyRegistry();
        if( componentStrategyRegistry == null )
        {
            throw new NoSuchComponentStrategyException( NonNlsMessages.ComponentStrategyRegistry_getComponentStrategy_componentStrategyRegistryNotAvailable );
        }

        final IComponentStrategy componentStrategy = componentStrategyRegistry.getObject( id );
        if( componentStrategy == null )
        {
            throw new NoSuchComponentStrategyException( NonNlsMessages.ComponentStrategyRegistry_getComponentStrategy_unknownComponentStrategyId( id ) );
        }

        return componentStrategy;
    }

    /**
     * Gets the container strategy with the specified identifier.
     * 
     * @param id
     *        The container strategy identifier; must not be {@code null}.
     * 
     * @return The container strategy with the specified identifier; never
     *         {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     * @throws org.gamegineer.table.core.NoSuchComponentStrategyException
     *         If {@code id} is not registered or is not a container strategy.
     */
    /* @NonNull */
    public static IContainerStrategy getContainerStrategy(
        /* @NonNull */
        final ComponentStrategyId id )
        throws NoSuchComponentStrategyException
    {
        final IComponentStrategy componentStrategy = getComponentStrategy( id );
        if( componentStrategy instanceof IContainerStrategy )
        {
            return (IContainerStrategy)componentStrategy;
        }

        throw new NoSuchComponentStrategyException( NonNlsMessages.ComponentStrategyRegistry_getContainerStrategy_notContainerStrategy( id ) );
    }
}

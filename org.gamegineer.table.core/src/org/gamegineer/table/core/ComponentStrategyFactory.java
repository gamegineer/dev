/*
 * ComponentStrategyFactory.java
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
 * Created on Aug 1, 2012 at 10:45:21 PM.
 */

package org.gamegineer.table.core;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.core.NullComponentStrategy;
import org.gamegineer.table.internal.core.NullContainerStrategy;

/**
 * A factory for creating common component strategies.
 */
@ThreadSafe
public final class ComponentStrategyFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentStrategyFactory} class.
     */
    private ComponentStrategyFactory()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a null component strategy.
     * 
     * @return A null component strategy; never {@code null}.
     */
    /* @NonNull */
    public static IComponentStrategy createNullComponentStrategy()
    {
        return NullComponentStrategy.INSTANCE;
    }

    /**
     * Creates a null container strategy.
     * 
     * @return A null container strategy; never {@code null}.
     */
    /* @NonNull */
    public static IContainerStrategy createNullContainerStrategy()
    {
        return NullContainerStrategy.INSTANCE;
    }
}

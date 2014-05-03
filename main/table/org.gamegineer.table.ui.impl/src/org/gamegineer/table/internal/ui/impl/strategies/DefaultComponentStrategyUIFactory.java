/*
 * DefaultComponentStrategyUIFactory.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Sep 29, 2012 at 10:00:28 PM.
 */

package org.gamegineer.table.internal.ui.impl.strategies;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.IComponentStrategy;
import org.gamegineer.table.core.IContainerStrategy;
import org.gamegineer.table.ui.IComponentStrategyUI;

/**
 * A factory for creating default component strategy user interfaces.
 */
@ThreadSafe
public final class DefaultComponentStrategyUIFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code DefaultComponentStrategyUIFactory} class.
     */
    private DefaultComponentStrategyUIFactory()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a default component strategy user interface for the specified
     * component strategy.
     * 
     * @param componentStrategy
     *        The component strategy; must not be {@code null}.
     * 
     * @return A default component strategy user interface for the specified
     *         component strategy; never {@code null}.
     */
    public static IComponentStrategyUI createDefaultComponentStrategyUI(
        final IComponentStrategy componentStrategy )
    {
        return (componentStrategy instanceof IContainerStrategy) //
            ? new DefaultContainerStrategyUI( componentStrategy.getId() ) //
            : new DefaultComponentStrategyUI( componentStrategy.getId() );
    }
}

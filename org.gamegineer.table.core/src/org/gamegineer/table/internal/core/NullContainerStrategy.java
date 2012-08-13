/*
 * NullContainerStrategy.java
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
 * Created on Aug 1, 2012 at 10:47:51 PM.
 */

package org.gamegineer.table.internal.core;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.IContainerLayout;
import org.gamegineer.table.core.IContainerStrategy;

/**
 * A null container strategy.
 */
@Immutable
public final class NullContainerStrategy
    extends NullComponentStrategy
    implements IContainerStrategy
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The strategy identifier. */
    private static final ComponentStrategyId ID = ComponentStrategyId.fromString( "org.gamegineer.componentStrategies.nullContainer" ); //$NON-NLS-1$

    /** The singleton instance of this class. */
    @SuppressWarnings( "hiding" )
    public static final NullContainerStrategy INSTANCE = new NullContainerStrategy();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NullContainerStrategy} class.
     */
    private NullContainerStrategy()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.IContainerStrategy#getDefaultLayout()
     */
    @Override
    public IContainerLayout getDefaultLayout()
    {
        return ContainerLayouts.ABSOLUTE;
    }

    /*
     * @see org.gamegineer.table.internal.core.NullComponentStrategy#getId()
     */
    @Override
    public ComponentStrategyId getId()
    {
        return ID;
    }

    /*
     * @see org.gamegineer.table.internal.core.NullComponentStrategy#isFocusable()
     */
    @Override
    public boolean isFocusable()
    {
        return true;
    }
}

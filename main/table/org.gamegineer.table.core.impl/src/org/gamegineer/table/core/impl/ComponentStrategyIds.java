/*
 * ComponentStrategyIds.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Sep 28, 2012 at 10:50:19 PM.
 */

package org.gamegineer.table.core.impl;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ComponentStrategyId;

/**
 * A collection of common component strategy identifiers used by the table
 * implementation.
 */
@ThreadSafe
public final class ComponentStrategyIds
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The tabletop component strategy identifier. */
    public static final ComponentStrategyId TABLETOP = ComponentStrategyId.fromString( "org.gamegineer.table.componentStrategies.tabletop" ); //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentStrategyIds} class.
     */
    private ComponentStrategyIds()
    {
    }
}

/*
 * ComponentStrategies.java
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
 * Created on Aug 13, 2012 at 7:53:44 PM.
 */

package org.gamegineer.table.internal.core;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.IComponentStrategy;
import org.gamegineer.table.core.IContainerStrategy;

/**
 * A collection of common component strategies.
 */
@ThreadSafe
public final class ComponentStrategies
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card component strategy. */
    public static final IComponentStrategy CARD = new CardStrategy();

    /** The card pile container strategy. */
    public static final IContainerStrategy CARD_PILE = new CardPileStrategy();

    /** The null component strategy. */
    public static final IComponentStrategy NULL_COMPONENT = new NullComponentStrategy();

    /** The null container strategy. */
    public static final IContainerStrategy NULL_CONTAINER = new NullContainerStrategy();

    /** The tabletop container strategy. */
    public static final IContainerStrategy TABLETOP = new TabletopStrategy();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentStrategies} class.
     */
    private ComponentStrategies()
    {
    }
}

/*
 * InternalComponentStrategies.java
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
 * Created on Aug 13, 2012 at 7:53:44 PM.
 */

package org.gamegineer.cards.internal.core.impl.strategies;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.IComponentStrategy;
import org.gamegineer.table.core.IContainerStrategy;

/**
 * A collection of component strategies provided by this bundle.
 */
@ThreadSafe
public final class InternalComponentStrategies
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card component strategy. */
    public static final IComponentStrategy CARD = new CardStrategy();

    /** The card pile container strategy. */
    public static final IContainerStrategy CARD_PILE = new CardPileStrategy();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code InternalComponentStrategies}
     * class.
     */
    private InternalComponentStrategies()
    {
    }
}

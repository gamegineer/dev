/*
 * AbstractContainerStrategy.java
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
 * Created on Aug 21, 2012 at 8:42:12 PM.
 */

package org.gamegineer.cards.internal.core.strategies;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.IContainerStrategy;

/**
 * Superclass for all container strategies.
 */
@Immutable
abstract class AbstractContainerStrategy
    extends AbstractComponentStrategy
    implements IContainerStrategy
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractContainerStrategy}
     * class.
     * 
     * @param id
     *        The component strategy identifier; must not be {@code null}.
     */
    AbstractContainerStrategy(
        /* @NonNull */
        final ComponentStrategyId id )
    {
        super( id );
    }
}

/*
 * NullContainerStrategyUI.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Mar 21, 2013 at 11:42:54 PM.
 */

package org.gamegineer.table.internal.ui.strategies;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.ComponentStrategies;
import org.gamegineer.table.ui.AbstractContainerStrategyUI;

/**
 * A null container strategy user interface.
 */
@Immutable
final class NullContainerStrategyUI
    extends AbstractContainerStrategyUI
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NullContainerStrategyUI} class.
     */
    NullContainerStrategyUI()
    {
        super( ComponentStrategies.NULL_CONTAINER.getId() );
    }
}

/*
 * DefaultComponentStrategyUI.java
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
 * Created on Sep 29, 2012 at 9:56:17 PM.
 */

package org.gamegineer.table.internal.ui.impl.strategies;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.ui.AbstractComponentStrategyUI;

/**
 * A default component strategy user interface.
 */
@Immutable
final class DefaultComponentStrategyUI
    extends AbstractComponentStrategyUI
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractComponentStrategyUI}
     * class.
     * 
     * @param id
     *        The component strategy identifier; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    DefaultComponentStrategyUI(
        /* @NonNull */
        final ComponentStrategyId id )
    {
        super( id );
    }
}

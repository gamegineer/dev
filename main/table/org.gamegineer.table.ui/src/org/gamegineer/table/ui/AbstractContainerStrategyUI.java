/*
 * AbstractContainerStrategyUI.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Sep 28, 2012 at 10:29:14 PM.
 */

package org.gamegineer.table.ui;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.ComponentStrategyId;

/**
 * Superclass for all container strategy user interfaces.
 */
@Immutable
public abstract class AbstractContainerStrategyUI
    extends AbstractComponentStrategyUI
    implements IContainerStrategyUI
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractContainerStrategyUI}
     * class.
     * 
     * @param id
     *        The component strategy identifier.
     */
    protected AbstractContainerStrategyUI(
        final ComponentStrategyId id )
    {
        super( id );
    }
}

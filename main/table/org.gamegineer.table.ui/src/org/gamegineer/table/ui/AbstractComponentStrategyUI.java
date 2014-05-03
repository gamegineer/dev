/*
 * AbstractComponentStrategyUI.java
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
 * Created on Sep 28, 2012 at 10:29:06 PM.
 */

package org.gamegineer.table.ui;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.ComponentStrategyId;

/**
 * Superclass for all component strategy user interfaces.
 */
@Immutable
public abstract class AbstractComponentStrategyUI
    implements IComponentStrategyUI
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component strategy identifier. */
    private final ComponentStrategyId id_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractComponentStrategyUI}
     * class.
     * 
     * @param id
     *        The component strategy identifier; must not be {@code null}.
     */
    protected AbstractComponentStrategyUI(
        final ComponentStrategyId id )
    {
        id_ = id;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.IComponentStrategyUI#getId()
     */
    @Override
    public final ComponentStrategyId getId()
    {
        return id_;
    }

    /**
     * This implementation returns {@code true}. Subclasses may override and are
     * not required to call the superclass implementation.
     * 
     * @see org.gamegineer.table.ui.IComponentStrategyUI#isFocusable()
     */
    @Override
    public boolean isFocusable()
    {
        return true;
    }
}

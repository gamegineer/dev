/*
 * IActionEnabledPredicate.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Oct 20, 2009 at 11:02:20 PM.
 */

package org.gamegineer.table.internal.ui.action;

import javax.swing.Action;

/**
 * A predicate used to determine if an action is enabled or disabled.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IActionEnabledPredicate
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Evaluates the enabled state of the specified action.
     * 
     * @param action
     *        The action; must not be changed and must not be {@code null}.
     * 
     * @return {@code true} if the action is enabled; {@code false} if the
     *         action is disabled.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code action} is {@code null}.
     */
    public boolean isActionEnabled(
        /* @NonNull */
        Action action );
}

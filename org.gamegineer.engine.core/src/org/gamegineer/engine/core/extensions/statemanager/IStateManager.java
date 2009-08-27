/*
 * IStateManager.java
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
 * Created on Jul 2, 2008 at 11:10:15 PM.
 */

package org.gamegineer.engine.core.extensions.statemanager;

import org.gamegineer.common.persistence.memento.IMemento;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.IEngineContext;

/**
 * An engine extension that manages the engine state.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IStateManager
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets a memento that represents the engine state.
     * 
     * @param context
     *        The context within which the extension is executed; must not be
     *        {@code null}.
     * 
     * @return A memento that represents the engine state; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    /* @NonNull */
    public IMemento getMemento(
        /* @NonNull */
        IEngineContext context );

    /**
     * Sets the engine state using the specified memento.
     * 
     * @param context
     *        The context within which the extension is executed; must not be
     *        {@code null}.
     * @param memento
     *        The memento; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} or {@code memento} is {@code null}.
     * @throws org.gamegineer.engine.core.EngineException
     *         If the memento does not represent a valid engine state.
     */
    public void setMemento(
        /* @NonNull */
        IEngineContext context,
        /* @NonNull */
        IMemento memento )
        throws EngineException;
}

/*
 * IStateChangeEvent.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Jun 2, 2008 at 8:44:12 PM.
 */

package org.gamegineer.engine.core.extensions.stateeventmediator;

import java.util.Collection;
import org.gamegineer.engine.core.AttributeName;

/**
 * The interface that defines the behavior for all event objects used to notify
 * listeners that the engine state is about to change or has been changed.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IStateChangeEvent
    extends IStateEvent
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Indicates this event contains a change record for the specified state
     * attribute.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * 
     * @return {@code true} if this event contains a change record for the
     *         specified state attribute; otherwise {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    public boolean containsAttributeChange(
        /* @NonNull */
        AttributeName name );

    /**
     * Gets the change record for the specified state attribute.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * 
     * @return The change record for the specified state attribute; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If change record for the specified state attribute does not
     *         exist.
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    /* @NonNull */
    public IAttributeChange getAttributeChange(
        /* @NonNull */
        AttributeName name );

    /**
     * Gets an immutable collection of all state attribute changes.
     * 
     * @return An immutable collection of all state attribute changes; never
     *         {@code null}.
     */
    /* @NonNull */
    public Collection<IAttributeChange> getAttributeChanges();
}

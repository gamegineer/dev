/*
 * IAttributeChange.java
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
 * Created on Jun 14, 2008 at 11:00:40 AM.
 */

package org.gamegineer.engine.core.extensions.stateeventmediator;

import org.gamegineer.engine.core.AttributeName;

/**
 * Describes an attribute change.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IAttributeChange
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the attribute name.
     * 
     * @return The attribute name; never {@code null}.
     */
    /* @NonNull */
    public AttributeName getName();

    /**
     * Gets the new attribute value.
     * 
     * @return The new attribute value; may be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the attribute was removed.
     */
    /* @Nullable */
    public Object getNewValue();

    /**
     * Gets the old attribute value.
     * 
     * @return The old attribute value; may be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the attribute was added.
     */
    /* @Nullable */
    public Object getOldValue();

    /**
     * Indicates the attribute has a new value.
     * 
     * @return {@code true} if the attribute has a new value; {@code false} if
     *         the attribute was removed.
     */
    public boolean hasNewValue();

    /**
     * Indicates the attribute has an old value.
     * 
     * @return {@code true} if the attribute has an old value; {@code false} if
     *         the attribute was added.
     */
    public boolean hasOldValue();
}

/*
 * ICardListener.java
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
 * Created on Oct 24, 2009 at 9:11:23 PM.
 */

package org.gamegineer.table.core;

import java.util.EventListener;

/**
 * The listener interface for use by clients to be notified of changes to the
 * card state.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ICardListener
    extends EventListener
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked after the card has been flipped.
     * 
     * @param event
     *        The event describing the card; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void cardFlipped(
        /* @NonNull */
        CardEvent event );

    /**
     * Invoked after the card location has changed.
     * 
     * @param event
     *        The event describing the card; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void cardLocationChanged(
        /* @NonNull */
        CardEvent event );
}

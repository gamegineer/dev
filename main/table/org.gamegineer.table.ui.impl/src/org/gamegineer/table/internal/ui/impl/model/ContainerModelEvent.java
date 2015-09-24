/*
 * ContainerModelEvent.java
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
 * Created on Jan 26, 2010 at 10:42:41 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import java.util.EventObject;
import net.jcip.annotations.ThreadSafe;

/**
 * An event fired by a container model.
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
@ThreadSafe
public class ContainerModelEvent
    extends EventObject
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -2912693789234160511L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerModelEvent} class.
     * 
     * @param source
     *        The container model that fired the event.
     */
    public ContainerModelEvent(
        final ContainerModel source )
    {
        super( source );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the container model that fired the event.
     * 
     * @return The container model that fired the event.
     */
    public final ContainerModel getContainerModel()
    {
        return (ContainerModel)getSource();
    }
}

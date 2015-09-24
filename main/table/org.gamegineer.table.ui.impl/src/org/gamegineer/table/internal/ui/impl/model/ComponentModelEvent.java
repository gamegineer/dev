/*
 * ComponentModelEvent.java
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
 * Created on Dec 25, 2009 at 9:46:35 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import java.util.EventObject;
import net.jcip.annotations.ThreadSafe;

/**
 * An event fired by a component model.
 */
@ThreadSafe
public final class ComponentModelEvent
    extends EventObject
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 1754225832033072604L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentModelEvent} class.
     * 
     * @param source
     *        The component model that fired the event.
     */
    public ComponentModelEvent(
        final ComponentModel source )
    {
        super( source );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the component model that fired the event.
     * 
     * @return The component model that fired the event.
     */
    public ComponentModel getComponentModel()
    {
        return (ComponentModel)getSource();
    }
}

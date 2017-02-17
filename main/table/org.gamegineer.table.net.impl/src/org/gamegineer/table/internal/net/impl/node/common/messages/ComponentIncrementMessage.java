/*
 * ComponentIncrementMessage.java
 * Copyright 2008-2017 Gamegineer contributors and others.
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
 * Created on Jun 30, 2011 at 10:29:37 PM.
 */

package org.gamegineer.table.internal.net.impl.node.common.messages;

import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.internal.net.impl.node.ComponentIncrement;
import org.gamegineer.table.internal.net.impl.transport.AbstractMessage;

/**
 * A message sent by a node to increment the state of a component.
 */
@NotThreadSafe
public final class ComponentIncrementMessage
    extends AbstractMessage
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -3587077290756850335L;

    /**
     * The incremental change to the state of the component.
     * 
     * @serial The incremental change to the state of the component.
     */
    private ComponentIncrement increment_;

    /**
     * The component path.
     * 
     * @serial The component path.
     */
    private ComponentPath path_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentIncrementMessage}
     * class.
     */
    public ComponentIncrementMessage()
    {
        increment_ = new ComponentIncrement();
        path_ = ComponentPath.ROOT;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the incremental change to the state of the component.
     * 
     * @return The incremental change to the state of the component.
     */
    public ComponentIncrement getIncrement()
    {
        return increment_;
    }

    /**
     * Gets the component path.
     * 
     * @return The component path.
     */
    public ComponentPath getPath()
    {
        return path_;
    }

    /**
     * Sets the incremental change to the state of the component.
     * 
     * @param increment
     *        The incremental change to the state of the component.
     */
    public void setIncrement(
        final ComponentIncrement increment )
    {
        increment_ = increment;
    }

    /**
     * Sets the component path.
     * 
     * @param path
     *        The component path.
     */
    public void setPath(
        final ComponentPath path )
    {
        path_ = path;
    }
}

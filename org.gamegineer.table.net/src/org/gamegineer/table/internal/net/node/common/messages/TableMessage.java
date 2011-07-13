/*
 * TableMessage.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Jun 16, 2011 at 10:28:50 PM.
 */

package org.gamegineer.table.internal.net.node.common.messages;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.net.transport.AbstractMessage;

/**
 * A message sent by a node to set the state of the table.
 */
@NotThreadSafe
public final class TableMessage
    extends AbstractMessage
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -9101815563823607975L;

    /**
     * The table memento.
     * 
     * @serial The table memento.
     */
    private Object memento_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableMessage} class.
     */
    public TableMessage()
    {
        memento_ = new Object();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the table memento.
     * 
     * @return The table memento; never {@code null}.
     */
    /* @NonNull */
    public Object getMemento()
    {
        return memento_;
    }

    /**
     * Sets the table memento.
     * 
     * @param memento
     *        The table memento; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code memento} is {@code null}.
     */
    public void setMemento(
        /* @NonNull */
        final Object memento )
    {
        assertArgumentNotNull( memento, "memento" ); //$NON-NLS-1$

        memento_ = memento;
    }
}

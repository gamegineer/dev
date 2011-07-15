/*
 * TableIncrementMessage.java
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
 * Created on Jul 12, 2011 at 8:42:39 PM.
 */

package org.gamegineer.table.internal.net.node.common.messages;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.net.node.TableIncrement;
import org.gamegineer.table.internal.net.transport.AbstractMessage;

/**
 * A message sent by a node to increment the state of the table.
 */
@NotThreadSafe
public final class TableIncrementMessage
    extends AbstractMessage
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -6365983508712683196L;

    /**
     * The incremental change to the state of the table.
     * 
     * @serial The incremental change to the state of the table.
     */
    private TableIncrement increment_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableIncrementMessage} class.
     */
    public TableIncrementMessage()
    {
        increment_ = new TableIncrement();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the incremental change to the state of the table.
     * 
     * @return The incremental change to the state of the table; never {@code
     *         null}.
     */
    /* @NonNull */
    public TableIncrement getIncrement()
    {
        return increment_;
    }

    /**
     * Sets the incremental change to the state of the table.
     * 
     * @param increment
     *        The incremental change to the state of the table; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code increment} is {@code null}.
     */
    public void setIncrement(
        /* @NonNull */
        final TableIncrement increment )
    {
        assertArgumentNotNull( increment, "increment" ); //$NON-NLS-1$

        increment_ = increment;
    }
}

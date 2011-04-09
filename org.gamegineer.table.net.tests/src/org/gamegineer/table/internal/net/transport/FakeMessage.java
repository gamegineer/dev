/*
 * FakeMessage.java
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
 * Created on Mar 5, 2011 at 10:19:16 PM.
 */

package org.gamegineer.table.internal.net.transport;

import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.net.transport.messages.AbstractMessage;

/**
 * A fake network table message.
 */
@NotThreadSafe
public final class FakeMessage
    extends AbstractMessage
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 4942004570714420954L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeMessage} class.
     */
    public FakeMessage()
    {
        super( 0xFFFFFFFF );
    }
}

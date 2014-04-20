/*
 * EchoRequestMessage.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Mar 3, 2011 at 10:43:25 PM.
 */

package org.gamegineer.table.internal.net.impl.node.common.messages;

import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.net.impl.transport.AbstractMessage;

/**
 * A request to echo the message content.
 */
@NotThreadSafe
public final class EchoRequestMessage
    extends AbstractMessage
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -720159066415394954L;

    /**
     * The message content.
     * 
     * @serial The message content.
     */
    private String content_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code EchoRequestMessage} class.
     */
    public EchoRequestMessage()
    {
        content_ = ""; //$NON-NLS-1$
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the message content.
     * 
     * @return The message content; never {@code null}.
     */
    public String getContent()
    {
        return content_;
    }

    /**
     * Sets the message content.
     * 
     * @param content
     *        The message content; must not be {@code null}.
     */
    public void setContent(
        final String content )
    {
        content_ = content;
    }
}

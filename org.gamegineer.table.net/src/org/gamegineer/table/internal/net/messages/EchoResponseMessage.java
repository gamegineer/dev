/*
 * EchoResponseMessage.java
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
 * Created on Mar 3, 2011 at 10:43:31 PM.
 */

package org.gamegineer.table.internal.net.messages;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.net.AbstractMessage;

/**
 * A response to an echo request.
 */
@NotThreadSafe
public final class EchoResponseMessage
    extends AbstractMessage
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 4079243186486929978L;

    /** The message identifier. */
    public static final int ID = 0x00000005;

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
     * Initializes a new instance of the {@code EchoResponseMessage} class.
     */
    public EchoResponseMessage()
    {
        super( ID );

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
    /* @NonNull */
    public String getContent()
    {
        return content_;
    }

    /**
     * Sets the message content.
     * 
     * @param content
     *        The message content; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code content} is {@code null}.
     */
    public void setContent(
        /* @NonNull */
        final String content )
    {
        assertArgumentNotNull( content, "content" ); //$NON-NLS-1$

        content_ = content;
    }
}

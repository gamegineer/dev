/*
 * DialogMessage.java
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
 * Created on Sep 23, 2010 at 10:57:01 PM.
 */

package org.gamegineer.common.ui.dialog;

import net.jcip.annotations.Immutable;

/**
 * A dialog message.
 */
@Immutable
public final class DialogMessage
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The message text. */
    private final String text_;

    /** The message type. */
    private final DialogMessageType type_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DialogMessage} class.
     * 
     * @param text
     *        The message text; must not be {@code null}.
     * @param type
     *        The message type; must not be {@code null}.
     */
    public DialogMessage(
        final String text,
        final DialogMessageType type )
    {
        text_ = text;
        type_ = type;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the message text.
     * 
     * @return The message text; never {@code null}.
     */
    public String getText()
    {
        return text_;
    }

    /**
     * Gets the message type.
     * 
     * @return The message type; never {@code null}.
     */
    public DialogMessageType getType()
    {
        return type_;
    }
}

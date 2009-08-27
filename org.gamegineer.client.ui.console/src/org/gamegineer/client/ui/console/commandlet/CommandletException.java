/*
 * CommandletException.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Oct 3, 2008 at 8:43:28 PM.
 */

package org.gamegineer.client.ui.console.commandlet;

/**
 * A checked exception that indicates an error occurred during the execution of
 * a commandlet.
 * 
 * <p>
 * Exceptions of this type support a localized detail message that is intended
 * to be displayed on the console when a commandlet error occurs. The
 * non-localized message is intended for a logging system or other support
 * facility.
 * </p>
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 */
public class CommandletException
    extends Exception
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -2453552143168627812L;

    /** The localized detail message. */
    private final String m_localizedMessage;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandletException} class with
     * no detail message, no localized detail message, and no cause.
     */
    public CommandletException()
    {
        this( null, null, null );
    }

    /**
     * Initializes a new instance of the {@code CommandletException} class with
     * the specified detail message, the specified localized detail message, and
     * no cause.
     * 
     * @param message
     *        The detail message; may be {@code null}.
     * @param localizedMessage
     *        The localized detail message; may be {@code null}.
     */
    public CommandletException(
        /* @Nullable */
        final String message,
        /* @Nullable */
        final String localizedMessage )
    {
        this( message, localizedMessage, null );
    }

    /**
     * Initializes a new instance of the {@code CommandletException} class with
     * no detail message, no localized detail message, and the specified cause.
     * 
     * @param cause
     *        The cause; may be {@code null}.
     */
    public CommandletException(
        /* @Nullable */
        final Throwable cause )
    {
        this( null, null, cause );
    }

    /**
     * Initializes a new instance of the {@code CommandletException} class with
     * the specified detail message, localized detail message, and cause.
     * 
     * @param message
     *        The detail message; may be {@code null}.
     * @param localizedMessage
     *        The localized detail message; may be {@code null}.
     * @param cause
     *        The cause; may be {@code null}.
     */
    public CommandletException(
        /* @Nullable */
        final String message,
        /* @Nullable */
        final String localizedMessage,
        /* @Nullable */
        final Throwable cause )
    {
        super( message, cause );

        m_localizedMessage = localizedMessage;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.lang.Throwable#getLocalizedMessage()
     */
    @Override
    public String getLocalizedMessage()
    {
        return m_localizedMessage;
    }
}

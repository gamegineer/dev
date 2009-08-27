/*
 * Messages.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Jun 9, 2008 at 10:58:27 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandeventmediator;

import org.eclipse.osgi.util.NLS;

/**
 * A utility class to manage localized messages for the package.
 */
final class Messages
    extends NLS
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the associated resource bundle. */
    private static final String BUNDLE_NAME = "org.gamegineer.engine.internal.core.extensions.commandeventmediator.Messages"; //$NON-NLS-1$

    // --- CommandEventMediatorExtension ------------------------------------

    /** The active command listener collection is not available. */
    public static String CommandEventMediatorExtension_activeCommandListeners_unavailable;

    /** Unexpected exception thrown from ICommandListener.commandExecuted(). */
    public static String CommandEventMediatorExtension_commandExecuted_unexpectedException;

    /** Unexpected exception thrown from ICommandListener.commandExecuting(). */
    public static String CommandEventMediatorExtension_commandExecuting_unexpectedException;

    /** The command listener is already registered. */
    public static String CommandEventMediatorExtension_listener_registered;

    /** The command listener has not been registered. */
    public static String CommandEventMediatorExtension_listener_unregistered;

    // --- CommandExecutedEventDelegate -------------------------------------

    /** No exception is available. */
    public static String CommandExecutedEventDelegate_getException_noException;

    /** No result is available. */
    public static String CommandExecutedEventDelegate_getResult_noResult;

    // --- Common -----------------------------------------------------------

    /** The command event mediator extension is not available. */
    public static String Common_commandEventMediatorExtension_unavailable;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code Messages} class.
     */
    static
    {
        NLS.initializeMessages( BUNDLE_NAME, Messages.class );
    }

    /**
     * Initializes a new instance of the {@code Messages} class.
     */
    private Messages()
    {
        super();
    }
}

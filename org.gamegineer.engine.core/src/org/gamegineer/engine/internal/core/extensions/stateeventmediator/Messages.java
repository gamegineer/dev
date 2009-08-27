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
 * Created on Jun 10, 2008 at 11:42:14 PM.
 */

package org.gamegineer.engine.internal.core.extensions.stateeventmediator;

import org.eclipse.osgi.util.NLS;
import org.gamegineer.engine.core.AttributeName;

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
    private static final String BUNDLE_NAME = "org.gamegineer.engine.internal.core.extensions.stateeventmediator.Messages"; //$NON-NLS-1$

    // --- Common -----------------------------------------------------------

    /** The state event mediator extension is not available. */
    public static String Common_stateEventMediatorExtension_unavailable;

    // --- StateChangeEventDelegate -----------------------------------------

    /** The attribute change does not exist. */
    public static String StateChangeEventDelegate_attributeChange_absent;

    // --- StateEventMediatorExtension --------------------------------------

    /** The active state listener collection is not available. */
    public static String StateEventMediatorExtension_activeStateListeners_unavailable;

    /** The state listener is already registered. */
    public static String StateEventMediatorExtension_listener_registered;

    /** The state listener has not been registered. */
    public static String StateEventMediatorExtension_listener_unregistered;

    /** Unexpected exception thrown from IStateListener.stateChanged(). */
    public static String StateEventMediatorExtension_stateChanged_unexpectedException;

    /** Unexpected exception thrown from IStateListener.stateChanging(). */
    public static String StateEventMediatorExtension_stateChanging_unexpectedException;


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


    // ======================================================================
    // Methods
    // ======================================================================

    // --- StateChangeEventDelegate -----------------------------------------

    /**
     * Gets the formatted message indicating the attribute change does not
     * exist.
     * 
     * @param attributeName
     *        The attribute name; must not be {@code null}.
     * 
     * @return The formatted message indicating the attribute change does not
     *         exist; never {@code null}.
     */
    /* @NonNull */
    static String StateChangeEventDelegate_attributeChange_absent(
        /* @NonNull */
        final AttributeName attributeName )
    {
        return bind( StateChangeEventDelegate_attributeChange_absent, attributeName );
    }
}

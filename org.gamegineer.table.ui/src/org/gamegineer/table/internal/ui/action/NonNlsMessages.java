/*
 * NonNlsMessages.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Oct 22, 2009 at 10:36:42 PM.
 */

package org.gamegineer.table.internal.ui.action;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;

/**
 * A utility class to manage non-localized messages for the package.
 */
@ThreadSafe
final class NonNlsMessages
    extends NLS
{
    // ======================================================================
    // Fields
    // ======================================================================

    // --- ActionMediator ---------------------------------------------------

    /** An action listener is already bound. */
    public static String ActionMediator_bindActionListener_alreadyBound;

    /** A should enable predicate is already bound. */
    public static String ActionMediator_bindShouldEnablePredicate_alreadyBound;

    /** A should select predicate is already bound. */
    public static String ActionMediator_bindShouldSelectPredicate_alreadyBound;

    /** The action has no attachments. */
    public static String ActionMediator_unbind_noAttachments;

    /** The action has no predicates. */
    public static String ActionMediator_update_noPredicates;

    // --- BasicAction ------------------------------------------------------

    /** The action listener is already registered. */
    public static String BasicAction_addActionListener_listener_registered;

    /** The should enable predicate is already registered. */
    public static String BasicAction_addShouldEnablePredicate_predicate_registered;

    /** The should select predicate is already registered. */
    public static String BasicAction_addShouldSelectPredicate_predicate_registered;

    /** The action listener is not registered. */
    public static String BasicAction_removeActionListener_listener_notRegistered;

    /** The should enable predicate is not registered. */
    public static String BasicAction_removeShouldEnablePredicate_predicate_notRegistered;

    /** The should select predicate is not registered. */
    public static String BasicAction_removeShouldSelectPredicate_predicate_notRegistered;

    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code NonNlsMessages} class.
     */
    static
    {
        NLS.initializeMessages( NonNlsMessages.class.getName(), NonNlsMessages.class );
    }

    /**
     * Initializes a new instance of the {@code NonNlsMessages} class.
     */
    private NonNlsMessages()
    {
    }
}

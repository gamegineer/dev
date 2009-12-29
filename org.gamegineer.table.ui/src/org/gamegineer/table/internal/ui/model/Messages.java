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
 * Created on Dec 26, 2009 at 8:59:34 PM.
 */

package org.gamegineer.table.internal.ui.model;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;

/**
 * A utility class to manage localized messages for the package.
 */
@ThreadSafe
final class Messages
    extends NLS
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the associated resource bundle. */
    private static final String BUNDLE_NAME = "org.gamegineer.table.internal.ui.model.Messages"; //$NON-NLS-1$

    // --- CardModel --------------------------------------------------------

    /** The card model listener is already registered. */
    public static String CardModel_addCardModelListener_listener_registered;

    /** A card model listener is already registered. */
    public static String CardModel_addCardModelListener_tooManyListeners;

    /**
     * An unexpected exception was thrown from
     * ICardModelListener.cardFocusChanged().
     */
    public static String CardModel_cardFocusChanged_unexpectedException;

    /** The card model listener is not registered. */
    public static String CardModel_removeCardModelListener_listener_notRegistered;

    // --- TableModel -------------------------------------------------------

    /** The table model listener is already registered. */
    public static String TableModel_addTableModelListener_listener_registered;

    /** A table model listener is already registered. */
    public static String TableModel_addTableModelListener_tooManyListeners;

    /**
     * An unexpected exception was thrown from
     * ITableModelListener.cardFocusChanged().
     */
    public static String TableModel_cardFocusChanged_unexpectedException;

    /** The card is not present in the table. */
    public static String TableModel_getCardModel_card_absent;

    /** The table model listener is not registered. */
    public static String TableModel_removeTableModelListener_listener_notRegistered;


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

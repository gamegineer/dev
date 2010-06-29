/*
 * Messages.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Oct 17, 2009 at 12:20:45 AM.
 */

package org.gamegineer.table.internal.core;

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

    // --- Card -------------------------------------------------------------

    /** The card listener is already registered. */
    public static String Card_addCardListener_listener_registered;

    /**
     * An unexpected exception was thrown from
     * ICardListener.cardLocationChanged().
     */
    public static String Card_cardLocationChanged_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * ICardListener.cardOrientationChanged().
     */
    public static String Card_cardOrientationChanged_unexpectedException;

    /** The face design size is not equal to the back design size. */
    public static String Card_ctor_faceDesign_sizeNotEqual;

    /** The card listener is not registered. */
    public static String Card_removeCardListener_listener_notRegistered;

    // --- CardPile ---------------------------------------------------------

    /** The card pile listener is already registered. */
    public static String CardPile_addCardPileListener_listener_registered;

    /** An unexpected exception was thrown from ICardPileListener.cardAdded(). */
    public static String CardPile_cardAdded_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * ICardPileListener.cardPileBoundsChanged().
     */
    public static String CardPile_cardPileBoundsChanged_unexpectedException;

    /** An unexpected exception was thrown from ICardPileListener.cardRemoved(). */
    public static String CardPile_cardRemoved_unexpectedException;

    /** An unknown layout is active. */
    public static String CardPile_getCardOffsetAt_unknownLayout;

    /** The card pile listener is not registered. */
    public static String CardPile_removeCardPileListener_listener_notRegistered;

    // --- CardPileBaseDesign -----------------------------------------------

    /** The card pile base design height must not be negative. */
    public static String CardPileBaseDesign_ctor_height_negative;

    /** The card pile base design width must not be negative. */
    public static String CardPileBaseDesign_ctor_width_negative;

    // --- CardSurfaceDesign ------------------------------------------------

    /** The card surface design height must not be negative. */
    public static String CardSurfaceDesign_ctor_height_negative;

    /** The card surface design width must not be negative. */
    public static String CardSurfaceDesign_ctor_width_negative;

    // --- Table ------------------------------------------------------------

    /** The table listener is already registered. */
    public static String Table_addTableListener_listener_registered;

    /** An unexpected exception was thrown from ITableListener.cardPileAdded(). */
    public static String Table_cardPileAdded_unexpectedException;

    /**
     * An unexpected exception was thrown from ITableListener.cardPileRemoved().
     */
    public static String Table_cardPileRemoved_unexpectedException;

    /** The table listener is not registered. */
    public static String Table_removeTableListener_listener_notRegistered;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code Messages} class.
     */
    static
    {
        NLS.initializeMessages( Messages.class.getName(), Messages.class );
    }

    /**
     * Initializes a new instance of the {@code Messages} class.
     */
    private Messages()
    {
        super();
    }
}

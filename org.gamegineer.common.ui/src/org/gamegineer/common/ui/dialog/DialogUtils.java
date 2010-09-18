/*
 * DialogUtils.java
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
 * Created on Sep 11, 2010 at 9:06:54 PM.
 */

package org.gamegineer.common.ui.dialog;

import java.awt.FontMetrics;
import net.jcip.annotations.ThreadSafe;

/**
 * A collection of useful methods for dialogs.
 */
@ThreadSafe
public final class DialogUtils
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Count of horizontal dialog units per character. */
    private static final int HORIZONTAL_DIALOG_UNIT_PER_CHAR = 4;

    /** Count of vertical dialog units per character. */
    private static final int VERTICAL_DIALOG_UNITS_PER_CHAR = 8;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DialogUtils} class.
     */
    private DialogUtils()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Converts the specified height in characters to pixels based on the
     * specified font.
     * 
     * @param fontMetrics
     *        The font metrics; must not be {@code null}.
     * @param chars
     *        The height in characters.
     * 
     * @return The height in pixels.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code fontMetrics} is {@code null}.
     */
    public static int convertHeightInCharsToPixels(
        /* @NonNull */
        final FontMetrics fontMetrics,
        final int chars )
    {
        return fontMetrics.getHeight() * chars;
    }

    /**
     * Converts the specified height in dialog units to pixels based on the
     * specified font.
     * 
     * @param fontMetrics
     *        The font metrics; must not be {@code null}.
     * @param dlus
     *        The height in dialog units.
     * 
     * @return The height in pixels.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code fontMetrics} is {@code null}.
     */
    public static int convertHeightInDlusToPixels(
        /* @NonNull */
        final FontMetrics fontMetrics,
        final int dlus )
    {
        // round to the nearest pixel
        return (fontMetrics.getHeight() * dlus + VERTICAL_DIALOG_UNITS_PER_CHAR / 2) / VERTICAL_DIALOG_UNITS_PER_CHAR;
    }

    /**
     * Converts the specified width in characters to pixels based on the
     * specified font.
     * 
     * @param fontMetrics
     *        The font metrics; must not be {@code null}.
     * @param chars
     *        The width in characters.
     * 
     * @return The width in pixels.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code fontMetrics} is {@code null}.
     */
    public static int convertWidthInCharsToPixels(
        /* @NonNull */
        final FontMetrics fontMetrics,
        final int chars )
    {
        return getAverageCharWidth( fontMetrics ) * chars;
    }

    /**
     * Converts the specified width in dialog units to pixels based on the
     * specified font.
     * 
     * @param fontMetrics
     *        The font metrics; must not be {@code null}.
     * @param dlus
     *        The width in dialog units.
     * 
     * @return The width in pixels.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code fontMetrics} is {@code null}.
     */
    public static int convertWidthInDlusToPixels(
        /* @NonNull */
        final FontMetrics fontMetrics,
        final int dlus )
    {
        // round to the nearest pixel
        return (getAverageCharWidth( fontMetrics ) * dlus + HORIZONTAL_DIALOG_UNIT_PER_CHAR / 2) / HORIZONTAL_DIALOG_UNIT_PER_CHAR;
    }

    /**
     * Gets the average character width for the specified font.
     * 
     * @param fontMetrics
     *        The font metrics; must not be {@code null}.
     * 
     * @return The average character width for the specified font.
     */
    private static int getAverageCharWidth(
        /* @NonNull */
        final FontMetrics fontMetrics )
    {
        // using Windows definition of average character width
        return (fontMetrics.charWidth( 'X' ) + fontMetrics.charWidth( 'x' )) / 2;
    }
}

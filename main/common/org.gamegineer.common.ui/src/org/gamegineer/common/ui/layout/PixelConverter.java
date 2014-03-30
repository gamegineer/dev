/*
 * PixelConverter.java
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
 * Created on Nov 4, 2011 at 9:21:21 PM.
 */

package org.gamegineer.common.ui.layout;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import java.awt.Component;
import java.awt.FontMetrics;
import net.jcip.annotations.Immutable;

/**
 * Performs various calculations from device-independent units (such as DLUs or
 * characters) to pixels.
 */
@Immutable
public final class PixelConverter
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Count of horizontal dialog units per character. */
    private static final int HORIZONTAL_DIALOG_UNIT_PER_CHAR = 4;

    /** Count of vertical dialog units per character. */
    private static final int VERTICAL_DIALOG_UNITS_PER_CHAR = 8;

    /** The font metrics used for pixel conversions. */
    private final FontMetrics fontMetrics_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PixelConverter} class using the
     * font of the specified component.
     * 
     * @param component
     *        The component whose font used for pixel conversions; must not be
     *        {@code null}.
     */
    public PixelConverter(
        final Component component )
    {
        this( nonNull( component.getFontMetrics( component.getFont() ) ) );
    }

    /**
     * Initializes a new instance of the {@code PixelConverter} class using the
     * specified font metrics.
     * 
     * @param fontMetrics
     *        The font metrics used for pixel conversions; must not be
     *        {@code null}.
     */
    public PixelConverter(
        final FontMetrics fontMetrics )
    {
        fontMetrics_ = fontMetrics;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Converts the specified height in characters to pixels.
     * 
     * @param chars
     *        The height in characters.
     * 
     * @return The height in pixels.
     */
    public int convertHeightInCharsToPixels(
        final int chars )
    {
        return fontMetrics_.getHeight() * chars;
    }

    /**
     * Converts the specified height in dialog units to pixels.
     * 
     * @param dlus
     *        The height in dialog units.
     * 
     * @return The height in pixels.
     */
    public int convertHeightInDlusToPixels(
        final int dlus )
    {
        // round to the nearest pixel
        return (fontMetrics_.getHeight() * dlus + VERTICAL_DIALOG_UNITS_PER_CHAR / 2) / VERTICAL_DIALOG_UNITS_PER_CHAR;
    }

    /**
     * Converts the specified width in characters to pixels.
     * 
     * @param chars
     *        The width in characters.
     * 
     * @return The width in pixels.
     */
    public int convertWidthInCharsToPixels(
        final int chars )
    {
        return getAverageCharWidth() * chars;
    }

    /**
     * Converts the specified width in dialog units to pixels.
     * 
     * @param dlus
     *        The width in dialog units.
     * 
     * @return The width in pixels.
     */
    public int convertWidthInDlusToPixels(
        final int dlus )
    {
        // round to the nearest pixel
        return (getAverageCharWidth() * dlus + HORIZONTAL_DIALOG_UNIT_PER_CHAR / 2) / HORIZONTAL_DIALOG_UNIT_PER_CHAR;
    }

    /**
     * Gets the average character width.
     * 
     * @return The average character width.
     */
    private int getAverageCharWidth()
    {
        // using Windows definition of average character width
        return (fontMetrics_.charWidth( 'X' ) + fontMetrics_.charWidth( 'x' )) / 2;
    }
}

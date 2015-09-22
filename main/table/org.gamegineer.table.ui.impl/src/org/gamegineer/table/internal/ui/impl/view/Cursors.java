/*
 * Cursors.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Aug 21, 2010 at 9:10:06 PM.
 */

package org.gamegineer.table.internal.ui.impl.view;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.ui.impl.Activator;
import org.gamegineer.table.internal.ui.impl.BundleImages;
import org.gamegineer.table.internal.ui.impl.Loggers;

/**
 * The collection of cursors available to all views.
 */
@ThreadSafe
final class Cursors
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The grab cursor. */
    private static final Cursor grabCursor_;

    /** The hand cursor. */
    private static final Cursor handCursor_;

    /** The invalid cursor. */
    private static final Cursor invalidCursor_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code Cursors} class.
     */
    static
    {
        grabCursor_ = createCursor( BundleImages.CURSOR_GRAB, new Point( 0, 0 ), NonNlsMessages.Cursors_grab_name );
        handCursor_ = createCursor( BundleImages.CURSOR_HAND, new Point( 0, 0 ), NonNlsMessages.Cursors_hand_name );
        invalidCursor_ = createInvalidCursor();
    }

    /**
     * Initializes a new instance of the {@code Cursors} class.
     */
    private Cursors()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a cursor with the specified image.
     * 
     * @param path
     *        The bundle-relative path to the cursor image; must not be
     *        {@code null}.
     * @param hotSpot
     *        The cursor hot spot; must not be {@code null}.
     * @param name
     *        The localized cursor name; must not be {@code null}.
     * 
     * @return A new cursor; never {@code null}.
     */
    private static Cursor createCursor(
        final String path,
        final Point hotSpot,
        final String name )
    {
        final ImageIcon cursorIcon = Activator.getDefault().getBundleImages().getIcon( path );
        assert cursorIcon != null;

        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension bestCursorSize = toolkit.getBestCursorSize( cursorIcon.getIconWidth(), cursorIcon.getIconHeight() );
        final Image cursorImage;
        if( (bestCursorSize.width == cursorIcon.getIconWidth()) && (bestCursorSize.height == cursorIcon.getIconHeight()) )
        {
            cursorImage = cursorIcon.getImage();
        }
        else
        {
            final BufferedImage image = new BufferedImage( bestCursorSize.width, bestCursorSize.height, BufferedImage.TYPE_INT_ARGB_PRE );
            final Graphics2D g = image.createGraphics();
            try
            {
                g.drawImage( cursorIcon.getImage(), 0, 0, null );
            }
            finally
            {
                g.dispose();
            }
            cursorImage = image;
        }

        return toolkit.createCustomCursor( cursorImage, hotSpot, name );
    }

    /**
     * Creates the invalid cursor.
     * 
     * @return The invalid cursor; never {@code null}.
     */
    private static Cursor createInvalidCursor()
    {
        try
        {
            final String cursorName = "Invalid.32x32"; //$NON-NLS-1$
            final Cursor cursor = Cursor.getSystemCustomCursor( cursorName );
            if( cursor == null )
            {
                throw new AWTException( cursorName );
            }

            return cursor;
        }
        catch( final AWTException e )
        {
            Loggers.getDefaultLogger().log( Level.WARNING, NonNlsMessages.Cursors_createInvalidCursor_failed, e );
            return Cursor.getDefaultCursor();
        }
    }

    /**
     * Gets the default cursor.
     * 
     * @return The default cursor; never {@code null}.
     */
    static Cursor getDefaultCursor()
    {
        return Cursor.getDefaultCursor();
    }

    /**
     * Gets the grab cursor.
     * 
     * @return The grab cursor; never {@code null}.
     */
    static Cursor getGrabCursor()
    {
        return grabCursor_;
    }

    /**
     * Gets the hand cursor.
     * 
     * @return The hand cursor; never {@code null}.
     */
    static Cursor getHandCursor()
    {
        return handCursor_;
    }

    /**
     * Gets the invalid cursor.
     * 
     * @return The invalid cursor; never {@code null}.
     */
    static Cursor getInvalidCursor()
    {
        return invalidCursor_;
    }
}

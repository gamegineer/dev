/*
 * IconProxy.java
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
 * Created on Nov 22, 2009 at 10:05:08 PM.
 */

package org.gamegineer.table.internal.ui.impl.util.swing;

import java.awt.Component;
import java.awt.Graphics;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Implementation of {@link Icon} to support lazy loading.
 * 
 * <p>
 * Instances of this class should only be used from the Swing Event Dispatch
 * Thread.
 * </p>
 */
@NotThreadSafe
public final class IconProxy
    implements Icon
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The underlying icon. */
    private @Nullable Icon icon_;

    /** The icon URL. */
    private final URL url_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code IconProxy} class.
     * 
     * @param url
     *        The icon URL.
     */
    public IconProxy(
        final URL url )
    {
        icon_ = null;
        url_ = url;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see javax.swing.Icon#getIconHeight()
     */
    @Override
    public int getIconHeight()
    {
        final Icon icon = load();

        return icon.getIconHeight();
    }

    /*
     * @see javax.swing.Icon#getIconWidth()
     */
    @Override
    public int getIconWidth()
    {
        final Icon icon = load();

        return icon.getIconWidth();
    }

    /**
     * Loads the underlying icon if necessary.
     * 
     * @return The underlying icon.
     */
    private Icon load()
    {
        if( icon_ == null )
        {
            icon_ = new ImageIcon( url_ );
        }

        assert icon_ != null;
        return icon_;
    }

    /*
     * @see javax.swing.Icon#paintIcon(java.awt.Component, java.awt.Graphics, int, int)
     */
    @Override
    public void paintIcon(
        final @Nullable Component c,
        final @Nullable Graphics g,
        final int x,
        final int y )
    {
        final Icon icon = load();

        icon.paintIcon( c, g, x, y );
    }
}

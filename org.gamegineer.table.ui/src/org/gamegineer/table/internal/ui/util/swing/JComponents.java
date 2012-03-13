/*
 * JComponents.java
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
 * Created on Oct 10, 2010 at 8:58:43 PM.
 */

package org.gamegineer.table.internal.ui.util.swing;

import java.awt.Dimension;
import javax.swing.JComponent;
import net.jcip.annotations.ThreadSafe;

/**
 * A collection of useful methods for working with Java Swing components.
 */
@ThreadSafe
public final class JComponents
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code JComponents} class.
     */
    private JComponents()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Freezes the height of the specified component so it is not enlarged or
     * reduced beyond its preferred height.
     * 
     * @param <T>
     *        The type of the component.
     * 
     * @param component
     *        The component; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code component} is {@code null}.
     */
    public static <T extends JComponent> void freezeHeight(
        /* @NonNull */
        final T component )
    {
        final Dimension preferredSize = component.getPreferredSize();
        final Dimension maximumSize = component.getMaximumSize();
        maximumSize.height = preferredSize.height;
        component.setMaximumSize( maximumSize );
    }
}

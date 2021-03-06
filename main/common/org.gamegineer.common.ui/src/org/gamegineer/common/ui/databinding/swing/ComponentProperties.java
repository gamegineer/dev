/*
 * ComponentProperties.java
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
 * Created on Oct 14, 2010 at 10:16:22 PM.
 */

package org.gamegineer.common.ui.databinding.swing;

import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.text.JTextComponent;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.databinding.property.value.IValueProperty;

/**
 * A factory for creating data binding properties for Swing components.
 */
@ThreadSafe
public final class ComponentProperties
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentProperties} class.
     */
    private ComponentProperties()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets a value property for observing the password of a
     * {@link JPasswordField}.
     * 
     * @return A value property for observing the password of a
     *         {@link JPasswordField}.
     */
    public static IValueProperty password()
    {
        return new ComponentPasswordProperty();
    }

    /**
     * Gets a value property for observing the single selection value of a
     * {@link JList}.
     * 
     * @return A value property for observing the password of a {@link JList}.
     */
    public static IValueProperty singleSelectionValue()
    {
        return new ComponentSingleSelectionValueProperty();
    }

    /**
     * Gets a value property for observing the text of a {@link JTextComponent}.
     * 
     * @return A value property for observing the text of a
     *         {@link JTextComponent}.
     */
    public static IValueProperty text()
    {
        return new ComponentTextProperty();
    }
}

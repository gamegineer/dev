/*
 * ComponentTextProperty.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Oct 14, 2010 at 10:23:03 PM.
 */

package org.gamegineer.common.ui.databinding.swing;

import java.awt.Component;
import javax.swing.text.JTextComponent;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.databinding.property.value.DelegatingValueProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;

/**
 * A value property for the text of an instance of {@link Component}.
 */
@NotThreadSafe
final class ComponentTextProperty
    extends DelegatingValueProperty
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The value property for instances of {@code JTextComponent}. */
    private IValueProperty textComponentValueProperty_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentTextProperty} class.
     */
    ComponentTextProperty()
    {
        super( String.class );

        textComponentValueProperty_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.eclipse.core.databinding.property.value.DelegatingValueProperty#doGetDelegate(java.lang.Object)
     */
    @Override
    protected IValueProperty doGetDelegate(
        final Object source )
    {
        if( source instanceof JTextComponent )
        {
            if( textComponentValueProperty_ == null )
            {
                textComponentValueProperty_ = new TextComponentTextProperty();
            }

            return textComponentValueProperty_;
        }

        throw new IllegalArgumentException( NonNlsMessages.ComponentProperties_text_unsupportedComponent( source ) );
    }
}

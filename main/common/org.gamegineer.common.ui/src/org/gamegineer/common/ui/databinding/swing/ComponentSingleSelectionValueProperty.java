/*
 * ComponentSingleSelectionValueProperty.java
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
 * Created on Sep 17, 2011 at 10:44:44 PM.
 */

package org.gamegineer.common.ui.databinding.swing;

import java.awt.Component;
import javax.swing.JList;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.databinding.property.value.DelegatingValueProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;

/**
 * A value property for the single selection value of an instance of
 * {@link Component}.
 */
@NotThreadSafe
final class ComponentSingleSelectionValueProperty
    extends DelegatingValueProperty
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The value property for instances of {@code JList}. */
    private IValueProperty listValueProperty_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentSingleSelectionValueProperty} class.
     */
    ComponentSingleSelectionValueProperty()
    {
        super( Object.class );

        listValueProperty_ = null;
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
        if( source instanceof JList )
        {
            if( listValueProperty_ == null )
            {
                listValueProperty_ = new ListSingleSelectionValueProperty();
            }

            return listValueProperty_;
        }

        throw new IllegalArgumentException( NonNlsMessages.ComponentProperties_singleSelectionValue_unsupportedComponent( source ) );
    }
}

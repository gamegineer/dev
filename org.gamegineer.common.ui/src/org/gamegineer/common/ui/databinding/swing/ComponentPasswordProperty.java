/*
 * ComponentPasswordProperty.java
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
 * Created on Oct 29, 2010 at 10:45:56 PM.
 */

package org.gamegineer.common.ui.databinding.swing;

import javax.swing.JPasswordField;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.databinding.property.value.DelegatingValueProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.gamegineer.common.core.security.SecureString;

/**
 * A value property for the password of an instance of
 * {@link java.awt.Component}.
 */
@NotThreadSafe
final class ComponentPasswordProperty
    extends DelegatingValueProperty
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The value property for instances of {@code JPasswordField}. */
    private IValueProperty passwordFieldValueProperty_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentPasswordProperty}
     * class.
     */
    ComponentPasswordProperty()
    {
        super( SecureString.class );

        passwordFieldValueProperty_ = null;
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
        if( source instanceof JPasswordField )
        {
            if( passwordFieldValueProperty_ == null )
            {
                passwordFieldValueProperty_ = new PasswordFieldTextProperty();
            }

            return passwordFieldValueProperty_;
        }

        throw new IllegalArgumentException( Messages.ComponentProperties_password_unsupportedComponent( source ) );
    }
}

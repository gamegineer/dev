/*
 * PasswordFieldTextProperty.java
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
 * Created on Oct 29, 2010 at 10:48:20 PM.
 */

package org.gamegineer.common.ui.databinding.swing;

import java.util.logging.Level;
import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.IProperty;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.core.databinding.property.NativePropertyListener;
import org.eclipse.core.databinding.property.value.SimpleValueProperty;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.common.internal.ui.Loggers;

/**
 * A value property for the password property of an instance of
 * {@link JPasswordField}.
 */
@Immutable
final class PasswordFieldTextProperty
    extends SimpleValueProperty
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PasswordFieldTextProperty}
     * class.
     */
    PasswordFieldTextProperty()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.eclipse.core.databinding.property.value.SimpleValueProperty#adaptListener(org.eclipse.core.databinding.property.ISimplePropertyListener)
     */
    @Override
    public INativePropertyListener adaptListener(
        @Nullable
        final ISimplePropertyListener listener )
    {
        assert listener != null;

        return new DocumentPropertyListener( this, listener );
    }

    /*
     * @see org.eclipse.core.databinding.property.value.SimpleValueProperty#doGetValue(java.lang.Object)
     */
    @Override
    protected Object doGetValue(
        @Nullable
        final Object source )
    {
        assert source != null;

        return SecureString.fromCharArray( getPassword( (JPasswordField)source ) );
    }

    /*
     * @see org.eclipse.core.databinding.property.value.SimpleValueProperty#doSetValue(java.lang.Object, java.lang.Object)
     */
    @Override
    protected void doSetValue(
        @Nullable
        final Object source,
        @Nullable
        final Object value )
    {
        assert source != null;
        assert value != null;

        ((JPasswordField)source).setText( ((SecureString)value).toString() );
    }

    /**
     * Gets the password for the specified password field.
     * 
     * @param source
     *        The password field; must not be {@code null}.
     * 
     * @return The password for the specified password field; never {@code null}
     *         .
     */
    private static char[] getPassword(
        final JPasswordField source )
    {
        final char[] password = source.getPassword();
        if( password == null )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.PasswordFieldTextProperty_getPassword_notAvailable );
            return new char[ 0 ];
        }

        return password;
    }

    /*
     * @see org.eclipse.core.databinding.property.value.IValueProperty#getValueType()
     */
    @Override
    public Object getValueType()
    {
        return SecureString.class;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A property listener for instances of {@link Document}.
     */
    @NotThreadSafe
    private static final class DocumentPropertyListener
        extends NativePropertyListener
        implements DocumentListener
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The text component associated with this listener. */
        @Nullable
        private JPasswordField source_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code DocumentPropertyListener}
         * class.
         * 
         * @param property
         *        The property to which this listener listens; must not be
         *        {@code null}.
         * @param listener
         *        The listener that receives property change notifications; must
         *        not be {@code null}.
         */
        DocumentPropertyListener(
            final IProperty property,
            final ISimplePropertyListener listener )
        {
            super( property, listener );

            source_ = null;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
         */
        @Override
        public void changedUpdate(
            @Nullable
            @SuppressWarnings( "unused" )
            final DocumentEvent event )
        {
            assert source_ != null;

            fireChange( source_, null );
        }

        /*
         * @see org.eclipse.core.databinding.property.NativePropertyListener#doAddTo(java.lang.Object)
         */
        @Override
        protected void doAddTo(
            @Nullable
            final Object source )
        {
            assert source != null;
            assert source_ == null;

            source_ = (JPasswordField)source;
            source_.getDocument().addDocumentListener( this );
        }

        /*
         * @see org.eclipse.core.databinding.property.NativePropertyListener#doRemoveFrom(java.lang.Object)
         */
        @Override
        protected void doRemoveFrom(
            @Nullable
            final Object source )
        {
            assert source != null;
            assert source_ == source;

            source_.getDocument().removeDocumentListener( this );
            source_ = null;
        }

        /*
         * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
         */
        @Override
        public void insertUpdate(
            @Nullable
            @SuppressWarnings( "unused" )
            final DocumentEvent event )
        {
            assert source_ != null;

            fireChange( source_, null );
        }

        /*
         * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
         */
        @Override
        public void removeUpdate(
            @Nullable
            @SuppressWarnings( "unused" )
            final DocumentEvent event )
        {
            assert source_ != null;

            fireChange( source_, null );
        }
    }
}

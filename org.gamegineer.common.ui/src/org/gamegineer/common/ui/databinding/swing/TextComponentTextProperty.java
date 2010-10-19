/*
 * TextComponentTextProperty.java
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
 * Created on Oct 14, 2010 at 10:30:55 PM.
 */

package org.gamegineer.common.ui.databinding.swing;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.IProperty;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.core.databinding.property.NativePropertyListener;
import org.eclipse.core.databinding.property.value.SimpleValueProperty;

/**
 * A value property for the text property of an instance of
 * {@link javax.swing.text.JTextComponent}.
 */
@Immutable
final class TextComponentTextProperty
    extends SimpleValueProperty
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TextComponentTextProperty}
     * class.
     */
    TextComponentTextProperty()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.eclipse.core.databinding.property.value.SimpleValueProperty#adaptListener(org.eclipse.core.databinding.property.ISimplePropertyListener)
     */
    @Override
    public INativePropertyListener adaptListener(
        final ISimplePropertyListener listener )
    {
        return new DocumentPropertyListener( this, listener );
    }

    /*
     * @see org.eclipse.core.databinding.property.value.SimpleValueProperty#doGetValue(java.lang.Object)
     */
    @Override
    protected Object doGetValue(
        final Object source )
    {
        return ((JTextComponent)source).getText();
    }

    /*
     * @see org.eclipse.core.databinding.property.value.SimpleValueProperty#doSetValue(java.lang.Object, java.lang.Object)
     */
    @Override
    protected void doSetValue(
        final Object source,
        final Object value )
    {
        ((JTextComponent)source).setText( (String)value );
    }

    /*
     * @see org.eclipse.core.databinding.property.value.IValueProperty#getValueType()
     */
    @Override
    public Object getValueType()
    {
        return String.class;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A property listener for instances of {@link javax.swing.text.Document}.
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
        private JTextComponent source_;


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
            /* @NonNull */
            final IProperty property,
            /* @NonNull */
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
            final Object source )
        {
            assert source_ == null;

            source_ = (JTextComponent)source;
            source_.getDocument().addDocumentListener( this );
        }

        /*
         * @see org.eclipse.core.databinding.property.NativePropertyListener#doRemoveFrom(java.lang.Object)
         */
        @Override
        protected void doRemoveFrom(
            final Object source )
        {
            assert source_ == source;

            source_.getDocument().removeDocumentListener( this );
            source_ = null;
        }

        /*
         * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
         */
        @Override
        public void insertUpdate(
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
            @SuppressWarnings( "unused" )
            final DocumentEvent event )
        {
            assert source_ != null;

            fireChange( source_, null );
        }
    }
}

/*
 * ListSingleSelectionValueProperty.java
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
 * Created on Sep 17, 2011 at 10:55:32 PM.
 */

package org.gamegineer.common.ui.databinding.swing;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.IProperty;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.core.databinding.property.NativePropertyListener;
import org.eclipse.core.databinding.property.value.SimpleValueProperty;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A value property for the single selection value property of an instance of
 * {@link JList}.
 */
@Immutable
final class ListSingleSelectionValueProperty
    extends SimpleValueProperty
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ListSingleSelectionValueProperty} class.
     */
    ListSingleSelectionValueProperty()
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
        final @Nullable ISimplePropertyListener listener )
    {
        assert listener != null;

        return new ListPropertyListener( this, listener );
    }

    /*
     * @see org.eclipse.core.databinding.property.value.SimpleValueProperty#doGetValue(java.lang.Object)
     */
    @Override
    protected @Nullable Object doGetValue(
        final @Nullable Object source )
    {
        assert source != null;

        return ((JList<?>)source).getSelectedValue();
    }

    /*
     * @see org.eclipse.core.databinding.property.value.SimpleValueProperty#doSetValue(java.lang.Object, java.lang.Object)
     */
    @Override
    protected void doSetValue(
        final @Nullable Object source,
        final @Nullable Object value )
    {
        assert source != null;

        ((JList<?>)source).setSelectedValue( value, true );
    }

    /*
     * @see org.eclipse.core.databinding.property.value.IValueProperty#getValueType()
     */
    @Override
    public @Nullable Object getValueType()
    {
        return null;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A property listener for instances of {@link JList}.
     */
    @NotThreadSafe
    private static final class ListPropertyListener
        extends NativePropertyListener
        implements ListSelectionListener
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The list associated with this listener. */
        private @Nullable JList<?> source_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ListPropertyListener} class.
         * 
         * @param property
         *        The property to which this listener listens.
         * @param listener
         *        The listener that receives property change notifications.
         */
        ListPropertyListener(
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
         * @see org.eclipse.core.databinding.property.NativePropertyListener#doAddTo(java.lang.Object)
         */
        @Override
        protected void doAddTo(
            final @Nullable Object source )
        {
            assert source != null;
            assert source_ == null;

            source_ = (JList<?>)source;
            source_.addListSelectionListener( this );
        }

        /*
         * @see org.eclipse.core.databinding.property.NativePropertyListener#doRemoveFrom(java.lang.Object)
         */
        @Override
        protected void doRemoveFrom(
            final @Nullable Object source )
        {
            assert source != null;
            assert source_ == source;

            source_.removeListSelectionListener( this );
            source_ = null;
        }

        /*
         * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
         */
        @Override
        public void valueChanged(
            final @Nullable ListSelectionEvent event )
        {
            assert source_ != null;

            fireChange( source_, null );
        }
    }
}

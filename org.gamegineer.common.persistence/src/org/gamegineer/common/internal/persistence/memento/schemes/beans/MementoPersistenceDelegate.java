/*
 * MementoPersistenceDelegate.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Jul 1, 2008 at 12:36:27 AM.
 */

package org.gamegineer.common.internal.persistence.memento.schemes.beans;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import net.jcip.annotations.Immutable;
import org.eclipse.core.runtime.IAdapterFactory;
import org.gamegineer.common.internal.persistence.memento.Memento;

/**
 * A persistence delegate for the {@code Memento} class.
 */
@Immutable
public final class MementoPersistenceDelegate
    extends PersistenceDelegate
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The flyweight instance of this class. */
    public static final PersistenceDelegate INSTANCE = new MementoPersistenceDelegate();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MementoPersistenceDelegate}
     * class.
     */
    private MementoPersistenceDelegate()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.beans.PersistenceDelegate#instantiate(java.lang.Object, java.beans.Encoder)
     */
    @Override
    protected Expression instantiate(
        final Object oldInstance,
        @SuppressWarnings( "unused" )
        final Encoder out )
    {
        final Memento obj = (Memento)oldInstance;
        return new Expression( oldInstance, Memento.class, "new", new Object[] { //$NON-NLS-1$
                obj.getAttributes()
            } );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A {@code PersistenceDelegate} adapter factory for instances of {@code
     * MockNonPersistableClass}.
     */
    public static final class AdapterFactory
        implements IAdapterFactory
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code AdapterFactory} class.
         */
        public AdapterFactory()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
         */
        public Object getAdapter(
            final Object adaptableObject,
            @SuppressWarnings( "unchecked" )
            final Class adapterType )
        {
            if( adapterType != PersistenceDelegate.class )
            {
                return null;
            }
            if( !(adaptableObject instanceof Memento) )
            {
                return null;
            }

            return MementoPersistenceDelegate.INSTANCE;
        }

        /*
         * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
         */
        public Class<?>[] getAdapterList()
        {
            return new Class<?>[] {
                PersistenceDelegate.class
            };
        }
    }
}

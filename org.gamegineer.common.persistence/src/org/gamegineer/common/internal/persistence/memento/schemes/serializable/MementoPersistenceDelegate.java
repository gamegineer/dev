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
 * Created on Jul 1, 2008 at 12:34:36 AM.
 */

package org.gamegineer.common.internal.persistence.memento.schemes.serializable;

import net.jcip.annotations.Immutable;
import org.eclipse.core.runtime.IAdapterFactory;
import org.gamegineer.common.internal.persistence.memento.Memento;
import org.gamegineer.common.persistence.schemes.serializable.IPersistenceDelegate;

/**
 * A persistence delegate for the {@code Memento} class.
 */
@Immutable
public final class MementoPersistenceDelegate
    implements IPersistenceDelegate
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The flyweight instance of this class. */
    public static final IPersistenceDelegate INSTANCE = new MementoPersistenceDelegate();


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
     * @see org.gamegineer.common.persistence.schemes.serializable.IPersistenceDelegate#replaceObject(java.lang.Object)
     */
    public Object replaceObject(
        final Object obj )
    {
        if( !(obj instanceof Memento) )
        {
            return obj;
        }

        return new MementoProxy( (Memento)obj );
    }

    /*
     * @see org.gamegineer.common.persistence.schemes.serializable.IPersistenceDelegate#resolveObject(java.lang.Object)
     */
    public Object resolveObject(
        final Object obj )
    {
        return obj;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * An {@code IPersistenceDelegate} adapter factory for instances of {@code
     * Memento}.
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
            if( adapterType != IPersistenceDelegate.class )
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
                IPersistenceDelegate.class
            };
        }
    }
}

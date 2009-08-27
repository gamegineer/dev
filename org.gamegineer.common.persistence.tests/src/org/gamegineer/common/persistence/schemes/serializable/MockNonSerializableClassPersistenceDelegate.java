/*
 * MockNonSerializableClassPersistenceDelegate.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Jun 21, 2008 at 10:11:36 PM.
 */

package org.gamegineer.common.persistence.schemes.serializable;

import org.eclipse.core.runtime.IAdapterFactory;

/**
 * A persistence delegate for the {@code MockNonSerializableClass} class.
 */
public final class MockNonSerializableClassPersistenceDelegate
    implements IPersistenceDelegate
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code MockNonSerializableClassPersistenceDelegate} class.
     */
    public MockNonSerializableClassPersistenceDelegate()
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
        if( !(obj instanceof MockNonSerializableClass) )
        {
            return obj;
        }

        return new MockNonSerializableClassProxy( (MockNonSerializableClass)obj );
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
     * An {@code IPersistenceDelegate} adapter factory for instances of
     * {@code MockNonSerializableClass}.
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
            if( !(adaptableObject instanceof MockNonSerializableClass) )
            {
                return null;
            }

            return new MockNonSerializableClassPersistenceDelegate();
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

/*
 * MockNonPersistableClassPersistenceDelegate.java
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
 * Created on Jun 21, 2008 at 11:44:46 PM.
 */

package org.gamegineer.common.persistence.schemes.beans;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.runtime.IAdapterFactory;

/**
 * A persistence delegate for the {@code MockNonPersistableClass} class.
 */
@NotThreadSafe
public final class MockNonPersistableClassPersistenceDelegate
    extends PersistenceDelegate
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * MockNonPersistableClassPersistenceDelegate} class.
     */
    public MockNonPersistableClassPersistenceDelegate()
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
    @SuppressWarnings( "boxing" )
    protected Expression instantiate(
        final Object oldInstance,
        @SuppressWarnings( "unused" )
        final Encoder out )
    {
        final MockNonPersistableClass obj = (MockNonPersistableClass)oldInstance;
        return new Expression( oldInstance, MockNonPersistableClass.class, "new", new Object[] { //$NON-NLS-1$
                obj.getIntField(), obj.getStringField()
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
            if( !(adaptableObject instanceof MockNonPersistableClass) )
            {
                return null;
            }

            return new MockNonPersistableClassPersistenceDelegate();
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

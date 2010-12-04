/*
 * CardSurfaceDesignPersistenceDelegate.java
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
 * Created on May 8, 2010 at 9:14:08 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.io.IOException;
import java.io.ObjectStreamClass;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegate;
import org.gamegineer.common.persistence.serializable.ObjectInputStream;

/**
 * A persistence delegate for the {@code CardSurfaceDesign} class.
 */
@Immutable
public final class CardSurfaceDesignPersistenceDelegate
    extends AbstractPersistenceDelegate
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardSurfaceDesignPersistenceDelegate} class.
     */
    public CardSurfaceDesignPersistenceDelegate()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegate#replaceObject(java.lang.Object)
     */
    @Override
    public Object replaceObject(
        final Object obj )
    {
        if( !(obj instanceof CardSurfaceDesign) )
        {
            return super.replaceObject( obj );
        }

        return new CardSurfaceDesignProxy( (CardSurfaceDesign)obj );
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegate#resolveClass(org.gamegineer.common.persistence.serializable.ObjectInputStream, java.io.ObjectStreamClass)
     */
    @Override
    public Class<?> resolveClass(
        final ObjectInputStream stream,
        final ObjectStreamClass desc )
        throws IOException
    {
        assertArgumentNotNull( stream, "stream" ); //$NON-NLS-1$
        assertArgumentNotNull( desc, "desc" ); //$NON-NLS-1$

        if( desc.getName().equals( CardSurfaceDesignProxy.class.getName() ) )
        {
            return CardSurfaceDesignProxy.class;
        }

        return super.resolveClass( stream, desc );
    }
}

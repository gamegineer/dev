/*
 * CardPileBaseDesignPersistenceDelegate.java
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
 * Created on Apr 27, 2010 at 10:35:43 PM.
 */

package org.gamegineer.table.internal.core.persistence.schemes.serializable;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.io.IOException;
import java.io.ObjectStreamClass;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.persistence.schemes.serializable.AbstractPersistenceDelegate;
import org.gamegineer.common.persistence.schemes.serializable.ObjectInputStream;
import org.gamegineer.table.internal.core.CardPileBaseDesign;

/**
 * A persistence delegate for the {@code CardPileBaseDesign} class.
 */
@Immutable
public final class CardPileBaseDesignPersistenceDelegate
    extends AbstractPersistenceDelegate
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardPileBaseDesignPersistenceDelegate} class.
     */
    public CardPileBaseDesignPersistenceDelegate()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.persistence.schemes.serializable.AbstractPersistenceDelegate#replaceObject(java.lang.Object)
     */
    @Override
    public Object replaceObject(
        final Object obj )
    {
        if( !(obj instanceof CardPileBaseDesign) )
        {
            return super.replaceObject( obj );
        }

        return new CardPileBaseDesignProxy( (CardPileBaseDesign)obj );
    }

    /*
     * @see org.gamegineer.common.persistence.schemes.serializable.AbstractPersistenceDelegate#resolveClass(org.gamegineer.common.persistence.schemes.serializable.ObjectInputStream, java.io.ObjectStreamClass)
     */
    @Override
    public Class<?> resolveClass(
        final ObjectInputStream stream,
        final ObjectStreamClass desc )
        throws IOException
    {
        assertArgumentNotNull( stream, "stream" ); //$NON-NLS-1$
        assertArgumentNotNull( desc, "desc" ); //$NON-NLS-1$

        if( desc.getName().equals( CardPileBaseDesignProxy.class.getName() ) )
        {
            return CardPileBaseDesignProxy.class;
        }

        return super.resolveClass( stream, desc );
    }
}

/*
 * CardSurfaceDesignExtensionProxyPersistenceDelegate.java
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
 * Created on Aug 18, 2010 at 11:37:10 PM.
 */

package org.gamegineer.table.internal.core.services.cardsurfacedesignregistry;

import net.jcip.annotations.Immutable;
import org.gamegineer.common.persistence.schemes.serializable.AbstractPersistenceDelegate;

/**
 * A persistence delegate for the {@code CardSurfaceDesignExtensionProxy} class.
 */
@Immutable
public final class CardSurfaceDesignExtensionProxyPersistenceDelegate
    extends AbstractPersistenceDelegate
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardSurfaceDesignExtensionProxyPersistenceDelegate} class.
     */
    public CardSurfaceDesignExtensionProxyPersistenceDelegate()
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
        if( !(obj instanceof CardSurfaceDesignExtensionProxy) )
        {
            return super.replaceObject( obj );
        }

        return ((CardSurfaceDesignExtensionProxy)obj).getDelegate();
    }
}

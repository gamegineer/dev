/*
 * ClassLoaderContextPersistenceDelegate.java
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
 * Created on May 7, 2010 at 11:15:16 PM.
 */

package org.gamegineer.common.persistence.beans;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import net.jcip.annotations.Immutable;

/**
 * A persistence delegate for the {@code ClassLoaderContext} class.
 */
@Immutable
final class ClassLoaderContextPersistenceDelegate
    extends PersistenceDelegate
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * ClassLoaderContextPersistenceDelegate} class.
     */
    ClassLoaderContextPersistenceDelegate()
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
        final ClassLoaderContext obj = (ClassLoaderContext)oldInstance;
        return new Expression( oldInstance, ClassLoaderContext.class, "new", new Object[] { //$NON-NLS-1$
                obj.getClassName()
            } );
    }
}

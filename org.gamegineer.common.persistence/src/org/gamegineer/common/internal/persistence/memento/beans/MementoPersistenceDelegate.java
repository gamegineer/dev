/*
 * MementoPersistenceDelegate.java
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
 * Created on Jul 1, 2008 at 12:36:27 AM.
 */

package org.gamegineer.common.internal.persistence.memento.beans;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.internal.persistence.memento.Memento;

/**
 * A persistence delegate for the {@code Memento} class.
 */
@Immutable
public final class MementoPersistenceDelegate
    extends PersistenceDelegate
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MementoPersistenceDelegate}
     * class.
     */
    public MementoPersistenceDelegate()
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
}

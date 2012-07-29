/*
 * AbsoluteContainerLayoutPersistenceDelegate.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Jul 5, 2012 at 8:46:59 PM.
 */

package org.gamegineer.table.internal.persistence.serializable;

import java.io.IOException;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegate;
import org.gamegineer.table.internal.core.AbsoluteContainerLayout;

/**
 * A persistence delegate for the {@link AbsoluteContainerLayout} class.
 */
@Immutable
public final class AbsoluteContainerLayoutPersistenceDelegate
    extends AbstractPersistenceDelegate
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbsoluteContainerLayoutPersistenceDelegate} class.
     */
    public AbsoluteContainerLayoutPersistenceDelegate()
    {
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
        throws IOException
    {
        if( obj instanceof AbsoluteContainerLayout )
        {
            return new AbsoluteContainerLayoutProxy( (AbsoluteContainerLayout)obj );
        }

        return super.replaceObject( obj );
    }
}
/*
 * ComponentPathPersistenceDelegate.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Jun 14, 2012 at 10:00:35 PM.
 */

package org.gamegineer.table.internal.persistence.serializable;

import java.io.IOException;
import net.jcip.annotations.Immutable;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegate;
import org.gamegineer.table.core.ComponentPath;

/**
 * A persistence delegate for the {@code ComponentPath} class.
 */
@Immutable
public final class ComponentPathPersistenceDelegate
    extends AbstractPersistenceDelegate
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentPathPersistenceDelegate} class.
     */
    public ComponentPathPersistenceDelegate()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegate#replaceObject(java.lang.Object)
     */
    @Override
    public @Nullable Object replaceObject(
        final @Nullable Object obj )
        throws IOException
    {
        if( obj instanceof ComponentPath )
        {
            return new ComponentPathProxy( (ComponentPath)obj );
        }

        return super.replaceObject( obj );
    }
}

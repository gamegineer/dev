/*
 * FakeNonSerializableClassPersistenceDelegate.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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

package org.gamegineer.common.persistence.serializable;

import java.io.IOException;
import net.jcip.annotations.Immutable;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A persistence delegate for the {@code FakeNonSerializableClass} class.
 */
@Immutable
public final class FakeNonSerializableClassPersistenceDelegate
    extends AbstractPersistenceDelegate
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code FakeNonSerializableClassPersistenceDelegate} class.
     */
    public FakeNonSerializableClassPersistenceDelegate()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegate#replaceObject(java.lang.Object)
     */
    @Nullable
    @Override
    public Object replaceObject(
        @Nullable
        final Object obj )
        throws IOException
    {
        if( obj instanceof FakeNonSerializableClass )
        {
            return new FakeNonSerializableClassProxy( (FakeNonSerializableClass)obj );
        }

        return super.replaceObject( obj );
    }
}

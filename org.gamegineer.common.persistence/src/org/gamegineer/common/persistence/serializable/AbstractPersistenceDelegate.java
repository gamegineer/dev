/*
 * AbstractPersistenceDelegate.java
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
 * Created on May 3, 2010 at 9:49:52 PM.
 */

package org.gamegineer.common.persistence.serializable;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.io.IOException;
import java.io.ObjectStreamClass;
import net.jcip.annotations.Immutable;

/**
 * Superclass for all implementations of
 * {@link org.gamegineer.common.persistence.serializable.IPersistenceDelegate}.
 */
@Immutable
public abstract class AbstractPersistenceDelegate
    implements IPersistenceDelegate
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractPersistenceDelegate}
     * class.
     */
    protected AbstractPersistenceDelegate()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.common.persistence.serializable.IPersistenceDelegate#annotateClass(org.gamegineer.common.persistence.serializable.ObjectOutputStream,
     *      java.lang.Class)
     */
    @Override
    @SuppressWarnings( "unused" )
    public void annotateClass(
        final ObjectOutputStream stream,
        final Class<?> cl )
        throws IOException
    {
        assertArgumentNotNull( stream, "stream" ); //$NON-NLS-1$
        assertArgumentNotNull( cl, "cl" ); //$NON-NLS-1$
    }

    /**
     * This implementation does not attempt to replace the object and returns
     * the same instance without modification.
     * 
     * @see org.gamegineer.common.persistence.serializable.IPersistenceDelegate#replaceObject(java.lang.Object)
     */
    @Override
    public Object replaceObject(
        final Object obj )
    {
        return obj;
    }

    /**
     * This implementation does not attempt to resolve the specified class and
     * always returns {@code null}.
     * 
     * @see org.gamegineer.common.persistence.serializable.IPersistenceDelegate#resolveClass(org.gamegineer.common.persistence.serializable.ObjectInputStream,
     *      java.io.ObjectStreamClass)
     */
    @Override
    @SuppressWarnings( "unused" )
    public Class<?> resolveClass(
        final ObjectInputStream stream,
        final ObjectStreamClass desc )
        throws IOException
    {
        assertArgumentNotNull( stream, "stream" ); //$NON-NLS-1$
        assertArgumentNotNull( desc, "desc" ); //$NON-NLS-1$

        return null;
    }

    /**
     * This implementation does not attempt to resolve the object and returns
     * the same instance without modification.
     * 
     * @see org.gamegineer.common.persistence.serializable.IPersistenceDelegate#resolveObject(java.lang.Object)
     */
    @Override
    public Object resolveObject(
        final Object obj )
    {
        return obj;
    }
}

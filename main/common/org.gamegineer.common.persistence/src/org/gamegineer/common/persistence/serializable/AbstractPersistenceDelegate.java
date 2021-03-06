/*
 * AbstractPersistenceDelegate.java
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
 * Created on May 3, 2010 at 9:49:52 PM.
 */

package org.gamegineer.common.persistence.serializable;

import java.io.IOException;
import java.io.ObjectStreamClass;
import net.jcip.annotations.Immutable;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Superclass for all implementations of {@link IPersistenceDelegate}.
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
        // do nothing
    }

    /**
     * This implementation does not attempt to replace the object and returns
     * the same instance without modification.
     * 
     * @see org.gamegineer.common.persistence.serializable.IPersistenceDelegate#replaceObject(java.lang.Object)
     */
    @Override
    @SuppressWarnings( "unused" )
    public @Nullable Object replaceObject(
        final @Nullable Object obj )
        throws IOException
    {
        return obj;
    }

    /**
     * This implementation returns the result of calling
     * 
     * <pre>
     * Class.forName( typeName, false, getClass().getClassLoader() )
     * </pre>
     * 
     * @see org.gamegineer.common.persistence.serializable.IPersistenceDelegate#resolveClass(org.gamegineer.common.persistence.serializable.ObjectInputStream,
     *      java.io.ObjectStreamClass)
     */
    @Override
    @SuppressWarnings( "unused" )
    public Class<?> resolveClass(
        final ObjectInputStream stream,
        final ObjectStreamClass desc )
        throws IOException, ClassNotFoundException
    {
        return Class.forName( desc.getName(), false, getClass().getClassLoader() );
    }

    /**
     * This implementation does not attempt to resolve the object and returns
     * the same instance without modification.
     * 
     * @see org.gamegineer.common.persistence.serializable.IPersistenceDelegate#resolveObject(java.lang.Object)
     */
    @Override
    @SuppressWarnings( "unused" )
    public @Nullable Object resolveObject(
        final @Nullable Object obj )
        throws IOException
    {
        return obj;
    }
}
